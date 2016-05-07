package cn.zhuqi.oa.service;

import java.util.List;

import cn.zhuqi.oa.model.FieldMap;
import cn.zhuqi.oa.model.ZSequence;

public interface ZSequenceService {

	public void addZSequence(ZSequence zSequence);

	public ZSequence findZSequenceById(int zSequenceId);

	public void delZSequence(int zSequenceId);

	public void updateZSequence(ZSequence zSequence);

	public List<FieldMap> getSequenceFields();
	
	/**
	 * 获得管理员定义的属性列表
	 * 
	 * @return
	 */
	public List<FieldMap> getCustomSequenceFields();
	
	/**
	 * 获得管理员定义的顺序ZSequence实体列表
	 * 
	 * @return
	 */
	public List<ZSequence> getCustomSequences();
	
	
	public List<ZSequence> getAllSequences();
}
