/*
 * WriteFits.java
 * version 1.0
 * 14 July 2022
 * Copyright 2022 Giuliano Giuffrida, Biblioteca Apostolica Vaticana
 * GNU General Public License v3.0
 *
 *
 */

package fileio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

import fits.HeaderValue;
import tiff.TiffRead;
import tiff.TiffEntry;
import tiff.TiffDirectory;
import tiff.TiffHandle;
import tiff.TiffTag;
/**
 * File FITS writer starting from DNG
 * @author Giuliano Giuffrida
 * @version 1.0
 */
public class WriteFits {
	
	static LinkedHashMap<String,HeaderValue> header;
	public static void convert(File input, File output, LinkedHashMap<String,HeaderValue> headerParams) throws IOException {
		
						
			header = new LinkedHashMap<String,HeaderValue>();
				
				RandomAccessFile rafdng = new RandomAccessFile(input, "r");
				
				TiffHandle inputHandle = new TiffHandle(rafdng, "Full");
				TiffRead inputData = new TiffRead(rafdng, inputHandle);
		    int pixels = (int) inputHandle.getImageWidth();
		    int scanlines = (int) inputHandle.getImageLength();
		    int samples = (int) inputHandle.getSamplesPerPixel();
		    int bits = (int) inputHandle.getBitsPerSample();
				RandomAccessFile raf = new RandomAccessFile(output, "rw");
				header.put("SIMPLE", new HeaderValue("T", "File does conform to FITS Standard"));
				header.put("BITPIX", new HeaderValue(bits, "Number of bits storing pix values"));
				header.put("NAXIS", new HeaderValue(2, "Number of data axes"));
				header.put("NAXIS1", new HeaderValue(pixels, "Length of data axis 1"));
				header.put("NAXIS2", new HeaderValue(scanlines, "Length of data axis 2"));		
				header.put("UNIKEY", new HeaderValue("T", "Compliant with UNI 11845:2022"));		
				
        ArrayList<TiffDirectory> tiffDirectoryList = inputHandle.getDirectoryList();
        TiffDirectory ifd0 = tiffDirectoryList.get(0);
        
        TiffDirectory ifdSub = ifd0.getSubDirectoryList().get(0);
        Map<Integer, TiffEntry> subTags = ifdSub.getDirectoryEntriesMap();         
		Map<Integer, TiffEntry> mainOrTags = ifd0.getDirectoryEntriesMap();
		Map<Integer, TiffEntry> mainTags = new HashMap<Integer, TiffEntry>(mainOrTags);
		if (ifd0.getExifDirectory()!=null) {
			Map<Integer, TiffEntry> exifTags = ifd0.getExifDirectory().getDirectoryEntriesMap();
			exifTags.forEach(
	    		  (key, value) -> mainTags.merge(key, value, (v1, v2) -> v1));			
		}
        
        
        /*
        System.out.println("MAIN");
        mainOrTags.entrySet().forEach(entry->{
            System.out.println(entry.getKey() + " " + TiffTag.getTagName(entry.getKey()));  
         });
        System.out.println("SUB");
        subTags.entrySet().forEach(entry->{
            System.out.println(entry.getKey() + " " + TiffTag.getTagName(entry.getKey()));  
         });
        System.out.println("EXIF");
        exifTags.entrySet().forEach(entry->{
            System.out.println(entry.getKey() + " " + TiffTag.getTagName(entry.getKey()));  
         }); */
         
//				TiffEntry DNGVersion=mainTags.get(TiffTag.DNGVERSION);
//				String DNGHeader=DNGVersion.getLongValues()[0] 
//						+ "." + DNGVersion.getLongValues()[1]+ "." + DNGVersion.getLongValues()[2]
//								+ "." + DNGVersion.getLongValues()[3];
        
        long[] dngv = ifd0.getTiffTag("DNGVersion").getArray();
        String DNGHeader = dngv[0]+"."+dngv[1]+"."+dngv[2]+"."+dngv[3];
		header.put("DNGVERSI", new HeaderValue(DNGHeader, "DNG Version of original file"));	
	  	
	  	TiffEntry model = mainTags.get(TiffTag.getTagCode("UNIQUECAMERAMODEL"));	  	
	  	putString(model, "INSTRUME", "Maker and model of the device");
	  	TiffEntry make = mainTags.get(TiffTag.getTagCode("MAKE"));	  	
	  	putString(make, "MAKE", "Maker of the device");
	  	TiffEntry datetime = mainTags.get(TiffTag.getTagCode("DATETIME"));	  	
	  	putString(datetime, "DATE-OBS", "Date and time of acquisition"); //TODO: reformat date
	  	//header.put("DATE-OBS", new HeaderValue("2019-02-28T12:45:14", "Date and time of acquisition")); //TODO: temp
	  	Date date = new Date();
	  	SimpleDateFormat DateFor = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  	String stringDate = DateFor.format(date);
	  	header.put("DATE", new HeaderValue(stringDate.replace(" ", "T"), "Date and time of FITS file creation")); //TODO: temp

	  	/*
	  	 * add key from input map		    	  	
	  	 */
	  	Iterator<String> paramsIter = headerParams.keySet().iterator();
	  	while (paramsIter.hasNext()) {
	  		String key=paramsIter.next();
			header.put(key, headerParams.get(key));
		}
	  	
	  	TiffEntry ColorMatrix1=mainTags.get(TiffTag.getTagCode("COLORMATRIX1"));
	  	putArray(ColorMatrix1, "COLOR1", "Matrix for color conversion");
	  	
	  	TiffEntry ColorMatrix2=mainTags.get(TiffTag.getTagCode("COLORMATRIX2"));
	  	putArray(ColorMatrix2, "COLOR2","Matrix for color conversion");

	  	TiffEntry CalibrationIlluminant1 = mainTags.get(TiffTag.getTagCode("CALIBRATIONILLUMINANT1"));
	  	putValue(CalibrationIlluminant1, "CALILL1","Illuminant for the first set of color calibration");

	  	TiffEntry CalibrationIlluminant2 = mainTags.get(TiffTag.getTagCode("CALIBRATIONILLUMINANT2"));
	  	putValue(CalibrationIlluminant2, "CALILL2", "Illuminant for the second set of color calibration");
	  	
	  	TiffEntry CameraCalibration1 = mainTags.get(TiffTag.getTagCode("CAMERACALIBRATION1"));  	
	  	putArray(CameraCalibration1, "CAMCA1", "Matrix from reference to individual camera");

	  	TiffEntry CameraCalibration2 = mainTags.get(TiffTag.getTagCode("CAMERACALIBRATION2"));  	
	  	putArray(CameraCalibration2, "CAMCA2", "Matrix from reference to individual camera");

	  	TiffEntry AsShotNeutral = mainTags.get(TiffTag.getTagCode("ASSHOTNEUTRAL"));  
	  	putArray(AsShotNeutral, "ASSHOTN", "White balance at time of capture");
	  	
	  	TiffEntry SubjectDistance = mainTags.get(TiffTag.getTagCode("SUBJECTDISTANCE"));  
	  	putArray(SubjectDistance, "DISTANC", "The distance to the subject, given in meters.");

	  	/*
	  	 * lens
	  	 */

	  	TiffEntry ForwardMatrix1=mainTags.get(TiffTag.getTagCode("FORWARDMATRIX1"));
	  	putArray(ForwardMatrix1, "FORWA1", "Matrix that maps white balanced camera colors to XYZ D50 color");
	  	
	  	TiffEntry ForwardMatrix2=mainTags.get(TiffTag.getTagCode("FORWARDMATRIX2"));
	  	putArray(ForwardMatrix2, "FORWA2","Matrix that maps white balanced camera colors to XYZ D50 color");

	  	TiffEntry DngLensInfo = mainTags.get(TiffTag.getTagCode("DNGLENSINFO"));  	
	  	putArray(DngLensInfo, "DNGLENS", "LensInfo contains information about the lens that captured the image");

	  	TiffEntry LensInfo = mainTags.get(TiffTag.getTagCode("LENSINFO"));  	
	  	putArray(LensInfo, "LENSINF", "LensInfo contains information about the lens that captured the image");

	  	TiffEntry LensModel = mainTags.get(TiffTag.getTagCode("LENSMODEL"));  	
	  	putString(LensModel, "LENSMOD", "Lens model name and number as ASCII string.");
	  		  	
	  	TiffEntry FocalLength = mainTags.get(TiffTag.getTagCode("FOCALLENGTH"));  	
	  	putArray(FocalLength, "FOCALLE", "The actual focal length of the lens, in mm");

	  	TiffEntry FocalLengthIn35Format = mainTags.get(TiffTag.getTagCode("FOCALLENGTHIN35MMFORMAT"));  	
	  	putValue(FocalLengthIn35Format, "FOCAL35M", "Focal length in 35 mm format");
	  	
	  	TiffEntry ApertureValue = mainTags.get(TiffTag.getTagCode("APERTUREVALUE"));  	
	  	putArray(ApertureValue, "APERTUR", "The lens aperture");

	  	TiffEntry MaxApertureValue = mainTags.get(TiffTag.getTagCode("MAXAPERTUREVALUE"));  	
	  	putArray(MaxApertureValue, "MAXAPER", "The smallest F number of the lens.");

	  	TiffEntry FocalPlaneXResolutionPrivate = mainTags.get(TiffTag.getTagCode("FOCALPLANEXRESOLUTIONPRIVATE"));  	
	  	putArray(FocalPlaneXResolutionPrivate, "FCXPRIV", "Number of pixels on X direction per unit on the camera focal plane.");

	  	TiffEntry FocalPlaneYResolutionPrivate = mainTags.get(TiffTag.getTagCode("FOCALPLANEYRESOLUTIONPRIVATE"));  	
	  	putArray(FocalPlaneYResolutionPrivate, "FCYPRIV", "Number of pixels on Y direction per unit on the camera focal plane.");

	  	TiffEntry FocalPlaneResolutionUnitPrivate = mainTags.get(TiffTag.getTagCode("FOCALPLANERESOLUTIONUNITPRIVATE"));  	
	  	putValue(FocalPlaneResolutionUnitPrivate, "FCUNITPR", "Focal Plane resolution unit");

	  	TiffEntry ISOSpeedRatings = mainTags.get(TiffTag.getTagCode("ISOSPEEDRATINGS"));  	
	  	putValue(ISOSpeedRatings, "ISOSPEED", "Indicates the ISO Speed and ISO Latitude of the camera or input device as specified in ISO 12232");

	  	TiffEntry ExposureTime = mainTags.get(TiffTag.getTagCode("EXPOSURETIME"));  	
	  	putArray(ExposureTime, "EXPTIME", "Exposure time, given in seconds.");

	  	TiffEntry ShutterSpeedValue = mainTags.get(TiffTag.getTagCode("SHUTTERSPEEDVALUE"));  	
	  	putArray(ShutterSpeedValue, "SHSPEED", "Shutter speed.");
	  	
	  	TiffEntry ColorSpace = mainTags.get(TiffTag.getTagCode("COLORSPACE"));  	
	  	putValue(ColorSpace, "COLSPACE", "Color Space");

	  	TiffEntry FNumber = mainTags.get(TiffTag.getTagCode("FNUMBER"));  	
	  	putArray(FNumber, "FNUMBER", "F Number");

	  	TiffEntry WhiteBalance = mainTags.get(TiffTag.getTagCode("WHITEBALANCE"));  	
	  	putValue(WhiteBalance, "WHITEBAL", "Indicates the white balance mode set when the image was shot");
	  	
	  	TiffEntry BaselineExposure = mainTags.get(TiffTag.getTagCode("BASELINEEXPOSURE"));  	
	  	putArray(BaselineExposure, "BASEEXP", "BaselineExposure specifies by how much (in EV units) to move the zero point");

	  	TiffEntry BaselineNoise = mainTags.get(TiffTag.getTagCode("BASELINENOISE"));  	
	  	putArray(BaselineNoise, "BASENOI", "Specifies the relative noise level of the camera model at a baseline ISO value of 100");

	  	TiffEntry BaselineSharpness = mainTags.get(TiffTag.getTagCode("BASELINESHARPNESS"));  	
	  	putArray(BaselineSharpness, "BASESH", "Specifies the relative amount of sharpening required for this camera model");
	  	
	  	TiffEntry SerialNumber = mainTags.get(TiffTag.getTagCode("SERIALNUMBER"));  	
	  	putString(SerialNumber, "SERIALN", "Camera serial number");

	  	TiffEntry CFALayout = subTags.get(TiffTag.getTagCode("CFALAYOUT"));  	
	  	putValue(CFALayout, "CFALAYOU", "CFALayout describes the spatial layout of the CFA - add legenda");
	  	
	  	TiffEntry CFAPlaneColor = subTags.get(TiffTag.getTagCode("CFAPLANECOLOR"));  	
	  	putArray(CFAPlaneColor, "CFAPLC", "CFAPlaneColor provides a mapping between the values in the CFAPattern tag and the plane numbers in LinearRaw space.");
	  
	  	TiffEntry NoiseProfile = mainTags.get(TiffTag.getTagCode("NOISEPROFILE")); 
	  	putArrayDouble(NoiseProfile, "NOISEP", "Describes the amount of noise in a raw image; models the amount of signal-dependent photon (shot) noise and signal-independent sensor readout noise");
	  	
	  	/*
	  	 * 
	  	 * 1 = Rectangular (or square) layout
2 = Staggered layout A: even columns are offset down by 1/2 row
3 = Staggered layout B: even columns are offset up by 1/2 row
4 = Staggered layout C: even rows are offset right by 1/2 column
5 = Staggered layout D: even rows are offset left by 1/2 column
	  	 */
	  	
	  	TiffEntry XMP = mainTags.get(TiffTag.getTagCode("XMP"));
	  	String XMPString=XMP.getStringValues();
	  	Scanner scanner = new Scanner(XMPString);
	    while (scanner.hasNextLine()) {
	        String line = scanner.nextLine();
	        if (line.toLowerCase().contains("lensinfo=")) {
				String[] value = line.split("=");
				putString(value[1].replace("\"", "").trim(), "AUX1", "LensInfo in aux xmp");
			}
	        if (line.toLowerCase().contains("lens=")) {
				String[] value = line.split("=");
			putString(value[1].replace("\"", "").trim(), "AUX2", "Lens in aux xmp");
			}
	        if (line.toLowerCase().contains("lensid=")) {
				String[] value = line.split("=");
				putString(value[1].replace("\"", "").trim(), "AUX3", "LensID in aux xmp");
			}
	        if (line.toLowerCase().contains("lensmodel=")) {
				String[] value = line.split("=");
				putString(value[1].replace("\"", "").trim(), "AUX4", "LensModel in exifEX xmp");
			}
	        if (line.toLowerCase().contains("crs:temperature=")) {
				String[] value = line.split("=");
				putString(value[1].replace("\"", "").trim(), "AUX5", "Temperature as elaborated by camera raw");
			}
	        if (line.toLowerCase().contains("crs:tint=")) {
				String[] value = line.split("=");
				putString(value[1].replace("\"", "").trim(), "AUX6", "Tint as elaborated by camera raw");
			}
	        // ApproximateFocusDistance
	        if (line.toLowerCase().contains("aux:approximatefocusdistance=")) {
				String[] value = line.split("=");
				putString(value[1].replace("\"", "").trim(), "AUX7", "Approximate Focus Distance");
			}
	        
	        
	        
	    }

	  	/*
	  	 * 
	  	 */
	  	
	  	TiffEntry photoInt = subTags.get(TiffTag.getTagCode("PHOTOMETRICINTERPRETATION"));
	  	putValue(photoInt, "PHOTOINT", "The color space. 32803=CFA, 34892=Linear Raw");
	  	
	  	//TiffEntry PlanarConfiguration = subTags.get(TiffTag.PLANARCONFIGURATION);
	  	//putValue(PlanarConfiguration, "PLANCONF", "blabla");

	  	
	  	
	  	TiffEntry CFARepeatPatternDim = subTags.get(TiffTag.getTagCode("CFAREPEATPATTERNDIM"));  	
	  	putArray(CFARepeatPatternDim, "CFARPDI", "Minimum rows and columns to define CFA pattern");
	  	
	  	TiffEntry CFAPattern = subTags.get(TiffTag.getTagCode("CFAPATTERN"));  	
	  	putArray(CFAPattern, "CFAPATT", "CFA geometric pattern");
	  	

	  	TiffEntry BlackLevelRepeatDim = subTags.get(TiffTag.getTagCode("BLACKLEVELREPEATDIM"));	
	  	putArray(BlackLevelRepeatDim, "BLACKRE", "Repeat pattern size for the BlackLevel tag");
	  	
	  	TiffEntry BlackLevel = subTags.get(TiffTag.getTagCode("BLACKLEVEL"));  	
	  	putArray(BlackLevel, "BLACKLE", " Zero light encoding level");	    	  	
	  	
	  	TiffEntry WhiteLevel = subTags.get(TiffTag.getTagCode("WHITELEVEL"));
	  	putValue(WhiteLevel, "WHITELEV", "Fully saturated encoding level for the raw sample values");

	  	TiffEntry DefaulCropOrigin = subTags.get(TiffTag.getTagCode("DEFAULTCROPORIGIN"));  	
	  	putArray(DefaulCropOrigin, "CROPORI", "Origin of the final image area");
	  	
	  	TiffEntry DefaulCropSize = subTags.get(TiffTag.getTagCode("DEFAULTCROPSIZE"));  	
	  	putArray(DefaulCropSize, "CROPSIZ", "Size of the final image area");
	  	
	  	TiffEntry BayerGreenSplit = subTags.get(TiffTag.getTagCode("BAYERGREENSPLIT"));
	  	putValue(BayerGreenSplit, "BAYGREEN","Green channels difference");
	  	
	  	TiffEntry ActiveArea = subTags.get(TiffTag.getTagCode("ACTIVEAREA"));  	
	  	putArray(ActiveArea, "ACTAREA", "Active (non-masked) pixels of the sensor");
	  	
	  	TiffEntry AnalogueBalance = mainTags.get(TiffTag.getTagCode("ANALOGUEBALANCE"));  	
	  	putArray(AnalogueBalance, "ANBAL", "AnalogBalance defines the gain, either analog (recommended) or digital (not recommended) that has been applied the stored raw values.");
	  	
	  	TiffEntry Orientation = mainTags.get(TiffTag.getTagCode("ORIENTATION"));
	  	putValue(Orientation, "ORIENTAT","The orientation of the image with respect to the rows and columns");

	  	
	  	writeHeader(raf, header);
			    for (int n=0; n < scanlines; n++) {
			    short[] line = inputData.getNextShortScanlineData();
			    byte[] newline = new byte[line.length*(bits/8)];
			    for (int i = 0; i < line.length; i++) {
					newline[i*2]=(byte) (line[i] >> 8);
					newline[i*2 + 1 ]=(byte) line[i];
				}
			    	raf.write(newline);
			    }
				int missingbytes=(pixels*(bits/8)*scanlines)%2880;
				if (missingbytes!=0) {
					byte[] filler = new byte[2880-missingbytes];
					raf.write(filler);					
				}
				raf.close();

	}
	private static void putArray(TiffEntry entry, String key, String comment ) {
    	long[] entryLong=null;
    	if (entry!=null) {
    		entryLong=entry.getLongValues();
			for (int i = 0; i < entryLong.length; i++) {
				header.put(key+i, new HeaderValue(entryLong[i], comment+". Index "+i));
			}

		}

	}
	private static void putArrayDouble(TiffEntry entry, String key, String comment ) {
    	double[] entryDouble=null;
    	if (entry!=null) {
    		entryDouble=entry.getDoubleValues();
			for (int i = 0; i < entryDouble.length; i++) {
				header.put(key+i, new HeaderValue(entryDouble[i], comment+". Index "+i));
			}

		}

	}
	private static void putValue(TiffEntry entry, String key, String comment ) {
    	if (entry!=null) {
    		long[] entryLong=entry.getLongValues();
				header.put(key, new HeaderValue(entryLong[0], comment));
		}
	}
	
	private static void putString(TiffEntry entry, String key, String comment) {
    	if (entry!=null) {
    		String value=removeNull(entry.getStringValues());  
    		if (key.equals("DATE-OBS")) {
    			String day=value.split(" ")[0].replace(":", "-");
    			value=day+"T"+value.split(" ")[1];
    		}
    			
				header.put(key, new HeaderValue(value, comment));
		}
		
	}
	private static void putString(String value, String key, String comment) {
    		if (key.equals("DATE-OBS")) {
    			String day=value.split(" ")[0].replace(":", "-");
    			value=day+"T"+value.split(" ")[1];
    		}
    			
				header.put(key, new HeaderValue(value, comment));
		
	}
	  private static String removeNull(String string) {
		  List<Character> good = new ArrayList<Character>();
		  byte[] stringBytes=string.getBytes();
		  for (int i = 0; i < stringBytes.length; i++) {
			if (stringBytes[i]>=32 && stringBytes[i]<=126) {
				good.add(new Character((char) stringBytes[i]));
			}
		}
		 String output="";
		 for (int i = 0; i < good.size(); i++) {
			output+=good.get(i);
		}
		 return output;
	  }
	  private static LinkedHashMap<Integer,String> getMapTags(HashMap<String,List<String>> maptags, String prefix) {
		  
		  LinkedHashMap<Integer,String> output = new LinkedHashMap<Integer,String>();
		  int counter=0;
		  for (Iterator iterator = maptags.keySet().iterator(); iterator.hasNext();) {
			String filename = (String) iterator.next();
			List<String> tags=maptags.get(filename);
			List<Integer> tagsNumber=new ArrayList<Integer>();
			for (int i = 0; i < tags.size(); i++) {
				String[] row=tags.get(i).split(",");
				Integer tag=Integer.parseInt(row[0]);
				tagsNumber.add(tag);
				if (output.get(tag)!=null) {
					String partial=output.get(tag);
					partial+=","+row[2]+"-"+row[3];
					output.replace(tag, partial);
				} else {
					if (!prefix.isEmpty()) {
						row[0]=prefix+row[0];
					}
					String partial=row[0]+","+row[1];
					for (int j = 0; j < counter; j++) {
						partial+=","+" "+"-"+" ";
					}
					partial+=","+row[2]+"-"+row[3];
					output.put(tag, partial);
				}
			}
			Integer[] keys=output.keySet().toArray(new Integer[0]);
			List<Integer> missings = findMissings(tagsNumber, keys);
			for (int i = 0; i < missings.size(); i++) {
				String partial=output.get(missings.get(i));
				partial+=","+" "+"-"+" ";
				output.replace(missings.get(i), partial);

			}
			counter++;
		  }
		  
		  return output;
		  
		  
	  }
	  
	  private static List<Integer> findMissings(List<Integer> tags, Integer[] keys) {
		  List<Integer> missings = new ArrayList<Integer>();
		  for (int i = 0; i < keys.length; i++) {
			if (!tags.contains(keys[i])) {
				missings.add(keys[i]);
			}
		}
		  
		  return missings;
	  }
	  public static short getShort(byte[] buffer, int offset, int nBytes)
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
	     result = 256*result+tmp[nBytes-n-1];
	      
	      }
	    return (short) result;
	    }

		public static void writeHeader(RandomAccessFile raf, LinkedHashMap<String,HeaderValue> header) throws IOException {
			LinkedHashMap<String, HeaderValue> headerNew = addContinueIfNeeded(header);
			Set<String> keyset = headerNew.keySet();
			String[] keys = keyset.toArray(new String[0]);
			byte[] rowHeader = new byte[80];
		    for (int i = 0; i < keys.length; i++) {
		    	String key=keys[i];
		    	/*
		    	 * PREVEDERE KEY CONTINUE
		    	 */
		    	if (key.equals("COMMENT") || key.equals("HISTORY")) {
			    	byte[] keybyte = getBytes(key);
			    	byte[] finalkey=new byte[8];
			    	for (int j = 0; j < keybyte.length; j++) {
			    		finalkey[j]=keybyte[j];
					}
			    	for (int j = keybyte.length; j < 8; j++) {
			    		finalkey[j]=32;
					}
			    	byte[] value = getValueComment(headerNew.get(key));
			    	for (int j = 0; j < 8; j++) {
			    		rowHeader[j]=finalkey[j];
					}
			    	for (int j = 8; j < rowHeader.length; j++) {
			    		rowHeader[j]=value[j-8];
					}
			    	raf.write(rowHeader);
		    	} else if (key.startsWith("CONTINUE")) {
			    	byte[] keybyte = getBytes(key);
			    	byte[] finalkey=new byte[8];
			    	for (int j = 0; j < Math.min(keybyte.length, finalkey.length); j++) {
			    		finalkey[j]=keybyte[j];
					}
			    	for (int j = Math.min(keybyte.length, finalkey.length); j < 8; j++) {
			    		finalkey[j]=32;
					}
			    	byte[] value = getValueNew(headerNew.get(keys[i]));
			    	//byte[] value = getValue(header.get(keys[i]));
			    	//byte[] value = new byte[70];
			    
			    	for (int j = 0; j < 8; j++) {
			    		rowHeader[j]=finalkey[j];
					}
			    	for (int j = 10; j < rowHeader.length; j++) {
			    		rowHeader[j]=value[j-10];
					}
			    	rowHeader[8]=32;
			    	rowHeader[9]=32;
			    	raf.write(rowHeader);		    				    	
		    	} else {
			    	byte[] keybyte = getBytes(key);
			    	byte[] finalkey=new byte[8];
			    	for (int j = 0; j < Math.min(keybyte.length, finalkey.length); j++) {
			    		finalkey[j]=keybyte[j];
					}
			    	for (int j = Math.min(keybyte.length, finalkey.length); j < 8; j++) {
			    		finalkey[j]=32;
					}
			    	byte[] value = getValueNew(headerNew.get(keys[i]));
			    	//byte[] value = getValue(header.get(keys[i]));
			    	//byte[] value = new byte[70];
			    
			    	for (int j = 0; j < 8; j++) {
			    		rowHeader[j]=finalkey[j];
					}
			    	for (int j = 10; j < rowHeader.length; j++) {
			    		rowHeader[j]=value[j-10];
					}
			    	rowHeader[8]=61;
			    	rowHeader[9]=32;
			    	raf.write(rowHeader);		    		
		    	}
			}
		    //write END
			for (int i = 3; i < rowHeader.length; i++) {
				rowHeader[i]=32;
			}
			rowHeader[0]=69;
			rowHeader[1]=78;
			rowHeader[2]=68;
			raf.write(rowHeader);
			// filling bytes up to 2880 block
			int missingbytes=(80*(keys.length+1))%2880;
			if (missingbytes!=0) {
				byte[] filler = new byte[2880-missingbytes];
				for (int i = 0; i < filler.length; i++) {
					filler[i]=32;
				}
				raf.write(filler);				
			}
		}
		/**
		 * Separate the value/comment longer that 70 byte using the CONTINUE key
		 * 
		 * @param header
		 * @return the new LinkedHashMap with the CONTINUE key
		 * @throws IOException
		 */
		public static LinkedHashMap<String,HeaderValue> addContinueIfNeeded(LinkedHashMap<String,HeaderValue> header) throws IOException {
			LinkedHashMap<String,HeaderValue> headerContinue = new LinkedHashMap<String,HeaderValue>();
			Set<String> keyset = header.keySet();
			String[] keys = keyset.toArray(new String[0]);
			int countComments=0;
			int minStartComment=20;
		    for (int i = 0; i < keys.length; i++) {
		    	String key=keys[i];
		    	HeaderValue headerValue = header.get(key);
		    	char[] value = headerValue.getValue().toString().toCharArray();
		    	String comment=headerValue.getComment();
		    	if (comment!=null) {
					// check if can stay on one line
		    		if ((Math.max(minStartComment, value.length)+comment.toCharArray().length) < 65 || (!headerValue.getValueType().equals("STRING") && ((Math.max(minStartComment, value.length)+comment.toCharArray().length)<67)  )   ) {
						headerContinue.put(key, headerValue);						
					} else {
						//check if the value can stay on one line
						if (value.length<=67) {
							// then only the comment need to be splitted
							char[] comment_full = comment.toCharArray();
							char[] newcomment = new char[65-Math.max(minStartComment, value.length)];
							for (int j = 0; j < newcomment.length; j++) {
								newcomment[j]=comment_full[j];
							}
							HeaderValue newHeaderValue= new HeaderValue(headerValue.getValue().toString(), new String(newcomment));
							headerContinue.put(key, newHeaderValue);								
							char[] comment_residual = new char[comment_full.length-newcomment.length];
							for (int j = 0; j < comment_residual.length; j++) {
								comment_residual[j]=comment_full[newcomment.length + j];
							}
							ArrayList<char[]> comments = splitter(comment_residual, 64, false);
							for (int j = 0; j < comments.size(); j++) {
								char ampersand = 38;
								newHeaderValue= new HeaderValue(String.valueOf(ampersand), new String(comments.get(j)));
								headerContinue.put("CONTINUE"+countComments, newHeaderValue);
								countComments++;								
							}
						} else {
							//split value and the check comment
							ArrayList<char[]> values = splitter(value, 67, true);
							for (int j = 0; j < values.size()-1; j++) {
								if (j==0) {
									HeaderValue newHeaderValue= new HeaderValue(new String(values.get(j)), null);
									headerContinue.put(key, newHeaderValue);	
								} else {
									HeaderValue newHeaderValue= new HeaderValue(new String(values.get(j)), null);
									headerContinue.put("CONTINUE"+countComments, newHeaderValue);
									countComments++;
								}
							}
								char[] lastValue=values.get(values.size()-1);
								if ((Math.max(minStartComment, lastValue.length)+comment.toCharArray().length) < 65) {
									HeaderValue newHeaderValue= new HeaderValue(new String(lastValue), comment);
									headerContinue.put("CONTINUE"+countComments, newHeaderValue);
									countComments++;
								} else {
									char[] comment_full = comment.toCharArray();
									char[] newcomment = new char[65-Math.max(minStartComment, lastValue.length)];
									for (int k = 0; k < newcomment.length; k++) {
										newcomment[k]=comment_full[k];
									}
									HeaderValue newHeaderValue= new HeaderValue(new String(lastValue), new String(newcomment));
									headerContinue.put("CONTINUE"+countComments, newHeaderValue);
									countComments++;
									char[] comment_residual = new char[comment_full.length-newcomment.length];
									for (int k = 0; k < comment_residual.length; k++) {
										comment_residual[k]=comment_full[newcomment.length + k];
									}
									
									ArrayList<char[]> comments = splitter(comment_residual, 64, false);
									for (int k = 0; k < comments.size(); k++) {
										char ampersand = 38;
										newHeaderValue= new HeaderValue(String.valueOf(ampersand), new String(comments.get(k)));
										headerContinue.put("CONTINUE"+countComments, newHeaderValue);
										countComments++;								
									}

								}
								
							}

						}
				} else {
					// no comment
					if (value.length<=68) {
						headerContinue.put(key, headerValue);
					} else {						
						ArrayList<char[]> values = splitter(value, 68, true);
						for (int j = 0; j < values.size(); j++) {
							if (j==0) {
								HeaderValue newHeaderValue= new HeaderValue(new String(values.get(j)), null);
								headerContinue.put(key, newHeaderValue);								
							} else {
								HeaderValue newHeaderValue= new HeaderValue(new String(values.get(j)), null);
								headerContinue.put("CONTINUE"+countComments, newHeaderValue);
								countComments++;
							}
						}
						
					}
				}
		    }

			return headerContinue;
		}
		
		public static ArrayList<char[]> splitter(char[] array, int portionLength, boolean ampersand) {
			ArrayList<char[]> output = new ArrayList<char[]>();
			if (array.length<= portionLength) {
				output.add(array);
				return output;
			}
			if (ampersand) {
				int portionMinus = portionLength-1;
				int numRows=array.length/(portionMinus); 
				int residuals=array.length%(portionMinus);
				for (int j = 0; j < numRows-1; j++) {
					char[] newarray = new char[portionLength];
					
					for (int c = 0; c < portionMinus; c++) {
						newarray[c]=array[j*(portionMinus) + c];	
					}
						newarray[portionMinus]=38;
						output.add(newarray);
				}
				if (residuals==0) {
					//numrows is the last row
					char[] newarray = new char[portionLength];
					for (int c = 0; c < portionMinus; c++) {
						newarray[c]=array[(numRows-1)*portionMinus + c];		
					}
					newarray[portionMinus]=32;
					output.add(newarray);
				}else {
					// last row without ampersand
					char[] newarray = new char[portionLength];
					for (int c = 0; c < portionMinus; c++) {
						newarray[c]=array[(numRows-1)*portionMinus + c];		
					}
					newarray[portionMinus]=38;
					output.add(newarray);
					char[] newarray1 = new char[residuals];
					for (int c = 0; c < residuals; c++) {
						newarray1[c]=array[(numRows)*portionMinus + c];		
					}
					output.add(newarray1);
				}
			} else {
				int numRows=array.length/(portionLength); 
				int residuals=array.length%(portionLength);
				for (int j = 0; j < numRows-1; j++) {
					char[] newarray = new char[portionLength];
					
					for (int c = 0; c < portionLength; c++) {
						newarray[c]=array[j*(portionLength) + c];		
					}
						output.add(newarray);
				}
				if (residuals==0) {
					//numrows is the last row
					char[] newarray = new char[portionLength];
					for (int c = 0; c < portionLength; c++) {
						newarray[c]=array[(numRows-1)*portionLength + c];		
					}
					output.add(newarray);
				}else {
					char[] newarray = new char[portionLength];
					for (int c = 0; c < portionLength; c++) {
						newarray[c]=array[(numRows-1)*portionLength + c];		
					}
					output.add(newarray);
					char[] newarray1 = new char[residuals];
					for (int c = 0; c < residuals; c++) {
						newarray1[c]=array[(numRows)*portionLength + c];		
					}
					output.add(newarray1);
				}
			}

			return output;
			
		}

		/**
		 * Given a Header value the method returns a byte[]
		 * it is expected that the (Math.max(minStartComment, valBytes.length)+commentBytes.length) is below 65 bytes or 68 without comment
		 * @param val
		 * @return
		 */
		public static byte[] getValueNew(HeaderValue val) { 
			
			Integer minStartCommentStart=20;
			Integer minStartComment=minStartCommentStart;
			byte[] valBytes = val.getValue().toString().getBytes();
			byte[] commentBytes =new byte[0];
			if (val.getComment()!=null) {
				commentBytes = val.getComment().getBytes();
			}
			byte[] value = new byte[70];
			String valType=val.getValueType();
			
			//70 - 2 single quotes - 3 (_/_)
				if (valType!="STRING") {
					
					if (valBytes.length<minStartCommentStart) {
						minStartComment=minStartCommentStart;// header formatted starting comment at byte 11 of the value
					} else {
						minStartComment=valBytes.length;
					}
					for (int i = 0; i < (minStartComment-valBytes.length); i++) {
						value[i]=32;
					}
					for (int i = 0; i < valBytes.length; i++) {
						value[i+(minStartComment-valBytes.length)]=valBytes[i];
					}
					if (commentBytes.length>0) {
						value[minStartComment]=32;
						value[minStartComment+1]=47;
						value[minStartComment+2]=32;
						for (int i = 0; i < commentBytes.length; i++) {
							value[minStartComment+3+i] = commentBytes[i];
						}		
						for (int i = minStartComment+3+commentBytes.length; i < value.length; i++) {
							value[i]=32;
						}						
					} else {
						for (int i = minStartComment; i < value.length; i++) {
							value[i]=32;
						}						
						
					}
				}	else {
					value[0]=39;
					for (int i = 0; i < valBytes.length; i++) {
						value[i+1]=valBytes[i];
					}				
					value[valBytes.length+1]=39;
					if (commentBytes.length>0) {
						value[valBytes.length+2]=32;
						value[valBytes.length+3]=47;
						value[valBytes.length+4]=32;
						for (int i = 0; i < commentBytes.length; i++) {
							value[valBytes.length+5+i] = commentBytes[i];
						}	
						for (int i = valBytes.length+5+commentBytes.length; i < value.length; i++) {
							value[i]=32;
						}

					} else {
						for (int i = valBytes.length+2; i < value.length; i++) {
							value[i]=32;
						}						
					
					}
					

				}		


			return value;
		}
		public static byte[] getValue(HeaderValue val) { 
			
			byte[] value = new byte[70];
			Integer minStartComment=11;
			Integer maxCommentLength=70-minStartComment-3;
			Integer minStartComment3=minStartComment+3;
		    String x = val.getValue().toString();
		    
		    if (x.toCharArray().length==1) {
				for (int i = 0; i < minStartComment; i++) {
					value[i]=32;
				}
				value[minStartComment]=(byte) x.toCharArray()[0];
				value[minStartComment+1]=47;
				value[minStartComment+2]=32;
		        byte[] charComment=val.getComment().getBytes();
		        if (charComment.length<=maxCommentLength) {
					for (int i = 0; i < charComment.length; i++) {
						value[minStartComment3+i]=charComment[i];
					}
					for (int i = charComment.length; i < maxCommentLength; i++) {
						value[minStartComment3+i]=32;
					}
				} else {
			        for (int i = minStartComment3; i < 70; i++) {
						value[i]=charComment[i-minStartComment3];
					}				
				}
				return value;
			}
		    
		    if (x.toCharArray()[0]==0) { //TODO: this doesn't look good, check it
	        	byte[] finalBytes= x.getBytes();
	        	if (finalBytes.length<minStartComment-1) {
	    	        for (int j = 0; j < minStartComment-finalBytes.length; j++) {
	    	        	value[j]=32;
	    			}
	    	        for (int j = minStartComment-finalBytes.length; j < minStartComment; j++) {
	    	        	value[j]=finalBytes[j-(minStartComment-finalBytes.length)];
	    			}
	    	        value[0]=39;
	    	        value[minStartComment]=39;
	    			value[minStartComment+1]=47;
	    			value[minStartComment+2]=32;
	    	        byte[] charComment=val.getComment().getBytes();
	    	        if (charComment.length<=maxCommentLength) {
	    				for (int i = 0; i < charComment.length; i++) {
	    					value[minStartComment3+i]=charComment[i];
	    				}
	    				for (int i = charComment.length; i < maxCommentLength; i++) {
	    					value[minStartComment3+i]=32;
	    				}
	    			} else {
	    		        for (int i = minStartComment3; i < 70; i++) {
	    					value[i]=charComment[i-minStartComment3];
	    				}				
	    			}
	    			return value;

				} else {
	    	        for (int j = 1; j < finalBytes.length+1; j++) {
	    	        	value[j]=finalBytes[j-1];
	    			}
	    	        value[0]=39;
	    	        value[finalBytes.length+1]=39;
	    			value[finalBytes.length+2]=47;
	    			value[finalBytes.length+3]=32;  			
	    	        byte[] charComment=val.getComment().getBytes();
	    	        if (charComment.length<=(69-finalBytes.length-3)) {
	    				for (int i = 0; i < charComment.length; i++) {
	    					value[finalBytes.length+4+i]=charComment[i];
	    				}
	    				for (int i = charComment.length; i <= (69-finalBytes.length-3); i++) {
	    					value[i]=32;
	    				}
	    			} else {
	    		        for (int i = finalBytes.length+4; i < 70; i++) {
	    					value[i]=charComment[i-finalBytes.length-4];
	    				}				
	    			}
	    			return value;
					
				}
		        
				
			} else {
		    try {
		    		
		        int tryInteger = Integer.parseInt(x);
		        char[] num=x.toCharArray();
		        if (num.length<minStartComment-1) {
			     	for (int j = 0; j < minStartComment+1-num.length; j++) {
			        	value[j]=32;
					}
			        for (int j = minStartComment+1-num.length; j < minStartComment+1; j++) {
			        	value[j]=(byte) num[j-(minStartComment+1-num.length)];
					}
					value[minStartComment+1]=47;
					value[minStartComment+2]=32;
			        byte[] charComment=val.getComment().getBytes();
			        if (charComment.length<=maxCommentLength) {
						for (int i = 0; i < charComment.length; i++) {
							value[minStartComment3+i]=charComment[i];
						}
						for (int i = charComment.length; i < maxCommentLength; i++) {
							value[minStartComment3+i]=32;
						}
					} else {
				        for (int i = minStartComment3; i < 70; i++) {
							value[i]=charComment[i-minStartComment3];
						}				
					}
					return value;

		        } else {
		        	
	    	        for (int j = 0; j < num.length; j++) {
	    	        	value[j]=(byte) num[j];
	    			}
	    			value[num.length]=47;
	    			value[num.length+1]=32;  			
	    	        byte[] charComment=val.getComment().getBytes();
	    	        if (charComment.length<=(70-num.length-2)) {
	    				for (int i = 0; i < charComment.length; i++) {
	    					value[num.length+2+i]=charComment[i];
	    				}
	    				for (int i = charComment.length; i <= (70-num.length-2); i++) {
	    					value[i]=32;
	    				}
	    			} else {
	    		        for (int i = num.length+2; i < 70; i++) {
	    					value[i]=charComment[i-num.length-2];
	    				}				
	    			}
	    			return value;

		        }
		        
		        
		    } catch (NumberFormatException e) {
		        try {
		            double d = Double.parseDouble(x);
			        char[] num=x.toCharArray();

			        if (num.length<minStartComment-1) {
				     	for (int j = 0; j < minStartComment+1-num.length; j++) {
				        	value[j]=32;
						}
				        for (int j = minStartComment+1-num.length; j < minStartComment+1; j++) {
				        	value[j]=(byte) num[j-(minStartComment+1-num.length)];
						}
						value[minStartComment+1]=47;
						value[minStartComment+2]=32;
				        byte[] charComment=val.getComment().getBytes();
				        if (charComment.length<=maxCommentLength) {
							for (int i = 0; i < charComment.length; i++) {
								value[minStartComment3+i]=charComment[i];
							}
							for (int i = charComment.length; i < maxCommentLength; i++) {
								value[minStartComment3+i]=32;
							}
						} else {
					        for (int i = minStartComment3; i < 70; i++) {
								value[i]=charComment[i-minStartComment3];
							}				
						}
						return value;

			        } else {
			        	
		    	        for (int j = 0; j < num.length; j++) {
		    	        	value[j]=(byte) num[j];
		    			}
		    			value[num.length]=47;
		    			value[num.length+1]=32;  			
		    	        byte[] charComment=val.getComment().getBytes();
		    	        if (charComment.length<=(70-num.length-2)) {
		    				for (int i = 0; i < charComment.length; i++) {
		    					value[num.length+2+i]=charComment[i];
		    				}
		    				for (int i = charComment.length; i <= (70-num.length-2); i++) {
		    					value[i]=32;
		    				}
		    			} else {
		    		        for (int i = num.length+2; i < 70; i++) {
		    					value[i]=charComment[i-num.length-2];
		    				}				
		    			}
		    			return value;

			        }
			        
			        
			        
		        } catch (NumberFormatException e2) {
		        	byte[] finalBytes= x.getBytes();
		        	if (finalBytes.length<minStartComment-1) {
		    	        for (int j = 0; j < minStartComment-finalBytes.length; j++) {
		    	        	value[j]=32;
		    			}
		    	        for (int j = minStartComment-finalBytes.length; j < minStartComment; j++) {
		    	        	value[j]=finalBytes[j-(minStartComment-finalBytes.length)];
		    			}
		    	        value[0]=39;
		    	        value[minStartComment]=39;
		    			value[minStartComment+1]=47;
		    			value[minStartComment+2]=32;
		    	        byte[] charComment=val.getComment().getBytes();
		    	        if (charComment.length<=maxCommentLength) {
		    				for (int i = 0; i < charComment.length; i++) {
		    					value[minStartComment3+i]=charComment[i];
		    				}
		    				for (int i = charComment.length; i < maxCommentLength; i++) {
		    					value[minStartComment3+i]=32;
		    				}
		    			} else {
		    		        for (int i = minStartComment3; i < 70; i++) {
		    					value[i]=charComment[i-minStartComment3];
		    				}				
		    			}
		    			return value;

					} else {
		    	        for (int j = 1; j < finalBytes.length+1; j++) {
		    	        	value[j]=finalBytes[j-1];
		    			}
		    	        value[0]=39;
		    	        value[finalBytes.length+1]=39;
		    			value[finalBytes.length+2]=47;
		    			value[finalBytes.length+3]=32;  			
		    	        byte[] charComment=val.getComment().getBytes();
		    	        if (charComment.length<(69-finalBytes.length-3)) {
		    				for (int i = 0; i < charComment.length; i++) {
		    					value[finalBytes.length+4+i]=charComment[i];
		    				}
		    				for (int i = charComment.length; i < (69-finalBytes.length-3); i++) {
		    					value[finalBytes.length+4+i]=32;
		    				}
		    			} else {
		    		        for (int i = finalBytes.length+4; i < 70; i++) {
		    					value[i]=charComment[i-finalBytes.length-4];
		    				}				
		    			}

		    	        return value;
						
					}

		        }
		    }
			}
		}
		
		public static byte[] getValueComment(HeaderValue val) { 
			byte[] charComment=val.getValue().toString().getBytes();
			byte[] value=new byte[72];
			for (int i = 0; i < charComment.length; i++) {
				value[i]=charComment[i];
			}
			for (int i = charComment.length; i < value.length; i++) {
				value[i]=32;
			}
			return value;
		}

		private static byte[] getBytes(String value) {
			
			char[] chars=value.toCharArray();
			byte[] output = new byte[chars.length];
			for (int i = 0; i < output.length; i++) {
				output[i]= (byte) chars[i];
				//System.out.println(output[i] + " " + chars[i] );
			}
			return output;
		}
		
}
