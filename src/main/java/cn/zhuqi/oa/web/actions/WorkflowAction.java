package cn.zhuqi.oa.web.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.zhuqi.oa.model.Workflow;
import cn.zhuqi.oa.service.WorkflowService;
import cn.zhuqi.oa.vo.PagerVO;
import cn.zhuqi.oa.web.JSONUtils;

import com.opensymphony.xwork2.ModelDriven;

@Controller("workflowAction")
@Scope("prototype")
public class WorkflowAction extends BaseAction implements ModelDriven {

	private Workflow workflow;

	@Resource
	private WorkflowService workflowService;

	@Override
	public Object getModel() {
		if (workflow == null) {
			workflow = new Workflow();
		}
		return workflow;
	}

	private File uploadFile;
	private String uploadFileFileName;
	private String uploadFileContentType;
	private String targetFileName;
	private String targetFileDir;

	public File getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}

	public String getUploadFileFileName() {
		return uploadFileFileName;
	}

	public void setUploadFileFileName(String uploadFileFileName) {
		this.uploadFileFileName = uploadFileFileName;
	}

	public String getUploadFileContentType() {
		return uploadFileContentType;
	}

	public void setUploadFileContentType(String uploadFileContentType) {
		this.uploadFileContentType = uploadFileContentType;
	}

	public String getTargetFileName() {
		return targetFileName;
	}

	public void setTargetFileName(String targetFileName) {
		this.targetFileName = targetFileName;
	}

	public String getTargetFileDir() {
		return targetFileDir;
	}

	public void setTargetFileDir(String targetFileDir) {
		this.targetFileDir = targetFileDir;
	}

	public String execute() {
		return "index";
	}

	public void list() {
		// TODO 处理一下by zhuqi ==>success
		PagerVO pv = workflowService.getAllWorkflow();
		Map map = new HashMap();
		map.put("iTotalRecords", pv.getTotal());
		map.put("iTotalDisplayRecords", pv.getTotal());
		map.put("aaData", pv.getDatas());
		JSONUtils.toJSON(map);
	}

	public String addInput() {
		return "add_input";
	}

	/**
	 * 新增流程定义
	 * 
	 * @return add_success
	 */
	public String add() throws IOException {
		// 首先实现文件上传 -depressed
		if (uploadFile != null) {
			/*
			 * String serverRealPath = ServletActionContext.getServletContext()
			 * .getRealPath(targetFileDir); File dir = new File(serverRealPath);
			 * if (!dir.exists()) { dir.mkdir(); } targetFileName =
			 * getNewFileName(uploadFileContentType); File targetFile = new
			 * File(serverRealPath, targetFileName);
			 * FileUtils.copyFile(uploadFile, targetFile);
			 */
			// 直接将上传文件转化成zip流
			FileInputStream in = new FileInputStream(uploadFile);
			ZipInputStream zis = new ZipInputStream(in);
			// 然后部署该流程
			workflowService.deployProcess(zis);
		} else {
			throw new RuntimeException("请选择上传文件");
		}

		return "add_success";
	}

	/**
	 * 删除流程定义
	 */
	public void del() {
		workflowService.delWorkflow(workflow.getId());
	}

	public String view() {
		return "view";
	}

	/**
	 * 使用的是get方法，用于显示工作流程图片
	 * 
	 * @throws IOException
	 */
	public void pic() throws IOException {
		workflow = workflowService.findWorkflowById(workflow.getId());
		byte[] processImage = workflow.getProcessImage();
		ServletOutputStream sos = ServletActionContext.getResponse()
				.getOutputStream();
		sos.write(processImage);
		sos.close();
	}

}
