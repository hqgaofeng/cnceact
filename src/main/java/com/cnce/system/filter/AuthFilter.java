package com.cnce.system.filter;

import com.alibaba.fastjson.JSONObject;
import com.cnce.cache.TokenCacher;
import com.cnce.common.utils.BizUtil;
import com.cnce.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "authFilter", urlPatterns = "/*")
@Order(1)
public class AuthFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest hsr = (HttpServletRequest) request;
        HttpServletResponse hsres = (HttpServletResponse) response;
        String userAgent = hsr.getHeader("User-Agent");
        if (StringUtils.isEmpty(userAgent)) {
            logger.error("user-agent is null");
            outRequestForJson(401,hsres,"user-agent is null");
            return;
        }
        String os = BizUtil.getOS(userAgent);
        //非app的直接放行，或者app的浏览器直接放行
        if(BizUtil.isWeb(userAgent) || "others".equals(os)){
            filterChain.doFilter(request, response);
            return;
        }
        //登录接口不校验token
        if(hsr.getRequestURI().contains("/login")){
            filterChain.doFilter(request, response);
            return;
        }
        //app的，postman测试的，需要校验token
        String token = hsr.getHeader("token");
        String user = hsr.getHeader("username");
        logger.error("username="+user+",token="+token+",user-agent="+userAgent+",os="+os+
                ",uri="+hsr.getRequestURI());
        if(StringUtils.isEmpty(token)) {
            logger.error("token is null");
            outRequestForJson(402,hsres,"token is null");
            return;
        }
        if(StringUtils.isEmpty(user)) {
            logger.error("user is null");
            outRequestForJson(403,hsres,"user is null");
            return;
        }
        String cacheToken = TokenCacher.getInstance().getToken(user);
        if(!token.equals(cacheToken)){
            logger.error("invalid token:client-token="+token+",cacheToken="+cacheToken);
            outRequestForJson(405,hsres,"invalid token");
            return;
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

    public void outRequestForJson(Integer code,HttpServletResponse response, String res) throws IOException {
        response.setContentType("application/json; charset=UTF-8");
        JSONObject json = new JSONObject();
        json.put("code",code);
        json.put("result",false);
        json.put("msg",res);
        response.getWriter().print(json.toJSONString());
    }

}