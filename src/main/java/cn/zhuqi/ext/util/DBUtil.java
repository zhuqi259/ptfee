package cn.zhuqi.ext.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Administrator
 * 
 */
public class DBUtil {

	private Connection conn;
	private Statement st;
	private PreparedStatement pps;
	private ResultSet rs;

	private String driver = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/ptfee2";
	private String username = "root";
	private String password = "root";

	public DBUtil() {
		super();
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public DBUtil(String driver, String url, String username, String password) {
		super();
		this.driver = driver;
		this.url = url;
		this.username = username;
		this.password = password;
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取连接的方法
	 * 
	 * @return Connection 一个有效的数据库连接
	 */
	private Connection getConnection() {
		return getConnection(url, username, password);
	}

	private Connection getConnection(String url, String username,
			String password) {
		try {
			Date date = new Date();
			long start = date.getTime();
			Connection conn = DriverManager.getConnection(url, username,
					password);
			long end = new Date().getTime();
			System.out.println("建立连接耗时" + (end - start) + "ms");
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 用于执行更新的方法
	 * 
	 * @param sql
	 *            String 类型的SQL语句（insert delete update）
	 * @return Integer 表示受影响的行数
	 */
	public int update(String sql) {
		int row = -1;
		try {
			conn = getConnection();
			st = conn.createStatement();
			row = st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return row;
	}

	/**
	 * 基于PreparedStatement的修改方法
	 * 
	 * @param sql
	 *            String 类型的SQL语句（insert delete update）
	 * @param obj
	 *            动态参数可以是一个数组
	 * @return Integer 表示受影响的行数
	 */
	public int update(String sql, Object... obj) {
		try {
			conn = getConnection();
			pps = conn.prepareStatement(sql);
			int length = 0;

			// length=obj.length; //依据数组的长度获取到 预处理的sql语句中的参数个数
			// 通过 ParameterMetaData 获取到预处理的sql语句中的参数个数
			ParameterMetaData pmd = pps.getParameterMetaData();
			length = pmd.getParameterCount();
			for (int i = 0; i < length; i++) {
				pps.setObject(i + 1, obj[i]);
			}
			return pps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return -1;
	}

	/**
	 * 批量更新
	 * 
	 * @param sql
	 * @param obj
	 * @return
	 */
	public int updateBatch(String sql, List<Map<String, Object>> list,
			Object... obj) {
		try {
			// 1. 建立与数据库的连接
			conn = getConnection();
			// 2. 执行sql语句
			// 1).先创建PreparedStatement语句(发送slq请求）：
			pps = conn.prepareStatement(sql);
			conn.setAutoCommit(false);// 1,首先把Auto commit设置为false,不让它自动提交
			int length = 0;
			// 通过 ParameterMetaData 获取到预处理的sql语句中的参数个数
			ParameterMetaData pmd = pps.getParameterMetaData();
			length = pmd.getParameterCount();

			for (Map<String, Object> map : list) {
				for (int i = 0; i < length; i++) {
					// 2) 设置sql语句1
					pps.setObject(i + 1, map.get(obj[i]));
				}
				// 3) 将一组参数添加到此 PreparedStatement 对象的批处理命令中。
				pps.addBatch();
			}

			// 4) 将一批参数提交给数据库来执行，如果全部命令执行成功，则返回更新计数组成的数组。
			int[] ret = pps.executeBatch();
			System.out.println("更新成功！");
			// 若成功执行完所有的插入操作，则正常结束
			conn.commit();// 2,进行手动提交（commit）
			System.out.println("提交成功!");
			conn.setAutoCommit(true);// 3,提交完成后回复现场将Auto commit,还原为true,
			int ii = 0;
			for (int i : ret) {
				ii += i;
			}
			return ii;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				// 若出现异常，对数据库中所有已完成的操作全部撤销，则回滚到事务开始状态
				if (!conn.isClosed()) {
					conn.rollback();// 4,当异常发生执行catch中SQLException时，记得要rollback(回滚)；
					System.out.println("插入失败，回滚！");
					conn.setAutoCommit(true);
					return -1;
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			close();
		}
		return -1;
	}

	public Map<String, Object> getOneRow(String sql) {
		List<Map<String, Object>> list = queryToList(sql);
		return list.size() > 0 ? list.get(0) : null;
	}

	public List<Map<String, Object>> queryToList(String sql) {

		List<Map<String, Object>> data = new LinkedList<Map<String, Object>>();
		try {
			conn = getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(sql);

			// ResultSetMetaData 是结果集元数据 他可以获取到结果中的信息 例如：结果集中共包括多少列，每列的名称和类型等信息
			ResultSetMetaData rsmd = rs.getMetaData();
			int columncount = rsmd.getColumnCount();
			// while条件成立表明结果集中存在数据
			while (rs.next()) {
				// 创建一个HashMap用于存储一条数据
				HashMap<String, Object> onerow = new HashMap<String, Object>();
				// for循环没循环一次表示获取到一条数据的一个属性（字段）
				for (int i = 0; i < columncount; i++) {
					String columnName = rsmd.getColumnName(i + 1);
					onerow.put(columnName, rs.getObject(i + 1));
				}
				data.add(onerow);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return data;
	}

	private void close() {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		try {
			if (conn != null && !conn.isClosed()) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		DBUtil dbu = new DBUtil();
		// dbu.getConnection();
		String sql = "select * from t_eproject";
		List<Map<String, Object>> list = dbu.queryToList(sql);
		System.out.println(list);
		// dbu.close();
		String sql1 = "insert into t_eproject(fid, kgtime) values(?, ?)";
		dbu.update(sql1, "JL2013004", new Date());
	}
}