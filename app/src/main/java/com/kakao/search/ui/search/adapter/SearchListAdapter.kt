package com.kakao.search.ui.search.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.kakao.search.R
import com.kakao.search.domain.model.remote.KakaoImage
import com.kakao.search.ext.getDataBinding

class SearchListAdapter: ListAdapter<SearchPresentation, SearchViewHolder<SearchPresentation>>(
    ItemDiffCallback()
) {
    var bookmarkClickListener: ((SearchPresentation.ImagePresent) -> (Unit))? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder<SearchPresentation> {
        return when(SearchPresentation.Type.values()[viewType]) {
            SearchPresentation.Type.Image -> SearchViewHolder.ImageSearchViewHolder(
                getDataBinding(
                    parent,
                    R.layout.item_image
                ),
                bookmarkClickListener
            )
            SearchPresentation.Type.PageNumber -> SearchViewHolder.PageNumberViewHolder(
                getDataBinding(
                    parent,
                    R.layout.item_page_number
                )
            )
        }
    }

    override fun onBindViewHolder(holder: SearchViewHolder<SearchPresentation>, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)) {
            is SearchPresentation.ImagePresent -> SearchPresentation.Type.Image.ordinal
            is SearchPresentation.PageNumberPresent -> SearchPresentation.Type.PageNumber.ordinal
            else -> 0
        }
    }

    fun updateBookmark(kakaoImage: KakaoImage) {
        val list = currentList.map { present ->
            when {
                present is SearchPresentation.ImagePresent && present.kakaoImage == kakaoImage -> {
                    present.copy(kakaoImage.copy(isBookmark = !kakaoImage.isBookmark))
                }

                else -> present
            }
        }

        submitList(list)
    }

    fun unBookmark(thumbnailList: List<String>) {
        val list = currentList.map { present ->
            when {
                present is SearchPresentation.ImagePresent && thumbnailList.contains(present.kakaoImage.thumbnail) -> {
                    present.copy(present.kakaoImage.copy(isBookmark = false))
                }

                else -> present
            }
        }

        submitList(list)
    }

    private class ItemDiffCallback: DiffUtil.ItemCallback<SearchPresentation>() {
        override fun areItemsTheSame(oldItem: SearchPresentation, newItem: SearchPresentation): Boolean {

            return when {
                oldItem is SearchPresentation.ImagePresent && newItem is SearchPresentation.ImagePresent -> {
                            oldItem.kakaoImage.thumbnail == newItem.kakaoImage.thumbnail
                            && oldItem.kakaoImage.isBookmark == newItem.kakaoImage.isBookmark
                }

                oldItem is SearchPresentation.PageNumberPresent && newItem is SearchPresentation.PageNumberPresent -> {
                            oldItem.page == newItem.page
                }

                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: SearchPresentation, newItem: SearchPresentation): Boolean {
            return oldItem == newItem
        }
    }
}