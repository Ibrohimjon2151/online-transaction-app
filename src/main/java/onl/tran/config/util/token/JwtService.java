package onl.tran.config.util.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

 public static final String SECRET_KEY = "2D7FFCE7D4726F4CE72B11F166298A2B7B8C9D4E5F6A7B8C9D4E5F6A7B8C9D4";

 private Key getSigningKey() {
  byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
  return Keys.hmacShaKeyFor(keyBytes);
 }

 public String generateToken(Map<String, Object> claims, UserDetails userDetails) {
  return Jwts.builder().setClaims(claims)
   .setSubject(userDetails.getUsername())
   .setIssuedAt(new Date(System.currentTimeMillis()))
   .setExpiration(new Date(System.currentTimeMillis()*1000*60*48))
   .signWith(getSigningKey(), SignatureAlgorithm.HS256)
   .compact();
 }

 public String extractUsername(String token) {
  return extractClaim(token, Claims::getSubject);
 }

 public <T> T extractClaim(String token, Function<Claims,T> clazz) {
  final Claims claims = extractAllClaims(token);
  return clazz.apply(claims);
 }

 public String generateToken(UserDetails userDetails) {
  return generateToken(new HashMap<>(),userDetails);
 }


 public boolean isTokenValid(String token, UserDetails userDetails){
  String username = this.extractUsername(token);
  return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
 }

 private boolean isTokenExpired(String token) {
  return extractionExpiration(token).before(new Date());
 }

 private Date extractionExpiration(String token) {
  return extractClaim(token, Claims::getExpiration);
 }


 public Claims extractAllClaims(String token) {
  return Jwts.parser()
   .setSigningKey(getSigningKey())
   .build()
   .parseClaimsJws(token)
   .getBody();
 }


}
