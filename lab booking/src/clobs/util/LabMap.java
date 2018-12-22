package clobs.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import clobs.DBConnection;
import clobs.Location;

public final class LabMap {
	
	private LabMap() {

	}

	public final static Vector<Location> getMap() {
		ResultSet res = null;
		DBConnection db;
		Vector<Location> resultVector = new Vector<Location>();
		String searchStmt = "SELECT * FROM locations";
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
