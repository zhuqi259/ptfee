package cn.zhuqi.oa.web.actions;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.zhuqi.oa.model.Database;
import cn.zhuqi.oa.model.Message;
import cn.zhuqi.oa.model.User;
import cn.zhuqi.oa.service.MessageService;
import cn.zhuqi.oa.service.UserService;
import cn.zhuqi.oa.vo.PagerVO;
import cn.zhuqi.oa.web.JSONUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

@Controller("messageAction")
@Scope("prototype")
public class MessageAction extends BaseAction implements ModelDriven {

	private Message message;

	@Resource
	private MessageService messageService;

	@Resource
	private UserService userService;

	private int userId;

	private int type;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public Object getModel() {
		if (message == null) {
			message = new Message();
		}
		userId = currentUser().getId();
		return message;
	}

	public String execute() {
		return "index";
	}

	/**
	 * 已接收消息
	 */
	public void Receivelist() {
		Map map = new HashMap();

		PagerVO pv = messageService.findAllReceiveMessage(userId);

		map.put("iTotalRecords", pv.getTotal());
		map.put("iTotalDisplayRecords", pv.getTotal());
		map.put("aaData", pv.getDatas());
		JSONUtils.toJSON(map);
	}

	/**
	 * 已发送消息
	 */
	public void Sendlist() {
		Map map = new HashMap();

		PagerVO pv = messageService.findAllSendMessage(userId);

		map.put("iTotalRecords", pv.getTotal());
		map.put("iTotalDisplayRecords", pv.getTotal());
		map.put("aaData", pv.getDatas());
		JSONUtils.toJSON(map);
	}

	/**
	 * 未读消息
	 */
	public void Readinglist() {
		Map map = new HashMap();

		PagerVO pv = messageService.findAllReadingMessage(userId);

		map.put("iTotalRecords", pv.getTotal());
		map.put("iTotalDisplayRecords", pv.getTotal());
		map.put("aaData", pv.getDatas());
		JSONUtils.toJSON(map);
	}

	public void Deletedlist() {
		Map map = new HashMap();

		PagerVO pv = messageService.findAllDeletedMessage(userId);

		map.put("iTotalRecords", pv.getTotal());
		map.put("iTotalDisplayRecords", pv.getTotal());
		// System.out.println("total----------------------" + pv.getTotal());
		map.put("aaData", pv.getDatas());
		JSONUtils.toJSON(map);
	}

	public String addInput() {
		List<User> users = userService.findAllUsers();
		ActionContext.getContext().put("users", users);
		return "add_input";
	}

	public String add() {
		message.setTime(new Date());
		messageService.addMessage(message);
		return "add_success";
	}

	public String replyInput() {
		ActionContext.getContext().put("to",
				userService.findUserById(message.getUto().getId()));
		System.out.println("Title----------" + message.getTitle());
		ActionContext.getContext().put("retitle", "Re:" + message.getTitle());
		return "reply_input";
	}

	public String view() {
		message = messageService.findMessageById(message.getId());
		if (userId == message.getUto().getId()) {
			ReadMessage();
		}
		return "view";
	}

	public void del() {
		messageService.delMessage(message.getId(), type);
	}

	private void ReadMessage() {
		if (message.getReaded() == 'N') {
			message.setReaded('Y');
			messageService.updateMessage(message);
		}
	}

}
