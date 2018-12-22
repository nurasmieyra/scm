package clobs;
//package xxx

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Realisiert die Verbindung mit der Datenbank
 *
 * Bietet public Methode excuteQ an @see {@link #excuteQ(String)}
 * Bietet public Methode excuteU an @see {@link #excuteU(String)}
 * Bietet public Methode getMeta an @see {@link #getMeta(ResultSet)}
 * Bietet public Methode close an @see {@link #close()}
 */
public class DBConnection {
	public Connection conn;

	/**
	 * Konstruktor
	 */
	
	public DBConnection() {
		try {
		     // Statement stmt;
		      Class.forName("com.mysql.jdbc.Driver");
		      String url =
		            "jdbc:mysql://localhost:3306/clobs";

		      conn = DriverManager.getConnection(
		                                 url,"clobsuser", "sagenicht");
   
		} catch( Exception e ) {
				System.out.println("Scheiße, keine DB verbindung!");
				e.printStackTrace();
		}
	}
	
	/**
	 * Führt eine Query aus mit Fehlerbehandelung
	 * 
	 * @param queryStmt
	 * @return ResultSet
	 */
	public ResultSet excuteQ(String queryStmt) {
		ResultSet res = null;
		Statement stmt = null;
		
		try {
			stmt = this.conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			res = stmt.executeQuery(queryStmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return res;
		
	}
	
	/**
	 * Führt eine updatende Query aus mit Fehlerbehandelung
	 * 
	 * @param updateStmt String
	 * @return int: Anzahl betroffener Zeilen
	 */
	public int excuteU(String updateStmt) {
		int res = 0;
		Statement stmt = null;
		
		try {
			stmt = this.conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			res = stmt.executeUpdate(updateStmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return res;
	}
	
	/**
	 * Gibt Metadata vom Resultset zurück mit Fehlerbehandelung
	 * 
	 * @param res ResultSet
	 * @return ResultSetMetaData
	 */
	public ResultSetMetaData getMeta(ResultSet res) {
		
		ResultSetMetaData rsmd = null;
		
		try {
			rsmd = res.getMetaData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rsmd;
	}
	
	/**
	 * Schliesst die connection.
	 * 
	 * @return boolean: true, wenn geschlossen, false, wenn nicht
	 */
	public boolean close() {
		Boolean closeOK = false;
		
		try {
			this.conn.close();
			closeOK = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return closeOK;
	}
	
}
		