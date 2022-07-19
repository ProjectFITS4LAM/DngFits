/*
 * TiffTag.java
 * version 1.0
 * 14 July 2022
 * Copyright 2022 Keith T. Knox
 * GNU General Public License v3.0
 *
 * Definition of the TIFF tags
 * 
 */

package tiff;

/**
 * 
 * @author Keith T. Knox
 * @version 1.0
 *
 */

public class TiffTag
  {
  // Static Fields
  // GPS Tags
  protected final static int GPSVERSIONID = 0;
  protected final static int GPSLATITUDEREF = 1;
  protected final static int GPSLATITUDE = 2;
  protected final static int GPSLONGITUDEREF = 3;
  protected final static int GPSLONGITUDE = 4;
  protected final static int GPSALTITUDEREF = 5;
  protected final static int GPSALTITUDE= 6;
  protected final static int GPSTIMESTAMP = 7;
  protected final static int GPSSATELLITES = 8;
  protected final static int GPSSTATUS = 9;
  protected final static int GPSMEASUREMODE = 10;
  protected final static int GPSDOP = 11;
  protected final static int GPSSPEEDREF = 12;
  protected final static int GPSSPEED = 13;
  protected final static int GPSTRACKREF = 14;
  protected final static int GPSTRACK = 15;
  protected final static int GPSIMGDIRECTIONREF = 16;
  protected final static int GPSIMGDIRECTION = 17;
  protected final static int GPSMAPDATUM = 18;
  protected final static int GPSDESTLATITUDEREF = 19;
  protected final static int GPSDESTLATITUDE = 20;
  protected final static int GPSDESTLONGITUDEREF = 21;
  protected final static int GPSDESTLONGITUDE = 22;
  protected final static int GPSDESTBEARINGREF = 23;
  protected final static int GPSDESTBEARING = 24;
  protected final static int GPSDESTDISTANCEREF = 25;
  protected final static int GPSDESTDISTANCE = 26;
  protected final static int GPSPROCESSINGMETHOD = 27;
  protected final static int GPSAREAINFORMATION = 28;
  protected final static int GPSDATESTAMP = 29;
  protected final static int GPSDIFFERENTIAL = 30;
  
  // Interoperability Tags
  protected final static int INTEROPERABILITYINDEX = 1;
  protected final static int INTEROPERABILITYVERSION = 2;
  
  // TIFF Baseline
  protected final static int NEWSUBFILETYPE = 254;
  protected final static int SUBFILETYPE = 255;
  protected final static int IMAGEWIDTH = 256;
  protected final static int IMAGELENGTH = 257;
  protected final static int BITSPERSAMPLE = 258;
  protected final static int COMPRESSION =  259;
  protected final static int PHOTOMETRICINTERPRETATION = 262;
  protected final static int THRESHOLDING = 263;
  protected final static int CELLWIDTH = 264;
  protected final static int CELLLENGTH = 265;
  protected final static int FILLORDER = 266;
  protected final static int DOCUMENTNAME = 269;
  protected final static int IMAGEDESCRIPTION = 270;
  protected final static int MAKE = 271;
  protected final static int MODEL = 272;
  protected final static int STRIPOFFSETS = 273;
  protected final static int ORIENTATION = 274;
  protected final static int SAMPLESPERPIXEL = 277;
  protected final static int ROWSPERSTRIP = 278;
  protected final static int STRIPBYTECOUNTS = 279;
  protected final static int MINSAMPLEVALUE = 280;
  protected final static int MAXSAMPLEVALUE = 281;
  protected final static int XRESOLUTION = 282;
  protected final static int YRESOLUTION = 283;
  protected final static int PLANARCONFIGURATION = 284;
  protected final static int PAGENAME = 285;
  protected final static int XPOSITION = 286;
  protected final static int YPOSITION = 287;
  protected final static int FREEOFFSETS = 288;
  protected final static int FREEBYTECOUNTS = 289;
  protected final static int GRAYRESPONSEUNIT = 290;
  protected final static int GRAYRESPONSECURVE = 291;
  protected final static int T4OPTIONS = 292;
  protected final static int T6OPTIONS = 293;
  protected final static int RESOLUTIONUNIT = 296;
  protected final static int PAGENUMBER = 297;
  protected final static int TRANSFERFUNCTION = 301;
  protected final static int SOFTWARE = 305;
  protected final static int DATETIME = 306;
  protected final static int ARTIST = 315;
  protected final static int HOSTCOMPUTER = 316;
  protected final static int PREDICTOR = 317;
  protected final static int WHITEPOINT = 318;
  protected final static int PRIMARYCHROMATICITIES = 319;
  protected final static int COLORMAP = 320;
  protected final static int HALFTONEHINTS = 321;
  protected final static int TILEWIDTH = 322;
  protected final static int TILELENGTH = 323;
  protected final static int TILEOFFSETS = 324;
  protected final static int TILEBYTECOUNTS = 325;
  protected final static int SUBIFDS = 330;
  protected final static int INKSET = 332;
  protected final static int INKNAMES = 333;
  protected final static int NUMBEROFINKS = 334;
  protected final static int DOTRANGE = 336;
  protected final static int TARGETPRINTER = 337;
  protected final static int EXTRASAMPLES = 338;
  protected final static int SAMPLEFORMAT = 339;
  protected final static int SMINSAMPLEVALUE = 340;
  protected final static int SMAXSAMPLEVALUE = 341;
  protected final static int TRANSFERRANGE = 342;
  protected final static int INDEXED = 346;
  protected final static int JPEGPROC = 512;
  protected final static int JPEGINTERCHANGEFORMAT = 513;
  protected final static int JPEGINTERCHANGEFORMATLENGTH = 514;
  protected final static int JPEGRESTARTINTERVAL = 515;
  protected final static int JPEGLOSSLESSPREDICTORS = 517;
  protected final static int JPEGPOINTTRANSFORMS = 518;
  protected final static int JPEGQTABLES = 519;
  protected final static int JPEGDCTABLES = 520;
  protected final static int JPEGACTABLES = 521;
  protected final static int YCBCRCOEFFICIENTS = 529;
  protected final static int YCBCRSUBSAMPLING = 530;
  protected final static int YCBCRPOSITIONING = 531;
  protected final static int REFERENCEBLACKWHITE = 532;
  protected final static int XMP = 700;
  protected final static int COPYRIGHT = 33432;


  //EXIF
  protected final static int CFAREPEATPATTERNDIM = 33421;  // 0x828d
  protected final static int CFAPATTERN = 33422;  // 0x828e
  protected final static int EXPOSURETIME = 33434;
  protected final static int FNUMBER = 33437;
  protected final static int IPTC = 33723;
  protected final static int PHOTOSHOP = 34377;
  protected final static int EXIFIFD = 34665;
  protected final static int ICCPROFILE = 34675;
  protected final static int EXPOSUREPROGRAM = 34850;  // 0x8822
  protected final static int SPECTRALSENSITIVITY = 34852;
  protected final static int GPSIFD = 34853;
  protected final static int ISOSPEEDRATINGS = 34855;
  protected final static int SENSITIVITYTYPE = 34864;
  protected final static int RECOMMENDEDEXPOSUREINDEX = 34866; // 0x8832 //
//  protected final static int INTEROPERABILITYIFD = 34965;
  protected final static int EXIFVERSION = 36864;  // 0x8830
  protected final static int DATETIMEORIGINAL = 36867;
  protected final static int DATETIMEDIGITIZED = 36868;
  protected final static int COMPONENTSCONFIGURATION = 37121;
  protected final static int SHUTTERSPEEDVALUE = 37377;  // 0x9201
  protected final static int APERTUREVALUE = 37378;  // 0x9202
  protected final static int BRIGHTNESSVALUE = 37379;  // 0x9203
  protected final static int EXPOSURECOMPENSATION = 37380;  // 0x9204
  protected final static int MAXAPERTUREVALUE = 37381;
  protected final static int SUBJECTDISTANCE = 37382;  // 0x9206
  protected final static int METERINGMODE = 37383;  // 0x9207
  protected final static int LIGHTSOURCE = 37384;  // 0x9208
  protected final static int FLASH = 37385;
  protected final static int FOCALLENGTH = 37386;
  protected final static int FOCALPLANEXRESOLUTION = 37390;  // 0x920e
  protected final static int FOCALPLANEYRESOLUTION = 37391;  // 0x920f
  protected final static int FOCALPLANERESOLUTIONUNIT = 37392;  // 0x9210
  protected final static int IMAGENUMBER = 37393;  // 0x9211
  protected final static int IMAGEHISTORY = 37395;
  protected final static int SUBSECTIME = 37520;  // 0x9290
  protected final static int SUBSECTIMEORIGINAL = 37521;  // 0x9291
  protected final static int SUBSECTIMEDIGITIZED = 37522;  // 0x9292
  protected final static int FLASHPIXVERSION = 40960;
  protected final static int COLORSPACE = 40961;
  protected final static int PIXELXDIMENSION = 40962;
  protected final static int PIXELYDIMENSION = 40963;
  protected final static int INTEROPERABILITYIFD = 40965;
  protected final static int FOCALPLANEXRESOLUTIONPRIVATE = 41486;  // 0xa20e
  protected final static int FOCALPLANEYRESOLUTIONPRIVATE = 41487;  // 0xa20f
  protected final static int FOCALPLANERESOLUTIONUNITPRIVATE = 41488;  // 0xa210
  protected final static int SENSINGMETHOD = 41495;
  protected final static int FILESOURCE = 41728;  // 0xa300
  protected final static int SCENETYPE = 41729;  // 0xa301
  protected final static int CFAPATTERN2 = 41730;  // 0xa302
  protected final static int CUSTOMRENDERED = 41985;  // 0xa401
  protected final static int EXPOSUREMODE = 41986;  // 0xa402
  protected final static int WHITEBALANCE = 41987;  // 0xa403
  protected final static int DIGITALZOOMRATIO = 41988;  // 0xa404
  protected final static int FOCALLENGTHIN35MMFORMAT = 41989;  // 0xa405
  protected final static int SCENECAPTURETYPE = 41990;  // 0xa406
  protected final static int GAINCONTROL = 41991;  // 0xa407
  protected final static int CONTRAST = 41992;  // 0xa408
  protected final static int SATURATION = 41993;  // 0xa409
  protected final static int SHARPNESS = 41994;  // 0xa40a
  protected final static int SUBJECTDISTANERANGE = 41996;  // 0xa40c
  protected final static int IMAGEUNIQUEID = 42016;
  protected final static int SERIALNUMBER = 42033;  // 0xa431
  protected final static int LENSINFO = 42034;  // 0xa432
  protected final static int LENSMODEL = 42036;  // 0xa434
  protected final static int LENSSERIALNUMBERL = 42037;  // 0xa435
  
  // DNG
  protected final static int DNGVERSION = 50706;
  protected final static int DNGBACKWARDVERSION = 50707;
  protected final static int UNIQUECAMERAMODEL = 50708;
  protected final static int LOCALIZEDCAMERAMODEL = 50709;
  protected final static int CFAPLANECOLOR = 50710;
  protected final static int CFALAYOUT = 50711;
  protected final static int LINEARIZATIONTABLE = 50712;
  protected final static int BLACKLEVELREPEATDIM = 50713;  // 0xc619 //
  protected final static int BLACKLEVEL = 50714;
  protected final static int WHITELEVEL = 50717;
  protected final static int DEFAULTSCALE = 50718; // 0xc61e //
  protected final static int DEFAULTCROPORIGIN = 50719;
  protected final static int DEFAULTCROPSIZE = 50720;
  protected final static int COLORMATRIX1 = 50721;  // 0xc621
  protected final static int COLORMATRIX2 = 50722;  // 0xc622
  protected final static int CAMERACALIBRATION1 = 50723;  // 0xc623
  protected final static int CAMERACALIBRATION2 = 50724;  // 0xc624
  protected final static int ANALOGUEBALANCE = 50727;  // 0xc627
  protected final static int ASSHOTNEUTRAL = 50728;  // 0xc628
  protected final static int BASELINEEXPOSURE = 50730;  // 0xc62a
  protected final static int BASELINENOISE = 50731;  // 0xc62b
  protected final static int BASELINESHARPNESS = 50732;  // 0xc62c
  protected final static int BAYERGREENSPLIT = 50733;  // 0xc62d
  protected final static int LINEARRESPONSELIMIT = 50734;  // 0xc62e
  protected final static int CAMERASERIALNUMBER = 50735;  // 0xc62f
  protected final static int DNGLENSINFO = 50736;  // 0xc630
  protected final static int ANTIALIASSTRENGTH = 50738;  // 0xc632 //
  protected final static int SHADOWSCALE = 50739;  // 0xc633
  protected final static int DNGPRIVATEDATA = 50740;  // 0xc634
  protected final static int CALIBRATIONILLUMINANT1 = 50778;  // 0xc65a
  protected final static int CALIBRATIONILLUMINANT2 = 50779;  // 0xc65b
  protected final static int BESTQUALITYSCALE = 50780;  // 0xc65c //
  protected final static int RAWDATAUNIQUEID = 50781;  // 0xc65d
  protected final static int ORIGINALRAWFILENAME = 50827;
  protected final static int ACTIVEAREA = 50829;  // 0xc68d
  protected final static int CAMERACALIBRATIONSIG = 50931;  // 0xc6f3
  protected final static int PROFILECALIBRATIONSIG = 50932;  // 0xc6f4
  protected final static int PROFILENAME = 50936;  // 0xc6f8
  protected final static int PROFILEHUESATMAPDIMS = 50937;  // 0xc6f9
  protected final static int PROFILEHUESATMAPDATA1 = 50938;  // 0xc6fa
  protected final static int PROFILEHUESATMAPDATA2 = 50939;  // 0xc6fb
  protected final static int PROFILETONECURVE = 50940;  // 0xc6fc
  protected final static int PROFILEEMBEDPOLICY = 50941;  // 0xc6fd
  protected final static int PROFILECOPYRIGHT = 50942;  // 0xc6fe
  protected final static int FORWARDMATRIX1 = 50964;  // 0xc714
  protected final static int FORWARDMATRIX2 = 50965;  // 0xc715
  protected final static int PREVIEWAPPLICATIONNAME = 50966;  // 0xc716
  protected final static int PREVIEWAPPLICATIONVERSION = 50967;  // 0xc717
  protected final static int PREVIEWSETTINGSDIGEST = 50969;  // 0xc719
  protected final static int PREVIEWCOLORSPACE = 50970;  // 0xc71a
  protected final static int PREVIEWDATETIME = 50971;  // 0xc71b
  protected final static int PROFILELOOKTABLEDIMS = 50981;  // 0xc725
  protected final static int PROFILELOOKTABLEDATA = 50982;  // 0xc726
  protected final static int NOISEPROFILE = 51041;  // 0xc761
  protected final static int NEWRAWIMAGEDIGEST = 51111;  // 0xc7a7

  protected final static String[] labels = new String []
    {
    "NewSubFileType:0:Full single page:1:Reduced single page:2:Full multi-page:3:Reduced multi-page:4:Transparency single page:5:Transparency multi-page",  
    "SubFileType:1:Full Resolution:2:Reduced Resolution:3:Multi-page",  
    "Compression:1:None:2:CCITT:3:T4:4:T6:5:LZW:6:JPEG thumbnail:7:JPEG:8:Deflate:32773:PackBits:34892:JPEG", 
    "PhotometricInterpretation:0:WhiteIsZero:1:BlackIsZero:2:RGB:3:PaletteColor:4:TransparencyMask:8:PhotometricCIELAB:32803:PhotometricCFA:34892:PhotometricLinearRaw",
    "Thresholding:1:No dithering:2:Ordered dither:3:Random dither",
    "FillOrder:1:Normal:2:Reversed",
    "Orientation:1:Horizontal:2:Mirror horizontal:3:Rotate 180:4:Mirror vertical:5:Mirror horizontal and rotate 270 CW:6:Rotate 90 CW:7:Mirror horizontal and rotate 90 CW:8:Rotate 270 CW",
    "PlanarConfiguration:1:Chunky:2:Planar",
    "GrayResponseUnit:1:0.1:2:0.001:3:0.0001:4:1e-05:5:1e-06",
    "T4Options:0:1D encoding, compressed, no fill bits:1:2D encoding, compressed, no fill bits:2:1D encoding, uncompressed, no fill bits:3:2D encoding, uncompressed, no fill bits:4:1D encoding, compressed, fill bits;5:2D encoding, compressed, fill bits:6:1D encoding, uncompressed, fill bits:7:2D encoding, uncompressed, fill bits",
    "T6Options:0:Compressed:1:Uncompressed",
    "ResolutionUnit:1:None:2:Inches:3:Centimeters",
    "Predictor:1:None:2:Horizontal difference:3:Floating point:34892:Hhorizontal difference X2:34893:Horizontal difference X4:34894:Floating point X2:34895:Floating point X4",
    "InkSet:1:CMYK:2:Not CMYK",
    "ExtraSamples:0:Unspecified:1:Associated Alpha:2:Unassociated Alpha",
    "SampleFormat:1:Unsigned integer:2:Signed integer:3:Float:4:Undefined:5:Complex integer:6:Complex float",
    "JPEGProc:1:Baseline:14:Lossless",
    "YCbCrPositioning:1:Centered:2:Co-sited",
    "ExposureProgram:0:Not defined:1:Manual:2:Program AE:3:Aperture-priority AE:4:Shutter speed priority AE:5:Creative [Slow speed]:6:Action [High speed]:7:Portrait:8:Landscape:9:Bulb",
    "SensitivityType:0:Unknown:1:Standard:2:Recommended exposure index:3:ISO speed",
    "MeteringMode:0:Unknown:1:Average:2:Center-weighted average:3:Spot:4:Multi-spot:5:Multi-segment:6:Partial:255:Other",
    "FocalPlaneResolutionUnit:1:None:2:Inches:3:Centimeters:4:Millimeters:5:Micrometers",
    "FocalPlaneResolutionUnitPrivate:1:None:2:Inches:3:Centimeters:4:Millimeters:5:Micrometers",
    "SensingMethod:1:Monochrome area:2:One-chip color area:3:Two-chip color area:4:Three-chip color area:5:Color sequential area:6:Monchrome linear:7:Trilinear:8:Color sequential linear",
    "FileSource:1:File scanner:2:Reflection print scanner:3:Digital camera",
    "SceneType:1:Directly photographed",
    "CustomRendered:0:Normal:1:custom:3:HDR:6:Panorama:8:Portrait",
    "ExposureMode:0:Auto:1:Manual:3:Auto bracket",
    "WhiteBalance:0:Auto:1:Manual",
    "SceneCaptureType:0:Standard:1:Landscape:2:Portrait:3:Night",
    "GainControl:0:None:1:Low gain up:2:High gain up:3:Low gain down:4:High gain down",
    "Contrast:0:Normal:1:Low:2:High",
    "Saturation:0:Normal:1:Low:2:High",
    "Sharpness:0:Normal:1:Soft:2:Hard",
    "SubjectDistanceRange:0:Unknown:1:Macro:2:Close:3:Distant",
    "CFALayout:1:Rectangular:2:Even columns offset down 1/2 row:3:Even columns offset up 1/2 row:4:Even rows offset right 1/2 column:5:Even rows offset left 1/2 column:6:Even rows offset up by 1/2 row, even columns offset left by 1/2 column:7:Even rows offset up by 1/2 row, even columns offset right by 1/2 olumn:8:Even rows offset down by 1/2 row, even columns offset left by 1/2 column:9:Even rows offset down by 1/2 row, even columns offset right by 1/2 column",
    "ProfileEmbedPolicy:0:Allow copying:1:Embed if used:2:Never embed:3:No restrictions",
    "PreviewColorSpace:0:Unknown:1:Gray gamma 2.2:2:sRGB:3:Adobe RGB:4:ProPhoto RGB"
    };
  
  
  
  // TIFF Data Types
  public final static int BYTE = 1;
  public final static int ASCII = 2;
  public final static int SHORT = 3;
  public final static int LONG = 4;
  public final static int RATIONAL = 5;
  public final static int SBYTE = 6;
  public final static int UNDEFINED = 7;
  public final static int SSHORT = 8;
  public final static int SLONG = 9;
  public final static int SRATIONAL = 10;
  public final static int FLOAT = 11;
  public final static int DOUBLE = 12;
  public final static int IFD = 13;
  public final static int LONG8 = 16;
  public final static int SLONG8 = 17;
  public final static int IFD8 = 18;

  // TIFF Compression Types
  protected final static int NONE = 1;
  protected final static int CCITT = 2;
  protected final static int T4 = 3;
  protected final static int T6 = 4;
  protected final static int LZW = 5;
  protected final static int JPEG = 6;
  protected final static int PACKBITS = 32773;
  
  // TIFF Sample Format Types
  protected final static int SF_INTEGER = 1;
  protected final static int SF_SINTEGER = 2;
  protected final static int SF_IEEEFLOATING = 3;
  protected final static int SF_UNDEFINED = 4;
  
  // TIFF Photometric Interpretations
  protected final static int WHITEISZERO = 0;
  protected final static int BLACKISZERO = 1;
  protected final static int RGB = 2;
  protected final static int PALETTECOLOR = 3;
  protected final static int TRANSPARENCYMASK = 4;
  protected final static int PHOTOMETRICCIELAB = 8;
  protected final static int PHOTOMETRICCFA = 32803;
  protected final static int PHOTOMETRICLINEARRAW = 34892;
  
  // TIFF Resolution Units
  protected final static int RU_UNDEFINED = 1;
  protected final static int RU_INCHES = 2;
  protected final static int RU_CENTIMETERS = 3;

  // TIFF Planar Configuration
  protected final static int CHUNKY = 1;
  protected final static int PLANAR = 2;

  // TIFF Subfile Types
  protected final static int FULL = 0;
  protected final static int REDUCED = 1;
  protected final static int MULTIPAGEFULL = 2;
  protected final static int MULTIPAGEREDUCED = 3;
  protected final static int TRANSPARENCYFULLSINGLE = 4;
  protected final static int TRANSPARENCYREDUCEDSINGLE = 5;
  protected final static int TRANSPARENCYFULLMULTIPAGE = 6;
  protected final static int TRANSPARENCYREDUCEDMULTIPAGE = 7;
  protected final static int ALTERNATEPREVIEW = 65537;
  
  // Fields
  private TiffDirectory tiffDirectory;
  private IPTCRecord iptcRecord;
  private TiffEntry tiffEntry;
  private IPTCDataSet iptcDataSet;

  // First Constructor -- from a Tiff Entry
  TiffTag(TiffDirectory directory, TiffEntry entry)
    {
    tiffDirectory = directory;
    iptcRecord = null;
    tiffEntry = entry;
    iptcDataSet = null;
    }

  // Second Constructor -- from an IPTC data set
  TiffTag(IPTCRecord record, IPTCDataSet dataSet)
    {
    tiffDirectory = null;
    iptcRecord = record;
    tiffEntry = null;
    iptcDataSet = dataSet;
    }

  // Third Constructor -- from a TIFF tag name
  TiffTag(TiffDirectory directory, String name)
    {
    tiffDirectory = directory;
    iptcRecord = null;
    tiffEntry = new TiffEntry(directory.getTiffHeader(), name);
    iptcDataSet = null;
    directory.addTiffEntry(tiffEntry);
    }

  // Fourth Constructor -- from an IPTC data set
  TiffTag(IPTCRecord record, String name)
    {
    tiffDirectory = null;
    iptcRecord = record;
    tiffEntry = null;
    iptcDataSet = new IPTCDataSet(name);
    record.addIPTCDataSet(iptcDataSet);
    }

  // Method to retrieve values from Tiff Tag
  public long getNumber()
    {
    long result = 0;
    if (tiffEntry != null)
      {
      long[] values = tiffEntry.getLongValues();
      if (values != null) result = values[0];
      }
    return result;
    }

  // Method to retrieve values from Tiff Tag
  public String getString()
    {
    if (tiffEntry != null)
      {
      if (tiffEntry.getTiffType() == ASCII) return tiffEntry.getStringValues();
      return null;
      }
    if (iptcDataSet != null) return iptcDataSet.getValue();
    return null;
    }

  // Method to retrieve values from Tiff Tag
  public long[] getArray()
    {
    long[] result = null;
    if (tiffEntry != null)
      {
      long[] values = tiffEntry.getLongValues();
      if (values != null) result = values;
      }
    return result;
    }

  // Method to put values into a Tiff Tag
  public void setString(String input)
    {
//    tiffEntry.setTiffCount(input.length());
    if (tiffEntry != null) tiffEntry.putStringValues(input);
    if (iptcDataSet != null) iptcDataSet.setValue(input);
    }

  // Method to put values into a Tiff Tag
  public void setNumber(long number)
    {
    int length = tiffEntry.getValueLength();
    long[] buffer = new long[1];
    buffer[0] = number;
    if (tiffEntry != null) tiffEntry.putLongValues(buffer);
    }

  // Method to put values into a Tiff Tag
  public void setNumber(long number, boolean b)
    {
    int length = tiffEntry.getValueLength();
    long[] buffer = new long[1];
    buffer[0] = number;
    if (tiffEntry != null) tiffEntry.putLongValues(buffer);
    }

  // Method to put values into a Tiff Tag
  public void setArray(long[] numbers)
    {
    if (tiffEntry != null)
      {
      tiffEntry.putLongValues(numbers);
      }
    }
  
  // Method to put values into a Tiff Tag
  public void setNumber(double number)
    {
    int length = tiffEntry.getValueLength();
    double[] buffer = new double[1];
    buffer[0] = number;
    if (tiffEntry != null) tiffEntry.putDoubleValues(buffer);
    }

  // Method to put values into a Tiff Tag
  public void setArray(double[] numbers)
    {
    if (tiffEntry != null) tiffEntry.putDoubleValues(numbers);
    }
  
  // Method to remove a TiffTag
  protected void deleteTiffTag(String name)
    {
    if (tiffEntry != null)
      {
      int code = getTiffTagCode(name);
      tiffDirectory.removeTiffEntry(code);
      tiffEntry = null;
      return;
      }
    if (iptcRecord != null) iptcRecord.removeIPTCDataSet(iptcDataSet.getTag().getIPTCTagName());
    }
  
  // Method to add a TiffTag
  protected void addTiffTag(String name)
    {
    if (tiffEntry != null)
      {
      tiffDirectory.addTiffEntry(tiffEntry);
      return;
      }
    if (iptcRecord != null) iptcRecord.removeIPTCDataSet(iptcDataSet.getTag().getIPTCTagName());
    }
  
  // Method to determine type from tag code
  public static int getTagType(int tag)
    {
    int result = getTiffTagType(tag);
    if (result == -1) result = getExifTagType(tag);
    if (result == -1) result = getDngTagType(tag);
    if (result == -1) result = getGpsTagType(tag);
    if (result == -1) result = getInterTagType(tag);
    return result;
    }
  
  // Method to determine type from tag code
  public static int getTiffTagType(int tag)
    {
    switch (tag)
      {
      case DOCUMENTNAME:  // TIFF
      case IMAGEDESCRIPTION:  // TIFF
      case MAKE:  // TIFF
      case MODEL:  // TIFF
      case PAGENAME:  // TIFF
      case SOFTWARE:  // TIFF
      case DATETIME:  // TIFF
      case ARTIST:  // TIFF
      case HOSTCOMPUTER:  // TIFF
      case INKNAMES:  // TIFF
      case TARGETPRINTER:  // TIFF
      case COPYRIGHT:  // TIFF
        return ASCII;
      case XMP:  // TIFF
        return BYTE;
      case BITSPERSAMPLE:  // TIFF
      case COMPRESSION: // TIFF
      case PHOTOMETRICINTERPRETATION:  // TIFF
      case THRESHOLDING:  // TIFF
      case CELLWIDTH:  // TIFF
      case CELLLENGTH:  // TIFF
      case FILLORDER:  // TIFF
      case ORIENTATION:  // TIFF
      case SAMPLESPERPIXEL:  // TIFF
      case MINSAMPLEVALUE:  // TIFF
      case MAXSAMPLEVALUE:  // TIFF
      case PLANARCONFIGURATION:  // TIFF
      case GRAYRESPONSEUNIT:  // TIFF
      case GRAYRESPONSECURVE:  // TIFF
      case RESOLUTIONUNIT:  // TIFF
      case PAGENUMBER:  // TIFF
      case TRANSFERFUNCTION:  // TIFF
      case PREDICTOR:  // TIFF
      case COLORMAP:  // TIFF
      case HALFTONEHINTS:  // TIFF
      case INKSET:  // TIFF
      case NUMBEROFINKS:  // TIFF
      case DOTRANGE:  // TIFF
      case EXTRASAMPLES:  // TIFF
      case SAMPLEFORMAT:  // TIFF
      case TRANSFERRANGE:  // TIFF
      case INDEXED:  // TIFF
      case JPEGPROC:  // TIFF
      case JPEGRESTARTINTERVAL:  // TIFF
      case JPEGLOSSLESSPREDICTORS:  // TIFF
      case JPEGPOINTTRANSFORMS:  // TIFF
      case YCBCRSUBSAMPLING:  // TIFF
      case YCBCRPOSITIONING:  // TIFF
        return SHORT;
      case NEWSUBFILETYPE:  // TIFF
      case IMAGEWIDTH:  // TIFF
      case IMAGELENGTH:  // TIFF
      case STRIPOFFSETS:  // TIFF
      case ROWSPERSTRIP:  // TIFF
      case STRIPBYTECOUNTS:  // TIFF
      case FREEOFFSETS:  // TIFF
      case FREEBYTECOUNTS:  // TIFF
      case T4OPTIONS:  // TIFF
      case T6OPTIONS:  // TIFF
      case TILEWIDTH:  // TIFF
      case TILELENGTH:  // TIFF
      case TILEOFFSETS:  // TIFF
      case TILEBYTECOUNTS:  // TIFF
      case SMINSAMPLEVALUE: // TIFF
      case SMAXSAMPLEVALUE: // TIFF
      case JPEGINTERCHANGEFORMAT:  // TIFF
      case JPEGINTERCHANGEFORMATLENGTH:  // TIFF
      case JPEGQTABLES:  // TIFF
      case JPEGDCTABLES:  // TIFF
      case JPEGACTABLES:  // TIFF
        return LONG;
      case XRESOLUTION:  // TIFF
      case YRESOLUTION:  // TIFF
      case XPOSITION:  // TIFF
      case YPOSITION:  // TIFF
      case WHITEPOINT: // TIFF
      case PRIMARYCHROMATICITIES:  // TIFF
      case YCBCRCOEFFICIENTS:  // TIFF
      case REFERENCEBLACKWHITE:  // TIFF
        return RATIONAL;
      case SUBIFDS:      // TIFF
        return IFD;
      }
    return -1;
    }

  // Method to determine type from tag code
  public static int getExifTagType(int tag)
    {
    switch (tag)
      {
      case SPECTRALSENSITIVITY:  // EXIF
      case DATETIMEORIGINAL:  // EXIF
      case DATETIMEDIGITIZED:  // EXIF
      case IMAGEHISTORY:  // EXIF
      case IMAGEUNIQUEID:  // EXIF
      case SUBSECTIME:  // EXIF
      case SUBSECTIMEORIGINAL:  // EXIF
      case SUBSECTIMEDIGITIZED: // EXIF
      case SERIALNUMBER:  // EXIF
      case LENSMODEL:  // EXIF
      case LENSSERIALNUMBERL:  // EXIF
        return ASCII;
      case IPTC:  // EXIF
      case PHOTOSHOP:  // EXIF
      case CFAPATTERN:  // EXIF
        return BYTE;
      case SUBFILETYPE:  // EXIF
      case EXPOSUREPROGRAM:  // EXIF
      case SENSITIVITYTYPE:  // EXIF
      case ISOSPEEDRATINGS:  // EXIF
      case FLASH:  // EXIF
      case COLORSPACE:  // EXIF
      case SENSINGMETHOD:  // EXIF
      case METERINGMODE:  // EXIF
      case LIGHTSOURCE:  // EXIF
      case FOCALPLANERESOLUTIONUNIT:  // EXIF
      case FOCALPLANERESOLUTIONUNITPRIVATE:  // EXIF
      case CUSTOMRENDERED:  // EXIF
      case EXPOSUREMODE:    // EXIF
      case WHITEBALANCE:    // EXIF
      case FOCALLENGTHIN35MMFORMAT:  // EXIF
      case SCENECAPTURETYPE:  // EXIF
      case GAINCONTROL:      // EXIF
      case CONTRAST:        // EXIF
      case SATURATION:     // EXIF
      case SHARPNESS:      // EXIF
      case SUBJECTDISTANERANGE:  // EXIF
      case CFAREPEATPATTERNDIM:  // EXIF
        return SHORT;
      case EXIFIFD:  // EXIF
      case GPSIFD:  // EXIF
      case RECOMMENDEDEXPOSUREINDEX:  // EXIF
      case INTEROPERABILITYIFD: // EXIF
      case IMAGENUMBER:  // EXIF
      case PIXELXDIMENSION:  // EXIF
      case PIXELYDIMENSION:  // EXIF
        return LONG;
      case EXPOSURETIME: // EXIF
      case FNUMBER:  // EXIF
      case MAXAPERTUREVALUE:  // EXIF
      case FOCALLENGTH:  // EXIF
      case FOCALPLANEXRESOLUTION:  // EXIF
      case FOCALPLANEYRESOLUTION:  // EXIF
      case DIGITALZOOMRATIO:  // EXIF
      case LENSINFO:  // EXIF
      case APERTUREVALUE:  // EXIF
      case FOCALPLANEXRESOLUTIONPRIVATE: // EXIF
      case FOCALPLANEYRESOLUTIONPRIVATE: // EXIF
        return RATIONAL;
      case SHUTTERSPEEDVALUE:  // EXIF
      case EXPOSURECOMPENSATION:  // EXIF
      case SUBJECTDISTANCE:  // EXIF
      case BRIGHTNESSVALUE:  // EXIF
        return SRATIONAL;
      case FLASHPIXVERSION:  // EXIF
      case ICCPROFILE:  // EXIF
      case EXIFVERSION: // EXIF
      case FILESOURCE:  // EXIF
      case SCENETYPE:   // EXIF
      case CFAPATTERN2: // EXIF
      case COMPONENTSCONFIGURATION:  // EXIF
        return UNDEFINED;
      }
    return -1;
    }

  // Method to determine type from tag code
  public static int getDngTagType(int tag)
    {
    switch (tag)
      {
      case UNIQUECAMERAMODEL:  // DNG
      case LOCALIZEDCAMERAMODEL:  // DNG
      case CAMERASERIALNUMBER:  // DNG
      case CAMERACALIBRATIONSIG:  // DNG
      case PROFILENAME:       // DNG
      case PROFILECOPYRIGHT:   // DNG
      case PREVIEWAPPLICATIONNAME:   // DNG
      case PREVIEWAPPLICATIONVERSION:  // DNG
      case PREVIEWDATETIME:    // DNG
        return ASCII;
      case DNGVERSION:  // DNG
      case DNGBACKWARDVERSION:  // DNG
      case RAWDATAUNIQUEID:  // DNG
      case PREVIEWSETTINGSDIGEST:  // DNG
      case NEWRAWIMAGEDIGEST:   // DNG
      case CFAPLANECOLOR:  // DNG
      case DNGPRIVATEDATA:  // DNG
      case PROFILECALIBRATIONSIG:  // DNG
        return BYTE;
      case CFALAYOUT:  // DNG
      case LINEARIZATIONTABLE:  // DNG
      case BLACKLEVELREPEATDIM:  // DNG
      case ACTIVEAREA:  // DNG
      case CALIBRATIONILLUMINANT1:  // DNG
      case CALIBRATIONILLUMINANT2:  // DNG
        return SHORT;
      case PROFILEHUESATMAPDIMS:  // DNG
      case PROFILEEMBEDPOLICY:  // DNG
      case PREVIEWCOLORSPACE:   // DNG
      case PROFILELOOKTABLEDIMS:  // DNG
      case BAYERGREENSPLIT:  // DNG
      case WHITELEVEL:  // DNG
        return LONG;
      case PROFILEHUESATMAPDATA1:  // DNG
      case PROFILEHUESATMAPDATA2:  // DNG
      case PROFILELOOKTABLEDATA:  // DNG
      case PROFILETONECURVE:  // DNG
        return FLOAT;
      case BLACKLEVEL:  // DNG
      case ANALOGUEBALANCE:  // DNG
      case BASELINENOISE:  // DNG
      case BASELINESHARPNESS:  // DNG
      case LINEARRESPONSELIMIT:  // DNG
      case DNGLENSINFO:   // DNG
      case DEFAULTSCALE:  // DNG
      case ANTIALIASSTRENGTH:  // DNG
      case BESTQUALITYSCALE:  // DNG
      case ASSHOTNEUTRAL:  // DNG
      case DEFAULTCROPORIGIN:  // DNG
      case DEFAULTCROPSIZE:  // DNG
        return RATIONAL;
      case NOISEPROFILE:  // DNG
        return DOUBLE;
      case COLORMATRIX1:  // DNG
      case COLORMATRIX2:  // DNG
      case CAMERACALIBRATION1:  // DNG
      case CAMERACALIBRATION2:  // DNG
      case BASELINEEXPOSURE:  // DNG
      case SHADOWSCALE:  // DNG
      case FORWARDMATRIX1:   // DNG
      case FORWARDMATRIX2:   // DNG
        return SRATIONAL;
      case ORIGINALRAWFILENAME:  // DNG
        return UNDEFINED;
      }
    return -1;
    }

  // Method to determine type from tag code
  public static int getGpsTagType(int tag)
    {
    switch (tag)
      {
      case GPSLATITUDEREF:
      case GPSLONGITUDEREF:
      case GPSSATELLITES:
      case GPSSTATUS:
      case GPSMEASUREMODE:
      case GPSSPEEDREF:
      case GPSTRACKREF:
      case GPSIMGDIRECTIONREF:
      case GPSMAPDATUM:
      case GPSDESTLATITUDEREF:
      case GPSDESTLONGITUDEREF:
      case GPSDESTBEARINGREF:
      case GPSDESTDISTANCEREF:
      case GPSDATESTAMP:
        return ASCII;
      case GPSVERSIONID:
      case GPSALTITUDEREF:
        return BYTE;
      case GPSDIFFERENTIAL:
        return SHORT;
      case GPSLATITUDE:
      case GPSLONGITUDE:
      case GPSALTITUDE:
      case GPSTIMESTAMP:
      case GPSDOP:
      case GPSSPEED:
      case GPSTRACK:
      case GPSIMGDIRECTION:
      case GPSDESTLATITUDE:
      case GPSDESTLONGITUDE:
      case GPSDESTBEARING:
      case GPSDESTDISTANCE:
        return RATIONAL;
      case GPSPROCESSINGMETHOD:
      case GPSAREAINFORMATION:
        return UNDEFINED;
      }
    return -1;
    }

  // Method to determine type from tag code
  public static int getInterTagType(int tag)
    {
    switch (tag)
      {
      case INTEROPERABILITYINDEX:
        return ASCII;
      case INTEROPERABILITYVERSION:
        return UNDEFINED;
      }
    return -1;
    }
  
  // Method to get tag code
  public static int getTagCode(String name)
    {
    int result = getTiffTagCode(name);
    if (result == -1) result = getExifTagCode(name);
    if (result == -1) result = getDngTagCode(name);
    if (result == -1) result = getGpsTagCode(name);
    if (result == -1) result = getInterTagCode(name);
    return result;
    }
  

  // Method to get TIFF code from tag name
  protected static int getTiffTagCode(String name)
    {
    if (name.equalsIgnoreCase("SubfileType")) return SUBFILETYPE;
    if (name.equalsIgnoreCase("NewSubfileType")) return NEWSUBFILETYPE;
    if (name.equalsIgnoreCase("ImageWidth")) return IMAGEWIDTH;
    if (name.equalsIgnoreCase("ImageLength")) return IMAGELENGTH;
    if (name.equalsIgnoreCase("BitsPerSample")) return BITSPERSAMPLE;
    if (name.equalsIgnoreCase("Compression")) return COMPRESSION;
    if (name.equalsIgnoreCase("PhotometricInterpretation")) return PHOTOMETRICINTERPRETATION;
    if (name.equalsIgnoreCase("Thresholding")) return THRESHOLDING;
    if (name.equalsIgnoreCase("CellWidth")) return CELLWIDTH;
    if (name.equalsIgnoreCase("CellLength")) return CELLLENGTH;
    if (name.equalsIgnoreCase("FillOrder")) return FILLORDER;
    if (name.equalsIgnoreCase("DocumentName")) return DOCUMENTNAME;
    if (name.equalsIgnoreCase("ImageDescription")) return IMAGEDESCRIPTION;
    if (name.equalsIgnoreCase("Make")) return MAKE;
    if (name.equalsIgnoreCase("Model")) return MODEL;
    if (name.equalsIgnoreCase("StripOffsets")) return STRIPOFFSETS;
    if (name.equalsIgnoreCase("Orientation")) return ORIENTATION;
    if (name.equalsIgnoreCase("SamplesPerPixel")) return SAMPLESPERPIXEL;
    if (name.equalsIgnoreCase("RowsPerStrip")) return ROWSPERSTRIP;
    if (name.equalsIgnoreCase("StripByteCounts")) return STRIPBYTECOUNTS;
    if (name.equalsIgnoreCase("MinSampleValue")) return MINSAMPLEVALUE;
    if (name.equalsIgnoreCase("MaxSampleValue")) return MAXSAMPLEVALUE;
    if (name.equalsIgnoreCase("XResolution")) return XRESOLUTION;
    if (name.equalsIgnoreCase("YResolution")) return YRESOLUTION;
    if (name.equalsIgnoreCase("PlanarConfiguration")) return PLANARCONFIGURATION;
    if (name.equalsIgnoreCase("PageName")) return PAGENAME;
    if (name.equalsIgnoreCase("XPosition")) return XPOSITION;
    if (name.equalsIgnoreCase("YPosition")) return YPOSITION;
    if (name.equalsIgnoreCase("FreeOffsets")) return FREEOFFSETS;
    if (name.equalsIgnoreCase("FreeByteCounts")) return FREEBYTECOUNTS;
    if (name.equalsIgnoreCase("GrayResponseUnit")) return GRAYRESPONSEUNIT;
    if (name.equalsIgnoreCase("GrayResponseCurve")) return GRAYRESPONSECURVE;
    if (name.equalsIgnoreCase("T4Options")) return T4OPTIONS;
    if (name.equalsIgnoreCase("T6Options")) return T6OPTIONS;
    if (name.equalsIgnoreCase("ResolutionUnit")) return RESOLUTIONUNIT;
    if (name.equalsIgnoreCase("PageNumber")) return PAGENUMBER;
    if (name.equalsIgnoreCase("TransferFunction")) return TRANSFERFUNCTION;
    if (name.equalsIgnoreCase("Software")) return SOFTWARE;
    if (name.equalsIgnoreCase("DateTime")) return DATETIME;
    if (name.equalsIgnoreCase("Artist")) return ARTIST;
    if (name.equalsIgnoreCase("HostComputer")) return HOSTCOMPUTER;
    if (name.equalsIgnoreCase("Predictor")) return PREDICTOR;
    if (name.equalsIgnoreCase("WhitePoint")) return WHITEPOINT;
    if (name.equalsIgnoreCase("PrimaryChromaticities")) return PRIMARYCHROMATICITIES;
    if (name.equalsIgnoreCase("ColorMap")) return COLORMAP;
    if (name.equalsIgnoreCase("HalftoneHints")) return HALFTONEHINTS;
    if (name.equalsIgnoreCase("TileWidth")) return TILEWIDTH;
    if (name.equalsIgnoreCase("TileLength")) return TILELENGTH;
    if (name.equalsIgnoreCase("TileOffsets")) return TILEOFFSETS;
    if (name.equalsIgnoreCase("TileByteCounts")) return TILEBYTECOUNTS;
    if (name.equalsIgnoreCase("SubIFDS")) return SUBIFDS;
    if (name.equalsIgnoreCase("InkSet")) return INKSET;
    if (name.equalsIgnoreCase("InkNames")) return INKNAMES;
    if (name.equalsIgnoreCase("NumberOfInks")) return NUMBEROFINKS;
    if (name.equalsIgnoreCase("DotRange")) return DOTRANGE;
    if (name.equalsIgnoreCase("TargetPrinter")) return TARGETPRINTER;
    if (name.equalsIgnoreCase("ExtraSamples")) return EXTRASAMPLES;
    if (name.equalsIgnoreCase("SampleFormat")) return SAMPLEFORMAT;
    if (name.equalsIgnoreCase("SMinSampleValue")) return SMINSAMPLEVALUE;
    if (name.equalsIgnoreCase("SMaxSampleValue")) return SMAXSAMPLEVALUE;
    if (name.equalsIgnoreCase("TransferRange")) return TRANSFERRANGE;
    if (name.equalsIgnoreCase("Indexed")) return INDEXED;
    if (name.equalsIgnoreCase("JPEGProc")) return JPEGPROC;
    if (name.equalsIgnoreCase("JPEGInterchangeFormat")) return JPEGINTERCHANGEFORMAT;
    if (name.equalsIgnoreCase("JPEGInterchangeFormatLngth")) return JPEGINTERCHANGEFORMATLENGTH;
    if (name.equalsIgnoreCase("JPEGRestartInterval")) return JPEGRESTARTINTERVAL;
    if (name.equalsIgnoreCase("JPEGLosslessPredictors")) return JPEGLOSSLESSPREDICTORS;
    if (name.equalsIgnoreCase("JPEGPointTransforms")) return JPEGPOINTTRANSFORMS;
    if (name.equalsIgnoreCase("JPEGQTables")) return JPEGQTABLES;
    if (name.equalsIgnoreCase("JPEGDCTables")) return JPEGDCTABLES;
    if (name.equalsIgnoreCase("JPEGACTables")) return JPEGACTABLES;
    if (name.equalsIgnoreCase("YCbCrCoefficients")) return YCBCRCOEFFICIENTS;
    if (name.equalsIgnoreCase("YCbCrSubSampling")) return YCBCRSUBSAMPLING;
    if (name.equalsIgnoreCase("YCbCrPositioning")) return YCBCRPOSITIONING;
    if (name.equalsIgnoreCase("ReferenceBlackWhite")) return REFERENCEBLACKWHITE;
    if (name.equalsIgnoreCase("XMP")) return XMP;
    if (name.equalsIgnoreCase("Copyright")) return COPYRIGHT;
    return -1;
    }
  
  // Method to get TIFF code from tag name
  protected static int getExifTagCode(String name)
    {
    if (name.equalsIgnoreCase("CFARepeatPatternDim")) return CFAREPEATPATTERNDIM;
    if (name.equalsIgnoreCase("CFAPattern")) return CFAPATTERN;
    if (name.equalsIgnoreCase("ExposureTime")) return EXPOSURETIME;
    if (name.equalsIgnoreCase("FNumber")) return FNUMBER;
    if (name.equalsIgnoreCase("IPTC")) return IPTC;
    if (name.equalsIgnoreCase("Photoshop")) return PHOTOSHOP;
    if (name.equalsIgnoreCase("ExifIFD")) return EXIFIFD;
    if (name.equalsIgnoreCase("ICCProfile")) return ICCPROFILE;
    if (name.equalsIgnoreCase("ExposureProgram")) return EXPOSUREPROGRAM;
    if (name.equalsIgnoreCase("SpectralSensitivity")) return SPECTRALSENSITIVITY;
    if (name.equalsIgnoreCase("GPSIFD")) return GPSIFD;
    if (name.equalsIgnoreCase("ISOSpeedRatings")) return ISOSPEEDRATINGS;
    if (name.equalsIgnoreCase("ExifVersion")) return EXIFVERSION;
    if (name.equalsIgnoreCase("RecommendedExposureIndex")) return RECOMMENDEDEXPOSUREINDEX;
    if (name.equalsIgnoreCase("InteroperabilityIFD")) return INTEROPERABILITYIFD;
    if (name.equalsIgnoreCase("SensitivityType")) return SENSITIVITYTYPE;
    if (name.equalsIgnoreCase("DateTimeOriginal")) return DATETIMEORIGINAL;
    if (name.equalsIgnoreCase("DateTimeDigitized")) return DATETIMEDIGITIZED;
    if (name.equalsIgnoreCase("ComponentsConfiguration")) return COMPONENTSCONFIGURATION;
    if (name.equalsIgnoreCase("ShutterSpeedValue")) return SHUTTERSPEEDVALUE;
    if (name.equalsIgnoreCase("ApertureValue")) return APERTUREVALUE;
    if (name.equalsIgnoreCase("BrightnessValue")) return BRIGHTNESSVALUE;
    if (name.equalsIgnoreCase("ExposureCompensation")) return EXPOSURECOMPENSATION;
    if (name.equalsIgnoreCase("MaxApertureValue")) return MAXAPERTUREVALUE;
    if (name.equalsIgnoreCase("SubjectDistance")) return SUBJECTDISTANCE;
    if (name.equalsIgnoreCase("MeteringMode")) return METERINGMODE;
    if (name.equalsIgnoreCase("LightSource")) return LIGHTSOURCE;
    if (name.equalsIgnoreCase("Flash")) return FLASH;
    if (name.equalsIgnoreCase("FocalLength")) return FOCALLENGTH;
    if (name.equalsIgnoreCase("FocalPlaneXResolution")) return FOCALPLANEXRESOLUTION;
    if (name.equalsIgnoreCase("FocalPlaneYResolution")) return FOCALPLANEYRESOLUTION;
    if (name.equalsIgnoreCase("FocalPlaneResolutionUnit")) return FOCALPLANERESOLUTIONUNIT;
    if (name.equalsIgnoreCase("ImageNumber")) return IMAGENUMBER;
    if (name.equalsIgnoreCase("ImageHistory")) return IMAGEHISTORY;
    if (name.equalsIgnoreCase("SubSecTime")) return SUBSECTIME;
    if (name.equalsIgnoreCase("SubSecTimeOriginal")) return SUBSECTIMEORIGINAL;
    if (name.equalsIgnoreCase("SubSecTimeDigitized")) return SUBSECTIMEDIGITIZED;
    if (name.equalsIgnoreCase("FlashPixVersion")) return FLASHPIXVERSION;
    if (name.equalsIgnoreCase("ColorSpace")) return COLORSPACE;
    if (name.equalsIgnoreCase("PixelXDimension")) return PIXELXDIMENSION;
    if (name.equalsIgnoreCase("PixelYDimension")) return PIXELYDIMENSION;
    if (name.equalsIgnoreCase("FocalPlaneXResolutionPrivate")) return FOCALPLANEXRESOLUTIONPRIVATE;
    if (name.equalsIgnoreCase("FocalPlaneYResolutionPrivate")) return FOCALPLANEYRESOLUTIONPRIVATE;
    if (name.equalsIgnoreCase("FocalPlaneResolutionUnitPrivate")) return FOCALPLANERESOLUTIONUNITPRIVATE;
    if (name.equalsIgnoreCase("SensingMethod")) return SENSINGMETHOD;
    if (name.equalsIgnoreCase("FileSource")) return FILESOURCE;
    if (name.equalsIgnoreCase("SceneType")) return SCENETYPE;
    if (name.equalsIgnoreCase("CFAPattern2")) return CFAPATTERN2;
    if (name.equalsIgnoreCase("CustomRendered")) return CUSTOMRENDERED;
    if (name.equalsIgnoreCase("ExposureMode")) return EXPOSUREMODE;
    if (name.equalsIgnoreCase("WhiteBalance")) return WHITEBALANCE;
    if (name.equalsIgnoreCase("DigitalZoomRatio")) return DIGITALZOOMRATIO;
    if (name.equalsIgnoreCase("FocalLengthIn35mmFormat")) return FOCALLENGTHIN35MMFORMAT;
    if (name.equalsIgnoreCase("SceneCaptureType")) return SCENECAPTURETYPE;
    if (name.equalsIgnoreCase("GainControl")) return GAINCONTROL;
    if (name.equalsIgnoreCase("Contrast")) return CONTRAST;
    if (name.equalsIgnoreCase("Saturation")) return SATURATION;
    if (name.equalsIgnoreCase("Sharpness")) return SHARPNESS;
    if (name.equalsIgnoreCase("SubjectDistanceRange")) return SUBJECTDISTANERANGE;
    if (name.equalsIgnoreCase("ImageUniqueID")) return IMAGEUNIQUEID;
    if (name.equalsIgnoreCase("SerialNumber")) return SERIALNUMBER;
    if (name.equalsIgnoreCase("LensInfo")) return LENSINFO;
    if (name.equalsIgnoreCase("LensModel")) return LENSMODEL;
    if (name.equalsIgnoreCase("LensSerialNumber")) return LENSSERIALNUMBERL;
    return -1;
    }
  
  // Method to get TIFF code from tag name
  protected static int getDngTagCode(String name)
    {
    if (name.equalsIgnoreCase("DNGVersion")) return DNGVERSION;
    if (name.equalsIgnoreCase("DNGBackwardVersion")) return DNGBACKWARDVERSION;
    if (name.equalsIgnoreCase("UniqueCameraModel")) return UNIQUECAMERAMODEL;
    if (name.equalsIgnoreCase("LocalizedCameraModel")) return LOCALIZEDCAMERAMODEL;
    if (name.equalsIgnoreCase("CFAPlaneColor")) return CFAPLANECOLOR ;
    if (name.equalsIgnoreCase("CFALayout")) return CFALAYOUT;
    if (name.equalsIgnoreCase("LinearizationTable")) return LINEARIZATIONTABLE;
    if (name.equalsIgnoreCase("BlackLevelRepeatDim")) return BLACKLEVELREPEATDIM;
    if (name.equalsIgnoreCase("BlackLevel")) return BLACKLEVEL;
    if (name.equalsIgnoreCase("WhiteLevel")) return WHITELEVEL;
    if (name.equalsIgnoreCase("DefaultScale")) return DEFAULTSCALE;
    if (name.equalsIgnoreCase("DefaultCropOrigin")) return DEFAULTCROPORIGIN;
    if (name.equalsIgnoreCase("DefaultCropSize")) return DEFAULTCROPSIZE;
    if (name.equalsIgnoreCase("ColorMatrix1")) return COLORMATRIX1;
    if (name.equalsIgnoreCase("ColorMatrix2")) return COLORMATRIX2;
    if (name.equalsIgnoreCase("CameraCalibration1")) return CAMERACALIBRATION1;
    if (name.equalsIgnoreCase("CameraCalibration2")) return CAMERACALIBRATION2;
    if (name.equalsIgnoreCase("AnalogueBalance")) return ANALOGUEBALANCE;
    if (name.equalsIgnoreCase("AsShotNeutral")) return ASSHOTNEUTRAL;
    if (name.equalsIgnoreCase("BaselineExposure")) return BASELINEEXPOSURE;
    if (name.equalsIgnoreCase("BaselineNoise")) return BASELINENOISE;
    if (name.equalsIgnoreCase("BaselineSharpness")) return BASELINESHARPNESS;
    if (name.equalsIgnoreCase("BayerGreenSplit")) return BAYERGREENSPLIT;
    if (name.equalsIgnoreCase("LinearResponseLimit")) return LINEARRESPONSELIMIT;
    if (name.equalsIgnoreCase("CameraSerialNumber")) return CAMERASERIALNUMBER;
    if (name.equalsIgnoreCase("DNGLensinfo")) return DNGLENSINFO;
    if (name.equalsIgnoreCase("AntiAliasStrength")) return  ANTIALIASSTRENGTH;
    if (name.equalsIgnoreCase("ShadowScale")) return  SHADOWSCALE;
    if (name.equalsIgnoreCase("DNGPrivateData")) return DNGPRIVATEDATA;
    if (name.equalsIgnoreCase("CalibrationIlluminant1")) return CALIBRATIONILLUMINANT1;
    if (name.equalsIgnoreCase("CalibrationIlluminant2")) return CALIBRATIONILLUMINANT2;
    if (name.equalsIgnoreCase("BestQualityScale")) return BESTQUALITYSCALE;
    if (name.equalsIgnoreCase("RawDataUniqueID")) return RAWDATAUNIQUEID;
    if (name.equalsIgnoreCase("OriginalRawFileName")) return ORIGINALRAWFILENAME;
    if (name.equalsIgnoreCase("ActiveArea")) return ACTIVEAREA;
    if (name.equalsIgnoreCase("CameraCalibrationSig")) return CAMERACALIBRATIONSIG;
    if (name.equalsIgnoreCase("ProfileCalibrationSig")) return PROFILECALIBRATIONSIG;
    if (name.equalsIgnoreCase("ProfileName")) return PROFILENAME;
    if (name.equalsIgnoreCase("ProfileHueSatMapDims")) return PROFILEHUESATMAPDIMS;
    if (name.equalsIgnoreCase("ProfileHueSatMapData1")) return PROFILEHUESATMAPDATA1;
    if (name.equalsIgnoreCase("ProfileHueSatMapData2")) return PROFILEHUESATMAPDATA2;
    if (name.equalsIgnoreCase("ProfileToneCurve")) return PROFILETONECURVE;
    if (name.equalsIgnoreCase("ProfileEmbedPolicy")) return PROFILEEMBEDPOLICY;
    if (name.equalsIgnoreCase("ProfileCopyright")) return PROFILECOPYRIGHT;
    if (name.equalsIgnoreCase("ForwardMatrix1")) return FORWARDMATRIX1;
    if (name.equalsIgnoreCase("ForwardMatrix2")) return FORWARDMATRIX2;
    if (name.equalsIgnoreCase("PreviewApplicationName")) return PREVIEWAPPLICATIONNAME;
    if (name.equalsIgnoreCase("PreviewApplicationVersion")) return PREVIEWAPPLICATIONVERSION;
    if (name.equalsIgnoreCase("PreviewSettingsDigest")) return PREVIEWSETTINGSDIGEST;
    if (name.equalsIgnoreCase("PreviewColorSpace")) return PREVIEWCOLORSPACE;
    if (name.equalsIgnoreCase("PreviewDateTime")) return PREVIEWDATETIME;
    if (name.equalsIgnoreCase("ProfileLookTableDims")) return PROFILELOOKTABLEDIMS;
    if (name.equalsIgnoreCase("ProfileLookTableData")) return PROFILELOOKTABLEDATA;
    if (name.equalsIgnoreCase("NoiseProfile")) return NOISEPROFILE;
    if (name.equalsIgnoreCase("NewRawImageDigest")) return NEWRAWIMAGEDIGEST;
    return -1;
    }

  // Method to get TIFF code from tag name
  protected static int getGpsTagCode(String name)
    {
    if (name.equalsIgnoreCase("GPSVersionID")) return GPSVERSIONID;
    if (name.equalsIgnoreCase("GPSLatitudeRef")) return GPSLATITUDEREF;
    if (name.equalsIgnoreCase("GPSLatitude")) return GPSLATITUDE;
    if (name.equalsIgnoreCase("GPSLongitudeRef")) return GPSLONGITUDEREF;
    if (name.equalsIgnoreCase("GPSLongitude")) return GPSLONGITUDE;
    if (name.equalsIgnoreCase("GPSAltitudeRef")) return GPSALTITUDEREF;
    if (name.equalsIgnoreCase("GPSAltitude")) return GPSALTITUDE;
    if (name.equalsIgnoreCase("GPSTimeStamp")) return GPSTIMESTAMP;
    if (name.equalsIgnoreCase("GPSSatellites")) return GPSSATELLITES;
    if (name.equalsIgnoreCase("GPSStatus")) return GPSSTATUS;
    if (name.equalsIgnoreCase("GPSMeasureMode")) return GPSMEASUREMODE;
    if (name.equalsIgnoreCase("GPSDOP")) return GPSDOP;
    if (name.equalsIgnoreCase("GPSSpeedRef")) return GPSSPEEDREF;
    if (name.equalsIgnoreCase("GPSpeed")) return GPSSPEED;
    if (name.equalsIgnoreCase("GPSTrackRef")) return GPSTRACKREF;
    if (name.equalsIgnoreCase("GPSTrack")) return GPSTRACK;
    if (name.equalsIgnoreCase("GPSImgDirectionRef")) return GPSIMGDIRECTIONREF;
    if (name.equalsIgnoreCase("GPSImgDirection")) return GPSIMGDIRECTION;
    if (name.equalsIgnoreCase("GPSMapDatum")) return GPSMAPDATUM;
    if (name.equalsIgnoreCase("GPSDestLatitudeRef")) return GPSDESTLATITUDEREF;
    if (name.equalsIgnoreCase("GPSDestLatitude")) return GPSDESTLATITUDE;
    if (name.equalsIgnoreCase("GPSDestLongitudeRef")) return GPSDESTLONGITUDEREF;
    if (name.equalsIgnoreCase("GPSDestLongitude")) return GPSDESTLONGITUDE;
    if (name.equalsIgnoreCase("GPSDestBearingRef")) return GPSDESTBEARINGREF;
    if (name.equalsIgnoreCase("GPSDestBearing")) return GPSDESTBEARING;
    if (name.equalsIgnoreCase("GPSDestDistanceRef")) return GPSDESTDISTANCEREF;
    if (name.equalsIgnoreCase("GPSDestDistance")) return GPSDESTDISTANCE;
    if (name.equalsIgnoreCase("GPSProcessingMethod")) return GPSPROCESSINGMETHOD;
    if (name.equalsIgnoreCase("GPSAreaInformation")) return GPSAREAINFORMATION;
    if (name.equalsIgnoreCase("GPSDateStamp")) return GPSDATESTAMP;
    if (name.equalsIgnoreCase("GPSDifferential")) return GPSDIFFERENTIAL;
    return -1;
    }

  // Method to get TIFF code from tag name
  protected static int getInterTagCode(String name)
    {
    if (name.equalsIgnoreCase("INTEROPERABILITYINDEX")) return INTEROPERABILITYINDEX;
    if (name.equalsIgnoreCase("INTEROPERABILITYVERSION")) return INTEROPERABILITYVERSION;
    return -1;
    }

    
  // Method to get TIFF code from tag name
  public static int getTiffDataTypes(String name)
    {
    if (name.equalsIgnoreCase("BYTE")) return BYTE;
    if (name.equalsIgnoreCase("ASCII")) return ASCII;
    if (name.equalsIgnoreCase("SHORT")) return SHORT;
    if (name.equalsIgnoreCase("LONG")) return LONG;
    if (name.equalsIgnoreCase("RATIONAL")) return RATIONAL;
    if (name.equalsIgnoreCase("SBYTE")) return SBYTE;
    if (name.equalsIgnoreCase("UNDEFINED")) return UNDEFINED;
    if (name.equalsIgnoreCase("SSHORT")) return SSHORT;
    if (name.equalsIgnoreCase("SLONG")) return SLONG;
    if (name.equalsIgnoreCase("SRATIONAL")) return SRATIONAL;
    if (name.equalsIgnoreCase("FLOAT")) return FLOAT;
    if (name.equalsIgnoreCase("DOUBLE")) return DOUBLE;
    if (name.equalsIgnoreCase("IFD")) return IFD;
    if (name.equalsIgnoreCase("LONG8")) return LONG8;
    if (name.equalsIgnoreCase("SLONG8")) return SLONG8;
    if (name.equalsIgnoreCase("IFD8")) return IFD8;
    return -1;
    }
  
  // Method to get TIFF code from tag name
  protected static int getTiffCompressionTypes(String name)
    {
    if (name.equalsIgnoreCase("None")) return NONE;
    if (name.equalsIgnoreCase("CCITT")) return CCITT;
    if (name.equalsIgnoreCase("T4")) return T4;
    if (name.equalsIgnoreCase("T6")) return T6;
    if (name.equalsIgnoreCase("LZW")) return LZW;
    if (name.equalsIgnoreCase("JPEG")) return JPEG;
    if (name.equalsIgnoreCase("PackBits")) return PACKBITS;
    return -1;
    }

  
  // Method to get TIFF code from tag name
  protected static int getTiffSampleFormatTypes(String name)
    {
    if (name.equalsIgnoreCase("SF_INTEGER") || name.equalsIgnoreCase("Integer")) return SF_INTEGER;
    if (name.equalsIgnoreCase("SF_SINTEGER") || name.equalsIgnoreCase("SInteger")) return SF_SINTEGER;
    if (name.equalsIgnoreCase("SF_IEEEFLOATING") || name.equalsIgnoreCase("IEEEFloating")) return SF_IEEEFLOATING;
    if (name.equalsIgnoreCase("SF_UNDEFINED") || name.equalsIgnoreCase("Undefined")) return SF_UNDEFINED;
    return -1;
    }
 
  // Method to get TIFF code from tag name
  protected static int getTiffPhotometricInterpretation(String name)
    {
    if (name.equalsIgnoreCase("WhiteIsZero")) return WHITEISZERO;
    if (name.equalsIgnoreCase("BlackIsZero")) return BLACKISZERO;
    if (name.equalsIgnoreCase("RGB")) return RGB;
    if (name.equalsIgnoreCase("PaletteColor")) return PALETTECOLOR;
    if (name.equalsIgnoreCase("TransparencyMask")) return TRANSPARENCYMASK;
    if (name.equalsIgnoreCase("PhotometricCIELAB")) return PHOTOMETRICCIELAB;
    if (name.equalsIgnoreCase("PhotometricCFA")) return PHOTOMETRICCFA;
    if (name.equalsIgnoreCase("PhotometricLinearRaw")) return PHOTOMETRICLINEARRAW;
    return -1;
    }
  
  // Method to get TIFF code from tag name
  protected static int getTiffResolutionUnits(String name)
    {
    if (name.equalsIgnoreCase("UNDEFINED")) return RU_UNDEFINED;
    if (name.equalsIgnoreCase("Inches")) return RU_INCHES;
    if (name.equalsIgnoreCase("Centimeters")) return RU_CENTIMETERS;
    return -1;
    }
  
  // Method to get TIFF code from tag name
  protected static int getTiffPlanarConfiguration(String name)
    {
    if (name.equalsIgnoreCase("Chunky")) return CHUNKY;
    if (name.equalsIgnoreCase("Planar")) return PLANAR;
    return -1;
    }

  // Method to get TIFF code from tag name
  protected static int getTiffNewSubFileType(String name)
    {
    if (name.equalsIgnoreCase("Full")) return FULL;
    if (name.equalsIgnoreCase("Reduced")) return REDUCED;
    if (name.equalsIgnoreCase("MultipageFull")) return MULTIPAGEFULL;
    if (name.equalsIgnoreCase("MultipageReduced")) return MULTIPAGEREDUCED;
    if (name.equalsIgnoreCase("TransparencyFullSingle")) return TRANSPARENCYFULLSINGLE;
    if (name.equalsIgnoreCase("TransparencyReducedSingle")) return TRANSPARENCYREDUCEDSINGLE;
    if (name.equalsIgnoreCase("TransparencyFullMultipage")) return TRANSPARENCYFULLMULTIPAGE;
    if (name.equalsIgnoreCase("TransparencyReducedMultipage")) return TRANSPARENCYREDUCEDMULTIPAGE;
    if (name.equalsIgnoreCase("AlternatePreview")) return ALTERNATEPREVIEW;
    return -1;
    }

  // Method to return any tag name
  public static String getTagName(int tag)
    {
    String result = getTiffTagName(tag);
    if (result.equals("UNKNOWN")) result = getExifTagName(tag);
    if (result.equals("UNKNOWN")) result = getDngTagName(tag);
    if (result.equals("UNKNOWN")) result = getGpsTagName(tag);
    if (result.equals("UNKNOWN")) result = getInterTagName(tag);
    return result;
    }
  
  // Method to return tiff tag name for tiff tag code
  protected static String getTiffTagName(int tag)
    {
    switch (tag)
      {
      case NEWSUBFILETYPE: return "NewSubfileType";
      case SUBFILETYPE: return "SubfileType";
      case IMAGEWIDTH: return "ImageWidth";
      case IMAGELENGTH: return "ImageLength";
      case BITSPERSAMPLE: return "BitsPerSample";
      case COMPRESSION: return "Compression";
      case PHOTOMETRICINTERPRETATION: return "PhotometricInterpretation";
      case THRESHOLDING: return "Thresholding";
      case CELLWIDTH: return "CellWidth";
      case CELLLENGTH: return "CellLength";
      case FILLORDER: return "FillOrder";
      case DOCUMENTNAME: return "DocumentName";
      case IMAGEDESCRIPTION: return "ImageDescription";
      case MAKE: return "Make";
      case MODEL: return "Model";
      case STRIPOFFSETS: return "StripOffsets";
      case ORIENTATION: return "Orientation";
      case SAMPLESPERPIXEL: return "SamplesPerPixel";
      case ROWSPERSTRIP: return "RowsPerStrip";
      case STRIPBYTECOUNTS: return "StripByteCounts";
      case MINSAMPLEVALUE: return "MinSampleValue";
      case MAXSAMPLEVALUE: return "MaxSampleValue";
      case XRESOLUTION: return "XResolution";
      case YRESOLUTION: return "YResolution";
      case PLANARCONFIGURATION: return "PlanarConfiguration";
      case PAGENAME: return "PageName";
      case XPOSITION: return "XPosition";
      case YPOSITION: return "YPosition";
      case FREEOFFSETS: return "FreeOffsets";
      case FREEBYTECOUNTS: return "FreeByteCounts";
      case GRAYRESPONSEUNIT: return "GrayResponseUnit";
      case GRAYRESPONSECURVE: return "GrayResponseCurve";
      case T4OPTIONS: return "T4Options";
      case T6OPTIONS: return "T6Options";
      case RESOLUTIONUNIT: return "ResolutionUnit";
      case PAGENUMBER: return "PageNumber";
      case TRANSFERFUNCTION: return "TransferFunction";
      case SOFTWARE: return "Software";
      case DATETIME: return "DateTime";
      case ARTIST: return "Artist";
      case HOSTCOMPUTER: return "HostComputer";
      case PREDICTOR: return "Predictor";
      case WHITEPOINT: return "WhitePoint";
      case PRIMARYCHROMATICITIES: return "PrimaryChromaticities";
      case COLORMAP: return "ColorMap";
      case HALFTONEHINTS: return "HalftoneHints";
      case TILEWIDTH: return "TileWidth";
      case TILELENGTH: return "TileLength";
      case TILEOFFSETS: return "TileOffsets";
      case TILEBYTECOUNTS: return "TileByteCounts";
      case SUBIFDS: return "SubIFDS";
      case INKSET: return "InkSet";
      case INKNAMES: return "InkNames";
      case NUMBEROFINKS: return "NumberOfInks";
      case DOTRANGE: return "DotRange";
      case TARGETPRINTER: return "TargetPrinter";
      case EXTRASAMPLES: return "ExtraSamples";
      case SAMPLEFORMAT: return "SampleFormat";
      case SMINSAMPLEVALUE: return "SMinSampleValue";
      case SMAXSAMPLEVALUE: return "SMaxSampleValue";
      case TRANSFERRANGE: return "TransferRange";
      case INDEXED: return "Indexed";
      case JPEGPROC: return "JPEGProc";
      case JPEGINTERCHANGEFORMAT: return "JPEGInterchangeFormat";
      case JPEGINTERCHANGEFORMATLENGTH: return "JPEGInterchangeFormatLength";
      case JPEGRESTARTINTERVAL: return "JPEGRestartInterval";
      case JPEGLOSSLESSPREDICTORS: return "JPEGLosslessPredictors";
      case JPEGPOINTTRANSFORMS: return "JPEGPointTransforms";
      case JPEGQTABLES: return "JPEGQTables";
      case JPEGDCTABLES: return "JPEGDCTables";
      case JPEGACTABLES: return "JPEGACTables";
      case YCBCRCOEFFICIENTS: return "YCbCrCoefficients";
      case YCBCRSUBSAMPLING: return "YCbCrSubSampling";
      case YCBCRPOSITIONING: return "YCbCrPositioning";
      case REFERENCEBLACKWHITE: return "ReferenceBlackWhite";
      case XMP: return "XMP";
      case COPYRIGHT: return "Copyright";
      }
    return "UNKNOWN";
    }

  // Method to return EXIF tag name for tiff tag code
  protected static String getExifTagName(int tag)
    {
    switch (tag)
      {
      case CFAREPEATPATTERNDIM: return "CFARepeatPatternDim";
      case CFAPATTERN: return "CFAPattern";
      case EXPOSURETIME: return "ExposureTime";
      case FNUMBER: return "FNumber";
      case IPTC: return "IPTC";
      case PHOTOSHOP: return "Photoshop";
      case EXIFIFD: return "ExifIFD";
      case ICCPROFILE: return "ICCProfile";
      case EXPOSUREPROGRAM: return "ExposureProgram";
      case SPECTRALSENSITIVITY: return "SpectralSensitivity";
      case GPSIFD: return "GPSIFD";
      case ISOSPEEDRATINGS: return "ISOSpeedRatings";
      case EXIFVERSION: return "ExifVersion";
      case RECOMMENDEDEXPOSUREINDEX: return "RecommendedExposureIndex";
      case INTEROPERABILITYIFD: return "InteroperabilityIFD";
      case SENSITIVITYTYPE: return "SensitivityType";
      case DATETIMEORIGINAL: return "DateTimeOriginal";
      case DATETIMEDIGITIZED: return "DateTimeDigitized";
      case COMPONENTSCONFIGURATION: return "ComponentsConfiguration";
      case SHUTTERSPEEDVALUE: return "ShutterSpeedValue";
      case APERTUREVALUE: return "ApertureValue";
      case BRIGHTNESSVALUE: return "BrightnessValue";
      case EXPOSURECOMPENSATION: return "ExposureCompensation";
      case MAXAPERTUREVALUE: return "MaxApertureValue";
      case SUBJECTDISTANCE: return "SubjectDistance";
      case METERINGMODE: return "MeteringMode";
      case LIGHTSOURCE: return "LightSource";
      case FLASH: return "Flash";
      case FOCALLENGTH: return "FocalLength";
      case FOCALPLANEXRESOLUTION: return "FocalPlaneXResolution";
      case FOCALPLANEYRESOLUTION: return "FocalPlaneYResolution";
      case FOCALPLANERESOLUTIONUNIT: return "FocalPlaneResolutionUnit";
      case IMAGENUMBER: return "ImageNumber";
      case IMAGEHISTORY: return "ImageHistory";
      case SUBSECTIME: return "SubSecTime";
      case SUBSECTIMEORIGINAL: return "SubSecTimeOriginal";
      case SUBSECTIMEDIGITIZED: return "SubSecTimeDigitized";
      case FLASHPIXVERSION: return "FlashPixVersion";
      case COLORSPACE: return "ColorSpace";
      case PIXELXDIMENSION: return "PixelXDimension";
      case PIXELYDIMENSION: return "PixelYDimension";
      case FOCALPLANEXRESOLUTIONPRIVATE: return "FocalPlaneXResolutionPrivate";
      case FOCALPLANEYRESOLUTIONPRIVATE: return "FocalPlaneYResolutionPrivate";
      case FOCALPLANERESOLUTIONUNITPRIVATE: return "FocalPlaneResolutionUnitPrivate";
      case SENSINGMETHOD: return "SensingMethod";
      case FILESOURCE: return "FileSource";
      case SCENETYPE: return "SceneType";
      case CFAPATTERN2: return "CFAPattern2";
      case CUSTOMRENDERED: return "CustomRendered";
      case EXPOSUREMODE: return "ExposureMode";
      case WHITEBALANCE: return "WhiteBalance";
      case DIGITALZOOMRATIO: return "DigitalZoomRatio";
      case FOCALLENGTHIN35MMFORMAT: return "FocalLengthIn35mmFormat";
      case SCENECAPTURETYPE: return "SceneCaptureType";
      case GAINCONTROL: return "GainControl";
      case CONTRAST: return "Contrast";
      case SATURATION: return "Saturation";
      case SHARPNESS: return "Sharpness";
      case SUBJECTDISTANERANGE: return "SubjectDistanceRange";
      case IMAGEUNIQUEID: return "ImageUniqueID";
      case SERIALNUMBER: return "SerialNumber";
      case LENSINFO: return "LensInfo";
      case LENSMODEL: return "LensModel";
      case LENSSERIALNUMBERL: return "LensSerialNumber";
      }
    return "UNKNOWN";
    }

  // Method to return EXIF tag name for tiff tag code
  protected static String getDngTagName(int tag)
    {
    switch (tag)
      {
      case DNGVERSION: return "DNGVersion";
      case DNGBACKWARDVERSION: return "DNGBackwardVersion";
      case UNIQUECAMERAMODEL: return "UniqueCameraModel";
      case LOCALIZEDCAMERAMODEL: return "LocalizedCameraModel";
      case CFAPLANECOLOR: return "CFAPlaneColor";
      case CFALAYOUT: return "CFALayout";
      case LINEARIZATIONTABLE: return "LinearizationTable";
      case BLACKLEVELREPEATDIM: return "BlackLevelRepeatDim";
      case BLACKLEVEL: return "BlackLevel";
      case WHITELEVEL: return "WhiteLevel";
      case DEFAULTSCALE: return "DefaultScale";
      case DEFAULTCROPORIGIN: return "DefaultCropOrigin";
      case DEFAULTCROPSIZE: return "DefaultCropSize";
      case COLORMATRIX1: return "ColorMatrix1";
      case COLORMATRIX2: return "ColorMatrix2";
      case CAMERACALIBRATION1: return "CameraCalibration1";
      case CAMERACALIBRATION2: return "CameraCalibration2";
      case ANALOGUEBALANCE: return "AnalogueBalance";
      case ASSHOTNEUTRAL: return "AsShotNeutral";
      case BASELINEEXPOSURE: return "BaselineExposure";
      case BASELINENOISE: return "BaselineNoise";
      case BASELINESHARPNESS: return "BaselineSharpness";
      case BAYERGREENSPLIT: return "BayerGreenSplit";
      case LINEARRESPONSELIMIT: return "LinearResponseLimit";
      case CAMERASERIALNUMBER: return "CameraSerialNumber";
      case DNGLENSINFO: return "DNGLensInfo";
      case ANTIALIASSTRENGTH: return "AntiAliasStrength";
      case SHADOWSCALE: return "ShadowScale";
      case DNGPRIVATEDATA: return "DNGPrivateData";
      case CALIBRATIONILLUMINANT1: return "CalibrationIlluminant1";
      case CALIBRATIONILLUMINANT2: return "CalibrationIlluminant2";
      case BESTQUALITYSCALE: return "BestQualityScale";
      case RAWDATAUNIQUEID: return "RawDataUniqueID";
      case ORIGINALRAWFILENAME: return "OriginalRawFileName";
      case ACTIVEAREA: return "ActiveArea";
      case CAMERACALIBRATIONSIG: return "CameraCalibrationSig";
      case PROFILECALIBRATIONSIG: return "ProfileCalibrationSig";
      case PROFILENAME: return "ProfileName";
      case PROFILEHUESATMAPDIMS: return "ProfileHueSatMapDims";
      case PROFILEHUESATMAPDATA1: return "ProfileHueSatMapData1";
      case PROFILEHUESATMAPDATA2: return "ProfileHueSatMapData2";
      case PROFILETONECURVE: return "ProfileToneCurve";
      case PROFILEEMBEDPOLICY: return "ProfileEmbedPolicy";
      case PROFILECOPYRIGHT: return "ProfileCopyright";
      case FORWARDMATRIX1: return "ForwardMatrix1";
      case FORWARDMATRIX2: return "ForwardMatrix2";
      case PREVIEWAPPLICATIONNAME: return "PreviewApplicationName";
      case PREVIEWAPPLICATIONVERSION: return "PreviewApplicationVersion";
      case PREVIEWSETTINGSDIGEST: return "PreviewSettingsDigest";
      case PREVIEWCOLORSPACE: return "PreviewColorSpace";
      case PREVIEWDATETIME: return "PreviewDateTime";
      case PROFILELOOKTABLEDIMS: return "ProfileLookTableDims";
      case PROFILELOOKTABLEDATA: return "ProfileLookTableData";
      case NOISEPROFILE: return "NoiseProfile";
      case NEWRAWIMAGEDIGEST: return "NewRawImageDigest";
      }
    return "UNKNOWN";
    }
  
  // Method to return EXIF tag name for tiff tag code
  protected static String getGpsTagName(int tag)
    {
    switch (tag)
      {
      case GPSVERSIONID: return "GPSVersionID";
      case GPSLATITUDEREF: return "GPSLatitudeRef";
      case GPSLATITUDE: return "GPSLatitude";
      case GPSLONGITUDEREF: return "GPSLongitudeRef";
      case GPSLONGITUDE: return "GPSLongitude";
      case GPSALTITUDEREF: return "GPSAltitudeRef";
      case GPSALTITUDE: return "GPSAltitude";
      case GPSTIMESTAMP: return "GPSTimeStamp";
      case GPSSATELLITES: return "GPSSatellites";
      case GPSSTATUS: return "GPSStatus";
      case GPSMEASUREMODE: return "GPSMeasureMode";
      case GPSDOP: return "GPSDOP";
      case GPSSPEEDREF: return "GPSSpeedRef";
      case GPSSPEED: return "GPSpeed";
      case GPSTRACKREF: return "GPSTrackRef";
      case GPSTRACK: return "GPSTrack";
      case GPSIMGDIRECTIONREF: return "GPSImgDirectionRef";
      case GPSIMGDIRECTION: return "GPSImgDirection";
      case GPSMAPDATUM: return "GPSMapDatum";
      case GPSDESTLATITUDEREF: return "GPSDestLatitudeRef";
      case GPSDESTLATITUDE: return "GPSDestLatitude";
      case GPSDESTLONGITUDEREF: return "GPSDestLongitudeRef";
      case GPSDESTLONGITUDE: return "GPSDestLongitude";
      case GPSDESTBEARINGREF: return "GPSDestBearingRef";
      case GPSDESTBEARING: return "GPSDestBearing";
      case GPSDESTDISTANCEREF: return "GPSDestDistanceRef";
      case GPSDESTDISTANCE: return "GPSDestDistance";
      case GPSPROCESSINGMETHOD: return "GPSProcessingMethod";
      case GPSAREAINFORMATION: return "GPSAreaInformation";
      case GPSDATESTAMP: return "GPSDateStamp";
      case GPSDIFFERENTIAL: return "GPSDifferential";
      }
    return "UNKNOWN";
    }
    
  // Method to return EXIF tag name for tiff tag code
  protected static String getInterTagName(int tag)
    {
    switch (tag)
      {
      case INTEROPERABILITYINDEX: return "InteroperabilityIndex";
      case INTEROPERABILITYVERSION: return "InteroperabilityVersion";
      }
    return "UNKNOWN";
    }
    
  // Method to get tag location
  public static String getTagLocation(String name)
    {
    String result = getTiffTagLocation(name);
    if (result.equals("UNKNOWN")) result = getExifTagLocation(name);
    if (result.equals("UNKNOWN")) result = getDngTagLocation(name);
    if (result.equals("UNKNOWN")) result = getIptcTagLocation(name);
    if (result.equals("UNKNOWN")) result = getGpsTagLocation(name);
    if (result.equals("UNKNOWN")) result = getInterTagLocation(name);
    return result;
    }
  
  // Method to get location from TIFF tag name
  protected static String getTiffTagLocation(String name)
    {
    String tiff = "TIFF", exif = "EXIF", iptc = "IPTC", dng = "DNG";
    if (name.equalsIgnoreCase("NewSubfileType")) return tiff;
    if (name.equalsIgnoreCase("SubfileType")) return tiff;
    if (name.equalsIgnoreCase("ImageWidth")) return tiff;
    if (name.equalsIgnoreCase("ImageLength")) return tiff;
    if (name.equalsIgnoreCase("BitsPerSample")) return tiff;
    if (name.equalsIgnoreCase("Compression")) return tiff;
    if (name.equalsIgnoreCase("PhotometricInterpretation")) return tiff;
    if (name.equalsIgnoreCase("Thresholding")) return tiff;
    if (name.equalsIgnoreCase("CellWidth")) return tiff;
    if (name.equalsIgnoreCase("CellLength")) return tiff;
    if (name.equalsIgnoreCase("FillOrder")) return tiff;
    if (name.equalsIgnoreCase("DocumentName")) return tiff;
    if (name.equalsIgnoreCase("ImageDescription")) return tiff;
    if (name.equalsIgnoreCase("Make")) return tiff;
    if (name.equalsIgnoreCase("Model")) return tiff;
    if (name.equalsIgnoreCase("StripOffsets")) return tiff;
    if (name.equalsIgnoreCase("Orientation")) return tiff;
    if (name.equalsIgnoreCase("SamplesPerPixel")) return tiff;
    if (name.equalsIgnoreCase("RowsPerStrip")) return tiff;
    if (name.equalsIgnoreCase("StripByteCounts")) return tiff;
    if (name.equalsIgnoreCase("MinSampleValue")) return tiff;
    if (name.equalsIgnoreCase("MaxSampleValue")) return tiff;
    if (name.equalsIgnoreCase("XResolution")) return tiff;
    if (name.equalsIgnoreCase("YResolution")) return tiff;
    if (name.equalsIgnoreCase("PlanarConfiguration")) return tiff;
    if (name.equalsIgnoreCase("PageName")) return tiff;
    if (name.equalsIgnoreCase("XPosition")) return tiff;
    if (name.equalsIgnoreCase("YPosition")) return tiff;
    if (name.equalsIgnoreCase("FreeOffsets")) return tiff;
    if (name.equalsIgnoreCase("FreeByteCounts")) return tiff;
    if (name.equalsIgnoreCase("GrayResponseUnit")) return tiff;
    if (name.equalsIgnoreCase("GrayResponseCurve")) return tiff;
    if (name.equalsIgnoreCase("T4Options")) return tiff;
    if (name.equalsIgnoreCase("T6Options")) return tiff;
    if (name.equalsIgnoreCase("ResolutionUnit")) return tiff;
    if (name.equalsIgnoreCase("PageNumber")) return tiff;
    if (name.equalsIgnoreCase("TransferFunction")) return tiff;
    if (name.equalsIgnoreCase("Software")) return tiff;
    if (name.equalsIgnoreCase("DateTime")) return tiff;
    if (name.equalsIgnoreCase("Artist")) return tiff;
    if (name.equalsIgnoreCase("HostComputer")) return tiff;
    if (name.equalsIgnoreCase("Predictor")) return tiff;
    if (name.equalsIgnoreCase("WhitePoint")) return tiff;
    if (name.equalsIgnoreCase("PrimaryChromaticities")) return tiff;
    if (name.equalsIgnoreCase("ColorMap")) return tiff;
    if (name.equalsIgnoreCase("HalftoneHints")) return tiff;
    if (name.equalsIgnoreCase("TileWidth")) return tiff;
    if (name.equalsIgnoreCase("TileLength")) return tiff;
    if (name.equalsIgnoreCase("TileOffsets")) return tiff;
    if (name.equalsIgnoreCase("TileByteCounts")) return tiff;
    if (name.equalsIgnoreCase("SubIFDS")) return tiff;
    if (name.equalsIgnoreCase("InkSet")) return tiff;
    if (name.equalsIgnoreCase("InkNames")) return tiff;
    if (name.equalsIgnoreCase("NumberOfInks")) return tiff;
    if (name.equalsIgnoreCase("DotRange")) return tiff;
    if (name.equalsIgnoreCase("TargetPrinter")) return tiff;
    if (name.equalsIgnoreCase("ExtraSamples")) return tiff;
    if (name.equalsIgnoreCase("SampleFormat")) return tiff;
    if (name.equalsIgnoreCase("SMinSampleValue")) return tiff;
    if (name.equalsIgnoreCase("SMaxSampleValue")) return tiff;
    if (name.equalsIgnoreCase("TransferRange")) return tiff;
    if (name.equalsIgnoreCase("JPEGProc")) return tiff;
    if (name.equalsIgnoreCase("JPEGInterchangeFormat")) return tiff;
    if (name.equalsIgnoreCase("JPEGInterchangeFormatLngth")) return tiff;
    if (name.equalsIgnoreCase("JPEGRestartInterval")) return tiff;
    if (name.equalsIgnoreCase("JPEGLosslessPredictors")) return tiff;
    if (name.equalsIgnoreCase("JPEGPointTransforms")) return tiff;
    if (name.equalsIgnoreCase("JPEGQTables")) return tiff;
    if (name.equalsIgnoreCase("JPEGDCTables")) return tiff;
    if (name.equalsIgnoreCase("JPEGACTables")) return tiff;
    if (name.equalsIgnoreCase("YCbCrCoefficients")) return tiff;
    if (name.equalsIgnoreCase("YCbCrSubSampling")) return tiff;
    if (name.equalsIgnoreCase("YCbCrPositioning")) return tiff;
    if (name.equalsIgnoreCase("ReferenceBlackWhite")) return tiff;
    if (name.equalsIgnoreCase("XMP")) return tiff;
    if (name.equalsIgnoreCase("Copyright")) return tiff;
    return "UNKNOWN";
    }

  
  // Method to get location from EXIF tag name
  protected static String getExifTagLocation(String name)
    {
    String tiff = "TIFF", exif = "EXIF", iptc = "IPTC", dng = "DNG";
    if (name.equalsIgnoreCase("CFARepeatPatternDim")) return exif;
    if (name.equalsIgnoreCase("CFAPattern")) return exif;
    if (name.equalsIgnoreCase("ExposureTime")) return exif;
    if (name.equalsIgnoreCase("FNumber")) return exif;
    if (name.equalsIgnoreCase("IPTC")) return tiff;
    if (name.equalsIgnoreCase("Photoshop")) return tiff;
    if (name.equalsIgnoreCase("ExifIFD")) return tiff;
    if (name.equalsIgnoreCase("ICC Profile")) return tiff;
    if (name.equalsIgnoreCase("ExposureProgram")) return exif;
    if (name.equalsIgnoreCase("SpectralSensitivity")) return exif;
    if (name.equalsIgnoreCase("GPSIFD")) return tiff;
    if (name.equalsIgnoreCase("ISOSpeedRatings")) return exif;
    if (name.equalsIgnoreCase("ExifVersion")) return exif;
    if (name.equalsIgnoreCase("RecommendedExposureIndex")) return exif;
    if (name.equalsIgnoreCase("InteroperabilityIFD")) return exif;
    if (name.equalsIgnoreCase("SensitivityType")) return exif;
    if (name.equalsIgnoreCase("DateTimeOriginal")) return exif;
    if (name.equalsIgnoreCase("DateTimeDigitized")) return exif;
    if (name.equalsIgnoreCase("ComponentsConfiguration")) return exif;
    if (name.equalsIgnoreCase("ShutterSpeedValue")) return exif;
    if (name.equalsIgnoreCase("ApertureValue")) return exif;
    if (name.equalsIgnoreCase("BrightnessValue")) return exif;
    if (name.equalsIgnoreCase("ExposureCompensation")) return exif;
    if (name.equalsIgnoreCase("MaxApertureValue")) return exif;
    if (name.equalsIgnoreCase("SubjectDistance")) return exif;
    if (name.equalsIgnoreCase("MeteringMode")) return exif;
    if (name.equalsIgnoreCase("LightSource")) return exif;
    if (name.equalsIgnoreCase("Flash")) return exif;
    if (name.equalsIgnoreCase("FocalLength")) return exif;
    if (name.equalsIgnoreCase("FocalPlaneXResolution")) return exif;
    if (name.equalsIgnoreCase("FocalPlaneYResolution")) return exif;
    if (name.equalsIgnoreCase("FocalPlaneResolutionUnit")) return exif;
    if (name.equalsIgnoreCase("ImageNumber")) return exif;
    if (name.equalsIgnoreCase("ImageHistory")) return exif;
    if (name.equalsIgnoreCase("SubSecTime")) return exif;
    if (name.equalsIgnoreCase("SubSecTimeOriginal")) return exif;
    if (name.equalsIgnoreCase("SubSecTimeDigitized")) return exif;
    if (name.equalsIgnoreCase("FlashPixVersion")) return exif;
    if (name.equalsIgnoreCase("ColorSpace")) return exif;
    if (name.equalsIgnoreCase("PixelXDimension")) return exif;
    if (name.equalsIgnoreCase("PixelYDimension")) return exif;
    if (name.equalsIgnoreCase("FocalPlaneXResolutionPrivate")) return exif;
    if (name.equalsIgnoreCase("FocalPlaneYResolutionPrivate")) return exif;
    if (name.equalsIgnoreCase("FocalPlaneResolutionUnitPrivate")) return exif;
    if (name.equalsIgnoreCase("SensingMethod")) return exif;
    if (name.equalsIgnoreCase("FileSource")) return exif;
    if (name.equalsIgnoreCase("SceneType")) return exif;
    if (name.equalsIgnoreCase("CFAPattern2")) return exif;
    if (name.equalsIgnoreCase("CustomRendered")) return exif;
    if (name.equalsIgnoreCase("ExposureMode")) return exif;
    if (name.equalsIgnoreCase("WhiteBalance")) return exif;
    if (name.equalsIgnoreCase("DigitalZoomRatio")) return exif;
    if (name.equalsIgnoreCase("FocalLengthIn35mmFormat")) return exif;
    if (name.equalsIgnoreCase("SceneCaptureType")) return exif;
    if (name.equalsIgnoreCase("GainControl")) return exif;
    if (name.equalsIgnoreCase("Contrast")) return exif;
    if (name.equalsIgnoreCase("Saturation")) return exif;
    if (name.equalsIgnoreCase("Sharpness")) return exif;
    if (name.equalsIgnoreCase("SubjectDistanceRange")) return exif;
    if (name.equalsIgnoreCase("ImageUniqueID")) return exif;
    if (name.equalsIgnoreCase("SerialNumber")) return exif;
    if (name.equalsIgnoreCase("LensInfo")) return exif;
    if (name.equalsIgnoreCase("LensModel")) return exif;
    if (name.equalsIgnoreCase("LensSerialNumber")) return exif;
    return "UNKNOWN";
    }

  // Method to get location from DNG tag name
  protected static String getDngTagLocation(String name)
    {
    String tiff = "TIFF", exif = "EXIF", iptc = "IPTC", dng = "DNG";
    if (name.equalsIgnoreCase("DNGVersion")) return dng;
    if (name.equalsIgnoreCase("DNGBackwardVersion")) return dng;
    if (name.equalsIgnoreCase("UniqueCameraModel")) return dng;
    if (name.equalsIgnoreCase("LocalizedCameraModel")) return dng;
    if (name.equalsIgnoreCase("CFAPlaneColor")) return dng;
    if (name.equalsIgnoreCase("CFALayout")) return dng;
    if (name.equalsIgnoreCase("LinearlizationTable")) return dng;
    if (name.equalsIgnoreCase("BlackLevelRepeatDim")) return dng;
    if (name.equalsIgnoreCase("BlackLevel")) return dng;
    if (name.equalsIgnoreCase("WhiteLevel")) return dng;
    if (name.equalsIgnoreCase("DefaultScake")) return dng;
    if (name.equalsIgnoreCase("DefaultCropOrigin")) return dng;
    if (name.equalsIgnoreCase("DefaultCropSize")) return dng;
    if (name.equalsIgnoreCase("ColorMatrix1")) return dng;
    if (name.equalsIgnoreCase("ColorMatrix2")) return dng;
    if (name.equalsIgnoreCase("CameraCalibration1")) return dng;
    if (name.equalsIgnoreCase("CameraCalibration2")) return dng;
    if (name.equalsIgnoreCase("AnalogueBalance")) return dng;
    if (name.equalsIgnoreCase("AsShotNeutral")) return dng;
    if (name.equalsIgnoreCase("BaselineExposure")) return dng;
    if (name.equalsIgnoreCase("BaselineNoise")) return dng;
    if (name.equalsIgnoreCase("BaselineSharpness")) return dng;
    if (name.equalsIgnoreCase("BayerGreenSplit")) return dng;
    if (name.equalsIgnoreCase("LinearResponseLimit")) return dng;
    if (name.equalsIgnoreCase("CameraSerialNumber")) return dng;
    if (name.equalsIgnoreCase("DNGLensInfo")) return dng;
    if (name.equalsIgnoreCase("AntiAliasStrength")) return dng;
    if (name.equalsIgnoreCase("ShadowScale")) return dng;
    if (name.equalsIgnoreCase("DNGPrivateData")) return dng;
    if (name.equalsIgnoreCase("CalibrationIlluminant1")) return dng;
    if (name.equalsIgnoreCase("CalibrationIlluminant2")) return dng;
    if (name.equalsIgnoreCase("BestQualityScale")) return dng;
    if (name.equalsIgnoreCase("RawDataUniqueID")) return dng;
    if (name.equalsIgnoreCase("OriginalRawFileName")) return dng;
    if (name.equalsIgnoreCase("ActiveArea")) return dng;
    if (name.equalsIgnoreCase("CameraCalibrationSig")) return dng;
    if (name.equalsIgnoreCase("ProfileCalibrationSig")) return dng;
    if (name.equalsIgnoreCase("ProfileName")) return dng;
    if (name.equalsIgnoreCase("ProfileHueSatMapDims")) return dng;
    if (name.equalsIgnoreCase("ProfileHueSatMapData1")) return dng;
    if (name.equalsIgnoreCase("ProfileHueSatMapData2")) return dng;
    if (name.equalsIgnoreCase("ProfileToneCurve")) return dng;
    if (name.equalsIgnoreCase("ProfileEmbedPolicy")) return dng;
    if (name.equalsIgnoreCase("ProfileCopyrigh")) return dng;
    if (name.equalsIgnoreCase("ForwardMatrix1")) return dng;
    if (name.equalsIgnoreCase("ForwardMatrix2")) return dng;
    if (name.equalsIgnoreCase("PreviewApplicationName")) return dng;
    if (name.equalsIgnoreCase("PreviewApplicationVersion")) return dng;
    if (name.equalsIgnoreCase("PreviewSettingDigest")) return dng;
    if (name.equalsIgnoreCase("PreviewColorSpace")) return dng;
    if (name.equalsIgnoreCase("PreviewDateTime")) return dng;
    if (name.equalsIgnoreCase("ProfileLookTableDims")) return dng;
    if (name.equalsIgnoreCase("ProfileLookTableData")) return dng;
    if (name.equalsIgnoreCase("NoiseProfile")) return dng;
    if (name.equalsIgnoreCase("NewRawImageDigest")) return dng;
    return "UNKNOWN";
    }

  // Method to get location from IPTC tag name
  protected static String getIptcTagLocation(String name)
    {
    String tiff = "TIFF", exif = "EXIF", iptc = "IPTC", dng = "DNG";
    if (name.equalsIgnoreCase("ObjectName")) return iptc;
    if (name.equalsIgnoreCase("Keywords")) return iptc;
    if (name.equalsIgnoreCase("DateCreated")) return iptc;
    if (name.equalsIgnoreCase("Country")) return iptc;
    if (name.equalsIgnoreCase("OriginalTransmission")) return iptc;
    if (name.equalsIgnoreCase("Headline")) return iptc;
    if (name.equalsIgnoreCase("Credit")) return iptc;
    if (name.equalsIgnoreCase("Source")) return iptc;
    if (name.equalsIgnoreCase("CopyrightNotice")) return iptc;
    if (name.equalsIgnoreCase("Caption")) return iptc;
    return "UNKNOWN";
    }

  // Method to get location from GPSInfo tag name
  protected static String getGpsTagLocation(String name)
    {
    String tiff = "TIFF", exif = "EXIF", iptc = "IPTC", dng = "DNG", gps = "GPS";
    if (name.equalsIgnoreCase("GPSVersionID")) return gps;
    if (name.equalsIgnoreCase("GPSLatitudeRef")) return gps;
    if (name.equalsIgnoreCase("GPSLatitude")) return gps;
    if (name.equalsIgnoreCase("GPSLongitudeRef")) return gps;
    if (name.equalsIgnoreCase("GPSLongitude")) return gps;
    if (name.equalsIgnoreCase("GPSAltitudeRef")) return gps;
    if (name.equalsIgnoreCase("GPSAltitude")) return gps;
    if (name.equalsIgnoreCase("GPSTimeStamp")) return gps;
    if (name.equalsIgnoreCase("GPSSatellites")) return gps;
    if (name.equalsIgnoreCase("GPSStatus")) return gps;
    if (name.equalsIgnoreCase("GPSMeasureMode")) return gps;
    if (name.equalsIgnoreCase("GPSDOP")) return gps;
    if (name.equalsIgnoreCase("GPSSpeedRef")) return gps;
    if (name.equalsIgnoreCase("GPSpeed")) return gps;
    if (name.equalsIgnoreCase("GPSTrackRef")) return gps;
    if (name.equalsIgnoreCase("GPSTrack")) return gps;
    if (name.equalsIgnoreCase("GPSImgDirectionRef")) return gps;
    if (name.equalsIgnoreCase("GPSImgDirection")) return gps;
    if (name.equalsIgnoreCase("GPSMapDatum")) return gps;
    if (name.equalsIgnoreCase("GPSDestLatitudeRef")) return gps;
    if (name.equalsIgnoreCase("GPSDestLatitude")) return gps;
    if (name.equalsIgnoreCase("GPSDestLongitudeRef")) return gps;
    if (name.equalsIgnoreCase("GPSDestLongitude")) return gps;
    if (name.equalsIgnoreCase("GPSDestBearingRef")) return gps;
    if (name.equalsIgnoreCase("GPSDestBearing")) return gps;
    if (name.equalsIgnoreCase("GPSDestDistanceRef")) return gps;
    if (name.equalsIgnoreCase("GPSDestDistance")) return gps;
    if (name.equalsIgnoreCase("GPSProcessingMethod")) return gps;
    if (name.equalsIgnoreCase("GPSAreaInformation")) return gps;
    if (name.equalsIgnoreCase("GPSDateStamp")) return gps;
    if (name.equalsIgnoreCase("GPSDifferential")) return gps;
    return "UNKNOWN";
    }

  // Method to get location from GPSInfo tag name
  protected static String getInterTagLocation(String name)
    {
    String tiff = "TIFF", exif = "EXIF", iptc = "IPTC", dng = "DNG", gps = "GPS", inter = "INTER";
    if (name.equalsIgnoreCase("InteroperabilityIndex")) return inter;
    if (name.equalsIgnoreCase("InteroperabilityVersion")) return inter;
    return "UNKNOWN";
    }

  public static String getTiffTypeName(int type)
    {
    switch (type)
      {
      case 1: return "BYTE";
      case 2: return "ASCII";
      case 3: return "SHORT";
      case 4: return "LONG";
      case 5: return "RATIONAL";
      case 6: return "SBYTE";
      case 7: return "UNDEFINED";
      case 8: return "SSHORT";
      case 9: return "SLONG";
      case 10: return "SRATIONAL";
      case 11: return "FLOAT";
      case 12: return "DOUBLE";
      case 13: return "IFD";
      case 16: return "LONG8";
      case 17: return "SLONG8";
      case 18: return "IFD8";
      }
    return "UNKNOWN";
    }

  public static String getTiffCompressionName(int mode)
    {
    switch (mode)
      {
      case 1: return "None";
      case 2: return "CCITT Group 3, 1-D Modified Huffman run-length";
      case 3: return "T4, Group 3 Fax";
      case 4: return "T6, Group 4 fax";
      case 5: return "LZW";
      case 6: return "JPEG";
      case 32773: return "PackBits";
      }
    return "UNKNOWN";
    }

  // Method to get TIFF code from tag name
  public static String getTiffSampleFormatName(int code)
    {
    if (code == SF_INTEGER) return "Integer";
    if (code == SF_SINTEGER) return "SInteger";
    if (code == SF_IEEEFLOATING) return "IEEEFloating";
    if (code == SF_UNDEFINED) return "Undefined";
    return "UNKNOWN";
    }
 
  public static String getTiffPhotometricInterpretationName(int photometricInterpretation)
    {
    switch (photometricInterpretation)
      {
      case WHITEISZERO: return "WhiteIsZero";
      case BLACKISZERO: return "BlackIsZero";
      case RGB: return "RGB";
      case PALETTECOLOR: return "PaletteColor";
      case TRANSPARENCYMASK: return "TransparencyMask";
      case PHOTOMETRICCIELAB: return "PhotometricCIELAB";
      case PHOTOMETRICCFA: return "PhotometricCFA";
      case PHOTOMETRICLINEARRAW: return "PhotometricLinearRaw";
      }
    return "UNKNOWN";
    }

  public static String getTiffResolutionUnitName(int resolutionUnit)
    {
    switch (resolutionUnit)
      {
      case 1: return "Undefined";
      case 2: return "Inches";
      case 3: return "Centimeters";
      }
    return "UNKNOWN";
    }

  public static String getTiffPlanarConfigurationName(int configuration)
    {
    switch (configuration)
      {
      case 1: return "Chunky";
      case 2: return "Planar";
      }
    return "UNKNOWN";
    }

  // Method to get TIFF code from tag name
  public static String getTiffSubFileName(int code)
    {
    switch (code)
      {
      case TiffTag.FULL: return "Full";
      case TiffTag.REDUCED: return "Reduced";
      case TiffTag.MULTIPAGEFULL: return "MultipageFull";
      case TiffTag.MULTIPAGEREDUCED: return "MultipageReduced";
      case TiffTag.TRANSPARENCYFULLSINGLE: return "TransparencyFillSingle";
      case TiffTag.TRANSPARENCYREDUCEDSINGLE: return "TransparencyReducedSingle";
      case TiffTag.TRANSPARENCYFULLMULTIPAGE: return "TransparencyFillMultipage";
      case TiffTag.TRANSPARENCYREDUCEDMULTIPAGE: return "TransparencyReducedMultipage";
      case TiffTag.ALTERNATEPREVIEW: return "AlternativePreview";
      }
    return null;
    }

  }

