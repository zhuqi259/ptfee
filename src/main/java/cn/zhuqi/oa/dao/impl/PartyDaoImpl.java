package cn.zhuqi.oa.dao.impl;

import org.springframework.stereotype.Repository;

import cn.zhuqi.oa.dao.PartyDao;
import cn.zhuqi.oa.model.Company;
import cn.zhuqi.oa.model.Party;

@Repository("partyDao")
public class PartyDaoImpl extends BaseDaoImpl implements PartyDao {

	@Override
	public Company findCompany() {

		getSession().enableFilter("no_contain_person");

		String hql = "select c from Company c where c.parent is null";

		return (Company) getSession().createQuery(hql).uniqueResult();
	}

	@Override
	public Party findById(int id) {
		// 能够返回实际的Party类型：比如Company/Department等等
		return (Party) getSession()
				.createQuery("select p from Party p where p.id = ?")
				.setParameter(0, id).uniqueResult();
	}

	
	@Override
	public void saveOrUpdate(Party party) {

		/**
		 * 如果party具有数据库标识，则自动发出更新语句；否则自动发出插入语句
		 * 对于id为int类型的实体对象，如果id不等于0，相当于具有数据库标识！
		 */
		getSession().saveOrUpdate(party);
	}

}
