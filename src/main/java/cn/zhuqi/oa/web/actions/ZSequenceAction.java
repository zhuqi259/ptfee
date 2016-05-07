package cn.zhuqi.oa.web.actions;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.zhuqi.oa.model.ZSequence;
import cn.zhuqi.oa.service.FieldMapService;
import cn.zhuqi.oa.service.ZSequenceService;

import com.opensymphony.xwork2.ModelDriven;

@Controller("zsequenceAction")
@Scope("prototype")
public class ZSequenceAction extends BaseAction implements ModelDriven {

	private ZSequence zSequence;

	@Resource
	private ZSequenceService zSequenceService;

	@Resource
	private FieldMapService fieldMapService;

	@Override
	public Object getModel() {
		if (zSequence == null) {
			zSequence = new ZSequence();
		}
		return zSequence;
	}

	public String execute() {
		return "index";
	}

	public List<ZSequence> getAllSequences() {
		return zSequenceService.getAllSequences();
	}

	public List<ZSequence> getCustomSequences() {
		return zSequenceService.getCustomSequences();
	}

	public void updateZSequenceInUse() {
		zSequence = zSequenceService.findZSequenceById(zSequence.getId());
		zSequence.setUsed('Y');
		zSequenceService.updateZSequence(zSequence);
	}

	public void updateZSequenceOffUse() {
		zSequence = zSequenceService.findZSequenceById(zSequence.getId());
		zSequence.setUsed('N');
		zSequenceService.updateZSequence(zSequence);
	}

	/**
	 * 这个del只有某些可以删除
	 * 
	 * @return
	 */
	public String del() {
		zSequenceService.delZSequence(zSequence.getId());
		return "index";
	}

	/**
	 * 判断是否选中
	 * 
	 * @return
	 */
	public String hasChecked(int zsequenceId) {
		// System.out.println(zsequenceId);
		ZSequence zsequence = zSequenceService.findZSequenceById(zsequenceId);
		// System.out.println(zsequence.getUsed());
		if (zsequence.getUsed() == 'Y') {
			return "checked";
		}
		return "";
	}

	public boolean IsCopyable(String name) {
		try {
			int num = Integer.valueOf(name);
			if (num > 0) {
				return true;
			} else {
				return false;
			}
		} catch (NumberFormatException e) {
			return true;
		}
	}

	// //////////////////////////
	// 排序

	private String[] myIndex;

	public String[] getMyIndex() {
		return myIndex;
	}

	public void setMyIndex(String[] myIndex) {
		this.myIndex = myIndex;
	}

	public String sort() {
		List<ZSequence> zSequences = getCustomSequences();
		int size = zSequences.size();
		// System.out.println(myIndex);
		if (myIndex == null || myIndex.length != size
				|| !IsRight(myIndex, size)) {
			throw new RuntimeException("排序出错");
		}
		for (int i = 0; i < size; i++) {
			ZSequence zSequence = zSequences.get(i);
			zSequence.setIndex(Integer.valueOf(myIndex[i]));
			zSequenceService.updateZSequence(zSequence);
		}
		return "sort_success";
	}

	private static boolean IsRight(String[] num, int size) {
		Integer[] a = new Integer[size];
		for (int i = 0; i < size; i++) {
			a[i] = Integer.valueOf(num[i]);
		}
		Arrays.sort(a);
		for (int i = 0; i < size; i++) {
			if ((i + 1) != a[i]) {
				return false;
			}
		}
		return true;
	}

}
