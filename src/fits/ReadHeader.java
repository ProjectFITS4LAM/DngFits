/*
 * ReadHeader.java
 * version 1.0
 * 14 July 2022
 * Copyright 2022 Giuliano Giuffrida, Biblioteca Apostolica Vaticana
 * GNU General Public License v3.0
 *
 *
 */

package fits;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

/**
 * Class used to read the Header of a FITS file
 * @author Giuliano Giuffrida
 * @version 1.0
 *
 */
public class ReadHeader {

	/**
	 * Read blocks of 2880 bytes until END key is detected
	 * @param file RandomAccessFile
	 * @return Header object
	 * @throws IOException
	 */
	public static Header read(RandomAccessFile file) throws IOException {
		boolean stillHead=true;
		ArrayList<byte[]> head = new ArrayList<byte[]> ();
		int endline=0;
		while (stillHead) {
			byte[] header = new byte[2880];
			file.readFully(header);
			head.add(header);
			for (int i = 0; i < 36; i++) {
				byte[] key = new byte[8];
				for (int j = 0; j < 8; j++) {
					key[j] = header[j + i*80];
				}	
				if (key[0]==69 && key[1]==78 && key[2]==68) {
					stillHead=false;
					endline=i + 36*(head.size() -1);
				}
			}
			
		}
		
		byte[] headerFull = new byte[2880*head.size()];
		for (int i = 0; i < head.size(); i++) {
			byte[] headpart=head.get(i);
			for (int j = 0; j < 2880; j++) {
				headerFull[2880*i+j]=headpart[j];
			}
		}
		return new Header(headerFull, endline);
	}

}
