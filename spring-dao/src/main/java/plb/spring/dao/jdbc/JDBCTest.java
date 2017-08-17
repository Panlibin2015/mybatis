package plb.spring.dao.jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.mysql.jdbc.PreparedStatement;

public class JDBCTest {

	private Properties pop = new Properties();

	private Connection con = null;

	@Before
	public void before() {
		try {
			pop.load(ClassLoader.getSystemResourceAsStream("plb/spring/dao/config/jdbc.properties"));
			Class.forName(pop.getProperty("mysql.driver"));
			String url = pop.getProperty("mysql.url");
			String user = pop.getProperty("mysql.username");
			String password = pop.getProperty("mysql.password");
			con = DriverManager.getConnection(url, user, password);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testConnectionExis() {
		Assert.assertNotNull(con);
	}

	@Test
	public void jdbcAccessTest() throws SQLException {
		String sql = "insert into t_spring_dao_test values(1,'龍七海',12)";
		PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
		ps.execute();
		ps.close();
		con.close();
	}

	@Test
	public void jdbcSelectAllRecords() throws SQLException {
		String sql = "select * from t_spring_dao_test ";
		PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
		ps.execute();
		ResultSet rs = ps.getResultSet();
		System.out.println("id\tname\tage");
		while (rs.next()) {
			String id = rs.getString(1);
			String name = rs.getString(2);
			String age = rs.getString(3);
			System.out.println(id + "\t" + name + "\t" + age);
		}
	}

	@Test
	public void jdbcDeleteRecord() throws SQLException {
		String sql = "delete from t_spring_dao_test where id = 1001";
		// 点禁止自动提交，设置回退
		Statement stmt = con.createStatement();
		stmt.execute(sql);
	}

	@Test
	public void jdbcGetTableInfo() throws SQLException {
		DatabaseMetaData m_DBMetaData = con.getMetaData();
		String columnName;
		String columnType;
		String m_TableName = "t_spring_dao_test";
		ResultSet colRet = m_DBMetaData.getColumns(null, "%", m_TableName, "%");
		System.out.println("columnName\tcolumnType\tdatasize\tdigits\t\tnullable");
		while (colRet.next()) {
			columnName = colRet.getString("COLUMN_NAME");
			columnType = colRet.getString("TYPE_NAME");
			int datasize = colRet.getInt("COLUMN_SIZE");
			int digits = colRet.getInt("DECIMAL_DIGITS");
			int nullable = colRet.getInt("NULLABLE");
			System.out.println(
					columnName + "\t\t" + columnType + "\t\t" + datasize + "\t\t" + digits + "\t\t" + nullable);
		}
	}

	@Test
	public void jdbcTransactionManageTest() throws SQLException, InterruptedException {
		System.out.println(con);
		String sql = "update t_spring_dao_test set age=" + 11 + " where id = 1";
		// String sql2 = "insert into t_spring_dao_test values(1003,'金克丝3',15)";

		String sql3 = "select * from t_spring_dao_test ";

		// 点禁止自动提交，设置回退
		con.setAutoCommit(false);
		final Statement stmt = con.createStatement();

		// 数据库更新操作1
		stmt.executeUpdate(sql);
		// 数据库更新操作2
		// stmt.executeUpdate(sql2);
		Thread.sleep(1000 * 10);

		// ResultSet rs = stmt.executeQuery(sql3);
		// System.out.println("id\tname\tage");
		// while (rs.next()) {
		// String id = rs.getString(1);
		// String name = rs.getString(2);
		// String age = rs.getString(3);
		// System.out.println(id + "\t" + name + "\t" + age);
		// }

		// 事务提交
		con.commit();
	}

	@Test
	public void jdbcTransactionManageTest2() {
		try {
			Properties pop = new Properties();

			Connection con = null;
			pop.load(ClassLoader.getSystemResourceAsStream("plb/spring/dao/config/jdbc.properties"));
			Class.forName(pop.getProperty("mysql.driver"));
			String url = pop.getProperty("mysql.url");
			String user = pop.getProperty("mysql.username");
			String password = pop.getProperty("mysql.password");
			con = DriverManager.getConnection(url, user, password);
			System.out.println(con);

			String sql = "update t_spring_dao_test set age=" + 16 + " where id = 1";
			// 点禁止自动提交，设置回退
			con.setAutoCommit(false);
			final Statement stmt = con.createStatement();

			// 数据库更新操作1
			stmt.executeUpdate(sql);
			// 事务提交
			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
