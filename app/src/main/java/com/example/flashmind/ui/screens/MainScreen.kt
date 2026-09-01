package com.example.flashmind.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flashmind.ui.components.*
import com.example.flashmind.viewmodel.FlashcardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: FlashcardViewModel) {
    val subjects = viewModel.subjects
    val selectedSubjectId = viewModel.selectedSubjectId
    val selectedSetId = viewModel.selectedSetId
    val studyState = viewModel.studyState
    
    Scaffold(
        topBar = {
            if (studyState.isStudying || selectedSetId.isNotEmpty() || selectedSubjectId.isNotEmpty()) {
                // Custom app bar handled in detail views
            } else {
                TopAppBar(
                    title = {
                        Text(
                            text = "FlashMind",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    actions = {
                        IconButton(onClick = { viewModel.toggleTheme() }) {
                            val icon = when (viewModel.isDarkMode) {
                                true -> "🌙"
                                false -> "☀️"
                                else -> "🌗"
                            }
                            Text(icon, fontSize = 20.sp)
                        }
                        
                        SubjectCounter(count = subjects.size) {
                            viewModel.showAddSubjectDialog()
                        }
                    }
                )
            }
        },
        floatingActionButton = {
            if (!studyState.isStudying && selectedSetId.isEmpty() && selectedSubjectId.isEmpty()) {
                ExtendedFloatingActionButton(
                    onClick = { viewModel.showAddSubjectDialog() },
                    icon = { Icon(Icons.Default.Add, contentDescription = null) },
                    text = { Text("New Subject") },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp),
        ) {
            // Content Dispatcher
            when {
                studyState.isStudying -> StudyView(viewModel)
                selectedSetId.isNotEmpty() -> SetDetailView(viewModel, selectedSetId)
                selectedSubjectId.isNotEmpty() -> SubjectDetailView(viewModel, selectedSubjectId)
                else -> SubjectListScreen(viewModel)
            }

            // Global Dialogs
            if (viewModel.isAddSubjectDialogOpen) {
                SubjectCreationDialog(viewModel)
            }
        }
    }
}

@Composable
fun SubjectListScreen(viewModel: FlashcardViewModel) {
    Column {
        if (viewModel.subjects.isEmpty()) {
            EmptyState(message = "No subjects yet. Create one to start!")
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(viewModel.subjects) { subject ->
                    SubjectCard(
                        subject = subject,
                        onClick = { viewModel.selectSubject(subject.id) },
                        onDelete = { viewModel.deleteSubject(subject.id) }
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        ProgressSection()
    }
}

@Composable
fun SubjectCounter(count: Int, onClick: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(30.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier.clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "$count subjects ready",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text("+", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun ProgressSection() {
    Column {
        Text(
            text = "Your Progress",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(8.dp))
        LinearProgressIndicator(
            progress = { 0.5f },
            modifier = Modifier.fillMaxWidth().height(8.dp),
            strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
        )
    }
}

@Composable
fun SubjectCreationDialog(viewModel: FlashcardViewModel) {
    AlertDialog(
        onDismissRequest = { viewModel.dismissAddSubjectDialog() },
        title = { Text("Create New Subject") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = viewModel.newSubjectTitle,
                    onValueChange = { viewModel.updateNewSubjectTitle(it) },
                    label = { Text("Subject Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = viewModel.newSubjectDescription,
                    onValueChange = { viewModel.updateNewSubjectDescription(it) },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { viewModel.confirmAddSubject() },
                enabled = viewModel.newSubjectTitle.isNotBlank()
            ) {
                Text("Create")
            }
        },
        dismissButton = {
            TextButton(onClick = { viewModel.dismissAddSubjectDialog() }) {
                Text("Cancel")
            }
        }
    )
}
