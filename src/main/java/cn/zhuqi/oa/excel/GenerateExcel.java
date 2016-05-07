package cn.zhuqi.oa.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cn.zhuqi.oa.model.FieldMap;
import cn.zhuqi.oa.model.Fund;
import cn.zhuqi.oa.model.Payment;
import cn.zhuqi.oa.model.Project;
import cn.zhuqi.oa.model.ZPayment;
import cn.zhuqi.oa.model.ZSequence;
import cn.zhuqi.oa.model.Zfile;
import cn.zhuqi.oa.service.FundService;
import cn.zhuqi.oa.service.PaymentService;
import cn.zhuqi.oa.service.ZPaymentService;
import cn.zhuqi.oa.service.ZSequenceService;
import cn.zhuqi.oa.service.ZfileService;
import cn.zhuqi.system.ClassUtil;
import cn.zhuqi.system.StringUtil;

/**
 * 生成Excel示例，2003和2007
 * 
 * @author Zhuqi
 * 
 */
public class GenerateExcel {

	// private Project project;

	private static final float RowHeight = 51.75f;
	private static final int ColumnWidth = 15 * 256;

	/**
	 * 创建2003文件的方法
	 * 
	 * @param projects
	 * @param zSequenceService
	 * @param filePath
	 * @param zfileService
	 */
	public static void generateExcel2003_6(List<Project> projects,
			ZSequenceService zSequenceService, PaymentService paymentService,
			String serverRealPath, String filePath) {
		// 先创建工作簿对象
		HSSFWorkbook workbook2003 = new HSSFWorkbook();
		// 创建工作表对象并命名
		HSSFSheet sheet = workbook2003.createSheet("配套费工程财务收、付款情况表");
		// 创建格式
		HSSFCellStyle setBorder = workbook2003.createCellStyle();
		setBorder.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 居中
		setBorder.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		setBorder.setWrapText(true);// 设置自动换行

		// 遍历集合对象创建行和单元格
		// for (int i = 0; i < studentList.size(); i++) {
		// 取出Student对象
		// Student student = studentList.get(i);

		// 创建行
		List<FieldMap> fieldMaps = zSequenceService.getCustomSequenceFields();
		HSSFRow row0 = sheet.createRow(0);
		row0.setHeightInPoints(RowHeight);
		for (int i = 0; i < fieldMaps.size(); i++) {
			FieldMap fieldMap = fieldMaps.get(i);
			HSSFCell nameCell = row0.createCell(i);
			if (fieldMap.getDisplayName() == null) {
				nameCell.setCellValue(fieldMap.getName());
			} else {
				nameCell.setCellValue(fieldMap.getDisplayName());
			}
			sheet.setColumnWidth(i, ColumnWidth);
		}
		/*
		 * 
		 * HSSFCell nameCell0 = row0.createCell(0);
		 * nameCell0.setCellValue("工程名称"); HSSFCell areaCell0 =
		 * row0.createCell(1); areaCell0.setCellValue("面积"); HSSFCell
		 * locationCell0 = row0.createCell(2); locationCell0.setCellValue("地址");
		 */

		// 开始创建单元格并赋值
		for (int ii = 0; ii < projects.size(); ii++) {
			Project project = projects.get(ii);
			System.out.println(project.getFid());
			System.out.println(ClassUtil.getFieldValue(project, "fid"));
			HSSFRow row = sheet.createRow(ii + 1);
			for (int i = 0; i < fieldMaps.size(); i++) {
				FieldMap fieldMap = fieldMaps.get(i);
				HSSFCell nameCell = row.createCell(i);
				if ("序号".equals(fieldMap.getDisplayName())) {
					nameCell.setCellValue(String.valueOf(ii + 1));
					continue;
				}
				Object value = new Object();
				try {
					int zPaymentId = Integer.parseInt(fieldMap.getName());
					if (zPaymentId > 0) {
						Payment payment = paymentService.getPayment(
								project.getId(), zPaymentId);
						if (payment != null) {
							value = payment.getMoney();
						} else {
							value = null;
						}
					} else {
						value = null;
					}
				} catch (NumberFormatException e1) {
					System.out.println(e1.getMessage());
					value = ClassUtil
							.getFieldValue(project, fieldMap.getName());
					System.out.println(value);
				}
				System.out.println("value-----------" + value);
				if (value == null) {
					nameCell.setCellValue("");
				} else {
					nameCell.setCellValue(value.toString());
				}
			}
		}
		/*
		 * HSSFCell nameCell = row.createCell(0);
		 * nameCell.setCellValue(project.getProname()); HSSFCell areaCell =
		 * row.createCell(1); areaCell.setCellValue(project.getArea()); HSSFCell
		 * locationCell = row.createCell(2);
		 * locationCell.setCellValue(project.getLocation());
		 */

		// 生成文件
		File file = new File(serverRealPath, filePath);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			workbook2003.write(fos);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 创建2007文件的方法
	 * 
	 * @param filePath
	 */
	public static void generateExcel2007(Project project, String filePath) {
		// 先创建工作簿对象
		XSSFWorkbook workbook2003 = new XSSFWorkbook();
		// 创建工作表对象并命名
		XSSFSheet sheet = workbook2003.createSheet("配套费工程财务收、付款情况表");

		// 遍历集合对象创建行和单元格
		// for (int i = 0; i < studentList.size(); i++) {
		// 取出Student对象
		// Student student = studentList.get(i);

		// 创建行
		XSSFRow row = sheet.createRow(0);
		// 开始创建单元格并赋值
		XSSFCell nameCell = row.createCell(0);
		nameCell.setCellValue(project.getProname());
		XSSFCell areaCell = row.createCell(1);
		areaCell.setCellValue(project.getArea());
		XSSFCell locationCell = row.createCell(2);
		locationCell.setCellValue(project.getLocation());

		// 生成文件
		File file = new File(filePath);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			workbook2003.write(fos);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	// //////////////////////////
	public static void generateExcel2003_2(Project project, String filePath) {
		// 先创建工作簿对象
		HSSFWorkbook workbook2003 = new HSSFWorkbook();
		// 创建工作表对象并命名
		HSSFSheet sheet = workbook2003.createSheet("配套费工程财务收、付款情况表");

		// 创建格式
		HSSFCellStyle setBorder = workbook2003.createCellStyle();
		setBorder.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 居中
		setBorder.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		setBorder.setWrapText(true);// 设置自动换行

		// 遍历集合对象创建行和单元格
		// for (int i = 0; i < studentList.size(); i++) {
		// 取出Student对象
		// Student student = studentList.get(i);

		// 创建行
		HSSFRow row0 = sheet.createRow(0);
		row0.setHeightInPoints(51.75f);
		sheet.setColumnWidth(0, 6666);
		sheet.setColumnWidth(1, 6666);
		sheet.setColumnWidth(2, 6666);
		HSSFCell nameCell0 = row0.createCell(0);
		nameCell0.setCellValue("工程名称");
		HSSFCell areaCell0 = row0.createCell(1);
		areaCell0.setCellValue("面积");
		HSSFCell locationCell0 = row0.createCell(2);
		locationCell0.setCellValue("地址");

		HSSFRow row = sheet.createRow(1);
		// 开始创建单元格并赋值
		HSSFCell nameCell = row.createCell(0);
		nameCell.setCellValue(project.getProname());
		HSSFCell areaCell = row.createCell(1);
		areaCell.setCellValue(project.getArea());
		HSSFCell locationCell = row.createCell(2);
		locationCell.setCellValue(project.getLocation());

		// 生成文件
		File file = new File(filePath);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			workbook2003.write(fos);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 创建2003文件的方法
	 * 
	 * @param project
	 * @param zSequenceService
	 * @param filePath
	 * @param zfileService
	 */
	public static void generateExcel2003_3(Project project,
			ZSequenceService zSequenceService, ZfileService zfileService,
			String filePath) {
		// 先创建工作簿对象
		HSSFWorkbook workbook2003 = new HSSFWorkbook();
		// 创建工作表对象并命名
		HSSFSheet sheet = workbook2003.createSheet("配套费工程财务收、付款情况表");
		// 创建格式
		HSSFCellStyle setBorder = workbook2003.createCellStyle();
		setBorder.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 居中
		setBorder.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		setBorder.setWrapText(true);// 设置自动换行

		// 遍历集合对象创建行和单元格
		// for (int i = 0; i < studentList.size(); i++) {
		// 取出Student对象
		// Student student = studentList.get(i);

		// 创建行

		List<FieldMap> fieldMaps = zSequenceService.getSequenceFields();
		HSSFRow row0 = sheet.createRow(0);
		row0.setHeightInPoints(RowHeight);
		for (int i = 0; i < fieldMaps.size(); i++) {
			FieldMap fieldMap = fieldMaps.get(i);
			HSSFCell nameCell = row0.createCell(i);
			nameCell.setCellValue(fieldMap.getName());
			sheet.setColumnWidth(i, ColumnWidth);
		}
		/*
		 * 
		 * HSSFCell nameCell0 = row0.createCell(0);
		 * nameCell0.setCellValue("工程名称"); HSSFCell areaCell0 =
		 * row0.createCell(1); areaCell0.setCellValue("面积"); HSSFCell
		 * locationCell0 = row0.createCell(2); locationCell0.setCellValue("地址");
		 */
		HSSFRow row = sheet.createRow(1);
		// 开始创建单元格并赋值
		for (int i = 0; i < fieldMaps.size(); i++) {
			FieldMap fieldMap = fieldMaps.get(i);
			HSSFCell nameCell = row.createCell(i);

			Object value = ClassUtil.getFieldValue(project, fieldMap.getName());
			if (value == null) {
				try {
					int zfileId = Integer.parseInt(fieldMap.getName());
					Zfile zfile = zfileService.findZfileById(zfileId);
					System.out.println(zfile.getName());
					value = zfile.getName();
				} catch (NumberFormatException e) {
					System.out.println(value);
				}
			} else {
				System.out.println(value);
			}
			if (value == null) {
				nameCell.setCellValue("");
			} else {
				nameCell.setCellValue(value.toString());
			}
		}
		/*
		 * HSSFCell nameCell = row.createCell(0);
		 * nameCell.setCellValue(project.getProname()); HSSFCell areaCell =
		 * row.createCell(1); areaCell.setCellValue(project.getArea()); HSSFCell
		 * locationCell = row.createCell(2);
		 * locationCell.setCellValue(project.getLocation());
		 */

		// 生成文件
		File file = new File(filePath);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			workbook2003.write(fos);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 创建2003文件的方法
	 * 
	 * @param project
	 * @param zSequenceService
	 * @param filePath
	 * @param zfileService
	 */
	public static void generateExcel2003_4(Project project,
			ZSequenceService zSequenceService, PaymentService paymentService,
			String filePath) {
		// 先创建工作簿对象
		HSSFWorkbook workbook2003 = new HSSFWorkbook();
		// 创建工作表对象并命名
		HSSFSheet sheet = workbook2003.createSheet("配套费工程财务收、付款情况表");
		// 创建格式
		HSSFCellStyle setBorder = workbook2003.createCellStyle();
		setBorder.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 居中
		setBorder.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		setBorder.setWrapText(true);// 设置自动换行

		// 遍历集合对象创建行和单元格
		// for (int i = 0; i < studentList.size(); i++) {
		// 取出Student对象
		// Student student = studentList.get(i);

		// 创建行

		List<FieldMap> fieldMaps = zSequenceService.getSequenceFields();
		HSSFRow row0 = sheet.createRow(0);
		row0.setHeightInPoints(RowHeight);
		for (int i = 0; i < fieldMaps.size(); i++) {
			FieldMap fieldMap = fieldMaps.get(i);
			HSSFCell nameCell = row0.createCell(i);
			if (fieldMap.getDisplayName() == null) {
				nameCell.setCellValue(fieldMap.getName());
			} else {
				nameCell.setCellValue(fieldMap.getDisplayName());
			}
			sheet.setColumnWidth(i, ColumnWidth);
		}
		/*
		 * 
		 * HSSFCell nameCell0 = row0.createCell(0);
		 * nameCell0.setCellValue("工程名称"); HSSFCell areaCell0 =
		 * row0.createCell(1); areaCell0.setCellValue("面积"); HSSFCell
		 * locationCell0 = row0.createCell(2); locationCell0.setCellValue("地址");
		 */
		HSSFRow row = sheet.createRow(1);
		// 开始创建单元格并赋值
		for (int i = 0; i < fieldMaps.size(); i++) {
			FieldMap fieldMap = fieldMaps.get(i);
			HSSFCell nameCell = row.createCell(i);

			Object value = ClassUtil.getFieldValue(project, fieldMap.getName());
			if (value == null) {
				try {
					int zPaymentId = Integer.parseInt(fieldMap.getName());
					// ZPayment zPayment = zPaymentService
					// .findZPaymentById(zPaymentId);
					Payment payment = paymentService.getPayment(
							project.getId(), zPaymentId);
					if (payment != null) {
						value = payment.getMoney();
					} else {
						value = null;
					}
					// System.out.println(zPayment.getName());
					// value = zPayment.getName();
				} catch (NumberFormatException e) {
					System.out.println(value);
				}
			} else {
				System.out.println(value);
			}
			if (value == null) {
				nameCell.setCellValue("");
			} else {
				nameCell.setCellValue(value.toString());
			}
		}
		/*
		 * HSSFCell nameCell = row.createCell(0);
		 * nameCell.setCellValue(project.getProname()); HSSFCell areaCell =
		 * row.createCell(1); areaCell.setCellValue(project.getArea()); HSSFCell
		 * locationCell = row.createCell(2);
		 * locationCell.setCellValue(project.getLocation());
		 */

		// 生成文件
		File file = new File(filePath);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			workbook2003.write(fos);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 创建2003文件的方法
	 * 
	 * @param project
	 * @param zSequenceService
	 * @param filePath
	 * @param zfileService
	 */
	public static void generateExcel2003_5(Project project,
			ZSequenceService zSequenceService, PaymentService paymentService,
			String filePath) {
		// 先创建工作簿对象
		HSSFWorkbook workbook2003 = new HSSFWorkbook();
		// 创建工作表对象并命名
		HSSFSheet sheet = workbook2003.createSheet("配套费工程财务收、付款情况表");
		// 创建格式
		HSSFCellStyle setBorder = workbook2003.createCellStyle();
		setBorder.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 居中
		setBorder.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		setBorder.setWrapText(true);// 设置自动换行

		// 遍历集合对象创建行和单元格
		// for (int i = 0; i < studentList.size(); i++) {
		// 取出Student对象
		// Student student = studentList.get(i);

		// 创建行

		List<FieldMap> fieldMaps = zSequenceService.getCustomSequenceFields();
		HSSFRow row0 = sheet.createRow(0);
		row0.setHeightInPoints(RowHeight);
		for (int i = 0; i < fieldMaps.size(); i++) {
			FieldMap fieldMap = fieldMaps.get(i);
			HSSFCell nameCell = row0.createCell(i);
			if (fieldMap.getDisplayName() == null) {
				nameCell.setCellValue(fieldMap.getName());
			} else {
				nameCell.setCellValue(fieldMap.getDisplayName());
			}
			sheet.setColumnWidth(i, ColumnWidth);
		}
		/*
		 * 
		 * HSSFCell nameCell0 = row0.createCell(0);
		 * nameCell0.setCellValue("工程名称"); HSSFCell areaCell0 =
		 * row0.createCell(1); areaCell0.setCellValue("面积"); HSSFCell
		 * locationCell0 = row0.createCell(2); locationCell0.setCellValue("地址");
		 */
		HSSFRow row = sheet.createRow(1);
		// 开始创建单元格并赋值
		for (int i = 0; i < fieldMaps.size(); i++) {
			FieldMap fieldMap = fieldMaps.get(i);
			HSSFCell nameCell = row.createCell(i);
			Object value = new Object();
			try {
				int zPaymentId = Integer.parseInt(fieldMap.getName());
				if (zPaymentId > 0) {
					Payment payment = paymentService.getPayment(
							project.getId(), zPaymentId);
					if (payment != null) {
						value = payment.getMoney();
					} else {
						value = null;
					}
				} else {
					value = null;
				}
			} catch (NumberFormatException e1) {
				value = ClassUtil.getFieldValue(project, fieldMap.getName());
			}
			System.out.println(value);

			if (value == null) {
				nameCell.setCellValue("");
			} else {
				nameCell.setCellValue(value.toString());
			}
		}
		/*
		 * HSSFCell nameCell = row.createCell(0);
		 * nameCell.setCellValue(project.getProname()); HSSFCell areaCell =
		 * row.createCell(1); areaCell.setCellValue(project.getArea()); HSSFCell
		 * locationCell = row.createCell(2);
		 * locationCell.setCellValue(project.getLocation());
		 */

		// 生成文件
		File file = new File(filePath);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			workbook2003.write(fos);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 创建2003文件的方法
	 * 
	 * @param projects
	 * @param zSequenceService
	 * @param filePath
	 * @param zfileService
	 */
	public static void generateExcel2003(List<Project> projects,
			ZSequenceService zSequenceService, PaymentService paymentService,
			String filePath) {
		// 先创建工作簿对象
		HSSFWorkbook workbook2003 = new HSSFWorkbook();
		// 创建工作表对象并命名
		HSSFSheet sheet = workbook2003.createSheet("配套费工程财务收、付款情况表");
		// 创建格式
		HSSFCellStyle setBorder = workbook2003.createCellStyle();
		setBorder.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 居中
		setBorder.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		setBorder.setWrapText(true);// 设置自动换行

		// 遍历集合对象创建行和单元格
		// for (int i = 0; i < studentList.size(); i++) {
		// 取出Student对象
		// Student student = studentList.get(i);

		// 创建行
		List<FieldMap> fieldMaps = zSequenceService.getCustomSequenceFields();
		HSSFRow row0 = sheet.createRow(0);
		row0.setHeightInPoints(RowHeight);
		for (int i = 0; i < fieldMaps.size(); i++) {
			FieldMap fieldMap = fieldMaps.get(i);
			HSSFCell nameCell = row0.createCell(i);
			if (fieldMap.getDisplayName() == null) {
				nameCell.setCellValue(fieldMap.getName());
			} else {
				nameCell.setCellValue(fieldMap.getDisplayName());
			}
			sheet.setColumnWidth(i, ColumnWidth);
		}
		/*
		 * 
		 * HSSFCell nameCell0 = row0.createCell(0);
		 * nameCell0.setCellValue("工程名称"); HSSFCell areaCell0 =
		 * row0.createCell(1); areaCell0.setCellValue("面积"); HSSFCell
		 * locationCell0 = row0.createCell(2); locationCell0.setCellValue("地址");
		 */

		// 开始创建单元格并赋值
		for (int ii = 0; ii < projects.size(); ii++) {
			Project project = projects.get(ii);
			HSSFRow row = sheet.createRow(ii + 1);
			for (int i = 0; i < fieldMaps.size(); i++) {
				FieldMap fieldMap = fieldMaps.get(i);
				HSSFCell nameCell = row.createCell(i);
				if (fieldMap.getDisplayName().equals("序号")) {
					nameCell.setCellValue(String.valueOf(ii + 1));
					continue;
				}
				Object value = new Object();
				try {
					int zPaymentId = Integer.parseInt(fieldMap.getName());
					if (zPaymentId > 0) {
						Payment payment = paymentService.getPayment(
								project.getId(), zPaymentId);
						if (payment != null) {
							value = payment.getMoney();
						} else {
							value = null;
						}
					} else {
						value = null;
					}
				} catch (NumberFormatException e1) {
					value = ClassUtil
							.getFieldValue(project, fieldMap.getName());
				}
				// System.out.println(value);
				if (value == null) {
					nameCell.setCellValue("");
				} else {
					nameCell.setCellValue(value.toString());
				}
			}
		}
		/*
		 * HSSFCell nameCell = row.createCell(0);
		 * nameCell.setCellValue(project.getProname()); HSSFCell areaCell =
		 * row.createCell(1); areaCell.setCellValue(project.getArea()); HSSFCell
		 * locationCell = row.createCell(2);
		 * locationCell.setCellValue(project.getLocation());
		 */

		// 生成文件
		File file = new File(filePath);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			workbook2003.write(fos);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 创建2003文件的方法
	 * 
	 * @param projects
	 * @param zSequenceService
	 * @param filePath
	 * @param zfileService
	 */
	public static void generateExcel2003_5(List<Project> projects,
			ZSequenceService zSequenceService, PaymentService paymentService,
			String serverRealPath, String filePath) {
		// 先创建工作簿对象
		HSSFWorkbook workbook2003 = new HSSFWorkbook();
		// 创建工作表对象并命名
		HSSFSheet sheet = workbook2003.createSheet("配套费工程财务收、付款情况表");
		// 创建格式
		HSSFCellStyle setBorder = workbook2003.createCellStyle();
		setBorder.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 居中
		setBorder.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		setBorder.setWrapText(true);// 设置自动换行

		// 创建行
		List<FieldMap> fieldMaps = zSequenceService.getCustomSequenceFields();
		HSSFRow row0 = sheet.createRow(0);
		row0.setHeightInPoints(RowHeight);
		for (int i = 0; i < fieldMaps.size(); i++) {
			FieldMap fieldMap = fieldMaps.get(i);
			HSSFCell nameCell = row0.createCell(i);
			if (fieldMap.getDisplayName() == null) {
				nameCell.setCellValue(fieldMap.getName());
			} else {
				nameCell.setCellValue(fieldMap.getDisplayName());
			}
			sheet.setColumnWidth(i, ColumnWidth);
		}

		// 开始创建单元格并赋值
		for (int ii = 0; ii < projects.size(); ii++) {
			Project project = projects.get(ii);
			HSSFRow row = sheet.createRow(ii + 1);
			for (int i = 0; i < fieldMaps.size(); i++) {
				FieldMap fieldMap = fieldMaps.get(i);
				HSSFCell nameCell = row.createCell(i);
				if (fieldMap.getDisplayName().equals("序号")) {
					nameCell.setCellValue(String.valueOf(ii + 1));
					continue;
				}
				Object value = new Object();
				try {
					int zPaymentId = Integer.parseInt(fieldMap.getName());
					if (zPaymentId > 0) {
						Payment payment = paymentService.getPayment(
								project.getId(), zPaymentId);
						if (payment != null) {
							value = payment.getMoney();
						} else {
							value = null;
						}
					} else {
						value = null;
					}
				} catch (NumberFormatException e1) {
					value = ClassUtil
							.getFieldValue(project, fieldMap.getName());
				}
				if (value == null) {
					nameCell.setCellValue("");
				} else {
					nameCell.setCellValue(value.toString());
				}
			}
		}

		// 生成文件
		File file = new File(serverRealPath, filePath);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			workbook2003.write(fos);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 
	 * 创建2003文件的方法,生成最终的Excel大表
	 * 
	 * @param projects
	 * @param zSequenceService
	 * @param paymentService
	 * @param fundService
	 * @param serverRealPath
	 * @param filePath
	 */
	public static void generateExcel2003_final(List<Project> projects,
			ZSequenceService zSequenceService, PaymentService paymentService,
			FundService fundService, String serverRealPath, String filePath) {
		// 先创建工作簿对象
		HSSFWorkbook workbook2003 = new HSSFWorkbook();
		// 创建工作表对象并命名
		HSSFSheet sheet = workbook2003.createSheet("配套费工程财务收、付款情况表");
		// 创建格式
		HSSFCellStyle setBorder = workbook2003.createCellStyle();
		setBorder.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 居中
		setBorder.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		setBorder.setWrapText(true);// 设置自动换行

		// 创建行
		List<FieldMap> fieldMaps = zSequenceService.getCustomSequenceFields();
		HSSFRow row0 = sheet.createRow(0);
		row0.setHeightInPoints(RowHeight);
		for (int i = 0; i < fieldMaps.size(); i++) {
			FieldMap fieldMap = fieldMaps.get(i);
			HSSFCell nameCell = row0.createCell(i);
			if (fieldMap.getDisplayName() == null) {
				nameCell.setCellValue(fieldMap.getName());
			} else {
				nameCell.setCellValue(fieldMap.getDisplayName());
			}
			sheet.setColumnWidth(i, ColumnWidth);
		}

		// 开始创建单元格并赋值
		for (int ii = 0; ii < projects.size(); ii++) {
			Project project = projects.get(ii);
			HSSFRow row = sheet.createRow(ii + 1);
			for (int i = 0; i < fieldMaps.size(); i++) {
				FieldMap fieldMap = fieldMaps.get(i);
				HSSFCell nameCell = row.createCell(i);
				if (fieldMap.getDisplayName().equals("序号")) {
					nameCell.setCellValue(String.valueOf(ii + 1));
					continue;
				}
				Object value = new Object();
				String name = fieldMap.getName();
				// System.out.println("----------------------------" + name);
				if (!StringUtil.IsInteger(name)) {
					if (name.startsWith("p.")) {
						int index = name.indexOf('.');
						String pid = name.substring(index + 1);
						// System.out
						// .println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>" + pid);
						try {
							int zPaymentId = Integer.parseInt(pid);
							if (zPaymentId > 0) {
								Payment payment = paymentService.getPayment(
										project.getId(), zPaymentId);
								if (payment != null) {
									value = payment.getMoney();
								} else {
									value = null;
								}
							} else {
								value = null;
							}
						} catch (NumberFormatException e1) {
							value = ClassUtil.getFieldValue(project, name);
						}
					} else if (name.startsWith("fn.")) {
						int index = name.indexOf('.');
						String pid = name.substring(index + 1);
						// System.out
						// .println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>" + pid);
						try {
							int zfundId = Integer.parseInt(pid);
							if (zfundId > 0) {
								Fund fund = fundService.getFund(
										project.getId(), zfundId);
								if (fund != null) {
									value = fund.getMoney();
								} else {
									value = null;
								}
							} else {
								value = null;
							}
						} catch (NumberFormatException e1) {
							value = ClassUtil.getFieldValue(project, name);
						}
					} else if (name.startsWith("ft.")) {
						int index = name.indexOf('.');
						String pid = name.substring(index + 1);
						// System.out
						// .println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>" + pid);
						try {
							int zfundId = Integer.parseInt(pid);
							if (zfundId > 0) {
								Fund fund = fundService.getFund(
										project.getId(), zfundId);
								if (fund != null) {
									value = fund.getTime();
								} else {
									value = null;
								}
							} else {
								value = null;
							}
						} catch (NumberFormatException e1) {
							value = ClassUtil.getFieldValue(project, name);
						}
					} else if (name.startsWith("fo.")) {
						int index = name.indexOf('.');
						String pid = name.substring(index + 1);
						// System.out
						// .println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>" + pid);
						try {
							int zfundId = Integer.parseInt(pid);
							if (zfundId > 0) {
								Fund fund = fundService.getFund(
										project.getId(), zfundId);
								if (fund != null) {
									value = fund.getOper();
								} else {
									value = null;
								}
							} else {
								value = null;
							}
						} catch (NumberFormatException e1) {
							value = ClassUtil.getFieldValue(project, name);
						}
					} else {
						value = ClassUtil.getFieldValue(project, name);
					}
				} else {
					value = null;
				}

				if (value == null) {
					nameCell.setCellValue("");
				} else {
					nameCell.setCellValue(value.toString());
				}
			}
		}

		// 生成文件
		File file = new File(serverRealPath, filePath);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			workbook2003.write(fos);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	// //////////////////////////

	/**
	 * 主函数
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		// generateExcel2003(xls2003);
		Project project = new Project();
		project.setProname("江南壹諕一期");
		project.setArea("324234");
		project.setLocation("吉林省长春市");
		generateExcel2007(project, "C:\\1.xlsx");
		long end = System.currentTimeMillis();
		System.out.println((end - start) + " ms done!");
	}
}
