@file:OptIn(ExperimentalMaterial3Api::class)

package com.yaabelozerov.olympteam.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.yaabelozerov.core.presentation.Constants
import com.yaabelozerov.olympteam.presentation.model.LoginScreenViewModel
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun LoginScreen(
    model: LoginScreenViewModel,
    onLoginAttempt: () -> Unit,
    showState: MutableStateFlow<Boolean>,
    onRegisterAttempt: () -> Unit
) {
    Column(
        Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val show = showState.collectAsState().value
        val state = model.loginScreenState.collectAsState()
        if (show) {
            OutlinedTextField(value = model.loginOld.collectAsState().value,
                onValueChange = { model.loginOld.value = it },
                label = { Text(text = "E-mail", fontSize = Constants.Fonts.small) }, textStyle = TextStyle.Default.copy(fontSize = Constants.Fonts.small))
            OutlinedTextField(value = model.passwordOld.collectAsState().value,
                onValueChange = { model.passwordOld.value = it },
                label = { Text(text = "Пароль", fontSize = Constants.Fonts.small) }, textStyle = TextStyle.Default.copy(fontSize = Constants.Fonts.small))
            Row {
                OutlinedButton(
                    onClick = { showState.value = !showState.value },
                    Modifier.background(MaterialTheme.colorScheme.surface)
                ) {
                    Text(text = "Создать аккаунт", fontSize = Constants.Fonts.xs)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    model.tryLogin(onLoginAttempt)
                }) {
                    Text(text = "Войти")
                }
            }
            if (state.value.error != null) {
                Text(text = state.value.error.toString(), color = MaterialTheme.colorScheme.error)
            }
        } else {
            OutlinedTextField(value = model.name.collectAsState().value,
                onValueChange = { model.name.value = it },
                label = { Text(text = "Имя", fontSize = Constants.Fonts.small) }, textStyle = TextStyle.Default.copy(fontSize = Constants.Fonts.small))
            OutlinedTextField(value = model.surname.collectAsState().value,
                onValueChange = { model.surname.value = it },
                label = { Text(text = "Фамилия", fontSize = Constants.Fonts.small) }, textStyle = TextStyle.Default.copy(fontSize = Constants.Fonts.small))
            OutlinedTextField(value = model.patronymic.collectAsState().value,
                onValueChange = { model.patronymic.value = it },
                label = { Text(text = "Отчество", fontSize = Constants.Fonts.small) }, textStyle = TextStyle.Default.copy(fontSize = Constants.Fonts.small))
            OutlinedTextField(value = model.email.collectAsState().value,
                onValueChange = { model.email.value = it },
                label = { Text(text = "E-mail", fontSize = Constants.Fonts.small) }, textStyle = TextStyle.Default.copy(fontSize = Constants.Fonts.small))
            OutlinedTextField(value = model.password.collectAsState().value,
                onValueChange = { model.password.value = it },
                label = { Text(text = "Пароль", fontSize = Constants.Fonts.small) }, textStyle = TextStyle.Default.copy(fontSize = Constants.Fonts.small))
            // TODO Photo
            OutlinedTextField(value = model.tgUsername.collectAsState().value,
                onValueChange = { model.tgUsername.value = it },
                label = {
                    Text(
                        text = "Юзернейм в Телеграме", fontSize = Constants.Fonts.small
                    )
                }, textStyle = TextStyle.Default.copy(fontSize = Constants.Fonts.small))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = model.isAdmin.collectAsState().value,
                    onCheckedChange = { model.isAdmin.value = it })
                Text(text = "Я организатор", fontSize = Constants.Fonts.small)
            }
            Row {
                OutlinedButton(
                    onClick = { showState.value = !showState.value },
                    Modifier.background(MaterialTheme.colorScheme.surface)
                ) {
                    Text(text = "Войти", fontSize = Constants.Fonts.xs)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    model.tryRegister(onRegisterAttempt)
                }) {
                    Text(text = "Создать аккаунт", fontSize = Constants.Fonts.xs)
                }
            }
            if (state.value.error != null) {
                Text(text = state.value.error.toString(), color = MaterialTheme.colorScheme.error)
            }
        }
    }
}