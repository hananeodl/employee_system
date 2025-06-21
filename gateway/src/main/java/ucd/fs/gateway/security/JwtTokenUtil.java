package ucd.fs.gateway.security;

//@Component
//public class JwtTokenUtil {
//
//    private final SecretKey secretKey;
//
//    public JwtTokenUtil() {
//        this.secretKey = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
//    }
//
//    public Claims validateToken(String token) {
//        return Jwts.parserBuilder()
//                .setSigningKey(secretKey)
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }
//}