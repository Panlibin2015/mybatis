package shiyanlou.mybatis.one2more.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import shiyanlou.mybatis.one2more.mapper.ClassesMapper;
import shiyanlou.mybatis.one2more.model.Classes;

public class One2MoreTest {

	public SqlSessionFactory factory;

	@Before
	public void before() {
		InputStream input = null;
		try {
			input = new FileInputStream("src/mybatis.cfg.xml");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		factory = new SqlSessionFactoryBuilder().build(input);
	}

	@Test
	public void testSqlSession() {
		System.out.println(factory);
	}

	@Test
	public void oneToOneTest() {
		SqlSession session = factory.openSession();
		ClassesMapper classMapper = session.getMapper(ClassesMapper.class);

		try {
			Classes classes = classMapper.selectClassAndStudentById(1);
			System.out.println(classes.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.commit();
			session.close();
		}
	}

}
