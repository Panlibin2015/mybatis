package plb.spring.dao.datasource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Test;

public class DataSourceTest {
	private Properties pop = new Properties();

	private Connection con = null;

	@Test
	public void dbcpAccessTest() {
		try {
			pop.load(ClassLoader.getSystemResourceAsStream("plb/spring/dao/config/jdbc.properties"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BasicDataSource ds = new BasicDataSource();
		String driverName = pop.getProperty("mysql.driver");
		String url = pop.getProperty("mysql.url");
		String username = pop.getProperty("mysql.username");
		String password = pop.getProperty("mysql.password");
		ds.setDriverClassName(driverName);
		ds.setUrl(url);
		ds.setUsername(username);
		ds.setPassword(password);
		try {
			con = ds.getConnection();
			System.out.println(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
