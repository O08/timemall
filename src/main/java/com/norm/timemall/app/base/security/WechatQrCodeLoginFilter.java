package com.norm.timemall.app.base.security;

import cn.hutool.core.util.StrUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

public class WechatQrCodeLoginFilter extends AbstractAuthenticationProcessingFilter {
    public WechatQrCodeLoginFilter() {
        super(new AntPathRequestMatcher("/api/v1/web_mall/do_wechat_qrCode_sign_in","GET"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        // 需要是 GET 请求
        if (!request.getMethod().equals("GET")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        String code = request.getParameter("code");
        String state = request.getParameter("state");

        // 判断请求格式是否 JSON
        if (StrUtil.isNotBlank(code) && StrUtil.isNotBlank(state)) {
            // 获得请求参数

            WechatQrCodeAuthenticationToken token = new WechatQrCodeAuthenticationToken(code,state);
            setDetails(request, token);
            Authentication authenticate = this.getAuthenticationManager().authenticate(token);
            return authenticate;
        }
        return null;
    }

    public void setDetails(HttpServletRequest request , WechatQrCodeAuthenticationToken token){
        token.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }
}