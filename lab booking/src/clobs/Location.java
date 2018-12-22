package clobs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;
import java.util.Vector;

import clobs.util.AvailabilityCheck;
import clobs.util.BookablePeriods;
import clobs.util.ItemList;
import clobs.util.StringToDate;

/**
 * 
 * Bietet public Methode getLocationID an @see {@link #getLocationID()}
 * Bietet public Methode setLocationID an @see {@link #setLocationID(int)}
 * Bietet public Methode getBuilding an @see {@link #getBuilding()}
 * Bietet public Methode setBuilding an @see {@link #setBuilding(String)}
 * Bietet public Methode getRoomnumber an @see {@link #getRoomnumber()}
 * Bietet public Methode setRoomnumber an @see {@link #setRoomnumber(String)}
 * Bietet public Methode getOpentime an @see {@link #getOpentime()}
 * Bietet public Methode setOpentime an @see {@link #setOpentime(Time)}
 * Bietet public Methode getClosetime an @see {@link #getClosetime()}
 * Bietet public Methode setClosetime an @see {@link #setClosetime(Time)}
 * Bietet public Methode getItemList an @see {@link #getItemList()}
 * Bietet public Methode setItemList an @see {@link #setItemList(Vector<Item>)}
 * Bietet public Methode getBookablePeriods an @see {@link #getBookablePeriods(String, String)}
 * Bietet public Methode getAvailability an @see {@link #getAvailability(String, String)}
 * Bietet public Methode getCurrentPublishingPeriod an @see {@link #getCurrentPublishingPeriod()}
 *
 */

public class Location {
	private int locationID;
	private String building;
	private String roomnumber;
	private Time opentime;
	private Time closetime;
	
	/**
	 * Konstruktor
	 */
	public Location(){
		
	}
	
	public Location(int locationID) {
		
		DBConnection db = new DBConnection();
		ResultSet res = null;
		String query = "select l.id, l.building, l.roomnumber,  l.opentime, l.closetime from locations l where l.id = " + locationID;
		res = db.excuteQ(query);
		
		if(res != null) {
			try {
				res.first();
				this.locationID = res.getInt("id");
				this.building = res.getString("building");
				this.roomnumber = res.getString("roomnumber");
				this.opentime = res.getTime("opentime");
				this.closetime = res.getTime("closetime");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		db.close();
		
	}
	
	/**
	 * Gibt die LocationID aus.
	 * @return int: LocationID
	 */
	
	public int getLocationID() {
		return locationID;
	}
	
	/**
	 * Legt die LocationID an. 
	 * @param locationID int: LocationID
	 */
	
	public void setLocationID(int locationID) {
		this.locationID = locationID;
	}
	
	/**
	 * Gibt das Gebaeude aus.
	 * @return String: Gebaeude
	 */
	
	public final String getBuilding() {
		return building;
	}
	
	/**
	 * Legt das Gebaeude an. 
	 * @param building String: Gebaeude
	 */
	
	public final void setBuilding(final String building) {
		this.building = building;
	}
	
	/**
	 * Gibt die Raumnummer aus.
	 * @return String: Raumnummer
	 */
	
	public final String getRoomnumber() {
		return roomnumber;
	}
	
	/**
	 * Legt die Raumnummer an. 
	 * @param roomnumber String: Raumnummer
	 */
	
	public void setRoomnumber(String roomnumber) {
		this.roomnumber = roomnumber;
	}
	
	/**
	 * Gibt die Oeffnungs-Zeit aus. 
	 * @return Time: Oeffnungs-Zeit
	 */
	
	public final Time getOpentime() {
		return opentime;
	}
	
	/**
	 * Legt die Oeffnungs-Zeit an. 
	 * @param opentime Time: Oeffnungs-Zeit
	 */
	
	public final void setOpentime(final Time  opentime) {
		this.opentime = opentime;
	}
	
	/**
	 * Gibt die Schliess-Zeit aus. 
	 * @return Time: Schliess-Zeit
	 */
	
	public final Time getClosetime() {
		return closetime;
	}
	
	/**
	 * Legt die Schliess-Zeit an. 
	 * @param closetime Time: Schliess-Zeit
	 */
	
	public final void setClosetime(final Time closetime) {
		this.closetime = closetime;
	}
	
	/**
	 * Gibt eine Liste der Items zur�ck.
	 * @return Vector<Item>: Vektor of Items mit einer Liste der Items
	 */
	
	public final Vector<Item> getItemList() {
		Vector<Item> resultVector = new Vector<Item>();
		String searchStmt = "select * from items where location_id = " + this.locationID;

		resultVector = ItemList.getList(searchStmt);

		return resultVector;
	}
	
	/**
	 * Wird denke ich nicht mehr gebraucht.
	 */
	
	public final void setItemList(final Vector<Item> itemList) {
	}
	
	/**
	 * Gibt die buchbaren Perioden an. 
	 * @param bookingStartDatetime String: Startindex des Suchraumes
	 * @param bookingEndDatetime String: Endindex des Suchraumes
	 * @return Vector<String>: Vector of Strings mit den beuchbaren Perioden
	 */
	
	public final Vector<Vector<String>> getBookablePeriods(String bookingStartDatetime, String bookingEndDatetime) {
		
		int id = this.locationID;
		return BookablePeriods.getPeriods(id, bookingStartDatetime, bookingEndDatetime, this.getClass().getSimpleName());
	}
	
	/**
	 * Pr�ft, ob die Location in dem angegebenen Zeitraum verf�gbar ist. 
	 * @param bookingStartDatetime String: Startindex des Suchraumes
	 * @param bookingEndDatetime String: Endindex des Suchraumes
	 * @return Boolean: true f�r erfolgreich, false nicht erfolgreich
	 */
	
	public final boolean getAvailability(String bookingStartDatetime, String bookingEndDatetime) {
		
		int id = this.locationID;
		//SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd");
		//System.out.println("1: " + bookingStartDatetime + "   " + bookingEndDatetime);
		//System.out.println("2: " + this.getCurrentPublishingPeriod().getStart() + "   " + this.getCurrentPublishingPeriod().getEnd());
		//Date periodStart = StringToDate.getDate(this.getCurrentPublishingPeriod().getStart(), "yyyy-MM-dd");
		//Date periodEnd = StringToDate.getDate(this.getCurrentPublishingPeriod().getEnd(), "yyyy-MM-dd");
		Date periodStart = this.getCurrentPublishingPeriod().getStart();
		Date periodEnd = this.getCurrentPublishingPeriod().getEnd();
		System.out.println("++++++++++++location.getavailability: "+periodStart+"     "+periodEnd);
		Date bookingDate = StringToDate.getDate(bookingStartDatetime, "yyyy-MM-dd");
		Boolean between = bookingDate.after(periodStart) && bookingDate.before(periodEnd);
		//System.out.println(between);
		return AvailabilityCheck.getAvailability(id, bookingStartDatetime, bookingEndDatetime,  this.getClass().getSimpleName()) && between;
	}
	
	/**
	 * Gibt die aktuelle Publishing-Periode zur�ck. 
	 * @return BookingPeriode: Buchungsperiode
	 */
	
	public final BookingPeriode getCurrentPublishingPeriod() {
		
		BookingPeriode period = new BookingPeriode();
		DBConnection db = new DBConnection();
		ResultSet res = null;
		String query = "select bp.id, bp.location_id, bp.provider_id, bp.start, bp.end, bp.bookingstage from bookingperiode bp where bp.location_id = " + this.locationID + " and now() between bp.start and bp.end";
		//System.out.println("+++++++++++++++"+query);
		res = db.excuteQ(query);
		
		if(res != null) {
			try {
				res.first();
				period.setPeriodeID(res.getInt("id"));
				//period.setLocationID(res.getInt("location_id"));
				period.setLocation(this);
				period.setProviderID(res.getInt("provider_id"));
				period.setStart(res.getDate("start"));
				period.setEnd(res.getDate("end"));
				//System.out.println("+++++++++++++++period start end: "+period.getStart().toString()+"   "+period.getEnd().toString());
				period.setBookingStage(res.getInt("bookingstage"));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		db.close();
		return period;
	}
}
