package com.yj.badmintonplayer.ui.bean

class IPInfoBean {
    private var ip: String? = null // ip地址
    private var mask: String? = null // 子网掩码
    private var broadcastAddress: String = "" // 广播地址

     constructor(ip: String?, mask: String?, broadcastAddress: String) {
        this.ip = ip
        this.mask = mask
        this.broadcastAddress = broadcastAddress
    }

    fun getIp(): String? {
        return ip
    }

    fun getMask(): String? {
        return mask
    }

    fun getBroadcastAddress(): String {
        return broadcastAddress
    }

    override fun toString(): String {
        return "IPInfoBean{" +
                "ip='" + ip!!.replace(".", "。") + '\'' +
                ", mask='" + mask!!.replace(".", "。") + '\'' +
                ", broadcastAddress='" + broadcastAddress!!.replace(".", "。") + '\'' +
                '}'
    }
}