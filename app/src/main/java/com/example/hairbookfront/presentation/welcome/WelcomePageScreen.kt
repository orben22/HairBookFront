import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import com.example.hairbookfront.R
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hairbookfront.presentation.Dimens.largePadding2
import com.example.hairbookfront.presentation.Dimens.largePadding3
import com.example.hairbookfront.presentation.Dimens.mediumPadding2
import com.example.hairbookfront.presentation.Dimens.smallPadding3
import com.example.hairbookfront.theme.HairBookFrontTheme

@Composable
fun WelcomePageScreen() {
    Surface(color = MaterialTheme.colorScheme.surface) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(smallPadding3, largePadding2, smallPadding3, largePadding3),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Icon
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground), // replace with your icon
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

            // Sign In Button
            Button(
                onClick = {
                    // Handle Sign In button click
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = smallPadding3)
            ) {
                Text(text = "Sign In")
            }

            // Sign Up Button
            Button(
                onClick = {
                    // Handle Sign Up button click
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Sign Up")
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