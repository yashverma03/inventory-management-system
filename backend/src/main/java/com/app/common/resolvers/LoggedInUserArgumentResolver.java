package com.app.common.resolvers;

import com.app.common.annotations.LoggedInUser;
import com.app.modules.jwt.classes.JwtPayload;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LoggedInUserArgumentResolver implements HandlerMethodArgumentResolver {

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.hasParameterAnnotation(LoggedInUser.class) &&
        parameter.getParameterType().equals(JwtPayload.class);
  }

  @Override
  public Object resolveArgument(
      MethodParameter parameter,
      ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest,
      WebDataBinderFactory binderFactory) {

    HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
    if (request == null) {
      return null;
    }

    // First try to get from request attribute (set by JWT filter)
    Object user = request.getAttribute("user");
    if (user instanceof JwtPayload) {
      return user;
    }

    // Fallback to SecurityContext
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.getPrincipal() instanceof JwtPayload) {
      return (JwtPayload) authentication.getPrincipal();
    }

    return null;
  }
}
