package cn.zhuqi.oa.dao;

import java.util.List;

import cn.zhuqi.oa.vo.PagerVO;
import cn.zhuqi.system.SystemContext;

public interface BaseDao {

	/**
	 * 保存实体对象
	 * 
	 * @param entity
	 */
	public void save(Object entity);

	/**
	 * 删除某个实体对象
	 * 
	 * @param entity
	 *            必须是某个实体对象
	 */
	public void del(Object entity);

	/**
	 * 更新实体对象
	 * 
	 * @param entity
	 *            必须是某个实体对象
	 */
	public void update(Object entity);

	/**
	 * 根据ID查询实体对象
	 * 
	 * @param <T>
	 *            某种实体对象类型
	 * @param entityClass
	 *            实体对象的Class
	 * @param id
	 *            实体对象的ID
	 * @return 相应的实体对象
	 */
	public <T> T findById(Class<T> entityClass, int id);
	
	/**
	 * 根据ID得到实体对象
	 * 
	 * @param <T>
	 *            某种实体对象类型
	 * @param entityClass
	 *            实体对象的Class
	 * @param id
	 *            实体对象的ID
	 * @return 相应的实体对象
	 */
	public <T> T getById(Class<T> entityClass, int id);

	/**
	 * 查询某种类型的所有的实体对象（不包含任何条件）
	 * 
	 * @param <T>
	 * @param entityClass
	 * @return
	 */
	public <T> List<T> findAll(Class<T> entityClass);

	/**
	 * 把实体对象当成查询条件，根据实体对象中包含的属性，自动生成SQL语句查询相应的实体对象
	 * 
	 * @param entity
	 *            传进来的参数必须是某种实体对象
	 * @return
	 */
	public List find(Object entity);

	/**
	 * 分页查询，查询语句不带任何?参数 将从SystemContext中取出offset和pagesize两个参数，进行分页查询
	 * 
	 * @param query
	 *            查询的HQL语句
	 * @return
	 * @see SystemContext
	 * @see PagerVO
	 */
	public PagerVO findPaginated(String query);

	/**
	 * 分页查询，查询语句只带一个?参数 将从SystemContext中取出offset和pagesize两个参数，进行分页查询
	 * 
	 * @param query
	 *            HQL查询语句
	 * @param param
	 *            HQL查询语句中用于替换?的参数值
	 * @return
	 */
	public PagerVO findPaginated(String query, Object param);

	/**
	 * 分页查询，查询语句带若干个?参数 将从SystemContext中取出offset和pagesize两个参数，进行分页查询
	 * 
	 * @param query
	 *            HQL查询语句
	 * @param params
	 *            HQL查询语句中若干个?参数所对应的值
	 * @return
	 */
	public PagerVO findPaginated(String query, Object[] params);

	/**
	 * 根据HQL语句进行分页查询，查询语句中不包含?参数
	 * 
	 * @param query
	 *            HQL查询语句
	 * @param offset
	 *            从第几行开始查询
	 * @param pagesize
	 *            最多返回多少行数据
	 * @return
	 */
	public PagerVO findPaginated(String query, int offset, int pagesize);

	/**
	 * 根据HQL语句进行分页查询，查询语句中只包含一个?参数
	 * 
	 * @param query
	 *            HQL查询语句
	 * @param param
	 *            HQL查询语句中用于替换?的参数值
	 * @param offset
	 *            从第几行开始查询
	 * @param pagesize
	 *            最多返回多少行数据
	 * @return
	 */
	public PagerVO findPaginated(String query, Object param, int offset,
								 int pagesize);

	/**
	 * 根据HQL语句进行分页查询，查询语句中包含若干个?参数
	 * 
	 * @param query
	 *            HQL查询语句
	 * @param params
	 *            HQL查询语句中用于替换?的参数值
	 * @param offset
	 *            从第几行开始查询
	 * @param pagesize
	 *            最多返回多少行数据
	 * @return
	 */
	public PagerVO findPaginated(String query, Object[] params, int offset,
								 int pagesize);

}
