package clobs.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * Bietet statische Methode getCalendar an @see {@link #getCalendar(String, String)}
 *
 */
public final class StringToCalendar {
	/**
	 * Wandelt den Datum-String in Calendar-Daten um. 
	 * @param dateString String: Datum und Zeit
	 * @param format String: ? was ist das ?
	 * @return Calendar: Datum und Zeit
	 */
	public final static Calendar getCalendar(String dateString, String format){
		
		Date date;
		DateFormat formatter = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance();
		try {
			date = (Date)formatter.parse(dateString);
			cal.setTime(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cal;
	}
}
