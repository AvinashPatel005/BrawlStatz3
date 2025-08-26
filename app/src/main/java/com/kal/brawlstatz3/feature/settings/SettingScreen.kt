package com.kal.brawlstatz3.feature.settings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kal.brawlstatz3.R
import com.kal.brawlstatz3.data.model.AppIcon
import com.kal.brawlstatz3.ui.theme.AppTheme

@Composable
fun SettingScreen(
    modifier: Modifier = Modifier,
    currentTheme: AppTheme,
    currentIcon: String?,
    onThemeChange: (Int) -> Unit = {},
    onIconChange: (String) -> Unit = {},
    onClearData: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        val themes = listOf("Amoled","Auto","Light", "Dark", "Classic","Variant")
        ExpandableSettingCard(
            title = "Theme",
            description = "Choose app theme"
        ) { onCollapse ->
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                themes.forEachIndexed { i, theme ->
                    ThemeOption(theme, currentTheme.ordinal==i) {
                        onThemeChange(i)
                    }
                }
            }
        }
        val icons = listOf(
            AppIcon(
            alias = "com.kal.brawlstatz3.DefaultIcon", name = "Default",
            icon = R.drawable.ic_launcher),
            AppIcon(
                name = "Classic",
                alias = "com.kal.brawlstatz3.Icon1",
                icon = R.drawable.ic_launcher_alt
            ))

        ExpandableSettingCard(
            title = "App Icon",
            description = "Choose your preferred icon"
        ) { onCollapse ->
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                icons.forEachIndexed { i,icon->
                    var selected = if (currentIcon==null)  ("com.kal.brawlstatz3.DefaultIcon" == icon.alias) else currentIcon==icon.alias
                    IconOption(
                        icon.icon,icon.name, selected
                    ) {
                        if(!selected){
                            onIconChange(icon.alias)
                        }
                    }
                }
            }
        }
            SettingCard(title = "Clear Data", description = "Remove all stored app data") {
            Button(
                onClick = { onClearData() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF56262))
            ) {
                Text("Clear Data", color = Color.White)
            }
        }
    }
}

@Composable
fun ExpandableSettingCard(
    title: String,
    description: String,
    content: @Composable (onCollapse: () -> Unit) -> Unit
) {
    var expanded by remember { mutableStateOf(true) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.clickable { expanded = !expanded }) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(title, style = MaterialTheme.typography.titleMedium)
                    Text(
                        description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null
                )
            }

            AnimatedVisibility(expanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    content { expanded = false }
                }
            }
        }
    }
}

@Composable
fun ThemeOption(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(40.dp))
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = null // handled by Row clickable
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun IconOption(
    icon: Int,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(40.dp))
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = selected,
                onClick = null
            )
            Spacer(Modifier.width(8.dp))
            Text(label, style = MaterialTheme.typography.bodyMedium)
        }

        Image(
            painter = painterResource(icon),
            contentDescription = label,
            modifier = Modifier.size(26.dp),
            contentScale = ContentScale.Crop
        )
    }
}


@Composable
fun SettingCard(
    title: String,
    description: String,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.weight(1f) // take remaining space
            ) {
                Text(title, style = MaterialTheme.typography.titleMedium)
                Text(
                    description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Box(
                modifier = Modifier.padding(start = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                content()
            }
        }
    }
}
