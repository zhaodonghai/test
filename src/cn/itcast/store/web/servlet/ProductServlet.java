package cn.itcast.store.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.store.domain.Product;
import cn.itcast.store.service.ProductService;
import cn.itcast.store.service.serviceImp.ProductServiceImp;
import cn.itcast.store.utils.PageModel;
import cn.itcast.store.utils.UUIDUtils;
import cn.itcast.store.web.base.BaseServlet;

public class ProductServlet extends BaseServlet {
	//public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}
	//findProductByPid
	public String findProductByPid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//服务端获取到pid
		String pid=request.getParameter("pid");
		//根据pid查询对应的商品信息,
		ProductService ProductService=new ProductServiceImp();
		Product pro=ProductService.findProductByPid(pid);
		//将商品放入request,
		request.setAttribute("pro", pro);
		
		String ranStr=UUIDUtils.getId();
		//在session存放一份随机字符串
		request.getSession().setAttribute("ranStr",ranStr);
		System.out.println(ranStr);
		
		//转发到product_info.jsp
		return "/jsp/product_info.jsp";
	}
	//findProductsWithCidAndPage
	public String findProductsWithCidAndPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//接受当前页
		int curNum=Integer.parseInt(request.getParameter("num"));
		//接受分类cid
		String cid=request.getParameter("cid");
		//调用业务层查询当前分类下的当前页数据功能,返回PageModel对象
		ProductService ProductService=new ProductServiceImp();
		PageModel pm=ProductService.findProductsWithCidAndPage(cid,curNum);
		//将PageModel对象存入到request
		request.setAttribute("page", pm);
		//转发到"/jsp/product_list.jsp"
		return "/jsp/product_list.jsp";
	}
}







