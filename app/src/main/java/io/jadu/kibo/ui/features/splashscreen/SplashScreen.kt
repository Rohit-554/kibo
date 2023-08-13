package io.jadu.kibo.ui.features.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import io.jadu.kibo.MainActivity
import io.jadu.kibo.R
import io.jadu.kibo.uitlities.Constants
import kotlinx.coroutines.launch

class SplashScreen : AppCompatActivity() {
    private lateinit var Auth0Account: Auth0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Auth0Account = Auth0(Constants.AUTH0_CLIENT_ID, Constants.AUTH0_DOMAIN)
        Handler(Looper.getMainLooper()).postDelayed({
//            lifecycleScope.launch {
//                try {
//                    val credentials = WebAuthProvider.login(Auth0Account)
//                        .withTrustedWebActivity()
//                        .await(this@SplashScreen)
                    val intent = Intent(this@SplashScreen ,MainActivity::class.java)
                    startActivity(intent)
                    finish()
//
//                    println(credentials)
//                } catch(e: AuthenticationException) {
//                    finish()
//                }
//
//                lifecycleScope.launch {
//                    try {
//                        WebAuthProvider.logout(Auth0Account)
//                            .await(this@SplashScreen)
//                        println("Logged out")
//                    } catch(e: AuthenticationException) {
//                        // show error message
//                    }
//                }
//
//            }
        }, 1500)



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
    }
}