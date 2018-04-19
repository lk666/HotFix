package com.cn.lk.androidexp

import android.content.Intent
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import lk.cn.com.hotfix.R


class ItemAdapter :
        BaseQuickAdapter<ActivityItem, BaseViewHolder>(R.layout.item_main) {
    override fun convert(helper: BaseViewHolder, item: ActivityItem?) {
        item ?: return

        helper.setText(R.id.btn, item.name)
        helper.getView<View>(R.id.btn).setOnClickListener {
            val i = Intent(mContext, item.cls)
            mContext.startActivity(i)
        }
    }

}