package cn.zhuqi.oa.web.actions;

import cn.zhuqi.oa.model.Pfile;
import cn.zhuqi.oa.model.Zfile;
import cn.zhuqi.oa.service.PfileService;
import cn.zhuqi.oa.service.ZfileService;
import cn.zhuqi.system.EncoderUtil;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

@SuppressWarnings("serial")
@Controller("fileDownloadFullAction")
@Scope("prototype")
public class FileDownloadFullAction extends ActionSupport {

	private static final String EXCELNAME = "配套费工程财务收、付款情况表.xls";

	@Resource
	private PfileService pfileService;
	@Resource
	private ZfileService zfileService;

	private int id;
	private String downloadPath;
	private String contentType;
	private String fileName;
	private int type;

	private InputStream in;

	public InputStream getIn() {
		return in;
	}

	public void setIn(InputStream in) {
		this.in = in;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDownloadPath() {
		return downloadPath;
	}

	public void setDownloadPath(String downloadPath) {
		this.downloadPath = downloadPath;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getFileName() throws UnsupportedEncodingException {
		fileName = EncoderUtil.encodingFileName(fileName);
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public InputStream getInputStream() {
		// return ServletActionContext.getServletContext().getResourceAsStream(
		// downloadPath);
		return in;
	}

	public String execute() {
		try {
			// HttpServletRequest request = ServletActionContext.getRequest();
			// String id = request.getParameter("id");
			// fileId = Integer.parseInt(id);

			// String _type = request.getParameter("type");
			// type = Integer.parseInt(_type);

			if (type == 1) {
				// 查找该文件
				Pfile pfile = pfileService.findPfileById(id);
				// 得到路径
				downloadPath = pfile.getPath();
				System.out.println(downloadPath);
				int position = downloadPath.lastIndexOf("/");
				if (position > 0) {
					fileName = downloadPath.substring(position + 1);
				} else {
					fileName = downloadPath;
				}

				int extPos = fileName.lastIndexOf(".");
				String contentTypeKey;
				if (extPos > 0) {
					contentTypeKey = "struts.contentType"
							+ fileName.substring(extPos);
				} else {
					contentTypeKey = "struts.contentType.txt";
				}
				contentType = getText(contentTypeKey);
				// 重新定义文件名称
				fileName = pfile.getZfile().getName()
						+ fileName.substring(extPos);

			} else if (type == 2) {
				Zfile zfile = zfileService.findZfileById(id);
				downloadPath = zfile.getPath();
				System.out.println(downloadPath);
				int position = downloadPath.lastIndexOf("/");
				if (position > 0) {
					fileName = downloadPath.substring(position + 1);
				} else {
					fileName = downloadPath;
				}

				int extPos = fileName.lastIndexOf(".");
				String contentTypeKey;
				if (extPos > 0) {
					contentTypeKey = "struts.contentType"
							+ fileName.substring(extPos);
				} else {
					contentTypeKey = "struts.contentType.txt";
				}
				contentType = getText(contentTypeKey);
				// 重新定义文件名称
				fileName = "【模板】" + zfile.getName()
						+ fileName.substring(extPos);
			} else if (type == 3) {

				System.out.println(downloadPath);
				String contentTypeKey = "struts.contentType.xls";
				contentType = getText(contentTypeKey);
				fileName = EXCELNAME;
			}
			System.out.println("fileName----------" + fileName);
			System.out.println("contentType---------------" + contentType);

			in = ServletActionContext.getServletContext().getResourceAsStream(
					downloadPath);
			if (in == null) {
				throw new RuntimeException("文件下载失败，请联系管理员");
			}

		} catch (Exception e) {
			throw new RuntimeException("文件下载失败，请联系管理员");
		}

		return SUCCESS;
	}

}
