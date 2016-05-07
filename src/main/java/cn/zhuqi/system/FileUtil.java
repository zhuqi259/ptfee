package cn.zhuqi.system;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.struts2.ServletActionContext;

/**
 * 
 * @author Zhuqi
 *
 */
public class FileUtil {

	public static boolean IsLost(String path) {
		if (path == null) {
			return true;
		}
		String realPath = ServletActionContext.getServletContext().getRealPath(
				path);
		File file = new File(realPath);
		return !file.exists();
	}

	/**
	 * 删除某个文件夹下的所有文件夹和文件
	 * 
	 * @param delpath
	 *            String
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @return boolean
	 */
	public static boolean deleteFolder(String delpath) throws Exception {
		try {

			File file = new File(delpath);
			// 当且仅当此抽象路径名表示的文件存在且 是一个目录时，返回 true
			if (!file.isDirectory()) {
				file.delete();
			} else if (file.isDirectory()) {
				String[] filelist = file.list();
				for (int i = 0; i < filelist.length; i++) {
					File delfile = new File(delpath + "\\" + filelist[i]);
					if (!delfile.isDirectory()) {
						delfile.delete();
						// System.out.println(delfile.getAbsolutePath() +
						// "删除文件成功");
					} else if (delfile.isDirectory()) {
						deleteFolder(delpath + "\\" + filelist[i]);
					}
				}
				// System.out.println(file.getAbsolutePath() + "删除成功");
				file.delete();
			}

		} catch (FileNotFoundException e) {
			System.out.println("deleteFolder() Exception:" + e.getMessage());
		}
		return true;
	}
}
