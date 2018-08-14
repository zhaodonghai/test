package cn.itcast.store.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.store.service.CategoryService;
import cn.itcast.store.service.serviceImp.CategoryServiceImp;
import cn.itcast.store.web.base.BaseServlet;

public class AdminProductServlet extends BaseServlet {

	public String addProUI(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//_服务端查询到所有分类,
		CategoryService CategoryService=new CategoryServiceImp();
		//放入request中,
		request.setAttribute("allCats", CategoryService.findAllCats());
		//转发到admin/product/add.jsp
		return "admin/product/add.jsp";
	}

}