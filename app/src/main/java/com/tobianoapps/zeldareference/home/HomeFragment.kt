package com.tobianoapps.zeldareference.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tobianoapps.zeldareference.databinding.FragmentHomeBinding
import com.tobianoapps.zeldareference.util.Extensions.autoCleared


class HomeFragment : Fragment() {

    // View Binding
    private var binding by autoCleared<FragmentHomeBinding>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

}
