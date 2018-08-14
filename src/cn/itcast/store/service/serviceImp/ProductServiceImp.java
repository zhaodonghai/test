package cn.itcast.store.service.serviceImp;

import java.sql.SQLException;
import java.util.List;

import cn.itcast.store.dao.ProductDao;
import cn.itcast.store.dao.daoImp.ProductDaoImp;
import cn.itcast.store.domain.Product;
import cn.itcast.store.service.ProductService;
import cn.itcast.store.utils.PageModel;

public class ProductServiceImp implements ProductService {
	@Override
	public void saveProduct(Product product) throws SQLException {
		ProductDao.saveProduct(product);
	}

	ProductDao ProductDao=new ProductDaoImp();
	
	
	@Override
	public PageModel findProductsWithCidAndPage(String cid, int curNum) throws SQLException {
		//1_创建PageModel对象,目的:携带分页参数 select count(*) from product where cid=?
		int totalRecords=ProductDao.findTotalNum(cid);
		PageModel pm=new PageModel(curNum, totalRecords, 12);
		//2_关联集合 SELECT * FROM product WHERE cid=1 LIMIT (当前页-1)*5,5;
		List<Product> list=ProductDao.findProductsWithCidAndPage(cid,pm.getStartIndex(),pm.getPageSize());
		pm.setList(list);
		//3_关联url
		pm.setUrl("ProductServlet?method=findProductsWithCidAndPage&cid="+cid);
		return pm;
	}


	@Override
	public Product findProductByPid(String pid) throws SQLException {
		return ProductDao.findProductByPid(pid);
		
	}

	
	@Override
	public List<Product> findNewProducts()throws SQLException  {
		return ProductDao.findNewProducts();
		
	}

	@Override
	public List<Product> findHotProducts() throws SQLException {
		return ProductDao.findHotProducts();
	}

}
