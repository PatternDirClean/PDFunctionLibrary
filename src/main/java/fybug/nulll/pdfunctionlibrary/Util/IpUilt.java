package fybug.nulll.pdfunctionlibrary.Util;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * <h2>ip 地址工具.</h2>
 *
 * @version 0.0.1
 * @since Util 0.0.1
 */
public
class IpUilt {
    /** 地址标识头 */
    private static final String[] HEADERS =
            {"X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_X_FORWARDED_FOR",
                    "HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_CLIENT_IP",
                    "HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR", "X-Real-IP"};

    /**
     * 判断ip是否为空
     *
     * @param ip 当前的 ip 地址
     *
     * @return 是否为空
     */
    public static
    boolean isEmptyIp(String ip) {
        return (ip == null || ip.length() == 0 || ip.trim().equals("") ||
                "unknown".equalsIgnoreCase(ip));
    }

    /**
     * 判断ip是否不为空
     *
     * @param ip 当前的 ip 地址
     *
     * @return 是否不为空
     */
    public static
    boolean isNotEmptyIp(String ip) {return !isEmptyIp(ip);}

    public static
    InetAddress getLocalHostLANAddress() {
        try {
            InetAddress candidateAddress = null;
            // 遍历所有的网络接口
            for ( Enumeration ifaces = NetworkInterface.getNetworkInterfaces();
                    ifaces.hasMoreElements(); ){
                NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
                // 在所有的接口下再遍历IP
                for ( Enumeration inetAddrs = iface.getInetAddresses();
                        inetAddrs.hasMoreElements(); ){
                    InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
                    if (!inetAddr.isLoopbackAddress()) {// 排除loopback类型地址
                        if (inetAddr.isSiteLocalAddress()) {
                            // 如果是site-local地址，就是它了
                            return inetAddr;
                        } else if (candidateAddress == null) {
                            // site-local类型的地址未被发现，先记录候选地址
                            candidateAddress = inetAddr;
                        }
                    }
                }
            }
            if (candidateAddress != null) {
                return candidateAddress;
            }
            // 如果没有发现 non-loopback地址.只能用最次选的方案
            InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
            return jdkSuppliedAddress;
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        return null;
    }
}
