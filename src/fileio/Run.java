/*
 * Run.java
 * version 1.0
 * 14 July 2022
 * Copyright 2022 Giuliano Giuffrida, Biblioteca Apostolica Vaticana
 * GNU General Public License v3.0
 *
 *
 */

package fileio;

import java.io.File;
import java.io.IOException;

import fits.HeaderParameters;
/**
 * Main class
 * @author Giuliano Giuffrida
 * @version 1.0
 */
public class Run {

	public static void main(String[] args) {
		
		if (args.length<3) {			
			System.out.println("dng to fits conversion : java -jar DngFits.jar dng2fits input.dng output.fits");
			System.out.println("fits to dng conversion : java -jar DngFits.jar fits2dng input.fits output.dng");
		} else {
			if (args[0].equals("fits2dng")) {
				
				System.out.println(args[1] + " --> " + args[2]);
				File input = new File(args[1]);
				File output = new File(args[2]);
				try {
					WriteDng.convert(input, output);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			if (args[0].equals("dng2fits")) {
				System.out.println(args[1] + " --> " + args[2]);

				File input = new File(args[1]);
				File output = new File(args[2]);
				HeaderParameters headParams=new HeaderParameters();
				try {
					WriteFits.convert(input, output, headParams.getHeaderParams());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}
		
	}

}
