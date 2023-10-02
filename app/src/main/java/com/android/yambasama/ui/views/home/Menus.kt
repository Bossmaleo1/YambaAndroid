package com.android.yambasama.ui.views.home

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.ContactPage
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.PeopleOutline
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.navigation.NavHostController
import com.android.yambasama.R
import com.android.yambasama.presentation.viewModel.drop.DropViewModel
import com.android.yambasama.presentation.viewModel.user.UserViewModel
import com.android.yambasama.ui.UIEvent.Event.AuthEvent
import com.android.yambasama.ui.views.model.Route

@Composable
@ExperimentalMaterial3Api
fun Menus(
    navController: NavHostController,
    dropViewModel: DropViewModel,
    userViewModel: UserViewModel
) {

    var expanded by remember { mutableStateOf(false) }

    //We add our badges
    BadgedBox(badge = {
        Badge {
            val badgeNumber = "8"
            Text(
                badgeNumber,
                modifier = Modifier.semantics {
                    contentDescription = "$badgeNumber new notifications"
                }
            )
        }
    }) {

        Icon(
            imageVector = Icons.Outlined.Notifications,
            contentDescription = "Localized description",
            modifier = Modifier.clickable {
                navController.navigate(Route.notificationListView)
            }
        )
    }

    IconButton(onClick = {
        expanded = true
    }) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = "Localized description"
        )
    }

    //we create our Dropdown Menu Item
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        DropdownMenuItem(
            text = {
                Text(
                    text = stringResource(id = R.string.account),
                    maxLines = 1
                )
            },
            onClick = {
                navController.navigate(Route.accountView)
            },
            leadingIcon = {
                Icon(
                    Icons.Outlined.AccountCircle,
                    contentDescription = null
                )
            })

        DropdownMenuItem(
            text = {
                Text(
                    text = stringResource(id = R.string.settings),
                    maxLines = 1
                )
            },
            onClick = { /* Handle edit! */ },
            leadingIcon = {
                Icon(
                    Icons.Outlined.Settings,
                    contentDescription = null
                )
            })

        DropdownMenuItem(
            text = {
                Text(
                    text = stringResource(id = R.string.contact_us),
                    maxLines = 1
                )
            },
            onClick = { /* Handle edit! */ },
            leadingIcon = {
                Icon(
                    Icons.Outlined.ContactPage,
                    contentDescription = null
                )
            }
        )

        DropdownMenuItem(
            text = {
                Text(
                    text = stringResource(id = R.string.guest_friend),
                    maxLines = 1
                )
            },
            onClick = { /* Handle edit! */ },
            leadingIcon = {
                Icon(
                    Icons.Outlined.PeopleOutline,
                    contentDescription = null
                )
            })

        DropdownMenuItem(
            text = {
                Text(
                    text = stringResource(id = R.string.about),
                    maxLines = 1
                )
            },
            onClick = { /* Handle edit! */ },
            leadingIcon = {
                Icon(
                    Icons.Outlined.HelpOutline,
                    contentDescription = null
                )
            })

        DropdownMenuItem(
            text = {
                Text(
                    stringResource(id = R.string.logout)
                )
            },
            onClick = {
                /* Handle settings! */
                dropViewModel.deleteAll()
                userViewModel.onEvent(AuthEvent.InitUserState)
                navController.navigate(Route.loginView)
            },
            leadingIcon = {
                Icon(
                    Icons.Outlined.Logout,
                    contentDescription = null
                )
            })
    }
}