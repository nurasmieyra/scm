package clobs;

import java.util.Vector;

/**
 * 
 * Bietet public Methode getPropertyIDan @see {@link #getPropertyID()}
 * Bietet public Methode setPropertyID an @see {@link #setPropertyID(int)}
 * Bietet public Methode getPropertyCategory an @see {@link #getPropertyCategory()}
 * Bietet public Methode setPropertyCategory an @see {@link #getPropertyCategory(String)}
 * Bietet public Methode getPropertyName an @see {@link #getPropertyName()}
 * Bietet public Methode setPropertyName an @see {@link #setPropertyName(Vector<String>)}
 * Bietet public Methode getPropertyValue an @see {@link #getPropertyValue()}
 * Bietet public Methode setPropertyValue an @see {@link #setPropertyValue(String)}
 *
 */

public class Property {

	private int propertyID;
	private String propertyCategory;
	private Vector<String> propertyName;
	private String propertyValue;
	
	public Property(int propertyID, String propertyCategory,
			Vector<String> propertyName, String propertyValue) {
		super();
		this.propertyID = propertyID;
		this.propertyCategory = propertyCategory;
		this.propertyName = propertyName;
		this.propertyValue = propertyValue;
	}
	
	
	
	
	/**
	 * Gibt die ID der Property aus. 
	 * @return int: ID der Property
	 */
	
	public int getPropertyID() {
		return propertyID;
	}
	
	/**
	 * Legt die ID der Property an. 
	 * @param propertyID int: ID der Property
	 */
	
	public void setPropertyID(int propertyID) {
		this.propertyID = propertyID;
	}
	
	/**
	 * Gibt die Kategorie der Property aus. 
	 * @return String: Kategorie der Property
	 */
	
	public String getPropertyCategory() {
		return propertyCategory;
	}
	
	/**
	 * Legt die Kategorie der Property an. 
	 * @param propertyCategory String: Kategorie der Property
	 */
	
	public void setPropertyCategory(String propertyCategory) {
		this.propertyCategory = propertyCategory;
	}
	
	/**
	 * Gibt den Namen der Property aus. 
	 * @return Vector<String>: Name der Property
	 */
	
	public Vector<String> getPropertyName() {
		return propertyName;
	}
	
	/**
	 * Legt den Namen der Property an. 
	 * @param propertyName Vector<String>: Name der Property
	 */
	
	public void setPropertyName(Vector<String> propertyName) {
		this.propertyName = propertyName;
	}
	
	/**
	 * Gibt den Wert der Property aus. 
	 * @return String: Wert der Property
	 */
	
	public String getPropertyValue() {
		return propertyValue;
	}
	
	/**
	 * Setzt den Wert der Property. 
	 * @param propertyValue String: Wert der Property
	 */
	
	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}
	
	
	
}
