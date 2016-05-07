package cn.zhuqi.oa.service;

import java.util.Date;
import java.util.List;

import cn.zhuqi.oa.model.Message;
import cn.zhuqi.oa.vo.PagerVO;

public interface MessageService {

	public void addMessage(Message message);

	public void updateMessage(Message message);

	public Message findMessageById(int messageId);

	public void delMessage(int messageId, int type);

	/**
	 * 所有未读消息
	 * 
	 * @param userId
	 * @return
	 */
	public PagerVO findAllReadingMessage(int userId);

	/**
	 * 所有已接收消息
	 * 
	 * @param userId
	 * @return
	 */
	public PagerVO findAllReceiveMessage(int userId);

	/**
	 * 所有已发送消息
	 * 
	 * @param userId
	 * @return
	 */
	public PagerVO findAllSendMessage(int userId);

	/**
	 * 所有已删除消息
	 * 
	 * @param userId
	 * @return
	 */
	public PagerVO findAllDeletedMessage(int userId);

	public List<Message> findAllMessageByFTD(int from, int to, Date dfrom,
											 Date dTo);

}
