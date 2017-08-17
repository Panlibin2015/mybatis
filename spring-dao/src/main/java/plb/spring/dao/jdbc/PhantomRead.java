package plb.spring.dao.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 幻象读
 * 
 * @author 潘利斌
 *
 */
public class PhantomRead {

	public static void main(String[] args) throws SQLException {
		Thread threadA = new Thread(new Runnable() {
			public void run() {
				try {
					phantomRead();
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
					phantomUpdate();
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

	public static void phantomRead() throws SQLException {
		Connection con = JDBCUtil.getConnection();
		con.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
		// con.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
		con.setAutoCommit(false);

		String querySql = "select count(1) as count from t_transaction_test";
		ResultSet rs = con.createStatement().executeQuery(querySql);
		int amt = -1;
		while (rs.next()) {
			amt = rs.getInt("count");
		}
		System.out.println("[" + Thread.currentThread().getName() + "]当前查询记录数:" + amt);

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		rs = con.createStatement().executeQuery(querySql);
		int amt2 = -1;
		while (rs.next()) {
			amt2 = rs.getInt("count");
		}
		System.out.println("[" + Thread.currentThread().getName() + "]当前查询记录数2:" + amt2);

		con.commit();
		JDBCUtil.close();
	}

	public static void phantomUpdate() throws SQLException {
		Connection con = JDBCUtil.getConnection();
		con.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
		con.setAutoCommit(false);

		try {
			Thread.sleep(1000 * 3);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String querySql = "insert into t_transaction_test(name,money) values('PhantomRead4',1000);";
		con.createStatement().executeUpdate(querySql);

		con.commit();
		JDBCUtil.close();
	}
}
