package aback;
import java.sql.Connection;
import java.sql.DriverManager;

public class M_administrador_conexion {
	public static Connection  ReturnConnection() {
		try{
					
		    Class.forName ("org.postgresql.Driver");
		    Connection con  =DriverManager.getConnection("jdbc:postgresql://localhost:5432/ElProyecto", "postgres", "1234");
		    //this.con = DriverManager.getConnection (leer.getProperty("url"), leer.getProperty("user"), leer.getProperty("password"));   
		    return con;
			
		} catch (Exception e) {
			e.printStackTrace ();
		  }	
	return null;
	
	}
	
}
