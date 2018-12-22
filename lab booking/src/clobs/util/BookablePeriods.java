package clobs.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import clobs.DBConnection;

/**
 * Bietet statische Methode getPeriods an @see {@link #BookablePeriods()}
 *
 */

public final class BookablePeriods {
	
	public BookablePeriods() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * Überprüft ob ein Item oder Location an einem Datum im gegebenen Interval verfügbar ist. 
	 * @param id int: Item (PC) oder location (Lab) ID
	 * @param bookingStartDatetime String: muss dasgleiche Datum wie bookingEndDatetime haben
	 * @param bookingEndDatetime String:
	 * @param ofTyp String: Item oder Location
	 * @return Vector der verfuegbaren Perioden
	 */
	public final static Vector<Vector<String>> getPeriods(int id, String bookingStartDatetime, String bookingEndDatetime, String ofTyp) {
		
		Vector<Vector<String>> periods;
		periods = new Vector<Vector<String>>();
		ResultSet res = null;
		DBConnection db = new DBConnection();
		String searchQuery = null;
		if(ofTyp.equalsIgnoreCase("Item")) {
			
			searchQuery = "call getItemBookablePeriodsOnDate(" + id + ", '" + bookingStartDatetime + "', '" + bookingEndDatetime + "')";
		} else if(ofTyp.equalsIgnoreCase("Location")) {
			
			searchQuery = "call getLocationBookablePeriodsOnDate(" + id + ", '" + bookingStartDatetime + "', '" + bookingEndDatetime + "')";
		} else {
			
		}
		res = db.excuteQ(searchQuery);
		try {
			while(res.next()) {
				
				Vector<String> period = new Vector<String>();
				period.add(res.getString("start"));
				period.add(res.getString("end"));
				periods.add(period);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		db.close();
		return periods;
	}
	
}
