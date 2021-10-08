package com.tobianoapps.zeldareference.bosses.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tobianoapps.zeldareference.R
import com.tobianoapps.zeldareference.api.models.Bosses
import com.tobianoapps.zeldareference.databinding.EmptyViewBinding
import com.tobianoapps.zeldareference.databinding.FragmentBossesBinding
import com.tobianoapps.zeldareference.databinding.LoadingViewBinding
import com.tobianoapps.zeldareference.util.Extensions.autoCleared
import com.tobianoapps.zeldareference.util.Extensions.getPath
import com.tobianoapps.zeldareference.util.Extensions.toggleLoadingView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BossesFragment : Fragment() {

    private val bossesMainViewModel: BossesMainViewModel by viewModels()

    // View Binding
    private var binding by autoCleared<FragmentBossesBinding>()
    private var loadingViewBinding by autoCleared<LoadingViewBinding>()
    private var emptyViewBinding by autoCleared<EmptyViewBinding>()

    // Recycler
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>

    /*** LIFECYCLE ***/
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate binding
        binding = FragmentBossesBinding.inflate(inflater, container, false)
        loadingViewBinding = binding.loadingViewContainer
        emptyViewBinding = binding.emptyViewContainer

        // Set binding properties
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            bvm = bossesMainViewModel
            apiPath = Bosses::class.java.getPath()
        }

        subscribeToObservers()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        recyclerAdapter = BossesAdapter(
            itemClick = BossesAdapter.ItemClick {
                it?.let {
                    findNavController().navigate(
                        BossesFragmentDirections.actionBossesFragmentToBossesDetailFragment(
                            it
                        )
                    )
                }
            }
        )
        recyclerView = binding.recycler.also {
            it.adapter = recyclerAdapter
            it.layoutManager = LinearLayoutManager(requireActivity())
        }
    }


    private fun subscribeToObservers() {

        bossesMainViewModel.sortedList.observe(viewLifecycleOwner) {
            (recyclerAdapter as BossesAdapter).differ.submitList(it)
        }

        bossesMainViewModel.showLoadingView.observe(viewLifecycleOwner) {
            loadingViewBinding.toggleLoadingView(it)
        }

        bossesMainViewModel.showEmptyView.observe(viewLifecycleOwner) {
            emptyViewBinding.toggleLoadingView(it)
        }

        bossesMainViewModel.errorMessage.observe(viewLifecycleOwner) {
            emptyViewBinding.errorSpeechBubbleText.text = requireActivity().getString(R.string.error_code_message_with_arg, it)
        }
    }
}
