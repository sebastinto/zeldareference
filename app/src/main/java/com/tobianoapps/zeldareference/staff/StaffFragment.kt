package com.tobianoapps.zeldareference.staff

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tobianoapps.zeldareference.databinding.FragmentStaffBinding
import com.tobianoapps.zeldareference.util.Extensions.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StaffFragment : Fragment() {

    // View Binding
    private var binding by autoCleared<FragmentStaffBinding>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStaffBinding.inflate(inflater, container, false)
        return binding.root
    }
}
