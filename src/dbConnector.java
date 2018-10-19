
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class dbConnector{
public dbConnector() {}
	
	public Connection getConnection() throws SQLException {
	        final String driver = "com.mysql.cj.jdbc.Driver";
	        final String dbPath = "jdbc:mysql://localhost:3306/dzienniczek";
	        Connection conn = DriverManager.getConnection(dbPath, "root", "admin");
	        return conn;
	}
	
}