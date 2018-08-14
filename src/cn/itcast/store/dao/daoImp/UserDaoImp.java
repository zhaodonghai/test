package cn.itcast.store.dao.daoImp;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import cn.itcast.store.dao.UserDao;
import cn.itcast.store.domain.User;
import cn.itcast.store.utils.JDBCUtils;

public class UserDaoImp implements UserDao {

	@Override
	public User userLogin(User user01) throws SQLException {
		String sql="select * from user where username =? and password=? and state=1";
		QueryRunner qr=new QueryRunner(JDBCUtils.getDataSource());
		return qr.query(sql, new BeanHandler<User>(User.class),user01.getUsername(),user01.getPassword());
		
	}

	@Override
	public User findUserByUsreName(String um) throws SQLException {
		String sql="select * from user where username=?";
		QueryRunner qr=new QueryRunner(JDBCUtils.getDataSource());
		return qr.query(sql, new BeanHandler<User>(User.class),um);
		
	}

	@Override
	public void userRegist(User user01) throws SQLException {
		String sql="INSERT INTO USER VALUES(?,?,?,?,?,?,?,?,?,?)";
		Object[] params={user01.getUid(),user01.getUsername(),user01.getPassword(),user01.getName(),user01.getEmail(),user01.getTelephone(),user01.getBirthday(),user01.getSex(),user01.getState(),user01.getCode()};
		QueryRunner qr=new QueryRunner(JDBCUtils.getDataSource());
		qr.update(sql,params);
	}

	@Override
	public User userActive(String code) throws SQLException {
		String sql="select * from user where code =?";
		QueryRunner qr=new QueryRunner(JDBCUtils.getDataSource());
		return qr.query(sql, new BeanHandler<User>(User.class),code);
		
	}

	@Override
	public void updateUser(User user01) throws SQLException {
		String sql="UPDATE USER SET username= ? ,PASSWORD=? ,NAME =? ,email =? ,telephone =? , birthday =?  ,sex =? ,state= ? , CODE = ? WHERE uid=?";
		Object[] params={user01.getUsername(),user01.getPassword(),user01.getName(),user01.getEmail(),user01.getTelephone(),user01.getBirthday(),user01.getSex(),user01.getState(),user01.getCode(),user01.getUid()};
		QueryRunner qr=new QueryRunner(JDBCUtils.getDataSource());
		qr.update(sql,params);
	}

	
	
}
