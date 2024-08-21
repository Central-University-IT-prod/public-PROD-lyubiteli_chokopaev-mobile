package com.yaabelozerov.olympteam.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import com.yaabelozerov.core.util.GlobalStateManager
import com.yaabelozerov.olympteam.MainActivity
import com.yaabelozerov.olympteam.data.remote.source.LoginApiImpl
import com.yaabelozerov.olympteam.presentation.model.LoginScreenViewModel
import com.yaabelozerov.user.di.DataStoreManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.await
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    @Inject
    lateinit var dataStoreManager: DataStoreManager

    @Inject
    lateinit var loginApiImpl: LoginApiImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // DEBUG
            var showLogin = MutableStateFlow(false)
            tryLoginFromSavedToken(showLogin)

            var isRegistered = MutableStateFlow(true)

            val loginScreenViewModel: LoginScreenViewModel = hiltViewModel()
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (showLogin.collectAsState().value) {
                    LoginScreen(model = loginScreenViewModel,
                        onLoginAttempt = { tryLoginFromSavedToken(show = showLogin) },
                        showState = isRegistered,
                        onRegisterAttempt = { tryLoginFromSavedToken(show = showLogin) })
                }
            }
        }
    }

    private fun tryLoginFromSavedToken(show: MutableStateFlow<Boolean>) {
        try {
            lifecycleScope.launch {
//                delay(1000)
                val key = dataStoreManager.currentUserToken.first()
                if (key != "") {
                    this@LoginActivity.applicationContext.startActivity(
                        Intent(
                            this@LoginActivity.applicationContext, MainActivity::class.java
                        ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    )
                } else {
                    show.update { true }
                }
            }
        } catch (e: Exception) {
            Log.e("LoginActivity_Login", "Unhandled exception")
            e.printStackTrace()
        }
    }
}