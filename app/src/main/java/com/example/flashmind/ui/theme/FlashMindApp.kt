package com.example.flashmind.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flashmind.data.FlashcardDatabase
import com.example.flashmind.data.FlashcardRepository
import com.example.flashmind.ui.screens.MainScreen
import com.example.flashmind.viewmodel.FlashcardViewModel

@Composable
fun FlashMindApp(viewModel: FlashcardViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        MainScreen(viewModel)
    }
}
