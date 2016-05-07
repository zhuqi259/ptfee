package cn.zhuqi.oa.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.zhuqi.oa.dao.PartyDao;
import cn.zhuqi.oa.model.Company;
import cn.zhuqi.oa.model.Party;
import cn.zhuqi.oa.service.PartyService;
import cn.zhuqi.oa.vo.PagerVO;

@Service("partyService")
public class PartyServiceImpl implements PartyService {

	@Resource
	private PartyDao partyDao;
	
	@Override
	public void addParty(Party party) {
		partyDao.save(party);
	}

	@Override
	public void saveCompany(Company current) {
		partyDao.saveOrUpdate(current);
	}

	@Override
	public Company currentCompany() {
		return partyDao.findCompany();
	}

	@Override
	public void delParty(int partyId) {
		
		//判断本party下面是否还有子party
		Party p = findPartyById(partyId);
		if(p.getChildren().size() > 0){
			throw new RuntimeException("本Party【ID="+partyId+"】下面还有子Party信息，不允许删除！");
		}
		
		partyDao.del(p);
	}

	@Override
	public Party findPartyById(int partyId) {
		return partyDao.findById( partyId);
	}

	@Override
	public PagerVO findPersons(int parentId,String search) {

		//根据父ID查询Person对象列表
		String hql = "select p.id,p.name,p.sex,p.phone from Person p where p.parent.id = "+parentId;
		
		if(parentId == 0){
			hql = "select p.id,p.name,p.sex,p.phone from Person p where 1=1 ";
		}
		
		if(search != null && !search.trim().equals("")){
			hql = hql + " and (p.name like '%"+search+"%' or p.sex like '%"+search+"%')";
		}
		
		return partyDao.findPaginated(hql);
	}

	@Override
	public void updateParty(Party party) {
		partyDao.update(party);
	}

}
