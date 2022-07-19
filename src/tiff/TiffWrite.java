/*
 * TiffWrite.java
 * version 1.0
 * 14 July 2022
 * Copyright 2022 Keith T. Knox
 * GNU General Public License v3.0
 * 
 * Writes out one scanline at a time, sequentially.
 * 
 */

package tiff;

import java.io.*;

/**
 * 
 * @author Keith T. Knox
 * @version 1.0
 *
 */

public class TiffWrite
  {
  // Field variables
  private int diskBufferLength;
  private byte[] diskBuffer;
  private int bufferBytePosition;
  private byte[] byteScanlineBuffer;
  
  // TIFF parameters
  private TiffHandle tiffHandle;
  private TiffHeader tiffHeader;
  private RandomAccessFile randomAccessFile;
  private long imageWidth;
  private long imageLength;
  private long bitsPerSample;
  private long samplesPerPixel;
  private int compression;
  private long[] stripByteCounts;
  private long[] stripOffsets;
  private int scanlineNumber = 0;
  
  // Constructor
  public TiffWrite(RandomAccessFile raf, TiffHandle th)
    {
    // Initialize
    tiffHandle = th;
    randomAccessFile = raf;
    tiffHeader = th.getTiffHeader();
    
    // Info from the Tiff header
    imageWidth = tiffHandle.getTiffTag("ImageWidth").getNumber();
    imageLength = tiffHandle.getTiffTag("ImageLength").getNumber();
    bitsPerSample = tiffHandle.getTiffTag("BitsPerSample").getNumber();
    samplesPerPixel = tiffHandle.getTiffTag("SamplesPerPixel").getNumber();
    compression = (int) tiffHandle.getTiffTag("Compression").getNumber();
    
    // Make the disk data buffer and allocate arrays
    diskBufferLength = 10*1024*1024;
    diskBuffer = new byte[diskBufferLength];
    bufferBytePosition = 0;
    int length = ((int) imageWidth)*((int) bitsPerSample)*((int) samplesPerPixel)/8;  // Does not take complex into account.
    byteScanlineBuffer = new byte[length];
    stripByteCounts = new long[(int) imageLength];
    stripOffsets = new long[(int) imageLength];
    }
  
  // Method to determine in which strip the scanline begins
  private void flushBuffer()
    {
    // Write the data to the disk
    if (bufferBytePosition == 0) return;
    try { randomAccessFile.write(diskBuffer, 0, bufferBytePosition); }
    catch(IOException e) { }
    bufferBytePosition = 0;
    }
  
  // Method to write the next scanline of byte data
  public void putNextScanlineByteData(byte[] b)
    {
    // Compress the scanline data
    int result = 0;
    if (compression == TiffTag.getTiffCompressionTypes("NONE")) result = encodeUncompressedScanline(b);
    else if (compression == TiffTag.getTiffCompressionTypes("CCITT")) result = 0;
    else if (compression == TiffTag.getTiffCompressionTypes("PACKBITS")) result = encodePackbitsScanline(b);
    stripByteCounts[scanlineNumber] = result;
    stripOffsets[scanlineNumber] = result;
    if (scanlineNumber > 0) stripOffsets[scanlineNumber] += stripOffsets[scanlineNumber-1];
    scanlineNumber++;
    if (scanlineNumber >= (int) imageLength) flushBuffer();
    }
  
  public long[] getCompressedStripByteCounts() { return stripByteCounts; }

  public long[] getCompressedStripOffsets() { return stripOffsets; }

  // Method to write the next scanline of short data
  public void putNextScanlineShortData(short[] s)
    {
    // Convert to a byte array then store
    for (int n=0; n < imageWidth*samplesPerPixel; n++) { tiffHeader.putShort(s[n], byteScanlineBuffer, 2*n, 2); }
    putNextScanlineByteData(byteScanlineBuffer);
    }

  // Method to write the next scanline of int data
  public void putNextScanlineIntData(int[] s)
    {
    // Convert to a byte array then store
    for (int n=0; n < imageWidth*samplesPerPixel; n++) { tiffHeader.putInt(s[n], byteScanlineBuffer, 4*n, 4); }
    putNextScanlineByteData(byteScanlineBuffer);
    }

  // Method to write the next scanline of long data
  public void putNextScanlineLongData(long[] s)
    {
    // Convert to a byte array then store
    for (int n=0; n < imageWidth*samplesPerPixel; n++) { tiffHeader.putLong(s[n], byteScanlineBuffer, 8*n, 8); }
    putNextScanlineByteData(byteScanlineBuffer);
    }

  // Method to write the next scanline of float data
  public void putNextScanlineFloatData(float[] f)
    {
    for (int n=0; n < imageWidth*samplesPerPixel; n++) tiffHeader.putFloat(f[n], byteScanlineBuffer, 4*n, 4);
    putNextScanlineByteData(byteScanlineBuffer);
    }
  
  // Method to write the next scanline of double data
  public void putNextScanlineDoubleData(double[] d)
    {
    for (int n=0; n < imageWidth*samplesPerPixel; n++) tiffHeader.putDouble(d[n], byteScanlineBuffer, 8*n, 8);
    putNextScanlineByteData(byteScanlineBuffer);
    }
  
  private int encodeUncompressedScanline(byte[] b)
    {
    int position = 0;
    int numberBytes = b.length;
    while (numberBytes > 0)
      {
      int bytesAvailable = diskBufferLength-bufferBytePosition;
      int length = (bytesAvailable >= numberBytes) ? numberBytes : bytesAvailable;
      for (int n=0; n < length; n++) diskBuffer[bufferBytePosition++] = b[position++];
      numberBytes -= length;
      if (numberBytes > 0) flushBuffer();
      }
    return b.length;
    }
  
  private int getToken(byte[] b, int offset, int length)
    {
    // Look at the first three bytes
    boolean identicalToken;
    if (length > 2) identicalToken = ((b[0] == b[1]) && (b[1] == b[2]));
    else identicalToken = false;
    
    // Find a block of at least three identical bytes
    int position = 0;
    if (identicalToken == true)
      {
      for (int n=0; n < (length-1); n++)
        {
        if (b[offset+position] == b[offset+position+1])
          {
          position++;
          if (position >= 127) return -position;
          }
        else return -position;
        }
      }
    
    // Measure the block of data to be copied literally
    position = 3;
    for (int n=3; n < length; n++)
      {
      int pos = offset+position;
      if ((b[pos] == b[pos-1]) && (b[pos-1] == b[pos-2])) return position-3;
      else position++;
      }
    return position-1;
    }
  
  private int encodePackbitsScanline(byte[] b)
    { 
    int position = 0;
    int numberBytes = b.length;
    int length;
    int total = 0;
    while (numberBytes > 0)
      {
      int bytesAvailable = diskBufferLength-bufferBytePosition;
      int token = getToken(b, position, numberBytes);
      if (token < 0)
        {
        length = 1-token;
        if (bytesAvailable < 2) flushBuffer();
        diskBuffer[bufferBytePosition++] = (byte) token;
        diskBuffer[bufferBytePosition++] = b[position];
        position += length;
        total += 2;
        }
      else
        {
        length = 1+token;
        if (bytesAvailable < (length+1)) flushBuffer();
        diskBuffer[bufferBytePosition++] = (byte) token;
        for (int n=0; n < length; n++) diskBuffer[bufferBytePosition++] = b[position++];
        total += (length+1);
        }
      numberBytes -= length;
      }
    return total;
    }


  }

