package cn.zhuqi.oa.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.zhuqi.oa.dao.MessageDao;
import cn.zhuqi.oa.model.Message;
import cn.zhuqi.oa.service.MessageService;
import cn.zhuqi.oa.vo.PagerVO;

@Service("messageService")
public class MessageServiceImpl implements MessageService {

	@Resource
	private MessageDao messageDao;

	@Override
	public void addMessage(Message message) {
		messageDao.save(message);
	}

	@Override
	public Message findMessageById(int messageId) {
		return messageDao.findById(Message.class, messageId);
	}

	@Override
	public void delMessage(int messageId, int type) {
		messageDao.delMessage(messageId, type);
	}

	@Override
	public PagerVO findAllReadingMessage(int userId) {
		return messageDao.findAllReadingMessage(userId);
	}

	@Override
	public PagerVO findAllReceiveMessage(int userId) {
		return messageDao.findAllReceiveMessage(userId);
	}

	@Override
	public PagerVO findAllSendMessage(int userId) {
		return messageDao.findAllSendMessage(userId);
	}

	@Override
	public PagerVO findAllDeletedMessage(int userId) {
		return messageDao.findAllDeletedMessage(userId);
	}

	@Override
	public void updateMessage(Message message) {
		messageDao.update(message);
	}

	@Override
	public List<Message> findAllMessageByFTD(int from, int to, Date dfrom,
			Date dTo) {
		return messageDao.findAllMessageByFTD(from, to, dfrom, dTo);
	}

}
