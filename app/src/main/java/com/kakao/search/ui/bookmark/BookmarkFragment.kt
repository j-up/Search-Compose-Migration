package com.kakao.search.ui.bookmark

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import com.kakao.search.R
import com.kakao.search.base.BaseFragment
import com.kakao.search.databinding.FragmentBookmarkBinding
import com.kakao.search.ui.bookmark.adapter.BookmarkListAdapter
import com.kakao.search.ui.bookmark.adapter.BookmarkPresentation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarkFragment : BaseFragment<FragmentBookmarkBinding>(R.layout.fragment_bookmark) {

    private val viewModel: BookmarkViewModel by viewModels()
    private val bookmarkListAdapter: BookmarkListAdapter by lazy { BookmarkListAdapter() }
    private val bookmarkRemoveList: MutableList<String> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        initUi()
        initObservers()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        setFragmentResult(FRAGMENT_RESULT_KEY, bundleOf(FRAGMENT_RESULT_ITEM_KEY to bookmarkRemoveList))
    }

    private fun initUi() {
        binding.apply {
            rvImage.apply {
                adapter = bookmarkListAdapter
                bookmarkListAdapter.bookmarkClickListener = {
                    viewModel.deleteBookmark(it.thumbnail)
                    bookmarkRemoveList.add(it.thumbnail)
                }
            }
        }
    }


    private fun initObservers() {
        viewModel.bookmarkDataStore.bookmarkFlow.asLiveData().observe(viewLifecycleOwner) { bookmarkMap ->
            val list = bookmarkMap.filter {
                it.value
            }.map {
                BookmarkPresentation.ImagePresent(it.key)
            }

            bookmarkListAdapter.submitList(list.reversed())
        }
    }

    companion object {
        const val FRAGMENT_RESULT_KEY = "resultKey"
        const val FRAGMENT_RESULT_ITEM_KEY = "itemKey"
    }
}