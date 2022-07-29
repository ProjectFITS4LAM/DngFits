/*
 * HeaderParameters.java
 * version 1.0
 * 14 July 2022
 * Copyright 2022 Giuliano Giuffrida, Biblioteca Apostolica Vaticana
 * GNU General Public License v3.0
 *
 *
 */

package fits;

import java.util.LinkedHashMap;

/**
 * Dummy header parameters 
 * @author Giuliano Giuffrida
 * @version 1.0
 */
public class HeaderParameters {
	LinkedHashMap<String,HeaderValue> headerParams;
	public HeaderParameters() {
		headerParams = new LinkedHashMap<String,HeaderValue>();

	}
	public LinkedHashMap<String, HeaderValue> getHeaderParams() {
		if (headerParams.isEmpty()) {
			dummyParams();
		}
		return headerParams;
	}
	
	public void dummyParams() {
		headerParams.put("EXTEND", new HeaderValue("T", "Extensions are permitted"));
		headerParams.put("COMMENT", new HeaderValue("This file uses Vatican Library keywords for RAW files", ""));
		headerParams.put("CREATOR", new HeaderValue("DngFits 1.0 beta", "Software that created this FITS file")); //TODO: temp
		headerParams.put("PROGRAM", new HeaderValue("Adobe DNG Converter", "Software that created the image")); //TODO: temp
		headerParams.put("AUTHOR", new HeaderValue("BAV", "Artist name")); //TODO: temp
		headerParams.put("ORIGIN", new HeaderValue("Biblioteca Apostolica Vaticana", "Copyright notice")); //TODO: temp
		headerParams.put("OBJECT", new HeaderValue("Ott.gr.62", "Item identification")); //TODO: temp
		headerParams.put("CUNIT1", new HeaderValue("mm", "Measurement Unit for axis 1")); //TODO: temp
		headerParams.put("CUNIT2", new HeaderValue("mm", "Measurement Unit for axis 2")); //TODO: temp
		headerParams.put("CDELT1", new HeaderValue("0.0635", "Coordinate increment along axis 1")); //TODO: temp
		headerParams.put("CDELT2", new HeaderValue("0.0635", "Coordinate increment along axis 2")); //TODO: temp
		
	}

}
