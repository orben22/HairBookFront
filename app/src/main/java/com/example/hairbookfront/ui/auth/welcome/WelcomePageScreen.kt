package com.example.hairbookfront.ui.auth.welcome

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.*
import com.example.hairbookfront.R
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.hairbookfront.ui.Dimens.mediumPadding2
import com.example.hairbookfront.ui.Dimens.smallPadding3
import com.example.hairbookfront.ui.Dimens.largePadding3
import com.example.hairbookfront.ui.common.TextFieldPassword
import com.example.hairbookfront.ui.common.AppTextField
import com.example.hairbookfront.ui.common.CustomButton
import com.example.hairbookfront.ui.common.ClickableText
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.hairbookfront.ui.common.AppDialog

@Composable
fun WelcomePageScreen(
    welcomeViewModel: WelcomeViewModel = hiltViewModel(),
    navController: NavHostController?
) {
    val context = LocalContext.current
    val email = welcomeViewModel.email.collectAsStateWithLifecycle()
    val password = welcomeViewModel.password.collectAsStateWithLifecycle()
    val emailError = welcomeViewModel.emailError.collectAsStateWithLifecycle()
    val passwordError = welcomeViewModel.passwordError.collectAsStateWithLifecycle()
    val showOrHidePassword = welcomeViewModel.showOrHidePassword.collectAsStateWithLifecycle()
    val showDialog = welcomeViewModel.showDialog.collectAsStateWithLifecycle()
    val dialogText = welcomeViewModel.dialogText.collectAsStateWithLifecycle()
    val signUpScreen = welcomeViewModel.signUpScreen.collectAsStateWithLifecycle()
    val homeScreen = welcomeViewModel.homeScreen.collectAsStateWithLifecycle()
    if (homeScreen.value != "") {
        navController?.navigate(homeScreen.value)
    }
    LaunchedEffect(Unit) {
        welcomeViewModel
            .toastMessage
            .collect { message ->
                Toast.makeText(
                    context,
                    message,
                    Toast.LENGTH_SHORT,
                ).show()
            }
    }
    if (signUpScreen.value != "") {
        navController?.navigate(signUpScreen.value)
    }
    Scaffold(
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(largePadding3))
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_round),
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                        .padding(bottom = 8.dp)
                )
                Text(
                    text = "Welcome To HairBook",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = mediumPadding2)
                )
                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = "Sign In",
                    modifier = Modifier
                        .padding(bottom = smallPadding3)
                        .align(Alignment.CenterHorizontally),
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold
                )
                AppTextField(
                    value = email.value,
                    placeholderText = "Email",
                    icon = Icons.Outlined.Email,
                    onValueChange = { welcomeViewModel.emailChanged(it) },
                    isError = emailError.value
                )
                TextFieldPassword(
                    password = password.value,
                    onValueChange = { welcomeViewModel.passwordChanged(it) },
                    isError = passwordError.value,
                    onIconClicked = { welcomeViewModel.showOrHidePassword() },
                    passwordVisibility = showOrHidePassword.value
                )
                CustomButton(text = "Sign In", onClick = {
                    welcomeViewModel.login()
                }, icon = null)
                Row {
                    Text(
                        text = "New here?",
                        modifier = Modifier,
                        fontSize = 20.sp
                    )
                    ClickableText(
                        text = " Sign Up",
                        onClick = { welcomeViewModel.showOrHideDialog() },
                        color = Color.Cyan,
                        fontSize = 20
                    )
                }

                if (showDialog.value) {
                    AppDialog(
                        dialogTitle = "Sign Up",
                        dialogText = "Are you a Barber or a Customer?",
                        numberOfConfirmButtons = 2,
                        textOfConfirmButtons = dialogText.value,
                        confirmFunctions = listOf(
                            welcomeViewModel::signUpCustomerClicked,
                            welcomeViewModel::signUpBarberClicked
                        ),
                        onDismissRequest = { welcomeViewModel.showOrHideDialog() }
                    )
                }
            }
        }
    )
}