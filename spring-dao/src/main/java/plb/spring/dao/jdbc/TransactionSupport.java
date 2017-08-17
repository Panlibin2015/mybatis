package plb.spring.dao.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import com.mysql.jdbc.DatabaseMetaData;

public class TransactionSupport {
	public static void main(String[] args) throws SQLException {
		Connection conn = JDBCUtil.getConnection();
		DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
		System.out.println(dm.supportsTransactions());
		System.out.println(dm.supportsTransactionIsolationLevel(Connection.TRANSACTION_READ_COMMITTED));
		
	}
}
