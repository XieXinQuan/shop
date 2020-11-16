package com.quan.util;

import com.quan.constant.Constant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * @author: xiexinquan520@163.com
 * User: XieXinQuan
 * DATE:2020/5/16
 */
public class JwtUtil {
    /**
     * 加盐
     */
    public static String secret = "huanting";

    public static String createToken(Long userId){

        return createToken(String.valueOf(userId));
    }

    /**
     * 生成token
     * @param subject
     * @return
     */
    public static String createToken (String subject){
        return createToken(subject, Constant.jwtExpire);
    }
    public static String createToken(String subject, long expire){
        Date nowDate = new Date();
        //过期时间
        Date expireDate = new Date(nowDate.getTime() + expire);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(subject)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 获取token中注册信息
     * @param token
     * @return
     */
    public static Claims getTokenClaim (String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
    /**
     * 验证token是否过期失效
     */
    public static boolean isTokenExpired (String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }

    /**
     * 获取token失效时间
     * @param token
     * @return
     */
    public static Date getExpirationDateFromToken(String token) {
        Claims tokenClaim = getTokenClaim(token);
        if (tokenClaim == null) {
            return null;
        }
        return tokenClaim.getExpiration();
    }
    /**
     * 获取信息从token中
     */
    public static String getSubjectFromToken(String token) {
        return getTokenClaim(token).getSubject();
    }

    /**
     * 获取jwt发布时间
     */
    public static Date getIssuedAtDateFromToken(String token) {
        return getTokenClaim(token).getIssuedAt();
    }
}
