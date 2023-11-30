package com.kakao.search.screens.search.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.kakao.search.databinding.ItemImageBinding
import com.kakao.search.databinding.ItemPageNumberBinding

abstract class SearchViewHolder<out MODEL : SearchPresentation>(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(model: @UnsafeVariance MODEL)

    class ImageSearchViewHolder(
        private val binding: ItemImageBinding,
        private val bookmarkClickListener: ((SearchPresentation.ImagePresent) -> (Unit))? = null
    ) :
        SearchViewHolder<SearchPresentation.ImagePresent>(binding) {

        override fun bind(model: SearchPresentation.ImagePresent) {
            binding.apply {
                item = model.kakaoImage
                ibBookmark.setOnClickListener {
                    bookmarkClickListener?.invoke(model)
                }
            }
        }
    }

    class PageNumberViewHolder(private val binding: ItemPageNumberBinding) :
        SearchViewHolder<SearchPresentation.PageNumberPresent>(binding) {
        override fun bind(model: SearchPresentation.PageNumberPresent) {
            binding.page = model.page
        }
    }

}