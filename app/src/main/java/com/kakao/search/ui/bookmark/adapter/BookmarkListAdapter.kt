package com.kakao.search.ui.bookmark.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.kakao.search.R
import com.kakao.search.ext.getDataBinding

class BookmarkListAdapter: ListAdapter<BookmarkPresentation, BookmarkViewHolder<BookmarkPresentation>>(
    ItemDiffCallback()
) {
    var bookmarkClickListener: ((BookmarkPresentation.ImagePresent) -> (Unit))? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder<BookmarkPresentation> {
        return when(BookmarkPresentation.Type.values()[viewType]) {
            BookmarkPresentation.Type.Image -> BookmarkViewHolder.ImageBookmarkViewHolder(
                getDataBinding(
                    parent,
                    R.layout.item_bookmark
                ),
                bookmarkClickListener
            )
        }
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder<BookmarkPresentation>, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)) {
            is BookmarkPresentation.ImagePresent -> BookmarkPresentation.Type.Image.ordinal
            else -> 0
        }
    }

    private class ItemDiffCallback: DiffUtil.ItemCallback<BookmarkPresentation>() {

        override fun areItemsTheSame(oldItem: BookmarkPresentation, newItem: BookmarkPresentation): Boolean {
            return when {
                oldItem is BookmarkPresentation.ImagePresent && newItem is BookmarkPresentation.ImagePresent -> {
                    oldItem.thumbnail == newItem.thumbnail
                }

                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: BookmarkPresentation, newItem: BookmarkPresentation): Boolean {
            return oldItem == newItem
        }
    }
}