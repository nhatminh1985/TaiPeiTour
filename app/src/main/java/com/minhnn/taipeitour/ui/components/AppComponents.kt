package com.minhnn.taipeitour.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import com.minhnn.taipeitour.R
import com.minhnn.taipeitour.data.entity.Data
import com.minhnn.taipeitour.data.entity.Images
import com.minhnn.taipeitour.ui.theme.Purple40
import com.minhnn.taipeitour.ui.viewmodel.AttractionsViewModel

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
fun NormalTextComponent(textValue: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp),
        text = textValue,
        style = TextStyle(
            fontSize = 8.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = FontFamily.Monospace,
            color = Purple40
        )
    )
}

@Composable
fun HeadingTextComponent(textValue: String, centerAligned: Boolean = false) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp),
        text = textValue,
        style = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        ),
        textAlign = if (centerAligned) TextAlign.Center else TextAlign.Start
    )
}

@Composable
fun InfiniteImageRow(listImages: List<Images>?) {

    val imageUrls = if (listImages?.isEmpty() == true) null else listImages?.map { it.src }

    // Default image resources if imageUrls is null or empty
    val defaultImageResources = listOf(
        R.mipmap.thumbnail
    )

    // Use defaultImageResources if imageUrls is null or empty
    val effectiveImageUrls =
        imageUrls?.filterNotNull().takeIf { !it.isNullOrEmpty() } ?: defaultImageResources

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
    ) {
        itemsIndexed(effectiveImageUrls) { index, imageResource ->
            if (imageResource is Int) {
                // Load drawable resource if imageResource is an Int
                Image(
                    painter = painterResource(id = imageResource),
                    contentDescription = null,
                    modifier = Modifier
                        .fillParentMaxWidth() // Make the image fill the parent max width
                        .height(260.dp),
                    contentScale = ContentScale.Crop
                )
            } else if (imageResource is String) {
                // Load image from URL if imageResource is a String
                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current).data(data = imageResource)
                            .apply(block = fun ImageRequest.Builder.() {
                                placeholder(R.drawable.ic_launcher_background)
                                error(R.drawable.ic_launcher_background)
                            }).build()
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .fillParentMaxWidth() // Make the image fill the parent max width
                        .height(260.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
fun DetailScreen(data: Data, navigateBack: () -> Unit, onUrlClick: (String) -> Unit) {

    DetailHeader(data, navigateBack)

    Column(
        modifier = Modifier
            .fillMaxSize()
        //.padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {

                // List images detail if have
                InfiniteImageRow(data.images)

                Spacer(modifier = Modifier.size(16.dp))

                HeadingTextComponent(textValue = data.name)

                Spacer(modifier = Modifier.size(16.dp))

                NormalTextComponent(textValue = data.introduction ?: "")

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(8.dp),
                    text = "Address: ${data.address}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(8.dp),
                    text = "Last Updated: ${data.modified}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "More details",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Blue,
                    modifier = Modifier.clickable {
                        if (data.official_site.isNullOrEmpty()) {
                            data.url?.let { onUrlClick(it) }
                        } else {
                            onUrlClick(data.official_site)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun EmptyStateComponent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {

        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = null
        )

        HeadingTextComponent(
            textValue =
            stringResource(R.string.no_attractions_found_as_of_now_please_check_in_some_time),
            centerAligned = true
        )
    }
}

@Preview
@Composable
fun EmptyStateComponentPreview() {
    EmptyStateComponent()
}

@Composable
fun LanguageSelector(
    navController: NavController? = null,
    attractionsViewModel: AttractionsViewModel = hiltViewModel()
) {
    var showDialog by remember { mutableStateOf(false) }

    val languages = listOf(
        "zh-tw" to "Traditional Chinese",
        "zh-cn" to "Simplified Chinese",
        "en" to "English",
        "ja" to "Japanese",
        "ko" to "Korean",
        "es" to "Spanish",
        //"id" to "Indonesian",
        "th" to "Thai",
        "vi" to "Vietnamese"
    )

    IconButton(onClick = { showDialog = true }) {
        Icon(
            painter = painterResource(id = R.mipmap.change_language),
            contentDescription = "Select Language",
            tint = Color.Unspecified,
            modifier = Modifier.size(24.dp) // Adjust the size as needed
        )
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Select Language") },
            text = {
                Column {
                    val currentLanguage = attractionsViewModel.language.collectAsState().value
                    languages.forEach { (code, name) ->
                        LanguageOption(
                            name, code, currentLanguage
                        ) { language ->
                            navController?.popBackStack()
                            attractionsViewModel.updateLanguage(language)
                            showDialog = false
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("OK")
                }
            }
        )
    }
}

@Composable
fun LanguageOption(
    languageName: String,
    languageCode: String,
    selectedLanguage: String,
    onSelect: (String) -> Unit
) {
    ClickableText(
        text = AnnotatedString(languageName),
        onClick = { onSelect(languageCode) },
        style = if (selectedLanguage == languageCode) {
            LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.primary)
        } else {
            LocalTextStyle.current
        }
    )
}

@Composable
fun Header() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Blue),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            color = Color.White,
            modifier = Modifier
                .padding(start = 8.dp) // Add 8dp padding to the left
                .weight(1f)
        )
        LanguageSelector()
    }
}

@Composable
fun DetailHeader(item: Data, navigateBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Blue),
        verticalAlignment = Alignment.CenterVertically
    ) {

        BackButton(navigateBack)

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = item.name,
            style = MaterialTheme.typography.titleMedium,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterVertically),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun ItemCard(item: Data, onItemClick: (Data) -> Unit) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onItemClick(item) }
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val imageUrl = item.images?.firstOrNull()?.src ?: ""
        val painter: Painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .placeholder(R.mipmap.thumbnail) // Replace with your placeholder image resource
                .error(R.mipmap.thumbnail) // Replace with your error image resource
                .build()
        )

        Image(
            painter = painter,
            contentDescription = item.name,
            modifier = Modifier
                .size(120.dp),
            //.clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = item.name,
                color = Color.Black,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            item.introduction?.let {
                Text(
                    text = it,
                    color = Color.Gray,
                    maxLines = 2, // Limit to 2 lines
                    overflow = TextOverflow.Ellipsis // Add ellipsis if text is too long
                )
            }
        }
    }
}

@Composable
fun ItemList(items: List<Data>, onItemClick: (Data) -> Unit) {
    LazyColumn {
        items(items) { item ->
            ItemCard(item = item, onItemClick = onItemClick)
        }
    }
}

@Composable
fun WebViewScreen(url: String, navigateBack: () -> Unit) {
    val state = rememberWebViewState(url = url)
    BackHandler(onBack = navigateBack)

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Blue),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BackHandler(onBack = navigateBack)

            Spacer(modifier = Modifier.weight(1f))
        }

        WebView(state = state, modifier = Modifier.fillMaxSize())
    }
}

@Composable
fun BackButton(navigateBack: () -> Unit) {
    BackHandler(onBack = navigateBack)

    IconButton(onClick = navigateBack) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            tint = Color.White
        )
    }
}

