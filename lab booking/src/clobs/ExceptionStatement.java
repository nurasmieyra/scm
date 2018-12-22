package clobs;

/**
 * 
 * Bietet public Methode getErrorStatement an @see {@link #getErrorStatement(String)}
 *
 */

public class ExceptionStatement {
	
	private String outputString;

	/**
	 * Konstruktor
	 */
	
	public ExceptionStatement(){

	}

	/**
	 * Legt eine Buchung an. 
	 * @param inputString String: Benennung des Fehlercodes
	 * @return String: String zur Ausgabe auf dem Bildschirm
	 */
	
	public String getErrorStatement(String inputString){

		if(inputString.equalsIgnoreCase("db_conn_lost_writing")){
			outputString = "Temporary lost connection to database. Data could not be written.";}

		if(inputString.equalsIgnoreCase("db_conn_lost_reading")){
			outputString = "Temporary lost connection to database...";}

		if(inputString.equalsIgnoreCase("time_fault")){
			outputString = "End of booking-periode is earlier than the beginning.";}

		if(inputString.equalsIgnoreCase("conn_lost")){
			outputString = "Temporary no connection to the server.";}
		
		if(inputString.equalsIgnoreCase("invalid_data")){
			outputString = "Please check your search-data.";}
		
		if(inputString.equalsIgnoreCase("x")){outputString = "x";}

		return outputString;

	}

}
