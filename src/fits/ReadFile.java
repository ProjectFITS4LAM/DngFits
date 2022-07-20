/*
 * ReadFile.java
 * version 1.0
 * 14 July 2022
 * Copyright 2022 Giuliano Giuffrida
 * GNU General Public License v3.0
 *
 *
 */

package fits;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

/**
 * From File to RandomAccessFile
 * @author Giuliano Giuffrida
 * @version 1.0
 *
 */
public class ReadFile {

	/**
	 * 
	 * @param filename Il nome del file completo di path
	 * @return Oggetto di tipo RandomAccessFile
	 * @throws FileNotFoundException
	 */
	public static RandomAccessFile read(File file) throws FileNotFoundException {
		return 	new RandomAccessFile(file, "r");

	}
}