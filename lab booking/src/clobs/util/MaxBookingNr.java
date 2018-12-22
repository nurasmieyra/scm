package clobs.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import clobs.DBConnection;

/**
 * Bietet statische Methode getMaxNr() an @see {@link #getMaxNr()}
 *
 */
public final class MaxBookingNr {

	private MaxBookingNr() {
		
	}
	
	/**
	 * Liefert momentan größte Buchungsnummer und um 1 erhöhen. Bei einer Buchung mit mehreren Gegenstände muss diese Nummer vorher ausgelesen
	 * und für alle Gegenstände anwandt werden. Verwendet von Booking.createBooking()
	 * 
	 * @return int
	 */
	public final static int getMaxNr() {
		
		int maxNr = 0;
		DBConnection db;
		db = new DBConnection();
		ResultSet res = null;
		String queryStmt = "select max(b.booking_nr) as maxnr from bookings b";
		res = db.excuteQ(queryStmt);
		
		try {
			res.first();
			maxNr = res.getInt("maxnr") + 1; 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		db.close();
		return maxNr;
	}
}
