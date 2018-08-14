package cn.itcast.store.service.serviceImp;

import java.sql.SQLException;
import java.util.List;

import cn.itcast.store.dao.CategoryDao;
import cn.itcast.store.dao.daoImp.CategoryDaoImp;
import cn.itcast.store.domain.Category;
import cn.itcast.store.service.CategoryService;
import cn.itcast.store.utils.BeanFactory;

public class CategoryServiceImp implements CategoryService {

	CategoryDao CategoryDao=(CategoryDao)BeanFactory.createObject("CategoryDao");
	
	@Override
	public void saveCat(Category c) throws SQLException {
		CategoryDao.saveCat(c);	
	}
	
	@Override
	public List<Category> findAllCats() throws SQLException{
		return CategoryDao.findAllCats();
	}

}