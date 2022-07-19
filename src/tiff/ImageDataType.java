/*
 * ImageDataType.java
 * version 1.0
 * 14 July 2022
 * Copyright 2022 Keith T. Knox
 * GNU General Public License v3.0
 */

package tiff;

/**
 * 
 * @author Keith T. Knox
 * @version 1.0
 *
 */

public class ImageDataType
  {
  // Fields
  private static final int UNSIGNEDINTEGER = 0;
  private static final int SIGNEDINTEGER = 1;
  private static final int REALFLOATING = 2;
  private static final int COMPLEXFLOATING = 3;
  
  // Method to create a dataType parameter
  static public int makeDataType(boolean floating, boolean signed, boolean complex)
    {
    if (floating == false && signed == false) return UNSIGNEDINTEGER;
    if (floating == false && signed == true) return SIGNEDINTEGER;
    if (floating == true && complex == false) return REALFLOATING;
    if (floating == true && complex == true) return COMPLEXFLOATING;
    return UNSIGNEDINTEGER;
    }
  
  // Method to create a Tiff SampleFormat parameter
  static public int makeSampleFormat(int type)
    {
    if (type == UNSIGNEDINTEGER) return TiffTag.getTiffSampleFormatTypes("SF_INTEGER");
    if (type == SIGNEDINTEGER) return TiffTag.getTiffSampleFormatTypes("SF_SINTEGER");
    if (type == REALFLOATING) return TiffTag.getTiffSampleFormatTypes("SF_IEEEFLOATING");
    if (type == COMPLEXFLOATING) return TiffTag.getTiffSampleFormatTypes("SF_IEEEFLOATING");
    return TiffTag.getTiffSampleFormatTypes("SF_INTEGER");
    }
  
  // Methods to determine if is integer or floating, signed or complex
  static public boolean isInteger(int dataType) { return (dataType == UNSIGNEDINTEGER || dataType == SIGNEDINTEGER); }
  static public boolean isFloating(int dataType) { return (dataType == REALFLOATING || dataType == COMPLEXFLOATING); }
  static public boolean isSigned(int dataType) { return (dataType == SIGNEDINTEGER || dataType == REALFLOATING || dataType == COMPLEXFLOATING); }
  static public boolean isComplex(int dataType) { return (dataType == COMPLEXFLOATING); }
  }

