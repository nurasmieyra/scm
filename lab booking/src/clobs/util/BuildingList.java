package clobs.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import clobs.DBConnection;
/**
 * Bietet statische Methode getList an @see {@link #getList()}
 *
 */
public final class BuildingList {
	
	public BuildingList() {

	}

	/**
	 * Gibt eine Liste aller Gebäude zurück 
	 * @return Vector<String>
	 */
	public final static Vector<String> getList() {
		ResultSet res = null;
		DBConnection db = new DBConnection();
		Vector<String> resultVector = new Vector<String>();
		String searchStmt = "SELECT DISTINCT building FROM locations";
		res = db.excuteQ(searchStmt);
		try {
			while (res.next()) {
				resultVector.add(res.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db.close();
		return resultVector;
	}
}

