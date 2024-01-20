import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.*
import com.example.hairbookfront.R
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hairbookfront.ui.Dimens.largePadding2
import com.example.hairbookfront.ui.Dimens.largePadding3
import com.example.hairbookfront.ui.Dimens.mediumPadding2
import com.example.hairbookfront.ui.Dimens.smallPadding3
import com.example.hairbookfront.theme.HairBookFrontTheme
import com.example.hairbookfront.ui.common.TextFieldPassword
import com.example.hairbookfront.ui.common.AppTextField
import com.example.hairbookfront.ui.common.CustomButton
import com.example.hairbookfront.ui.common.ClickableText


@Composable
fun WelcomePageScreen() {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    Surface(color = MaterialTheme.colorScheme.surface) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Icon
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .padding(bottom = 8.dp)
            )
            // Welcome Text
            Text(
                text = "Welcome To HairBook",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = mediumPadding2)
            )
            Spacer(modifier = Modifier.height(30.dp))

            // Sign In Text
            Text(
                text = "Sign In",
                modifier = Modifier
                    .padding(bottom = smallPadding3)
                    .align(Alignment.CenterHorizontally),
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold // Make the text bold
            )
            //email text field
            AppTextField(
                value = email.value,
                placeholderText = "Email",
                icon = Icons.Outlined.Email,
                onValueChange = { email.value = it })
            //password text field
            TextFieldPassword(
                password = password.value,
                onValueChange = { password.value = it })

            // Sign In Button
            CustomButton(text = "Sign In", onClick = { /*TODO*/ }, icon = null)

            Row {
                Text(
                    text = "New here?",
                    modifier = Modifier,
                    fontSize = 20.sp
                )
                // Clickable Sign Up Text
                ClickableText(text = "Sign Up", onClick = { /*TODO*/ }, color = Color.Cyan, fontSize = 20)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun WelcomePagePreviewDark() {
    HairBookFrontTheme {
        WelcomePageScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun WelcomePagePreviewLight() {
    HairBookFrontTheme {
        WelcomePageScreen()
    }
}