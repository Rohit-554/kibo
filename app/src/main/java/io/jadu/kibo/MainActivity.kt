package io.jadu.kibo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.authentication.storage.CredentialsManager
import com.auth0.android.authentication.storage.SharedPreferencesStorage
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import dagger.hilt.android.AndroidEntryPoint
import io.jadu.kibo.ui.features.news.viewModel.NewsViewModel
import io.jadu.kibo.uitlities.Constants
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val newsViewModel: NewsViewModel by viewModels()
    private lateinit var Auth0Account:Auth0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Auth0Account = Auth0(Constants.AUTH0_CLIENT_ID, Constants.AUTH0_DOMAIN)
        newsViewModel.newsLiveData.observe(this){
            Log.d("MainActivity", "onCreate: $it")
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
        lifecycleScope.launch {
            try {
                val credentials = WebAuthProvider.login(Auth0Account)
                    .withTrustedWebActivity()
                    .await(this@MainActivity)
                println(credentials)
            } catch(e: AuthenticationException) {
                // show error message
            }
        }

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