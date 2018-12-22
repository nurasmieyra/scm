package clobs;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * Bietet public Methode getPeriodeID an @see {@link #getPeriodeID()}
 * Bietet public Methode setPeriodeID an @see {@link #setPeriodeID(int)}
 * Bietet public Methode getLocationID an @see {@link #getLocationID()}
 * Bietet public Methode setLocationID an @see {@link #setLocationID(int)}
 * Bietet public Methode getProviderID an @see {@link #getProviderID()}
 * Bietet public Methode setProviderID an @see {@link #setProviderID(int)}
 * Bietet public Methode getStart an @see {@link #getStart()}
 * Bietet public Methode setStart an @see {@link #setStart(String)}
 * Bietet public Methode getEnd an @see {@link #getEnd()}
 * Bietet public Methode setEnd an @see {@link #setEnd(String)}
 * Bietet public Methode getBookingStage an @see {@link #getBookingStage()}
 * Bietet public Methode setBookingStage an @see {@link #setBookingStage(int)}
 *
 */

public class BookingPeriode {

	private int periodeID;
	private Location location;
	private int providerID;
	private Date start;
	private Date end;
	private int bookingStage;
	
	/**
	 * Konstruktor
	 */
	
	public BookingPeriode() {
		
	}

	public BookingPeriode(int userID) {
		
		DBConnection db = new DBConnection();
		ResultSet res = null;
		String query = "select  bp.id, bp.location_id, bp.provider_id, bp.start, bp.end, bp.bookingstage from bookingperiode bp where bp.provider_id = " + userID;
		res = db.excuteQ(query);
		
		try {
			while(res.next()) {
				
				this.periodeID = res.getInt("id");
				this.location = new Location(res.getInt("location_id"));
				this.providerID = res.getInt("provider_id");
				this.start = res.getDate("start");
				this.end = res.getDate("end");
				this.bookingStage = res.getInt("bookingstage");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		db.close();	
	}
	
	public final void initPerID(int ID, String attribute) {
		
		DBConnection db = new DBConnection();
		ResultSet res = null;
		String column = null;
		if(attribute.equalsIgnoreCase("location"))  {
			column = "location_id";
		} else if(attribute.equalsIgnoreCase("periode")) {
				column = "id";
		} 
		
		String query = "select  bp.id, bp.location_id, bp.provider_id, bp.start, bp.end, bp.bookingstage from bookingperiode bp where " + column + " = " + ID;
		res = db.excuteQ(query);
		
		try {
			while(res.next()) {
				
				this.periodeID = res.getInt("id");
				this.location = new Location(res.getInt("location_id"));
				this.providerID = res.getInt("provider_id");
				this.start = res.getDate("start");
				this.end = res.getDate("end");
				this.bookingStage = res.getInt("bookingstage");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		db.close();	
	}
	
	public boolean updateBookingPeriode() {
		
		Boolean ok = false;
		DBConnection db = new DBConnection();
		int res = 0;
		String updateQuery = "update bookingperiode bp set bp.start = \"" + this.start +"\", bp.end =\"" + this.end + "\", bp.bookingstage =" + this.bookingStage + " where bp.id = " + this.getPeriodeID();
		res = db.excuteU(updateQuery);
		ok = (res == 1)?true:false;
		db.close();	
		return ok;
	}
	
	/**
	 * Gibt die ID der Buchungsperiode aus. 
	 * @return int: ID der Periode
	 */
	
	public final int getPeriodeID() {
		return periodeID;
	}

	/**
	 * Legt die ID der Buchungsperiode an. 
	 * @param periodeID int: ID der Periode
	 */
	
	public final void setPeriodeID(int periodeID) {
		this.periodeID = periodeID;
	}

	/**
	 * Gibt die Location aus. 
	 * @return Location: Location Objekt
	 */
	
	public final Location getLocation() {
		return location;
	}

	/**
	 * Legt die Location fest. 
	 * @param Location:  Location Objekt
	 */
	
	public final void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * Gibt die ID des Providers aus. 
	 * @return int: providerID
	 */
	
	public final int getProviderID() {
		return providerID;
	}

	/**
	 * Legt die ID des Providers an. 
	 * @param providerID int: ID des Providers
	 */
	
	public final void setProviderID(int providerID) {
		this.providerID = providerID;
	}

	/**
	 * Gibt den Start der Buchungsperiode aus. 
	 * @return String: Start (Datum und Zeit)
	 */
	
	public final Date getStart() {
		return start;
	}

	/**
	 * Legt den Start der Buchungsperiode an. 
	 * @param start String: Start (Datum und Zeit)
	 */
	
	public final void setStart(Date start) {
		this.start = start;
	}

	/**
	 * Gibt das Ende der Buchungsperiode aus. 
	 * @return String: Ende (Datum und Zeit)
	 */
	
	public final Date getEnd() {
		return end;
	}

	/**
	 * Legt das Ende der Buchungsperiode an. 
	 * @param end String: Ende (Datum und Zeit)
	 */
	
	public final void setEnd(Date end) {
		this.end = end;
	}

	/**
	 * Gibt den Stage der Buchung aus. 
	 * @return int: Stage der Buchung
	 */
	
	public final int getBookingStage() {
		return bookingStage;
	}

	/**
	 * Legt den Stage der Buchung an. 
	 * @param bookingStage int: Stage der Buchung
	 */
	
	public final void setBookingStage(int bookingStage) {
		this.bookingStage = bookingStage;
	}
	
	
}
