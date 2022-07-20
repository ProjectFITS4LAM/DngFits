/*
 * ReadData.java
 * version 1.0
 * 14 July 2022
 * Copyright 2022 Giuliano Giuffrida
 * GNU General Public License v3.0
 *
 *
 */

package fits;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Read the image data array
 * 
 * @author Giuliano Giuffrida
 * @version 1.0
 *
 */
public class ReadData {

	/**
	 *  Read the image data array and return a byte[]
	 * 
	 * @param file
	 *             input file RandomAccessFile
	 * @param header
	 *             Header object
	 * @return image data array as byte[]
	 * @throws IOException
	 */
	public static byte[] readData(RandomAccessFile file, Header header) throws IOException {

		int[] naxis = getNaxis(header.getHeader());
		int bits=naxis[3];
		byte[] data = new byte[naxis[0] * naxis[1] * naxis[2]*(bits/8)];
		file.readFully(data);
		return data;
	}
	/**
	 * Read a portion of the image data array
	 * 
	 * @param file Input file RandomAccessFile
	 * @param bufferSize num of bytes to be read
	 * @return
	 * @throws IOException
	 */
	public static byte[] readDataBuffer(RandomAccessFile file, int bufferSize) throws IOException {
		byte[] data = new byte[bufferSize];
		file.readFully(data);
		return data;		
	}
	
	/**
	 * Read from header the 3 values NAXIS1, NAXIS2, NAXIS3 and BITPIX
	 * 
	 * 
	 * @param header
	 *            L'header come array di byte
	 * @return
	 */
	private static int[] getNaxis(byte[] header) {
		
		int[] naxis=new int[4];
		boolean result=false;
		byte[] bitpix = new byte[70];
		byte[] value1 = new byte[70];
		byte[] value2 = new byte[70];
		byte[] value3 = new byte[70];
		boolean resultbit=false;
		boolean result1=false;
		boolean result2=false;
		boolean result3=false;
		for (int j = 10; j < 80; j++) {
			bitpix[j-10] = header[j + 1*80];
			value1[j-10] = header[j + 3*80];
			value2[j-10] = header[j + 4*80];
			value3[j-10] = header[j + 5*80];
		}	
		if (returnNumberIfValid(bitpix) > 0) {
			resultbit=true;
		}
		if (returnNumberIfValid(value1) > 0) {
			result1=true;
		}
		if (returnNumberIfValid(value2) > 0) {
			result2=true;
		}
		if (returnNumberIfValid(value3) > 0) {
			result3=true;
		}
		if (resultbit && result1 && result2 && result3) {
			result=true;
		}
		if (result) {
			naxis[0]=returnNumberIfValid(value1);
			naxis[1]=returnNumberIfValid(value2);
			naxis[2]=returnNumberIfValid(value3);
			naxis[3]=returnNumberIfValid(bitpix);
		}
		return naxis;
	}

	/**
	 * check if a byte[] contain a int and return it, otherwise return -9999
	 * 
	 * @param value
	 *           
	 * @return 
	 */
	public static int returnNumberIfValid(byte[] value) {
		int number = -9999;
		boolean stillvalue = true;
		int num = 0;
		List<Integer> indexes = new ArrayList<Integer>();
		while (stillvalue) {
			if (value[num] != 32 && value[num] != 47) {
				indexes.add(num);
			}
			if (value[num] == 47 || num == 69) {
				stillvalue = false;
			}
			num++;
		}
		boolean valid = true;
		for (int i = 1; i < indexes.size(); i++) {
			if (indexes.get(i) != (indexes.get(i - 1) + 1)) {
				valid = false;
			}
		}
		boolean isdouble = false;
		for (int i = 0; i < indexes.size(); i++) {
			if (value[indexes.get(i)] < 46 || value[indexes.get(i)] > 57) {
				valid = false;
			}
			if (value[indexes.get(i)] == 46) {
				isdouble = true;
			}
		}
		if (indexes.isEmpty()) {
			valid = false;
		}
		if (valid) {
			byte[] numero = new byte[indexes.size()];
			for (int i = 0; i < indexes.size(); i++) {
				numero[i] = value[indexes.get(i)];
			}
			String numerostr = "";
			try {

				numerostr = new String(numero, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (isdouble) {
				Double realNumber = Double.parseDouble(numerostr);
				number = (int) Math.round(realNumber);
			} else {
				number = Integer.parseInt(numerostr);
			}

		}
		return number;
	}

}
