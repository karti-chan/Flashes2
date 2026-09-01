package com.example.flashmind

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flashmind.data.FlashcardDatabase
import com.example.flashmind.data.FlashcardRepository
import com.example.flashmind.ui.theme.FlashMindApp
import com.example.flashmind.ui.theme.FlashMindTheme
import com.example.flashmind.viewmodel.FlashcardViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val database = FlashcardDatabase.getDatabase(context)
            val dao = database.flashcardDao()
            val repository = FlashcardRepository(dao)
            
            val viewModel: FlashcardViewModel = viewModel(
                factory = FlashcardViewModel.Factory(repository)
            )

            FlashMindTheme(viewModel = viewModel) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    FlashMindApp(viewModel = viewModel)
                }
            }
        }
    }
}
