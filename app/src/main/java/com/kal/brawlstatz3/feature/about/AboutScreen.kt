package com.kal.brawlstatz3.feature.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kal.brawlstatz3.BuildConfig
import com.kal.brawlstatz3.R
import com.kal.brawlstatz3.feature.about.components.AboutLinkBox
@Composable
fun AboutAppScreen() {
    Column(
        modifier = Modifier
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        // App Logo
        Image(
            painter = painterResource(id = R.drawable.logo_icon),
            contentDescription = "App Logo",
            modifier = Modifier
                .size(180.dp) // bigger circle
                .clip(CircleShape)
                .background(Color.Black)
                .border(4.dp, MaterialTheme.colorScheme.primary, CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(10.dp))
        // App Title + Tagline
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                "Brawlstatz",
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                "Track • Analyze • Dominate",
                style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.primary),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))
        }

        // App Description
        Text(
            "BrawlStatz helps you track your brawlers, monitor tier rankings, and analyze your progress in real-time. " +
                    "Built with speed and simplicity in mind, our goal is to make your gaming experience smarter and more engaging.",
            style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 20.sp),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(40.dp))
        // Square Link Buttons
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AboutLinkBox("Website", "https://teamdiversity.web.app")
            AboutLinkBox("Privacy Policy", "https://avinashpatel005.github.io/bs-privacy-policy/")
        }

        // Version at bottom
        Text(
            text = "Version ${BuildConfig.VERSION_NAME}",
            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.outline),
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}
