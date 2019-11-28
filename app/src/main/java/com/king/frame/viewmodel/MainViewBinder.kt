package com.king.frame.viewmodel

/**
 * MainActivity的UI操作接口
 *
 * @author king
 * @date 2019-11-27 11:56
 */
interface MainViewBinder : IViewBinder {

    fun showProgress() //显示进度条

    fun hideProgress() //隐藏进度条

    fun showBookData(response: String?, error: Exception?) //请求成功
}
