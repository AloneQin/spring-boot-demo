package com.example.demo.utils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

/**
 * IP地址处理工具类
 */
public class IpUtils {

    private static final String UNKNOWN = "unknown";
    private static final String LOCALHOST = "127.0.0.1";
    private static final String SEPARATOR = ",";

    private static final Pattern IPV4_PATTERN = Pattern.compile(
            "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$"
    );

    /**
     * 获取客户端真实IP地址
     */
    public static String getRealIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && !ip.isEmpty() && !UNKNOWN.equalsIgnoreCase(ip)) {
            // 多个路由时，取第一个非unknown的ip
            if (ip.contains(SEPARATOR)) {
                ip = ip.split(SEPARATOR)[0];
            }
        }
        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 如果是本机地址，需要做特殊处理
        if (LOCALHOST.equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                // ignore
            }
        }
        return ip;
    }

    /**
     * 验证IPv4地址格式
     */
    public static boolean isValidIpv4(String ip) {
        return ip != null && IPV4_PATTERN.matcher(ip).matches();
    }

    /**
     * 验证IPv6地址格式
     */
    public static boolean isValidIpv6(String ip) {
        try {
            InetAddress.getByName(ip);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断是否为内网IP
     */
    public static boolean isInternalIp(String ip) {
        if (isValidIpv4(ip)) {
            String[] parts = ip.split("\\.");
            if (parts.length == 4) {
                int first = Integer.parseInt(parts[0]);
                int second = Integer.parseInt(parts[1]);

                // 10.x.x.x
                if (first == 10) {
                    return true;
                }
                // 172.16.x.x - 172.31.x.x
                if (first == 172 && second >= 16 && second <= 31) {
                    return true;
                }
                // 192.168.x.x
                return first == 192 && second == 168;
            }
        }
        return false;
    }
}
