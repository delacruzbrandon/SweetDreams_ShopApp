package dcbrh.ph.sweetdreams

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.graphics.component1
import androidx.core.graphics.component2
import androidx.databinding.DataBindingUtil
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import dcbrh.ph.sweetdreams.databinding.ActivityAddProductBinding
import java.io.ByteArrayOutputStream
import java.util.*

class AddProduct : AppCompatActivity() {

    private lateinit var binding: ActivityAddProductBinding
    private lateinit var inputStream: ByteArray

    private lateinit var uriReference: Uri
    private var buttonReference: ImageButton? = null
    private var productCategory: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_product)

        buttonFunctions()
        binding.radioGroupCategoryActivityAddProduct.setOnCheckedChangeListener { _, checkedId ->
            val toolsCategory = binding.radioButtonCategoryTools
            val equipmentCategory = binding.radioButtonCategoryEquipment
            val ingredientsCategory = binding.radioButtonCategoryIngredients
            val packagingCategory = binding.radioButtonCategoryPackaging

            when (checkedId) {
                toolsCategory.id -> productCategory = toolsCategory.text.toString()
                equipmentCategory.id -> productCategory = equipmentCategory.text.toString()
                ingredientsCategory.id -> productCategory = ingredientsCategory.text.toString()
                packagingCategory.id -> productCategory = packagingCategory.text.toString()
            }
        }
    }

    private fun buttonFunctions() {
        binding.apply {
            imageButtonBackAddProductActivity.setOnClickListener {
                val intent = Intent(this@AddProduct, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }

            imageButtonCheckAddProductActivity.setOnClickListener {
                Log.d("AddProduct", "Storing data in FirebaseDatabase")
                storeImagesInFirebaseStorage()
            }


            imageButton1ActivityAddProduct.setOnClickListener {
                selectPhotoToUpload(imageButton1ActivityAddProduct)

            }

            imageButton2ActivityAddProduct.setOnClickListener {
                selectPhotoToUpload(imageButton2ActivityAddProduct)

            }

            imageButton3ActivityAddProduct.setOnClickListener {
                selectPhotoToUpload(imageButton3ActivityAddProduct)
            }
        }
    }

    private fun selectPhotoToUpload(currentButton: ImageButton){
        Log.d("AddProduct", "Selecting Photo $currentButton")
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 0)
        buttonReference = currentButton
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            Log.d("AddProduct", "Photo Selected")
            uriReference = data.data!!
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uriReference)
            val compressedBitmap = compressSelectedBitmap(decodeSampleUriFromStream(this, uriReference, 200, 185))
            buttonReference?.apply {
                background?.alpha = 0
                setImageBitmap(compressedBitmap)
            }
        }
    }

    private fun calculateTargetSize(options: BitmapFactory.Options, reqHeight: Int, reqWidth: Int): Int{
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 2

        if (height > reqHeight || width > reqWidth) {
            val halfHeight = height / 2
            val halfWidth = width / 2

            while (halfHeight / inSampleSize >= reqHeight || halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }

    private fun decodeSampleUriFromStream(context: Context, imageUri: Uri, reqWidth: Int, reqHeight: Int): Bitmap {
        return BitmapFactory.Options().run {
            inJustDecodeBounds = true
            BitmapFactory.decodeStream(context.contentResolver.openInputStream(imageUri), null, this)

            inSampleSize = calculateTargetSize(this, reqHeight, reqWidth)

            inJustDecodeBounds = false
            BitmapFactory.decodeStream(context.contentResolver.openInputStream(imageUri), null, this)!!
        }
    }

    private fun compressSelectedBitmap(bitmap: Bitmap): Bitmap {
        val outputStream = ByteArrayOutputStream()
        //TODO The Problem is within your bitmap compress. It might be lacking in Bitmap dimension definition.

        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
        inputStream = outputStream.toByteArray()

        Log.d("AddProduct", "${inputStream.size} bytes")
        Log.d("AddProduct", "${inputStream.size / 1_000} kb")
        Log.d("AddProduct", "${inputStream.size / 100_000} mb")

        return BitmapFactory.decodeByteArray(inputStream, 0, inputStream.size)
    }


    private fun storeImagesInFirebaseStorage() {
        val productName = binding.inputFieldProductEditText.text.toString()
        val randomUUID = UUID.randomUUID().toString()
        val firebaseStorage = FirebaseStorage.getInstance().getReference("Product Images/$productName/${"$randomUUID.jpg"}/")
        firebaseStorage.putBytes(inputStream)
            .addOnSuccessListener { it ->
                Log.d("AddProduct", "Image stored in FirebaseStorage $productName")
                Log.d("AddProduct", "Image stored in FirebaseStorage ${it.metadata?.path}")
                firebaseStorage.downloadUrl.addOnSuccessListener {
                    storeDataInFirebaseDatabase(productName, randomUUID, "$it")
                    Log.d("AddProduct", "ImageURL: $it")
                }
                
            }

    }


    private fun storeDataInFirebaseDatabase(productName: String, randomUUID: String, imageLink: String) {
        val productPrice = binding.inputFieldPriceEditText.text.toString()
        val productAmount = binding.inputFieldPcsEditText.text.toString()
        val productBrand = binding.inputFieldBrandEditText.text.toString()



        val database = FirebaseDatabase.getInstance()
        val product = Product(productName, "₱$productPrice", productAmount, productBrand, imageLink)
        val childReference = database.getReference("products/$productCategory/$randomUUID")

        childReference.setValue(product).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(this, "Data stored", Toast.LENGTH_SHORT).show()
                Log.d("AddProduct", "Data stored in FirebaseDatabase $randomUUID")
                return@addOnCompleteListener
            } else {
                Toast.makeText(this, "Failed to store", Toast.LENGTH_SHORT).show()
                Log.d("AddProduct", "Data failed to store in FirebaseDatabase ${it.result}")
                return@addOnCompleteListener
            }
        }

    }
}

class Product(val name: String, val price: String, val amount: String, val brand: String, val imageLink: String){
    constructor(): this("", "" ,"", "", "")
}

