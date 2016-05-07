package cn.zhuqi.oa.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.zhuqi.oa.dao.MessageDao;
import cn.zhuqi.oa.model.Message;
import cn.zhuqi.oa.vo.PagerVO;

@Repository("messageDao")
public class MessageDaoImpl extends BaseDaoImpl implements MessageDao {

	@Override
	public void delMessage(int messageId, int type) {
		Message message = findById(Message.class, messageId);
		if (type == 1) {
			message.setFused('N');
		} else if (type == 2) {
			message.setTused('N');
		}
		update(message);
	}

	@Override
	public PagerVO findAllReadingMessage(int userId) {
		String hql = "select m.id, m.ufr.person.name, m.title, m.rtime from Message m where m.tused= 'Y' and m.readed = 'N' and m.uto.id = ? order by m.time desc";
		return findPaginated(hql, userId);
	}

	@Override
	public PagerVO findAllReceiveMessage(int userId) {
		String hql = "select m.id, m.ufr.person.name, m.title, m.rtime from Message m where m.tused= 'Y' and m.uto.id = ? order by m.time desc";
		return findPaginated(hql, userId);
	}

	@Override
	public PagerVO findAllSendMessage(int userId) {
		String hql = "select m.id, m.ufr.person.name, m.title, m.rtime from Message m where m.fused= 'Y' and m.ufr.id = ? order by m.time desc";
		return findPaginated(hql, userId);
	}

	@Override
	public PagerVO findAllDeletedMessage(int userId) {
		String hql = "select m.id, m.ufr.person.name, m.uto.person.name, m.title, m.rtime from Message m where ( m.tused = 'N' and m.uto.id = ? ) or ( m.fused = 'N' and m.ufr.id = ? ) order by m.time desc";
		return findPaginated(hql, new Object[] { userId, userId });
	}

	@Override
	public List<Message> findAllMessageByFTD(int from, int to, Date dfrom,
			Date dTo) {
		String hql = "select m from Message m where m.ufr.id = ? and m.uto.id = ? and m.time >= ? and m.time < ?";
		return getSession().createQuery(hql).setParameter(0, from)
				.setParameter(1, to).setTimestamp(2, dfrom)
				.setTimestamp(3, dTo).list();
	}
}
