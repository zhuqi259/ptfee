package cn.zhuqi.oa.service;

import java.util.List;

import cn.zhuqi.oa.model.FieldMap;

public interface FieldMapService {

	public void addFieldMap(FieldMap fieldMap);

	public FieldMap findFieldMapById(int fieldMapId);

	public void delFieldMap(int fieldMapId);

	public void updateFieldMap(FieldMap fieldMap);

	public List<FieldMap> findAllFieldMaps();
}
