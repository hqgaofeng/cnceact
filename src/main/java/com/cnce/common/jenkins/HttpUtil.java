package com.cnce.common.jenkins;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;


@Slf4j
public class HttpUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    public static void close(CloseableHttpResponse rsp) {
        if (!StringUtils.isEmpty(rsp)) {
            try {
                EntityUtils.consume(rsp.getEntity());
            } catch (Exception e) {
                logger.error(null, e);
            }
        }
    }
}
