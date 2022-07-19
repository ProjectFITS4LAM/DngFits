package fits;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Oggetto di tipo Header, contiene l'Header come byte[] e il numero di riga contenente la chiave "END"
 * @author g.giuffrida
 *
 */
public class Header {

	byte[] header;
	int endline;
	
	public Header(byte[] header, int endline) {
		this.header=header;
		this.endline=endline;
	}

	public byte[] getHeader() {
		return header;
	}

	public int getEndline() {
		return endline;
	}
	/**
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public LinkedHashMap<String,String> getMap() throws UnsupportedEncodingException {
		//TODO : fix CONTINUE for comments
		int numBlocks=header.length/2880;
		LinkedHashMap<String,String> headerMap = new LinkedHashMap<String,String>();
		int countComments=0;
		for (int i = 0; i < 36*numBlocks; i++) {
			byte[] key = new byte[8];
			byte[] eq = new byte[2];
			byte[] value = new byte[70];
			for (int j = 0; j < 8; j++) {
				key[j] = header[j + i*80];
			}	
			for (int j = 8; j <= 9; j++) {
				eq[j-8] = header[j + i*80];
			}	
			for (int j = 10; j < 80; j++) {
				value[j-10] = header[j + i*80];
			}	
			String keyst=new String(key, "ASCII");

			if (!keyst.equals("COMMENT ") && !keyst.equals("CONTINUE")) {
				byte[] newvalue = getValueWithoutComment(value);
				headerMap.put(new String(key, "ASCII"), new String(newvalue, "ASCII"));
				
			} else {
				if (keyst.equals("COMMENT ")) {
					headerMap.put(keyst+countComments, new String(eq, "ASCII")+new String(value, "ASCII"));
					countComments++;
				} else { 
					
					
					byte[] keyOld = new byte[8];
					for (int j = 0; j < 8; j++) {
						keyOld[j] = header[j + (i-1)*80];
					}	
					String valueOld=headerMap.get(new String(keyOld, "ASCII"));
					valueOld=valueOld.replace("&'", "");
					//prendo continue e sommo, anche con eq visto usanza bav
					byte[] newvalue = getValueWithoutComment(value);
					String toadd=new String(newvalue, "ASCII");
					valueOld+=toadd.substring(1).replace("&'", "");
					boolean stillcontinue=true;
					int k=1;
					while (stillcontinue) {
						for (int j = 0; j < 8; j++) {
							key[j] = header[j + (i+k)*80];
						}	
						keyst=new String(key, "ASCII");
						if (!keyst.equals("CONTINUE")) {
							stillcontinue=false;
						} else {
							for (int j = 8; j <= 9; j++) {
								eq[j-8] = header[j + (i+k)*80];
							}	
							for (int j = 10; j < 80; j++) {
								value[j-10] = header[j + (i+k)*80];
							}	
							byte[] newvalue1 = getValueWithoutComment(value);
							toadd=new String(newvalue1, "ASCII");
							valueOld+=toadd.substring(1).replace("&'", "");
							k++;
						}

					}
					headerMap.put(new String(keyOld, "ASCII"), valueOld);
					i+=(k-1);
				}
			}
		}
		return headerMap;
	}
	
	public static byte[] getValueWithoutComment(byte[] value) {

		//check comment
		boolean comment=false;
		int indexComment=0;
		boolean isString=false;
		while (!comment) {
			if (value[indexComment]==39) {
				isString=true;
			}
			if (isString && indexComment>0 ) {
				if (indexComment==69) {
					comment=true;
				} else if (value[indexComment]==47) {
					int countQuote=0;
					for (int j = 0; j < indexComment; j++) {
						if (value[j]==39) {
							countQuote++;
						}
					}
					if (countQuote%2==0) {
						comment=true;
					}
				}
				
				
				if ((value[indexComment-1]==39 && value[indexComment]==47) || indexComment==69) {
					comment=true;
				}
				
			} else {
				if (value[indexComment]==47 || indexComment==69) {
					comment=true;
				}
			}
			indexComment++;
		}
		if (comment) {
			byte[] newvalue= new byte[indexComment-1];
			for (int j = 0; j < newvalue.length; j++) {
				newvalue[j]=value[j];
			}
			return newvalue;
		} else {
			return value;			
		}
	
	}
}
