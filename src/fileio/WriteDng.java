/*
 * WriteDng.java
 * version 1.0
 * 14 July 2022
 * Copyright 2022 Giuliano Giuffrida
 * GNU General Public License v3.0
 *
 *
 */

package fileio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;
import java.util.Map;

import fits.Header;
import fits.ReadData;
import fits.ReadFile;
import fits.ReadHeader;
import tiff.TiffDirectory;
import tiff.TiffHandle;
import tiff.TiffWrite;
import tiff.TiffTag;
/**
 * File Adobe DNG writer starting from FITS created by WriteFits class
 * @author Giuliano Giuffrida
 * @version 1.0
 */
public class WriteDng {

	
//	static DngHandle outputHandle0;
//	static DngHandle outputHandle;
	static TiffHandle outputHandle;
	/**
	 * Convert an input fits file on a DNG file
	 * @param input
	 * @param output
	 * @throws IOException
	 */
	public static void convert(File input, File output) throws IOException {
		
		RandomAccessFile rafFits = ReadFile.read(input);
		RandomAccessFile raf = new RandomAccessFile(output, "rw");
		Header headerobj = ReadHeader.read(rafFits);
		byte[] header = headerobj.getHeader();
		Map<String, String> headerMap = headerobj.getMap();
		//
		int pixels=Integer.parseInt(headerMap.get("NAXIS1  ").trim());
		int scanlines=Integer.parseInt(headerMap.get("NAXIS2  ").trim());
		int samples = 1;
		int bits=16;
		int type=0;
//		outputHandle0 = new DngHandle("II");

		String dngv=headerMap.get("DNGVERSI");
		String[] dngv_a=dngv.replace("'", "").trim().split("\\.");
		long[] dngVersion= new long[] {
				Long.parseLong(dngv_a[0]),
				Long.parseLong(dngv_a[1]),
				Long.parseLong(dngv_a[2]),
				Long.parseLong(dngv_a[3])
				
		};
		
    outputHandle = new TiffHandle("II");
    TiffDirectory ifd0 = new TiffDirectory(outputHandle);
    ifd0.setDNGFlag(true);
	outputHandle.appendTiffDirectory(ifd0);
    
    ifd0.setTiffTag("DNGVersion", dngVersion);
    String model=headerMap.get("INSTRUME");
    ifd0.setTiffTag("UniqueCameraModel", model.replace("'", "").trim()+'\0');
    String make=headerMap.get("MAKE    ");
    ifd0.setTiffTag("Make", make.replace("'", "").trim()+'\0');
    putArray(headerMap, "COLOR1", "ColorMatrix1", findArrayLength(headerMap, "COLOR1"), ifd0);
    putArray(headerMap, "COLOR2", "ColorMatrix2", findArrayLength(headerMap, "COLOR2"), ifd0);
    putArray(headerMap, "CAMCA1", "CameraCalibration1", findArrayLength(headerMap, "CAMCA1"), ifd0);
    putArray(headerMap, "CAMCA2", "CameraCalibration2", findArrayLength(headerMap, "CAMCA2"), ifd0);
    putArray(headerMap, "ASSHOTN", "AsShotNeutral", findArrayLength(headerMap, "ASSHOTN"), ifd0);
    putArray(headerMap, "DNGLENS", "DNGLENSINFO", findArrayLength(headerMap, "DNGLENS"), ifd0);
    
    putValue(headerMap, "CALILL1", "CalibrationIlluminant1", ifd0);
    putValue(headerMap, "CALILL2", "CalibrationIlluminant2", ifd0);
    
    String lensmodel=headerMap.get("LENSMOD ");
    if (lensmodel!=null) {
        ifd0.setTiffTag("LensModel", lensmodel.replace("'", "").trim()+'\0'); 		
	}
    putArray(headerMap, "LENSINF", "LENSINFO", findArrayLength(headerMap, "LENSINF"), ifd0);
    putArray(headerMap, "FOCALLE", "FOCALLENGTH", findArrayLength(headerMap, "FOCALLE"), ifd0);
    putValue(headerMap, "FOCAL35M", "FOCALLENGTHIN35MMFORMAT", ifd0);
    putArray(headerMap, "APERTUR", "APERTUREVALUE", findArrayLength(headerMap, "APERTUR"), ifd0);
    putArray(headerMap, "MAXAPER", "MAXAPERTUREVALUE", findArrayLength(headerMap, "MAXAPER"), ifd0);
    putArray(headerMap, "FCXPRIV", "FOCALPLANEXRESOLUTIONPRIVATE", findArrayLength(headerMap, "FCXPRIV"), ifd0);
    putArray(headerMap, "FCYPRIV", "FOCALPLANEYRESOLUTIONPRIVATE", findArrayLength(headerMap, "FCYPRIV"), ifd0);
    putValue(headerMap, "FCUNITPR", "FocalPlaneResolutionUnitPrivate", ifd0);
    putValue(headerMap, "ISOSPEED", "ISOSpeedRatings", ifd0);
    putArray(headerMap, "EXPTIME", "EXPOSURETIME", findArrayLength(headerMap, "EXPTIME"), ifd0);
    putArray(headerMap, "SHSPEED", "SHUTTERSPEEDVALUE", findArrayLength(headerMap, "SHSPEED"), ifd0);
    putValue(headerMap, "COLSPACE", "COLORSPACE", ifd0);
    putArray(headerMap, "FNUMBER", "FNUMBER", findArrayLength(headerMap, "FNUMBER"), ifd0);
    putValue(headerMap, "WHITEBAL", "WHITEBALANCE", ifd0);
    putArray(headerMap, "FORWA1", "ForwardMatrix1", findArrayLength(headerMap, "FORWA1"), ifd0);
    putArray(headerMap, "FORWA2", "ForwardMatrix2", findArrayLength(headerMap, "FORWA2"), ifd0);

    putArray(headerMap, "BASEEXP", "BaselineExposure", findArrayLength(headerMap, "BASEEXP"), ifd0);
    putArray(headerMap, "BASENOI", "BaselineNoise", findArrayLength(headerMap, "BASENOI"), ifd0);
    putArray(headerMap, "BASESH", "BaselineSharpness", findArrayLength(headerMap, "BASESH"), ifd0);
    String SerialNumber=headerMap.get("SERIALN ");
    if (SerialNumber!=null) {
        ifd0.setTiffTag("SerialNumber", SerialNumber.replace("'", "").trim()+'\0'); 		
	}
    putValue(headerMap, "CFALAYOU", "CFALayout", ifd0);
    putArray(headerMap, "CFAPLC", "CFAPlaneColor", findArrayLength(headerMap, "CFAPLC"), ifd0);
    putArrayDouble(headerMap, "NOISEP", "NoiseProfile", findArrayLength(headerMap, "NOISEP"), ifd0);
    putValue(headerMap, "ORIENTAT", "Orientation", ifd0);

    

 		
//		outputHandle0.setTiffTag("DNGVersion", dngVersion);
//		String model=headerMap.get("INSTRUME");
//		outputHandle0.setTiffTag("UniqueCameraModel", model.replace("'", "").trim()+'\0');
//		putArray(headerMap, "COLOR1", "ColorMatrix1", findArrayLength(headerMap, "COLOR1"), true);
//		putArray(headerMap, "COLOR2", "ColorMatrix2", findArrayLength(headerMap, "COLOR2"), true);
//		putArray(headerMap, "ASSHOTN", "AsShotNeutral", findArrayLength(headerMap, "ASSHOTN"), true);
//		putValue(headerMap, "CALILL1", "CalibrationIlluminant1", true);
//		putValue(headerMap, "CALILL2", "CalibrationIlluminant2", true);
//		long subifd=outputHandle0.getBytes(0).length+12;
//		outputHandle0.setTiffTag("SubIFDS", subifd);
		
    TiffDirectory subIFD = new TiffDirectory(outputHandle);
    subIFD.setDNGFlag(true);  // This tells this directory not to make an EXIF sub IFD for EXIF tags within this directory
    subIFD.insertImage("Full", pixels, scanlines, samples, bits, type);
    subIFD.setTiffTag("Compression", 1);
    
//    outputHandle = new DngHandle("II", pixels, scanlines, samples, bits, type);
//    outputHandle.setTiffTag("NewSubfileType", 0);
//    outputHandle.setTiffTag("ImageWidth", pixels);
//    outputHandle.setTiffTag("ImageLength", scanlines);
//    outputHandle.setTiffTag("BitsPerSample", bits);
//    outputHandle.setTiffTag("Compression", 1);
    
    
		putValue(headerMap, "PHOTOINT", "PhotometricInterpretation", subIFD);		
//		outputHandle.setTiffTag("SamplesPerPixel", 1);
//		outputHandle.setTiffTag("RowsPerStrip", scanlines);
//		outputHandle.setTiffTag("StripByteCounts", pixels*scanlines*2);
		putArray(headerMap, "CFARPDI", "CFARepeatPatternDim", findArrayLength(headerMap, "CFARPDI"), subIFD);
		putArray(headerMap, "CFAPATT", "CFAPattern", findArrayLength(headerMap, "CFAPATT"), subIFD);
		putArray(headerMap, "CFAPLAN", "CFAPlaneColor", findArrayLength(headerMap, "CFAPLAN"), subIFD);
		putArray(headerMap, "BLACKRE", "BlackLevelRepeatDim", findArrayLength(headerMap, "BLACKRE"), subIFD);
		putArray(headerMap, "BLACKLE", "BlackLevel", findArrayLength(headerMap, "BLACKLE"), subIFD);
		putArray(headerMap, "CROPORI", "DefaultCropOrigin", findArrayLength(headerMap, "CROPORI"), subIFD);
		putArray(headerMap, "CROPSIZ", "DefaultCropSize", findArrayLength(headerMap, "CROPSIZ"), subIFD);
		putValue(headerMap, "BAYGREEN", "BayerGreenSplit", subIFD);
		putValue(headerMap, "PLANCONF", "PlanarConfiguration", subIFD);
		putValue(headerMap, "WHITELEV", "WhiteLevel", subIFD);
		putArray(headerMap, "ACTAREA", "ActiveArea", findArrayLength(headerMap, "ACTAREA"), subIFD);
		putArray(headerMap, "ANBAL", "AnalogueBalance", findArrayLength(headerMap, "ANBAL"), subIFD);
		
		putArray(headerMap, "DISTANC", "SubjectDistance", findArrayLength(headerMap, "DISTANC"), ifd0);

		String aux1=headerMap.get("AUX1    ");
		String aux2=headerMap.get("AUX2    ");
		String aux3=headerMap.get("AUX3    ");
		String aux4=headerMap.get("AUX4    ");
		String aux5=headerMap.get("AUX5    ");
		String aux6=headerMap.get("AUX6    ");
		String aux7=headerMap.get("AUX7    ");
		String xmpFirst="<?xpacket begin=\"\" id=\"W5M0MpCehiHzreSzNTczkc9d\"?>\r\n" + 
				"<x:xmpmeta xmlns:x=\"adobe:ns:meta/\" x:xmptk=\"Adobe XMP Core 5.6-c140 79.160451, 2017/05/06-01:08:21        \">\r\n" + 
				" <rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\">\r\n" + 
				"  <rdf:Description rdf:about=\"\"\r\n" + 
				"   xmlns:aux=\"http://ns.adobe.com/exif/1.0/aux/\"\r\n";
		String xmpLast=	"  </rdf:Description>\r\n" + 
				" </rdf:RDF>\r\n" + 
				"</x:xmpmeta>";
		if (aux1!=null) {
			xmpFirst=xmpFirst +
					"   aux:LensInfo=\""+  aux1.replace("'", "").trim()   +"\"\r\n";
		}
		if (aux2!=null) {
			xmpFirst=xmpFirst +
					"   aux:Lens=\""+  aux2.replace("'", "").trim()  +"\"\r\n";
		}
		if (aux3!=null) {
			xmpFirst=xmpFirst +
					"   aux:LensID=\""+  aux3.replace("'", "").replaceAll("\\s","").trim()   +"\"\r\n";
		}
		if (aux4!=null) {
			xmpFirst=xmpFirst +
					"   exifEX:LensModel=\""+  aux4.replace("'", "").replaceAll("\\s","").trim()   +"\"\r\n";
		}
		if (aux5!=null) {
			xmpFirst=xmpFirst +
					"   crs:Temperature=\""+  aux5.replace("'", "").replaceAll("\\s","").trim()   +"\"\r\n";
		}	
		if (aux6!=null) {
			xmpFirst=xmpFirst +
					"   crs:Tint=\""+  aux6.replace("'", "").replaceAll("\\s","").trim()   +"\"\r\n";
		}	
		if (aux7!=null) {
			xmpFirst=xmpFirst +
					"   aux:ApproximateFocusDistance=\""+  aux7.replace("'", "").replaceAll("\\s","").trim()   +"\"\r\n";
		}
		
/*		
		xmpFirst=xmpFirst +
				"   crs:Version=\"9.9\"\r\n"
		   + "   crs:Version=\"9.9\"\r\n"
		   + "   crs:ProcessVersion=\"6.7\"\r\n"  
		   + "   crs:WhiteBalance=\"As Shot\"\r\n";

		
		*/
		xmpFirst=xmpFirst+">\r\n";
		String xmp = xmpFirst+xmpLast;
			//	"   aux:LensInfo=\"600/10 600/10 28/10 28/10\"\r\n" + 
			//	"   aux:Lens=\"60.0 mm f/2.8\"\r\n" + 
			//	"   aux:LensID=\"49\">\r\n" + 
		char[] xmpChar = xmp.toCharArray();
		long[] xmpLong = new long[xmpChar.length];
		for (int i = 0; i < xmpLong.length; i++) {
			xmpLong[i] = (byte) xmpChar[i];
		}
		ifd0.setTiffTag("XMP", xmpLong);
		
				
//		long StripOffsets=outputHandle.getBytesNoHeader(subifd).length + subifd + 12;
//		outputHandle.setTiffTag("StripOffsets", StripOffsets);
		
		
		ifd0.insertSubDirectory(subIFD);
		outputHandle.setImageDirectory(subIFD);
				

		/*
		 * writing
		 */
		TiffWrite outputData = new TiffWrite(raf, outputHandle);
		byte[] buf = outputHandle.getBytes();
		try { raf.write(buf); 
		    
		 }
		catch(IOException e) { e.printStackTrace(); }
		

//		outputData = new DngWrite(raf, outputHandle);
//		   buf = outputHandle.getBytesNoHeader(subifd);
//		    try { 
//		    	raf.write(buf); 
//		    }
//		    catch(IOException e) { e.printStackTrace(); }
		    
		    
		    // Write out all of the scanlines
		    for (int n=0; n < scanlines; n++)
		      {
		    	byte[] line = ReadData.readDataBuffer(rafFits, (pixels*(bits/8)));
			    byte[] newline = new byte[line.length];
			    for (int i = 0; i < line.length/2; i++) {
					newline[2*i]= line[2*i+1];
				    newline[2*i+1]= line[2*i];
				}
		        outputData.putNextScanlineByteData(newline);
		      }
		    try { raf.close(); }
		    catch(FileNotFoundException e) { System.out.println("File not found."); }
		    catch(IOException e) { e.printStackTrace(); }
	}
	
	/**
	 * Determine the length of an array written on the fits header on multiple keys
	 * @param headerMap
	 * @param headerkey
	 * @return
	 */
	private static int findArrayLength(Map<String, String> headerMap, String headerkey) {
		String firstkey=headerkey+"0";
		if (firstkey.length()<8) {
			int length=firstkey.length();
			for (int i = length; i < 8; i++) {
				firstkey+=" ";
			}			
		}
		String value=headerMap.get(firstkey);
		int counter=0;
		while (value!=null) {
			String key=headerkey+counter;
			if (key.length()<8) {
				int length=key.length();
				for (int k = length; k < 8; k++) {
					key+=" ";
				}			
			}
			value=headerMap.get(key);
			if (value!=null) {
				counter++;
			}
		}
		return counter;
	}
	/**
	 * Put an array of values coming from fits header on a dng tag
	 * @param headerMap
	 * @param headerkey
	 * @param tifftag
	 * @param tagcount
	 * @param ismainifd
	 */
	private static void putArray(Map<String, String> headerMap, String headerkey, String tifftag, int tagcount, TiffDirectory tiffDir) {
		String firstkey=headerkey+"0";
		if (firstkey.length()<8) {
			int length=firstkey.length();
			for (int i = length; i < 8; i++) {
				firstkey+=" ";
			}			
		}
		String firstvalue=headerMap.get(firstkey);
		if (firstvalue!=null) {
			long[] values=new long[tagcount];
			for (int i = 0; i < values.length; i++) {
				String key=headerkey+i;
				if (key.length()<8) {
					int length=key.length();
					for (int k = length; k < 8; k++) {
						key+=" ";
					}			
				}
				values[i]=Long.parseLong(headerMap.get(key).trim());
			}
			tiffDir.setTiffTag(tifftag, values);

		}
	}
	/**
	 * Put an array of values coming from fits header on a dng tag
	 * @param headerMap
	 * @param headerkey
	 * @param tifftag
	 * @param tagcount
	 * @param ismainifd
	 */
	private static void putArrayDouble(Map<String, String> headerMap, String headerkey, String tifftag, int tagcount, TiffDirectory tiffDir) {
		String firstkey=headerkey+"0";
		if (firstkey.length()<8) {
			int length=firstkey.length();
			for (int i = length; i < 8; i++) {
				firstkey+=" ";
			}			
		}
		String firstvalue=headerMap.get(firstkey);
		if (firstvalue!=null) {
			double[] values=new double[tagcount];
			for (int i = 0; i < values.length; i++) {
				String key=headerkey+i;
				if (key.length()<8) {
					int length=key.length();
					for (int k = length; k < 8; k++) {
						key+=" ";
					}			
				}
				values[i]=Double.parseDouble(headerMap.get(key).trim().replace("'", ""));
			}
			tiffDir.setTiffTag(tifftag, values);

		}
	}
	/**
	 * Put value coming from fits header on a dng tag
	 * @param headerMap
	 * @param headerkey
	 * @param tifftag
	 * @param ismainifd
	 */
	private static void putValue(Map<String, String> headerMap, String headerkey, String tifftag, TiffDirectory tiffDir) {
		if (headerkey.length()<8) {
			int length=headerkey.length();
			for (int i = length; i < 8; i++) {
				headerkey+=" ";
			}			
		}
		String value=headerMap.get(headerkey);
		if (value!=null) {
			long valueLong=Long.parseLong(value.trim());
			tiffDir.setTiffTag(tifftag, valueLong);
		}

	}
}
