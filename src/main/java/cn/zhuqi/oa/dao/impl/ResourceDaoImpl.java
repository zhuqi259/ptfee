package cn.zhuqi.oa.dao.impl;

import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.zhuqi.oa.dao.ResourceDao;
import cn.zhuqi.oa.model.ActionResource;

@Repository("resourceDao")
public class ResourceDaoImpl extends BaseDaoImpl implements
		ResourceDao {

	@Override
	public void delAllActionResources() {
		//不能像这样做批量删除，因为t_resource_opers表依赖于本类对应的表
//		getSession().createQuery("delete ActionResource")
//			.executeUpdate();
		
		Iterator it= getSession().createQuery("from ActionResource").iterate();
		while(it.hasNext()){
			//把ActionResource对象及其相关的操作对象一起删除！
			getSession().delete(it.next());
		}
		getSession().flush();
	}

	@Override
	public ActionResource findActionResourceBySn(String sn) {
		String hql = "select ar from ActionResource ar where ar.sn = ?";
		return (ActionResource)getSession().createQuery(hql)
			.setParameter(0, sn)
			.uniqueResult();
	}

	@Override
	public List<ActionResource> findAll() {
		String hql = "select ar from ActionResource ar order by ar.orderNumber";
		return getSession().createQuery(hql).list();
	}

	@Override
	public List<ActionResource> findAllTopActionResource() {
		String hql = "select ar from ActionResource ar where ar.parent is null order by ar.orderNumber";
		return getSession().createQuery(hql).list();
	}

	@Override
	public void update(ActionResource ar) {
		ActionResource oldAr = (ActionResource)getSession().load(ActionResource.class, ar.getId());
		ar.setOpers(oldAr.getOpers());
		
		//！！！！注意，这里需使用merge方法！！！！
		getSession().merge(ar);
	}

	@Override
	public ActionResource findByClassName(String className) {
		String hql = "select ar from ActionResource ar where ar.className like ?";
		return (ActionResource)getSession().createQuery(hql)
			.setParameter(0, "%"+className+"%")
			.uniqueResult();
	}

}
