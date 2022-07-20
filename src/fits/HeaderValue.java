/*
 * HeaderValue.java
 * version 1.0
 * 14 July 2022
 * Copyright 2022 Giuliano Giuffrida
 * GNU General Public License v3.0
 *
 *
 */

package fits;
/**
 * Object HeaderValue, useful to store value, comment and determine value datatype
 * @author Giuliano Giuffrida
 * @version 1.0
 */
public class HeaderValue {
	Object value;
	String comment;
	String valueType;
	public HeaderValue(Object value, String comment) {
		this.value=value;
		this.comment=comment;
	}
	public Object getValue() {
		return value;
	}
	public String getComment() {
		return comment;
	}	
	/**
	 * 
	 * @return STRING, BOOLEAN, INTEGER or DOUBLE
	 */
	public String getValueType () {
		String val=value.toString();
		byte[] valueBytes = value.toString().trim().getBytes();
		String type="";
		if (valueBytes.length==0) {
			type="STRING"; //only spaces
		}
		if (valueBytes.length==1) {
			if (valueBytes[0]==84 || valueBytes[0]==70) {
				type="BOOLEAN";
			}
			else if (valueBytes[0]>=48 && valueBytes[0]<=57) {
				type="INTEGER";
			} else {
				type="STRING";
			}
		} else if (valueBytes.length>1){

			// if contain only numbers and a ONE dot is DOUBLE or INTEGER
			boolean numbers=true;
			int countDots=0;
			for (int i = 0; i < valueBytes.length; i++) {
				boolean singleNumber=false;
				boolean isdot=false;
				if (valueBytes[i]>=48 && valueBytes[i]<=57) {
					singleNumber=true;
				}
				if (valueBytes[i]==46) {
					isdot=true;
					countDots+=1;
				}
				if (!singleNumber && !isdot ) {
					//if start with - could be a number
					if (i==0 && valueBytes[i]==45) {
						numbers=true;
					} else {
						numbers=false;							
					}
				}					
			}
			if (numbers && countDots==0) {
				// if start with 0 and no dot then is string
				if (valueBytes[0]==48) {
					type="STRING";
				} else {
					type="INTEGER";
				}
			}
			if (numbers && countDots==1) {
				type="DOUBLE";
			}
			if (!numbers) {
				type="STRING";
			}
			if (countDots>1) {
				type="STRING";
			}
		}
		return type;
		
	}

}
