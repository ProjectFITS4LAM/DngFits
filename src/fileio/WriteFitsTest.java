package fileio;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import fits.HeaderValue;

class WriteFitsTest {

	@Test
	void testAddContinueIfNeeded() throws IOException {
		LinkedHashMap<String,HeaderValue> header = new LinkedHashMap<String,HeaderValue>();
		char[] value = new char[150];
		char[] comment = new char[10];
		for (int i = 0; i < value.length; i++) {
			value[i]='a';
		}
		for (int i = 0; i < comment.length; i++) {
			comment[i]='c';
		}
		header.put("KEY", 
		new HeaderValue(new String(value), new String(comment)));
		LinkedHashMap<String, HeaderValue> headerNew = WriteFits.addContinueIfNeeded(header);
		boolean tooLong=false;
		for (Map.Entry<String, HeaderValue> entry : headerNew.entrySet()) {
		    System.out.println(entry.getKey() + ":" + entry.getValue().getValue() + " // " + entry.getValue().getComment());
	    	char[] valuenew = entry.getValue().getValue().toString().trim().toCharArray();
	    	String commentnew=entry.getValue().getComment();
	    	if (commentnew!=null) {
	    		if ((valuenew.length+commentnew.toCharArray().length) > 68) {
	    			tooLong=true;						
	    		}
	    	} else {
	    		if (valuenew.length>68) {
	    			tooLong=true;
				}
	    	}
		}	
		assertFalse(tooLong);
	}

	@Test
	void testSplitter() {
		fail("Not yet implemented");
	}

	@Test
	void testGetValueNew() {
		fail("Not yet implemented");
	}

}
