package cn.itcast.store.web.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.store.domain.User;
import cn.itcast.store.service.UserService;
import cn.itcast.store.service.serviceImp.UserServiceImp;
import cn.itcast.store.utils.BeanFactory;
import cn.itcast.store.utils.CookUtils;
import cn.itcast.store.utils.MyBeanUtils;
import cn.itcast.store.utils.UUIDUtils;
import cn.itcast.store.web.base.BaseServlet;

public class UserServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	UserService UserService = (UserService) BeanFactory
			.createObject("UserService");

	// registUI
	public String registUI(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		return "/jsp/register.jsp";
	}

	// userExists
	public String userExists(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 接受用户名
		String um = request.getParameter("username");
		// 调用业务层功能,根据用户名查询用户

		User user = UserService.findUserByUsreName(um);
		// 完成响应
		if (null != user) {
			response.getWriter().print("11");
		} else {
			response.getWriter().print("00");
		}
		return null;
	}

	// userRegist
	public String userRegist(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		// 1_接受用户数据,
		User user01 = MyBeanUtils.populate(User.class,
				request.getParameterMap());
		// 2_部分数据是通过程序来设置的:uid,state,code
		user01.setUid(UUIDUtils.getId());
		user01.setState(0);
		user01.setCode(UUIDUtils.getId());
		// 3_调用业务层注册功能,向用户邮箱发送一份激活邮件
		UserService UserService = new UserServiceImp();
		UserService.userRegist(user01);
		// 4_向客户端提示:用户注册成功,请激活,转发到提示页面
		request.setAttribute("msg", "用户注册成功,请激活!");
		return "/jsp/info.jsp";
	}

	// active
	public String active(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 服务端获取到激活码,和数据库中已经存在的激活码对比,如果存在,激活成功,更改用户激活状态1,转发到登录页面,提示:激活成功,请登录.
		String code = request.getParameter("code");
		// 调用业务层功能:根据激活码查询用户 select * from user where code=?
		User user01 = UserService.userActive(code);
		// 如果用户不为空,激活码正确的,可以激活
		if (null != user01) {
			user01.setState(1);
			user01.setCode("");
			UserService.updateUser(user01);
		}
		// 转发到登录页面,提示:激活成功,请登录
		request.setAttribute("msg", "用户激活成功,请登录");
		return "/jsp/login.jsp";

	}

	// loginUI
	public String loginUI(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Cookie ck = CookUtils.getCookieByName("remUser", request.getCookies());
		if (null != ck) {
			request.setAttribute("remUser", ck.getValue());
		}

		return "/jsp/login.jsp";
	}

	// userLogin
	public String userLogin(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 接受用户名和密码
		User user01 = MyBeanUtils.populate(User.class,
				request.getParameterMap());
		// 调用业务层登录功能
		User user02 = UserService.userLogin(user01);

		if (null != user02) {
			// 登录成功,向session中放入用户信息,重定向到首页
			request.getSession().setAttribute("loginUser", user02);

			// 在登录成功的基础上,判断用户是否选中自动登录复选框
			String autoLogin = request.getParameter("autoLogin");
			if ("yes".equals(autoLogin)) {
				// 用户选中自动登录复选框
				Cookie ck = new Cookie("autoLogin", user02.getUsername() + "#"
						+ user02.getPassword());
				ck.setPath("/store_v1");
				ck.setMaxAge(23423424);
				response.addCookie(ck);
			}
			// remUser
			String remUser = request.getParameter("remUser");
			if ("yes".equals(remUser)) {
				// 用户选中自动登录复选框
				Cookie ck = new Cookie("remUser", user02.getUsername());
				ck.setPath("/store_v1");
				ck.setMaxAge(23423424);
				response.addCookie(ck);
			}

			response.sendRedirect(request.getContextPath() + "/jsp/index.jsp");
		} else {
			// 登录失败,向request放入提示信息,转发到登录页面,显示提示userLogin
			request.setAttribute("msg", "用户名和密码不匹配!");
			return "/jsp/login.jsp";
		}
		return null;
	}

	// logOut
	public String logOut(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 用户退出,清空用户session
		request.getSession().invalidate();
		Cookie ck = CookUtils
				.getCookieByName("autoLogin", request.getCookies());
		if (null != ck) {
			ck.setMaxAge(0);
			ck.setPath("/store_v1");
			response.addCookie(ck);
		}
		response.sendRedirect(request.getContextPath() + "/jsp/index.jsp");
		return null;
	}

}