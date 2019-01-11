package com.config;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.netflix.zuul.filters.route.ZuulFallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * @Author: yunxiang.yang
 * @Date: 2019/1/9 16:23
 */

@Component
public class UserFallbackProvider implements ZuulFallbackProvider {

    private String route = "eureka-provider";

    @Override
    public String getRoute() {
//        表明是为哪个微服务提供回退
        return route;
    }

    @Override
    public ClientHttpResponse fallbackResponse() {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
//                fallback的状态码
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
//                数字类型的状态码，返回200
                return this.getStatusCode().value();
            }

            @Override
            public String getStatusText() throws IOException {
//                状态文本，本例返回OK
                return this.getStatusCode().getReasonPhrase();
            }

            @Override
            public void close() {

            }

            @Override
            public InputStream getBody() throws IOException {
//              响应体
                JSONObject result = new JSONObject();
                result.put("error", String.format("微服务%s不可用，请稍后再试", route));
                return new ByteArrayInputStream(result.toString().getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
//               设置headers
                HttpHeaders httpHeaders = new HttpHeaders();
                MediaType mediaType = new MediaType("application", "json", Charset.forName("UTF-8"));
                httpHeaders.setContentType(mediaType);
                return httpHeaders;
            }
        };
    }
}
