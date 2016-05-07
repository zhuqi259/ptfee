package cn.zhuqi.system;

import info.monitorenter.cpdetector.io.ASCIIDetector;
import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;
import info.monitorenter.cpdetector.io.ParsingDetector;
import info.monitorenter.cpdetector.io.UnicodeDetector;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;


import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import sun.misc.BASE64Encoder;

public class EncoderUtil {

    private static String base64EncodeFileName(String fileName) {
        BASE64Encoder base64Encoder = new BASE64Encoder();
        try {
            return "=?UTF-8?B?" + base64Encoder.encode(fileName.getBytes("UTF-8")) + "?=";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 判断用户使用的浏览器类型, 分别处理文件下载时名字乱码问题 [注]暂时不支持FF的名称过长~~
     *
     * @param fileName
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String encodingFileName(String fileName)
            throws UnsupportedEncodingException {

        HttpServletRequest request = ServletActionContext.getRequest();
        String userAgent = request.getHeader("User-Agent");// 获取浏览器的类型
        // Firefox？IE？
        if (userAgent.contains("Firefox")) {// 处理火狐下载文件名乱码问题,以UTF-8编码，用ISO-8859-1解码

            fileName = base64EncodeFileName(fileName);

        } else {// if (userAgent.contains("IE")) // 处理IE下载文件名乱码问题，以UTF-8编码
            fileName = URLEncoder.encode(fileName, "UTF-8");
            // 替换文件名中的空格
            fileName = StringUtils.replace(fileName, "+", "%20");
            fileName = StringUtils.replace(fileName, "%2B", "+");
        }
        return fileName;
    }

    /*************************************
     * java编码与txt编码对应 java txt unicode unicode big endian utf-8 utf-8 utf-16
     * unicode gb2312 ANSI
     ***************************************/
    /**
     * @param file
     * @return
     * @throws IOException
     */
    public static String getTxtType(File file) throws IOException {
        return getTxtType(new FileInputStream(file));
    }

    /**
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static String getTxtType(InputStream inputStream) throws IOException {
        byte[] head = new byte[3];
        inputStream.read(head);
        String code = "";
        code = "gb2312";
        if (head[0] == -1 && head[2] == -2) {
            code = "UTF-16";
        }
        if (head[0] == -2 && head[2] == -1) {
            code = "Unicode";
        }
        if (head[0] == -17 && head[2] == -69) {
            code = "UTF-8";
        }
        return code;
    }

    public static String getcharset(String filepath) {
        String code = "";
        try {
            InputStream inputStream = new FileInputStream(filepath);
            byte[] head = new byte[4];
            inputStream.read(head);
            code = "gb2312";
            if (head[0] == -1 && head[1] == -2)
                code = "UTF-16";
            if (head[0] == -2 && head[1] == -1)
                code = "Unicode";
            if (head[0] == -17 && head[1] == -69 && head[2] == -65)
                code = "UTF-8";

            System.out.println(head[0] + " " + head[1] + " " + head[2]);
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return code;
    }

    public static String getEncoding(InputStream inputstream) {
        /*------------------------------------------------------------------------
          detector是探测器，它把探测任务交给具体的探测实现类的实例完成。
		  cpDetector内置了一些常用的探测实现类，这些探测实现类的实例可以通过add方法  
		  加进来，如ParsingDetector、 JChardetFacade、ASCIIDetector、UnicodeDetector。    
		  detector按照“谁最先返回非空的探测结果，就以该结果为准”的原则返回探测到的  
		  字符集编码。  
		--------------------------------------------------------------------------*/
        CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
        /*-------------------------------------------------------------------------
		  ParsingDetector可用于检查HTML、XML等文件或字符流的编码,构造方法中的参数用于  
		  指示是否显示探测过程的详细信息，为false不显示。  
		---------------------------------------------------------------------------*/
        detector.add(new ParsingDetector(false));
		/*--------------------------------------------------------------------------  
		  JChardetFacade封装了由Mozilla组织提供的JChardet，它可以完成大多数文件的编码  
		  测定。所以，一般有了这个探测器就可满足大多数项目的要求，如果你还不放心，可以  
		  再多加几个探测器，比如下面的ASCIIDetector、UnicodeDetector等。  
		---------------------------------------------------------------------------*/
        detector.add(JChardetFacade.getInstance());
        // ASCIIDetector用于ASCII编码测定
        detector.add(ASCIIDetector.getInstance());
        // UnicodeDetector用于Unicode家族编码的测定
        detector.add(UnicodeDetector.getInstance());
        java.nio.charset.Charset charset = null;
        try {
            charset = detector.detectCodepage(new BufferedInputStream(
                    inputstream), 100);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (charset != null) {
            return charset.name();
        } else {
            return null;
        }
    }

    public static String getEncoding(String document) {
		/*------------------------------------------------------------------------  
		  detector是探测器，它把探测任务交给具体的探测实现类的实例完成。  
		  cpDetector内置了一些常用的探测实现类，这些探测实现类的实例可以通过add方法  
		  加进来，如ParsingDetector、 JChardetFacade、ASCIIDetector、UnicodeDetector。    
		  detector按照“谁最先返回非空的探测结果，就以该结果为准”的原则返回探测到的  
		  字符集编码。  
		--------------------------------------------------------------------------*/
        CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
		/*-------------------------------------------------------------------------  
		  ParsingDetector可用于检查HTML、XML等文件或字符流的编码,构造方法中的参数用于  
		  指示是否显示探测过程的详细信息，为false不显示。  
		---------------------------------------------------------------------------*/
        detector.add(new ParsingDetector(false));
		/*--------------------------------------------------------------------------  
		  JChardetFacade封装了由Mozilla组织提供的JChardet，它可以完成大多数文件的编码  
		  测定。所以，一般有了这个探测器就可满足大多数项目的要求，如果你还不放心，可以  
		  再多加几个探测器，比如下面的ASCIIDetector、UnicodeDetector等。  
		---------------------------------------------------------------------------*/
        detector.add(JChardetFacade.getInstance());
        // ASCIIDetector用于ASCII编码测定
        detector.add(ASCIIDetector.getInstance());
        // UnicodeDetector用于Unicode家族编码的测定
        detector.add(UnicodeDetector.getInstance());
        java.nio.charset.Charset charset = null;
        File f = new File(document);
        try {
            charset = detector.detectCodepage(new BufferedInputStream(
                    new FileInputStream(f)), 100);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (charset != null) {
            System.out.println(f.getName() + "编码是：" + charset.name());
            return charset.name();
        } else {
            System.out.println(f.getName() + "未知");
            return null;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(getcharset("Q&A.txt"));
        System.out.println(getEncoding("Q&A.txt"));
        System.out.println(getEncoding(new FileInputStream("Q&A.txt")));
    }
}
