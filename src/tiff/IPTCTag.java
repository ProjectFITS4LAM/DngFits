/*
 * IPTCTag.java
 * version 1.0
 * 14 July 2022
 * Copyright 2022 Keith T. Knox
 * GNU General Public License v3.0
 */

package tiff;

import java.util.ArrayList;

/**
 * 
 * @author Keith T. Knox
 * @version 1.0
 *
 */

public class IPTCTag
  {
  // IPTC Tag Constants
  public final static int start = 28;
  
  // Fields
  int marker;
  int record;
  int ID;
  int length;
  
  // First Constructor
  public IPTCTag(byte[] buf, int position)
    {
    marker = record = ID = length = 0;
    if (position < buf.length) marker = getUnsignedByte(buf, position);
    if (position+1 < buf.length) record = getUnsignedByte(buf, position+1);
    if (record == 0) return;
    if (position+2 < buf.length) ID = getUnsignedByte(buf, position+2);
    if (position+4 < buf.length) length = 256*getUnsignedByte(buf, position+3)+getUnsignedByte(buf, position+4);
    }
  
  // Method to get unsigned byte from the incoming buffer
  private int getUnsignedByte(byte[] buf, int position)
    {
    int value = buf[position];
    if (value < 0) value += 256;
    return value;
    }
  
  // Second Constructor
  public IPTCTag(int record, int ID)
    {
    this.marker = this.record = this.ID = this.length = 0;
    this.marker = start;
    this.record = record;
    if (record == 0) return;
    this.ID = ID;
    this.length = 0;
    }
  
  // Method to say if valid
  public boolean isValid()
    {
    if (marker != start) return false;
    if (record == 0) return false;
    return true;
    }
  
  // Method to get ID
  public int getID() { return ID; }
  
  // Methods to get lengths
  protected int getTagLength() { return 5; }
  protected int getDataLength() { return length; }
  
  // Method to write tag into buffer
  protected void putBytes(byte[] buffer, long position)
    {
    buffer[(int) position] = (byte) marker;
    buffer[(int) (position+1)] = (byte) record;
    buffer[(int) (position+2)] = (byte) ID;
    buffer[(int) (position+3)] = (byte) (length/256);
    buffer[(int) (position+4)] = (byte) (length-(length/256));
    }
  
  public String getIPTCTagName()
    {
    switch (ID)
      {
      case   0: return "RecordVersion";
      case   5: return "ObjectName";
      case   7: return "EditStatus";
      case   8: return "EditorialUpdate";
      case  10: return "Urgency";
      case  15: return "Category";
      case  20: return "SupplementalCategory";
      case  22: return "FixtureIdentifier";
      case  25: return "Keywords";
      case  30: return "ReleaseDate";
      case  35: return "ReleaseTime";
      case  37: return "ExpirationDate";
      case  38: return "ExpirationTime";
      case  40: return "SpecialInstructions";
      case  42: return "ActionAdvised";
      case  45: return "ReferenceService";
      case  47: return "ReferenceDate";
      case  50: return "ReferenceNumber";
      case  55: return "DateCreated";
      case  60: return "TimeCreated";
      case  62: return "DigitalCreationDate";
      case  63: return "DigitalCreationTime";
      case  65: return "OriginatingProgram";
      case  70: return "ProgramVersion";
      case  75: return "ObjectCycle";
      case  80: return "By-line";
      case  85: return "By-lineTitle";
      case  90: return "City";
      case  92: return "Sublocation";
      case  95: return "Province/State";
      case 100: return "CountryCode";
      case 101: return "Country";
      case 103: return "OriginalTransmission";
      case 105: return "Headline";
      case 110: return "Credit";
      case 115: return "Source";
      case 116: return "CopyrightNotice";
      case 118: return "Contact";
      case 120: return "Caption/Abstract";
      case 122: return "Writer/Editor";
      case 125: return "RasterizedCaption";
      case 130: return "ImageType";
      case 131: return "ImageOrientation";
      case 135: return "LanguageIdentifier";
      case 150: return "AudioType";
      case 151: return "AudioSamplingRate";
      case 152: return "AudioSamplingResolution";
      case 153: return "AudioDuration";
      case 154: return "AudioOutcue";
      }
    return "UNKNOWN";
    }

//Method to print out the metadata
 public void print(ArrayList<String> printout)
   {
   printout.add("marker: "+marker);
   printout.add("record: "+record);
   printout.add("ID: ("+ID+") "+getIPTCTagName());
   printout.add("length: "+length);
   }

  }

