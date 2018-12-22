package clobs.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import clobs.DBConnection;

/**
 * Bietet statische Methode getAvailability an @see {@link #getAvailability(int, String, String, String)}
 *
 */
public final class AvailabilityCheck{
	
	private AvailabilityCheck() {}

	/**
	 * Überprüft ob ein Item oder Location an einem Datum im gegebenen Interval verfügbar ist. 
	 * @param id int: Item (PC) oder location (Lab) ID
	 * @param bookingStartDatetime String: muss dasgleiche Datum wie bookingEndDatetime haben
	 * @param bookingEndDatetime String:
	 * @param ofTyp String: Item oder Location
	 * @return Boolean: true für verfügbar, false nicht verfügbar
	 */
	public final synchronized static boolean getAvailability(int id, String bookingStartDatetime, String bookingEndDatetime, String ofTyp) {
		
		boolean checkResult = false; // Standartmäßig nicht verfügbar
		ResultSet res = null;
		DBConnection db = new DBConnection();
		String searchQuery = null;
		if(ofTyp.equalsIgnoreCase("Item")) {
			// alle Buchungen an dem Tag, wobei ausgeschlossen sind die, die vor bookingEndDatetime enden oder nach bookingStartDatetime beginnen. Jeweils mit 10 Minuten Pufferzeit
			searchQuery = "select * from bookings b where b.item_id = " + id + " and date('" + bookingStartDatetime + "') = date(b.start) and not (date_sub(b.start, interval 10 minute) >= '" + bookingEndDatetime + "') and not (date_add(b.end, interval 10 minute) <= '" + bookingStartDatetime + "')";
		} else if(ofTyp.equalsIgnoreCase("Location")) {
			searchQuery = "select l.* from locations l where exists (select b.* from bookings b, items i where b.item_id = i.id and i.location_id = l.id and date('" + bookingStartDatetime + "') = date(b.start) and not (date_sub(b.start, interval 10 minute) >= '" + bookingEndDatetime + "') and not (date_add(b.end, interval 10 minute) <= '" + bookingStartDatetime + "')) and  l.id = " + id;
		} else {
			
		}
		res = db.excuteQ(searchQuery);
		
		try {
			if(!res.first()) { // Es gibt kine Buchung in dem gegebenen Interval
				checkResult = true;
			}else {
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		db.close();
		return checkResult;
	}
	
}
