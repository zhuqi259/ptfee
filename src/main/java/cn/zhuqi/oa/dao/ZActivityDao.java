package cn.zhuqi.oa.dao;

import java.util.List;

import cn.zhuqi.oa.model.ZActivity;
import cn.zhuqi.oa.model.ZPayment;
import cn.zhuqi.oa.model.Zfile;
import cn.zhuqi.oa.model.Zfund;
import cn.zhuqi.oa.vo.PagerVO;

public interface ZActivityDao extends BaseDao {

	/**
	 * 通过配置初始化工作流中的所有节点
	 */
	public void initActivity();

	/**
	 * 查找所有的节点
	 * 
	 * @param activityName
	 * @return ID , name
	 */
	public List findAllActivity(String activityName);

	public PagerVO findAllZActivity(String activityName);

	public PagerVO findAllFiles(int activityId);

	public List<Zfile> findAllFileList(int activityId);

	public ZActivity findActivityByName(String activityName);

	public PagerVO findAllPayments(int activityId);

	public List<ZPayment> findAllPaymentList(int activityId);

	public PagerVO findAllFunds(Integer activityId);

	public List<Zfund> findAllFundList(Integer activityId);

	public List<ZActivity> findAllActivityByOrder();

}
