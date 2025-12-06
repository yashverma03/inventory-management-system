package com.app.modules.jwt.filters;

import com.app.common.annotations.IsPublic;
import com.app.common.exception.ErrorResponseUtil;
import com.app.modules.jwt.JwtService;
import com.app.modules.jwt.records.JwtPayload;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  @Autowired
  private JwtService jwtService;

  @Autowired
  private RequestMappingHandlerMapping handlerMapping;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    try {
      // Skip authentication for Swagger endpoints
      String path = request.getRequestURI();
      if (path.startsWith("/api-docs") ||
          path.startsWith("/swagger-ui") ||
          path.startsWith("/docs") ||
          path.equals("/swagger-ui.html")) {
        filterChain.doFilter(request, response);
        return;
      }

      // Check if handler has @IsPublic annotation
      try {
        HandlerExecutionChain handlerChain = handlerMapping.getHandler(request);
        if (handlerChain != null && handlerChain.getHandler() instanceof HandlerMethod) {
          HandlerMethod handlerMethod = (HandlerMethod) handlerChain.getHandler();
          if (handlerMethod.getMethodAnnotation(IsPublic.class) != null ||
              handlerMethod.getBeanType().getAnnotation(IsPublic.class) != null) {
            // @IsPublic endpoint - allow without authentication
            filterChain.doFilter(request, response);
            return;
          }
        }
      } catch (Exception e) {
        // If we can't determine the handler, continue with authentication
      }

      // Extract token from Authorization header
      String authHeader = request.getHeader("Authorization");
      if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        throw new ResponseStatusException(
            HttpStatus.UNAUTHORIZED,
            "Missing or invalid Authorization header");
      }

      String token = authHeader.substring(7);

      // Validate and extract token
      JwtPayload tokenPayload = jwtService.validateAndExtractToken(token);

      // Set user in request attribute (for easy access in controllers)
      request.setAttribute("user", tokenPayload);

      // Set authentication in SecurityContext
      List<SimpleGrantedAuthority> authorities = Collections.singletonList(
          new SimpleGrantedAuthority("ROLE_" + tokenPayload.role().getValue().toUpperCase()));

      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
          tokenPayload,
          null,
          authorities);
      authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authentication);

      filterChain.doFilter(request, response);

    } catch (ResponseStatusException e) {
      ErrorResponseUtil.writeErrorResponse(response, e);
    }
  }
}
