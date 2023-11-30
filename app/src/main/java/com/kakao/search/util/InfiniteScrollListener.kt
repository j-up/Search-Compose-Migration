package com.kakao.search.util

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class InfiniteScrollListener(
    private val startPage: Int
) : RecyclerView.OnScrollListener() {

    var page = startPage

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        recyclerView.adapter?.let { adapter ->
            if(adapter.itemCount <= 0) return

            val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
            val itemTotalCount = adapter.itemCount - 1

            if (!recyclerView.canScrollVertically(1) && lastVisibleItemPosition == itemTotalCount) {
                page++
                onLoadMore(page = page)
            }
        }
    }

    fun reset() {
        page = startPage
    }

    abstract fun onLoadMore(page: Int)
}