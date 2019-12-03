package com.king.frame.ui.activity

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.provider.Settings

import com.king.frame.BaseApplication
import com.king.frame.R
import com.king.frame.base.BaseActivity

import java.util.ArrayList

import androidx.appcompat.app.AlertDialog
import me.weyye.hipermission.HiPermission
import me.weyye.hipermission.PermissionCallback
import me.weyye.hipermission.PermissionItem

/**
 * 欢迎页
 *
 * @author king
 * @time 2019-11-27 09:23
 */
class SplashActivity : BaseActivity() {

    private val handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            if (msg.what == 100) {
                openMainPage()
            }

        }
    }

    override val contentView: Int
        get() = R.layout.activity_splash

    override fun onCreate(savedInstanceState: Bundle?) {
        this.isFullScreen = true
        super.onCreate(savedInstanceState)
        setContentView(contentView)
        initView()
        initData()
    }

    override fun initView() {

    }


    override fun initData() {
        //权限申请
        requestPermissions()
    }


    /**
     * 打开主界面
     */
    private fun openMainPage() {
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    /**
     * 申请权限
     */
    private fun requestPermissions() {
        val permissionItems = ArrayList<PermissionItem>()
        permissionItems.add(PermissionItem(Manifest.permission.WRITE_EXTERNAL_STORAGE, "存储", R.drawable.permission_ic_storage))
        HiPermission.create(this@SplashActivity)
                .permissions(permissionItems)
                .checkMutiPermission(object : PermissionCallback {
                    override fun onClose() {}

                    override fun onFinish() {

                        handler.sendEmptyMessageDelayed(100, 1500)//直接跳转
                    }

                    override fun onDeny(permission: String, position: Int) {
                        showPermissionDialog()
                    }

                    override fun onGuarantee(permission: String, position: Int) {

                    }
                })
    }


    /**
     * 某一权限没有被授予，弹出提示框
     */
    private fun showPermissionDialog() {
        val builder = AlertDialog.Builder(this)
                .setTitle("温馨提示")
                .setMessage("请在设置中开启所需权限，以正常使用app功能")
                .setNeutralButton("取消") { dialog, which ->
                    dialog.dismiss()
                    finish()
                }
                .setNegativeButton("去设置") { dialog, which ->
                    dialog.dismiss()
                    launchAppSetting()
                    finish()
                }

        val dialog = builder.show()
        dialog.setCanceledOnTouchOutside(false)
    }

    override fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }

    companion object {

        fun launchAppSetting() {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.data = Uri.parse("package:" + BaseApplication.instance!!.packageName)
            BaseApplication.instance!!.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }
    }

}
