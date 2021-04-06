    package dcbrh.ph.sweetdreams.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ConcatAdapter
import com.google.firebase.database.*
import dcbrh.ph.sweetdreams.Product
import dcbrh.ph.sweetdreams.ProductDetailActivity
import dcbrh.ph.sweetdreams.R
import dcbrh.ph.sweetdreams.adapters.GridAdapter
import dcbrh.ph.sweetdreams.adapters.HeaderAdapter
import dcbrh.ph.sweetdreams.adapters.MainFragmentConcatAdapter
import dcbrh.ph.sweetdreams.databinding.FragmentProductBinding

private const val TAG = "MainFragment"
class MainFragment : Fragment(), GridAdapter.OnItemClickListener {

    private lateinit var binding: FragmentProductBinding
    private lateinit var  product: ArrayList<Product>
    private lateinit var headerAdapter: HeaderAdapter
    private lateinit var gridAdapter: GridAdapter
    private lateinit var adapterReference: ConcatAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product, container, false)

        product = ArrayList()
        headerAdapter = HeaderAdapter()
        gridAdapter = GridAdapter(product, this)
//        adapterReference = ConcatAdapter(headerAdapter, MainFragmentConcatAdapter(product, gridAdapter)) TODO Study if concat adapter is needed
        fetchDataInFirebaseDatabase()

        return binding.root
    }

    private fun fetchDataInFirebaseDatabase() {
        val databaseReference = FirebaseDatabase.getInstance().getReference("products/") // DO NOT Remove this part.

        val valueListener = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    val dataValue = it.getValue(Product::class.java)
                    if (dataValue != null) product.add(dataValue)
                }

                Log.d("MainFragment", "Number of children ${snapshot.key} = ${snapshot.childrenCount}")
//                adapterReference.adapters[1].notifyDataSetChanged() TODO
                gridAdapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {}
        }
        databaseReference.child("Equipment/").addListenerForSingleValueEvent(valueListener)
        databaseReference.child("Ingredients/").addListenerForSingleValueEvent(valueListener)
        databaseReference.child("Tools/").addListenerForSingleValueEvent(valueListener)
//        binding.fragmentProductRepeatGridRecyclerView.adapter = adapterReference TODO
        binding.fragmentProductRepeatGridRecyclerView.adapter = gridAdapter
    }

    override fun onItemClick(gridPosition: Int) {
        val clickedGridItem = product[gridPosition]
        val intent = Intent(layoutInflater.context, ProductDetailActivity::class.java)
        intent.apply {
            putExtra("itemImageLink", clickedGridItem.imageLink)
            putExtra("itemName", clickedGridItem.name)
            putExtra("itemPrice", clickedGridItem.price)
            putExtra("itemAmount", clickedGridItem.amount)
            putExtra("itemBrand", clickedGridItem.brand)
        }
        startActivity(intent)

        Log.d(TAG, "onItemClick: $gridPosition")
    }

}

// TODO SINGLE VALUE
//databaseReference.addListenerForSingleValueEvent(object : ValueEventListener{
//    override fun onDataChange(snapshot: DataSnapshot) {
//        snapshot.children.forEach {
//            val dataValue = it.getValue(Product::class.java)
//            if (dataValue != null) product.add(dataValue)
//            adapterReference.adapters[1].notifyDataSetChanged()
//        }
//        Log.d("MainFragment", "Number of children ${snapshot.key} = ${snapshot.childrenCount}")
//    }
//    override fun onCancelled(error: DatabaseError) {}
//})

// TODO CHILD EVENT
//databaseReference.addChildEventListener(object : ChildEventListener{
//    override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
//
//        snapshot.children.forEach {
//            val dataValue = it.getValue(Product::class.java)
//            if (dataValue != null) product.add(dataValue)
//            adapterReference.adapters[1].notifyDataSetChanged()
//        }
//        Log.d("MainFragment", "Number of children ${snapshot.key} = ${snapshot.childrenCount}")
//    }
//    override fun onCancelled(error: DatabaseError) {}
//    override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
//    override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
//    override fun onChildRemoved(snapshot: DataSnapshot) {}
//})

// TODO VALUE EVENT
//val valueListener = object : ValueEventListener{
//    override fun onDataChange(snapshot: DataSnapshot) {
//        snapshot.children.forEach {
//            val dataValue = it.getValue(Product::class.java)
//            if (dataValue != null) product.add(dataValue)
//            adapterReference.adapters[1].notifyDataSetChanged()
//        }
//        Log.d("MainFragment", "Number of children ${snapshot.key} = ${snapshot.childrenCount}")
//    }
//    override fun onCancelled(error: DatabaseError) {}
//}
//databaseReference.child("Equipment/").addValueEventListener(valueListener)
//databaseReference.child("Ingredients/").addValueEventListener(valueListener)
//databaseReference.child("Tools/").addValueEventListener(valueListener)
