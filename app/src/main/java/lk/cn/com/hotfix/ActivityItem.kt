package com.cn.lk.androidexp

import java.io.Serializable

public class ActivityItem(public var name: String = "", public var cls: Class<*>? = null) :
        Serializable {
    public var SSS: Int = 0
        get() = field
        protected set(value) {
            field = value
        }

}