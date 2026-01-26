package com.echoform.security;

import com.echoform.repository.OneTimeTokenRepository;
import com.echoform.service.FormService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class TokenScopeValidationFilter extends OncePerRequestFilter {

    private final OneTimeTokenRepository oneTimeTokenRepository;
    private final FormService formService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        
        if (!"/api/login/ott".equals(request.getRequestURI()) || !"POST".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        String tokenValue = request.getParameter("token");
        String publicLink = request.getParameter("publicLink");

        if (tokenValue != null && publicLink != null) {
            var form = formService.getFormByPublicLink(publicLink);
            if (form.isEmpty()) {
                 response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid public link");
                 return;
            }
            Long expectedFormId = form.get().getId();
            String expectedUsername = "form:" + expectedFormId;

            var actualUsername = oneTimeTokenRepository.findUsernameByToken(tokenValue);
            
            if (actualUsername.isEmpty()) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                return;
            }
            
            if (!expectedUsername.equals(actualUsername.get())) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"Token Mismatch\", \"message\": \"This token is for a different form.\"}");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
