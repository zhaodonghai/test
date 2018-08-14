package cn.itcast.store.service.serviceImp;

import java.sql.SQLException;

import cn.itcast.store.dao.UserDao;
import cn.itcast.store.dao.daoImp.UserDaoImp;
import cn.itcast.store.domain.User;
import cn.itcast.store.service.UserService;
import cn.itcast.store.utils.BeanFactory;
import cn.itcast.store.utils.MailUtils;

public class UserServiceImp implements UserService {
	UserDao UserDao=(UserDao)BeanFactory.createObject("UserDao");
	
	@Override
	public User userLogin(User user01) throws SQLException {
		return UserDao.userLogin(user01);
	}
	
	@Override
	public User findUserByUsreName(String um) throws SQLException{

		return UserDao.findUserByUsreName(um);
		
	}

	@Override
	public void userRegist(User user01) throws SQLException{
		//3_调用业务层注册功能
		UserDao.userRegist(user01);
		try {
			//向用户邮箱发送一份激活邮件
			MailUtils.sendMail(user01.getEmail(),user01.getCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public User userActive(String code) throws SQLException {
		return UserDao.userActive(code);
		
	}

	@Override
	public void updateUser(User user01) throws SQLException {
		UserDao.updateUser(user01);
	}
	
}
