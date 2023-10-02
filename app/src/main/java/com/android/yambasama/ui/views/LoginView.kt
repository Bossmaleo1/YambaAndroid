package com.android.yambasama.ui.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.Login
import androidx.compose.material.icons.outlined.ManageAccounts
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material.Scaffold
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.android.yambasama.R
import com.android.yambasama.presentation.util.getFieldError
import com.android.yambasama.presentation.viewModel.user.UserViewModel
import com.android.yambasama.ui.UIEvent.Event.AuthEvent
import com.android.yambasama.ui.UIEvent.UIEvent
import com.android.yambasama.ui.views.model.Route
import kotlinx.coroutines.flow.collectLatest


@Composable
@ExperimentalMaterial3Api
fun login(navController: NavHostController, userViewModel: UserViewModel) {
    var email by rememberSaveable { mutableStateOf("sidneymaleoregis@gmail.com") }
    var password by rememberSaveable { mutableStateOf("Nfkol3324012020@!") }
    var passwordHidden by rememberSaveable { mutableStateOf(true) }

    val screenState = userViewModel.screenState.value
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { },
        backgroundColor = MaterialTheme.colorScheme.background,
        content = { innerPadding ->

            Column {
                if (screenState.isLoad) {
                    AlertDialog(
                        onDismissRequest = {
                            // Dismiss the dialog when the user clicks outside the dialog or on the back
                            // button. If you want to disable that functionality, simply use an empty
                        },
                        title = {

                        },
                        text = {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Row {
                                    CircularProgressIndicator()
                                    Row(Modifier.padding(10.dp)) {
                                        Text(text = stringResource(id = R.string.wait))
                                    }
                                }

                            }
                        },
                        confirmButton = {

                        },
                        dismissButton = {

                        }
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {

                Text(
                    text = stringResource(R.string.app_name),
                    modifier = Modifier
                        .padding(top = 50.dp),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 40.sp
                )

                OutlinedTextField(
                    value = email,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                        unfocusedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                        disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                    ),
                    onValueChange = {
                        email = it
                        userViewModel.onEvent(
                            AuthEvent.EmailValueEntered(it)
                        )
                    },
                    label = { Text(stringResource(id = R.string.your_email)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    placeholder = { Text("") },
                    leadingIcon = {
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Filled.Email,
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp, bottom = 0.dp, start = 30.dp, end = 30.dp),
                    shape = RoundedCornerShape(12.dp)
                )

                val containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                OutlinedTextField(
                    value = password,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = containerColor,
                        unfocusedContainerColor = containerColor,
                        disabledContainerColor = containerColor,
                    ),
                    onValueChange = {
                        password = it
                        userViewModel.onEvent(
                            AuthEvent.PasswordValueEntered(it)
                        )
                    },
                    label = { Text(stringResource(id = R.string.your_password)) },
                    visualTransformation =
                    if (passwordHidden) {
                        PasswordVisualTransformation()
                    } else {
                        VisualTransformation.None
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    leadingIcon = {
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Filled.Lock,
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    },
                    trailingIcon = {
                        IconButton(onClick = { passwordHidden = !passwordHidden }) {
                            val visibilityIcon =
                                if (passwordHidden) painterResource(id = R.drawable.baseline_visibility_24)
                                else painterResource(id = R.drawable.baseline_visibility_off_24)
                            val description =
                                if (passwordHidden) "Show password" else "Hide password"
                            Icon(painter = visibilityIcon, contentDescription = description)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, bottom = 0.dp, start = 30.dp, end = 30.dp),
                    shape = RoundedCornerShape(12.dp)
                )

                ClickableText(
                    buildAnnotatedString {
                        pushStringAnnotation(
                            tag = "",
                            annotation = ""
                        )
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold,
                                textDecoration = TextDecoration.Underline,
                                fontSize = 15.sp
                            )
                        ) {
                            append(stringResource(R.string.password_forget))
                        }

                        pop()
                    },
                    onClick = {

                    }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, bottom = 0.dp, start = 30.dp, end = 30.dp)
                )

                OutlinedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, bottom = 0.dp, start = 30.dp, end = 30.dp),
                    border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.primary),
                    onClick = {
                        //we init our fields
                        userViewModel.onEvent(
                            AuthEvent.IsInitField(email, password)
                        )
                        //we test if fields is Empties
                        userViewModel.onEvent(
                            AuthEvent.IsEmptyField(getFieldError(context, password, email))
                        )

                        //We initialize the connection parameters
                        userViewModel.onEvent(
                            AuthEvent.ConnectionAction
                        )

                        //We get our token
                        userViewModel.onEvent(
                            AuthEvent.GetToken(
                                app = context,
                                userName = email,
                                password = password
                            )
                        )


                    }) {
                    Icon(
                        imageVector = Icons.Outlined.Login,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text(stringResource(R.string.connexion))
                }

                OutlinedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp, bottom = 0.dp, start = 30.dp, end = 30.dp),
                    border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.primary),
                    onClick = {

                    }) {
                    Icon(
                        imageVector = Icons.Outlined.ManageAccounts,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text(stringResource(R.string.open_account))
                }
            }

            if (screenState.isNetworkError) {
                userViewModel.onEvent(AuthEvent.IsNetworkError(context.getString(R.string.is_connect_error)))
            } else if (!screenState.isNetworkConnected) {
                userViewModel.onEvent(AuthEvent.IsNetworkConnected(context.getString(R.string.network_error)))
            }

            LaunchedEffect(key1 = true) {
                userViewModel.uiEventFlow.collectLatest { event ->
                    when (event) {
                        is UIEvent.ShowMessage -> {
                            scaffoldState.snackbarHostState.showSnackbar(
                                message = event.message
                            )
                        }

                        else -> {}
                    }
                }
            }

            LaunchedEffect(key1 = screenState.user !== null && screenState.token !== null) {
                if (screenState.user !== null && screenState.token !== null) {
                    navController.navigate(Route.homeView)
                }
            }
        })

}