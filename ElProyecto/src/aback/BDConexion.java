package aback;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;


public class BDConexion extends LeerProperties{
		public PreparedStatement stmt;
		public Connection con;
		public ResultSet rs;
		public Properties leer= new LeerProperties().getFile("C:\\Users\\Loriana Ciccolella\\eclipse-workspace\\ElProyecto\\WebContent\\query.properties");
		
		public BDConexion(){
			  try{
				this.con = null;		
			    Class.forName ("org.postgresql.Driver");
			    this.con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ElProyecto", "postgres", "1234");
			    //this.con = DriverManager.getConnection (leer.getProperty("url"), leer.getProperty("user"), leer.getProperty("password"));   
			  } 
			  catch (Exception e) {
				e.printStackTrace ();
			  }	
		}
		public ResultSet executeStatement(String query,Object...value) throws SQLException{
			
				this.stmt = this.con.prepareStatement(leer.getProperty(query));
				for(int i=0; i < value.length; i++ )
					this.stmt.setObject(i + 1, value[i]);
				
				return this.stmt.executeQuery();			
		}
		
		
		public int executeUpdate(String query,Object...value) throws SQLException{
			this.stmt = this.con.prepareStatement(leer.getProperty(query));
			for(int i=0; i < value.length; i++ )
			this.stmt.setObject(i + 1, value[i]);
			
			return this.stmt.executeUpdate();
		}
		
		public int excuteDelete(String query, int id) throws SQLException{
			this.stmt = this.con.prepareStatement(leer.getProperty(query));
			this.stmt.setInt(0, id);
			System.out.println("id usuario en executeDelete:"+id);
			System.out.println("query delete--"+query);
			return this.stmt.executeUpdate();
		}
		
		
		protected void closeMainResource() throws SQLException {
			if(this.stmt != null) 
				this.stmt.close();
		}
		
		public void dbPrepareStatement(Object value) {
			// TODO Auto-generated method stub
			
		}
		public static BDConexion getIntsnaces() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
