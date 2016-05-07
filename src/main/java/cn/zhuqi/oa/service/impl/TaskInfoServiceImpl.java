package cn.zhuqi.oa.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.zhuqi.oa.dao.TaskInfoDao;
import cn.zhuqi.oa.model.TaskInfo;
import cn.zhuqi.oa.service.TaskInfoService;

@Service("taskInfoService")
public class TaskInfoServiceImpl implements TaskInfoService {

	@Resource
	private TaskInfoDao taskInfoDao;

	@Override
	public void addTaskInfo(TaskInfo taskInfo) {
		taskInfoDao.save(taskInfo);
	}

	@Override
	public TaskInfo findTaskInfoById(Integer taskInfoId) {
		return taskInfoDao.findById(TaskInfo.class, taskInfoId);
	}

	@Override
	public void delTaskInfo(Integer taskInfoId) {
		taskInfoDao.del(findTaskInfoById(taskInfoId));
	}

	@Override
	public void updateTaskInfo(TaskInfo taskInfo) {
		taskInfoDao.update(taskInfo);
	}

	@Override
	public List<TaskInfo> findAllTaskInfos() {
		return taskInfoDao.findAll(TaskInfo.class);
	}

	@Override
	public TaskInfo getPAUTaskInfo(int projectId, int activityId) {
		return taskInfoDao.getPAUTaskInfo(projectId, activityId);
	}

	@Override
	public List<TaskInfo> findAllProjectTaskInfos(int projectId) {
		return taskInfoDao.findAllProjectTaskInfos(projectId);
	}

}
