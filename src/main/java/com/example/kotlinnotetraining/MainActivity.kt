package com.example.kotlinnotetraining

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
fun MemoItem(item:String){
    Card(){
        TextField(
            value = item,
            onValueChange = { newMemo: String -> item = newMemo }
        )
    }
}

@Composable
fun MemoList(){
    val memoList = List(10){index ->
        listOf("memo$index")
    }
    LazyVerticalGrid(

    ) { items(memoList.size)
    {
        memo -> MemoItem(memoList[memo])
    }}

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
        MemoItem()
    }
}

