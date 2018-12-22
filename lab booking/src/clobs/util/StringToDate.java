package clobs.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Bietet statische Methode getDate an @see {@link #getDate(String, String)}
 *
 */
public final class StringToDate {
	/**
	 * Wandelt den Datum-String in Date-Daten um. 
	 * @param dateString String: Datum und Zeit
	 * @param format String: ? was ist das ?
	 * @return Calendar: Datum und Zeit
	 */
	public final static Date getDate(String dateString, String format){
		
		Date date = null;
		DateFormat formatter = new SimpleDateFormat(format);
		try {
			date = (Date)formatter.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return date;
	}
}
