package fits;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

/**
 * Accede al file fits e ritorna un oggetto di tipo RandomAccessFile
 * @author g.giuffrida
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
