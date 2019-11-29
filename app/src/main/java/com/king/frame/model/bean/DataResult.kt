package com.king.frame.model.bean

/**
 * 封装的网络请求结果实体类
 *
 * @author king
 * @date 2019-11-29 09:53
 */
class DataResult(val resultCode:Int,val result:String?,val error:Exception? = null) {
}