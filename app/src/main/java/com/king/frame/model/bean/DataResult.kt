package com.king.frame.model.bean

/**
 * 封装的网络请求结果实体类
 *
 * @author king
 * @date 2019-11-29 09:53
 */
sealed class DataResult<out T : Any> {
    //成功
    data class Success<out T : Any>(val result: T?) : DataResult<T>()
    //失败
    data class Error(val errorCode:String?,val errorMessage: String?) : DataResult<Nothing>()
}