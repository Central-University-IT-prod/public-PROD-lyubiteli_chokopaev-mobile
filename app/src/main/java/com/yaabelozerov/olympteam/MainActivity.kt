package com.yaabelozerov.olympteam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yaabelozerov.core.presentation.Constants
import com.yaabelozerov.core.util.GlobalStateManager
import com.yaabelozerov.olympteam.presentation.FeedScreen
import com.yaabelozerov.olympteam.presentation.ProfileScreen
import com.yaabelozerov.olympteam.presentation.Tabs
import com.yaabelozerov.olympteam.presentation.model.FeedScreenViewModel
import com.yaabelozerov.olympteam.presentation.model.ProfileScreenViewModel
import com.yaabelozerov.olympteam.presentation.theme.OlympTeamTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var navController: NavHostController

    @Inject
    lateinit var stateManager: GlobalStateManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var accessState = MutableStateFlow("LOGGEDOUT")

            val profileScreenViewModel: ProfileScreenViewModel = hiltViewModel()
            profileScreenViewModel.loadCurrentUser(accessState)

            val feedScreenViewModel: FeedScreenViewModel = hiltViewModel()
            feedScreenViewModel.loadTeams()
            feedScreenViewModel.loadStats()

            OlympTeamTheme {
                val items = listOf(
                    BottomNavigationBarItem(
                        title = if (accessState.collectAsState().value != "ADMIN") "Лента" else "Статистика",
                        selectedIcon = Icons.Filled.Home,
                        unselectedIcon = Icons.Outlined.Home,
                        route = Tabs.FeedScreen.route,
                    ),
                    BottomNavigationBarItem(
                        title = "Профиль",
                        selectedIcon = Icons.Filled.AccountCircle,
                        unselectedIcon = Icons.Outlined.AccountCircle,
                        route = Tabs.ProfileScreen.route,
                    ),
                )
                var selectedIndex by rememberSaveable {
                    mutableIntStateOf(0)
                }
                Scaffold(bottomBar = {
                    NavigationBar {
                        items.forEachIndexed { index, item ->
                            NavigationBarItem(selected = selectedIndex == index, onClick = {
                                selectedIndex = index
                                navController.navigate(item.route) {
                                    popUpTo(item.route) {
                                        inclusive = true
                                    }
                                }
                            }, icon = {
                                Icon(
                                    imageVector = if (selectedIndex == index) item.selectedIcon else item.unselectedIcon,
                                    contentDescription = item.title
                                )
                            }, label = { Text(text = item.title, fontSize = Constants.Fonts.xs) })
                        }
                    }
                }) { paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = Tabs.FeedScreen.route,
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        composable(route = Tabs.FeedScreen.route) {
                            FeedScreen(model = feedScreenViewModel, accessState = accessState)
                        }
                        composable(route = Tabs.ProfileScreen.route) {
                            ProfileScreen(
                                model = profileScreenViewModel,
                                saveNewUserInfo = { profileScreenViewModel.patchUserData(it)
                                                  feedScreenViewModel.loadTeams()},
                                saveNewTeamInfo = { profileScreenViewModel.patchTeamData(it) },
                                createNewTeam = { leaderId, eventId, name, size, description, need ->
                                    profileScreenViewModel.createNewTeam(
                                        leaderId,
                                        eventId,
                                        name,
                                        size,
                                        description,
                                        need,
                                        accessState
                                    )
                                    profileScreenViewModel.loadCurrentUser(accessState)
                                },
                                removeUserFromTeam = { uid, tid ->
                                    run {
                                        profileScreenViewModel.user.update {
                                            profileScreenViewModel.user.value.copy(teamData = profileScreenViewModel.user.value.teamData?.copy(
                                                members = profileScreenViewModel.user.value.teamData?.members?.filter { it.id != uid }
                                                    ?: emptyList()))
                                        }
                                        profileScreenViewModel.removeUserFromTeam(
                                            uid, tid
                                        )
                                    }
                                },
                                deleteTeam = {
                                    profileScreenViewModel.deleteTeam(it)
                                    profileScreenViewModel.user.update { profileScreenViewModel.user.value.copy(teamData = null) }
                                    accessState.update { "MEMBER" }
                                },
                                accessState = accessState
                            )
                        }
                    }
                }
            }
        }
    }
}

data class BottomNavigationBarItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String,
)
