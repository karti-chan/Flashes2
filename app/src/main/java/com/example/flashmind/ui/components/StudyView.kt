package com.example.flashmind.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.font.FontWeight
import com.example.flashmind.data.CardType
import com.example.flashmind.viewmodel.FlashcardViewModel
import android.widget.Toast

@Composable
fun StudyView(viewModel: FlashcardViewModel) {
    val studyState = viewModel.studyState
    val cards = studyState.sessionCards
    
    if (studyState.currentCardIndex >= cards.size) {
        // Finished
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "🎉 Session Complete!",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { viewModel.quitStudy() },
                shape = RoundedCornerShape(60.dp)
            ) {
                Text("Back to Set")
            }
        }
        return
    }
    
    val card = cards[studyState.currentCardIndex]
    var userAnswer by remember(studyState.currentCardIndex) { mutableStateOf("") }
    var tracePath by remember(studyState.currentCardIndex) { mutableStateOf(Path()) }
    var isTracing by remember(studyState.currentCardIndex) { mutableStateOf(false) }
    var isAnswerRevealed by remember(studyState.currentCardIndex) { mutableStateOf(false) }
    val context = LocalContext.current

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val isLandscape = maxWidth > maxHeight
        val strokeColor = MaterialTheme.colorScheme.onSurface
        
        Card(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(if (isLandscape) 12.dp else 24.dp)
            ) {
                // Progress
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${studyState.currentCardIndex + 1} / ${cards.size}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = if (card.type == CardType.TEXT) "📝 Text" else "✏️ Trace",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                
                Spacer(modifier = Modifier.height(if (isLandscape) 8.dp else 20.dp))
                
                // Card Content
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    shape = RoundedCornerShape(32.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    if (isLandscape) {
                        Row(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                            // Left side: Question
                            Column(
                                modifier = Modifier
                                    .weight(0.4f)
                                    .fillMaxHeight()
                                    .verticalScroll(rememberScrollState()),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(40.dp),
                                    color = MaterialTheme.colorScheme.surfaceVariant
                                ) {
                                    Text(
                                        text = if (card.type == CardType.TEXT) "Type answer" else "Trace character",
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                                        fontSize = 12.sp
                                    )
                                }
                                Spacer(modifier = Modifier.height(12.dp))
                                SmartText(
                                    text = card.question,
                                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold)
                                )
                                if (isAnswerRevealed) {
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Column {
                                        Text(
                                            text = "Answer: ",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                        SmartText(
                                            text = card.answer,
                                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }
                            }
                            
                            Spacer(modifier = Modifier.width(16.dp))
                            
                            // Right side: Interactive
                            Column(
                                modifier = Modifier.weight(0.6f).fillMaxHeight(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                if (card.type == CardType.TEXT) {
                                    if (!isAnswerRevealed) {
                                        OutlinedTextField(
                                            value = userAnswer,
                                            onValueChange = { userAnswer = it },
                                            placeholder = { Text("Answer...") },
                                            modifier = Modifier.fillMaxWidth(),
                                            singleLine = true,
                                            shape = RoundedCornerShape(60.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.weight(1f))
                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        if (isAnswerRevealed) {
                                            Button(
                                                onClick = { viewModel.nextCard() },
                                                modifier = Modifier.fillMaxWidth(),
                                                shape = RoundedCornerShape(60.dp)
                                            ) { Text("Next Card →") }
                                        } else {
                                            Button(
                                                onClick = {
                                                    if (userAnswer.trim().equals(card.answer, ignoreCase = true)) {
                                                        Toast.makeText(context, "✅ Correct!", Toast.LENGTH_SHORT).show()
                                                        viewModel.nextCard()
                                                        userAnswer = ""
                                                    } else {
                                                        Toast.makeText(context, "❌ Try again!", Toast.LENGTH_SHORT).show()
                                                    }
                                                },
                                                modifier = Modifier.weight(1f),
                                                shape = RoundedCornerShape(60.dp)
                                            ) { Text("Check") }
                                            OutlinedButton(
                                                onClick = { isAnswerRevealed = true },
                                                modifier = Modifier.weight(1f),
                                                shape = RoundedCornerShape(60.dp)
                                            ) { Text("Reveal") }
                                        }
                                    }
                                } else {
                                    // TRACE in landscape
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .weight(1f)
                                            .clip(RoundedCornerShape(24.dp))
                                            .background(MaterialTheme.colorScheme.surfaceVariant)
                                    ) {
                                        Canvas(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .pointerInput(studyState.currentCardIndex) {
                                                    if (!isAnswerRevealed) {
                                                        detectDragGestures(
                                                            onDragStart = { offset ->
                                                                isTracing = true
                                                                tracePath.moveTo(offset.x, offset.y)
                                                            },
                                                            onDrag = { change, _ ->
                                                                tracePath.lineTo(change.position.x, change.position.y)
                                                                val p = tracePath
                                                                tracePath = Path().apply { addPath(p) }
                                                            }
                                                        )
                                                    }
                                                }
                                        ) {
                                            drawPath(
                                                path = tracePath,
                                                color = strokeColor,
                                                style = Stroke(
                                                    width = 6.dp.toPx(),
                                                    cap = StrokeCap.Round,
                                                    join = StrokeJoin.Round
                                                )
                                            )
                                        }
                                        if (!isTracing && !isAnswerRevealed) {
                                            Text(
                                                "Draw here",
                                                modifier = Modifier.align(Alignment.Center),
                                                color = MaterialTheme.colorScheme.outline
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        if (isAnswerRevealed) {
                                            Button(
                                                onClick = { viewModel.nextCard() },
                                                modifier = Modifier.fillMaxWidth(),
                                                shape = RoundedCornerShape(60.dp)
                                            ) { Text("Next Card →") }
                                        } else {
                                            Button(
                                                onClick = { isAnswerRevealed = true },
                                                modifier = Modifier.weight(1f),
                                                shape = RoundedCornerShape(60.dp)
                                            ) { Text("Reveal") }
                                            OutlinedButton(
                                                onClick = { tracePath = Path(); isTracing = false },
                                                modifier = Modifier.weight(1f),
                                                shape = RoundedCornerShape(60.dp)
                                            ) { Text("Clear") }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        // Portrait layout
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(24.dp)
                                .verticalScroll(rememberScrollState()),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Surface(
                                shape = RoundedCornerShape(40.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant
                            ) {
                                Text(
                                    text = if (card.type == CardType.TEXT) "Type answer" else "Trace character",
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                                    fontSize = 14.sp
                                )
                            }
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            if (card.type == CardType.TEXT) {
                                SmartText(
                                    text = card.question,
                                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.SemiBold)
                                )
                                
                                Spacer(modifier = Modifier.height(20.dp))
                                
                                if (isAnswerRevealed) {
                                    Surface(
                                        shape = RoundedCornerShape(16.dp),
                                        color = MaterialTheme.colorScheme.primaryContainer,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(16.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Text(
                                                text = "Answer:",
                                                style = MaterialTheme.typography.labelMedium,
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                            SmartText(
                                                text = card.answer,
                                                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                                                color = MaterialTheme.colorScheme.onPrimaryContainer
                                            )
                                        }
                                    }
                                } else {
                                    OutlinedTextField(
                                        value = userAnswer,
                                        onValueChange = { userAnswer = it },
                                        placeholder = { Text("Type your answer...") },
                                        modifier = Modifier.fillMaxWidth(),
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                                        singleLine = true,
                                        shape = RoundedCornerShape(60.dp)
                                    )
                                }
                                
                                Spacer(modifier = Modifier.height(16.dp))
                                
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    if (isAnswerRevealed) {
                                        Button(
                                            onClick = { viewModel.nextCard() },
                                            modifier = Modifier.weight(1f),
                                            shape = RoundedCornerShape(60.dp)
                                        ) {
                                            Text("Next Card →")
                                        }
                                    } else {
                                        Button(
                                            onClick = {
                                                if (userAnswer.trim().equals(card.answer, ignoreCase = true)) {
                                                    Toast.makeText(context, "✅ Correct!", Toast.LENGTH_SHORT).show()
                                                    viewModel.nextCard()
                                                    userAnswer = ""
                                                } else {
                                                    Toast.makeText(context, "❌ Try again!", Toast.LENGTH_SHORT).show()
                                                }
                                            },
                                            modifier = Modifier.weight(1f),
                                            shape = RoundedCornerShape(60.dp)
                                        ) {
                                            Text("Check Answer")
                                        }
                                        OutlinedButton(
                                            onClick = { isAnswerRevealed = true },
                                            modifier = Modifier.weight(0.5f),
                                            shape = RoundedCornerShape(60.dp)
                                        ) {
                                            Text("Reveal")
                                        }
                                    }
                                }
                            } else {
                                // TRACE card portrait
                                Row(
                                    verticalAlignment = Alignment.Top,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text("✏️ ", style = MaterialTheme.typography.headlineSmall)
                                    SmartText(
                                        text = card.question,
                                        style = MaterialTheme.typography.headlineSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                                
                                if (isAnswerRevealed) {
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Surface(
                                        shape = RoundedCornerShape(16.dp),
                                        color = MaterialTheme.colorScheme.primaryContainer,
                                        modifier = Modifier.padding(bottom = 8.dp)
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                            horizontalAlignment = Alignment.Start
                                        ) {
                                            Text(
                                                text = "Answer: ",
                                                style = MaterialTheme.typography.labelSmall,
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.onPrimaryContainer
                                            )
                                            SmartText(
                                                text = card.answer,
                                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                                color = MaterialTheme.colorScheme.onPrimaryContainer
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))
                                
                                Surface(
                                    shape = RoundedCornerShape(40.dp),
                                    color = MaterialTheme.colorScheme.surfaceVariant,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(250.dp)
                                ) {
                                    Box(
                                        modifier = Modifier.fillMaxSize()
                                    ) {
                                        Canvas(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .pointerInput(studyState.currentCardIndex) {
                                                    if (!isAnswerRevealed) {
                                                        detectDragGestures(
                                                            onDragStart = { offset ->
                                                                isTracing = true
                                                                tracePath.moveTo(offset.x, offset.y)
                                                            },
                                                            onDrag = { change, _ ->
                                                                tracePath.lineTo(change.position.x, change.position.y)
                                                                val p = tracePath
                                                                tracePath = Path().apply { addPath(p) }
                                                            }
                                                        )
                                                    }
                                                }
                                        ) {
                                            drawPath(
                                                path = tracePath,
                                                color = strokeColor,
                                                style = Stroke(
                                                    width = 8.dp.toPx(),
                                                    cap = StrokeCap.Round,
                                                    join = StrokeJoin.Round
                                                )
                                            )
                                        }

                                        if (!isTracing && !isAnswerRevealed) {
                                            Column(
                                                modifier = Modifier.fillMaxSize(),
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                verticalArrangement = Arrangement.Center
                                            ) {
                                                Text(
                                                    text = "✍️",
                                                    fontSize = 64.sp,
                                                    color = MaterialTheme.colorScheme.outline
                                                )
                                                Text(
                                                    text = "Trace the character here",
                                                    color = MaterialTheme.colorScheme.outline,
                                                    fontSize = 16.sp
                                                )
                                            }
                                        }
                                    }
                                }
                                
                                Spacer(modifier = Modifier.height(16.dp))
                                
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    if (isAnswerRevealed) {
                                        Button(
                                            onClick = { viewModel.nextCard() },
                                            modifier = Modifier.fillMaxWidth(),
                                            shape = RoundedCornerShape(60.dp)
                                        ) {
                                            Text("Next Card →")
                                        }
                                    } else {
                                        Button(
                                            onClick = { isAnswerRevealed = true },
                                            modifier = Modifier.weight(1f),
                                            shape = RoundedCornerShape(60.dp)
                                        ) {
                                            Text("Reveal Answer")
                                        }
                                        OutlinedButton(
                                            onClick = {
                                                tracePath = Path()
                                                isTracing = false
                                            },
                                            modifier = Modifier.weight(0.5f),
                                            shape = RoundedCornerShape(60.dp)
                                        ) {
                                            Text("Clear")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(if (isLandscape) 4.dp else 16.dp))
                
                TextButton(
                    onClick = { viewModel.quitStudy() },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Quit Study")
                }
            }
        }
    }
}
