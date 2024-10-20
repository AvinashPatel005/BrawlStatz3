package com.kal.brawlstatz3.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

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