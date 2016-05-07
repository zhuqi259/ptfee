package cn.zhuqi.oa.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.MethodMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.stereotype.Service;

import cn.zhuqi.oa.annotations.Oper;
import cn.zhuqi.oa.annotations.Res;
import cn.zhuqi.oa.dao.ResourceDao;
import cn.zhuqi.oa.model.ActionMethodOper;
import cn.zhuqi.oa.model.ActionResource;
import cn.zhuqi.oa.service.ResourceService;

@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {

	private Logger logger = Logger.getLogger(ResourceServiceImpl.class);

	@Resource
	private ResourceDao resourceDao;

	@Override
	public void rebuildActionResources() {
		try {

			// 扫描某个包（package）包括其子包中的Action类
			// 按照本路径模式规定，所有的Action类均需要命名为XXXAction
			String pathPattern = "cn/zhuqi/oa/web/**/*Action.class";

			// Spring中的资源路径解释器
			ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

			// 得到在包下面的所有的类（封装成了Resource类型）
			org.springframework.core.io.Resource[] res = resolver
					.getResources(pathPattern);

			if (res != null) {

				// 为了得到MetadataReader，先创建factory
				MetadataReaderFactory metaFactory = new CachingMetadataReaderFactory();

				// 逐个扫描这些类
				for (org.springframework.core.io.Resource r : res) {

					// 读取指定的类的信息
					MetadataReader metadataReader = metaFactory
							.getMetadataReader(r);

					// 提取类的信息，并保存到ActionResource对象
					saveActionResource(metadataReader, metaFactory, resolver);
				}
			}

			// 建立资源之间的父子关系
			List<ActionResource> allResources = resourceDao.findAll();
			for (ActionResource ar : allResources) {
				// 如果定义了parentSn，则根据这个parentSn查找父亲，并建立关联
				String parentSn = ar.getParentSn();
				if (parentSn != null && !parentSn.trim().equals("")) {
					ActionResource parent = resourceDao
							.findActionResourceBySn(parentSn);
					if (parent != null) {
						ar.setParent(parent);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("在重新构建操作资源时出现异常！", e);
		}
	}

	private void saveActionResource(MetadataReader metadataReader,
			MetadataReaderFactory metaFactory, ResourcePatternResolver resolver)
			throws IOException {

		// 得到类的元数据描述信息
		ClassMetadata classMetadata = metadataReader.getClassMetadata();

		// 得到类的注解元数据
		AnnotationMetadata annotationMetadata = metadataReader
				.getAnnotationMetadata();

		// 判断某个类是否定义了@Res注解
		if (annotationMetadata.hasAnnotation(Res.class.getName())) {
			logger.debug("扫描到类：" + classMetadata.getClassName() + " 包含有@Res注解！");

			// ///////////////////// 提取资源信息 //////////////////////////////
			// 取出@Res注解的属性
			Map resAttrs = annotationMetadata.getAnnotationAttributes(Res.class
					.getName());

			// 获得@Res注解中的sn属性的值
			String resSn = (String) resAttrs.get("sn");

			// 获得@Res注解中的name属性的值
			String resName = (String) resAttrs.get("name");

			// 获得@Res注解中的orderNumber属性的值
			int orderNumber = (Integer) resAttrs.get("orderNumber");

			// 获得@Res注解中的parentSn属性的值
			String parentSn = (String) resAttrs.get("parentSn");

			// 获得@Res注解所在的类名
			String className = classMetadata.getClassName();

			// 首先根据sn，查询数据库，如果已经存在，则不再创建，而是使用原有的ActionResource对象
			ActionResource ar = resourceDao.findActionResourceBySn(resSn);
			if (ar == null) {
				// 如果对应的资源尚不存在，则创建ActionResource对象
				ar = new ActionResource();
			}

			ar.addClassName(className);
			ar.setName(resName);
			ar.setSn(resSn);
			ar.setOrderNumber(orderNumber);
			ar.setParentSn(parentSn);

			logger.debug("扫描到资源【" + resSn + "(" + resName + ")】：" + className);

			// 搜索本类型下面定义了@Oper的方法及其父类型下面定义了@Oper的方法
			searchOperAnnotations(ar, metadataReader, metaFactory, resolver);

			resourceDao.save(ar);
		}

	}

	/**
	 * 搜索包含有@Oper注解的方法，除了搜索本类型，有父类，还需继续搜索父类中包含@Oper注解的方法
	 */
	private void searchOperAnnotations(ActionResource ar,
			MetadataReader metadataReader, MetadataReaderFactory metaFactory,
			ResourcePatternResolver resolver) throws IOException {
		// 得到类的注解元数据
		AnnotationMetadata annotationMetadata = metadataReader
				.getAnnotationMetadata();
		// 扫描这个类下面的方法，寻找包含@Oper注解的方法
		Set<MethodMetadata> methodMetas = annotationMetadata
				.getAnnotatedMethods(Oper.class.getName());
		for (MethodMetadata mmd : methodMetas) {
			Map<String, Object> operAttrs = mmd
					.getAnnotationAttributes(Oper.class.getName());
			String methodName = mmd.getMethodName();
			String operName = (String) operAttrs.get("name");
			if (operName == null || operName.equals("")) { // 未定义操作名
				operName = getDefaultOperName(methodName);
			}
			String operSn = (String) operAttrs.get("sn");
			if (operSn == null || operSn.equals("")) {
				operSn = getDefaultOperSn(methodName);
			}
			int operIndex = (Integer) operAttrs.get("index");
			if (operIndex == -1) {
				operIndex = getDefaultOperIndex(methodName);
			}

			ar.addActionMethodOper(methodName, operName, operSn, operIndex);
			logger.debug("扫描到操作【" + operSn + "(" + operName + ")】[" + operIndex
					+ "]：" + methodName);
		}

		// 如果有父类，而且不是java.lang.Object，则继续搜索这个父类中是否还包含有@Oper注解的方法
		if (metadataReader.getClassMetadata().hasSuperClass()
				&& !metadataReader.getClassMetadata().getSuperClassName()
						.equals(Object.class.getName())) {
			// 得到父类的名称，比如：cn.com.leadfar.oa.web.actions.PartyAction
			String superClassName = metadataReader.getClassMetadata()
					.getSuperClassName();
			// 构造父类的资源路径，比如：cn/com/leadfar/oa/web/actions/PartyAction.class
			String superClassPath = superClassName.replace('.', '/') + ".class";
			org.springframework.core.io.Resource superClassResource = resolver
					.getResource(superClassPath);

			// 递归搜索父类包含的操作
			searchOperAnnotations(ar,
					metaFactory.getMetadataReader(superClassResource),
					metaFactory, resolver);
		}
	}

	/**
	 * 根据方法名，得到缺省的操作名
	 * 
	 * @param methodName
	 * @return
	 */
	private String getDefaultOperName(String methodName) {
		if (methodName.startsWith("add")) {
			return "添加";
		} else if (methodName.startsWith("update")) {
			return "更新";
		} else if (methodName.startsWith("del")) {
			return "删除";
		} else {
			return "查询";
		}
	}

	private String getDefaultOperSn(String methodName) {
		if (methodName.startsWith("add")) {
			return "CREATE";
		} else if (methodName.startsWith("update")) {
			return "UPDATE";
		} else if (methodName.startsWith("del")) {
			return "DELETE";
		} else {
			return "READ";
		}
	}

	private int getDefaultOperIndex(String methodName) {
		if (methodName.startsWith("add")) {
			return 0;
		} else if (methodName.startsWith("update")) {
			return 1;
		} else if (methodName.startsWith("del")) {
			return 2;
		} else {
			return 3;
		}
	}

	@Override
	public void addActionResource(ActionResource ar) {
		resourceDao.save(ar);
	}

	@Override
	public void delActionResource(int actionResourceId) {
		resourceDao.del(findActionResourceById(actionResourceId));
	}

	@Override
	public ActionResource findActionResourceById(int actionResourceId) {
		return resourceDao.findById(ActionResource.class, actionResourceId);
	}

	@Override
	public ActionResource findActionResourceByClassName(String className) {
		return resourceDao.findByClassName(className);
	}

	@Override
	public List<ActionResource> findAllActionResources() {
		return resourceDao.findAll();
	}

	@Override
	public List<ActionResource> findAllTopActionResources() {
		return resourceDao.findAllTopActionResource();
	}

	@Override
	public void updateActionResource(ActionResource ar) {
		resourceDao.update(ar);
	}

	@Override
	public void addActionResourceOper(int actionResourceId,
			ActionMethodOper oper) {
		ActionResource ar = findActionResourceById(actionResourceId);
		ar.addActionMethodOper(oper.getMethodName(), oper.getOperName(),
				oper.getOperSn(), oper.getOperIndex());
	}

	@Override
	public void delActionResourceOper(int actionResourceId, String operSn) {
		ActionResource ar = findActionResourceById(actionResourceId);
		ar.getOpers().remove(operSn);
	}

}
