package clobs.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import clobs.DBConnection;
import clobs.Item;
/**
 * Bietet statische Methode getList an @see {@link #getList(String)}
 *
 */
public final class ItemList {
	
	public ItemList() {
		
	}
	/**
	 * Gibt ein Vektor von Items zurück
	 * @param bookingStartDatetime String: muss dasgleiche Datum wie bookingEndDatetime haben
	 * @return Vector<Item>
	 */
	public final static Vector<Item> getList(String searchStmt) {

		ResultSet res = null;
		DBConnection db = new DBConnection();
		Vector<Item> resultVector = new Vector<Item>();
		res = db.excuteQ(searchStmt);
		try {
			while (res.next()) {

				Item myItem = new Item();
				myItem.setItemID(res.getInt("id"));
				myItem.setLocationID(res.getInt("location_id"));
				myItem.setConfigurationID(res.getInt("configuration_id"));
				myItem.setDescription(res.getString("description"));
				myItem.setDomainID(res.getInt("domain_id"));
				resultVector.add(myItem);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		db.close();
		return resultVector;
	}
	
	
	
}

