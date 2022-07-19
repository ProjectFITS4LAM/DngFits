/*
 * TiffEntry.java
 * version 1.0
 * 14 July 2022
 * Copyright 2022 Keith T. Knox
 * GNU General Public License v3.0
 *
 * Format of an entry in a TIFF directory.
 *
 */

package tiff;

import java.io.RandomAccessFile;
import java.util.ArrayList;

/**
 * 
 * @author Keith T. Knox
 * @version 1.0
 *
 */

public class TiffEntry
  {
  private int tiffTag;
  private int tiffType;
  private long tiffCount;
  private long tiffOffset;
  private TiffValue tiffValue;
  private TiffHeader tiffHeader;

  // First Constructor
  TiffEntry(RandomAccessFile raf, TiffHeader h, byte[] buffer, int offset)
    {
    // Information from the TIFF header
    tiffHeader = h;
    int versionNumber = tiffHeader.getVersionNumber();
    int offsetBytesize = 4, countLength = 4;
    if (versionNumber == 43) { offsetBytesize = 8; countLength = 8; }

    // Analyze the directory entry buffer
    int position = offset;
    tiffTag = h.getInt(buffer, position, 2); position += 2;
    tiffType = h.getInt(buffer, position, 2); position += 2;
    tiffCount = h.getLong(buffer, position, countLength); position += countLength;
    tiffOffset = h.getLong(buffer, position, offsetBytesize);


    // Need this kludge for IPTC type
    if (tiffTag == TiffTag.IPTC && tiffType == TiffTag.LONG) { tiffType = TiffTag.BYTE; tiffCount *= 4; }

    // Analyze the values, if tiffOffset is a pointer, read them from the disk
    if (fitsWithin(tiffType, tiffCount) == false)
      {
      byte[] buf = new byte[getValueLength()];
      tiffHeader.readBuffer(raf, tiffOffset, buf);
      buffer = buf; position = 0;
      }
    
    // Now points to the data, create the value from the data
    tiffValue = new TiffValue(buffer, position, getValueLength());
    }
  
  // Second Constructor
  TiffEntry(TiffHeader h, byte[] buffer, int offset, long directoryOffset)
    {
    // Information from the TIFF header
    tiffHeader = h;
    int versionNumber = tiffHeader.getVersionNumber();
    int offsetBytesize = 4, countLength = 4;
    if (versionNumber == 43) { offsetBytesize = 8; countLength = 8; }

    // Analyze the directory entry buffer
    int position = offset;
    tiffTag = h.getInt(buffer, position, 2); position += 2;
    tiffType = h.getInt(buffer, position, 2); position += 2;
    tiffCount = h.getLong(buffer, position, countLength); position += countLength;
    tiffOffset = h.getLong(buffer, position, offsetBytesize);
    
    // Need this kludge for IPTC type
    if (tiffTag == TiffTag.IPTC && tiffType == TiffTag.LONG) { tiffType = TiffTag.BYTE; tiffCount *= 4; }
    if (tiffTag == TiffTag.EXIFIFD) return;

    // Analyze the values
    if (fitsWithin(tiffType, tiffCount) == false) position = (int) tiffOffset;
    tiffValue = new TiffValue(buffer, position, getValueLength());
    }

  // Third Constructor
  TiffEntry(int tTag, int tType, long tCount, TiffHeader h)
    {
    // Information from the TIFF header
    tiffHeader = h;

    // Create a TiffEntry
    tiffTag = tTag;
    tiffType = tType;
    tiffCount = tCount;
    tiffOffset = 0;
    tiffValue = new TiffValue(null, 0, 0);
    }
  
  // Fourth Constructor
  TiffEntry(TiffHeader h, String name)
    {
    // Information from the TIFF header
    tiffHeader = h;

    // Create a TiffEntry
    tiffTag = TiffTag.getTagCode(name);
    tiffType = TiffTag.getTagType(tiffTag);
    tiffCount = 0;
    tiffOffset = 0;
    tiffValue = new TiffValue(null, 0, 0);
    }
  
  protected int getTiffTagCode() { return tiffTag; }
  protected int getTiffType() { return tiffType; }
  protected long getTiffCount() { return tiffCount; }
  protected long getTiffOffset() { return tiffOffset; }
  protected TiffValue getTiffValue() { return tiffValue; }
  protected void setTiffType(int tType) { tiffType = tType; }
  protected void setTiffCount(long tCount) { tiffCount = tCount; }

  public String getStringValues()
    {
    byte[] b = tiffValue.getValuesByteBuffer();
    byte[] c = new byte[b.length];
    for (int m=0; m < b.length; m++)
      {
      c[m] = b[m];
      if (c[m] == 0) c[m] = 9;
      }
    return new String(c);
    }
  
  public long[] getLongValues()
    {
    if (tiffValue == null) return null;
    int len = (int) tiffCount;
    if (tiffType == TiffTag.RATIONAL || tiffType == TiffTag.SRATIONAL) len *= 2;
    long[] result = new long[len];
    byte[] b = tiffValue.getValuesByteBuffer();
    for (int n=0; n < len; n++)
      {
      switch (tiffType)
        {
        case TiffTag.BYTE:
        case TiffTag.UNDEFINED:
          result[n] = tiffHeader.getLong(b, n, 1);
          break;
        case TiffTag.SBYTE:
          result[n] = tiffHeader.getLong(b, n, 1);
          if (result[n] > 127) result[n] -= 256;
          break;
        case TiffTag.SHORT:
          result[n] = tiffHeader.getLong(b, 2*n, 2);
          break;
        case TiffTag.SSHORT:
          result[n] = tiffHeader.getLong(b, 2*n, 2);
          if (result[n] > 32767) result[n] -= 65536;
          break;
        case TiffTag.LONG:
          result[n] = tiffHeader.getLong(b, 4*n, 4);
          break;
        case TiffTag.SLONG:
          result[n] = tiffHeader.getLong(b, 4*n, 4);
          if (result[n] > 2147483647L) result[n] -= 4294967296L;
          break;
        case TiffTag.RATIONAL:
          result[n] = tiffHeader.getLong(b, 4*n, 4);
          break;
        case TiffTag.SRATIONAL:
          result[n] = tiffHeader.getLong(b, 4*n, 4);
          if (result[n] > 2147483647L) result[n] -= 4294967296L;
          break;
        case TiffTag.IFD:
          result[n] = tiffHeader.getLong(b, 4*n, 4);
          break;
        case TiffTag.IFD8:
        case TiffTag.LONG8:
        case TiffTag.SLONG8:
          result[n] = tiffHeader.getLong(b, 8*n, 8);
          break;
        }
      }
    return result;
    }

  public double[] getDoubleValues()
    {
    if (tiffValue == null) return null;
    int len = (int) tiffCount;
    double[] result = new double[len];
    byte[] b = tiffValue.getValuesByteBuffer();
    for (int n=0; n < len; n++)
      {
      switch (tiffType)
        {
        case TiffTag.FLOAT:
          result[n] = tiffHeader.getDouble(b, 4*n, 4);
          break;
        case TiffTag.DOUBLE:
          result[n] = tiffHeader.getDouble(b, 8*n, 8);
          break;
        }
      }
    return result;
    }

  public void putStringValues(String inputString)
    {
    TiffValue value = getTiffValue();
    byte[] s = inputString.getBytes();
    int length = s.length+1;
    tiffCount = length;
    int offsetBytesize = (tiffHeader.getVersionNumber() == 42) ? 4 : 8;
    if (length < offsetBytesize) length = offsetBytesize;
    byte[] b = new byte[length];
    for (int n=0; n < s.length; n++) b[n] = s[n];
    b[s.length]= 0; 
    value.setValuesByteBuffer(b);
    }

  public void putLongValues(long[] input)
    {
    TiffValue value = getTiffValue();
    tiffCount = input.length;
    if (tiffType == TiffTag.RATIONAL || tiffType == TiffTag.SRATIONAL) tiffCount = tiffCount/2;
    long comparison = 65536L*65536L;
//    setTiffType(TiffTag.LONG);
    if (tiffType == TiffTag.LONG) for (int n=0; n < input.length; n++) if (input[n] > comparison) { setTiffType(TiffTag.LONG8); break; }
    if (tiffType == TiffTag.LONG8)
      {
      boolean greater = false;
      for (int n=0; n < input.length; n++) if (input[n] > comparison) { greater = true; break; }
      if (greater == false) setTiffType(TiffTag.LONG);
      }
    int length = (int) getValueLength();
    byte[] b = new byte[length];
    for (int n=0; n < input.length; n++)
      {
      switch (tiffType)
        {
        case TiffTag.BYTE:
        case TiffTag.UNDEFINED:
          tiffHeader.putLong(input[n], b, n, 1);
          break;
        case TiffTag.SBYTE:
          tiffHeader.putLong(input[n], b, n, 1);
          break;
        case TiffTag.SHORT:
          tiffHeader.putLong(input[n], b, 2*n, 2);
          break;
        case TiffTag.SSHORT:
          tiffHeader.putLong(input[n], b, 2*n, 2);
          break;
        case TiffTag.IFD:
        case TiffTag.LONG:
          tiffHeader.putLong(input[n], b, 4*n, 4);
          break;
        case TiffTag.SLONG:
          tiffHeader.putLong(input[n], b, 4*n, 4);
          break;
        case TiffTag.RATIONAL:
          tiffHeader.putLong(input[n], b, 4*n, 4);
          break;
        case TiffTag.SRATIONAL:
          tiffHeader.putLong(input[n], b, 4*n, 4);
          break;
        case TiffTag.IFD8:
        case TiffTag.LONG8:
        case TiffTag.SLONG8:
          tiffHeader.putLong(input[n], b, 8*n, 8);
          break;
        }
      }
    value.setValuesByteBuffer(b);
    }
  
  public void putDoubleValues(double[] input)
    {
    TiffValue value = getTiffValue();
    tiffCount = input.length;
    int length = (int) getValueLength();
    byte[] b = new byte[length];
    for (int n=0; n < input.length; n++)
      {
      switch (tiffType)
        {
        case TiffTag.FLOAT:
          tiffHeader.putDouble(input[n], b, 4*n, 4);
          break;
        case TiffTag.DOUBLE:
          tiffHeader.putDouble(input[n], b, 8*n, 8);
          break;
        }
      }
    value.setValuesByteBuffer(b);
    }
  
  // Method to write entry data into output buffer
  protected void putBytes(byte[] buffer, long directoryPosition, long valuesPosition)
    {
    int versionNumber = tiffHeader.getVersionNumber();
    int numberLength = 2, entryLength = 12, offsetBytesize = 4, countLength = 4;
    if (versionNumber == 43) { numberLength = 8; entryLength = 20; offsetBytesize = 8; countLength = 8; }
    tiffHeader.putInt(tiffTag, buffer, (int) directoryPosition, 2);
    tiffHeader.putInt(tiffType, buffer, (int) directoryPosition+2, 2);
    tiffHeader.putLong(tiffCount, buffer, (int) directoryPosition+4, countLength);
    directoryPosition += 4+countLength;
    
    // Must write out the offset pointer for the EXIF directory
    if (tiffTag == TiffTag.EXIFIFD || tiffTag == TiffTag.IPTC || tiffTag == TiffTag.GPSIFD || tiffTag == TiffTag.INTEROPERABILITYIFD)
      {
      tiffHeader.putLong(valuesPosition, buffer, (int) directoryPosition, offsetBytesize);
      return;
      }

    // Must write out the offset pointer(s) for the sub directories
    if (tiffTag == TiffTag.SUBIFDS)
      {
      long[] b = getLongValues();
      for (int m=0; m < b.length; m++)
        {
        tiffHeader.putLong(b[m], buffer, (int) (directoryPosition+m*offsetBytesize), offsetBytesize);
        }
      return;
      }

    // Put out the values buffer data
    if (tiffValue == null) return;
    byte[] b = tiffValue.getValuesByteBuffer();
    if (fitsWithin(tiffType, tiffCount) == true)
      {
      for (int n=0; n < offsetBytesize; n++) buffer[(int) directoryPosition+n] = 0;
      for (int n=0; n < tiffValue.getValuesByteLength(); n++) buffer[(int) directoryPosition+n] = b[n];
      }
    else
      {
      for (int m=0; m < tiffValue.getValuesByteLength(); m++) buffer[(int) (valuesPosition+m)] = b[m];
      tiffHeader.putLong(valuesPosition, buffer, (int) directoryPosition, offsetBytesize);
      }
    }

  // Method to determine if values fit within this entry's value position
  protected boolean fitsWithin(int type, long count)
    {
    int versionNumber = tiffHeader.getVersionNumber();
    int offsetBytesize = (versionNumber == 42) ? 4 : 8;
    switch (tiffType)
        {
        case TiffTag.ASCII:
        case TiffTag.BYTE:
        case TiffTag.SBYTE:
        case TiffTag.UNDEFINED:
          if (count <= offsetBytesize) return true;
          else return false;
        case TiffTag.SHORT:
        case TiffTag.SSHORT:
          if (count <= offsetBytesize/2) return true;
          else return false;
        case TiffTag.LONG:
        case TiffTag.SLONG:
        case TiffTag.FLOAT:
        case TiffTag.IFD:
          if (count <= offsetBytesize/4) return true;
          else return false;
        case TiffTag.RATIONAL:
        case TiffTag.SRATIONAL:
        case TiffTag.DOUBLE:
        case TiffTag.LONG8:
        case TiffTag.SLONG8:
        case TiffTag.IFD8:
          if (count <= offsetBytesize/8) return true;
          else return false;
        }
    return false;
    }

  // Method to determine how many bytes are used by this entry's values
  protected int getValueLength()
    {
    int result = 0;
    switch (tiffType)
      {
      case TiffTag.ASCII:
      case TiffTag.BYTE:
      case TiffTag.SBYTE:
      case TiffTag.UNDEFINED:
        result = (int) tiffCount;
        break;
      case TiffTag.SHORT:
      case TiffTag.SSHORT:
        result = 2*(int) tiffCount;
        break;
      case TiffTag.LONG:
      case TiffTag.SLONG:
      case TiffTag.FLOAT:
      case TiffTag.IFD:
        result = 4*(int) tiffCount;
        break;
      case TiffTag.RATIONAL:
      case TiffTag.SRATIONAL:
      case TiffTag.DOUBLE:
      case TiffTag.LONG8:
      case TiffTag.SLONG8:
      case TiffTag.IFD8:
        result = 8*(int) tiffCount;
        break;
      }
    if ((result % 2) == 1) result++;
    return result;
    }

  // Method to extract the labels for tag values
  private String retrieveLabel(String name, int value)
    {
    // Find the labels listed under "name"
    String result = null;
    for (int n=0; n < TiffTag.labels.length; n++)
      {
      String label = TiffTag.labels[n];
      int index = label.indexOf(":");
      String tagName = label.substring(0, index);
      label = label.substring(index+1);
      if (name.equalsIgnoreCase(tagName)) { result = label; break; }
      }
    if (result == null) return result;
    
    // Find the appropriate label, depending on the value
    int index = result.indexOf(":");
    while (index > -1)
      {
      String number = result.substring(0, index);
      int stop = result.indexOf(":", index+1);
      String label = (stop > -1) ? result.substring(index+1, stop) : result.substring(index+1);
      if (Integer.parseInt(number) == value) { result = label; break; }
      if (stop > -1) { result = result.substring(stop+1); index = result.indexOf(":"); }
      else { result = null; index = -1; }
      }
    return result;
    }
  

  // Method to print out the metadata
  protected void print(ArrayList<String> printout, String title)
    {
    print(printout, title, null);
    }
  
  // Method to print out the metadata
  protected void print(ArrayList<String> printout, String title, String type)
    {
    printout.add(title);
    String hex = Integer.toHexString(tiffTag);
    if (hex.length() == 1) hex = "0x000"+hex;
    else if (hex.length() == 2) hex = "0x00"+hex;
    else if (hex.length() == 3) hex = "0x0"+hex;
    else if (hex.length() == 4) hex = "0x"+hex;
    String tagName = TiffTag.getTagName(tiffTag);
    if (type != null)
      {
      if (type.equals("Exif")) tagName = TiffTag.getExifTagName(tiffTag);
      else if (type.equals("GPS")) tagName = TiffTag.getGpsTagName(tiffTag);
      else if (type.equals("Interoperability")) tagName = TiffTag.getInterTagName(tiffTag);
      }
    printout.add("Tag: ("+tiffTag+"/"+hex+") "+tagName);
    printout.add("Type: ("+tiffType+") "+TiffTag.getTiffTypeName(tiffType));
    printout.add("Count: "+tiffCount+(tiffCount == 1 ? " value" : " values"));
    if (tiffValue == null) printout.add("tiffValue: ("+tiffValue+")");
    else
      {
      StringBuilder buf = new StringBuilder();
      byte[] b = tiffValue.getValuesByteBuffer();
      switch (tiffType)
        {
        case TiffTag.BYTE:
        case TiffTag.SBYTE:
        case TiffTag.SHORT:
        case TiffTag.SSHORT:
        case TiffTag.LONG:
        case TiffTag.SLONG:
        case TiffTag.LONG8:
        case TiffTag.SLONG8:
        case TiffTag.IFD:
        case TiffTag.IFD8:
        case TiffTag.UNDEFINED:
          long[] tmp = getLongValues();
          buf.append("Value: ");
          // Added if tmp is length == 0  7 March 2020
          if (tmp == null || tmp.length == 0) buf.append("null");
          else
            {
            for (int m=0; m < (int) tiffCount; m++) buf.append(tmp[m] + " ");
            String label = retrieveLabel(tagName, (int) tmp[0]);
            if (label != null) buf.append("("+label+")");
            }
          printout.add(buf.toString());
          break;
        case TiffTag.RATIONAL:
        case TiffTag.SRATIONAL:
          tmp = getLongValues();
          buf.append("Value: ");
          if (tmp == null) buf.append("null");
          else for (int m=0; m < (int) tiffCount; m++)
            {
            buf.append(tmp[2*m]+"/"+tmp[2*m+1]+" = "+(double) tmp[2*m] / (double) tmp[2*m+1]);
            if (m != (int) (tiffCount-1)) buf.append(", ");
            }
          printout.add(buf.toString());
          break;
        case TiffTag.FLOAT:
        case TiffTag.DOUBLE:
          double[] dtmp = getDoubleValues();
          buf.append("Value: ");
          if (dtmp == null) buf.append("null");
          else for (int m=0; m < (int) tiffCount; m++) buf.append(dtmp[m] + " ");
          printout.add(buf.toString());
          break;
        case TiffTag.ASCII:
          byte[] c = new byte[b.length];
          for (int m=0; m < b.length; m++)
            {
            c[m] = b[m];
            if (c[m] == 0) c[m] = 9;
            }
          String s = new String(c);
          printout.add("Value: " + s.trim());
          break;
        }
      }
    }

  }






