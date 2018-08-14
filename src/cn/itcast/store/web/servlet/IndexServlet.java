package cn.itcast.store.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.store.domain.Category;
import cn.itcast.store.domain.Product;
import cn.itcast.store.service.CategoryService;
import cn.itcast.store.service.ProductService;
import cn.itcast.store.service.serviceImp.CategoryServiceImp;
import cn.itcast.store.service.serviceImp.ProductServiceImp;
import cn.itcast.store.web.base.BaseServlet;

public class IndexServlet extends BaseServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		//查询全部分类
		CategoryService CategoryService=new CategoryServiceImp();
		List<Category> list=CategoryService.findAllCats();
		//放入request域对象
		req.setAttribute("allCats", list);

		ProductService ProductService=new ProductServiceImp();
		//获取最新9件商品
		List<Product> list01=ProductService.findNewProducts();
		//获取最热9件商品
		List<Product> list02=ProductService.findHotProducts();
		//将最新商品放入request
		req.setAttribute("news", list01);
		//将最热商品放入request
		req.setAttribute("hots", list02);
		
		
		//转发到/jsp/index.jsp
		return "/jsp/index.jsp";
	}
}