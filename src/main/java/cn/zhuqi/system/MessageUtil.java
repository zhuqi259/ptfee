package cn.zhuqi.system;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import cn.zhuqi.oa.model.Message;
import cn.zhuqi.oa.model.User;
import cn.zhuqi.oa.service.MessageService;
import cn.zhuqi.oa.service.UserService;

public class MessageUtil {

	public static void sendRemind(UserService userService,
			MessageService messageService, int userId, int count) {
		
		if (count > 0) {
			// 有待办任务
			User from = userService.findUserByUsername("login");
			Calendar currentDate = new GregorianCalendar();
			currentDate.set(Calendar.HOUR_OF_DAY, 0);
			currentDate.set(Calendar.MINUTE, 0);
			currentDate.set(Calendar.SECOND, 0);
			Date dFrom = (Date) currentDate.getTime().clone();
			User user = userService.findUserById(userId);
			List<Message> messages = messageService.findAllMessageByFTD(
					from.getId(), user.getId(), dFrom, new Date());
			if (messages == null || messages.size() == 0) {
				// 今天还没发这条消息
				Message message = new Message();
				message.setTitle("系统提示");
				message.setContent(user.getPerson().getName()
						+ "，你好！<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;今天你有【待办任务】"
						+ count + "项!<br>" + TimeUtil.Date2Str(new Date()));
				message.setUto(user);
				message.setUfr(from);
				message.setTime(new Date());
				messageService.addMessage(message);
			}
		}

	}
}
