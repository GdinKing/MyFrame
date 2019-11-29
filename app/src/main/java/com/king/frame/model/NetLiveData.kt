package com.king.frame.model

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo

import androidx.lifecycle.LiveData

/**
 * 网络状态信息监听类
 *
 * 演示自定义LiveData
 *
 * @author king
 * @date 2019-11-29 11:09
 */
class NetLiveData private constructor(private val mContext: Context) : LiveData<NetworkInfo>() {
    companion object {
        private var instance: NetLiveData? = null

        fun getInstance(context: Context): NetLiveData {
            if (instance == null) {
                instance = NetLiveData(context)
            }
            return instance as NetLiveData
        }
    }

    private var netReceiver:NetStatusReceiver? =null
    private var intentFilter:IntentFilter?=null

    init {
        netReceiver = NetStatusReceiver()
        intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
    }


    override fun onActive() {
        super.onActive()
        //注册监听
        mContext.registerReceiver(netReceiver,intentFilter)
    }

    override fun onInactive() {
        super.onInactive()
        //取消注册
        mContext.unregisterReceiver(netReceiver)
    }

    //网络状态变化监听类
    class NetStatusReceiver : BroadcastReceiver(){
        override fun onReceive(context: Context, intent: Intent?) {
            val connectivityManager = context.getSystemService(
                    Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            if(networkInfo!=null){ //断网时可能为空
                getInstance(context).value = networkInfo
            }
        }
    }
}
