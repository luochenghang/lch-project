package com.lch.common.config;


import io.jsonwebtoken.*;
import org.bouncycastle.util.encoders.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

/**
 * jwt加密和解密的工具类
 * 创建者 科帮网
 * 创建时间 2017年11月24日
 */
public class JwtUtils {


	public static final String JWT_KEY_USER_ID="uid";

	public static final long ttlMillis = 60 * 60 *1000;

	private static Key getKeyInstance(){
		SignatureAlgorithm signatureAlgorithm=SignatureAlgorithm.HS256;
		byte[] dc= DatatypeConverter.parseBase64Binary("luochenghang");
		return new SecretKeySpec(dc,signatureAlgorithm.getJcaName());
	}

	//生成token的方法  适用于登录
	public static String generatorToken(String uid){
		long nowMillis = System.currentTimeMillis();
		//Date now = new Date(nowMillis);
		long expMillis = nowMillis + ttlMillis;
		Date expDate = new Date(expMillis);// 过期时间

		return Jwts.builder().claim(JWT_KEY_USER_ID, uid)
				.setExpiration(expDate)
				.signWith(SignatureAlgorithm.HS256,getKeyInstance()).compact();
	}


	//根据token获得token中的信息
	public static Claims getTokenInfo(String token){
		Jws<Claims> claimsJws=Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(token);
		Claims claims=claimsJws.getBody();
		claims.get(JWT_KEY_USER_ID).toString();
		//return new JwtInfo((claims.get(JWT_KEY_USER_ID)).toString());
		return  claims;
	}

	//根据token删除token中的信息
	public static void removeToken(String token){
		Jws<Claims> claimsJws=Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(token);
		Claims claims=claimsJws.getBody();
		claims.remove(JWT_KEY_USER_ID);
	}




	/**
	 * 签发JWT
	 * @Author  科帮网
	 * @param id
	 * @param subject 可以是JSON数据 尽可能少
	 * @param ttlMillis
	 * @return  String
	 * @Date	2017年11月24日
	 * 2017年11月24日  张志朋  首次创建
	 *
	 */
//	public static String createJWT(String id, String subject, long ttlMillis) {
//		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
//		long nowMillis = System.currentTimeMillis();
//		Date now = new Date(nowMillis);
//		SecretKey secretKey = generalKey();
//		JwtBuilder builder = Jwts.builder()
//				.setId(id)
//				.setSubject(subject)   // 主题
//				.setIssuer("luochenghang")     // 签发者
//				.setIssuedAt(now)      // 签发时间
//				.signWith(signatureAlgorithm, secretKey); // 签名算法以及密匙
//		if (ttlMillis >= 0) {
//			long expMillis = nowMillis + ttlMillis;
//			Date expDate = new Date(expMillis);
//			builder.setExpiration(expDate); // 过期时间
//		}
//		return builder.compact();
//	}
	/**
	 * 验证JWT
	 * @param jwtStr
	 * @return
	 */
	public static CheckResult validateJWT(String jwtStr) {
		CheckResult checkResult = new CheckResult();
		Claims claims = null;
		try {
//			claims = parseJWT(jwtStr);
			claims = getTokenInfo(jwtStr);
			checkResult.setSuccess(true);
			checkResult.setClaims(claims);
		} catch (ExpiredJwtException e) {
			checkResult.setMsg("登录已过期");
			checkResult.setSuccess(false);
		} catch (SignatureException e) {
			checkResult.setMsg("验证不通过");
			checkResult.setSuccess(false);
		} catch (Exception e) {
			checkResult.setMsg("验证不通过");
			checkResult.setSuccess(false);
		}
		return checkResult;
	}
//	public static SecretKey generalKey() {
//		byte[] encodedKey = Base64.decode(SystemConstant.JWT_SECERT);
//	    SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
//	    return key;
//	}

	/**
	 *
	 * 解析JWT字符串
	 * //@param jwt
	 * @return
	 * @throws Exception
	 */
//	public static Claims parseJWT(String jwt) throws Exception {
//		//Jws<Claims> claimsJws=Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(token);
//		//		Claims claims=claimsJws.getBody();
//		SecretKey secretKey = generalKey();
//		return Jwts.parser()
//			.setSigningKey(secretKey)
//			.parseClaimsJws(jwt)
//			.getBody();
//	}
	public static void main(String[] args) throws InterruptedException {
		//小明失效 10s
		String sc = generatorToken("1");
		System.out.println(sc);
		System.out.println(validateJWT(sc).getMsg());
		System.out.println(validateJWT(sc).getClaims().getId());
		System.out.println(validateJWT(sc).getClaims().get("uid"));
		//Thread.sleep(3000);
		System.out.println(validateJWT(sc).getClaims());
	}
}
