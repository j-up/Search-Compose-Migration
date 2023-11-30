package com.kakao.search.ui.bookmark.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.kakao.search.databinding.ItemBookmarkBinding

abstract class BookmarkViewHolder<out MODEL : BookmarkPresentation>(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(model: @UnsafeVariance MODEL)

    class ImageBookmarkViewHolder(
        private val binding: ItemBookmarkBinding,
        private val bookmarkClickListener: ((BookmarkPresentation.ImagePresent) -> (Unit))? = null
    ) : BookmarkViewHolder<BookmarkPresentation.ImagePresent>(binding) {

        override fun bind(model: BookmarkPresentation.ImagePresent) {
            binding.apply {
                thumbnail = model.thumbnail
                ibBookmark.setOnClickListener {
                    bookmarkClickListener?.invoke(model)
                }
            }
        }
    }

}