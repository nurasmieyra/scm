package clobs.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import clobs.DBConnection;
import clobs.Location;
/**
 * Bietet statische Methode getList an @see {@link #getList(String, int)}
 *
 */
public final class RoomList {
	
	public RoomList() {
		
	}
	/**
	 * Gibt in Form eines Vektors die Liste der verfügbaren Gebäude zurück. 
	 * @param building String: Gebäudebezeichnung
	 * @param userStage int: Status des Benutzers
	 * @return Vector<Location>
	 */
	public final static Vector<Location> getList(String building, int userStage) {
		ResultSet res = null;
		DBConnection db;
		Vector<Location> resultVector = new Vector<Location>();
		String searchStmt = "SELECT l.* FROM locations l where  l.id in (select lc.id from locations lc, bookingperiode bp where lc.id = bp.location_id and bp.bookingstage >= " + userStage + ")";
		String and = (building.isEmpty())?"":(" and l.building = '" + building + "'");
		searchStmt = searchStmt + and;
		db = new DBConnection();
		res = db.excuteQ(searchStmt);
		
		try {
			while (res.next()) {
				Location room = new Location();
				room.setLocationID(res.getInt("id"));
				room.setBuilding(res.getString("building"));
				room.setRoomnumber(res.getString("roomnumber"));
				room.setOpentime(res.getTime("opentime"));
				room.setClosetime(res.getTime("closetime"));
				resultVector.add(room);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db.close();
		
		return resultVector;
	}
	
}

