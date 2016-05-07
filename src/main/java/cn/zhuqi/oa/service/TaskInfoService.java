package cn.zhuqi.oa.service;

import java.util.List;

import cn.zhuqi.oa.model.TaskInfo;

public interface TaskInfoService {

	public void addTaskInfo(TaskInfo taskInfo);

	public TaskInfo findTaskInfoById(Integer taskInfoId);

	public void delTaskInfo(Integer taskInfoId);

	public void updateTaskInfo(TaskInfo taskInfo);

	public List<TaskInfo> findAllTaskInfos();

	public List<TaskInfo> findAllProjectTaskInfos(int projectId);

	public TaskInfo getPAUTaskInfo(int projectId, int activityId);

}
