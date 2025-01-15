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
                val msg = JsonUtil.toJson(broadcastBean)
                val broadcastMessage = msg.toByteArray()
                val broadcastAddress = InetAddress.getByName("255.255.255.255")
                val sendPacket = DatagramPacket(
                    broadcastMessage,
                    broadcastMessage.size,
                    broadcastAddress,
                    BROADCAST_PORT
                )
                // 发送广播消息
                socket.send(sendPacket)
                Log.d(TAG, "Sent broadcast message=" + msg)
//
//                val buffer = ByteArray(BUFFER_SIZE)
//                val receivePacket = DatagramPacket(buffer, buffer.size)
//                // 接收广播回复
//                socket.receive(receivePacket)
//                val receivedMessage = String(receivePacket.data, 0, receivePacket.length)
//                var receivedBroadcastBean: BroadcastBean? = null
//                try {
//                    receivedBroadcastBean = JsonUtil.fromJson<BroadcastBean>(receivedMessage)
//                } catch (e: Exception) {
//                }
//                if (receivedBroadcastBean != null) {
//                    // 只接受客人发送的广播
//                    if (receivedBroadcastBean.type == BroadcastBean.BroadcastType.TYPE_GUEST_REPLAY) {
//                        Log.d(TAG, "Received reply: $receivedMessage")
//                        listener.onReceiver(receivedBroadcastBean)
//                    }
//                }
            } catch (e: IOException) {
//                Log.e(TAG, "Error broadcasting: ${e.message}")
            }
        })
        broadcastThread.start()
    }

    // 发送房主广播
    fun sendRoomerBroadcast(gameBean: GameBean) {
        // 因为UDP不可靠，所以一次发送3条
        for (i in 0 until 3) {
            val broadcastThread = Thread(Runnable {
                try {
                    val broadcastBean =
                        BroadcastBean(gameBean, BroadcastBean.BroadcastType.TYPE_ROOMER_BROADCAST)
                    val msg = JsonUtil.toJson(broadcastBean)
                    val broadcastMessage = msg.toByteArray()
                    val broadcastAddress = InetAddress.getByName("255.255.255.255")
                    val sendPacket = DatagramPacket(
                        broadcastMessage,
                        broadcastMessage.size,
                        broadcastAddress,
                        BROADCAST_PORT
                    )
                    // 发送广播消息
                    socket.send(sendPacket)
                } catch (e: IOException) {
                }
            })
            broadcastThread.start()
        }
    }


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
//                    Log.d(TAG, "startReceive: $receivedMessage")
                } catch (e: IOException) {
//                    Log.e(TAG, "Error receiving packet: ${e.message}")
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
//                Log.d(TAG, "Sent reply to broadcast")
            } catch (e: IOException) {
//                Log.e(TAG, "Error sending reply: ${e.message}")
            }
        })
        thread.start()
    }
}