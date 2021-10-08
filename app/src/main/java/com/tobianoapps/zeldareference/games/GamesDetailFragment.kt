package com.tobianoapps.zeldareference.games

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.tobianoapps.zeldareference.R
import com.tobianoapps.zeldareference.databinding.FragmentGamesDetailBinding

class GamesDetailFragment : Fragment() {

    private val args: GamesDetailFragmentArgs by navArgs()

    private var _binding: FragmentGamesDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate binding
        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_games_detail,
            container,
            false
        )

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
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
