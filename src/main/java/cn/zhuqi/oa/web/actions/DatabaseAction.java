package cn.zhuqi.oa.web.actions;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.zhuqi.oa.model.Database;
import cn.zhuqi.oa.model.Zfile;
import cn.zhuqi.oa.service.DatabaseService;
import cn.zhuqi.oa.service.ZfileService;
import cn.zhuqi.system.FileNameUtil;
import cn.zhuqi.system.FileUtil;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

@Controller("databaseAction")
@Scope("prototype")
public class DatabaseAction extends BaseAction implements ModelDriven {

	private Database database;

	@Resource
	private DatabaseService databaseService;

	@Resource
	private ZfileService zfileService;

	@Override
	public Object getModel() {
		if (database == null) {
			database = new Database();
		}
		return database;
	}

	// 删除时使用
	private int fid;

	public int getFid() {
		return fid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}

	private File[] uploadFiles;
	private String[] uploadFilesFileName;
	private String[] uploadFilesContentType;
	private String[] targetFileNames;
	private String targetFileDir;
	private int filesCount;
	private String[] fileUsed;
	private String[] zname;

	public File[] getUploadFiles() {
		return uploadFiles;
	}

	public void setUploadFiles(File[] uploadFiles) {
		this.uploadFiles = uploadFiles;
	}

	public String[] getUploadFilesFileName() {
		return uploadFilesFileName;
	}

	public void setUploadFilesFileName(String[] uploadFilesFileName) {
		this.uploadFilesFileName = uploadFilesFileName;
	}

	public String[] getUploadFilesContentType() {
		return uploadFilesContentType;
	}

	public void setUploadFilesContentType(String[] uploadFilesContentType) {
		this.uploadFilesContentType = uploadFilesContentType;
	}

	public String[] getTargetFileNames() {
		return targetFileNames;
	}

	public void setTargetFileNames(String[] targetFileNames) {
		this.targetFileNames = targetFileNames;
	}

	public String getTargetFileDir() {
		return targetFileDir;
	}

	public void setTargetFileDir(String targetFileDir) {
		this.targetFileDir = targetFileDir;
	}

	public int getFilesCount() {
		return filesCount;
	}

	public void setFilesCount(int filesCount) {
		this.filesCount = filesCount;
	}

	public String[] getFileUsed() {
		return fileUsed;
	}

	public void setFileUsed(String[] fileUsed) {
		this.fileUsed = fileUsed;
	}

	public String[] getZname() {
		return zname;
	}

	public void setZname(String[] zname) {
		this.zname = zname;
	}

	public String execute() {

		List<Database> databases = databaseService.getAllDatabaseList();
		ActionContext.getContext().put("databases", databases);

		return "index";
	}

	public String list() {
		database = databaseService.findDatabaseById(database.getId());
		return "list";
	}

	public String list2() {
		database = databaseService.findDatabaseById(database.getId());
		return "list2";
	}

	public List<Zfile> getAllZfiles() {
		// System.out.println(database.getId());
		// System.out.println(databaseService.findAllFileList(database.getId())
		// .size());
		return databaseService.findAllFileList(database.getId());
	}

	public String update() throws IOException {
		// 上传修改path
		upload();
		// 修改zfile.name
		List<Zfile> zfiles = getAllZfiles();
		int size = zfiles.size();
		for (int i = 0; i < size; i++) {
			Zfile zfile = zfiles.get(i);
			zfile.setName(zname[i]);
			zfileService.updateZfile(zfile);
		}
		database = databaseService.findDatabaseById(database.getId());
		return "update_success";
	}

	public String addInput() {
		database = databaseService.findDatabaseById(database.getId());
		return "add_input";
	}

	public String add() throws IOException {
		upload2();
		return "list";
	}

	public String del() {
		Zfile zfile = zfileService.findZfileById(fid);
		// System.out.println(zfile.getPath());
		// 删除文件
		String realPath = ServletActionContext.getServletContext().getRealPath(
				zfile.getPath());
		File file = new File(realPath);
		if (file.isFile() && file.exists()) {
			file.delete();
		}
		zfileService.delZfile(fid);
		database = databaseService.findDatabaseById(database.getId());
		return "list";
	}

	private void upload2() throws IOException {
		if (uploadFiles != null) {
			int databaseId = database.getId();
			database = databaseService.findDatabaseById(databaseId);
			// String name = database.getName();
			String prefix = targetFileDir;
			String serverRealPath = ServletActionContext.getServletContext()
					.getRealPath(prefix);
			File dir = new File(serverRealPath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			filesCount = uploadFiles.length;
			assert filesCount == zname.length;
			// System.out.println(filesCount == zname.length);
			targetFileNames = new String[filesCount];
			for (int i = 0; i < filesCount; i++) {
				targetFileNames[i] = FileNameUtil.getNewFileName(
						uploadFilesFileName[i], i);
				// 实际上传
				File targetFile = new File(serverRealPath, targetFileNames[i]);
				FileUtils.copyFile(uploadFiles[i], targetFile);

				Zfile zfile = new Zfile();
				zfile.setName(zname[i]);
				zfile.setPath(prefix + targetFileNames[i]);
				zfile.setBase(database);
				zfileService.addZfile(zfile);
			}

		}
	}

	private void upload() throws IOException {
		// 实现文件上传 TODO 2013/7/15 实现文件上传 by zhuqi
		if (uploadFiles != null) {
			int databaseId = database.getId();
			database = databaseService.findDatabaseById(databaseId);
			// String name = database.getName();
			String prefix = targetFileDir;
			String serverRealPath = ServletActionContext.getServletContext()
					.getRealPath(prefix);
			File dir = new File(serverRealPath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			filesCount = uploadFiles.length;
			targetFileNames = new String[filesCount];
			List<Zfile> zfiles = getAllZfiles();
			int i = 0;
			for (int j = 0; j < fileUsed.length; j++) {
				// System.out.println("fileUsed-->" + fileUsed[j]);
				if (fileUsed[j].equals("1")) {
					targetFileNames[i] = FileNameUtil.getNewFileName(
							uploadFilesFileName[i], i);
					// 实际上传
					File targetFile = new File(serverRealPath,
							targetFileNames[i]);
					FileUtils.copyFile(uploadFiles[i], targetFile);
					Zfile zfile = zfiles.get(j);
					zfile.setPath(prefix + "/" + targetFileNames[i]);
					zfileService.updateZfile(zfile);
					i++;
				}
			}
			if (i != filesCount) {
				System.out
						.println("------->>>>>>>>>>>>>>>>>>>>>>>>>>>有文件上传失败!!");
			}
			// addActionMessage("共上传了" + filesCount + "个文件");

		} else {
			// addActionError("请选择上传文件");
		}
	}

	public boolean isNull() {
		List<Zfile> zfiles = getAllZfiles();
		for (Iterator<Zfile> it = zfiles.iterator(); it.hasNext();) {
			Zfile zfile = it.next();
			if (zfile.getPath() == null) {
				return true;
			}
		}
		return false;
	}

	public boolean IsLost(String path) {
		return FileUtil.IsLost(path);
	}

	// 清理空间使用
	private String home;

	private String excel;

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public String getExcel() {
		return excel;
	}

	public void setExcel(String excel) {
		this.excel = excel;
	}

	public String clean() throws Exception {
		String serverRealPath = ServletActionContext.getServletContext()
				.getRealPath(home);
		File desk = new File(serverRealPath);
		if (desk.exists() && desk.isDirectory()) {
			File[] dirs = desk.listFiles();
			if (dirs != null && dirs.length > 0) {
				for (File dir : dirs) {
					if (dir.exists() && dir.isDirectory()) {
						File[] files = dir.listFiles();
						if (files == null || files.length == 0) {
							dir.delete();
						}
					}
				}
			}
		}
		String excelRealPath = ServletActionContext.getServletContext()
				.getRealPath(excel);
		// 删除excel文件夹
		FileUtil.deleteFolder(excelRealPath);
		return "clean_success";
	}

}
