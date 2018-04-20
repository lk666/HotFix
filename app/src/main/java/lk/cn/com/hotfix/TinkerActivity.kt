package lk.cn.com.hotfix

import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.tencent.tinker.lib.tinker.Tinker
import com.tencent.tinker.lib.tinker.TinkerInstaller
import com.tencent.tinker.loader.shareutil.ShareTinkerInternals

/**
 * BootClassLoader是在Zygote进程的入口方法中创建的，PathClassLoader则是在Zygote进程创建SystemServer进程时创建的
 */
class TinkerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tinker)
    }

    /**
     * 加载patch包
     */
    fun onLoadPatch(v: View?) {
        TinkerInstaller.onReceiveUpgradePatch(applicationContext,
                Environment.getExternalStorageDirectory().absolutePath +
                        "/Download/patch.apk")
    }

    /**
     * 删除patch包
     */
    fun onCleanPatch(v: View?) {
        Tinker.with(applicationContext).cleanPatch()
    }

    /**
     * 结束程序
     */
    fun kill(v: View?) {
        ShareTinkerInternals.killAllOtherProcess(applicationContext)
        System.exit(0) // 无效
    }

//    /**
//     * 加载so包
//     */
//    fun onLoadLib() {
//    // #method 1, hack classloader library path
//    TinkerLoadLibrary.installNavitveLibraryABI(getApplicationContext(), "armeabi")
//    System.loadLibrary("stlport_shared")
//
//    // #method 2, for lib/armeabi, just use TinkerInstaller.loadLibrary
////                TinkerLoadLibrary.loadArmLibrary(getApplicationContext(), "stlport_shared");
//
//    // #method 3, load tinker patch library directly
////                TinkerInstaller.loadLibraryFromTinker(getApplicationContext(), "assets/x86", "stlport_shared");

//    }
}
