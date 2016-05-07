package cn.zhuqi.oa.service;

import cn.zhuqi.oa.model.Company;
import cn.zhuqi.oa.model.Party;
import cn.zhuqi.oa.vo.PagerVO;

public interface PartyService {
	
	/**
	 * 添加Party对象
	 * @param party
	 */
	public void addParty(Party party);
	
	/**
	 * 查询当前系统中的总公司对象
	 * @return
	 */
	public Company currentCompany();
	
	/**
	 * 更新Party对象
	 * @param party
	 */
	public void updateParty(Party party);
	
	/**
	 * 删除party对象
	 * 如果party对象下面有子party信息，将不允许删除，抛出RuntimeException异常
	 * @param partyId
	 */
	public void delParty(int partyId);
	
	/**
	 * 根据ID查询party对象
	 * @param partyId
	 * @return
	 */
	public Party findPartyById(int partyId);
	
	/**
	 * 根据查询条件查询人员列表，这个查询条件将匹配人员姓名或性别
	 * @param parentId 在哪个公司、部门或岗位下面查询人员，如果此值为0，
	 *  将查询所有的人员信息
	 * @param search 查询条件
	 * @return 返回列表中的元素是数组：人员ID，姓名，性别，电话
	 */
	public PagerVO findPersons(int parentId, String search);
	
	/**
	 * 保存公司对象
	 * 如果公司对象不存在（其ID为0），则添加
	 * 如果公司对象已存在（其ID不为0），则更新
	 * @param current 公司对象
	 */
	public void saveCompany(Company current);
}
