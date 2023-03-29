package com.cnce.system.service.impl;

import com.cnce.cache.TokenCacher;
import com.cnce.common.domain.DictDO;
import com.cnce.common.service.DictService;
import com.cnce.common.utils.*;
import com.cnce.system.domain.UserDO;
import com.cnce.system.service.LoginService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录
 */
@Service
public class LoginServiceImpl implements LoginService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private DictService dictService;

    @Override
    public ResultData ajaxLogin(HttpServletRequest request, String username, String pwd) {
        String password = MD5Utils.encrypt(username, pwd);
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        String tokenStr = MD5Utils.encrypt(password,String.valueOf(System.currentTimeMillis()));
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            Map<String, Object> params = new HashMap();
            params.put("token",tokenStr);//用：username+password进行加密,然后在时间戳MD5加密
            String userAgent = request.getHeader("User-Agent");
            String os = BizUtil.getOS(userAgent);
            //非app的，或者app的浏览器不存储token
            if(BizUtil.isWeb(userAgent) || "others".equals(os)){
                //
            }else{
                UserDO user =  ShiroUtils.getUser();
                params.put("level",(null==user || "admin".equals(user.getUsername()))?-1:user.getLevel());
                params.put("attrId",null==user?0:user.getAttrId());
                TokenCacher.getInstance().putToken(username,tokenStr,30);
            }
            ResultData rd = this.checkPwd(pwd);
            if(null != rd){
                return rd;
            }
            return new ResultData("0",true,params,"操作成功");
        } catch (AuthenticationException e) {
            logger.error(e.getMessage());
            return new ResultData("201",false,"用户名或密码错误");
        }
    }

    private ResultData checkPwd(String pwd){
        List<DictDO> pwd_complex = dictService.listByType("check_pwd_complex");
        if(CollectionUtils.isEmpty(pwd_complex) || pwd_complex.size()==0 ||
                "no".equals(pwd_complex.get(0).getValue())){
            return null;
        }
        //登录成功后，校验密码复杂度
        if(!PwdCheckUtil.checkContainCase(pwd) ||
                !PwdCheckUtil.checkContainDigit(pwd) ||
                !PwdCheckUtil.checkPasswordLength(pwd,"8","40")||
                !PwdCheckUtil.checkContainLowerCase(pwd) ||
                !PwdCheckUtil.checkContainUpperCase(pwd)||
                !PwdCheckUtil.checkContainSpecialChar(pwd)){
            return new ResultData("202",false,"密码强度太弱,请修改密码(包含大小写字母、数字、特殊字符，长度至少为8位)");
        }
        //密码定期是否修改
        UserDO user = ShiroUtils.getUser();
        List<DictDO> dds = dictService.listByType("pwd_time_limit");
        int pwdLimit = 3;//默认3个月
        if(!CollectionUtils.isEmpty(dds)){
            pwdLimit = Integer.parseInt(dds.get(0).getValue());
        }
        if(TimeUtils.addTime(user.getPwdModified(),pwdLimit,"month").before(new Date())){
            return new ResultData("203",false,"您密码超过"+pwdLimit+"月未修改,请重新修改后登录");
        }
        return null;
    }

}