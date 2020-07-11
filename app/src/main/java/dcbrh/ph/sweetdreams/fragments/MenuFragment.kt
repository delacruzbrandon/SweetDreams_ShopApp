package dcbrh.ph.sweetdreams.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import dcbrh.ph.sweetdreams.R
import dcbrh.ph.sweetdreams.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {

    private lateinit var binding: FragmentMenuBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_menu, container, false)

        return binding.root
    }
}