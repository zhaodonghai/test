package cn.itcast.store.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.store.domain.Category;
import cn.itcast.store.service.CategoryService;
import cn.itcast.store.service.serviceImp.CategoryServiceImp;
import cn.itcast.store.utils.JedisUtils;
import cn.itcast.store.utils.UUIDUtils;
import cn.itcast.store.web.base.BaseServlet;
import redis.clients.jedis.Jedis;

public class AdminCategoryServlet extends BaseServlet {

	CategoryService CategoryService=new CategoryServiceImp();

	public String findAllCats(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//调用service查询全部分类信息,
		List<Category> allCats = CategoryService.findAllCats();
		//放入request
		request.setAttribute("allCats", allCats);
		//转发到admin/category/list.jsp
		return "/admin/category/list.jsp";
	}
	//addCatUI
	public String addCatUI(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/admin/category/add.jsp";
		
	}
	//addCat
	public String addCat(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String cname=request.getParameter("cname");
		Category c=new Category();
		c.setCid(UUIDUtils.getId());
		c.setCname(cname);
		CategoryService.saveCat(c);
		
		//跟新缓存
		Jedis jedis = JedisUtils.getJedis();
		jedis.del("allCats");
		JedisUtils.closeJedis(jedis);
		
		response.sendRedirect(request.getContextPath()+"/AdminCategoryServlet?method=findAllCats");
		return null;
	}
}



