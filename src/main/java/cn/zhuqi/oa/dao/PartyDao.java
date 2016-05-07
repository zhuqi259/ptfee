package cn.zhuqi.oa.dao;

import cn.zhuqi.oa.model.Company;
import cn.zhuqi.oa.model.Party;

public interface PartyDao extends BaseDao {
	/**
	 * 查找顶层公司
	 * 
	 * @return
	 */
	public Company findCompany();

	/**
	 * 通过ID查找Party对象
	 * 
	 * @param id
	 * @return
	 */
	public Party findById(int id);

	/**
	 * 
	 * 添加或更新Party对象
	 * 
	 * @param party
	 */
	public void saveOrUpdate(Party party);
}
