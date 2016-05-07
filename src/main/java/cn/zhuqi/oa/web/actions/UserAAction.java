package cn.zhuqi.oa.web.actions;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;

import sun.security.rsa.RSASignature.MD5withRSA;

import cn.zhuqi.oa.annotations.Oper;
import cn.zhuqi.oa.annotations.Res;
import cn.zhuqi.oa.model.User;
import cn.zhuqi.oa.service.UserService;
import cn.zhuqi.oa.vo.UserVO;
import cn.zhuqi.system.MD5Util;

import com.opensymphony.xwork2.ModelDriven;

@Controller("userAAction")
@Scope("prototype")
@Res(name = "用户操作", sn = "user", orderNumber = 80, parentSn = "security")
public class UserAAction extends UserAction implements ModelDriven {

    private UserVO userVO;

    @Resource
    private UserService userService;

    @Override
    public Object getModel() {
        if (userVO == null) {
            userVO = new UserVO();
        }
        return userVO;
    }

    @Oper
    public String updatePwd() {
        int userId = userVO.getId();
        User user = userService.findUserById(userId);
        String password = MD5Util.Str2Md5_32(userVO.getPassword());
        if (!password.equals(user.getPassword())) {
            throw new RuntimeException("您输入的原密码不正确!");
        }
        if (!userVO.getNewPassword().equals(userVO.getRePassword())) {
            throw new RuntimeException("您两次输入的新密码不一致!");
        }
        String newPassword = MD5Util.Str2Md5_32(userVO.getNewPassword());
        user.setPassword(newPassword); // 设置为新密码
        userService.updateUser(user);
        return "update_success";
    }

    public void check() throws IOException {
        PrintWriter out = ServletActionContext.getResponse().getWriter();
        String password = MD5Util.Str2Md5_32(userVO.getPassword());
        User user = userService.findUserById(userVO.getId());
        if (!password.equals(user.getPassword())) {
            out.print("<font color='red'>密码错误</font>");
        } else {
            out.print("<font color='blue'>密码正确</font>");
        }
    }

}
