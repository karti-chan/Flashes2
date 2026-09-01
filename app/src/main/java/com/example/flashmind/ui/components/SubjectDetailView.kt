package com.example.flashmind.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.flashmind.data.GroupType
import com.example.flashmind.viewmodel.FlashcardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectDetailView(viewModel: FlashcardViewModel, subjectId: String) {
    val subject = viewModel.subjects.find { it.id == subjectId }
    val allSets = viewModel.sets.filter { it.subjectId == subjectId }
    
    val symbolsSets = allSets.filter { it.type == GroupType.SYMBOLS }
    val flashcardsSets = allSets.filter { it.type == GroupType.FLASHCARDS }
    
    subject?.let {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Column {
                            Text(text = it.title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                            Text(text = it.description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { viewModel.deselectSubject() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            }
        ) { paddingValues ->
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues).padding(horizontal = 16.dp)) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 80.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        SectionHeader("SYMBOLS") {
                            viewModel.showAddSetDialog(GroupType.SYMBOLS)
                        }
                    }
                    if (symbolsSets.isEmpty()) {
                        item { Text("No symbol sets here. Tap + to add.", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.outline) }
                    } else {
                        items(symbolsSets) { set ->
                            SetCard(
                                set = set,
                                onClick = { viewModel.selectSet(set.id) },
                                onDelete = { viewModel.deleteSet(set.id) }
                            )
                        }
                    }
                    
                    item {
                        SectionHeader("FLASHCARDS") {
                            viewModel.showAddSetDialog(GroupType.FLASHCARDS)
                        }
                    }
                    if (flashcardsSets.isEmpty()) {
                        item { Text("No flashcard sets here. Tap + to add.", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.outline) }
                    } else {
                        items(flashcardsSets) { set ->
                            SetCard(
                                set = set,
                                onClick = { viewModel.selectSet(set.id) },
                                onDelete = { viewModel.deleteSet(set.id) }
                            )
                        }
                    }
                }

                if (viewModel.isAddSetDialogOpen) {
                    SetCreationDialog(viewModel, subjectId)
                }
            }
        }
    }
}

@Composable
fun SetCreationDialog(viewModel: FlashcardViewModel, subjectId: String) {
    AlertDialog(
        onDismissRequest = { viewModel.dismissAddSetDialog() },
        title = { Text("New Set in ${viewModel.newSetGroupType.name}") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = viewModel.newSetTitle,
                    onValueChange = { viewModel.updateNewSetTitle(it) },
                    label = { Text("Set Name (e.g. Kinematyka)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = viewModel.newSetDescription,
                    onValueChange = { viewModel.updateNewSetDescription(it) },
                    label = { Text("Short Description") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { viewModel.confirmAddSet(subjectId) },
                enabled = viewModel.newSetTitle.isNotBlank()
            ) {
                Text("Add Set")
            }
        },
        dismissButton = {
            TextButton(onClick = { viewModel.dismissAddSetDialog() }) {
                Text("Cancel")
            }
        }
    )
}
