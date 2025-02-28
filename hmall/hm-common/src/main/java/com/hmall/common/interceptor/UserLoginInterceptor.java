package com.hmall.common.interceptor;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.hmall.common.utils.UserContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class UserLoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1.获取登录用户信息
        String userId = request.getHeader("user-info");
        log.info("[UserLoginInterceptor] userId:{}", userId);
        if (StringUtils.isNotBlank(userId)) {
            // 2.存入上下文
            UserContext.setUser(Long.valueOf(userId));
        }
        // 3.放行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 清理用户
        UserContext.removeUser();
    }
}
