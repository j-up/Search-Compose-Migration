package com.kakao.search.ext

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kakao.search.R
import com.kakao.search.util.RecyclerViewDecoration

@BindingAdapter("paddingFirstItemHeight", "dividerHeight", "dividerPadding", "dividerColor", requireAll = false)
fun RecyclerView.setDivider(paddingFirstItemHeight: Float?, dividerHeight: Float?, dividerPadding: Float?, @ColorInt dividerColor: Int?) {
    val decoration = RecyclerViewDecoration(
        paddingFirstItemHeight = paddingFirstItemHeight ?: 0f,
        height = dividerHeight ?: 0f,
        padding = dividerPadding ?: 0f,
        color = dividerColor ?: Color.TRANSPARENT
    )

    addItemDecoration(decoration)
}

inline fun <reified T: ViewDataBinding> getDataBinding(parent: ViewGroup, id: Int): T =
    DataBindingUtil.inflate(
        LayoutInflater.from(parent.context), id, parent, false
    )

@BindingAdapter("android:src")
fun ImageView.glide(url: String?) {
    Glide.with(this.context)
        .load(url ?: kotlin.run {
            ColorDrawable(
                ContextCompat.getColor(
                    this.context,
                    R.color.grey
                )
            )
        })
        .fitCenter()
        .error(ColorDrawable(ContextCompat.getColor(this.context, R.color.grey)))
        .into(this)
}
