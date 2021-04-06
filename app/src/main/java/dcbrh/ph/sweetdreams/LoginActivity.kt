package dcbrh.ph.sweetdreams

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dcbrh.ph.sweetdreams.databinding.ActivityLoginBinding
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.apply {
            textViewRegisterActivityLogin.setOnClickListener {
                Log.d("LoginActivity", "Inflate register activity")
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            }

            buttonCreateAccountActivityLogin.setOnClickListener {
                Log.d("LoginActivity", "Logging User in")
                verifyEmailAddress()
            }

            buttonShowPasswordLoginActivity.setOnClickListener {
                val showButton = binding.buttonShowPasswordLoginActivity
                if (showButton.text == ("Show")) {
                    binding.editTextPasswordActivityLogin.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    showButton.text = "Hide"
                } else {
                    binding.editTextPasswordActivityLogin.transformationMethod = PasswordTransformationMethod.getInstance()
                    showButton.text = "Show"
                }

            }
        }
    }

    private fun verifyEmailAddress() {
        val email = binding.editTextEmailActivityLogin.text.toString()
        val password = binding.editTextPasswordActivityLogin.text.toString()

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("LoginActivity", "User logged in ${it.result}")
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        }.addOnFailureListener {
            Log.d("LoginActivity", "Failed to login")
        }
    }

}