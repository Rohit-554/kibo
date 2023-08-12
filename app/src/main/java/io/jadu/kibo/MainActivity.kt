package io.jadu.kibo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.authentication.storage.CredentialsManager
import com.auth0.android.authentication.storage.SharedPreferencesStorage
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint
import io.jadu.kibo.databinding.ActivityMainBinding
import io.jadu.kibo.ui.features.news.viewModel.NewsViewModel
import io.jadu.kibo.uitlities.Constants
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private val newsViewModel: NewsViewModel by viewModels()
    private lateinit var Auth0Account:Auth0
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar = findViewById<MaterialToolbar>(R.id.appToolbar)
        Auth0Account = Auth0(Constants.AUTH0_CLIENT_ID, Constants.AUTH0_DOMAIN)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(R.id.homeNewsFragment),
            fallbackOnNavigateUpListener = ::onSupportNavigateUp
        )
//        setupActionBarWithNavController(navController,appBarConfiguration)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            supportActionBar?.title = destination.label
        }
//        setUpBottomNav()
        binding.bottomNavigationView.setupWithNavController(navController)
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            // In order to get the expected behavior, you have to call default Navigation method manually
            NavigationUI.onNavDestinationSelected(item, navController)
            return@setOnItemSelectedListener true
        }
        val callback = object : Callback<Credentials, AuthenticationException> {
            override fun onFailure(exception: AuthenticationException) {
                // Failure! Check the exception for details
            }

            override fun onSuccess(credentials: Credentials) {
                // Success! Access token and ID token are presents
            }
        }


//        WebAuthProvider.login(Auth0Account)
//            .start(this, callback)
//        lifecycleScope.launch {
//            try {
//                val credentials = WebAuthProvider.login(Auth0Account)
//                    .withTrustedWebActivity()
//                    .await(this@MainActivity)
//                findNavController(R.id.fragmentContainerView).navigate(R.id.homeNewsFragment)
//
//                println(credentials)
//            } catch(e: AuthenticationException) {
//                finish()
//            }
//        }

//        lifecycleScope.launch {
//            try {
//                WebAuthProvider.logout(Auth0Account)
//                    .await(this@MainActivity)
//                println("Logged out")
//            } catch(e: AuthenticationException) {
//                // show error message
//            }
//        }
//
//        val authentication = AuthenticationAPIClient(Auth0Account)
//        val storage = SharedPreferencesStorage(this)
//        val manager = CredentialsManager(authentication, storage)
//        lifecycleScope.launch {
//            try {
//                val credentials = authentication
//                    .login("info@auth0.com", "a secret password", "my-database-connection")
//                    .setScope("openid email profile offline_access")
//                    .await()
//                manager.saveCredentials(credentials)
//            } catch (e: AuthenticationException) {
//
//            }
//        }
//        val authenticated = manager.hasValidCredentials()
    }
}