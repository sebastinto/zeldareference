package com.tobianoapps.zeldareference.bosses.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.tobianoapps.zeldareference.R
import com.tobianoapps.zeldareference.databinding.AppearancesItemBinding
import com.tobianoapps.zeldareference.databinding.FragmentBossesDetailBinding
import com.tobianoapps.zeldareference.databinding.LoadingViewBinding
import com.tobianoapps.zeldareference.util.Extensions.autoCleared
import com.tobianoapps.zeldareference.util.Extensions.toggleLoadingView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BossesDetailFragment : Fragment() {

    private val args: BossesDetailFragmentArgs by navArgs()

    private val bossesDetailViewModel: BossesDetailViewModel by viewModels()

    private var binding by autoCleared<FragmentBossesDetailBinding>()
    private var appearancesItemBinding by autoCleared<AppearancesItemBinding>()
    private var appearancesItemLoadingView by autoCleared<LoadingViewBinding>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate bindings
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_bosses_detail,
            container,
            false
        )

        appearancesItemBinding = binding.appearancesContainer
        appearancesItemLoadingView = appearancesItemBinding.loadingViewContainer

        // Set binding properties
        binding.lifecycleOwner = viewLifecycleOwner
        binding.data = args.data

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set action bar title with game name
        activity?.findViewById<Toolbar>(R.id.toolbar)?.title = args.data.name

        subscribeToObservers()
    }

    private fun subscribeToObservers() {

        bossesDetailViewModel.appearancesResponse.observe(viewLifecycleOwner) {
            appearancesItemBinding.appearances = it.data
        }

        bossesDetailViewModel.showAppearancesLoadingView.observe(viewLifecycleOwner) {
            appearancesItemLoadingView.toggleLoadingView(it)
        }

        bossesDetailViewModel.appearancesErrorMessage.observe(viewLifecycleOwner) {
            it?.let {
                Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
