package com.tobianoapps.zeldareference.monsters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tobianoapps.zeldareference.databinding.FragmentMonstersBinding
import com.tobianoapps.zeldareference.util.Extensions.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MonsterFragment : Fragment() {

    // View Binding
    private var binding by autoCleared<FragmentMonstersBinding>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMonstersBinding.inflate(inflater, container, false)
        return binding.root
    }
}
