import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.*
import com.example.hairbookfront.R
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.hairbookfront.ui.Dimens.mediumPadding2
import com.example.hairbookfront.ui.Dimens.smallPadding3
import com.example.hairbookfront.theme.HairBookFrontTheme
import com.example.hairbookfront.ui.Dimens.largePadding3
import com.example.hairbookfront.ui.common.TextFieldPassword
import com.example.hairbookfront.ui.common.AppTextField
import com.example.hairbookfront.ui.common.CustomButton
import com.example.hairbookfront.ui.common.ClickableText
import com.example.hairbookfront.ui.auth.welcome.WelcomeViewModel
import com.example.hairbookfront.ui.navgraph.Routes
import kotlinx.coroutines.launch


@Composable
fun WelcomePageScreen(
    welcomeViewModel: WelcomeViewModel = hiltViewModel(),
    navController: NavHostController?
) {
    val context = LocalContext.current
    val email = welcomeViewModel.email.collectAsState()
    val password = welcomeViewModel.password.collectAsState()
    val emailError = welcomeViewModel.emailError.collectAsState()
    val passwordError = welcomeViewModel.passwordError.collectAsState()
    val showOrHidePassword = welcomeViewModel.showOrHidePassword.collectAsState()
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
                    welcomeViewModel.viewModelScope.launch {
                        if (welcomeViewModel.login()) {
                            navController?.navigate(Routes.CustomerGraph.route)
                        }
                    }
                }, icon = null)
                Row {
                    Text(
                        text = "New here?",
                        modifier = Modifier,
                        fontSize = 20.sp
                    )
                    ClickableText(
                        text = " Sign Up",
                        onClick = { /*TODO*/ },
                        color = Color.Cyan,
                        fontSize = 20
                    )
                }
            }
        }
    )
}


@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun WelcomePagePreviewDark() {
    HairBookFrontTheme {
        WelcomePageScreen(navController = null)
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun WelcomePagePreviewLight() {
    HairBookFrontTheme {
        WelcomePageScreen(navController = null)
    }
}