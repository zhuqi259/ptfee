package cn.zhuqi.oa.dao;

import java.util.List;

import cn.zhuqi.oa.model.TaskInfo;

public interface TaskInfoDao extends BaseDao {

	public TaskInfo getPAUTaskInfo(int projectId, int activityId);

	public List<TaskInfo> findAllProjectTaskInfos(int projectId);

}
