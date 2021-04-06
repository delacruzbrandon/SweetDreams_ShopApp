package dcbrh.ph.sweetdreams

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dcbrh.ph.sweetdreams.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)

        binding.apply {
            textViewLoginActivityRegister.setOnClickListener {
                Log.d("LoginActivity", "Inflate login activity")
                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            }
            buttonCreateAccountActivityRegister.setOnClickListener {
                Log.d("RegisterActivity", "Creating User Account")
                registerUserFirebaseAuth()
            }

            buttonShowPasswordRegisterActivity.setOnClickListener {
                val showButton = binding.buttonShowPasswordRegisterActivity
                if (showButton.text.equals("Show")) {
                    binding.editTextPasswordActivityRegister.transformationMethod =
                        HideReturnsTransformationMethod.getInstance()
                    showButton.text = "Hide"
                } else {
                    binding.editTextPasswordActivityRegister.transformationMethod =
                        PasswordTransformationMethod.getInstance()
                    showButton.text = "Show"
                }

            }
        }
    }

    var username: String? = null
    var email: String? = null
    var password: String? = null

    private fun registerUserFirebaseAuth() {
        val firebaseAuth = FirebaseAuth.getInstance()

        username = binding.editTextUsernameActivityRegister.text.toString()
        email = binding.editTextEmailActivityRegister.text.toString()
        password = binding.editTextPasswordActivityRegister.text.toString()

        if (email!!.isEmpty() || password!!.isEmpty()) {
            Toast.makeText(this, "Don't leave Email/Pass empty", Toast.LENGTH_SHORT).show()
            Log.d("RegisterActivity", "Don't leave Email or Password empty")
            return
        }
        firebaseAuth.createUserWithEmailAndPassword(email!!, password!!).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("RegisterActivity", "User created ${it.result}")
                Log.d("RegisterActivity", "Storing User in FirebaseDatabase")
                storeUserInFirebaseDatabase()
                return@addOnCompleteListener
            }else {
                Log.d("RegisterActivity", "Failed to create User")
                return@addOnCompleteListener
            }
        }
    }

    private fun storeUserInFirebaseDatabase() {
        val uid = FirebaseAuth.getInstance().uid.toString()
        val user = User(username!!, email!!, password!!)
        val database = FirebaseDatabase.getInstance()
        val childReference = database.getReference("user/$uid")
        childReference.setValue(user).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(this, "Congrats! You're registered", Toast.LENGTH_SHORT).show()
                Log.d("RegisterActivity", "User stored in FirebaseDatabase ${it.result}")
                return@addOnCompleteListener
            } else {
                Log.d("RegisterActivity", "Failed to store in FirebaseDatabase")
                return@addOnCompleteListener
            }
        }
    }

    class User(val username: String, val email: String, val password: String){
        constructor(): this("", "", "")
    }

}