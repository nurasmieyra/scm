package clobs;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * Bietet public Methode createBooking an @see {@link #createBooking(int)}
 * Bietet public Methode cancelBooking an @see {@link #cancelBooking()}
 * Bietet public Methode getBookingID an @see {@link #getBookingID()}
 * Bietet public Methode setBookingID an @see {@link #setBookingID(int)}
 * Bietet public Methode getUserID an @see {@link #getUserID()}
 * Bietet public Methode setUserID an @see {@link #setUserID(int)}
 * Bietet public Methode getItemID an @see {@link #getItemID()}
 * Bietet public Methode setItemID an @see {@link #setItemID(int)}
 * Bietet public Methode getStart an @see {@link #getStart()}
 * Bietet public Methode setStart an @see {@link #setStart(String)}
 * Bietet public Methode getEnd an @see {@link #getEnd()}
 * Bietet public Methode setEnd an @see {@link #setEnd(String)}
 * Bietet public Methode getRecurrencetypeID an @see {@link #getRecurrencetypeID()}
 * Bietet public Methode setRecurrencetypeID an @see {@link #setRecurrencetypeID(int)}
 * Bietet public Methode getCategoryID an @see {@link #getCategoryID()}
 * Bietet public Methode setCategoryID an @see {@link #setCategoryID(int)}
 *
 */

public class Booking {

	private int bookingID;
	private int userID;
	private int itemID;
	private String start;
	private String end;
	private int recurrencetypeID;
	private int categoryID;

	/**
	 * Konstruktor
	 */
	Booking() {
		
	}
	
	/**
	 * 
	 * @param userID
	 * @param itemID
	 * @param start
	 * @param end
	 * @param recurrencetypeID
	 * @param categoryID
	 */
	public Booking(int userID, int itemID, String start, String end, int recurrencetypeID, int categoryID){
		this.userID = userID;
		this.itemID = itemID;
		this.start = start;
		this.end = end;
		this. recurrencetypeID = recurrencetypeID;
		this.categoryID = categoryID;
	}
	
	/**
	 * Legt eine Buchung an. 
	 * @param maxNr int: Die eigentliche Buchungsnummer. Eine Buchung mit mehrerer Gegenstände werden 
	 * eine gemeinsame Buchungsnummer bekommen 
	 * @return Boolean: true f�r erfolgreich, false nicht erfolgreich
	 */
	public synchronized boolean createBooking(int maxNr) {
		
		boolean done = false;
		DBConnection db;
		db = new DBConnection();
		String createStmt = null;
		try {
			db.conn.setAutoCommit(false);
			createStmt = "INSERT INTO bookings(user_id, item_id, start, end, recurrencetype_id, category_id, booking_nr) values ("+ this.userID+", "+ this.itemID +",  '" + start + "',  '" + end + "', " + this.recurrencetypeID + ", " +this.categoryID + ", " + maxNr + ")";
			int coin = db.excuteU(createStmt);
			db.conn.commit();
			done = (coin > 0)?true:false;
			db.conn.setAutoCommit(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db.close();
		
		return done;		
	}
	
	/**
	 * Das absolute Startdatum bei wiederkehrender Buchung 
	 * @return String
	 */
	public String getAbsoluteBookingStart() {
		
		String start = null;
		DBConnection db = new DBConnection();
		String query = "SELECT date_format(min(start), '%Y-%m-%d %H:%i:%s') as start FROM bookings where booking_nr = " + this.bookingID;
		ResultSet res = db.excuteQ(query);
		try {
			while(res.next()) {
				
				start = res.getString("start");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db.close();
		return start;
	}
	
	/**
	 * Das absolute Enddatum bei wiederkehrender Buchung 
	 * @return String
	 */
	public String getAbsoluteBookingEnd() {
		
		String end = null;
		DBConnection db = new DBConnection();
		String query = "SELECT  date_format(max(end), '%Y-%m-%d %H:%i:%s') as end FROM bookings where booking_nr = " + this.bookingID;
		ResultSet res = db.excuteQ(query);
		try {
			while(res.next()) {
				
				end = res.getString("end");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db.close();
		return end;
	}
	
	/**
	 * Storniert eine Buchung. 
	 * @return Boolean: true f�r erfolgreich, false nicht erfolgreich
	 */
	
	public boolean cancelBooking() {
		
		return true;
	}
	
	/**
	 * Gibt die ID der Buchung aus. 
	 * @return int: BuchungsID
	 */
	
	public int getBookingID() {
		return bookingID;
	}

	/**
	 * Legt die BuchungsID an. 
	 * @param bookingID int: BuchungsID
	 */
	
	public void setBookingID(int bookingID) {
		this.bookingID = bookingID;
	}

	/**
	 * Gibt die ID des Users aus. 
	 * @return int: userID
	 */
	
	public int getUserID() {
		return userID;
	}

	/**
	 * Legt die userID an. 
	 * @param userID int: userID
	 */
	
	public void setUserID(int userID) {
		this.userID = userID;
	}

	/**
	 * Gibt die ID des Items aus. 
	 * @return int: itemID
	 */
	
	public int getItemID() {
		return itemID;
	}

	/**
	 * Legt die itemID an. 
	 * @param itemID int: itemID
	 */
	
	public void setItemID(int itemID) {
		this.itemID = itemID;
	}

	/**
	 * Gibt den Start der Buchung aus. 
	 * @return String: Start (Datum und Zeit)
	 */
	
	public String getStart() {
		return start;
	}

	/**
	 * Legt den Start der Buchung an. 
	 * @param start String: Start (Datum und Zeit)
	 */
	
	public void setStart(String start) {
		this.start = start;
	}

	/**
	 * Gibt das Ende der Buchung aus. 
	 * @return String: Ende (Datum und Zeit)
	 */
	
	public String getEnd() {
		return end;
	}

	/**
	 * Legt das Ende der Buchung an. 
	 * @param end String: Ende (Datum und Zeit)
	 */
	
	public void setEnd(String end) {
		this.end = end;
	}

	/**
	 * Gibt die Art der Wiederholung aus. 
	 * @return int: Art der Wiederholung gespeichert als int.
	 */
	
	public int getRecurrencetypeID() {
		return recurrencetypeID;
	}

	/**
	 * Legt die Art der Wiederholung fest. 
	 * @param recurrencetypeID int: Art der Wiederholung gespeichert als int.
	 */
	
	public void setRecurrencetypeID(int recurrencetypeID) {
		this.recurrencetypeID = recurrencetypeID;
	}

	/**
	 * Gibt die categoryID aus. 
	 * @return int: was ist das?
	 */
	
	public int getCategoryID() {
		return categoryID;
	}

	/**
	 * Legt die categoryID fest. 
	 * @param categoryID int: was ist das?
	 */
	
	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}
	
}

	