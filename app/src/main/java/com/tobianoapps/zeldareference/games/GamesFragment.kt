package com.tobianoapps.zeldareference.games

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tobianoapps.zeldareference.R
import com.tobianoapps.zeldareference.api.models.Games
import com.tobianoapps.zeldareference.databinding.EmptyViewBinding
import com.tobianoapps.zeldareference.databinding.FragmentGamesBinding
import com.tobianoapps.zeldareference.databinding.LoadingViewBinding
import com.tobianoapps.zeldareference.util.Extensions.autoCleared
import com.tobianoapps.zeldareference.util.Extensions.getPath
import com.tobianoapps.zeldareference.util.Extensions.toggleLoadingView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GamesFragment @JvmOverloads constructor(
    viewModelStoreOwner: ViewModelStoreOwner? = null
) : Fragment() {

    private val gamesViewModel: GamesViewModel by viewModels(
        { viewModelStoreOwner ?: this }
    )

    // View Binding
    private var binding by autoCleared<FragmentGamesBinding>()
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
        binding = FragmentGamesBinding.inflate(inflater, container, false)
        loadingViewBinding = binding.loadingViewContainer
        emptyViewBinding = binding.emptyViewContainer

        // Set binding properties
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            gvm = gamesViewModel
            bvm = gamesViewModel
            apiPath = Games::class.java.getPath()
        }

        subscribeToObservers()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        recyclerAdapter = GamesAdapter(
            itemClick = GamesAdapter.ItemClick {
                it?.let {
                    findNavController().navigate(
                        GamesFragmentDirections.actionGamesFragmentToGamesDetailFragment(
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

        gamesViewModel.sortedList.observe(viewLifecycleOwner) {
            (recyclerAdapter as GamesAdapter).differ.submitList(it)
        }

        gamesViewModel.showLoadingView.observe(viewLifecycleOwner) {
            loadingViewBinding.toggleLoadingView(it)
        }

        gamesViewModel.showEmptyView.observe(viewLifecycleOwner) {
            emptyViewBinding.toggleLoadingView(it)
        }

        gamesViewModel.errorMessage.observe(viewLifecycleOwner) {
            emptyViewBinding.errorSpeechBubbleText.text = requireActivity().getString(R.string.error_code_message_with_arg, it)
        }
    }
}
