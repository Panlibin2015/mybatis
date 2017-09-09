package com.shiyanlou.mybatis.testpackage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import com.shiyanlou.mybatis.mapper.UserMapper;
import com.shiyanlou.mybatis.model.User;

public class UserTest {

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
	public void insertTest() {
		User user = generatorUser("longqihai", "aptx1230", "彩虹城小区43幢111室", "男");
		SqlSession session = factory.openSession();
		UserMapper userMapper = session.getMapper(UserMapper.class);
		try {
			userMapper.insertUser(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			session.commit();
			session.close();
		}
	}

	@Test
	public void deleteTest() {
		SqlSession session = factory.openSession();
		UserMapper userMapper = session.getMapper(UserMapper.class);
		try {
			int count = userMapper.deleteUser(2);
			System.out.println(count);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.commit();
			session.close();
		}
	}

	@Test
	public void updateTest() {
		SqlSession session = factory.openSession();
		UserMapper userMapper = session.getMapper(UserMapper.class);
		try {
			User user = generatorUser("longqihai2", null, "杭州", "女");
			user.setId(4);
			int count = userMapper.updateUser(user);
			System.out.println(count);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.commit();
			session.close();
		}
	}

	@Test
	public void selectTest() {
		SqlSession session = factory.openSession();
		UserMapper userMapper = session.getMapper(UserMapper.class);
		try {
			List<User> users = userMapper.selectAllUser();
			for (User user : users) {
				System.out.println(user.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	private User generatorUser() {
		User user = new User();
		user.setAddress("杭州市" + new Random().nextInt(100));
		user.setPassword(new Random().nextInt(30000) + "");
		user.setUsername("小李" + new Random().nextInt(100));
		return user;
	}

	private User generatorUser(String username, String password, String address, String sex) {
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setAddress(address);
		user.setSex(sex);
		return user;
	}

}
