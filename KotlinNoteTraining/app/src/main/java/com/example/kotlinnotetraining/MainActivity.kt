package com.example.kotlinnotetraining

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.room.util.copy
import com.example.kotlinnotetraining.ui.theme.KotlinNoteTrainingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KotlinNoteTrainingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun MemoItem(item: String) {
    Card(
        modifier = Modifier
            .padding(12.dp)
            .size(120.dp),
    ) {
        Text(item, modifier = Modifier.padding(12.dp))
    }
}

@Composable
fun MemoList(
    modifier: Modifier = Modifier
) {
    var memoList by remember {
        mutableStateOf(List(10) { index ->
            "memo$index"
        })
    }
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier

    ) {
        items(memoList.size)
        { memo ->
            MemoItem(memoList[memo])
        }
    }

}

@Composable
fun MemoDetail() {
    Card(
    ) {
        TextField(
            value = "メモ",
            onValueChange = {},
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemoListScreen() {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    "Memo App",
                    color = MaterialTheme.colorScheme.tertiary,
                    fontWeight = FontWeight.Bold
                )
            })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
            ) {
                Icon(Icons.Default.Add, contentDescription = "new")
            }
        }
    ) { innerPadding ->
        MemoList(
            modifier = Modifier.padding(innerPadding)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KotlinNoteTrainingTheme {
        Greeting("Android")
    }
}

@Preview(showBackground = true)
@Composable
fun MemoItemPreview() {
    KotlinNoteTrainingTheme {
        MemoItem("メモ")
    }
}

@Preview(showBackground = true, widthDp = 320, backgroundColor = 0xFFCCC2DC)
@Composable
fun MemoListPreview() {
    KotlinNoteTrainingTheme {
        MemoList()
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 450)
@Composable
fun MemoDetailPreview() {
    KotlinNoteTrainingTheme {
        MemoDetail()
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 450)
@Composable
fun MemoListScreenPreview() {
    KotlinNoteTrainingTheme {
        MemoListScreen()
    }
}


