package com.example.kotlinnotetraining

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.util.copy
import com.example.kotlinnotetraining.ui.theme.KotlinNoteTrainingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KotlinNoteTrainingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MemoApp()
                }
            }
        }
    }
}

@Composable
fun MemoApp() {
    var memoList by remember {
        mutableStateOf(List(10) { index ->
            "memo$index"
        })
    }
    val navigationController = rememberNavController()
    NavHost(
        navController = navigationController,
        startDestination = "list"
    ) {
        composable("list") {
            MemoListScreen(memoList = memoList, onClick = { index -> navigationController.navigate("detail/$index")})
        }
        composable("detail/{index}", arguments = listOf(navArgument("index") { type = NavType.IntType})) { backStackEntry ->
            val index = backStackEntry.arguments?.getInt("index") ?: return@composable
            MemoDetail(text = memoList[index])
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
fun MemoItem(item: String, onClick: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .padding(12.dp)
            .size(120.dp)
            .clickable(onClick = { onClick })
    ) {
        Text(item, modifier = Modifier.padding(12.dp))
    }
}

@Composable
fun MemoList(
    memoList: List<String>,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier

    ) {
        items(memoList.size)
        { memo ->
            MemoItem(memoList[memo], onClick = onClick)
        }
    }

}

@Composable
fun MemoDetail(
    text: String
) {
    Card(
    ) {
        TextField(
            value = text,
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
fun MemoListScreen(
    memoList: List<String>,
    onClick: (Int) -> Unit
) {
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
            modifier = Modifier.padding(innerPadding),
            memoList = memoList,
            onClick = onClick
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
        MemoItem("メモ", onClick = {})
    }
}

//@Preview(showBackground = true, widthDp = 320, backgroundColor = 0xFFCCC2DC)
//@Composable
//fun MemoListPreview() {
//    KotlinNoteTrainingTheme {
//        MemoList()
//    }
//}

@Preview(showBackground = true, widthDp = 320, heightDp = 450)
@Composable
fun MemoDetailPreview() {
    KotlinNoteTrainingTheme {
        MemoDetail(text = "memo")
    }
}

//@Preview(showBackground = true, widthDp = 320, heightDp = 450)
//@Composable
//fun MemoListScreenPreview() {
//    KotlinNoteTrainingTheme {
//        MemoListScreen()
//    }
//}


