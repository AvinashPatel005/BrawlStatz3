package com.kal.brawlstatz3.ui.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.kal.brawlstatz3.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier: Modifier = Modifier
) {
    TopAppBar(title = {
        Text(text = "Brawl StatZ")
    })
//    MediumTopAppBar(
//        title = {
//            Text(text = "Brawl StatZ")
//        },
//        navigationIcon = {
//            IconButton(onClick = { /* do something */ }) {
//                Icon(
//                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
//                    contentDescription = "Localized description"
//                )
//            }
//        },
//        actions = {
//            IconButton(onClick = { /* do something */ }) {
//                Icon(
//                    imageVector = Icons.Filled.Menu,
//                    contentDescription = "Localized description"
//                )
//            }
//        },
//        scrollBehavior = scrollBehavior
//    )
//    TopAppBar(
//        title = {
//            Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween,verticalAlignment = Alignment.CenterVertically) {
//                Text(text = "Brawl StatZ")
//                val interactionSource = remember {
//                    MutableInteractionSource()
//                }
//                var text = remember {
//                    mutableStateOf("")
//                }
//                BasicTextField(
//                    value = text.value,
//                    onValueChange = {
//                        text.value = it
//                    },
//                    modifier = Modifier
//                        .height(40.dp),
//                    singleLine = true,
//                    textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground),
//                    cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
//                    interactionSource = interactionSource
//                ) { innerTextField ->
//                    OutlinedTextFieldDefaults.DecorationBox(
//                        value = text.value,
//                        innerTextField = innerTextField,
//                        enabled = true,
//                        singleLine = true,
//                        leadingIcon = {
//                            Icon(imageVector = Icons.Default.Search, contentDescription = null)
//                        },
//                        visualTransformation = VisualTransformation.None,
//                        interactionSource = interactionSource,
//                        contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(
//                            top = 0.dp,
//                            bottom = 0.dp,
//                        ),
//                        container = {
//                            OutlinedTextFieldDefaults.ContainerBox(true, false, interactionSource, OutlinedTextFieldDefaults.colors(), shape = RoundedCornerShape(20.dp))
//                        },
//                    )
//                }
//                FilledIconButton(onClick = { /*TODO*/ }) {
//                    Icon(imageVector = Icons.Default.Search, contentDescription = null)
//                }
//            }
//        },
//        navigationIcon = {
//            Icon(painter = painterResource(id = R.drawable.logo_menu), contentDescription = null)
//        }
//    )
}