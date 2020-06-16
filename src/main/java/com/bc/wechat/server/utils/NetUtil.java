package com.bc.wechat.server.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * 网络工具类
 *
 * @author zhou
 */
public class NetUtil {
    /**
     * 获取本地IP
     *
     * @return 本地IP
     */
    public static String getLocalIP() {
        String localIP = "127.0.0.1";
        try {
            OK:
            for (Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces(); interfaces.hasMoreElements(); ) {
                NetworkInterface networkInterface = interfaces.nextElement();
                if (networkInterface.isLoopback() || networkInterface.isVirtual() || !networkInterface.isUp()) {
                    continue;
                }
                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    if (address instanceof Inet4Address) {
                        localIP = address.getHostAddress();
                        break OK;
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return localIP;
    }

    /**
     * 通过request获取ip
     *
     * @return ip
     */
    public static String getIP() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getServerName();
    }

    /**
     * 通过request获取端口
     *
     * @return 端口
     */
    public static String getLocalPort() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getServerPort() + "";
    }

    /**
     * 获取IP和端口
     *
     * @return IP和端口
     */
    public static String getIpAndPort() {
        return getLocalIP() + ":" + getLocalPort();
    }
}
