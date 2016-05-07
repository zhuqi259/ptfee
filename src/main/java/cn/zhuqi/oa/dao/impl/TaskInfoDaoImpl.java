package cn.zhuqi.oa.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.zhuqi.oa.dao.TaskInfoDao;
import cn.zhuqi.oa.model.TaskInfo;

@Repository("taskInfoDao")
public class TaskInfoDaoImpl extends BaseDaoImpl implements TaskInfoDao {

	@Override
	public TaskInfo getPAUTaskInfo(int projectId, int activityId) {
		String hql = "select ti from TaskInfo ti where ti.project.id = ? and ti.activity.id = ? order by ti.time desc";
		List<TaskInfo> taskInfos = getSession().createQuery(hql)
				.setParameter(0, projectId).setParameter(1, activityId).list();
		if (taskInfos != null && taskInfos.size() > 0) {
			return taskInfos.get(0);
		}
		return null;
	}

	@Override
	public List<TaskInfo> findAllProjectTaskInfos(int projectId) {
		String hql = "select ti from TaskInfo ti where ti.project.id = ? ";
		return getSession().createQuery(hql).setParameter(0, projectId).list();

	}

}
