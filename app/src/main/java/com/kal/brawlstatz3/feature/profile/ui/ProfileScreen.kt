package com.kal.brawlstatz3.feature.profile.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.Indication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.kal.brawlstatz3.R
import com.kal.brawlstatz3.data.model.brawler.Brawler
import com.kal.brawlstatz3.feature.profile.ProfileUiEvent
import com.kal.brawlstatz3.feature.profile.ui.components.BattleCard
import com.kal.brawlstatz3.feature.profile.ui.components.StatCard
import com.kal.brawlstatz3.feature.profile.ui.components.StatHeader
import com.kal.brawlstatz3.feature.profile.viewmodel.ProfileViewModel
import com.kal.brawlstatz3.util.FirebaseStorageUtil
import com.kal.brawlstatz3.util.LoadingBar
import kotlin.math.ceil


@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel,
    brawlerList: List<Brawler>,
    modifier: Modifier = Modifier
) {
    val profile = profileViewModel.profile.value
    val club = profileViewModel.club.value
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val itemWidth = screenWidth / 2 - 16.dp
    val favorite = brawlerList.find { brawler -> brawler.name == profile.favourite }
    val sortBy = listOf(
        "Name",
        "Current Trophy",
        "Highest Trophy",
        "Level",
        "Rank",
        "Mastery",
    )
    if (profileViewModel.tagList.isEmpty() || profileViewModel.currentTag.value == "") {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Column() {

                Text(text = "ADD A PLAYER", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text(text = "Keep you progression handy")
                OutlinedTextField(
                    onValueChange = {
                        profileViewModel.onEvent(
                            ProfileUiEvent.InitialTagValue(
                                it
                            )
                        )
                    },
                    value = profileViewModel.initialInputTag.value,
                    placeholder = {
                        Text(text = "Enter player tag")
                    },
                    singleLine = true,
                    keyboardActions = KeyboardActions(
                        onDone = {
                            profileViewModel.onEvent(ProfileUiEvent.ItemAdd)
                        }
                    ))
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        profileViewModel.onEvent(ProfileUiEvent.ItemAdd)
                    }
                ) {
                    Text("Add")
                }
                Image(
                    painter = painterResource(R.drawable.icon_edgar),
                    contentDescription = null,
                    modifier = Modifier
                        .alpha(0.6f)
                        .fillMaxWidth(.6f)
                )
            }
        }
    } else if (profileViewModel.errorLog.value != "") {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(R.drawable.icon_spike_sad),
                    contentDescription = null,
                    modifier = Modifier.alpha(0.6f)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text("Something Went Wrong!")
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = {
                        profileViewModel.onEvent(ProfileUiEvent.Reload)
                    }
                ) {
                    Text("Refresh")
                }
            }
        }
    } else if (profileViewModel.isLoading.value && !profileViewModel.isRefreshing.value) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            LoadingBar()
        }
    } else {
        PullToRefreshBox(onRefresh = {
            profileViewModel.onEvent(ProfileUiEvent.Refresh)
        }, isRefreshing = profileViewModel.isLoading.value) {
            LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {

                item {
                    Card(
                        shape = RoundedCornerShape(
                            topStart = 20.dp,
                            topEnd = 0.dp,
                            bottomEnd = 20.dp,
                            bottomStart = 0.dp
                        )
                    ) {
                        Box {
                            if (favorite != null) {
                                GlideImage(
                                    model = FirebaseStorageUtil().getBrawlerModel2DURL(favorite.id),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .align(
                                            Alignment.CenterEnd
                                        )
                                        .size(200.dp)
                                        .alpha(0.4f)
                                )
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                            ) {
                                Spacer(modifier = Modifier.width(8.dp))
                                GlideImage(
                                    model = profile.dp,
                                    contentDescription = null,
                                    modifier = Modifier.size(100.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = profile.name,
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        GlideImage(
                                            model = club.dp,
                                            contentDescription = null,
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(2.dp))
                                        Text(text = club.name, fontSize = 12.sp)
                                    }
                                    profile.fame?.let {
                                        GlideImage(
                                            model = it.icon,
                                            contentDescription = it.value,
                                            modifier = Modifier.size(36.dp)
                                        )
                                    }
                                }
                            }
                            profile.accountCreated?.let {
                                Text(
                                    text = it,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .padding(top = 12.dp)
                                        .align(Alignment.TopEnd)
                                        .background(
                                            MaterialTheme.colorScheme.primary,
                                            shape = RoundedCornerShape(
                                                topStart = 8.dp,
                                                bottomEnd = 0.dp,
                                                topEnd = 0.dp,
                                                bottomStart = 2.dp
                                            )
                                        )
                                        .padding(start = 6.dp, end = 2.dp),
                                    fontSize = 12.sp
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .padding(bottom = 2.dp, end = 2.dp)
                                    .align(Alignment.BottomEnd)
                            ) {
                                if (profile.winStreak != null) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        GlideImage(
                                            model = profile.winStreak.icon,
                                            contentDescription = null,
                                            modifier = Modifier.size(30.dp)
                                        )
                                        Text(
                                            text = profile.winStreak.value,
                                            fontWeight = FontWeight.ExtraBold,
                                            fontSize = 16.sp,
                                            color = MaterialTheme.colorScheme.onSurface,
                                            style = TextStyle(
                                                shadow = Shadow(
                                                    Color.Black, Offset(1f, 1f), 4f
                                                )
                                            ),
                                            modifier = Modifier.offset(x = (-4).dp)
                                        )
                                    }
                                }
                            }

                        }
                    }
                }
                profile.ranked?.let {
                    item {
                        StatHeader(header = "Ranked")
                    }
                    item {
                        profile.ranked.current.let {
                            StatCard(
                                icon = it.icon,
                                name = it.name,
                                value = it.value
                            )
                        }
                    }
                    item {
                        profile.ranked.highest.let {
                            StatCard(
                                icon = it.icon,
                                name = it.name,
                                value = it.value
                            )
                        }
                    }
                }


                item {
                    StatHeader(header = "Stats")
                }

                items(profile.stats, key = { stat -> stat.name }) { stat ->
                    StatCard(icon = stat.icon, name = stat.name, value = stat.value)
                }
                item {
                    StatHeader(header = "Progress")
                }
                items(profile.progress, key = { progress -> progress.name }) { progress ->
                    StatCard(icon = progress.icon, name = progress.name, value = progress.value)
                }
                item {
                    StatHeader(header = "Battle Log (" + profile.battleLog.filter { log -> log.result > 0 }.size + "/" + profile.battleLog.size + ")")
                }
                item {
                    val itemCount = profile.battleLog.size
                    val columns = maxOf(1, (LocalConfiguration.current.screenWidthDp - 16) / 50)
                    val rows = ceil(itemCount / columns.toFloat()).toInt()
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(50.dp),
                        modifier = Modifier.height((rows * 30).dp),
                        userScrollEnabled = false
                    ) {
                        itemsIndexed(profile.battleLog, key = { index, _ -> index }) { _, battle ->
                            BattleCard(battle = battle)
                        }
                    }
                }

                item {
                    Row(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillParentMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Brawler", fontSize = 21.sp, fontWeight = FontWeight.Bold)
                        Text(
                            text = if (profileViewModel.sortBy.intValue != -1) sortBy[profileViewModel.sortBy.intValue] else "Sort",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable(
                                interactionSource = null,
                                indication = null
                            ) {
                                profileViewModel.cycleSort()
                            })
                    }
                }
                item {
                    LazyHorizontalGrid(
                        rows = GridCells.Fixed(2),
                        modifier = Modifier.height(260.dp)
                    ) {
                        items(profileViewModel.brawlers) { brawler ->
                            Column(
                                modifier = Modifier
                                    .width(itemWidth)
                                    .padding(start = 4.dp, end = 4.dp, bottom = 8.dp)
                                    .border(
                                        1.dp,
                                        MaterialTheme.colorScheme.surfaceVariant,
                                        RoundedCornerShape(4.dp)
                                    )
                            ) {
                                Box(modifier = Modifier
                                    .weight(1f)
                                    .padding(1.dp)) {
                                    Row(
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        GlideImage(model = brawler.dp, contentDescription = null)
                                        Column(
                                            horizontalAlignment = Alignment.End,
                                            verticalArrangement = Arrangement.SpaceBetween,
                                            modifier = Modifier
                                                .fillMaxHeight()
                                                .padding(end = 4.dp, bottom = 2.dp)
                                        ) {
                                            Text(text = brawler.name, fontWeight = FontWeight.Bold)
                                            Column(horizontalAlignment = Alignment.End) {
                                                Row {
                                                    brawler.gears.forEach {
                                                        Spacer(modifier = Modifier.width(2.dp))
                                                        GlideImage(
                                                            model = it,
                                                            contentDescription = null,
                                                            modifier = Modifier.size(18.dp)
                                                        )
                                                    }
                                                }
                                                Spacer(modifier = Modifier.height(2.dp))
                                                Row {
                                                    brawler.starPower.forEach {
                                                        Spacer(modifier = Modifier.width(2.dp))
                                                        Box(contentAlignment = Alignment.Center) {
                                                            Image(
                                                                painter = painterResource(id = R.drawable.icon_starpower),
                                                                contentDescription = null,
                                                                modifier = Modifier.size(18.dp)
                                                            )
                                                            GlideImage(
                                                                model = it,
                                                                contentDescription = null,
                                                                modifier = Modifier.size(8.dp)
                                                            )
                                                        }
                                                    }
                                                    brawler.gadget.forEach {
                                                        Spacer(modifier = Modifier.width(2.dp))
                                                        Box(contentAlignment = Alignment.Center) {
                                                            Image(
                                                                painter = painterResource(id = R.drawable.icon_gadget),
                                                                contentDescription = null,
                                                                modifier = Modifier.size(18.dp)
                                                            )
                                                            GlideImage(
                                                                model = it,
                                                                contentDescription = null,
                                                                modifier = Modifier.size(8.dp)
                                                            )

                                                        }
                                                    }
                                                }
                                            }

                                        }
                                    }
                                    GlideImage(
                                        model = brawler.tier,
                                        contentDescription = null,
                                        modifier = Modifier.size(20.dp)
                                    )

                                }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(MaterialTheme.colorScheme.surfaceVariant)
                                        .padding(horizontal = 8.dp, vertical = 2.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text(text = "Highest", fontSize = 10.sp, lineHeight = 16.sp)
                                        Text(
                                            text = brawler.highestTrophy.toString(),
                                            fontSize = 12.sp,
                                            lineHeight = 16.sp
                                        )
                                    }
                                    Column {
                                        Text(text = "Current", fontSize = 10.sp, lineHeight = 16.sp)
                                        Text(
                                            text = brawler.currentTrophy.toString() + if (brawler.seasonEndTrophy != 0) "(" + brawler.seasonEndTrophy + ")" else "",
                                            fontSize = 12.sp,
                                            lineHeight = 16.sp
                                        )
                                    }
                                    Column {
                                        Text(text = "Level", fontSize = 10.sp, lineHeight = 16.sp)
                                        Text(
                                            text = brawler.powerLevel.toString(),
                                            fontSize = 12.sp,
                                            lineHeight = 16.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }


    }
    if (profileViewModel.isMenu.value) Dialog(onDismissRequest = {
        profileViewModel.onEvent(ProfileUiEvent.MenuDismiss)
    }) {
        Card(
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
                Text(
                    text = "Add a Player",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
                OutlinedTextField(
                    value = profileViewModel.inputTag.value,
                    placeholder = {
                        Text(text = "Enter Tag")
                    },
                    onValueChange = {
                        profileViewModel.onEvent(ProfileUiEvent.InputTagValueChanged(it))
                    },
                    singleLine = true,
                    keyboardActions = KeyboardActions(
                        onDone = {
                            profileViewModel.onEvent(ProfileUiEvent.ItemAdd)
                        }
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                profileViewModel.tagList.forEachIndexed { i, tag ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp),
                        horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(tag)
                        if (profileViewModel.isDeleting.value) {
                            IconButton(
                                onClick = {
                                    profileViewModel.onEvent(ProfileUiEvent.ItemDelete(tag = tag))
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        } else {
                            RadioButton(onClick = {
                                profileViewModel.onEvent(ProfileUiEvent.SelectTag(tag))
                            }, selected = profileViewModel.currentTag.value == tag)
                        }
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = {
                            profileViewModel.onEvent(ProfileUiEvent.DeleteClicked)
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                    }
                    Button(onClick = {
                        profileViewModel.onEvent(ProfileUiEvent.ItemAdd)
                    }) {
                        Text(text = "Add")
                    }
                }
            }
        }
    }
}
