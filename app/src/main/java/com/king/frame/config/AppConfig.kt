package com.king.frame.config

import android.os.Environment

/**
 * 项目配置
 *
 * @author king
 * @date 2019-11-27 15:44
 */

object AppConfig {

    //日志文件存储路径
    val LOG_FILE_PATH = Environment.getExternalStorageDirectory().toString() + "/myframe/log/"

    //https://www.juhe.cn/docs/api/id/46
    //聚合数据申请的key
    const val JUHE_KEY = "3e2a77a6e04527ffb9c9dab25c67ec94"

    //api接口服务器地址
    const val BASE_API_URL = "http://apis.juhe.cn"

}
