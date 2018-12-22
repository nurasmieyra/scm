package clobs.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
/**
 * Bietet statische Methode getDates an @see {@link #getDates(String, String)}
 *
 */
public final class WeeklyDates {
	
	private WeeklyDates() {
		
	}
	/**
	 * Stellt die Daten für wöchentlich wiederkehrende Buchungen bereit. 
	 * @param StartDate String:
	 * @param EndDate String:
	 * @return Vector<String>: Vektor der Daten für wöchentlich wiederkehrende Buchungen
	 */
	public final static Vector<String> getDates(String startDate, String endDate) {
		
		Vector<String> result = new Vector<String>();
		
		DateFormat formatter = null ; 
		Date date1 ;
		Date date2 ;


		formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date1 = (Date)formatter.parse(startDate);
			date2 = (Date)formatter.parse(endDate);
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			cal1.setTime(date1);
			cal2.setTime(date2);
			cal2.add(Calendar.DAY_OF_MONTH, -7);
			result.add(formatter.format((cal1.getTime())));
			while(cal1.before(cal2)) {
				
				cal1.add(Calendar.DAY_OF_MONTH, 7);
				result.add(formatter.format((cal1.getTime())));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

}
