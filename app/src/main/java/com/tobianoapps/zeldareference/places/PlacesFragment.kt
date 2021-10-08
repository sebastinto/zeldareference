package com.tobianoapps.zeldareference.places

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tobianoapps.zeldareference.databinding.FragmentPlacesBinding
import com.tobianoapps.zeldareference.util.Extensions.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlacesFragment : Fragment() {

    // View Binding
    private var binding by autoCleared<FragmentPlacesBinding>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlacesBinding.inflate(inflater, container, false)
        return binding.root
    }
}
