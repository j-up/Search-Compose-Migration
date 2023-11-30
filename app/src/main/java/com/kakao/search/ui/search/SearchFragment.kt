package com.kakao.search.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kakao.search.R
import com.kakao.search.base.BaseFragment
import com.kakao.search.databinding.FragmentSearchBinding
import com.kakao.search.domain.model.remote.KakaoImage
import com.kakao.search.ui.bookmark.BookmarkFragment
import com.kakao.search.ui.search.adapter.SearchListAdapter
import com.kakao.search.ui.search.adapter.SearchPresentation
import com.kakao.search.util.InfiniteScrollListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private val viewModel: SearchViewModel by viewModels()
    private val searchListAdapter: SearchListAdapter by lazy { SearchListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        initUi()
        initObservers()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFragmentResultListener(BookmarkFragment.FRAGMENT_RESULT_KEY) { _, result ->
            val list = result.getStringArrayList(BookmarkFragment.FRAGMENT_RESULT_ITEM_KEY)

            if(!list.isNullOrEmpty()) {
                searchListAdapter.unBookmark(list)
            }
        }
    }

    private fun initUi() {
        binding.apply {
            etSearch.setOnEditorActionListener { textView, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    scrollListener.reset()
                    searchListAdapter.submitList(null)
                    viewModel.stateClear()

                    val query = textView.text.toString()

                    when (query.isEmpty()) {
                        true -> {
                            Toast.makeText(requireContext(), "검색어를 입력하세요", Toast.LENGTH_SHORT).show()
                        }

                        false -> {
                            viewModel.fetchMediaThumbnails(query, START_PAGE)
                        }
                    }
                }

                false
            }

            searchListAdapter.bookmarkClickListener = {
                searchListAdapter.updateBookmark(it.kakaoImage)
                viewModel.updateBookmark(it.kakaoImage)
            }

            rvImage.apply {
                adapter = searchListAdapter
                addOnScrollListener(scrollListener)
            }

            btBookmark.setOnClickListener {
                findNavController().navigate(R.id.to_bookmarkFragment)
            }
        }
    }


    private fun initObservers() {
        viewModel.searchStateLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                SearchState.OnClear -> {

                }

                is SearchState.OnImageListLoad -> {
                    when(state.list.isEmpty()) {
                        true -> {
                            Toast.makeText(requireContext(), "결과 없음", Toast.LENGTH_SHORT).show()
                        }

                        false -> {
                            searchListAdapter.submitList(state.list)
                        }
                    }
                }

                SearchState.OnFail -> {
                    Toast.makeText(requireContext(), "에러 발생", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private val scrollListener = object : InfiniteScrollListener(START_PAGE) {
        override fun onLoadMore(page: Int) {
            viewModel.fetchMediaThumbnails(binding.etSearch.text.toString(), page)
        }
    }

    companion object {
        const val START_PAGE = 1
    }
}