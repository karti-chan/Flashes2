package com.example.flashmind.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.flashmind.viewmodel.FlashcardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetDetailView(viewModel: FlashcardViewModel, setId: String) {
    val set = viewModel.sets.find { it.id == setId }
    val cards by viewModel.getCards(setId).collectAsState(initial = emptyList())
    
    set?.let { currentSet ->
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Column {
                            Text(text = currentSet.title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                            Text(text = currentSet.description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { viewModel.deselectSet() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    actions = {
                        IconButton(onClick = { viewModel.showAddCardDialog() }) {
                            Icon(Icons.Default.Add, contentDescription = "Add Card")
                        }
                    }
                )
            },
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    onClick = { viewModel.showAddCardDialog() },
                    icon = { Icon(Icons.Default.Add, contentDescription = null) },
                    text = { Text("New Card") }
                )
            }
        ) { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
                if (cards.isEmpty()) {
                    EmptyState(message = "No cards here. Tap + to add your first one!")
                } else {
                    // Study Button
                    Button(
                        onClick = { viewModel.startStudy() },
                        modifier = Modifier.fillMaxWidth(),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(60.dp)
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Start Studying (${cards.size} cards)")
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(
                        text = "Cards Preview",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        itemsIndexed(cards) { index, card ->
                            CardPreviewItem(
                                index = index + 1,
                                card = card,
                                onEdit = { viewModel.showEditCardDialog(index) },
                                onDelete = { viewModel.deleteCard(currentSet.id, index) },
                                onTypeToggle = { viewModel.toggleCardType(currentSet.id, index) }
                            )
                        }
                    }
                }

                if (viewModel.isAddCardDialogOpen) {
                    CardCreationDialog(viewModel)
                }
            }
        }
    }
}

@Composable
fun CardCreationDialog(viewModel: FlashcardViewModel) {
    AlertDialog(
        onDismissRequest = { viewModel.dismissAddCardDialog() },
        title = { Text(if (viewModel.isEditingCard) "Edit Flashcard" else "New Flashcard") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                // Card Type Selector
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    com.example.flashmind.data.CardType.entries.forEach { type ->
                        FilterChip(
                            selected = viewModel.newCardType == type,
                            onClick = { viewModel.updateNewCardType(type) },
                            label = { Text(type.name.lowercase()) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                Text("Front (Question)", style = MaterialTheme.typography.labelMedium)
                OutlinedTextField(
                    value = viewModel.newCardQuestion,
                    onValueChange = { viewModel.updateNewCardQuestion(it) },
                    modifier = Modifier.fillMaxWidth()
                )
                MathShortcutRow { viewModel.insertMathToQuestion(it) }
                FormulaPreview(text = viewModel.newCardQuestion)
                
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                Text("Back (Answer)", style = MaterialTheme.typography.labelMedium)
                OutlinedTextField(
                    value = viewModel.newCardAnswer,
                    onValueChange = { viewModel.updateNewCardAnswer(it) },
                    modifier = Modifier.fillMaxWidth()
                )
                MathShortcutRow { viewModel.insertMathToAnswer(it) }
                FormulaPreview(text = viewModel.newCardAnswer)
            }
        },
        confirmButton = {
            Button(
                onClick = { viewModel.confirmAddCard() },
                enabled = viewModel.newCardQuestion.isNotBlank() && viewModel.newCardAnswer.isNotBlank()
            ) {
                Text(if (viewModel.isEditingCard) "Update" else "Add Card")
            }
        },
        dismissButton = {
            TextButton(onClick = { viewModel.dismissAddCardDialog() }) {
                Text("Cancel")
            }
        }
    )
}
