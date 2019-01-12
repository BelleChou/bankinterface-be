package com.yihuisoft.common.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;

/**
  * @author luweibin
  * @Description:
  * @Date: Created in 13:57 2018/4/4
  * @version v1.0.0
*/
public class JavaWebToken {

    /**
     * 该方法使用HS256算法和Secret:bankgl生成signKey
     * @param key
     * @return
     */
    private static Key getKeyInstance(String key) {
        //We will sign our JavaWebToken with our ApiKey secret
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(key);
        return new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
    }

    /**
     * 使用HS256签名算法和生成的signingKey最终的Token,claims中是有效载荷
     * @param claims
     * @param key
     * @return
     */
    public static String createJavaWebToken(Map<String, Object> claims,String key) {
        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, getKeyInstance(key)).compact();
    }

    /**
     * 解析Token，同时也能验证Token，当验证失败返回null
     * @param jwt
     * @param key
     * @return
     */
    public static Map<String, Object> parserJavaWebToken(String jwt,String key) {
        try {
            return Jwts.parser().setSigningKey(getKeyInstance(key)).parseClaimsJws(jwt).getBody();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 测试
     * @param args
     */
    public static void main(String args[]){
        System.out.println("11111111111111");
        Map<String,Object> m = new HashMap<String,Object>();
        m.put("userid", "1111111");
        String key = "23adsfasdfasdfasdewqwer";
        String token = JavaWebToken.createJavaWebToken(m,key);
        System.out.println(token);
        System.out.println(parserJavaWebToken("eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyaWQiOiIxMTExMTExIn0.JkrG0UBtm5YxDDkqBtTsuYLBzSfWVeihgYCQ3k0wS28",key));

    }
}
