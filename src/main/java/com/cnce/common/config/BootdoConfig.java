package com.cnce.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
/*
@ConfigurationProperties 是 Spring Boot 中的标签，它可以让开发者将整个配置文件，映射到对象中，比@Value 效率更高。
 */
@Component
@ConfigurationProperties(prefix="cnce")
public class BootdoConfig {
	//上传路径
	private String uploadPath;

	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}
}
