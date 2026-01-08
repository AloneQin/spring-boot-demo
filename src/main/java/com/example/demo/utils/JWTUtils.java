package com.example.demo.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * JSON Web Token 工具类
 */
public class JWTUtils {

    /**
     * 默认过期时间: 24小时
     */
    private static final long DEFAULT_EXPIRATION = 1000 * 60 * 60 * 24;

    /**
     * 密钥
     */
    private static final String SECRET = "mySecret";

    /**
     * 生成token
     *
     * @param claims 载荷
     * @return token
     */
    public static String generateToken(Map<String, Object> claims) {
        return generateToken(null, claims, DEFAULT_EXPIRATION);
    }

    /**
     * 生成 JWT token
     *
     * @param headerClaims 头部声明信息
     * @param claims 载荷
     * @param expiration 过期时间
     * @return token
     */
    public static String generateToken(Map<String, Object> headerClaims, Map<String, Object> claims, long expiration) {
        JWTCreator.Builder builder = JWT.create().withHeader(headerClaims);

        // 添加声明信息
        if (SmartCollectionUtils.nonEmpty(claims)) {
            claims.forEach((key, value) -> {
                if (value instanceof String) {
                    builder.withClaim(key, (String) value);
                } else if (value instanceof Integer) {
                    builder.withClaim(key, (Integer) value);
                } else if (value instanceof Long) {
                    builder.withClaim(key, (Long) value);
                } else if (value instanceof Double) {
                    builder.withClaim(key, (Double) value);
                } else if (value instanceof Boolean) {
                    builder.withClaim(key, (Boolean) value);
                } else if (value instanceof Date) {
                    builder.withClaim(key, (Date) value);
                } else if (value instanceof Map) {
                    // 先检查是否为正确的泛型类型
                    Map<?, ?> mapValue = (Map<?, ?>) value;
                    // 检查键是否都是String类型
                    boolean allKeysAreStrings = mapValue.keySet().stream()
                            .allMatch(k -> k instanceof String);
                    if (allKeysAreStrings) {
                        // 安全转换
                        @SuppressWarnings("unchecked")
                        Map<String, ?> stringMap = (Map<String, ?>) value;
                        builder.withClaim(key, stringMap);
                    } else {
                        // 如果键不是字符串，则转换为字符串表示
                        builder.withClaim(key, value.toString());
                    }
                } else if (value instanceof List<?>) {
                    builder.withClaim(key, (List<?>) value);
                } else {
                    // 其他类型转换为字符串
                    builder.withClaim(key, value != null ? value.toString() : null);
                }
            });
        }

        Date expiresAt = new Date(System.currentTimeMillis() + expiration);
        builder.withExpiresAt(expiresAt);
        return builder.sign(Algorithm.HMAC256(SECRET));
    }

    /**
     * 验证 JWT token 合法性，过期均会被判断不合法
     *
     * @param token JWT token
     * @return true表示合法，false表示不合法
     */
    public static boolean verifyToken(String token) {
        try {
            JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    /**
     * 判断token是否过期
     *
     * @param token JWT token
     * @return true表示已过期，false表示未过期
     */
    public static boolean isTokenExpired(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getExpiresAt().before(new Date());
        } catch (Exception e) {
            // 如果解析失败或者没有过期时间，默认认为已过期
            return true;
        }
    }

    /**
     * 解析得到token的载荷
     *
     * @param token JWT token
     * @return token中的所有载荷信息
     */
    public static Map<String, Claim> getClaims(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaims();
        } catch (Exception e) {
            throw new RuntimeException("Failed to decode token", e);
        }
    }

    /**
     * 安全解析Claim值，自动识别最适合的类型
     * @param claim 待解析的Claim
     * @return 解析后的值
     */
    public static Object safeParseClaim(Claim claim) {
        if (claim == null) {
            return null;
        }

        // 按优先级尝试解析不同类型的值
        try {
            // 1. 尝试解析为布尔值
            Boolean boolValue = claim.asBoolean();
            if (boolValue != null) {
                return boolValue;
            }

            // 2. 尝试解析为整数
            Integer intValue = claim.asInt();
            if (intValue != null) {
                return intValue;
            }

            // 3. 尝试解析为长整数
            Long longValue = claim.asLong();
            if (longValue != null) {
                return longValue;
            }

            // 4. 尝试解析为双精度浮点数
            Double doubleValue = claim.asDouble();
            if (doubleValue != null) {
                return doubleValue;
            }

            // 5. 尝试解析为日期
            Date dateValue = claim.asDate();
            if (dateValue != null) {
                return dateValue;
            }

            // 6. 尝试解析为数组
            Object[] arrayValue = claim.asArray(Object.class);
            if (arrayValue != null) {
                return Arrays.asList(arrayValue);
            }

            // 6. 尝试解析为列表
            List<?> listValue = claim.asList(Object.class);
            if (listValue != null) {
                return listValue;
            }

            // 7. 尝试解析为 Map
            Map<String, Object> mapValue = claim.asMap();
            if (mapValue != null) {
                return mapValue;
            }

            // 7. 最后作为字符串处理
            return claim.asString();
        } catch (Exception e) {
            // 如果所有尝试都失败，返回原始字符串表示
            return claim.toString();
        }
    }

    /**
     * 安全解析Claim值，根据指定类型进行解析
     *
     * @param claim 待解析的Claim
     * @param clazz 指定要解析的类型Class
     * @param <T>   泛型类型
     * @return 解析后的指定类型的值
     */
    public static <T> T safeParseClaim(Claim claim, Class<T> clazz) {
        if (claim == null || clazz == null) {
            return null;
        }

        try {
            if (clazz == Boolean.class || clazz == boolean.class) {
                return clazz.cast(claim.asBoolean());
            } else if (clazz == Integer.class || clazz == int.class) {
                return clazz.cast(claim.asInt());
            } else if (clazz == Long.class || clazz == long.class) {
                return clazz.cast(claim.asLong());
            } else if (clazz == Double.class || clazz == double.class) {
                return clazz.cast(claim.asDouble());
            } else if (clazz == Date.class) {
                return clazz.cast(claim.asDate());
            } else if (clazz == String.class) {
                return clazz.cast(claim.asString());
            } else if (clazz.isArray()) {
                return clazz.cast(claim.asArray(clazz.getComponentType()));
            } else if (List.class.isAssignableFrom(clazz)) {
                return clazz.cast(claim.asList(Object.class));
            } else if (Map.class.isAssignableFrom(clazz)) {
                return clazz.cast(claim.asMap());
            } else {
                // 对于其他自定义类型，尝试转换为字符串再处理
                String stringValue = claim.asString();
                if (stringValue != null) {
                    // 这里可以添加更多的类型转换逻辑，如JSON反序列化等
                    return clazz.cast(stringValue);
                }
                return null;
            }
        } catch (Exception e) {
            // 类型转换异常时返回null
            return null;
        }
    }

    public static void main(String[] args) {
        Map<String, Object> claims = Map.of(
                "username", "admin",
                "password", "123456",
                "roles", List.of("admin", "user")
        );
        String token = generateToken(null, claims, 3600 * 1000);
        System.out.println(token);

        System.out.println(verifyToken(token));

        System.out.println(isTokenExpired(token));

        Map<String, Claim> claimsMap = getClaims(token);
        System.out.println(claimsMap);

        System.out.println(safeParseClaim(claimsMap.get("username")));

        List<String> roles = safeParseClaim(claimsMap.get("roles"), List.class);
        System.out.println(roles);
    }
}
