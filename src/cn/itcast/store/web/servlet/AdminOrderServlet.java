package cn.itcast.store.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.store.domain.Order;
import cn.itcast.store.domain.OrderItem;
import cn.itcast.store.service.OrderService;
import cn.itcast.store.service.serviceImp.OrderServiceImp;
import cn.itcast.store.web.base.BaseServlet;
import net.sf.json.JSONArray;

public class AdminOrderServlet extends BaseServlet {
	
	OrderService OrderService=new OrderServiceImp();
	
	//?method=findAllOrders&
	public String findAllOrders(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取state,
		String state=request.getParameter("state");
		
		//用来存放全部订单或者不同状态订单
		List<Order> list=null;
		if(null==state||"".equals(state)){
			//如果获取不到查询全部订单,
			list=OrderService.findAllOrders();
		}else{
			//如果可以获取到state,查询不同状态下的订单	
			list=OrderService.findAllOrdersWithState(state);
		}
		//将查询到的订单放入request,
		request.setAttribute("orders", list);
		//转发到admin/order/list.jsp
		return "/admin/order/list.jsp";
	}
	//findOrderByOid
	public String findOrderByOid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("application/json;charset=utf-8");
		//接受oid,
		String oid=request.getParameter("oid");
		//根据oid查询订单包括订单上的所有订单项以及商品信息,
		Order order=OrderService.findOrderByOid(oid);
		//将所有的订单项以及商品信息转换为JSON格式的数据返回到客户端
		List<OrderItem> list=order.getList();
		String jsonStr=JSONArray.fromObject(list).toString();
		response.getWriter().print(jsonStr);
		return null;
	}
	//sendGood
	public String sendGood(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取oid
		String oid=request.getParameter("oid");
		//根据oid查询订单
		Order order=OrderService.findOrderByOid(oid);
		//设置订单状态为3
		order.setState(3);
		//更新订单状态
		OrderService.updateOrder(order);
		//重新定向到已经发货订单
		response.sendRedirect(request.getContextPath()+"/AdminOrderServlet?method=findAllOrders&state=3");
		return null;
	}
	
}