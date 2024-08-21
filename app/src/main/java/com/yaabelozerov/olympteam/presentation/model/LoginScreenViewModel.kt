package com.yaabelozerov.olympteam.presentation.model

import android.content.Context
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.yaabelozerov.core.util.GlobalStateManager
import com.yaabelozerov.olympteam.data.remote.model.RegisterDTO
import com.yaabelozerov.olympteam.data.remote.source.LoginApiImpl
import com.yaabelozerov.olympteam.data.remote.source.RegisterApiImpl
import com.yaabelozerov.user.di.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.await
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val loginApiImpl: LoginApiImpl,
    private val dataStoreManager: DataStoreManager,
    private val registerApiImpl: RegisterApiImpl,
    @ApplicationContext private val context: Context
) : ViewModel() {
    val loginScreenState = MutableStateFlow(LoginScreenState())

    val loginOld = MutableStateFlow("")
    val passwordOld = MutableStateFlow("")

    val name = MutableStateFlow("")
    val surname = MutableStateFlow("")
    val patronymic = MutableStateFlow("")
    val email = MutableStateFlow("")
    val password = MutableStateFlow("")
    val photo = MutableStateFlow("")
    val tgUsername = MutableStateFlow("")
    val isAdmin = MutableStateFlow(false)
    val role = MutableStateFlow("Выберите роль")
    val tags = MutableStateFlow(listOf<String>())

    fun tryLogin(onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val resp =
                    loginApiImpl.signIn(username = loginOld.value, password = passwordOld.value)
                        .await()
                Log.i("LoginScreenViewModel", resp.toString())
                if (resp.token != null) {
                    dataStoreManager.setCurrentUserToken(resp.token)
                    onSuccess()
                } else {
                    loginScreenState.update { LoginScreenState(isLoading = false, error = null) }
                }
            } catch (e: HttpException) {
                when (e.code()) {
                    404 -> {
                        loginScreenState.update {
                            LoginScreenState(
                                isLoading = false, error = "Пользователь с таким e-mail не найден"
                            )
                        }
                    }

                    401 -> {
                        loginScreenState.update {
                            LoginScreenState(
                                isLoading = false, error = "Неверные данные"
                            )
                        }
                    }

                    else -> {
                        loginScreenState.update {
                            LoginScreenState(
                                isLoading = false, error = "Ошибка сервера. Попробуйте позже"
                            )
                        }
                    }
                }
            } catch (e: UnknownHostException) {
                loginScreenState.update {
                    LoginScreenState(isLoading = false, error = "Ошибка сети")
                }
            } catch (e: Exception) {
                loginScreenState.update { LoginScreenState(isLoading = false, error = e.message) }
                e.printStackTrace()
            }
        }
    }

    fun tryRegister(onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val req = RegisterDTO(
                    name = name.value,
                    surname = surname.value,
                    patronymic = patronymic.value,
                    email = email.value,
                    password = password.value,
                    photo = "",
                    tgUsername = tgUsername.value,
                    isAdmin = isAdmin.value,
                    role = role.value,
                    tags = tags.value
                )
                val resp = registerApiImpl.createUser(req).await()
                Log.i("tryRegister_Response", resp.toString())
                if (resp.token != null) {
                    dataStoreManager.setCurrentUserToken(resp.token)
                    onSuccess()
                }
            } catch (e: HttpException) {
                when (e.code()) {
                    422 -> {
                        loginScreenState.update {
                            LoginScreenState(
                                isLoading = false,
                                error = "Некорректный e-mail или пароль. Пароль должен насчитывать не менее 8 символов"
                            )
                        }
                    }

                    409 -> {
                        loginScreenState.update {
                            LoginScreenState(
                                isLoading = false, error = "E-mail или Телеграм уже зарегистрирован"
                            )
                        }
                    }

                    else -> {
                        loginScreenState.update {
                            LoginScreenState(
                                isLoading = false, error = "Ошибка сервера. Попробуйте позже"
                            )
                        }
                    }
                }
            } catch (e: UnknownHostException) {
                loginScreenState.update {
                    LoginScreenState(isLoading = false, error = "Ошибка сети")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}