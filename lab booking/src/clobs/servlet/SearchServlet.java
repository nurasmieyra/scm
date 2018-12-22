package clobs.servlet;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import clobs.Configuration;
import clobs.Item;
import clobs.Location;
import clobs.User;
import clobs.util.ItemList;
import clobs.util.RoomList;
import clobs.util.TimeCheck;
/**
 * 
 * Bietet private Methode searchRoomsFromBuilding an @see {@link #searchRoomsFromBuilding(String, int)}
 * Bietet private Methode searchItemInLocation an @see {@link #searchItemInLocation(String, int)}
 * Bietet private Methode searchItemInBuilding an @see {@link #searchItemInBuilding(String, int)}
 * Bietet private Methode searchItemInEverywhere an @see {@link #searchItemInEverywhere(int)}
 * Stellt Interaktion zwischen JSP und hauptsaechlich der Klasse User.java da.
 */
public class SearchServlet extends HttpServlet {
	 /**
	  * @see HttpServlet#HttpServlet()
	  */
	 private static final long serialVersionUID = 1L;
	 private String targetsite = "/clobs/jsp/index.jsp";

	 public void init(ServletConfig config)
	  throws ServletException {
  
	  super.init(config);
	 }
	 /**
	  * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	  */
	 public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	  	 // If it is a get request forward to doPost()
		 doPost(request, response);
	 }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
		 
	 public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		 HttpSession session = request.getSession(false);
		 if (session == null) {
			 response.sendRedirect(targetsite+"?target=error&error=conn_lost");
		 }

		 String action = request.getParameter("action");
		 
		 
		 /*************************** Session: BEGIN  ****************************/
		 
		 User user = (User) session.getAttribute("user");
		 
		 Boolean searchTypeSwitched = false;
		 String selectedSearchType = null;
		 if(request.getParameter("searchType") == null) { // Search Site wird zum ersten Mal geladen
			 
			 selectedSearchType = "pcsearch";
		 } else {
			 // überprüft ob searchType sich geändert hat
			 searchTypeSwitched = (session.getAttribute("selectedSearchType") == null || (session.getAttribute("selectedSearchType") != null  && !(session.getAttribute("selectedSearchType").toString().equalsIgnoreCase(request.getParameter("searchType")))))?true:false;
			 selectedSearchType = request.getParameter("searchType");
		 }
		 session.setAttribute("selectedSearchType", selectedSearchType);

		 String searchStartDate = request.getParameter("searchStartYear") + "-" + request.getParameter("searchStartMonth") + "-" + request.getParameter("searchStartDay");
		 session.setAttribute("searchStartDate", searchStartDate);
		 System.out.println(searchStartDate);
		 
		  String recurType = request.getParameter("recurType");
		  session.setAttribute("recurType", recurType);
		  
		 String searchEndDate = request.getParameter("searchEndYear") + "-" + request.getParameter("searchEndMonth") + "-" + request.getParameter("searchEndDay");
		  if(recurType != null) {
				 /**  ++++++++++   searchEndDate zum Testzweck statisch gesetzt @Riewert: ersetzt es mit DropDown Werte  ++++++++++++  **/
				 session.setAttribute("searchEndDate", searchEndDate);
				 /**  +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++**/
		  } else {
			  	session.setAttribute("searchEndDate", searchStartDate);
		  }
		  System.out.println(searchEndDate);
			 
		 String searchStarttime = request.getParameter("searchStartTime");
		  session.setAttribute("searchStartTime", searchStarttime);
		  
		  String searchEndTime = request.getParameter("searchEndTime");
		  session.setAttribute("searchEndTime", searchEndTime);
		  
		  boolean timeCheck = TimeCheck.check(searchStarttime, searchEndTime, "time");
		  boolean dateCheck = TimeCheck.check(searchStartDate, searchEndDate, "date");
		  
		  String selectedBuildingDD = request.getParameter("searchBuildingDD");
		  session.setAttribute("searchBuildingDD", selectedBuildingDD);
		  String selectedRoomDD = request.getParameter("searchRoomDD");
		  session.setAttribute("searchRoomDD", selectedRoomDD);
		  
		  // Ein Gebäude wurde in der DropDown gewählt, werden die drin befindende 
		  // Räume in Session geschrieben und in der 2ten DD zum Auswahl bereitgestellt
		  if (!(selectedBuildingDD.equalsIgnoreCase("default")) && !(selectedRoomDD==null)) {
			  
			  String building = selectedBuildingDD;
			  Vector<Location> roomsFromBuilding = new Vector<Location>();
			  roomsFromBuilding = searchRoomsFromBuilding(building, user.getUserstage());
			  session.setAttribute("roomsFromBuilding", roomsFromBuilding);
		  } else {
			  
			  session.setAttribute("roomsFromBuilding", null);
		  }
		  
		  // Wenn Suchetyp geändert wird, werden Einträge von Building und Room DDs gelerrt
		  if(searchTypeSwitched == true) {
			  
			  selectedBuildingDD = null;
			  selectedRoomDD = null;
			  session.setAttribute("selectedBuildingDD", selectedBuildingDD);
			  session.setAttribute("selectedRoomDD", selectedRoomDD);
		  }
		  
		  // Ein Raum wurde in der DD gewählt, die Konfiguration von den Computers werden geladen
		  if (!(selectedRoomDD == null || selectedRoomDD.equalsIgnoreCase("default") || selectedRoomDD==null)) {

			  Configuration itemConfiguration = new Configuration();
			  itemConfiguration.getRoomProperties(selectedBuildingDD, selectedRoomDD);
			  session.setAttribute("roomItemConfiguration", itemConfiguration);
		  } else {
			  
		  }
		  /*************************** Session Handler: BEGIN ****************************/

		  /***************************** Action Handler: BEGIN ****************************/

			
		  if (action.equalsIgnoreCase("doSearch")) {
			  
			  if(searchTypeSwitched == false) { // Beim gleich gebliebenem Suchetyp
				  
				  if(timeCheck && dateCheck) {

					  if(selectedSearchType.equalsIgnoreCase("pcsearch")) {
						
						  Vector<Item> itemList = new Vector<Item>();
						  if( !(selectedBuildingDD.equalsIgnoreCase("default") || selectedBuildingDD == null) && (selectedRoomDD.equalsIgnoreCase("default") || selectedRoomDD == null)) {
							  // sucht alle Items eines Gebäudes, wenn nur Building aber kein Room gewählt
								  itemList = searchItemInBuilding(selectedBuildingDD, user.getUserstage());
								  session.setAttribute("roomItemConfiguration", null);
							  } else if( !(selectedBuildingDD.equalsIgnoreCase("default") || selectedBuildingDD == null) && !(selectedRoomDD.equalsIgnoreCase("default") || selectedRoomDD == null)) {
								// sucht alle Items in einem Raum, wenn Building und Room gewählt
								  itemList = searchItemInLocation(selectedRoomDD, user.getUserstage());
							  } else if((selectedBuildingDD.equalsIgnoreCase("default") || selectedBuildingDD == null)) {
								 // Leeres Suche Ergebnis wenn kein Building gewählt 
								  //itemList = null;
								  itemList = searchItemInEverywhere(user.getUserstage());
							  }
						  
						  session.setAttribute("searchResult", itemList);	
						  session.setAttribute("searchResultType", "item");
						  response.sendRedirect(targetsite+"?target=search");
						  
					  } else if(selectedSearchType.equalsIgnoreCase("labsearch")) {
						  
							  Vector<Location> locationList = new Vector<Location>();
							  String building = (selectedBuildingDD.equalsIgnoreCase("default"))?"":selectedBuildingDD;
							  locationList = RoomList.getList(building, user.getUserstage());
							  session.setAttribute("searchResult", locationList);
							  session.setAttribute("searchResultType", "location");
							  session.setAttribute("roomItemConfiguration", null);
							  response.sendRedirect(targetsite+"?target=search");		
					  }
					  
				  }  else {

					  response.sendRedirect(targetsite+"?target=error&error=invalid_data");
				  }
			  } else {
					  session.setAttribute("searchResult", null);
					  response.sendRedirect(targetsite+"?target=search");
			  }

		  }
		  
		  /***************************** Action Handler: END ****************************/	   		

	 }
	  
	 /**
	  * Alle in einem Gebäude befindende Labs ausgeben
	  * @param building Gebäudename
	  * @return Vector&#60Location&#62
	  */
	  private Vector<Location> searchRoomsFromBuilding(String building, int userStage) {
		  
		  Vector<Location> resultVector = new Vector<Location>();
		  resultVector = RoomList.getList(building, userStage);

		  return resultVector;
	  }

	  /**
	   * Alle Items (PC) in einem Location (Lab) ausgeben
	   * @param locationID ID der Location
	   * @return Vector&#60Item&#62
	   */
	  private final Vector<Item> searchItemInLocation(String locationID, int userStage) {
		  
			Vector<Item> resultVector = new Vector<Item>();
			String searchStmt = "select i.* from items i where i.location_id = " + locationID + " and i.location_id in (select lc.id from locations lc, bookingperiode bp where lc.id = bp.location_id and bp.bookingstage >= " + userStage + ")";
			resultVector = ItemList.getList(searchStmt);

			return resultVector;
	  }

	  /**
	   * Alle Items (PC) in einem Gebäude ausgeben
	   * @param buildingID ID des Gebäudes
	   * @return Vector&#60Item&#62
	   */
	  private final Vector<Item> searchItemInBuilding(String buildingID, int userStage) {
		  
			Vector<Item> resultVector = new Vector<Item>();
			String searchStmt = "SELECT i.* FROM items i, locations l where i.location_id = l.id and l.building = '" + buildingID + "' and i.location_id in (select lc.id from locations lc, bookingperiode bp where lc.id = bp.location_id and bp.bookingstage >= " + userStage + ")";
			resultVector = ItemList.getList(searchStmt);

			return resultVector;
	  }
	  
	  /**
	   * Alle Items ausgeben
	   * 
	   * @return Vector&#60Item&#62
	   */
	  private final Vector<Item> searchItemInEverywhere(int userStage) {
		  
			Vector<Item> resultVector = new Vector<Item>();
			String searchStmt = "select i.* from items i where i.location_id in (select lc.id from locations lc, bookingperiode bp where lc.id = bp.location_id and bp.bookingstage >= " + userStage + ")";
			resultVector = ItemList.getList(searchStmt);

			return resultVector;
	  }
  
}
