/*
 * IPTCDataSet.java
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

public class IPTCDataSet
  {
  // Fields
  IPTCTag tag;
  String value;
  
  // First Constructor
  public IPTCDataSet(byte[] buffer, int position)
    {
    tag = new IPTCTag(buffer, position);
    if (tag.isValid() == false) return;
    byte[] data = new byte[tag.getDataLength()];
    for (int n=0; n < data.length; n++) data[n] = buffer[n+position+tag.getTagLength()];
    value = new String(data);
    }

  // Second Constructor
  public IPTCDataSet(String name)
    {
    tag = new IPTCTag(2, getIPTCTagID(name));
    value = null;
    }

  // Method to return if valid data set
  public boolean isValid() { return tag.isValid(); }
  
  // Method to return Tag
  public IPTCTag getTag() { return tag; }
  
  // Method to return data set length
  protected int getLength() { return tag.getTagLength()+tag.getDataLength(); }

  // Method to return data set lengths
  protected int getTagLength() { return tag.getTagLength(); }
  protected int getDataLength() { return tag.getDataLength(); }

  // Method to write data set into buffer
  protected void putBytes(byte[] buffer, long recordPosition)
    {
    int position = (int) recordPosition;
    tag.putBytes(buffer, position);
    position += tag.getTagLength();
    byte[] b = value.getBytes();
    for (int n=0; n < b.length; n++) buffer[n+position] = b[n];
    }

  // Method to return data set value
  public String getValue() { return value; }

  // Method to set data set value
  public void setValue(String value) { this.value = new String(value); }

//Method to print out the metadata
  protected void print(ArrayList<String> printout)
   {
   if (tag == null) printout.add("tag: "+tag);
   else tag.print(printout);
   printout.add("value: ("+value+")");
   }

 // Method to get TIFF code from tag name
 private int getIPTCTagID(String name)
   {
   if (name.equalsIgnoreCase("RecordVersion")) return 0;
   if (name.equalsIgnoreCase("ObjectName")) return 5;
   if (name.equalsIgnoreCase("EditStatus")) return 7;
   if (name.equalsIgnoreCase("EditorialUpdate")) return 8;
   if (name.equalsIgnoreCase("Urgency")) return 10;
   if (name.equalsIgnoreCase("Category")) return 15;
   if (name.equalsIgnoreCase("SupplementalCategory")) return 20;
   if (name.equalsIgnoreCase("FixtureIdentifier")) return 22;
   if (name.equalsIgnoreCase("Keywords")) return 25;
   if (name.equalsIgnoreCase("ReleaseDate")) return 30;
   if (name.equalsIgnoreCase("ReleaseTime")) return 35;
   if (name.equalsIgnoreCase("ExpirationDate")) return 37;
   if (name.equalsIgnoreCase("ExpirationTime")) return 38;
   if (name.equalsIgnoreCase("SpecialInstructions")) return 40;
   if (name.equalsIgnoreCase("ActionAdvised")) return 42;
   if (name.equalsIgnoreCase("ReferenceService")) return 45;
   if (name.equalsIgnoreCase("ReferenceDate")) return 47;
   if (name.equalsIgnoreCase("ReferenceNumber")) return 50;
   if (name.equalsIgnoreCase("DateCreated")) return 55;
   if (name.equalsIgnoreCase("TimeCreated")) return 60;
   if (name.equalsIgnoreCase("DigitalCreationDate")) return 62;
   if (name.equalsIgnoreCase("DigitalCreationTime")) return 63;
   if (name.equalsIgnoreCase("OriginatingProgram")) return 65;
   if (name.equalsIgnoreCase("ProgramVersion")) return 70;
   if (name.equalsIgnoreCase("ObjectCycle")) return 75;
   if (name.equalsIgnoreCase("By-line")) return 80;
   if (name.equalsIgnoreCase("By-lineTitle")) return 85;
   if (name.equalsIgnoreCase("City")) return 90;
   if (name.equalsIgnoreCase("Sublocation")) return 92;
   if (name.equalsIgnoreCase("Province/State")) return 95;
   if (name.equalsIgnoreCase("CountryCode")) return 100;
   if (name.equalsIgnoreCase("Country")) return 101;
   if (name.equalsIgnoreCase("OriginalTransmission")) return 103;
   if (name.equalsIgnoreCase("Headline")) return 105;
   if (name.equalsIgnoreCase("Credit")) return 110;
   if (name.equalsIgnoreCase("Source")) return 115;
   if (name.equalsIgnoreCase("CopyrightNotice")) return 116;
   if (name.equalsIgnoreCase("Contact")) return 118;
   if (name.equalsIgnoreCase("Caption/Abstract")) return 120;
   if (name.equalsIgnoreCase("Writer/Editor")) return 122;
   if (name.equalsIgnoreCase("RasterizedCaption")) return 125;
   if (name.equalsIgnoreCase("ImageType")) return 130;
   if (name.equalsIgnoreCase("ImageOrientation")) return 131;
   if (name.equalsIgnoreCase("LanguageIdentifier")) return 135;
   if (name.equalsIgnoreCase("AudioType")) return 150;
   if (name.equalsIgnoreCase("AudioSamplingRate")) return 151;
   if (name.equalsIgnoreCase("AudioSamplingResolution")) return 152;
   if (name.equalsIgnoreCase("AudioDuration")) return 153;
   if (name.equalsIgnoreCase("AudioOutcue")) return 154;
   return -1;
   }

  }

