@file:OptIn(
    ExperimentalGlideComposeApi::class,
    ExperimentalLayoutApi::class,
    ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class
)

package com.yaabelozerov.olympteam.presentation

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.yaabelozerov.core.presentation.Constants
import com.yaabelozerov.core.util.GlobalStateManager
import com.yaabelozerov.olympteam.R
import com.yaabelozerov.olympteam.presentation.model.ProfileScreenViewModel
import com.yaabelozerov.teams.domain.model.TeamData
import com.yaabelozerov.user.domain.model.UserData
import kotlinx.coroutines.flow.MutableStateFlow
import java.net.URI

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    model: ProfileScreenViewModel,
    saveNewUserInfo: (UserData) -> Unit,
    saveNewTeamInfo: (TeamData) -> Unit,
    createNewTeam: (Int, Int, String, Int, String, List<String>) -> Unit,
    removeUserFromTeam: (p1: Int, p2: Int) -> Unit,
    deleteTeam: (Int) -> Unit,
    accessState: MutableStateFlow<String>
) {
    var isUserEditModal by rememberSaveable {
        mutableStateOf(false)
    }
    var isTeamEditModal by rememberSaveable {
        mutableStateOf(false)
    }
    var sheetUserEdit by rememberSaveable {
        mutableStateOf("")
    }
    var sheetTeamEdit by rememberSaveable {
        mutableStateOf("")
    }
    var isCreateTeamModal by rememberSaveable {
        mutableStateOf(false)
    }

    val pickUsersLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                model.uploadUsers(uri)
            }
        }

    if (isCreateTeamModal) {
        Dialog(onDismissRequest = { isCreateTeamModal = false }) {
            var name by remember {
                mutableStateOf("")
            }
            var description by remember {
                mutableStateOf("")
            }
            var size by remember {
                mutableStateOf("1")
            }
            var lst by remember {
                mutableStateOf(listOf<String>())
            }

            Card {
                Column(
                    Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text(text = "Название", fontSize = Constants.Fonts.small) },
                        textStyle = TextStyle(fontSize = Constants.Fonts.small)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text(text = "Описание", fontSize = Constants.Fonts.small) },
                        textStyle = TextStyle(fontSize = Constants.Fonts.small)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = size,
                        onValueChange = { size = it },
                        label = {
                            Text(
                                text = "Кол-во участников", fontSize = Constants.Fonts.small
                            )
                        },
                        textStyle = TextStyle(fontSize = Constants.Fonts.small),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Column(horizontalAlignment = Alignment.Start) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(checked = lst.contains("backend"), onCheckedChange = {
                                if (it) {
                                    lst = lst.plusElement("backend")
                                } else {
                                    lst = lst.minusElement("backend")
                                }
                            })
                            Text(
                                text = stringFromServerRole("backend"),
                                fontSize = Constants.Fonts.small
                            )
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(checked = lst.contains("frontend"), onCheckedChange = {
                                if (it) {
                                    lst = lst.plusElement("frontend")
                                } else {
                                    lst = lst.minusElement("frontend")
                                }
                            })
                            Text(
                                text = stringFromServerRole("frontend"),
                                fontSize = Constants.Fonts.small
                            )
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(checked = lst.contains("mobile"), onCheckedChange = {
                                if (it) {
                                    lst = lst.plusElement("mobile")
                                } else {
                                    lst = lst.minusElement("mobile")
                                }
                            })
                            Text(
                                text = stringFromServerRole("mobile"),
                                fontSize = Constants.Fonts.small
                            )
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(checked = lst.contains("fullstack"), onCheckedChange = {
                                if (it) {
                                    lst = lst.plusElement("fullstack")
                                } else {
                                    lst = lst.minusElement("fullstack")
                                }
                            })
                            Text(
                                text = stringFromServerRole("fullstack"),
                                fontSize = Constants.Fonts.small
                            )
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(checked = lst.contains("datascience"), onCheckedChange = {
                                if (it) {
                                    lst = lst.plusElement("datascience")
                                } else {
                                    lst = lst.minusElement("datascience")
                                }
                            })
                            Text(
                                text = stringFromServerRole("datascience"),
                                fontSize = Constants.Fonts.small
                            )
                        }
                    }

                    Button(onClick = {
                        createNewTeam(
                            model.user.value.userData!!.id!!, 1, name, try { size.toInt() } catch (e: Exception) { 0 }, description, lst
                        )

                        isCreateTeamModal = false
                    }) {
                        Text(text = "Сохранить")
                    }
                }
            }
        }
    }

    if (isUserEditModal) {
        Dialog(onDismissRequest = { isUserEditModal = false }) {
            when (sheetUserEdit) {
                "FIO" -> {
                    var name = remember {
                        mutableStateOf(model.user.value.userData?.name ?: "")
                    }
                    var surname = remember {
                        mutableStateOf(model.user.value.userData?.surname ?: "")
                    }
                    var patronymic = remember {
                        mutableStateOf(model.user.value.userData?.patronymic ?: "")
                    }

                    Card {
                        Column(
                            Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            OutlinedTextField(
                                value = name.value,
                                onValueChange = { name.value = it },
                                label = { Text(text = "Имя", fontSize = Constants.Fonts.small) },
                                textStyle = TextStyle(fontSize = Constants.Fonts.small)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = surname.value,
                                onValueChange = { surname.value = it },
                                label = {
                                    Text(
                                        text = "Фамилия", fontSize = Constants.Fonts.small
                                    )
                                },
                                textStyle = TextStyle(fontSize = Constants.Fonts.small)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = patronymic.value,
                                onValueChange = { patronymic.value = it },
                                label = {
                                    Text(
                                        text = "Отчество", fontSize = Constants.Fonts.small
                                    )
                                },
                                textStyle = TextStyle(fontSize = Constants.Fonts.small)
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            Button(onClick = {
                                saveNewUserInfo(
                                    UserData(
                                        name = name.value,
                                        surname = surname.value,
                                        patronymic = patronymic.value
                                    )
                                )
                                model.user.value.userData = model.user.value.userData!!.copy(
                                    name = name.value,
                                    surname = surname.value,
                                    patronymic = patronymic.value
                                )
                                isUserEditModal = false
                            }) {
                                Text(text = "Сохранить")
                            }
                        }
                    }
                }

                "ROLE" -> {
                    Card {
                        Column(
                            Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Button(onClick = {
                                saveNewUserInfo(UserData(role = "backend"))
                                model.user.value.userData =
                                    model.user.value.userData!!.copy(role = stringFromServerRole("backend"))
                                isUserEditModal = false
                            }, modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    text = stringFromServerRole("backend"),
                                    fontSize = Constants.Fonts.small
                                )
                            }
                            Button(onClick = {
                                saveNewUserInfo(UserData(role = "frontend"))
                                model.user.value.userData =
                                    model.user.value.userData!!.copy(role = stringFromServerRole("frontend"))
                                isUserEditModal = false
                            }, modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    text = stringFromServerRole("frontend"),
                                    fontSize = Constants.Fonts.small
                                )
                            }
                            Button(onClick = {
                                saveNewUserInfo(UserData(role = "mobile"))
                                model.user.value.userData =
                                    model.user.value.userData!!.copy(role = stringFromServerRole("mobile"))
                                isUserEditModal = false
                            }, modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    text = stringFromServerRole("mobile"),
                                    fontSize = Constants.Fonts.small
                                )
                            }
                            Button(onClick = {
                                saveNewUserInfo(UserData(role = "fullstack"))
                                model.user.value.userData =
                                    model.user.value.userData!!.copy(role = stringFromServerRole("fullstack"))
                                isUserEditModal = false
                            }, modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    text = stringFromServerRole("fullstack"),
                                    fontSize = Constants.Fonts.small
                                )
                            }
                            Button(onClick = {
                                saveNewUserInfo(UserData(role = "datascience"))
                                model.user.value.userData =
                                    model.user.value.userData!!.copy(role = stringFromServerRole("datascience"))
                                isUserEditModal = false
                            }, modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    text = stringFromServerRole("datascience"),
                                    fontSize = Constants.Fonts.small
                                )
                            }
                            OutlinedButton(onClick = {
                                saveNewUserInfo(UserData(role = null))
                                model.user.value.userData = model.user.value.userData!!.copy(
                                    role = stringFromServerRole(
                                        null
                                    )
                                )
                                isUserEditModal = false
                            }, modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    text = stringFromServerRole(null),
                                    fontSize = Constants.Fonts.small
                                )
                            }
                        }
                    }
                }

                "TG" -> {
                    Card {
                        var tg = remember {
                            mutableStateOf(model.user.value.userData?.tgId ?: "")
                        }

                        Column(
                            Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            OutlinedTextField(
                                value = tg.value,
                                onValueChange = { tg.value = it },
                                label = {
                                    Text(
                                        text = "Телеграм", fontSize = Constants.Fonts.small
                                    )
                                },
                                textStyle = TextStyle(fontSize = Constants.Fonts.small)
                            )

                            Button(onClick = {
                                saveNewUserInfo(
                                    UserData(
                                        tgId = tg.value
                                    )
                                )
                                model.user.value.userData = model.user.value.userData!!.copy(
                                    tgId = tg.value
                                )
                                isUserEditModal = false
                            }) {
                                Text(text = "Сохранить", fontSize = Constants.Fonts.small)
                            }
                        }
                    }
                }

                "TAGS" -> {
                    Card {
                        var tags = remember {
                            mutableStateOf(model.user.value.userData?.tags ?: emptyList())
                        }

                        LazyColumn(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(tags.value) { t ->
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    OutlinedTextField(
                                        value = tags.value.find { it == t }!!,
                                        onValueChange = {
                                            val elem = tags.value.indexOfFirst { it == t }
                                            if (elem != -1) {
                                                tags.value = tags.value.subList(
                                                    fromIndex = 0, toIndex = elem
                                                ) + listOf(it) + tags.value.subList(
                                                    fromIndex = elem + 1, toIndex = tags.value.size
                                                )
                                            }
                                        },
                                        modifier = Modifier.weight(1f),
                                        textStyle = TextStyle(fontSize = Constants.Fonts.small)
                                    )
                                    IconButton(onClick = {
                                        tags.value = tags.value.minusElement(t)
                                    }, modifier = Modifier.size(32.dp)) {
                                        Icon(
                                            imageVector = Icons.Outlined.Delete,
                                            contentDescription = "remove tag icon"
                                        )
                                    }
                                }
                            }

                            item {
                                Column {
                                    OutlinedButton(onClick = {
                                        tags.value = tags.value.plusElement("")
                                    }, modifier = Modifier.width(192.dp)) {
                                        Text(text = "Добавить", fontSize = Constants.Fonts.small)
                                    }
                                    Button(onClick = {
                                        saveNewUserInfo(
                                            UserData(
                                                tags = tags.value
                                            )
                                        )
                                        model.user.value.userData =
                                            model.user.value.userData!!.copy(
                                                tags = tags.value
                                            )
                                        isUserEditModal = false
                                    }, modifier = Modifier.width(192.dp)) {
                                        Text(text = "Сохранить", fontSize = Constants.Fonts.small)
                                    }
                                }
                            }
                        }
                    }
                }

                "LANGS" -> {
                    var lst = remember {
                        mutableStateOf(model.user.value.userData?.langs)
                    }

                    Card {
                        Column(
                            Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Column(horizontalAlignment = Alignment.Start) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Checkbox(checked = lst.value?.contains("python") ?: false,
                                        onCheckedChange = {
                                            if (it) {
                                                lst.value = lst.value?.plusElement("python")
                                            } else {
                                                lst.value = lst.value?.minusElement("python")
                                            }
                                        })
                                    Text(text = "Python", fontSize = Constants.Fonts.small)
                                }
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Checkbox(checked = lst.value?.contains("rust") ?: false,
                                        onCheckedChange = {
                                            if (it) {
                                                lst.value = lst.value?.plusElement("rust")
                                            } else {
                                                lst.value = lst.value?.minusElement("rust")
                                            }
                                        })
                                    Text(text = "Rust", fontSize = Constants.Fonts.small)
                                }
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Checkbox(checked = lst.value?.contains("js") ?: false,
                                        onCheckedChange = {
                                            if (it) {
                                                lst.value = lst.value?.plusElement("js")
                                            } else {
                                                lst.value = lst.value?.minusElement("js")
                                            }
                                        })
                                    Text(text = "JavaScript", fontSize = Constants.Fonts.small)
                                }
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Checkbox(checked = lst.value?.contains("cs") ?: false,
                                        onCheckedChange = {
                                            if (it) {
                                                lst.value = lst.value?.plusElement("cs")
                                            } else {
                                                lst.value = lst.value?.minusElement("cs")
                                            }
                                        })
                                    Text(text = "C#", fontSize = Constants.Fonts.small)
                                }
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Checkbox(checked = lst.value?.contains("cpp") ?: false,
                                        onCheckedChange = {
                                            if (it) {
                                                lst.value = lst.value?.plusElement("cpp")
                                            } else {
                                                lst.value = lst.value?.minusElement("cpp")
                                            }
                                        })
                                    Text(text = "C++", fontSize = Constants.Fonts.small)
                                }
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Checkbox(checked = lst.value?.contains("go") ?: false,
                                        onCheckedChange = {
                                            if (it) {
                                                lst.value = lst.value?.plusElement("go")
                                            } else {
                                                lst.value = lst.value?.minusElement("go")
                                            }
                                        })
                                    Text(text = "Go", fontSize = Constants.Fonts.small)
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Button(onClick = {
                                saveNewUserInfo(UserData(langs = lst.value))
                                model.user.value.userData =
                                    model.user.value.userData!!.copy(langs = lst.value)
                                isUserEditModal = false
                            }) {
                                Text(text = "Сохранить", fontSize = Constants.Fonts.small)
                            }
                        }
                    }
                }
            }
        }
    }

    val userState = model.user.collectAsState()

    if (!userState.value.isLoading) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (userState.value.userData != null) {
                item {
                    FilledProfileUserData(modifier = Modifier.padding(32.dp, 32.dp, 32.dp, 0.dp),
                        userData = userState.value.userData as UserData,
                        edit = { sheetUserEdit = it },
                        open = { isUserEditModal = true })
                }
            } else {
                item { Text(text = "User data is null") }
            }
            item {
                when (accessState.collectAsState().value) {
                    "LEADER" -> {
                        if (userState.value.teamData != null) {
                            FilledTeamData(
                                modifier = Modifier.padding(32.dp, 0.dp),
                                teamData = userState.value.teamData as TeamData,
                                removeUserFromTeam,
                                deleteTeam,
                                edit = { sheetTeamEdit = it },
                                open = { isTeamEditModal = true },
                                accessState = accessState
                            )
                        }
                    }

                    "MEMBER" -> {
                        if (userState.value.teamData != null) {
                            FilledTeamData(
                                modifier = Modifier.padding(16.dp, 0.dp),
                                teamData = userState.value.teamData as TeamData,
                                removeUserFromTeam,
                                {},
                                edit = {},
                                open = {},
                                accessState = accessState
                            )
                        } else {
                            Column(
                                Modifier.fillMaxHeight(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Spacer(modifier = Modifier.weight(1f))
                                Text(
                                    text = "У вас ещё нет команды. Найдите или создайте свою:",
                                    modifier = Modifier.padding(32.dp, 0.dp),
                                    fontSize = Constants.Fonts.medium,
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Button(onClick = { isCreateTeamModal = true }) {
                                    Text(
                                        text = "Создать команду", fontSize = Constants.Fonts.small
                                    )
                                }
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }

                    "ADMIN" -> {
                        Column(
                            Modifier.padding(16.dp, 0.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            OutlinedButton(onClick = { pickUsersLauncher.launch("*/*")
                            }) {
                                Text(
                                    text = "Загрузить список участников (.xlsx)",
                                    fontSize = Constants.Fonts.small
                                )
                            }
                            OutlinedButton(onClick = { /*TODO*/ }) {
                                Text(
                                    text = "Загрузить результаты (.xlsx)",
                                    fontSize = Constants.Fonts.small
                                )
                            }
                        }
                        Text(text = "Admin", modifier = Modifier.padding(16.dp, 0.dp))
                    }

                }
            }
        }

        if (isTeamEditModal) {
            Dialog(onDismissRequest = { isTeamEditModal = false }) {
                when (sheetTeamEdit) {
                    "NAME" -> {
                        var name = remember {
                            mutableStateOf(model.user.value.teamData?.name ?: "")
                        }
                        var description = remember {
                            mutableStateOf(model.user.value.teamData?.description ?: "")
                        }

                        Card {
                            Column(
                                Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                OutlinedTextField(value = name.value,
                                    onValueChange = { name.value = it },
                                    label = {
                                        Text(
                                            text = "Название", fontSize = Constants.Fonts.small
                                        )
                                    })
                                Spacer(modifier = Modifier.height(8.dp))
                                OutlinedTextField(value = description.value,
                                    onValueChange = { description.value = it },
                                    label = {
                                        Text(
                                            text = "Описание", fontSize = Constants.Fonts.small
                                        )
                                    })
                                Spacer(modifier = Modifier.height(8.dp))

                                Button(onClick = {
                                    Log.i("saveNewTeam", name.value.toString())
                                    saveNewTeamInfo(
                                        TeamData(
                                            id = model.user.value.teamData!!.id,
                                            name = name.value,
                                            description = description.value
                                        )
                                    )
                                    model.user.value.teamData = model.user.value.teamData!!.copy(
                                        name = name.value, description = description.value
                                    )
                                    isTeamEditModal = false
                                }) {
                                    Text(text = "Сохранить")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FilledTeamData(
    modifier: Modifier = Modifier,
    teamData: TeamData,
    removeUserFromTeam: (Int, Int) -> Unit,
    deleteTeam: (Int) -> Unit,
    edit: (String) -> Unit,
    open: () -> Unit,
    accessState: MutableStateFlow<String>
) {
    if (accessState.collectAsState().value == "LEADER" || accessState.collectAsState().value == "MEMBER") {
        Log.i("access", accessState.collectAsState().value)
        Column(modifier = modifier) {
            Row {
                Text(text = "Моя команда:", fontSize = Constants.Fonts.medium)
                Spacer(modifier = Modifier.weight(1f))
                if (accessState.collectAsState().value == "LEADER") {
                    IconButton(onClick = {
                        deleteTeam(teamData.id!!)
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = "Delete user team icon"
                        )
                    }
                }
            }
            Text(text = teamData.name ?: "",
                fontSize = Constants.Fonts.large,
                modifier = Modifier.clickable {
                    edit("NAME")
                    open()
                })
            Spacer(modifier = Modifier.height(4.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                teamData.tags?.forEach {
                    Card {
                        Text(
                            text = it,
                            fontSize = Constants.Fonts.small,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = teamData.description ?: "",
                fontSize = Constants.Fonts.small,
                modifier = Modifier.clickable {
                    edit("NAME")
                    open()
                })
            Spacer(modifier = Modifier.height(16.dp))
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                teamData.members?.toSet()?.forEach {
                    Card(
                        colors = if (it.id == teamData.leader!!.id) CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        ) else CardDefaults.cardColors()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(8.dp)
                        ) {
                            GlideImage(
                                model = it.avatarUrl,
                                contentDescription = "${it.name}'s avatar",
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(
                                        CircleShape
                                    )
                                    .background(MaterialTheme.colorScheme.primary),
                                loading = placeholder(R.drawable.icons8_user),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "${it.name} ${it.surname}", fontSize = Constants.Fonts.small
                            )
                            if (accessState.collectAsState().value == "LEADER" && it.id != teamData.leader!!.id) {
                                IconButton(onClick = {
                                    removeUserFromTeam(it.id!!, teamData.id ?: 0)
                                }) {
                                    Icon(
                                        imageVector = Icons.Outlined.Delete,
                                        contentDescription = "Delete user from team icon"
                                    )

                                }
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Ищем:", fontSize = Constants.Fonts.small)
            Spacer(modifier = Modifier.height(4.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                teamData.need?.forEach {
                    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)) {
                        Text(
                            text = it,
                            fontSize = Constants.Fonts.small,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
                if (teamData.need == null || teamData.need!!.isEmpty()) {
                    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)) {
                        Text(
                            text = "любые роли",
                            fontSize = Constants.Fonts.small,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FilledProfileUserData(
    modifier: Modifier = Modifier, userData: UserData, edit: (String) -> Unit, open: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        verticalArrangement = Arrangement.Center
    ) {
        GlideImage(
            model = userData.avatarUrl,
            contentDescription = "User avatar",
            Modifier
                .size(96.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary),
            loading = placeholder(R.drawable.icons8_user),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable {
            edit("FIO")
            open()
        }) {
            Text(
                "${userData.name} ${userData.surname}\n${userData.patronymic}",
                fontSize = Constants.Fonts.medium,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Outlined.Edit,
                contentDescription = "edit icon",
                tint = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f),
                modifier = Modifier.size(16.dp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable {
            edit("ROLE")
            open()
        }) {
            Text(
                text = stringFromServerRole(userData.role),
                color = MaterialTheme.colorScheme.primary,
                fontWeight = MaterialTheme.typography.labelMedium.fontWeight,
                fontSize = Constants.Fonts.small,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Outlined.Edit,
                contentDescription = "edit icon",
                tint = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f),
                modifier = Modifier.size(16.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        if (userData.tgId != null) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable {
                edit("TG")
                open()
            }) {
                GlideImage(
                    model = R.drawable.icons8_telegram,
                    contentDescription = "Telegram icon",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = userData.tgId!!, fontSize = Constants.Fonts.small
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = "edit icon",
                    tint = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f),
                    modifier = Modifier.size(16.dp)
                )
            }
        }
        if (userData.email != null) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.MailOutline,
                    contentDescription = "E-mail icon",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = userData.email!!, fontSize = Constants.Fonts.small)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        FlowRow(horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.clickable {
                edit("TAGS")
                open()
            }) {
            if (userData.tags == null || userData.tags!!.isEmpty()) {
                OutlinedCard {
                    Text(
                        text = "Добавить навыки",
                        fontSize = Constants.Fonts.small,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
            userData.tags?.forEach {
                OutlinedCard {
                    Text(
                        text = it,
                        fontSize = Constants.Fonts.small,
                        modifier = Modifier.padding(4.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        FlowRow(horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.clickable {
                edit("LANGS")
                open()
            }) {
            if (userData.langs == null || userData.langs!!.isEmpty()) {
                OutlinedCard {
                    Text(
                        text = "Добавить языки",
                        fontSize = Constants.Fonts.small,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
            userData.langs?.forEach {
                OutlinedCard {
                    Text(
                        text = stringFromServerLang(it),
                        fontSize = Constants.Fonts.small,
                        modifier = Modifier.padding(4.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}

fun stringFromServerRole(s: String?): String {
    return when (s) {
        "backend", "Бэкенд" -> "Бэкенд"
        "frontend", "Фронтенд" -> "Фронтенд"
        "mobile", "Мобильная разработка" -> "Мобильная разработка"
        "fullstack", "Фуллстек" -> "Фуллстек"
        "datascience", "Анализ данных" -> "Анализ данных"
        else -> "Роль не выбрана"
    }
}

fun stringFromServerLang(s: String?): String {
    return when (s) {
        "python" -> "Python"
        "rust" -> "Rust"
        "js" -> "JavaScript"
        "cs" -> "C#"
        "cpp" -> "C++"
        "go" -> "Go"
        else -> ""
    }
}