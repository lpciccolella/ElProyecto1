package aback;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;


public class LeerProperties {
		public Connection con;
		//private static Properties prop = new Properties();
		private static LeerProperties propr = null;
		
		public Properties getFile(String url) {
			FileInputStream profile = null;
			Properties prop = null;
			try {
				profile = new FileInputStream(url);
				prop = new Properties();
				prop.load(profile);
				profile.close();
			}catch(FileNotFoundException e){
				e.printStackTrace();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
			System.out.println(prop.toString());
			return prop;
		}
		public static synchronized LeerProperties getInstance () {
			/*if(propr == null) {
				propr = new LeerProperties();
			}*/
			return propr = ((propr == null) ? propr = new LeerProperties() : propr);
		}
		/*public String getValue(String key){
			return prop.getProperty(key);
		}*/
		public Object getValue2(String string) {
			// TODO Auto-generated method stub
			return null;
		}
		public Object getValue(String string, Object[] obj) {
			// TODO Auto-generated method stub
			return null;
		}
	}
