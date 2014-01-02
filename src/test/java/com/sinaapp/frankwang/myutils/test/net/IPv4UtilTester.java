package com.sinaapp.frankwang.myutils.test.net;

import com.sinaapp.frankwang.myutils.net.IPv4Util;

public class IPv4UtilTester {

	public static void main(String[] args) {
		test01();
	}

	private static void test01() {
		String ipAddr = "192.168.8.1";

        byte[] bytearr = IPv4Util.ipToBytesByInet(ipAddr);

        StringBuffer byteStr = new StringBuffer();

        for (byte b : bytearr) {
            if (byteStr.length() == 0) {
                byteStr.append(b);
            } else {
                byteStr.append("," + b);
            }
        }
        System.out.println("IP: " + ipAddr + " ByInet --> byte[]: [ " + byteStr
                + " ]");

        bytearr = IPv4Util.ipToBytesByReg(ipAddr);
        byteStr = new StringBuffer();

        for (byte b : bytearr) {
            if (byteStr.length() == 0) {
                byteStr.append(b);
            } else {
                byteStr.append("," + b);
            }
        }
        System.out.println("IP: " + ipAddr + " ByReg  --> byte[]: [ " + byteStr
                + " ]");

        System.out.println("byte[]: " + byteStr + " --> IP: "
                + IPv4Util.bytesToIp(bytearr));

        int ipInt = IPv4Util.ipToInt(ipAddr);

        System.out.println("IP: " + ipAddr + "  --> int: " + ipInt);

        System.out.println("int: " + ipInt + " --> IP: "
                + IPv4Util.intToIp(ipInt));

        String ipAndMask = "192.168.1.1/24";

        int[] ipscope = IPv4Util.getIPIntScope(ipAndMask);
        System.out.println(ipAndMask + " --> int地址段：[ " + ipscope[0] + ","
                + ipscope[1] + " ]");

        System.out.println(ipAndMask + " --> IP 地址段：[ "
                + IPv4Util.intToIp(ipscope[0]) + ","
                + IPv4Util.intToIp(ipscope[1]) + " ]");

        String ipAddr1 = "192.168.1.1", ipMask1 = "255.255.255.0";

        int[] ipscope1 = IPv4Util.getIPIntScope(ipAddr1, ipMask1);
        System.out.println(ipAddr1 + " , " + ipMask1 + "  --> int地址段 ：[ "
                + ipscope1[0] + "," + ipscope1[1] + " ]");

        System.out.println(ipAddr1 + " , " + ipMask1 + "  --> IP地址段 ：[ "
                + IPv4Util.intToIp(ipscope1[0]) + ","
                + IPv4Util.intToIp(ipscope1[1]) + " ]");
	}

}
