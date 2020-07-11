package dcbrh.ph.sweetdreams

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dcbrh.ph.sweetdreams.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // sets up bottomNavigationView with fragments
        val bottomNavigation = binding.activityMainBottomNavigationView
        val navFragment = supportFragmentManager.findFragmentById(R.id.activityMain_fragmentNavHost_viewLayout) as NavHostFragment
        bottomNavigation.setupWithNavController(navFragment.navController)
    }

}


