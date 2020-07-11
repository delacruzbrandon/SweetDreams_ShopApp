package dcbrh.ph.sweetdreams.adapter

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import dcbrh.ph.sweetdreams.R
import dcbrh.ph.sweetdreams.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {

    private lateinit var binding: FragmentFirstBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_first, container, false)

        cardView()

        return binding.root
    }

    private fun cardView() {
        val adapterReference = GroupAdapter<GroupieViewHolder>()
        binding.fragmentToolsRepeatGridRecyclerView.adapter = adapterReference

            adapterReference.setOnItemClickListener { item, view ->
            Log.d("FirstFragment", "Item clicked")
        }

        adapterReference.add(CardGroup())
        adapterReference.add(CardGroup())
        adapterReference.add(CardGroup())
        adapterReference.add(CardGroup())
        adapterReference.add(CardGroup())
        adapterReference.add(CardGroup())
        adapterReference.add(CardGroup())
        adapterReference.add(CardGroup())
        adapterReference.add(CardGroup())
        adapterReference.add(CardGroup())
        adapterReference.add(CardGroup())
        adapterReference.add(CardGroup())
        adapterReference.add(CardGroup())
        adapterReference.add(CardGroup())
        adapterReference.add(CardGroup())
        adapterReference.add(CardGroup())


    }
}

class CardGroup() : Item<GroupieViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.grid_repeat_card_view_layout
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

    }
}