package com.rk.parkly.ui.breakingnews

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.rk.parkly.adapter.ArticleRecyclerviewAdapter
import com.rk.parkly.databinding.FragmentBreakingNewsBinding
import com.rk.parkly.util.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "BreakingNewsFragment"

@AndroidEntryPoint
class BreakingNewsFragment : Fragment() {
    private val breakingNewsViewModel: BreakingNewsViewModel by viewModels()
    private var breakingNewsBinding: FragmentBreakingNewsBinding? = null
    private val binding get() = breakingNewsBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        breakingNewsBinding = FragmentBreakingNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val articleAdapter = ArticleRecyclerviewAdapter()
        binding.breakingNewsRecyclerview.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = articleAdapter
            // setHasFixedSize(true)
        }

        articleAdapter.addLoadStateListener { loadStates ->
            val refreshState = loadStates.refresh
            binding.progressBar.isVisible = refreshState is LoadState.Loading

        }
        
        viewLifecycleOwner.lifecycleScope.launch {
            breakingNewsViewModel.articles.collectLatest { pagingData ->
                articleAdapter.submitData(pagingData)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        breakingNewsBinding = null
    }
}
