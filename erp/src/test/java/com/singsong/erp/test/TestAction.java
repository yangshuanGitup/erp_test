package com.singsong.erp.test;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.singsong.erp.business.api.email.ui.BillCreateAction;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath:/spring/*.xml" })
public class TestAction {
	// 模拟request,response
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;

	// 注入loginController
	@Autowired
	private BillCreateAction billCreateAction;

	// 执行测试方法之前初始化模拟request,response
	@Before
	public void setUp() {
		request = new MockHttpServletRequest();
		request.setCharacterEncoding("UTF-8");
		response = new MockHttpServletResponse();
	}

	/**
	 * 
	 * @Title：testLogin
	 * @Description: 测试用户登录
	 */
	@Test
	public void testListPage() {
		try {
			 billCreateAction.group(request, response);
//			billCreateAction.single(request, response);
//			billCreateAction.singleTest(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		String s="2017-08-26 00:00:00.0";
		System.out.println(DateTime.parse(s.substring(0,s.length()-2), DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate());
	}
}
