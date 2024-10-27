package com.kal.brawlstatz3.feature.brawlers.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.kal.brawlstatz3.feature.brawlers.BrawlerUiEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrawlersTopBar(
    isSearchActive: Boolean,
    searchQuery: String,
    onEvent: (BrawlerUiEvent) -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    val scope = rememberCoroutineScope()
    Row(
        Modifier.background(SearchBarDefaults.colors().containerColor, RoundedCornerShape(100)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AnimatedVisibility(visible = isSearchActive) {
            BasicTextField(value = searchQuery,
                textStyle = TextStyle(color = SearchBarDefaults.inputFieldColors().focusedTextColor),
                cursorBrush = SolidColor(
                    SearchBarDefaults.inputFieldColors().cursorColor
                ),
                onValueChange = {
                    onEvent(BrawlerUiEvent.SearchQueryChanged(it))
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                modifier = Modifier.focusRequester(focusRequester),
                decorationBox = { innerTextField ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                            .padding(horizontal = 16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Search,
                            tint = SearchBarDefaults.inputFieldColors().focusedLeadingIconColor,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(contentAlignment = Alignment.CenterStart) {
                            if (searchQuery.isEmpty())
                                Text(
                                    text = "Search",
                                    color = SearchBarDefaults.inputFieldColors().focusedPlaceholderColor
                                )
                            innerTextField.invoke()
                        }

                    }

                })
        }
        IconButton(onClick = {
            onEvent(BrawlerUiEvent.SearchToggled)
            if (!isSearchActive) {
                scope.launch {
                    delay(100)
                    focusRequester.requestFocus()
                }
            }
        }) {
            Icon(
                imageVector = if (isSearchActive) Icons.Default.Close else Icons.Default.Search,
                contentDescription = null
            )
        }
    }

}