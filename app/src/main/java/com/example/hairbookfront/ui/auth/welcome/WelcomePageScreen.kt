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
import androidx.compose.runtime.getValue
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
import com.example.hairbookfront.ui.common.TextFieldPasswordComponent
import com.example.hairbookfront.ui.common.TextFieldComponent
import com.example.hairbookfront.ui.common.ButtonComponent
import com.example.hairbookfront.ui.common.ClickableText
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.hairbookfront.ui.common.DialogComponent

@Composable
fun WelcomePageScreen(
    viewModel: WelcomeViewModel = hiltViewModel(),
    navController: NavHostController?
) {
    val context = LocalContext.current
    val email by viewModel.email.collectAsStateWithLifecycle()
    val password by viewModel.password.collectAsStateWithLifecycle()
    val emailError by viewModel.emailError.collectAsStateWithLifecycle()
    val passwordError by viewModel.passwordError.collectAsStateWithLifecycle()
    val showOrHidePassword by viewModel.showOrHidePassword.collectAsStateWithLifecycle()
    val showDialog by viewModel.showDialog.collectAsStateWithLifecycle()
    val dialogText by viewModel.dialogText.collectAsStateWithLifecycle()
    val screen by viewModel.screen.collectAsStateWithLifecycle()
    LaunchedEffect(screen) {
        if (screen != "") {
            viewModel.clearScreen()
            navController?.navigate(screen)
        }
    }
    LaunchedEffect(Unit) {
        viewModel
            .toastMessage
            .collect { message ->
                Toast.makeText(
                    context,
                    message,
                    Toast.LENGTH_SHORT,
                ).show()
            }
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
                TextFieldComponent(
                    value = email,
                    placeholderText = "Email",
                    icon = Icons.Outlined.Email,
                    onValueChange = { viewModel.emailChanged(it) },
                    isError = emailError
                )
                TextFieldPasswordComponent(
                    password = password,
                    onValueChange = { viewModel.passwordChanged(it) },
                    isError = passwordError,
                    onIconClicked = { viewModel.showOrHidePassword() },
                    passwordVisibility = showOrHidePassword
                )
                ButtonComponent(text = "Sign In", onClick = {
                    viewModel.login()
                }, icon = null)
                Row {
                    Text(
                        text = "New here?",
                        modifier = Modifier,
                        fontSize = 20.sp
                    )
                    ClickableText(
                        text = " Sign Up",
                        onClick = { viewModel.showOrHideDialog() },
                        color = Color.Cyan,
                        fontSize = 20
                    )
                }

                if (showDialog) {
                    DialogComponent(
                        dialogTitle = "Sign Up",
                        dialogText = "Are you a Barber or a Customer?",
                        numberOfConfirmButtons = 2,
                        textOfConfirmButtons = dialogText,
                        confirmFunctions = listOf(
                            viewModel::signUpCustomerClicked,
                            viewModel::signUpBarberClicked
                        ),
                        onDismissRequest = { viewModel.showOrHideDialog() }
                    )
                }
            }
        }
    )
}