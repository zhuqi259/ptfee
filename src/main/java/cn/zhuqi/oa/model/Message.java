package cn.zhuqi.oa.model;

import java.util.Date;

import cn.zhuqi.system.StringUtil;
import cn.zhuqi.system.TimeUtil;

public class Message {

	private int id;
	private String title;
	private String content;
	private Date time;
	private String rtime;
	private User ufr;
	private User uto;
	/**
	 * 是否已读
	 */
	private char readed = 'N';
	/**
	 * 发送者是否删除
	 */
	private char fused = 'Y';
	/**
	 * 接受者是否删除
	 */
	private char tused = 'Y';

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = StringUtil.myReplace(content);
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
		this.rtime = TimeUtil.Date2Str(time);
	}

	public String getRtime() {
		return rtime;
	}

	public void setRtime(String rtime) {
		this.rtime = rtime;
	}

	public User getUfr() {
		return ufr;
	}

	public void setUfr(User ufr) {
		this.ufr = ufr;
	}

	public User getUto() {
		return uto;
	}

	public void setUto(User uto) {
		this.uto = uto;
	}

	public char getReaded() {
		return readed;
	}

	public void setReaded(char readed) {
		this.readed = readed;
	}

	public char getFused() {
		return fused;
	}

	public void setFused(char fused) {
		this.fused = fused;
	}

	public char getTused() {
		return tused;
	}

	public void setTused(char tused) {
		this.tused = tused;
	}

}
