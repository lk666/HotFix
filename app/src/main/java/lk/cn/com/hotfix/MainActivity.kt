package lk.cn.com.hotfix

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.view.View
import dalvik.system.DexClassLoader
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setValue()
    }

    private fun setValue() {
        var s = StringBuilder("")

        // App系统类加载器是PathClassLoader，而BootClassLoader是其parent类加载器。
        var cl = classLoader
        while (cl != null) {
            s.append(cl).append("\n").append("\n")
            cl = cl.parent
        }

        File.pathSeparator


        tv.text = """错误的信息:1+1=3
            |
            |$s
        """.trimMargin()
    }

    fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn -> fix()
        }
    }

    /** Android中加载一个类是遍历PathDexList的Element[]数组，
     *  这个Element包含了DexFile，调用DexFile的方法来获取Class文件，
     *  如果获取到了Class，就跳出循环。否则就在下一个Element中寻找Class。
     */
    private fun fix() {
//        tv.text = "1+1=2"
        // 加载新的类

        // 新dex
        val dex = File(Environment.getExternalStorageDirectory().absolutePath + File
                .separator + "Download")

        // 优化dex存储的目录（odex文件存储目录）
        val dexOptimizeDir = getDir("dex", Context.MODE_PRIVATE)
        val optPath = dexOptimizeDir.absolutePath

//        dexPath，指的是在Android包含类和资源的jar/apk类型的文件集合，指的是包含dex文件。多个文件用File.pathSeparator分隔开。
//        optimizedDirectory，指的是odex优化文件存放的路径，可以为null，那么就采用默认的系统路径。
//        libraryPath，指的是native库文件存放目录，也是以File.pathSeparator分隔。
//        parent，parent类加载器，先在父加载器中找class，找不到再用自己的加载器
        File.pathSeparator
        val dexClassLoader = DexClassLoader(dex.absolutePath, optPath, null, classLoader)

        var s = ""
        try {
            val clazz = dexClassLoader.loadClass("AddMethod")
            s +="""loaded class: $clazz
            | class loader:  + ${clazz.classLoader}
            | class loader parent:  + ${clazz.classLoader.parent}""".trimMargin()

            val constructor = clazz.getConstructor()
            constructor.isAccessible = true
            val o = constructor.newInstance()
            val print = clazz.getDeclaredMethod("print")
            print.isAccessible = true
            print.invoke(o)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


}
