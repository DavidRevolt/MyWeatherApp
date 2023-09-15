package com.example.myweatherapp.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.myweatherapp.R
import com.example.myweatherapp.ui.designsystem.lightAppBarIconTint
import com.example.myweatherapp.ui.designsystem.lightAppBarTextStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    navigationIcon: ImageVector,
    navigationIconContentDescription: String?,
    searchIcon: ImageVector,
    searchIconContentDescription: String?,
    gpsIcon: ImageVector,
    gpsIconContentDescription: String?,
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
    onNavigationClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    onGpsClick: () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                style = lightAppBarTextStyle
            )
        },
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                Icon(
                    imageVector = navigationIcon,
                    contentDescription = navigationIconContentDescription,
                    tint = lightAppBarIconTint,
                )
            }
        },
        actions = {
            IconButton(onClick =  onGpsClick ) {
                Icon(
                    imageVector = gpsIcon,
                    contentDescription = gpsIconContentDescription,
                    tint = lightAppBarIconTint
                )
            }

            IconButton(onClick = onSearchClick) {
                Icon(
                    imageVector = searchIcon,
                    contentDescription = searchIconContentDescription,
                    tint = lightAppBarIconTint,
                )
            }
        },
        colors = colors
    )
}


@Composable
fun AppBarDropDownMenu(
    onAboutClick: () -> Unit,
    expanded: MutableState<Boolean>
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopStart) //Align
    ) {
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
            modifier = Modifier
                .background(Color.White),
            offset = DpOffset(x = 25.dp, y = 25.dp)
        ) {
            DropdownMenuItem(text = { Text(stringResource(R.string.aboutScreenTitle)) }, onClick = {
                expanded.value = false
                onAboutClick.invoke()
            }, leadingIcon = {
                Icon(imageVector = Icons.Rounded.Info, contentDescription = "")
            })
        }
    }
}
