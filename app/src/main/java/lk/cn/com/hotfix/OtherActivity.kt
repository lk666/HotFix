package lk.cn.com.hotfix

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class OtherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setValue()
    }

    private fun setValue() {
        tv.text = "另外的Activity"
        var s = StringBuilder("")

        // App系统类加载器是PathClassLoader，而BootClassLoader是其parent类加载器。
        var cl = classLoader
        while (cl != null) {
            s.append(cl).append("\n").append("\n")
            cl = cl.parent
        }

        File.pathSeparator

        tv.text = """${tv.text}
            |
            |$s
        """.trimMargin()
    }

    fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn -> fix()
        }
    }

    private fun fix() {
    }
}
