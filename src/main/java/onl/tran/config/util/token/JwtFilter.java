package onl.tran.config.util.token;

import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

 private final JwtService jwtService;

 private final UserDetailsService userDetailsService;

 @Override
 protected void doFilterInternal(@Nonnull HttpServletRequest request,
                                 @Nonnull HttpServletResponse response,
                                 @Nonnull FilterChain filterChain) throws ServletException, IOException {

  final String authorizationHeader = request.getHeader("Authorization");
  String jwtToken = null;
  String phoneNumber = null;
  if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
   filterChain.doFilter(request, response);
   return;
  }

  jwtToken = authorizationHeader.substring(7);
  phoneNumber = jwtService.extractUsername(jwtToken);

  if (phoneNumber != null && SecurityContextHolder.getContext().getAuthentication() == null) {
   UserDetails userDetails = this.userDetailsService.loadUserByUsername(phoneNumber);
   if (jwtService.isTokenValid(jwtToken, userDetails)) {
    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
     userDetails, null, userDetails.getAuthorities()
    );
    authentication.setDetails(
     new WebAuthenticationDetailsSource().buildDetails(request)
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);
   }
  }
  filterChain.doFilter(request, response);
 }
}
