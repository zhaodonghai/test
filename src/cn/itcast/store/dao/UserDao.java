package cn.itcast.store.dao;

import java.sql.SQLException;

import cn.itcast.store.domain.User;

public interface UserDao {

	User findUserByUsreName(String um) throws SQLException;

	void userRegist(User user01)throws SQLException;

	User userActive(String code)throws SQLException;

	void updateUser(User user01)throws SQLException;

	User userLogin(User user01)throws SQLException;

}
