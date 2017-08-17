package plb.spring.dao.springjdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringJDBCTest {

	private ApplicationContext ctx = null;

	@Before
	public void before() {
		try{
		ctx = new ClassPathXmlApplicationContext(
				"classpath:plb/spring/dao/springjdbc/config/applicationContext-dao.xml");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Test
	public void dataSourceTest() throws SQLException {
		try {
			DataSource dataSource = (DataSource) ctx.getBean("dataSource");
			System.out.println(dataSource);
			ResultSet rs = dataSource.getConnection().createStatement()
					.executeQuery("SELECT count(*) as count FROM t_transaction_test");
			while (rs.next()) {
				int count = rs.getInt("count");
				Assert.assertNotEquals(0, count);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
