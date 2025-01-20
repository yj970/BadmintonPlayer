package com.yj.badmintonplayer.ui.utils

import com.yj.badmintonplayer.ui.bean.IPInfoBean
import java.net.Inet4Address
import java.net.NetworkInterface
import java.net.SocketException
import kotlin.math.pow

object IPUtil {


    /**
     * 获取ip地址、子网掩码、广播地址
     */
    fun getIpInfoList(): List<IPInfoBean> {
        val ipInfoBeans: MutableList<IPInfoBean> = ArrayList<IPInfoBean>()
        try {
            val enumNetworkInterfaces = NetworkInterface.getNetworkInterfaces()
            while (enumNetworkInterfaces.hasMoreElements()) {
                val networkInterface = enumNetworkInterfaces.nextElement()
                val list = networkInterface.interfaceAddresses
                for (interfaceAddress in list) {
                    val inetAddress = interfaceAddress.address
                    if (inetAddress is Inet4Address && inetAddress.isSiteLocalAddress()) {
                        // ip地址
                        val ip = inetAddress.getHostAddress()
                        if (ip != null) {
                            // 子网掩码（仅仅处理ipv4）
                            //获取掩码位数，通过 calcMaskByPrefixLength 转换为字符串
                            val mask =
                                calcMaskByPrefixLength(interfaceAddress.networkPrefixLength.toInt())
                            // 广播地址
                            val broadcastAddress = getBroadcastAddress(mask, ip)
                            // 新增
                            ipInfoBeans.add(IPInfoBean(ip, mask, broadcastAddress))
                        }
                    }
                }
            }
        } catch (e: SocketException) {
            e.printStackTrace()
        }
        return ipInfoBeans
    }

    /*通过子网掩码的位数计算子网掩码*/
    private fun calcMaskByPrefixLength(length: Int): String {
        val mask = -0x1 shl (32 - length)
        val partsNum = 4
        val bitsOfPart = 8
        val maskParts = IntArray(partsNum)
        val selector = 0x000000ff

        for (i in maskParts.indices) {
            val pos = maskParts.size - 1 - i
            maskParts[pos] = (mask shr (i * bitsOfPart)) and selector
        }

        var result = ""
        result = result + maskParts[0]

        for (i in 1 until maskParts.size) {
            result = result + "." + maskParts[i]
        }
        return result
    }

    /**
     * subnet为子网掩码
     * 获取广播地址
     */
    private fun getBroadcastAddress(subnet: String, ip: String): String {
        val ips = ip.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()
        val subnets = subnet.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()
        val sb = StringBuffer()
        for (i in ips.indices) {
            ips[i] = ((subnets[i].toInt().inv())
                    or (ips[i].toInt())).toString()
            sb.append(turnToStr(ips[i].toInt()))
            if (i != (ips.size - 1)) sb.append(".")
        }
        return turnToIp(sb.toString())
    }

    private fun turnToStr(num: Int): String {
        var str = ""
        str = Integer.toBinaryString(num)
        val len = 8 - str.length
        for (i in 0 until len) {
            str = "0$str"
        }
        if (len < 0) str = str.substring(24, 32)
        return str
    }

    /**
     * 转换成Str
     */
    private fun turnToIp(str: String): String {
        val ips = str.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()
        val sb = StringBuffer()
        for (i in ips.indices) {
            sb.append(turnToInt(ips[i]))
            sb.append(".")
        }
        sb.deleteCharAt(sb.length - 1)
        return sb.toString()
    }

    /**
     * 转换成int
     */
    private fun turnToInt(str: String): Int {
        var total = 0
        var top = str.length
        for (i in 0 until str.length) {
            val h = str[i].toString()
            top--
            total += (2.0.pow(top.toDouble()).toInt()) * (h.toInt())
        }
        return total
    }
}