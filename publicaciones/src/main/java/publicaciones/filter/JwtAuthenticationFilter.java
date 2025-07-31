package publicaciones.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import publicaciones.util.JwtUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final JwtUtils jwtUtils;

    public JwtAuthenticationFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        try {
            // Limpiar el contexto de seguridad al inicio de cada request
            SecurityContextHolder.clearContext();

            String header = request.getHeader("Authorization");
            logger.debug("Authorization header: {}", header != null ? "Bearer ***" : "null");

            if (header != null && header.startsWith("Bearer ")) {
                String token = header.substring(7);

                if (jwtUtils.validateJwtToken(token)) {
                    String username = jwtUtils.getUserNameFromJwtToken(token);
                    var roles = jwtUtils.getRolesFromJwtToken(token)
                            .stream()
                            .map(role -> {
                                // Asegurar que los roles tengan el prefijo ROLE_
                                if (!role.startsWith("ROLE_")) {
                                    return new SimpleGrantedAuthority("ROLE_" + role);
                                }
                                return new SimpleGrantedAuthority(role);
                            })
                            .toList();

                    logger.debug("Authenticated user: {} with roles: {}", username, roles);

                    var auth = new UsernamePasswordAuthenticationToken(username, null, roles);
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                } else {
                    logger.debug("Invalid JWT token");
                }
            } else {
                logger.debug("No JWT token found in request");
            }
        } catch (Exception e) {
            logger.error("Error processing JWT token: {}", e.getMessage());
            SecurityContextHolder.clearContext();
        }

        chain.doFilter(request, response);
    }
}