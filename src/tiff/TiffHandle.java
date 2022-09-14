/*
 * TiffHandle.java
 * version 1.0
 * 14 July 2022
 * Copyright 2022 Keith T. Knox
 * GNU General Public License v3.0
 *
 * Reads in the metadata of a TIFF image file and creates a handle.
 *
 */

package tiff;

import java.util.*;
import java.io.*;

/**
 *
 * @author Keith T. Knox
 * @author Giuliano Giuffrida
 * @version 1.0
 *
 */

public class TiffHandle
  {
  // Private fields
  private TiffHeader tiffHeader;
  private TiffDirectory tiffDirectory;
  private TiffDirectory imageDirectory;
  private int tiffDirectoryNumber;
  private ArrayList<TiffDirectory> tiffDirectoryList;
  private ArrayList<String> printout = new ArrayList<String>();  
  
  // First Constructor -- open a TiffHandle from a file
  public TiffHandle(RandomAccessFile raf)
    {
    // Read in all the image directories
    if (acquire(raf, null) == false) return;
    
    // Set image directory 0 to be the main image directory
    tiffDirectoryNumber = 0;
    tiffDirectory = tiffDirectoryList.get(0);
    imageDirectory = tiffDirectory;
    }
  
  // Second Constructor -- copy an existing TiffHandle
  public TiffHandle(TiffHandle src)
    {
    // Read in all the image directories
    if (acquire(null, src) == false) return;
        
    // Find the main image directory
    tiffDirectoryNumber = 0;
    tiffDirectory = tiffDirectoryList.get(0);
    imageDirectory = findDirectory("Full");
    if (imageDirectory == null) imageDirectory = findDirectory("Reduced");
    }
  
  // Third Constructor -- construct one from scratch
  public TiffHandle(String byteOrder, int pixels, int scanlines, int samples, int bits, int type)
    {
    tiffDirectoryList = new ArrayList<TiffDirectory>();
    tiffHeader = new TiffHeader(byteOrder, 42);  // Ask for 42.  It will adjust upwards, if necessary.
    tiffDirectory = new TiffDirectory(tiffHeader);
    imageDirectory = tiffDirectory;
    tiffDirectoryList.add(tiffDirectory);
    tiffDirectory.insertImage("Full", pixels, scanlines, samples, bits, type);
    checkBigTiffRequired();
    }

  // Fourth Constructor -- find the image with the appropriate SubFileType
  public TiffHandle(RandomAccessFile raf, String subFileType)
    {
    // Read in all the image directories
    if (acquire(raf, null) == false) return;
    if (subFileType == null)
      {
      tiffDirectoryNumber = 0;
      tiffDirectory = tiffDirectoryList.get(0);
      imageDirectory = tiffDirectory;
      return;
      }
    
    // Find a directory of the appropriate SubFileType
    if (subFileType.equalsIgnoreCase("Full"))
      {
      imageDirectory = findDirectory("Full");
      if (imageDirectory == null) imageDirectory = findDirectory("Reduced");
      return;
      }
    if (subFileType.equalsIgnoreCase("Reduced"))
      {
      imageDirectory = findDirectory("Reduced");
      if (imageDirectory == null) imageDirectory = findDirectory("Full");
      return;
      }
      
    // We didn't find anything -- punt
    tiffDirectoryNumber = 0;
    tiffDirectory = tiffDirectoryList.get(0);
    imageDirectory = tiffDirectory;
    }
  
  // Fifth Constructor -- bare bones handle - only a header with byteOrder specified
  public TiffHandle(String byteOrder)
    {
    tiffDirectoryList = new ArrayList<TiffDirectory>();
    tiffHeader = new TiffHeader(byteOrder, 42);  // Ask for 42.  It will adjust upwards, if necessary.
    }
    
  private boolean acquire(RandomAccessFile raf, TiffHandle src)
    {
    // Analyze the TIFF header
    byte[] buffer = null;
    if (src != null)
      {
      buffer = src.getBytes();
      tiffHeader = new TiffHeader(buffer);
      }
    if (raf != null) tiffHeader = new TiffHeader(raf);
    if (tiffHeader.isTiffFormat() == false) return false;
    tiffDirectoryList = new ArrayList<TiffDirectory>();

    // Read in all the image directories
    long nextTiffDirectoryOffset = tiffHeader.getDirectoryOffset();
    while (nextTiffDirectoryOffset != 0)
      {
      TiffDirectory nextTiffDirectory = null;
      if (raf != null) nextTiffDirectory = new TiffDirectory(raf, tiffHeader, nextTiffDirectoryOffset);
      if (src != null) nextTiffDirectory = new TiffDirectory(tiffHeader, buffer, (int) nextTiffDirectoryOffset);
      tiffDirectoryList.add(nextTiffDirectory);
      nextTiffDirectoryOffset = nextTiffDirectory.getNextDirectoryOffset();
      }
    
    return true;
    }
  
  private TiffDirectory findDirectory(String subFileType)
    {
    // Find a directory of the appropriate SubFileType
    int code = TiffTag.getTiffTagCode("NewSubFileType");
    for (int n=0; n < tiffDirectoryList.size(); n++)
      {
      TiffDirectory td = tiffDirectoryList.get(n);
      tiffDirectory = td;
      tiffDirectoryNumber = n;
      String type = td.getNewSubFileType();
      if (type == null) type = "null";
      if (type.equalsIgnoreCase(subFileType))
        {
        imageDirectory = td;
        return td;
        }
      else
        {
        ArrayList<TiffDirectory> sdl = td.getSubDirectoryList();
        for (int m=0; m < sdl.size(); m++)
          {
          TiffDirectory sd = sdl.get(m);
          type = sd.getNewSubFileType();
          if (type == null) type = "null";
          if (type.equalsIgnoreCase(subFileType))
            {
            imageDirectory = sd;
            return sd;
            }
          }
        }
      }
    return null;
    }
  
  
  /*
   * Methods to get create directories and insert entries.
   */
  
  // Method to make a TIFF, an EXIF, or a SubIFD directory
  public TiffDirectory makeTiffDirectory()  { return new TiffDirectory(tiffHeader); }
  
  // Method to make an IPTC Record, if necessary
  public IPTCRecord makeIPTCRecord() { return new IPTCRecord(); }
  
  // Method to append directory to this directory
  public int appendTiffDirectory(TiffDirectory tiffDir)
    {
    // Check for an existing IFD, add to the last one
    if (tiffDirectoryList.size() == 0) tiffDirectory = tiffDir;
    tiffDirectoryList.add(tiffDir);
    return (tiffDirectoryList.size()-1);
    }
  
  // Method to set the imageDirectory
  public void setImageDirectory(TiffDirectory tiffDir)
    {
    imageDirectory = tiffDir;
    }
  
  // Method to retrieve directory list
  public ArrayList<TiffDirectory> getDirectoryList() { return tiffDirectoryList; }
  
  
  /*
   * Methods to get internal pointers and get information.
   */
  
  // Method to convert metadata into an array of bytes
  public byte[] getBytes()
    {
    // Make one long buffer with all of the metadata
    checkBigTiffRequired();
    long length = getLength();
    byte[] result = new byte[(int) length];
    tiffHeader.putBytes(result, 0);
    long pos = tiffHeader.getLength();
    for (int n=0; n < tiffDirectoryList.size(); n++)
      {
      TiffDirectory td = tiffDirectoryList.get(n);
      length = td.getLength();
      long offset = ((n+1) < tiffDirectoryList.size()) ? length : 0;
      td.putBytes(result, pos, offset);
      pos += length;
      }
    return result;
    }
  
  // Method to return the byte order
  public String getByteOrder() { return tiffHeader.getByteOrder(); }
  
  // Method to return the version number
  public int getVersionNumber() { return tiffHeader.getVersionNumber(); }
  
  // Method to return the directory offset
  protected long getDirectoryOffset() { return tiffHeader.getDirectoryOffset(); }
  
  // Method to return the header length
  protected long getLength()
    {
    long length = 0;
    if (tiffHeader != null) length += tiffHeader.getLength();
    for (int n=0; n < tiffDirectoryList.size(); n++) length += tiffDirectoryList.get(n).getLength();
    return length;
    }
  
  // Method to say if is TIFF file format
  public boolean isTiffFormat() { return tiffHeader.isTiffFormat(); }

  // Method to say if Big TIFF file format
  public boolean isBigTIFF() { return (tiffHeader.getVersionNumber() == 43); }

  // Method to get the Directory number
  public int getTiffDirectoryNumber() {  return tiffDirectoryNumber; }
  
  // Method to retrieve the sub directory
  public TiffDirectory getImageDirectory() { return imageDirectory; }
  
  
  // Method to get SubFileType
  public String getNewSubFileType() { return imageDirectory.getNewSubFileType(); }

  // Methods to return the image parameters from imageDirectory
  public long getImageWidth() { return imageDirectory.getImageWidth(); }

  // Method to find ImageLength
  public long getImageLength() { return imageDirectory.getImageLength(); }
  
  // Method to find SamplesPerPixel
  public long getSamplesPerPixel() { return imageDirectory.getSamplesPerPixel(); }
  
  // Method to find BitsPerSample
  public long getBitsPerSample() { return imageDirectory.getBitsPerSample(); }
  
  // Method to determine image data type
  public int getImageDataType() { return imageDirectory.getImageDataType(); }
    
  // Method to return a Tiff Tag entry from the imageDirectory
  public TiffTag getTiffTag(String name) { return imageDirectory.getTiffTag(name); }
  
  // Method to set a Tiff Tag entry
  public TiffTag setTiffTag(String name, String input) { return imageDirectory.setTiffTag(name, input); }

  // Method to set a Tiff Tag entry
  public TiffTag setTiffTag(String name, long input) { return imageDirectory.setTiffTag(name, input); }
  
  // Method to set a Tiff Tag entry
  public TiffTag setTiffTag(String name, long[] input) { return imageDirectory.setTiffTag(name, input); }
  
  // Method to set a Tiff Tag entry
  public TiffTag setTiffTag(String name, double input) { return imageDirectory.setTiffTag(name, input); }
  
  // Method to set a Tiff Tag entry
  public TiffTag setTiffTag(String name, double[] input) { return imageDirectory.setTiffTag(name, input); }
  
  // Method to delete a Tag from its name
  public void deleteTag(String name) { imageDirectory.deleteTag(name); }
  
  // Method to delete a TiffTag from its name
  public void deleteEveryTag(String name)
    {
    for (int n=0; n < tiffDirectoryList.size(); n++)
      {
      TiffDirectory td = tiffDirectoryList.get(n);
      td.deleteTag(name);
      }
    }
  
  // Private methods
  // Only using the first bitsPerSample entry.  Might need others.
  private void checkBigTiffRequired()
    {
    long comparison = 65536;
    comparison = comparison*comparison/2;
    long pixels = getTiffTag("ImageWidth").getNumber();
    long scanlines = getTiffTag("ImageLength").getNumber();
    long samples = getTiffTag("SamplesPerPixel").getNumber();
    long bits = getTiffTag("BitsPerSample").getArray()[0];
    long stripLength = pixels*scanlines*samples*(bits/8);
    tiffHeader.setBigTIFF(stripLength >= comparison);
    setTiffTag("RowsPerStrip", (long) scanlines);
    setTiffTag("StripByteCounts", stripLength);
    setBitsPerSample(bits);
    TiffTag tag = getTiffTag("StripOffsets");
    if (tag == null) setTiffTag("StripOffsets", 0);
    long length = getLength();
    setTiffTag("StripOffsets", length);
    }
  
  
  
  
  // Method to retrieve the TIFF header
  protected TiffHeader getTiffHeader() { return tiffHeader; }
    
  protected String getImageDataTypeString()
    {
    int type = getImageDataType();
    if (ImageDataType.isInteger(type) && ImageDataType.isSigned(type) == false) return new String("UNSIGNEDINTEGER");
    if (ImageDataType.isInteger(type) && ImageDataType.isSigned(type)) return new String("SIGNEDINTEGER");
    if (ImageDataType.isFloating(type)) return new String("REALFLOATING");
    if (ImageDataType.isComplex(type)) return new String("COMPLEXFLOATING");
    return "UNKNOWN";
    }
  
  // Methods to set basic image information
  public void setImageWidth(long pixels) { setTiffTag("ImageWidth", pixels); }
  public void setImageLength(long scanlines) { setTiffTag("ImageLength", scanlines); }
  public void setSamplesPerPixel(long samples) { setTiffTag("SamplesPerPixel", samples);  /*setTiffTag("BitsPerSample", getBitsPerSample());*/ }
  public void setBitsPerSample(long bits)
    {
    int nSamples = (int) getTiffTag("SamplesPerPixel").getNumber();
    long[] b = new long[nSamples];
    for (int n=0; n < nSamples; n++) b[n] = bits;
    setTiffTag("BitsPerSample", b);
    }
  public void setImageDataType(int dataType)
    {
    boolean floating = ImageDataType.isFloating(dataType);
    boolean signed = ImageDataType.isFloating(dataType);
    boolean complex = ImageDataType.isComplex(dataType);
    int sampleFormat = TiffTag.SF_INTEGER;
    if (floating == true) sampleFormat = TiffTag.SF_IEEEFLOATING;
    else if (signed == true) sampleFormat = TiffTag.SF_SINTEGER;
    setTiffTag("SampleFormat", sampleFormat);
    if (complex == false) return;
    int samplesPerPixel = (int) getSamplesPerPixel();
    TiffTag es = getTiffTag("ExtraSamples");
    if (es != null)
      {
      long[] extraSamples = es.getArray();
      if (extraSamples != null) samplesPerPixel -= es.getArray().length;
      deleteTag("ExtraSamples");
      }
    long[] extraSamples = new long[samplesPerPixel];
    for (int n=0; n < samplesPerPixel; n++) extraSamples[n] = 0;
    setTiffTag("ExtraSamples", extraSamples);
    setTiffTag("SamplesPerPixel", 2*samplesPerPixel);
    }
  public void setStripByteCounts()
    {
    long pixels = imageDirectory.getImageWidth();
    long scanlines = imageDirectory.getImageLength();
    long samples = imageDirectory.getSamplesPerPixel();
    long bits = imageDirectory.getBitsPerSample();
    long length = pixels*scanlines*samples*(bits/8);
    setTiffTag("StripByteCounts", length);
    }
  
  // Method to retrieve the TIFF directory
  protected TiffDirectory getTiffDirectory() { return tiffDirectory; }
  
  // Method to retrieve the EXIF directory
  protected TiffDirectory getExifDirectory() { return tiffDirectory.getExifDirectory(); }
  
  // Method to retrieve the IPTC record
  protected IPTCRecord getIPTCRecord() { return tiffDirectory.getIPTCRecord(); }
  
  // Method to print out the metadata
  public ArrayList<String> print(String name)
    {
    String banner = "******************************************";
    printout.add(banner);
    printout.add("*    "+name);
    printout.add(banner);
    if (tiffHeader != null) tiffHeader.print(printout);
    for (int n=0; n < tiffDirectoryList.size(); n++)
      {
      TiffDirectory td = tiffDirectoryList.get(n);
      td.print(printout, "IFD "+n);
      }
    return printout;
    }

  
  }



