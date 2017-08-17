package plb.spring.dao.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UnrepeatableReadTest {

	public static void main(String[] args) throws SQLException {
		Thread threadA = new Thread(new Runnable() {
			public void run() {
				try {
					repeatRead();
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
					repeatReadUpdate();
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
		String sql = "insert into t_transaction_test values(2,'RepeatableRead',1000)";
		try {
			JDBCUtil.getConnection().createStatement().execute(sql);
			JDBCUtil.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void repeatReadUpdate() throws SQLException {
		Connection con = JDBCUtil.getConnection();
		con.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED); 
//		con.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
		con.setAutoCommit(false);

		String querySql = "select * from t_transaction_test where id = 2";
		ResultSet rs = con.createStatement().executeQuery(querySql);
		int amt = -1;
		while (rs.next()) {
			amt = rs.getInt("money");
		}
		System.out.println("[" + Thread.currentThread().getName() + "]当前用户余额1:" + amt + "元");

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		rs = con.createStatement().executeQuery(querySql);
		int amt2 = -1;
		while (rs.next()) {
			amt2 = rs.getInt("money");
		}
		System.out.println("[" + Thread.currentThread().getName() + "]当前用户余额2:" + amt2 + "元");
		
		con.commit();
		JDBCUtil.close();
	}

	public static void repeatRead() throws SQLException {
		Connection con = JDBCUtil.getConnection();
		con.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
		con.setAutoCommit(false);

		String querySql = "select * from t_transaction_test where id = 2";
		ResultSet rs = con.createStatement().executeQuery(querySql);
		int amt = -1;
		while (rs.next()) {
			amt = rs.getInt("money");
		}
		System.out.println("[" + Thread.currentThread().getName() + "]当前用户余额:" + amt + "元");
		amt -= 500;
		String updateSql = "update t_transaction_test set money = " + amt + " where id=2";
		con.createStatement().execute(updateSql);

		try {
			Thread.sleep(1000 * 3);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		con.commit();
		JDBCUtil.close();
	}
}
