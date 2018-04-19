package lk.cn.com.hotfix

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.view.View
import dalvik.system.DexClassLoader
import kotlinx.android.synthetic.main.activity_modify.*
import java.io.File

/**
 * BootClassLoader是在Zygote进程的入口方法中创建的，PathClassLoader则是在Zygote进程创建SystemServer进程时创建的
 */
class AddDexActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify)
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

        tv.text = """Android中的类加载器是BootClassLoader、PathClassLoader、DexClassLoader，
            |
            |其中BootClassLoader是虚拟机加载系统类需要用到的，
            |    PathClassLoader是App加载自身dex文件中的类用到的，
            |    DexClassLoader可以加载直接或间接包含dex文件的文件，如APK等。
            |
            |PathClassLoader和DexClassLoader都继承自BaseDexClassLoader，它的一个DexPathList类型的成员变量pathList很重要。
            |DexPathList中有一个Element类型的数组dexElements，这个数组中存放了包含dex文件（对应的是DexFile）的元素。
            |
            |BaseDexClassLoader加载一个类，最后调用的是DexFile的方法进行加载的。
            |
            | activity类加载器：$s""".trimMargin()
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
        // 新dex
        val dex = File(Environment.getExternalStorageDirectory().absolutePath + File
                .separator + "Download" + File.separator + "AddMethod.dex")

        // 优化dex存储的目录（odex文件存储目录）
        val dexOptimizeDir = getDir("dex", Context.MODE_PRIVATE)
        val optPath = dexOptimizeDir.absolutePath

//        dexPath，指的是在Android包含类和资源的jar/apk类型的文件集合，指的是包含dex文件。多个文件用File.pathSeparator分隔开。
//        optimizedDirectory，指的是odex优化文件存放的路径，可以为null，那么就采用默认的系统路径。
//        libraryPath，指的是native库文件存放目录，也是以File.pathSeparator分隔。
//        parent，parent类加载器，先在父加载器中找class，找不到再用自己的加载器(双亲委托模式)
        val dexClassLoader = DexClassLoader(dex.absolutePath, optPath, null, classLoader)

        var s = ""
        try {
            val clazz = dexClassLoader.loadClass("AddMethod")
            val constructor = clazz.getConstructor()
            constructor.isAccessible = true
            val o = constructor.newInstance()
            val print = clazz.getDeclaredMethod("add", Int::class.java, Int::class.java)
            print.isAccessible = true
            var res = print.invoke(o, 1, 1)
            s += """loaded class: $clazz
                |
            | class loader: ${clazz.classLoader}
            |
            | class loader parent: ${clazz.classLoader.parent}
            |
            | 运算结果：$res""".trimMargin()
        } catch (e: Exception) {
            s += e.message
        }
        tv.text = """${tv.text}
            |================================
            |$s""".trimMargin()
    }
}
