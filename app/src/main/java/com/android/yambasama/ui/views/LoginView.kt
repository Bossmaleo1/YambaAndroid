package com.android.yambasama.ui.views

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.Login
import androidx.compose.material.icons.outlined.ManageAccounts
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavHostController
import com.android.yambasama.R
import com.android.yambasama.R.string.password_forget
import com.android.yambasama.data.model.dataLocal.TokenRoom
import com.android.yambasama.data.model.dataLocal.UserRoom
import com.android.yambasama.data.model.dataRemote.User
import com.android.yambasama.data.util.Resource
import com.android.yambasama.presentation.viewModel.user.UserViewModel
import javax.inject.Inject
import com.android.yambasama.ui.views.model.Route


@Composable
@ExperimentalMaterial3Api
fun Login(navController: NavHostController, userViewModel: UserViewModel, context: Any) {
    var email by rememberSaveable { mutableStateOf("sidneymaleoregis@gmail.com") }
    var password by rememberSaveable { mutableStateOf("Nfkol3324012020@!") }
    var passwordHidden by rememberSaveable { mutableStateOf(true) }
    val isLoading = remember { mutableStateOf(false) }

    fun showProgressBar(){
        isLoading.value = true
    }

    fun hideProgressBar(){
        isLoading.value = false
    }

    Column {
        if (isLoading.value) {
            AlertDialog(
                onDismissRequest = {
                    // Dismiss the dialog when the user clicks outside the dialog or on the back
                    // button. If you want to disable that functionality, simply use an empty
                    // onCloseRequest.
                    isLoading.value = false
                },
                title = {

                },
                text = {
                    Column( modifier = Modifier
                        .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Row() {
                            CircularProgressIndicator()
                            Row(Modifier.padding(10.dp)) {
                                Text(text = "Connexion en cours...")
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

    fun getUser(userViewModel: UserViewModel, userName: String, token: String, context: Any) {
        userViewModel.getUser(userName, token)
        userViewModel.user.observe(context as LifecycleOwner) { user->
            when (user) {
                is Resource.Success -> {
                    Log.d("Test1", "'user':'${user.data?.Users?.get(0)?.lastName}'");
                    val user = user.data?.Users?.get(0) as User
                    //we save the user Token
                    userViewModel.saveToken(
                        TokenRoom(
                            1,
                            //we split the bear characters
                            token.split(" ")[1])
                    )

                    //We save the user
                    userViewModel.saveUser(
                        UserRoom(
                            user.id,
                            user.firstName,
                            user.lastName,
                            user.roles[0],
                            user.phone,
                            user.nationality,
                            user.sex,
                            user.state,
                            token.split(" ")[1],
                            user.email,
                            user.username,
                            user.pushNotifications?.get(0)?.keyPush,
                            user.images[0].imageName
                        )


                    )
                    hideProgressBar()
                    navController.navigate(Route.homeView)
                }

                is Resource.Error -> {
                    hideProgressBar()
                    user.message?.let {
                        Toast.makeText(context as Context, "An error occurred : $it", Toast.LENGTH_LONG)
                            .show()
                    }
                }

                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

    fun viewModelLogin(userViewModel: UserViewModel, userName: String, password: String, context: Any) {
        userViewModel.getToken(userName, password)
        userViewModel.token.observe(context as LifecycleOwner) {token->
            when (token) {
                is Resource.Success -> {
                    Log.d("Test1", "'token':'${token.data?.token}'");
                    token.data?.token?.let {
                        getUser(userViewModel, userName,
                            "Bearer $it",context as LifecycleOwner)
                    }
                }

                is Resource.Error -> {
                    hideProgressBar()
                    token.message?.let {
                        Toast.makeText(context as Context, "An error occurred : $it", Toast.LENGTH_LONG)
                            .show()
                    }
                }

                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
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
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
            ),
            onValueChange = { email = it },
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

        OutlinedTextField(
            value = password,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
            ),
            onValueChange = { password = it },
            label = { Text(stringResource(id = R.string.your_password)) },
            visualTransformation =
            if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
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
                    val description = if (passwordHidden) "Show password" else "Hide password"
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
                        fontWeight = FontWeight.Bold, textDecoration = TextDecoration.Underline,
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
                viewModelLogin(userViewModel, email,password, context)
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

}