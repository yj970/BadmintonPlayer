package com.yj.badmintonplayer.ui.utils

import android.util.Log
import com.yj.badmintonplayer.ui.bean.BroadcastBean
import com.yj.badmintonplayer.ui.bean.GameBean
import java.io.IOException
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.util.Timer
import java.util.TimerTask

class UDPUtil {
    val TAG = "TAG123"
    private val BROADCAST_PORT = 12345
    private val BUFFER_SIZE = 1024 * 1024
    private lateinit var socket: DatagramSocket
    var timer: Timer? = null
    private var stopReceiver = false

    // 创建socket
    init {
        try {
            // 创建 DatagramSocket
            socket = DatagramSocket(BROADCAST_PORT)
            // 允许发送广播
            socket.broadcast = true
        } catch (e: IOException) {
//            Log.e(TAG, "Error creating socket: ${e.message}")
        }
    }

    fun startHeartbeatBroadcastTask(gameBean: GameBean) {
        timer?.cancel()
        timer = Timer()
        timer?.schedule(object : TimerTask() {
            override fun run() {
                startHeartbeatBroadcastTask(
                    BroadcastBean(
                        gameBean,
                        BroadcastBean.BroadcastType.TYPE_HEART_BEAT
                    )
                )
            }
        }, 0, 1000)
    }

    fun stopStartBroadcast() {
        timer?.cancel()
    }

    // 发送心跳广播
    private fun startHeartbeatBroadcastTask(
        broadcastBean: BroadcastBean
    ) {
        val broadcastThread = Thread(Runnable {
            try {
                val list = IPUtil.getIpInfoList()
                for (ip in list) {
                    val sendPacket = getDatagramPacket(broadcastBean, ip.getBroadcastAddress())
                    // 发送广播消息
                    socket.send(sendPacket)
                    Log.d(TAG, "发送心跳。。。")
                }
            } catch (e: IOException) {
//                Log.e(TAG, "Error broadcasting: ${e.message}")
            }
        })
        broadcastThread.start()
    }

    private fun getDatagramPacket(
        broadcastBean: BroadcastBean,
        broadcastAddress: String
    ): DatagramPacket {
        val msg = JsonUtil.toJson(broadcastBean)
        val broadcastMessage = msg.toByteArray()
        val broadcastAddress = InetAddress.getByName(broadcastAddress)
        val sendPacket = DatagramPacket(
            broadcastMessage,
            broadcastMessage.size,
            broadcastAddress,
            BROADCAST_PORT
        )
        return sendPacket
    }

    // 发送房主广播
//    fun sendRoomerBroadcast(gameBean: GameBean) {
//        // 因为UDP不可靠，所以一次发送3条
//        for (i in 0 until 3) {
//            val broadcastThread = Thread(Runnable {
//                try {
//                    val list = IPUtil.getIpInfoList()
//                    val broadcastBean =
//                        BroadcastBean(gameBean, BroadcastBean.BroadcastType.TYPE_ROOMER_BROADCAST)
//                    for (ip in  list) {
//                        val sendPacket = getDatagramPacket(broadcastBean, ip.getBroadcastAddress())
//                        // 发送广播消息
//                        socket.send(sendPacket)
//                        Log.d(TAG, "房主广播。。。")
//                    }
//                } catch (e: IOException) {
//                }
//            })
//            broadcastThread.start()
//        }
//    }


    // 接收广播
    fun startReceive(listener: IReceiverListener) {
        val receiveThread = Thread(Runnable {
            val buffer = ByteArray(BUFFER_SIZE)
            val packet = DatagramPacket(buffer, buffer.size)
            while (!stopReceiver) {
                try {
                    socket.receive(packet)
                    val receivedMessage = String(packet.data, 0, packet.length)
                    var receivedBroadcast: BroadcastBean? = null
                    try {
                        receivedBroadcast = JsonUtil.fromJson<BroadcastBean>(receivedMessage)
                    } catch (e: Exception) {
                    }
                    if (receivedBroadcast != null) {
                        listener.onReceiver(receivedBroadcast, packet.address)
                    }

                    // 只打印房主广播
                    // todo test
                    Log.d(TAG, "接收到消息: $receivedMessage")
                } catch (e: IOException) {
//                    Log.e(TAG, "失败")
                    break
                }
            }
        })
        receiveThread.start()
    }

    interface IReceiverListener {
        fun onReceiver(broadcastBean: BroadcastBean, address: InetAddress)
    }

    interface IReceiverListener2 {
        fun onReceiver(broadcastBean: BroadcastBean)
    }

    fun stopReceive() {
        stopReceiver = true
    }

    fun release() {
        if (socket != null) {
            socket.close()
        }
    }


    // 回复广播
    fun guessReplyToRoomer(address: InetAddress, gameBean: GameBean) {
        val thread = Thread(Runnable {
            val broadcastBean =
                BroadcastBean(gameBean, BroadcastBean.BroadcastType.TYPE_GUEST_REPLAY)
            val msg = JsonUtil.toJson(broadcastBean)
            val replyMessage = msg.toByteArray()
            val replyPacket = DatagramPacket(
                replyMessage,
                replyMessage.size,
                address,
                BROADCAST_PORT
            )
            try {
                socket.send(replyPacket)
//                Log.d(TAG, "回复广播:"+msg)
            } catch (e: IOException) {
//                Log.e(TAG, "回复广播失败...")
            }
        })
        thread.start()
    }
}