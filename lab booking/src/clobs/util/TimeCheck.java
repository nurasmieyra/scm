package clobs.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Bietet statische Methode check an @see {@link #check(String, String, String)}
 * 
 */
public final class TimeCheck {
	
	private TimeCheck() {
		
	}
	
	/**
	 * Überprüft ob es zwischen den Start- und Enduhrzeit mindestens 1 Stunde gibt.
	 * Überprüft ob Enddatum hinter oder gleich dem Startdatum.
	 *  
	 * @param start String im Form '2010-01-20' oder '15:30'
	 * @param end String im Form '2010-01-20' oder '16:30'
	 * @param type String im Form 'date', 'time' Caseinsensitiv. Ob es sich um einem Datum- oder Uhrzeitcheck handelt 
	 * @return boolen true für ok
	 */
	public final static boolean check(String start, String end, String type) {
	   
		DateFormat formatter = null ; 
		Date date1 ;
		Date date2 ;
		boolean ok = false;
		
		if(type.equalsIgnoreCase("time")) {
			formatter = new SimpleDateFormat("HH:mm");
		} else if(type.equalsIgnoreCase("date")) {
			formatter = new SimpleDateFormat("yyyy-MM-dd");
		}
		
		try {
			date1 = (Date)formatter.parse(start);
			date2 = (Date)formatter.parse(end);
			long diff;

			diff = (date2.getTime() - date1.getTime()) / 1000 / 60; //Differenz in Minuten
			if(type.equalsIgnoreCase("time") && diff >= 60) {
				ok = true;
			} else if(type.equalsIgnoreCase("date") && diff >= 0) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(date1);
				if(cal.get(Calendar.DAY_OF_WEEK) != 1 && cal.get(Calendar.DAY_OF_WEEK) != 7) {
					ok = true;
					System.out.println(cal.get(Calendar.DAY_OF_WEEK) );
				}
			}			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ok;
   }
}   

