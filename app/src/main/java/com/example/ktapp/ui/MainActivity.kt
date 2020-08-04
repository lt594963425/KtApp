package com.example.ktapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.ktapp.databinding.ActivityMainDataBinding
import android.net.Uri
import com.esri.arcgisruntime.ArcGISRuntimeEnvironment
import com.example.ktapp.R
import com.example.ktapp.data.People
import com.example.ktapp.utils.SDFileSelecteUtil
import com.example.ktapp.ui.login.LoginActivity
import com.example.ktapp.ui.start.StartActivity
import com.example.ktapp.ui.roomdata.RoomDataActivity
import com.example.ktapp.ui.welcome.WelcomeActivity
import com.example.ktapp.utils.Installation
import kotlinx.android.synthetic.main.activity_main_data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**
 *
 */
class MainActivity : AppCompatActivity() {
    val runtimeKey = "runtimelite,1000,rud5244175697,none,D7MFA0PL4S8PC2EN0171"
    var databinding: ActivityMainDataBinding? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databinding = DataBindingUtil.setContentView<ActivityMainDataBinding>(
            this,
            R.layout.activity_main_data
        )
        ArcGISRuntimeEnvironment.setLicense(runtimeKey)
        val user = People()
        user.setName("Hello")
        user.setAge("12")
        databinding?.user = user
        start_tv.setOnClickListener { startActivity(Intent(this, StartActivity::class.java)) }
        login_tv.setOnClickListener { startActivity(Intent(this, LoginActivity::class.java)) }
        room_tv.setOnClickListener { startActivity(Intent(this, RoomDataActivity::class.java)) }
        welcome_tv.setOnClickListener { startActivity(Intent(this, WelcomeActivity::class.java)) }
        gismap_tv.setOnClickListener { startActivity(Intent(this, GisMapActivity::class.java)) }

        GlobalScope.launch(Dispatchers.Unconfined) {
            Log.d("AA", "协程初始化完成，时间: " + System.currentTimeMillis())
            for (i in 1..3) {
                Log.d("AA", "协程任务1打印第$i 次，时间: " + System.currentTimeMillis())
            }
            delay(500)
            for (i in 1..3) {
                Log.d("AA", "协程任务2打印第$i 次，时间: " + System.currentTimeMillis())
            }
        }
        Log.d("AA", "hahahahahaahahahahahahh  " + System.currentTimeMillis())

        Log.e("设备ID",  Installation.id(this) +"");

    }

    // 打开系统的文件选择器
    fun selectedKmlFile() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        startActivityForResult(intent, 1101)
    }


}
