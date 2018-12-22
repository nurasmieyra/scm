package clobs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 * 
 * Bietet public Methode loginUser an @see {@link #loginUser(String, String)}
 * Bietet public Methode getMyBookings an @see {@link #getMyBookings()}
 * Bietet public Methode getUserID an @see {@link #getUserID()}
 * Bietet public Methode setUserID an @see {@link #setUserID(int)}
 * Bietet public Methode getFirstname an @see {@link #getFirstname()}
 * Bietet public Methode setFirstname an @see {@link #setFirstname(String)}
 * Bietet public Methode getLastname an @see {@link #getLastname()}
 * Bietet public Methode setLastname an @see {@link #setLastname(String)}
 * Bietet public Methode getEmail an @see {@link #getEmail()}
 * Bietet public Methode setEmail an @see {@link #setEmail(String)}
 * Bietet public Methode getUsername an @see {@link #getUsername()}
 * Bietet public Methode setUsername an @see {@link #setUsername(String)}
 * Bietet public Methode getUserstage an @see {@link #getUserstage()}
 * Bietet public Methode setUserstage an @see {@link #setUserstage(int)}
 *
 */

public class User {

	private int userID;
	private String firstname;
	private String lastname ;
	private String email;
	private String username;
	//protected String Password;;
	private int userstage; 
	
	/**
	 * Konstruktor
	 */
	
	public User() {

	}
	
	
	/**
	 * Loggt den Benutzer ein. 
	 * @param username String: Username
	 * @param password String: Passwort
	 * @return Boolean: true fuer erfolgreich, false nicht erfolgreich
	 */
	
	public boolean loginUser(String username, String password) {

		int coin = 0;
		ResultSet res = null;
		DBConnection db = new DBConnection();
		String queryStmt = "SELECT * FROM users where username = \"" + username+"\" and password = MD5('"+password+"')";
		
		res = db.excuteQ(queryStmt);

			try {
				if (res.first()) {
					try {
						    //res.next();
							this.userID = res.getInt(1);
							this.username = res.getString(2);
							//this.password = res.getString(3);
							this.firstname = res.getString(4);
							this.lastname = res.getString(5);
							this.email = res.getString(6);
							this.userstage = res.getInt(7);	
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					coin = 1;
				} else {
						coin = 0;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			db.close();
			if (coin == 1) {
				return true;
				} else {
					return false;
			}
	}
	
	/**
	 * Gibt die Buchungen des Users in Form eines Vektors aus. 
	 * @return Vector<Booking>: Buchungen des Users als Vektor
	 */
	
	public final Vector<Booking> getMyBookings() {
		
		Vector<Booking> myBookings = new Vector<Booking>();
		ResultSet res = null;
		DBConnection db = new DBConnection();
		String query = "select booking_nr, user_id, item_id, date_format(start, '%Y-%m-%d %H:%i:%s') as start, date_format(end, '%Y-%m-%d %H:%i:%s') as end, recurrencetype_id, category_id from bookings where user_id =" + this.userID + " order by booking_nr asc, item_id asc, start asc, item_id asc";
		res = db.excuteQ(query);

		try {
			while(res.next()) {
				Booking booking = new Booking();
				booking.setBookingID(res.getInt("booking_nr"));
				booking.setUserID(res.getInt("user_id"));
				booking.setItemID(res.getInt("item_id"));
				booking.setStart(res.getString("start"));
				booking.setEnd(res.getString("end"));
				booking.setRecurrencetypeID(res.getInt("recurrencetype_id"));
				booking.setCategoryID(res.getInt("category_id"));				
				
				myBookings.add(booking);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db.close();

		return myBookings;
	} 
	
	/**
	 * Administrator kann sich seine Buchungsperiode ansehen
	 * @return Vector&#60BookingPeriode&#62
	 */
	public final Vector<BookingPeriode> getMyBookingPeriode() {
		
		Vector<BookingPeriode> myBookingPeriode = new Vector<BookingPeriode>();
		
		if(this.getUserstage() < 2) {
			
			ResultSet res = null;
			DBConnection db = new DBConnection();
			String query = "select bp.id, bp.location_id, bp.provider_id, bp.start, bp.end, bp.bookingstage from bookingperiode bp where bp.provider_id = " + this.userID;
			res = db.excuteQ(query);
			try {
				while(res.next()) {
					BookingPeriode bookingPeriode = new BookingPeriode();
					bookingPeriode.setPeriodeID(res.getInt("id"));
					Location location = new Location(res.getInt("location_id"));
					bookingPeriode.setLocation(location);
					bookingPeriode.setProviderID(res.getInt("provider_id"));
					bookingPeriode.setStart(res.getDate("start"));
					bookingPeriode.setEnd(res.getDate("end"));
					bookingPeriode.setBookingStage(res.getInt("bookingstage"));			
					
					myBookingPeriode.add(bookingPeriode);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			db.close();
		}
		
		return myBookingPeriode;
	} 

	/**
	 * Gibt die UserID aus. 
	 * @return int: ID des Users
	 */
	
	public int getUserID() {
		return userID;
	}

	
	/**
	 * Legt die UserID an. 
	 * @param userID int: ID des Users
	 */
	
	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	/**
	 * Gibt den Vornamen aus. 
	 * @return String: Vorname
	 */
	
	public String getFirstname() {
		return firstname;
	}
	
	/**
	 * Legt den Vornamen an. 
	 * @return String: Vorname
	 */
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	/**
	 * Gibt den Familiennamen aus. 
	 * @return String: Familienname
	 */
	
	public String getLastname() {
		return lastname;
	}
	
	
	/**
	 * Legt den Familiennamen an. 
	 * @return String: Familienname
	 */
	
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	/**
	 * Gibt die email des Users aus. 
	 * @return String: email des Users
	 */
	
	public String getEmail() {
		return email;
	}
	
	/**
	 * Legt die email des Users an. 
	 * @param email String: email des Users
	 */
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Gibt den Namen des Users aus.
	 * @return String: Name des Users
	 */
	
	public String getUsername() {
		return username;
	}
	
	/**
	 * Legt den Usernamen an. 
	 * @param username String: Name des Users
	 */
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * Gibt den Status des Users aus. 
	 * @return int: userstage
	 */
	
	public int getUserstage() {
		return userstage;
	}
	
	/**
	 * Legt des Status des Users an. 
	 * @param userstage int: Userstage
	 */
	
	public void setUserstage(int userstage) {
		this.userstage = userstage;
	}
	
	
	
	
}
