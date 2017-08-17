package plb.spring.dao.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DirtyReadTest {

	public static void main(String[] args) throws SQLException {
		Thread threadA = new Thread(new Runnable() {
			public void run() {
				try {
					dirtyReadAdd100();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		threadA.setName("Transaction A");

		Thread threadB = new Thread(new Runnable() {
			public void run() {
				try {
					dirtyReadDel500();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		threadB.setName("Transaction B");

		threadB.start();
		try {
			Thread.sleep(1000 * 1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		threadA.start();

	}

	public static void init() {
		String sql = "insert into t_spring_dao_test values(2000,'DirtyReadTest',15,1000)";
		try {
			JDBCUtil.getConnection().createStatement().execute(sql);
			JDBCUtil.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void dirtyReadAdd100() throws SQLException {
		Connection con = JDBCUtil.getConnection();
		con.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
		con.setAutoCommit(false);

		String querySql = "select * from t_spring_dao_test where id = 2000";
		ResultSet rs = con.createStatement().executeQuery(querySql);
		int amt = -1;
		while (rs.next()) {
			amt = rs.getInt("amount");
		}
		System.out.println("[" + Thread.currentThread().getName() + "]当前用户余额:" + amt + "元");
		amt += 100;
		String updateSql = "update t_spring_dao_test set amount = " + amt + " where id=2000";
		con.createStatement().execute(updateSql);

		con.commit();
		JDBCUtil.close();
	}

	public static void dirtyReadDel500() throws SQLException {
		Connection con = JDBCUtil.getConnection();

		con.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
		con.setAutoCommit(false);

		String querySql = "select * from t_spring_dao_test where id = 2000";
		ResultSet rs = con.createStatement().executeQuery(querySql);
		int amt = -1;
		while (rs.next()) {
			amt = rs.getInt("amount");
		}
		System.out.println("[" + Thread.currentThread().getName() + "]当前用户余额:" + amt + "元");
		amt -= 500;
		String updateSql = "update t_spring_dao_test set amount = " + amt + " where id=2000";
		con.createStatement().execute(updateSql);

		try {
			Thread.sleep(1000 * 3);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		con.rollback();
		JDBCUtil.close();
	}
}
