@file:OptIn(ExperimentalLayoutApi::class)

package com.yaabelozerov.olympteam.presentation

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yaabelozerov.core.presentation.Constants
import com.yaabelozerov.olympteam.presentation.model.FeedScreenViewModel
import com.yaabelozerov.stats.presentation.MyPieChart
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun FeedScreen(
    modifier: Modifier = Modifier, accessState: MutableStateFlow<String>, model: FeedScreenViewModel
) {
    val teamsValue = model.feed.collectAsState().value
    when (accessState.collectAsState().value) {
        "MEMBER" -> LazyColumn(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(teamsValue.teams) {
                OutlinedCard(modifier = Modifier.fillMaxWidth()) {
                    var hasApplied by rememberSaveable {
                        mutableStateOf(false)
                    }
                    Column(
                        Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(text = it.name ?: "", fontSize = Constants.Fonts.large)
                        Text(text = it.description ?: "", fontSize = Constants.Fonts.small)
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            it.tags?.forEach { tag ->
                                Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
                                    Text(
                                        tag,
                                        fontSize = Constants.Fonts.small,
                                        modifier = Modifier.padding(4.dp)
                                    )
                                }
                            }
                        }
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            it.members?.toSet()?.forEach { user ->
                                Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
                                    Column(Modifier.padding(4.dp)) {
                                        Text(
                                            (user.name ?: "") + " " + (user.surname ?: ""),
                                            fontSize = Constants.Fonts.small
                                        )
                                    }
                                }
                            }
                        }
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(text = "Ищем:", fontSize = Constants.Fonts.small)
                            it.need?.forEach { need ->
                                Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)) {
                                    Text(
                                        need,
                                        fontSize = Constants.Fonts.small,
                                        modifier = Modifier.padding(4.dp)
                                    )
                                }
                            }
                        }
                        if (!hasApplied) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Button(onClick = {
                                    model.applyForTeam(it.id!!)
                                    hasApplied = true
                                }) {
                                    Text(text = "Подать заявку", fontSize = Constants.Fonts.small)
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = "${ it.members?.size ?: 0 }/${it.maxSize}", fontSize = Constants.Fonts.small)
                            }
                        } else {
                            OutlinedButton(onClick = { }) {
                                Text(text = "Заявка отправлена!")
                            }
                        }
                    }
                }
            }
        }
        "ADMIN" -> {
            Column {
                Log.i("teamsval", teamsValue.toString())
                MyPieChart(teamsValue.langStats)
            }
        }
    }
}