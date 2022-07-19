/*
 * TiffRead.java
 * version 1.0
 * 14 July 2022
 * Copyright 2022 Keith T. Knox
 * GNU General Public License v3.0
 *
 * Reads the scanline data of a TIFF image file
 * and provides it one scanline at a time, sequentially.
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

public class TiffRead
  {
  // Field variables
  private int diskBufferLength;
  private byte[] diskBuffer;
  private int numberDataBytesInBuffer;
  private int bufferBytePosition;
  
  // TIFF parameters
  private TiffHandle tiffHandle;
  private TiffHeader tiffHeader;
  private RandomAccessFile randomAccessFile;
  private long imageWidth;
  private long imageLength;
  private long bitsPerSample;
  private long inputSamplesPerPixel;
  private long outputSamplesPerPixel;
  private int compression;
  private int rowsPerStrip;
  private long extraSamples;
  private long[] stripByteCounts;
  private long[] stripBytesToBeRead;
  private long[] stripOffsets;
  private byte[] inputScanlineByteData;
  private byte[] scanlineByteData;
  private short[] scanlineShortData;
  private int[] scanlineIntData;
  private long[] scanlineLongData;
  private float[] scanlineFloatData;
  private double[] scanlineDoubleData;
  
  // Constructor
  public TiffRead(RandomAccessFile raf, TiffHandle th)
    {
    // Initialize
    tiffHandle = th;
    randomAccessFile = raf;
    tiffHeader = th.getTiffHeader();
    
    // Info from the Tiff handle
    imageWidth = tiffHandle.getImageWidth();
    imageLength = tiffHandle.getImageLength();
    bitsPerSample = tiffHandle.getBitsPerSample();
    inputSamplesPerPixel = tiffHandle.getSamplesPerPixel();
    // Doesn't seem to be needed
//    rowsPerStrip = (int) tiffHandle.getTiffTag("RowsPerStrip").getNumber();
    stripByteCounts = tiffHandle.getTiffTag("StripByteCounts").getArray();
    stripOffsets = tiffHandle.getTiffTag("StripOffsets").getArray();
    stripBytesToBeRead = new long[stripByteCounts.length];
    
    // Info from the Tiff header from tags that might not be present
    TiffTag compressionTag = tiffHandle.getTiffTag("Compression");
    TiffTag extraSamplesTag = tiffHandle.getTiffTag("ExtraSamples");
    compression = (compressionTag == null ) ? 1 : (int) tiffHandle.getTiffTag("Compression").getNumber();
    extraSamples = (extraSamplesTag == null ) ? 0 : tiffHandle.getTiffTag("ExtraSamples").getArray().length;
    outputSamplesPerPixel = inputSamplesPerPixel-extraSamples;
    
    // Deleting the ExtraSamples for now.
    // This means that alpha masks are being ignored.
    
    // Allocate the data buffers
    inputScanlineByteData = new byte[(int) (imageWidth*inputSamplesPerPixel*bitsPerSample/8)];
    scanlineByteData = new byte[(int) (imageWidth*outputSamplesPerPixel*bitsPerSample/8)];
    scanlineShortData = new short[(int) (imageWidth*outputSamplesPerPixel)];
    scanlineIntData = new int[(int) (imageWidth*outputSamplesPerPixel)];
    scanlineLongData = new long[(int) (imageWidth*outputSamplesPerPixel)];
    scanlineFloatData = new float[(int) (imageWidth*outputSamplesPerPixel)];
    scanlineDoubleData = new double[(int) (imageWidth*outputSamplesPerPixel)];

    // Make the disk data buffer and do the initial fill
    diskBufferLength = 10*1024*1024;
    diskBuffer = new byte[diskBufferLength];
    for (int n=0; n < stripByteCounts.length; n++) stripBytesToBeRead[n] = stripByteCounts[n];
    numberDataBytesInBuffer = 0;
    bufferBytePosition = 0;
    fillBuffer();
    }
  
  // Method to determine in which strip the scanline begins
  private void fillBuffer()
    {
    // Set the initial parameters
    int bytesRead = numberDataBytesInBuffer-bufferBytePosition;
    if (bytesRead > 0)
      {
      for (int n=0; n < bytesRead; n++) diskBuffer[n] = diskBuffer[n+bufferBytePosition];
      numberDataBytesInBuffer = bytesRead;
      bufferBytePosition = 0;
     }
    else
      {
      numberDataBytesInBuffer = 0;
      bufferBytePosition = 0;
      }
    
    // Find out in which strip to start
    for (int n=0; n < stripByteCounts.length; n++)
      {
      // Find the first strip that has not already been read
      if (stripBytesToBeRead[n] > 0)
        {
        // Determine the number of bytes that can be read from this strip
        int numberBytesToRead = diskBufferLength-bytesRead;
        int readLength = (stripBytesToBeRead[n] >= (long) numberBytesToRead) ? numberBytesToRead : (int) stripBytesToBeRead[n];
        
        // Seek to the beginning of the data
        long scanlineOffset = stripOffsets[n]+stripByteCounts[n]-stripBytesToBeRead[n];
        try { randomAccessFile.seek(scanlineOffset); }
        catch(IOException e) { }
        
        // Read in the data from the disk
        try { randomAccessFile.readFully(diskBuffer, bytesRead, readLength); }
        catch(IOException e) { }
        
        // Update the parameters
        bytesRead += readLength;
        stripBytesToBeRead[n] -= (long) readLength;
        numberDataBytesInBuffer += readLength;
        }
      
      // See if we need to read the next strip
      if (bytesRead >= diskBufferLength) break;
      }
     }
  
  // Method to return the next scanline
  public byte[] getNextByteScanlineData()
    {
    // Decompress the scanline data
    switch (compression)
      {
      case TiffTag.NONE:
        uncompressedScanline();
        break;
      case TiffTag.CCITT:
        break;
      case TiffTag.PACKBITS:
        packbitsScanline();
        break;
      }
    return scanlineByteData;
    }

  // Method to read a scanline and return the short data
  public short[] getNextShortScanlineData()
    {
    byte[] b = getNextByteScanlineData();
    for (int n=0; n < imageWidth*inputSamplesPerPixel; n++)
      {
      scanlineShortData[n] = tiffHeader.getShort(b, 2*n, 2);
//      Unnecessary, cannot happen, short is signed 16-bit number
//      if (scanlineShortData[n] > 32767) scanlineShortData[n] -= 32768;
      }
    return scanlineShortData;
    }

  // Method to read a scanline and return the int data
  public int[] getNextIntScanlineData()
    {
    byte[] b = getNextByteScanlineData();
    for (int n=0; n < imageWidth*inputSamplesPerPixel; n++)
      {
      scanlineIntData[n] = tiffHeader.getInt(b, 4*n, 4);
//    Unnecessary, cannot happen, int is signed 32-bit number
//      if (scanlineShortData[n] > 2147483647L) scanlineIntData[n] -= 2147483648L;
      }
    return scanlineIntData;
    }

  // Method to read a scanline and return the long data
  public long[] getNextLongScanlineData()
    {
    byte[] b = getNextByteScanlineData();
    for (int n=0; n < imageWidth*inputSamplesPerPixel; n++)
      {
      scanlineLongData[n] = tiffHeader.getLong(b, 8*n, 8);
      // Cannot do unsigned longs in Java
//      if (scanlineShortData[n] > 2147483647L) scanlineLongData[n] -= 2147483648L;
      }
    return scanlineLongData;
    }

  // Method to read a scanline and return the float data
  public float[] getNextFloatScanlineData()
    {
    byte[] b = getNextByteScanlineData();
    for (int n=0; n < imageWidth*inputSamplesPerPixel; n++) scanlineFloatData[n] = tiffHeader.getFloat(b, 4*n, 4);
    return scanlineFloatData;
    }
  
  // Method to read a scanline and return the double data
  public double[] getNextDoubleScanlineData()
    {
    byte[] b = getNextByteScanlineData();
    for (int n=0; n < imageWidth*inputSamplesPerPixel; n++) scanlineDoubleData[n] = tiffHeader.getDouble(b, 8*n, 8);
    return scanlineDoubleData;
    }
  
  private void uncompressedScanline()
    {
    int bytesAvailable = numberDataBytesInBuffer-bufferBytePosition;
    int numberBytes = (int) (imageWidth*bitsPerSample*inputSamplesPerPixel/8);
    if (bytesAvailable < numberBytes) fillBuffer();
    if (inputSamplesPerPixel == outputSamplesPerPixel) for (int n=0; n < numberBytes; n++) scanlineByteData[n] = diskBuffer[n+bufferBytePosition];
    else
      {
      int nBytesPerSample = (int) (bitsPerSample/8);
      for (int n=0; n < numberBytes; n++) inputScanlineByteData[n] = diskBuffer[n+bufferBytePosition];
      for (int n=0; n < imageWidth; n++) 
        {
        for (int k=0; k < outputSamplesPerPixel; k++)
          {
          for (int i=0; i < nBytesPerSample; i++)
            {
            scanlineByteData[(int) (nBytesPerSample*(n*outputSamplesPerPixel+k)+i)] = inputScanlineByteData[(int) (nBytesPerSample*(n*inputSamplesPerPixel+k)+i)];
            }
          }
        }
      }
    bufferBytePosition += numberBytes;
    }
  
  private void packbitsScanline()
    {
    int bytesAvailable = numberDataBytesInBuffer-bufferBytePosition;
    int numberBytes = 0;
    int scanlineBytes = (int) (imageWidth*bitsPerSample*inputSamplesPerPixel/8);
    int mode = 0;
    int length = 0;
    while (bytesAvailable > 0)
      {
      int tmp = diskBuffer[bufferBytePosition++];
      bytesAvailable--;
      if (mode == 0)
        {
        if (tmp >= 0)
          {
          // Copy length number of bytes
          mode = 1;
          length = tmp+1;
          }
        else if (tmp > -128)
          {
          // Copy next byte length times
          mode = 2;
          length = 1-tmp;
          }
        }
      else
        {
        if (mode == 1)
          {
          inputScanlineByteData[numberBytes++] = (byte) tmp;
          length--;
          if (length <= 0) mode = 0;
          }
        else
          {
          for (int n=0; n < length; n++) inputScanlineByteData[numberBytes++] = (byte) tmp;
          mode = 0;
          }
        }
      if (bytesAvailable <= 0)
        {
        fillBuffer();
        bytesAvailable = numberDataBytesInBuffer-bufferBytePosition;
        }
      if (numberBytes >= scanlineBytes) break;
      }
    
    // Copy data from input to output buffer
    int nBytesPerSample = (int) (bitsPerSample/8);
    for (int n=0; n < imageWidth; n++) 
      {
      for (int k=0; k < outputSamplesPerPixel; k++)
        {
        for (int i=0; i < nBytesPerSample; i++)
          {
          scanlineByteData[(int) (nBytesPerSample*(n*outputSamplesPerPixel+k)+i)] = inputScanlineByteData[(int) (nBytesPerSample*(n*inputSamplesPerPixel+k)+i)];
          }
        }
      }
    }


  }

