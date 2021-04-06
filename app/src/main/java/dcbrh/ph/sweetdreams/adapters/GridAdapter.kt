package dcbrh.ph.sweetdreams.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dcbrh.ph.sweetdreams.Product
import dcbrh.ph.sweetdreams.R
import dcbrh.ph.sweetdreams.adapters.GridAdapter.GridViewHolder
import kotlinx.android.synthetic.main.grid_repeat_card_view_layout.view.*


class GridAdapter(
    private val product: ArrayList<Product>, private val clickListener: OnItemClickListener): RecyclerView.Adapter<GridViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        val cellGrid = LayoutInflater.from(parent.context).inflate(R.layout.grid_repeat_card_view_layout, parent, false)
        return GridViewHolder(cellGrid)
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        holder.apply {
            titleText.text = product[position].name
            priceText.text = product[position].price
            Picasso.get().load(product[position].imageLink).into(holder.imageView)

        }
    }

    override fun getItemCount(): Int = product.size

    inner class GridViewHolder(private val gridView: View): RecyclerView.ViewHolder(gridView), View.OnClickListener {
        val titleText: TextView = gridView.gridRepeat_cardView_title_textView
        val priceText: TextView = gridView.gridRepeat_cardView_price_textView
        val imageView: ImageView = gridView.gridRepeat_cardView_productPhoto_imageView

        init {
            gridView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                clickListener.onItemClick(position)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(gridPosition: Int)
    }
}



