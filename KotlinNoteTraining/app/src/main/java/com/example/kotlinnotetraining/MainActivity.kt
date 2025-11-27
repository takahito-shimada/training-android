package com.example.kotlinnotetraining

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.util.TableInfo
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
    val memoList = remember {
        mutableStateListOf(*Array(10) { index -> "memo$index" })
    }

    val navigationController = rememberNavController()
    NavHost(
        navController = navigationController,
        startDestination = "list"
    ) {
        composable("list") {
            MemoListScreen(
                memoList = memoList,
                onClick = { index -> navigationController.navigate("detail/$index")},
                onAddClick={
                    val newIndex = memoList.size
                    memoList.add("")
                    navigationController.navigate("detail/$newIndex")
                },
                onDeleteClick = { index ->
                    memoList.removeAt(index)
                }
            )
        }
        composable("detail/{index}", arguments = listOf(navArgument("index") { type = NavType.IntType})) { backStackEntry ->
            val index = backStackEntry.arguments?.getInt("index") ?: return@composable
            MemoDetail(text = memoList[index], onTextChange = {newValue -> memoList[index] = newValue})
        }
    }
}

@Composable
fun MemoItem(
    index: Int,
    item: String,
    onClick: (Int) -> Unit,
    onDeleteClick: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .size(120.dp)
            .clip(RoundedCornerShape(10.dp))

    ) {
        Card(
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
            modifier = Modifier
                .padding(12.dp)
                .fillMaxSize()
                .clickable(onClick = { onClick(index) })
        ) {
            Text(item,
                modifier = Modifier.padding(12.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis)
        }
        IconButton(
            onClick = { onDeleteClick(index) },
            modifier = Modifier
                .padding(16.dp)
                .size(width = 30.dp, height = 30.dp)
                .align(Alignment.BottomEnd)
        ) {
            Icon(Icons.Default.Delete, contentDescription = null)
        }
    }
}

@Composable
fun MemoList(
    memoList: List<String>,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit,
    onDeleteClick: (Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier

    ) {
        items(memoList.size)
        { memo ->
            MemoItem(index = memo,memoList[memo], onClick = onClick, onDeleteClick = onDeleteClick)
        }
    }

}

@Composable
fun MemoDetail(
    text: String,
    onTextChange: (String) -> Unit
) {
    var newText by remember { mutableStateOf(text)  }
    val imageList = listOf(R.drawable.background, R.drawable.road_bg, R.drawable.nature_bg)
    val bgImage = remember { imageList.random() }

    Box(){
        Image(
            painter = painterResource(bgImage),
            contentDescription = "背景",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Card(
            modifier = Modifier.fillMaxSize()
                .padding(vertical = 50.dp)
                .padding(30.dp)
                .alpha(0.9f)
        ) {

            TextField(
                value = newText,
                onValueChange = {newValue ->
                    newText = newValue
                    onTextChange(newValue) },
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



}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemoListScreen(
    memoList: List<String>,
    onAddClick: () -> Unit,
    onClick: (Int) -> Unit,
    onDeleteClick: (Int) -> Unit
) {
    var query by remember { mutableStateOf("") }
    val filteredMemoList = memoList.filter { it.contains(query) }

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
                onClick = { onAddClick() },
            ) {
                Icon(Icons.Default.Add, contentDescription = "new")
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            )
        {
            Column(modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()) {
                OutlinedTextField(
                    value = query,
                    onValueChange = {value -> query = value },
                    label = {Text("search")},
                    modifier = Modifier.fillMaxWidth().padding( 16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        )
                )

                MemoList(
                    memoList = filteredMemoList,
                    onClick = onClick,
                    onDeleteClick = onDeleteClick
                )
            }
        }


    }
}


//@Preview(showBackground = true)
//@Composable
//fun MemoItemPreview() {
//    KotlinNoteTrainingTheme {
//        MemoItem("メモ", onClick = {})
//    }
//}

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
        MemoDetail(text = "memo", onTextChange = {})
    }
}

//@Preview(showBackground = true, widthDp = 320, heightDp = 450)
//@Composable
//fun MemoListScreenPreview() {
//    KotlinNoteTrainingTheme {
//        MemoListScreen()
//    }
//}


