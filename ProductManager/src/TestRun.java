
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class TestRun {

	public static void main(String[] args) {
		 saveProp();
		
		 saveJdbcSettings();

	}

	public static void saveJdbcSettings() {

		Properties prop = new Properties();
		prop.setProperty("driver", "oracle.jdbc.driver.OracleDriver");
		prop.setProperty("url", "jdbc:oracle:thin:@localhost:1521:xe");
		prop.setProperty("username", "C##JDBC");
		prop.setProperty("password", "JDBC");
		
		try {
			prop.store(new FileOutputStream("resources/driver.properties"), "");
			
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	

	public static void saveProp() {
		Properties prop = new Properties();

		prop.setProperty("allSelect", "SELECT * FROM PRODUCT");
		prop.setProperty("insert", "INSERT INTO PRODUCT VALUES (?, ?, ?, ?, ?)"); 
		prop.setProperty("update", "UPDATE PRODUCT SET P_NAME = ?, PRICE = ?, DESCRIPTION = ?, STOCK = ? WHERE PRODUCT_id = ?"); 
		prop.setProperty("delete", "DELETE PRODUCT WHERE PRODUCT_ID = ?");
		prop.setProperty("search", "SELECT * FROM PRODUCT WHERE P_NAME LIKE '%' || ? || '%'");
		
		try {
			prop.storeToXML(new FileOutputStream("resources/query.xml"), "Properties Test");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
