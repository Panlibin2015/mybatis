package plb.spring.dao.jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCUtil {
	private static String url, user, password;
	private static ThreadLocal<Connection> locals = new ThreadLocal<Connection>();

	static {
		try {
			Properties pop = new Properties();
			pop.load(ClassLoader.getSystemResourceAsStream("plb/spring/dao/config/jdbc.properties"));
			Class.forName(pop.getProperty("mysql.driver"));
			url = pop.getProperty("mysql.url");
			user = pop.getProperty("mysql.username");
			password = pop.getProperty("mysql.password");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private JDBCUtil() {
	};

	public static Connection getConnection() {
		Connection con = locals.get();
		try {
			if (con == null) {
				con = DriverManager.getConnection(url, user, password);
				locals.set(con);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return locals.get();
	}

	public static void close() {
		Connection con = locals.get();
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
