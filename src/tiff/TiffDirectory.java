/*
 * TiffDirectory.java
 * version 1.0
 * 14 July 2022
 * Copyright 2022 Keith T. Knox
 * GNU General Public License v3.0
 *
 * TIFF Directory of a TIFF image file.
 *
 */


package tiff;

import java.io.RandomAccessFile;
import java.util.*;

/**
 * 
 * @author Keith T. Knox
 * @version 1.0
 *
 */

public class TiffDirectory
  {
  // Fields
  private HashMap<Integer,TiffEntry> directoryEntriesMap;
  private long nextDirectoryOffset;
  private TiffHeader tiffHeader;
  private TiffDirectory exifDirectory;
  private TiffDirectory gpsDirectory;
  private TiffDirectory interDirectory;
  private ArrayList<TiffDirectory> subDirectories = new ArrayList<TiffDirectory>();
  private IPTCRecord iptcRecord;
  private boolean flagDNG = false;

  
  // First Constructor -- open a TiffDirectory from a file
  public TiffDirectory(RandomAccessFile raf, TiffHeader h, long directoryOffset)
    {
    // Information from the TIFF header
    tiffHeader = h;
    exifDirectory = null;
    gpsDirectory = null;
    interDirectory = null;
    iptcRecord = null;
    int versionNumber = tiffHeader.getVersionNumber();
    int numberLength = 2, entryLength = 12, offsetBytesize = 4;
    if (versionNumber == 43) { numberLength = 8; entryLength = 20; offsetBytesize = 8; }

    // Get the number of entries in the directory IFD
    byte[] buffer = new byte[8];
    tiffHeader.readBuffer(raf, directoryOffset, buffer);
    long numberDirectoryEntries = tiffHeader.getLong(buffer, 0, numberLength);

    // Read in the directory entries
    int position = 0;
    buffer = new byte[(int) (numberLength+entryLength*numberDirectoryEntries+offsetBytesize)];
    tiffHeader.readBuffer(raf, directoryOffset, buffer);
    position += numberLength;

    // Analyze each directory entry
    directoryEntriesMap = new HashMap<Integer,TiffEntry>();
    for (int n=0; n < numberDirectoryEntries; n++)
      {
      TiffEntry entry = new TiffEntry(raf, tiffHeader, buffer, position);
      long[] longValues = entry.getLongValues();
      directoryEntriesMap.put(entry.getTiffTagCode(), entry);
      if (entry.getTiffTagCode() == TiffTag.EXIFIFD) exifDirectory = new TiffDirectory(raf, h, entry.getTiffOffset());
      if (entry.getTiffTagCode() == TiffTag.IPTC) iptcRecord = new IPTCRecord(entry.getTiffValue().getValuesByteBuffer());
      if (entry.getTiffTagCode() == TiffTag.GPSIFD)
        {
        gpsDirectory = new TiffDirectory(raf, h, entry.getTiffOffset());
        }
      if (entry.getTiffTagCode() == TiffTag.INTEROPERABILITYIFD)
        {
        interDirectory = new TiffDirectory(raf, h, entry.getTiffOffset());
        }
      if (entry.getTiffTagCode() == TiffTag.SUBIFDS) for (int m=0; m < longValues.length; m++) subDirectories.add(new TiffDirectory(raf, h, longValues[m]));
      position += entryLength;
      }
    nextDirectoryOffset = tiffHeader.getLong(buffer, position, offsetBytesize);
    }

  // Second Constructor -- open a TIFF directory from a buffer
  public TiffDirectory(TiffHeader h, byte[] buffer, int bufferOffset)
    {
    // Information from the TIFF header
    tiffHeader = h;
    exifDirectory = null;
    iptcRecord = null;
    int versionNumber = tiffHeader.getVersionNumber();
    int numberLength = 2, entryLength = 12, offsetBytesize = 4, countLength = 4;
    if (versionNumber == 43) { numberLength = 8; entryLength = 20; offsetBytesize = 8; countLength = 8; }

    // Get the number of entries in the directory IFD
    long numberDirectoryEntries = tiffHeader.getLong(buffer, bufferOffset, numberLength);
    int position = bufferOffset+numberLength;

    // Analyze each directory entry
    directoryEntriesMap = new HashMap<Integer,TiffEntry>();
    for (int n=0; n < numberDirectoryEntries; n++)
      {
      TiffEntry entry = new TiffEntry(tiffHeader, buffer, position, 0);
      directoryEntriesMap.put(entry.getTiffTagCode(), entry);
      long[] longValues = entry.getLongValues();
      if (entry.getTiffTagCode() == TiffTag.EXIFIFD) exifDirectory = new TiffDirectory(h, buffer, (int) entry.getTiffOffset());
      if (entry.getTiffTagCode() == TiffTag.IPTC) iptcRecord = new IPTCRecord(entry.getTiffValue().getValuesByteBuffer());
      if (entry.getTiffTagCode() == TiffTag.GPSIFD)
        {
        gpsDirectory = new TiffDirectory(h, buffer, (int) entry.getTiffOffset());
        }
      if (entry.getTiffTagCode() == TiffTag.INTEROPERABILITYIFD)
        {
        interDirectory = new TiffDirectory(h, buffer, (int) entry.getTiffOffset());
        }
     if (entry.getTiffTagCode() == TiffTag.SUBIFDS) for (int m=0; m < longValues.length; m++) subDirectories.add(new TiffDirectory(h, buffer, (int) longValues[m]));
      position += entryLength;
      }
    nextDirectoryOffset = tiffHeader.getLong(buffer, position, offsetBytesize);
    }

  // Third Constructor
  public TiffDirectory(TiffHeader h)
    {
    // Establish an empty directory
    tiffHeader = h;
    directoryEntriesMap = new HashMap<Integer,TiffEntry>();
    nextDirectoryOffset = 0;
    exifDirectory = null;
    gpsDirectory = null;
    iptcRecord = null;
    }

  // Fourth Constructor
  public TiffDirectory(TiffHandle h)
    {
    // Establish an empty directory
    tiffHeader = h.getTiffHeader();
    directoryEntriesMap = new HashMap<Integer,TiffEntry>();
    nextDirectoryOffset = 0;
    exifDirectory = null;
    gpsDirectory = null;
    iptcRecord = null;
    }

  /*
   * Methods to insert directories
   */
  
  // Method to insert a sub directory
  public void insertSubDirectory(TiffDirectory subDirectory)
    {
    subDirectories.add(subDirectory);
    int tiffTag = TiffTag.getTiffTagCode("SubIFDS");
    TiffEntry subEntry = findTiffEntry(tiffTag);
    if (subEntry == null) subEntry = new TiffEntry(tiffHeader, "SubIFDS");
    subEntry.setTiffCount(subDirectories.size());
    addTiffEntry(subEntry);
    }

  // Insert an EXIF directory
  public void insertExifDirectory(TiffDirectory exifDirectory)
    {
    this.exifDirectory = exifDirectory;
    int tiffTag = TiffTag.getTiffTagCode("EXIFIFD");
    TiffEntry exifEntry = findTiffEntry(tiffTag);
    if (exifEntry == null) exifEntry = new TiffEntry(tiffHeader, "EXIFIFD");
    exifEntry.setTiffCount(1);
    addTiffEntry(exifEntry);
    }

  // Insert a GPS directory
  public void insertGpsDirectory(TiffDirectory gpsDirectory)
    {
    this.gpsDirectory = gpsDirectory;
    int tiffTag = TiffTag.getTiffTagCode("GPSIFD");
    TiffEntry gpsEntry = findTiffEntry(tiffTag);
    if (gpsEntry == null) gpsEntry = new TiffEntry(tiffHeader, "GPSIFD");
    gpsEntry.setTiffCount(1);
    addTiffEntry(gpsEntry);
    }

  // Insert an Interoperability directory
  public void insertInterDirectory(TiffDirectory interDirectory)
    {
    this.interDirectory = interDirectory;
    int tiffTag = TiffTag.getTiffTagCode("INTEROPERABILITYIFD");
    TiffEntry intEntry = findTiffEntry(tiffTag);
    if (intEntry == null) intEntry = new TiffEntry(tiffHeader, "INTEROPERABILITYIFD");
    intEntry.setTiffCount(1);
    addTiffEntry(intEntry);
    }

  // Insert an IPTC record
  public void insertIPTCRecord(IPTCRecord iptcRecord)
    {
    this.iptcRecord = iptcRecord;
    int tiffTag = TiffTag.getTiffTagCode("IPTC");
    TiffEntry iptcEntry = findTiffEntry(tiffTag);
    if (iptcEntry == null) iptcEntry = new TiffEntry(tiffHeader, "IPTC");
    addTiffEntry(iptcEntry);
    }

  // Insert an image into the directory
  public void insertImage(String newSubFileType, int pixels, int scanlines, int samples, int bits, int type)
    {
    setTiffTag("ImageWidth", pixels);
    setTiffTag("ImageLength", scanlines);
    setTiffTag("SamplesPerPixel", samples);
    setTiffTag("BitsPerSample", bits);
    setTiffTag("Compression", (long) TiffTag.NONE);
    setTiffTag("NewSubfileType", TiffTag.getTiffNewSubFileType(newSubFileType));
    setTiffTag("PlanarConfiguration", (long) TiffTag.CHUNKY);
    int sampleFormat = TiffTag.SF_INTEGER;
    if (ImageDataType.isFloating(type) == true) sampleFormat = TiffTag.SF_IEEEFLOATING;
    else if (ImageDataType.isSigned(type) == true) sampleFormat = TiffTag.SF_SINTEGER;
    if (sampleFormat != TiffTag.SF_INTEGER) setTiffTag("SampleFormat", sampleFormat);
    if (ImageDataType.isComplex(type) == true) { setTiffTag("ExtraSamples", samples); setTiffTag("SamplesPerPixel", 2*samples); }
    }
  
  // Method to set DNG flag
  public void setDNGFlag(boolean flag) { flagDNG = flag; }
  
  // Method to make an EXIF directory, if necessary
  private void makeExifDirectory(TiffHeader tiffHeader)
    {
    exifDirectory = new TiffDirectory(tiffHeader);
    new TiffTag(this, "ExifIFD");
    }
  
  // Method to make an IPTC Record, if necessary
  private IPTCRecord makeIPTCRecord() { return new IPTCRecord(); }
  
  public ArrayList<TiffDirectory> getSubDirectoryList() { return subDirectories; }

  public HashMap<Integer,TiffEntry> getDirectoryEntriesMap() { return directoryEntriesMap; }
  
  // Method to find Tiff Tag
  public TiffEntry findTiffEntry(int tiffTag) { return directoryEntriesMap.get(tiffTag); }

  // Method to remove Tiff Tag
  public void removeTiffEntry(int tiffTag) { directoryEntriesMap.remove(tiffTag); }

  // Method to add a Tiff Tag
  public void addTiffEntry(TiffEntry tiffEntry) { directoryEntriesMap.put(tiffEntry.getTiffTagCode(), tiffEntry); }
  
  // Method to get SubFileType
  public String getNewSubFileType()
    {
    int value = (int) getImageNumber("NewSubFileType");
    if (value == -1) return "Full";  // This means, no tag entry, default "Full"
    return TiffTag.getTiffSubFileName(value);
    }

  // Method to find the ImageWidth
  public long getImageWidth() { return getImageNumber("ImageWidth"); }
  
  // Method to find ImageLength
  public long getImageLength() { return getImageNumber("ImageLength"); }
  
  // Method to find SamplesPerPixel
  public long getSamplesPerPixel() { return getImageNumber("SamplesPerPixel"); }
  
  // Method to find BitsPerSample
  public long getBitsPerSample() { return getImageNumber("BitsPerSample"); }
  
  // Method to determine image data type
  public int getImageDataType()
    {
    int sampleFormat = (int) getImageNumber("SampleFormat");
    if (sampleFormat == 0) sampleFormat = TiffTag.SF_INTEGER;
    boolean floating = (sampleFormat == TiffTag.SF_IEEEFLOATING);
    boolean signed = ((sampleFormat == TiffTag.SF_SINTEGER) || (sampleFormat == TiffTag.SF_IEEEFLOATING));
    int samplesPerPixel = (int) getSamplesPerPixel();
    long[] extraSamples = getImageArray("ExtraSamples");
    boolean complex = false;
    if (extraSamples != null) complex = ((samplesPerPixel == 2*extraSamples.length) && (extraSamples[0] == 0));
    return ImageDataType.makeDataType(floating, signed, complex);
    }
  
  private long getImageNumber(String name)
    {
    int tagCode = TiffTag.getTiffTagCode(name);
    TiffEntry entry = findTiffEntry(tagCode);
    if (entry != null)
      {
      long[] b = entry.getLongValues();
      if (b != null) return b[0];
      }
    return -1;
    }
  
  private long[] getImageArray(String name)
    {
    int tagCode = TiffTag.getTiffTagCode(name);
    TiffEntry entry = findTiffEntry(tagCode);
    if (entry != null) return entry.getLongValues();
    return null;
    }
  
  
  // Method to return a Tiff Tag entry from this directory
  public TiffTag getTiffTag(String name)
    {
    // Find the tagCode for this entry name
    int tiffTagCode = TiffTag.getTagCode(name);
    
    // Look in this directory for this TIFF tag code
    if (tiffTagCode > -1)
      {
      // Found it in this directory
      TiffEntry entry = findTiffEntry(tiffTagCode);
      if (entry != null) return new TiffTag(this, entry);
      
      // Look instead in any exifDirectory
      if (exifDirectory != null)
        {
        entry = exifDirectory.findTiffEntry(tiffTagCode);
        if (entry != null) return new TiffTag(exifDirectory, entry);
        }
      
      // Try instead in any subDirectories
      for (int n=0; n < subDirectories.size(); n++)
        {
        TiffDirectory sd = subDirectories.get(n);
        entry = sd.findTiffEntry(tiffTagCode);
        if (entry != null) return new TiffTag(sd, entry);
        }
      return null;
      }
    
    // See if this is an IPTC tag
    IPTCDataSet iptcDataSet = findIPTCTagName(name);
    if (iptcDataSet != null) return new TiffTag(getIPTCRecord(), iptcDataSet);
    return null;
    }
  
  // Method to set a Tiff Tag entry
  public TiffTag setTiffTag(String name, String input)
    {
    // See if there is an existing tag for this name
    TiffTag tag = getTiffTag(name);
    if (tag == null) tag = makeTiffTag(name);
    
    // Before we set the tag, see if it is a special case
    // compression, sampleformat, photometric, resunits, planar, subtype
    if (name.equalsIgnoreCase("Compression")) tag.setNumber(TiffTag.getTiffCompressionTypes(input));
    else if (name.equalsIgnoreCase("PhotometricInterpretation")) tag.setNumber(TiffTag.getTiffPhotometricInterpretation(input));
    else if (name.equalsIgnoreCase("ResolutionUnit")) tag.setNumber(TiffTag.getTiffResolutionUnits(input));
    else if (name.equalsIgnoreCase("PlanarConfiguration")) tag.setNumber(TiffTag.getTiffPlanarConfiguration(input));
    else if (name.equalsIgnoreCase("NewSubFileType")) tag.setNumber(TiffTag.getTiffNewSubFileType(input));
    else if (name.equalsIgnoreCase("SampleFormat")) tag.setNumber(TiffTag.getTiffSampleFormatTypes(input));
    else tag.setString(input);
    return tag;
    }
  
  // Method to set a Tiff Tag entry
  public TiffTag setTiffTag(String name, long input)
    {
    // See if there is an existing tag for this name
    TiffTag tag = getTiffTag(name);
    if (tag == null) tag = makeTiffTag(name);
    tag.setNumber(input);
    return tag;
    }
  
  // Method to set a Tiff Tag entry
  public TiffTag setTiffTag(String name, long[] input)
    {
    // See if there is an existing tag for this name
    TiffTag tag = getTiffTag(name);
    if (tag == null) tag = makeTiffTag(name);
    tag.setArray(input);
    return tag;
    }
  
  // Method to set a Tiff Tag entry
  public TiffTag setTiffTag(String name, double input)
    {
    // See if there is an existing tag for this name
    TiffTag tag = getTiffTag(name);
    if (tag == null) tag = makeTiffTag(name);
    tag.setNumber(input);
    return tag;
    }
  
  // Method to set a Tiff Tag entry
  public TiffTag setTiffTag(String name, double[] input)
    {
    // See if there is an existing tag for this name
    TiffTag tag = getTiffTag(name);
    if (tag == null) tag = makeTiffTag(name);
    tag.setArray(input);
    return tag;
    }
  

  // Method to create a TiffTag from its name
  private TiffTag makeTiffTag(String name)
    {
    String location = TiffTag.getTagLocation(name);
    if (location.equals("TIFF") || location.equals("DNG")) return new TiffTag(this, name);
    if (location.equals("EXIF"))
      {
	    if (flagDNG) return new TiffTag(this, name);
      if (exifDirectory == null) makeExifDirectory(tiffHeader);
      return new TiffTag(exifDirectory, name);
      }
    if (location.equals("IPTC"))
      {
      if (iptcRecord == null) makeIPTCRecord();
      return new TiffTag(iptcRecord, name);
      }
    return null;
    }
  

  // Method to delete a tag from its name
  public void deleteTag(String name)
    {
    int tagCode = TiffTag.getTagCode(name);
    String location = TiffTag.getTagLocation(name);
    if (location.equals("TIFF") || location.equals("DNG")) removeTiffEntry(tagCode);
    else if (location.equals("EXIF") || location.equals("DNG"))
      {
      TiffDirectory exifDirectory = getExifDirectory();
      if (exifDirectory != null) exifDirectory.removeTiffEntry(tagCode);
      }
    else if (location.equals("IPTC"))
      {
      IPTCRecord iptcRecord = getIPTCRecord();
      if (iptcRecord != null) iptcRecord.removeIPTCDataSet(name);
      }
    }
  
  
  
  // Method to find IPTC Record Tag Name
  public IPTCDataSet findIPTCTagName(String name)
    {
    if (iptcRecord == null) return null;
    IPTCDataSet dataSet = iptcRecord.findIPTCDataSetName(name);
    return dataSet;
    }

  // Method to form the IFD buffer for this directory
  protected void putBytes(byte[] buffer, long directoryOffset, long length)
    {
    // Figure out the total length
    int nEntries = directoryEntriesMap.size();
    int versionNumber = tiffHeader.getVersionNumber();
    int numberLength = 2, entryLength = 12, offsetBytesize = 4, countLength = 4;
    if (versionNumber == 43) { numberLength = 8; entryLength = 20; offsetBytesize = 8; countLength = 8; }
    long directoryPosition = directoryOffset;
    long valuesPosition = directoryOffset+numberLength+nEntries*entryLength+offsetBytesize;
    
    // Write out the entries
    tiffHeader.putLong(nEntries, buffer, (int) directoryPosition, numberLength);
    directoryPosition += numberLength;
    Object[] entriesArray = directoryEntriesMap.keySet().toArray();
    Arrays.sort(entriesArray);
    for (int n=0; n < nEntries; n++)
      {
      TiffEntry entry = directoryEntriesMap.get(entriesArray[n]);
      switch (entry.getTiffTagCode())
        {
        case TiffTag.IPTC:
          if (iptcRecord != null)
            {
            entry.putBytes(buffer, directoryPosition, valuesPosition);
            iptcRecord.putBytes(buffer, valuesPosition);
            valuesPosition += iptcRecord.getLength();
            }
          break;
        case TiffTag.EXIFIFD:
          if (exifDirectory != null)
            {
            entry.putBytes(buffer, directoryPosition, valuesPosition);
            exifDirectory.putBytes(buffer, valuesPosition, 0L);
            valuesPosition += exifDirectory.getLength();
            }
          break;
        case TiffTag.GPSIFD:
        if (gpsDirectory != null)
          {
          entry.putBytes(buffer, directoryPosition, valuesPosition);
          gpsDirectory.putBytes(buffer, valuesPosition, 0L);
          valuesPosition += gpsDirectory.getLength();
          }
        break;
        case TiffTag.INTEROPERABILITYIFD:
        if (interDirectory != null)
          {
          entry.putBytes(buffer, directoryPosition, valuesPosition);
          interDirectory.putBytes(buffer, valuesPosition, 0L);
          valuesPosition += interDirectory.getLength();
          }
        break;
        case TiffTag.SUBIFDS:
          if (subDirectories.size() > 0)
            {
            long[] a = new long[subDirectories.size()];
            long[] b = new long[subDirectories.size()];
            for (int m=0; m < subDirectories.size(); m++)
              {
              TiffDirectory sub = subDirectories.get(m);
              long dirLength = sub.getLength();
              a[m] = dirLength;
              }
            b[0] = valuesPosition;
            if (subDirectories.size() > 1) b[0] += subDirectories.size();
            for (int m=1; m < subDirectories.size(); m++) b[m] = b[m-1]+a[m-1];
            entry.putLongValues(b);
            entry.putBytes(buffer, directoryPosition, valuesPosition);
            valuesPosition += (entry.fitsWithin(entry.getTiffType(), entry.getTiffCount())) ? 0 : entry.getValueLength();
            for (int m=0; m < subDirectories.size(); m++)
              {
              TiffDirectory sub = subDirectories.get(m);
              long dirLength = sub.getLength();
              long offset = ((n+1) < subDirectories.size()) ? dirLength : 0;
              sub.putBytes(buffer, valuesPosition, offset);
              valuesPosition += sub.getLength();
              }
            entry.putLongValues(b);
            entry.putBytes(buffer, directoryPosition, valuesPosition);
            }
        default:
          entry.putBytes(buffer, directoryPosition, valuesPosition);
          valuesPosition += (entry.fitsWithin(entry.getTiffType(), entry.getTiffCount())) ? 0 : entry.getValueLength();
          break;
        }
      directoryPosition += entryLength;
      }
    
    // Put out the nextDirectoryOffset
    long offset = 0L;
    if (length > 0L) offset = directoryOffset+length;
    tiffHeader.putLong(offset, buffer, (int) directoryPosition, offsetBytesize);
    }
  
  // in getting length for subIFD, offset is taken care of
  // for exifIFD, the offset is sufficient
  // for subIFD, there is an additional offset for each subdirectory
  
  // Method to determine how many bytes are used by this directory
  protected long getLength()
    {
    long result = 0;
    int numberDirectoryEntries = directoryEntriesMap.size();
    int versionNumber = tiffHeader.getVersionNumber();
    int numberLength = 2, entryLength = 12, offsetBytesize = 4, countLength = 4;
    if (versionNumber == 43) { numberLength = 8; entryLength = 20; offsetBytesize = 8; countLength = 8; }
    result = numberLength+numberDirectoryEntries*entryLength+offsetBytesize;
    Object[] entriesArray = directoryEntriesMap.keySet().toArray();
    Arrays.sort(entriesArray);
    for (int n=0; n < numberDirectoryEntries; n++)
      {
      TiffEntry entry = directoryEntriesMap.get(entriesArray[n]);
      switch (entry.getTiffTagCode())
        {
        case TiffTag.EXIFIFD:
          if (exifDirectory != null) result += exifDirectory.getLength();
          break;
        case TiffTag.GPSIFD:
          if (gpsDirectory != null) result += gpsDirectory.getLength();
          break;
        case TiffTag.INTEROPERABILITYIFD:
        if (interDirectory != null) result += interDirectory.getLength();
        break;
        case TiffTag.IPTC:
          if (iptcRecord != null) result += iptcRecord.getLength();
          break;
        case TiffTag.SUBIFDS:
          for (int m=0; m < subDirectories.size(); m++) result += subDirectories.get(m).getLength();
          if (subDirectories.size() > 1) result += offsetBytesize*subDirectories.size();
          break;
        default:
          result += (entry.fitsWithin(entry.getTiffType(), entry.getTiffCount())) ? 0 : entry.getValueLength();
          break;
        }
      }
    if ((result % 2) == 1) result++;
    return result;
    }

  // Method to retrieve disk offset of the next TIFF directory
  public long getNextDirectoryOffset() { return nextDirectoryOffset; }
  
  // Method to retrieve the EXIF directory
  public TiffDirectory getExifDirectory() { return exifDirectory; }
  
  // Method to retrieve the IPTC record
  public IPTCRecord getIPTCRecord() { return iptcRecord;  }
  
  // Method to say if Big TIFF file format
  public boolean isBigTIFF() { return (tiffHeader.getVersionNumber() == 43); }
  
    
 // Method to print out the metadata
    protected void print(ArrayList<String> printout, String title)
      {
      String banner = "******************************************";
      if (printout.size() == 0) printout.add(banner);
      else if (printout.get(printout.size()-1).equals(banner) == false) printout.add(banner);
      printout.add("*    "+title);
      printout.add(banner);
      int numberDirectoryEntries = directoryEntriesMap.size();
      printout.add("numberDirectoryEntries: "+numberDirectoryEntries);
      Object[] entriesArray = directoryEntriesMap.keySet().toArray();
      Arrays.sort(entriesArray);
      for (int n=0; n < numberDirectoryEntries; n++)
        {
        TiffEntry entry = directoryEntriesMap.get(entriesArray[n]);
        if (entry == null) printout.add("Entry: "+entry);
        else
          {
          if (title.startsWith("IFD") || title.startsWith("SubIFD")) entry.print(printout, "Entry: "+n);
          else if (title.startsWith("Exif")) entry.print(printout, "Entry: "+n, "Exif");
          else if (title.startsWith("GPS")) entry.print(printout, "Entry: "+n, "GPS");
          else if (title.startsWith("Interoperability")) entry.print(printout, "Entry: "+n, "Interoperability");
          if (entry.getTiffTagCode() == TiffTag.EXIFIFD && exifDirectory != null) exifDirectory.print(printout, "Exif IFD");
          if (entry.getTiffTagCode() == TiffTag.GPSIFD && gpsDirectory != null) gpsDirectory.print(printout, "GPS IFD");
          if (entry.getTiffTagCode() == TiffTag.INTEROPERABILITYIFD && interDirectory != null) interDirectory.print(printout, "Interoperability IFD");
          if (entry.getTiffTagCode() == TiffTag.IPTC && iptcRecord != null) iptcRecord.print(printout, "IPTC Record");
          if (entry.getTiffTagCode() == TiffTag.SUBIFDS && subDirectories != null) for (int m=0; m < subDirectories.size(); m++) subDirectories.get(m).print(printout, "SubIFD "+m);
          }
        }
      printout.add("nextDirectoryOffset: "+nextDirectoryOffset);
      printout.add(banner);
      }


    // Method to retrieve TiffHeader
    public TiffHeader getTiffHeader() { return tiffHeader; }
    
  }



