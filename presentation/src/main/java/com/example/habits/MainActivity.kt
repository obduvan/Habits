package com.example.habits

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.example.habits.databinding.ActivityMainBinding
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val havHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val navController = havHostFragment.navController

        appBarConfiguration = AppBarConfiguration(
            navController.graph, binding.navigationDrawer
        )

        binding.navView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)

        loadImage(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) ||
                super.onSupportNavigateUp()
    }

    private fun loadImage(activity: MainActivity) {
        this.lifecycleScope.launch {
            Glide.with(activity)
                .load(urlImage)
                .override(50, 50)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.baseline_warning_24)
                .centerCrop()
                .into(binding.navView.getHeaderView(0).findViewById(R.id.avatar))
        }
    }

    companion object {
        private const val urlImage =
            "https://upload.wikimedia.org/wikipedia/commons/9/9d/Tomato.png"
        private const val urlImage2 = "https://i.stack.imgur.com/ILTQq.png"
    }
}

