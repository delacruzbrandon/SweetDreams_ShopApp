package dcbrh.ph.sweetdreams.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import dcbrh.ph.sweetdreams.R
import dcbrh.ph.sweetdreams.adapters.HeaderAdapter.HeaderViewHolder

class HeaderAdapter(): RecyclerView.Adapter<HeaderViewHolder>() {

    class HeaderViewHolder(private val headerView: View): RecyclerView.ViewHolder(headerView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.header_layout, parent, false)
        return HeaderViewHolder(view)

    }

    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {
    }

    override fun getItemCount(): Int {
        return 1
    }

}