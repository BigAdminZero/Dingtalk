package com.dingtalk.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dingtalk.bean.User;
import com.dingtalk.dao.UserMapper;



/**
 * 测试dao层的工作
 * @author user
 *推荐Spring的项目使用spring的单元测试，可以自动注入我们需要的组件
 *1.导入springTest模块
 *2.@ContextConfiguration指定Spring配置文件的位置
 *3.直接autowired要使用的组件即可
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class MapperTest {
	
	@Autowired
	UserMapper userMapper;
	
	/**
	 * 测试UserMapper
	 */
	@Test
	public void  testCDUS(){
		//1.创建spring容器
		//ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		//2.从容器中获取mapper
		//UserMapper bean = (UserMapper) ctx.getBean("UserMapper.class");
	
		System.out.println(userMapper);
		
		userMapper.insertSelective(new User("王昆林", "zero127115", 1));
		userMapper.insertSelective(new User("殷俊", "123456", 1));
		
		
	}
}
