package com.example.hairbookfront.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.hairbookfront.R
import com.example.hairbookfront.data.entity.Article
import com.example.hairbookfront.ui.theme.Purple40


@Composable
fun Loader() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(50.dp)
                .padding(10.dp),
            color = Purple40
        )
    }
}

@Composable
fun TextComponent(textValue: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .wrapContentHeight(), text = textValue,
        style = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal
        )
    )
}

@Composable
fun NewsRowComponent(page: Int, article: Article) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp),
            model = article.urlToImage,
            contentDescription = "image",
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.baseline_broken_image_24),
            error = painterResource(id = R.drawable.baseline_broken_image_24)
        )
        TextComponent(textValue = "Page: $page\n\n ${article.title}")

        Spacer(modifier = Modifier.size(10.dp))

        TextComponent(textValue = article.description ?: "")

        Spacer(modifier = Modifier.weight(1f))

        AuthorDetailsComponent(article.author, article.source?.name)
    }
}


@Composable
fun AuthorDetailsComponent(authorName: String?, sourceName: String?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, bottom = 24.dp)
    ) {
        authorName?.also {
            Text(text = it)
        }
        Spacer(modifier = Modifier.size(10.dp))

        sourceName?.also {
            Text(text = it)
        }
    }
}

@Composable
fun EmptyStateComponent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_app_blocking_24),
            contentDescription = "empty state",
            modifier = Modifier
                .size(50.dp)
                .padding(10.dp),
            tint = Color.Gray
        )
        Text(
            text = "No data found",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal
            )
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoaderPreview() {
    Loader()
}

@Preview(showBackground = true)
@Composable
fun NewsRowComponentPreview() {
    val article =
        Article(
            "author",
            "title",
            "description",
            "url",
            "urlToImage",
            "publishedAt",
            "content",
            null
        )
    NewsRowComponent(0, article)
}