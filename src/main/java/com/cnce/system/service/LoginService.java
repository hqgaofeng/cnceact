package com.cnce.system.service;

import com.cnce.common.utils.ResultData;

import javax.servlet.http.HttpServletRequest;

public interface LoginService {

    ResultData ajaxLogin(HttpServletRequest request, String username, String password);

}
