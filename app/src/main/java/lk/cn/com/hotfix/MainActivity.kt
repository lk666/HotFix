package lk.cn.com.hotfix

import com.cn.lk.androidexp.ActivityItem

/**
 * BootClassLoader是在Zygote进程的入口方法中创建的，PathClassLoader则是在Zygote进程创建SystemServer进程时创建的
 */
class MainActivity : SelectBaseActivity(listOf(ActivityItem("加载外部dex方法", AddDexActivity::class.java)))