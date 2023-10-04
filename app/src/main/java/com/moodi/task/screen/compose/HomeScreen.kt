@file:OptIn(ExperimentalMaterial3Api::class)

package com.moodi.task.screen.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.moodi.task.data.local.GiphyAppModel
import com.moodi.task.ui.sate.RandomState
import com.moodi.task.ui.sate.search.SearchState

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    searchState: SearchState,
    randomState: RandomState,
    onPressedItem: (GiphyAppModel) -> Unit,
    onSearchClearPress: () -> Unit,
    onQuerySearch: (String) -> Unit
) {

    var searchTextQuery = rememberSaveable { mutableStateOf("") }
    var searchFocusActive = rememberSaveable { mutableStateOf(false) }
    var searchResultList = remember { mutableStateListOf<GiphyAppModel>() }

    if (!searchState.data.isNullOrEmpty()) {
        searchResultList.clear()
        searchResultList.addAll(searchState.data)
    } else if (searchState.loading) {

    } else if (!searchState.error.isNullOrEmpty()) {
        searchResultList.clear()
    }

    Scaffold(
        topBar = {
            Row {
                SearchBar(
                    trailingIcon = {
                        if (searchFocusActive.value &&
                            searchTextQuery.value.isNotEmpty()
                        ) {
                            Icon(
                                modifier = Modifier.clickable {
                                    onSearchClearPress.invoke()
                                    if (searchTextQuery.value.isNotEmpty()) {
                                        searchTextQuery.value = ""
                                        searchFocusActive.value = false
                                    }
                                },
                                imageVector = Icons.Outlined.Clear,
                                contentDescription = null
                            )
                        }
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(text = "Search")
                    },
                    query = searchTextQuery.value,
                    onQueryChange = {
                        searchTextQuery.value = it
                        onQuerySearch.invoke(searchTextQuery.value)
                    }, onSearch = {
                        searchFocusActive.value = false
                    }, active = searchFocusActive.value,
                    onActiveChange = {
                        searchFocusActive.value = it
                    },
                    content = {
                        Column {
                            Text(
                                modifier = Modifier.padding(top = 24.dp, start = 16.dp),
                                text = "Search Results",
                                style = MaterialTheme.typography.labelLarge
                            )
                            SearchResult(searchResultList.toList()) {
                                onPressedItem.invoke(it)
                            }
                        }
                    }
                )
            }
        },
        content = {
            RandomGiphyView(
                randomState = randomState,
            )
        })

}

@Composable
fun RandomGiphyView(
    randomState: RandomState
) {

    when (randomState) {
        is RandomState.Empty -> {}
        is RandomState.Loading -> {
            CircularLoader()
        }

        is RandomState.NetworkError -> {
        }

        is RandomState.Success -> {
            GiphySection(
                giphyAppModel = randomState.data,
                title = "Random Gif"
            )
        }
    }
}

@Composable
private fun CircularLoader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun GiphySection(giphyAppModel: GiphyAppModel?, title: String) {
    Column {
        Spacer(Modifier.height(64.dp))
        Text(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
            text = title,
            style = MaterialTheme.typography.labelLarge
        )
        Column {
            AsyncImage(
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f),
                model = giphyAppModel?.animatedUrl,
                contentDescription = null
            )
            BottomGiphSection(giphyAppModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomGiphSection(randomGifState: GiphyAppModel?) {
    Row(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = randomGifState?.title ?: "",
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = randomGifState?.displayLink ?: "",
                style = MaterialTheme.typography.labelSmall
            )
        }
        Badge(
            content = {
                Text(
                    text = randomGifState?.ageRate ?: "",
                    style = MaterialTheme.typography.labelSmall
                )
            },
        )

    }
}

@Composable
fun SearchResult(
    searchList: List<GiphyAppModel>,
    onItemClick: (GiphyAppModel) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(searchList) {
            Surface(
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier.padding(4.dp),
            ) {
                AsyncImage(
                    modifier = Modifier.clickable {
                        onItemClick.invoke(it)
                    },
                    contentScale = ContentScale.Crop,
                    model = it.stillUrl,
                    contentDescription = null,
                )
            }
        }

    }


}

@Preview(showBackground = true, widthDp = 300)
@Composable
fun PreviewHomeScreen() {
    BottomGiphSection(
        GiphyAppModel(
            "1",
            "12+",
            "This is the title",
            "https://media0.giphy.com/media/3o7aD2X",
            "https://www.google.com",
            "https://media0.giphy.com/media/3o7aD2X"
        )
    )
}

fun Modifier.badgeLayout() =
    layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)

        // based on the expectation of only one line of text
        val minPadding = placeable.height / 4

        val width = maxOf(placeable.width + minPadding, placeable.height)
        layout(width, placeable.height) {
            placeable.place((width - placeable.width) / 2, 0)
        }
    }