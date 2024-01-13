package com.example.hairbookfront.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.hairbookfront.R
import com.example.hairbookfront.domain.model.Article
import com.example.hairbookfront.presentation.Dimens.fontMedium
import com.example.hairbookfront.presentation.Dimens.mediumPadding1
import com.example.hairbookfront.presentation.Dimens.smallPadding1
import com.example.hairbookfront.presentation.Dimens.smallPadding2

@Composable
fun NewsRowComponent(page: Int, article: Article) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(smallPadding1)
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
fun TextComponent(textValue: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .wrapContentHeight(), text = textValue,
        style = TextStyle(
            fontSize = fontMedium,
            fontWeight = FontWeight.Normal
        )
    )
}

@Composable
fun AuthorDetailsComponent(authorName: String?, sourceName: String?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = smallPadding1, end = smallPadding1, bottom = mediumPadding1)
    ) {
        authorName?.also {
            Text(text = it)
        }
        Spacer(modifier = Modifier.size(smallPadding2))

        sourceName?.also {
            Text(text = it)
        }
    }
}