package com.tobianoapps.zeldareference.dungeons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tobianoapps.zeldareference.databinding.FragmentDungeonsBinding
import com.tobianoapps.zeldareference.util.Extensions.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DungeonsFragment : Fragment() {

    // View Binding
    private var binding by autoCleared<FragmentDungeonsBinding>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDungeonsBinding.inflate(inflater, container, false)
        return binding.root
    }
}
