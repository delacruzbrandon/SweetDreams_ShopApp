package dcbrh.ph.sweetdreams.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dcbrh.ph.sweetdreams.Product
import dcbrh.ph.sweetdreams.ProductDetailActivity
import dcbrh.ph.sweetdreams.R
import dcbrh.ph.sweetdreams.adapters.MainFragmentConcatAdapter.ConcatViewHolder
import dcbrh.ph.sweetdreams.fragments.MainFragment
import kotlinx.android.synthetic.main.concat_recycler_view.view.*

class MainFragmentConcatAdapter(val product: ArrayList<Product>, val gridAdapter: GridAdapter): RecyclerView.Adapter<ConcatViewHolder>(), GridAdapter.OnItemClickListener {
    class ConcatViewHolder(val concatView: View): RecyclerView.ViewHolder(concatView) {
        fun bind(gridAdapter: GridAdapter) {
            concatView.concat_recyclerView.adapter = gridAdapter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConcatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.concat_recycler_view, parent, false)
        return ConcatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ConcatViewHolder, position: Int) {
        val adapter = GridAdapter(product, this)
        holder.bind(adapter)
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onItemClick(gridPosition: Int) { // TODO You stopped here 12:21 am | Try and implement another onItemClickListener here for the GridAdapter
        val clickedGridItem = product[gridPosition]
        Log.d("Concat", "onItemClick: $gridPosition")
    }

}