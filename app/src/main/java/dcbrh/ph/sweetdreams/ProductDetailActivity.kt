package dcbrh.ph.sweetdreams

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.squareup.picasso.Picasso
import dcbrh.ph.sweetdreams.databinding.ActivityProductDetailBinding
import kotlinx.android.synthetic.main.activity_product_detail.*

class ProductDetails(var itemName: String ="", var itemImageLink: String ="", var itemPrice: String ="", var itemBrand: String ="", var itemAmount: String="")

class ProductDetailActivity : AppCompatActivity() {

    private val productDetails = ProductDetails()
    private lateinit var binding: ActivityProductDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this ,R.layout.activity_product_detail)
        binding.productData = productDetails

        val dataTitle = intent.getStringExtra("itemName")!!
        val dataImageLink = intent.getStringExtra("itemImageLink")!!
        val dataPrice = intent.getStringExtra("itemPrice")!!
        val dataBrand = intent.getStringExtra("itemBrand")!!
        val dataAmount = intent.getStringExtra("itemAmount")!!

        // Todo You stopped here 10/10/2020 8:52 | Schedule your study Jetpack Compose and View Binding
        productDetails.run {
            itemName = dataTitle
            itemPrice = dataPrice
        }
        Picasso.get().load(dataImageLink).into(imageView_photo_productDetail)


    }


}