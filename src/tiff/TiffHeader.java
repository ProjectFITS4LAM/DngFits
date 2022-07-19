/*
 * TiffHeader.java
 * version 1.0
 * 14 July 2022
 * Copyright 2022 Keith T. Knox
 * GNU General Public License v3.0
 *
 * TIFF Header of a TIFF image file.
 *
 */

package tiff;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

/**
 * 
 * @author Keith T. Knox
 * @version 1.0
 *
 */

class TiffHeader
  {
  private String byteOrder;
  private int versionNumber;
  private int offsetBytesize;
  private long directoryOffset;

  // First Constructor
  TiffHeader(RandomAccessFile raf)
    {
    // Get the TIFF file header of 16 bytes
    byte[] header = new byte[16];
    readBuffer(raf, 0L, header);

    // Analyze the TIFF file header of 16 bytes
    byteOrder = new String(header, 0, 2);
    versionNumber = getInt(header, 2, 2);
    if (versionNumber == 42) directoryOffset = getLong(header, 4, 4);
    if (versionNumber == 43)
      {
      // Could check the offsetBytesize, but what's the point.  Should be 8;
      offsetBytesize = getInt(header, 4, 2);
      directoryOffset = getLong(header, 8, 8);
      }
    }
  
  // Second Constructor
  TiffHeader(byte[] buffer)
    {
    // Analyze the TIFF file header of 16 bytes
    byteOrder = new String(buffer, 0, 2);
    versionNumber = getInt(buffer, 2, 2);
    if (versionNumber == 42) directoryOffset = getLong(buffer, 4, 4);
    if (versionNumber == 43) directoryOffset = getLong(buffer, 8, 8);
    }

  // Third Constructor
  TiffHeader(String byteOrder, int versionNumber)
    {
    // Establish the byte order
    if (byteOrder.equals("II") || byteOrder.equals("MM")) this.byteOrder = byteOrder;
    else this.byteOrder = "MM";
    
    // Establish the version number
    if (versionNumber == 42 || versionNumber == 43) this.versionNumber = versionNumber;
    else this.versionNumber = 42;
    
    // Finish the header
    if (versionNumber == 42) directoryOffset = 8;
    else directoryOffset = 16;
    }
  
  protected void putBytes(byte[] buffer, int offset)
    {
    // This routine is only used to generate Hoku TIFF headers,
    // so assume that the directory immediately follows this header
    byte[] b = byteOrder.getBytes();
    for (int n=0; n < 2; n++) buffer[n+offset] = b[n];
    putInt(versionNumber, buffer, 2+offset, 2);
    int offsetBytesize = (versionNumber == 42) ? 4 : 8;
    if (versionNumber == 43)
      {
      putInt(offsetBytesize, buffer, 4+offset, 2);
      putInt(0, buffer, 6+offset, 2);
      }
    putInt((versionNumber == 42) ? 8 : 16, buffer, offsetBytesize+offset, offsetBytesize);
    }

  protected long getLength()
    {
    // This routine is used to define length of TIFF header
    if (versionNumber == 42) return 8L;
    if (versionNumber == 43) return 16L;
    return 0L;
    }

  protected String getByteOrder() { return byteOrder; }

  protected int getVersionNumber() { return versionNumber; }

  protected long getDirectoryOffset() { return directoryOffset; }

  protected long getOffsetBytesize() { return (versionNumber == 42) ? 4 : 8; }

  protected boolean isTiffFormat()
    {
    boolean order = (byteOrder.equals("II") || byteOrder.equals("MM"));
    boolean number = (versionNumber == 42) || (versionNumber == 43);
    return (order && number);
    }

  protected boolean isBigTiff() { return (versionNumber == 43); }

  protected short getShort(byte[] buffer, int offset, int nBytes)
    {
    int result = 0;
    int[] tmp = new int[nBytes];
    for (int n=0; n < nBytes; n++)
      {
      tmp[n] = buffer[offset+n];
      if (tmp[n] < 0) tmp[n] += 256;
      }
    for (int n=0; n < nBytes; n++)
      {
      if (byteOrder.equals("II")) result = 256*result+tmp[nBytes-n-1];
      else result = 256*result+tmp[n];
      }
    return (short) result;
    }

  protected int getInt(byte[] buffer, int offset, int nBytes)
    {
    int result = 0;
    int[] tmp = new int[nBytes];
    for (int n=0; n < nBytes; n++)
      {
      tmp[n] = buffer[offset+n];
      if (tmp[n] < 0) tmp[n] += 256;
      }
    for (int n=0; n < nBytes; n++)
      {
      if (byteOrder.equals("II")) result = 256*result+tmp[nBytes-n-1];
      else result = 256*result+tmp[n];
      }
    return result;
    }

  protected long getLong(byte[] buffer, int offset, int nBytes)
    {
    long result = 0;
    long[] tmp = new long[nBytes];
    for (int n=0; n < nBytes; n++)
      {
      tmp[n] = buffer[offset+n];
      if (tmp[n] < 0) tmp[n] += 256;
      }
    for (int n=0; n < nBytes; n++)
      {
      if (byteOrder.equals("II")) result = 256*result+tmp[nBytes-n-1];
      else result = 256*result+tmp[n];
      }
    return result;
    }

  protected float getFloat(byte[] buffer, int offset, int nBytes)
    {
    long result = 0;
    int[] tmp = new int[nBytes];
    for (int n=0; n < nBytes; n++)
      {
      tmp[n] = buffer[offset+n];
      if (tmp[n] < 0) tmp[n] += 256;
      }
    for (int n=0; n < nBytes; n++)
      {
      if (byteOrder.equals("II")) result = 256*result+tmp[nBytes-n-1];
      else result = 256*result+tmp[n];
      }
    return Float.intBitsToFloat((int) result);
    }

  protected double getDouble(byte[] buffer, int offset, int nBytes)
    {
    long result = 0;
    int[] tmp = new int[nBytes];
    for (int n=0; n < nBytes; n++)
      {
      tmp[n] = buffer[offset+n];
      if (tmp[n] < 0) tmp[n] += 256;
      }
    for (int n=0; n < nBytes; n++)
      {
      if (byteOrder.equals("II")) result = 256*result+tmp[nBytes-n-1];
      else result = 256*result+tmp[n];
      }
    return Double.longBitsToDouble( result);
    }

  protected void putInt(int value, byte[] buffer, int offset, int nBytes)
    {
    if (byteOrder.equals("II"))
      for (int n=0; n < nBytes; n++) { buffer[n+offset] = (byte) (value & 0xFF); value = (value >> 8); }
    else
      for (int n=nBytes-1; n >= 0; n--) { buffer[n+offset] = (byte) (value & 0xFF); value = (value >> 8); }
    }

  protected void putLong(long value, byte[] buffer, int offset, int nBytes)
    {
    if (byteOrder.equals("II"))
      for (int n=0; n < nBytes; n++) { buffer[n+offset] = (byte) (value & 0xFF); value = (value >> 8); }
    else
      for (int n=nBytes-1; n >= 0; n--) { buffer[n+offset] = (byte) (value & 0xFF); value = (value >> 8); }
    }

  protected void putShort(short value, byte[] buffer, int offset, int nBytes)
    {
    int tmp = value;
    if (tmp < 0) tmp += 65536;
    putInt(tmp, buffer, offset, nBytes);
    }

  protected void putFloat(float value, byte[] buffer, int offset, int nBytes)
    {
    int tmp = Float.floatToIntBits(value);
    putInt(tmp, buffer, offset, nBytes);
    }

  protected void putDouble(double value, byte[] buffer, int offset, int nBytes)
    {
    long tmp = Double.doubleToLongBits(value);
    putLong(tmp, buffer, offset, nBytes);
    }

  protected void readBuffer(RandomAccessFile raf, long directoryOffset, byte[] buffer)
    {
    try { raf.seek(directoryOffset); }
    catch(IOException e) { }
    try { raf.readFully(buffer); }
    catch(IOException e) { }
    }
  
  protected void setBigTIFF(boolean flag)
    {
    if (flag == false)
      {
      versionNumber = 42;
      directoryOffset = 8;
      }
    else
      {
      versionNumber = 43;
      directoryOffset = 16;
      }
    }
  
  
  //Method to print out the metadata
  protected void print(ArrayList<String> printout)
    {
    printout.add("*    Header");
    printout.add("******************************************");
    printout.add("byteOrder: "+byteOrder);
    printout.add("versionNumber: "+versionNumber);
    if (isBigTiff()) printout.add("offsetBytesize: "+offsetBytesize);
    printout.add("directoryOffset: "+directoryOffset);
    }

  }





