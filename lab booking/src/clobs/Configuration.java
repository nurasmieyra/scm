package clobs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 * 
 * Bietet public Methode getItemProperties an @see {@link #getItemProperties(int)}
 * Bietet public Methode getRoomProperties an @see {@link #getRoomProperties(String, String)}
 * Bietet public Methode getPropertyCategory an @see {@link #getPropertyCategory()}
 * Bietet public Methode setPropertyCategory an @see {@link #setPropertyCategory(Vector<String>)}
 * Bietet public Methode getPropertyName an @see {@link #getPropertyName()}
 * Bietet public Methode setPropertyName an @see {@link #setPropertyName(Vector<String>)}
 * Bietet public Methode getPropertyValue an @see {@link #getPropertyValue()}
 * Bietet public Methode setPropertyValue an @see {@link #setPropertyValue(Vector<String>)}
 *
 */

public class Configuration {

	private Vector<String> propertyCategory;
	private Vector<String> propertyName;
	private Vector<String> propertyValue;

	/**
	 * Konstruktor
	 */
	
	public Configuration() {
		this.propertyCategory = new Vector<String>();
		this.propertyName = new Vector<String>();
		this.propertyValue = new Vector<String>();
	}

	/**
	 * Holt aus der Datenbank die Properties eines Items. 
	 * @param itemID int: ID des Items
	 */
	
	public final void getItemProperties(int itemID) {
			
			ResultSet res = null;
			DBConnection db = new DBConnection();
			String searchStmt = "select (select pp.name from properties pp where pp.id = p.parent_property) as propertyCategory, p.name as propertyName, pv.value as propertyValue from properties p, property_values pv where p.id = pv.property_id and pv.id in (select c.position from configurations c, items i where c.id = i.configuration_id and i.id = " + itemID  +")";
			res  = db.excuteQ(searchStmt);
			
		      try {
				while(res.next()) {
					  this.propertyCategory.add(res.getString("propertyCategory"));
					  this.propertyName.add(res.getString("propertyName"));
					  this.propertyValue.add(res.getString("propertyValue"));
				  }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	/**
	 * Holt aus der Datenbank die Properties eines Raumes. 
	 * @param building String: Gebäudebezeichnung
	 * @param locationID String: ID der Location
	 */
	
	public final void getRoomProperties(String building, String locationID) {
		ResultSet res = null;
		DBConnection db = new DBConnection();
		String searchStmt = "select (select pp.name from properties pp where pp.id = p.parent_property) as propertyCategory, p.name as propertyName, pv.value as propertyValue from properties p, property_values pv where p.id = pv.property_id and pv.id in (select c.position from configurations c where c.id = 	(select distinct i.configuration_id from items i, locations l where i.location_id = l.id and l.building = \"" + building + "\" and l.id = \"" + locationID + "\"))";
		res  = db.excuteQ(searchStmt);
	      try {
				while(res.next()) {
					  this.propertyCategory.add(res.getString("propertyCategory"));
					  this.propertyName.add(res.getString("propertyName"));
					  this.propertyValue.add(res.getString("propertyValue"));
				  }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
	
	/**
	 * Gibt als Vektor die Kategorie der Property zurück. 
	 * @return Vector: Art der Property
	 */
	
	public final Vector<String> getPropertyCategory() {
		return propertyCategory;
	}

	/**
	 * Legt als Vektor die Kategorie der Property an. 
	 * @param propertyCategory Vector<String>: Art der Property
	 */
	
	public final void setPropertyCategory(Vector<String> propertyCategory) {
		this.propertyCategory = propertyCategory;
	}

	/**
	 * Gibt als Vektor den Namen der Property zurück. 
	 * @return Vector: Name der Property
	 */
	
	public final Vector<String> getPropertyName() {
		return propertyName;
	}

	/**
	 * Legt als Vektor den Namen der Property an. 
	 * @param Vector<String>: Name der Property
	 */
	
	public final void setPropertyName(Vector<String> propertyName) {
		this.propertyName = propertyName;
	}

	/**
	 * Gibt als Vektor den Wert der Property zurück. 
	 * @return Vector: Wert der Property
	 */
	
	public final Vector<String> getPropertyValue() {
		return propertyValue;
	}

	/**
	 * Legt als Vektor den Wert der Property an. 
	 * @param Vector<String>: Wert der Property
	 */
	
	public final void setPropertyValue(Vector<String> propertyValue) {
		this.propertyValue = propertyValue;
	}
}
