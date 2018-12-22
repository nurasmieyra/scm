package clobs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import clobs.util.AvailabilityCheck;
import clobs.util.BookablePeriods;
import clobs.util.StringToDate;

/**
 * 
 * Bietet public Methode getItemID an @see {@link #getItemID()}
 * Bietet public Methode setItemID an @see {@link #setItemID(int)}
 * Bietet public Methode getLocationID an @see {@link #getLocationID()}
 * Bietet public Methode setLocationID an @see {@link #setLocationID(int)}
 * Bietet public Methode getConfigurationID an @see {@link #getConfigurationID()}
 * Bietet public Methode setConfigurationID an @see {@link #setConfigurationID(int)}
 * Bietet public Methode getDescription an @see {@link #getDescription()}
 * Bietet public Methode setDescription an @see {@link #setDescription(String)}
 * Bietet public Methode getDomainID an @see {@link #getDomainID()}
 * Bietet public Methode setDomainID an @see {@link #setDomainID(int)}
 * Bietet public Methode getBookablePeriodsOnDate an @see {@link #getBookablePeriodsOnDate(String, String)}
 * Bietet public Methode getAvailability an @see {@link #getAvailability(String, String)}
 *
 */

public class Item {

	private int itemID;
	private int locationID;
	private int configurationID ;
	private String description;
	private int domainID;
	
	/**
	 * Konstruktor
	 */
	
	public Item() {
		
	}

	/**
	 * Konstrutor mit gegebener Itemid, füllt restliche Attribute auf
	 * @param itemID
	 */
	public Item(int itemID) {
		
		DBConnection db = new DBConnection();
		String query = "select location_id, configuration_id, description, domain_id from items where id =" + itemID;
		ResultSet res = db.excuteQ(query);
		try {
			while(res.next()) {
				
				this.locationID = res.getInt("location_id");
				this.configurationID = res.getInt("configuration_id");
				this.description = res.getString("description");
				this.domainID = res.getInt("domain_id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Gibt die ID des Items aus. 
	 * @return int: itemID
	 */

	public final int getItemID() {
		return itemID;
	}

	/**
	 * Legt die itemID an. 
	 * @param itemID int: itemID
	 */
	
	public final void setItemID(int itemID) {
		this.itemID = itemID;
	}

	/**
	 * Gibt die ID der Location aus. 
	 * @return int: LocationID
	 */

	public final int getLocationID() {
		return locationID;
	}

	/**
	 * Legt die LocationID an. 
	 * @param locationID int: locationID
	 */
	
	public final void setLocationID(int locationID) {
		this.locationID = locationID;
	}

	/**
	 * Gibt die ID der Configuration aus. 
	 * @return int: configurationID
	 */

	public final int getConfigurationID() {
		return configurationID;
	}

	/**
	 * Legt die ID der Configuration an. 
	 * @param configurationID int: configurationID
	 */
	
	public final void setConfigurationID(int configurationID) {
		this.configurationID = configurationID;
	}

	/**
	 * Gibt die Beschreibung des Items aus. 
	 * @return String: description
	 */

	public final String getDescription() {
		return description;
	}
	
	/**
	 * Legt die Beschreibung des Items an. 
	 * @param description String: Beschreibung des Items
	 */

	public final void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gibt die ID der Domain aus. 
	 * @return int: domainID
	 */

	public final int getDomainID() {
		return domainID;
	}

	/**
	 * Legt die ID der Domain an. 
	 * @param domainID int: ID der Domain
	 */
	
	public final void setDomainID(int domainID) {
		this.domainID = domainID;
	}
	
	/**
	 * Gibt einen Vektor der verf�gbaren Perioden zu diesem Zeitpunkt aus. 
	 * @param bookingStartDatetime String: Startzeitpunkt der verf�gbaren Perioden
	 * @param bookingEndtime String: Endzeitpunkt der verf�gbaren Perioden
	 * @return Vector: Vektor der verf�gbaren Perioden
	 */

	public final Vector<Vector<String>> getBookablePeriodsOnDate(String bookingStartDatetime, String bookingEndtime) {
		
		int id = this.itemID;
		return BookablePeriods.getPeriods(id, bookingStartDatetime, bookingEndtime, this.getClass().getSimpleName());
	}
	
	/**
	 * Pr�ft, ob eine Location zu dem Zeitintervall verf�gbar ist. 
	 * @param bookingStartDatetime String: Startzeitpunkt der verf�gbaren Perioden
	 * @param bookingEndtime String: Endzeitpunkt der verf�gbaren Perioden
	 * @return Boolean: true, wenn verf�gbar, false, wenn nicht verf�gbar
	 */
	
	public final boolean getAvailability(String bookingStartDatetime, String bookingEndDatetime) {
		
		int id = this.itemID;
		Location location = new Location(this.locationID);
		Date periodStart = location.getCurrentPublishingPeriod().getStart();
		Date periodEnd = location.getCurrentPublishingPeriod().getEnd();
		//System.out.println("+++++++++++++item.getavailability: "+periodStart +"        "+ periodEnd);
		Date bookingDate = StringToDate.getDate(bookingStartDatetime, "yyyy-MM-dd");
		Boolean between = bookingDate.after(periodStart) && bookingDate.before(periodEnd);
		//System.out.println(between);
		return AvailabilityCheck.getAvailability(id, bookingStartDatetime, bookingEndDatetime,  this.getClass().getSimpleName()) && between;
	}
}
