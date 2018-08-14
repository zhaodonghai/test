package cn.itcast.store.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JDBCUtils {
	private static ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource(
			"itcast");
	private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();

	/**
	 * 从线程中获取连接
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		Connection connection = threadLocal.get();
		if (connection == null) {
			connection = comboPooledDataSource.getConnection();
			threadLocal.set(connection);
		}
		return connection;
	}

	public static DataSource getDataSource() {
		return comboPooledDataSource;
	}

	public static void closeResource(Statement statement, ResultSet resultSet) {
		closeResultSet(resultSet);
		closeStatement(statement);
	}

	public static void closeResource(Connection connection,
			Statement statement, ResultSet resultSet) {
		closeResource(statement, resultSet);
		closeConn(connection);
	}

	private static void closeConn(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
				threadLocal.remove();
			} catch (Exception e) {
				e.printStackTrace();
			}
			connection = null;
		}

	}

	private static void closeStatement(Statement statement) {

		if (statement != null) {
			try {
				statement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			statement = null;
		}

	}

	private static void closeResultSet(ResultSet resultSet) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			resultSet = null;
		}

	}

	public static void startTransaction() throws SQLException {
		getConnection().setAutoCommit(false);
	}

	public static void commitAndClose() {
		Connection connection = null;
		try {
			connection = getConnection();
			connection.commit();
			connection.close();
			threadLocal.remove();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void rollbackAndClose() {
		Connection connection = null;
		try {
			connection = getConnection();
			connection.rollback();
			connection.close();
			threadLocal.remove();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws SQLException {
		System.out.println(getConnection());
	}
}
