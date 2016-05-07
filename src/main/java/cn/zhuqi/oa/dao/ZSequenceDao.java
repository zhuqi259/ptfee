package cn.zhuqi.oa.dao;

import java.util.List;

import cn.zhuqi.oa.model.FieldMap;
import cn.zhuqi.oa.model.ZSequence;

public interface ZSequenceDao extends BaseDao {

	public List<ZSequence> getAllSequences();

	public List<FieldMap> getSequenceFields();

	public List<ZSequence> findAllZSequencesByFieldMap(int fieldMapId);

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

}
