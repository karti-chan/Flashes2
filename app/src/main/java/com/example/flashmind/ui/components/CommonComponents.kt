package com.example.flashmind.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flashmind.data.CardType
import com.example.flashmind.data.Flashcard

/**
 * Professional shared components for FlashMind.
 */

@Composable
fun SectionHeader(title: String, onAddClick: (() -> Unit)? = null) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(top = 16.dp, bottom = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            letterSpacing = 2.sp
        )
        if (onAddClick != null) {
            IconButton(onClick = onAddClick, modifier = Modifier.size(32.dp)) {
                Icon(Icons.Default.Add, contentDescription = "Add", tint = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

@Composable
fun MathShortcutRow(onInsert: (String) -> Unit) {
    val shortcuts = listOf(
        "$$" to "$$", "x²" to "^2", "xₙ" to "_n", "√x" to "\\sqrt{}",
        "a/b" to "\\frac{}{}", "π" to "\\pi", "θ" to "\\theta",
        "Σ" to "\\sum_{}^{}", "∫" to "\\int", "∞" to "\\infty",
        "±" to "\\pm", "≈" to "\\approx", "≠" to "\\neq"
    )
    
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
    ) {
        items(shortcuts) { (label, code) ->
            AssistChip(
                onClick = { onInsert(code) },
                label = { Text(label, fontSize = 12.sp) }
            )
        }
    }
}

@Composable
fun FormulaPreview(text: String) {
    if (text.isBlank()) return
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text("Formula Preview:", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(4.dp))
            SmartText(text = text, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
fun CardPreviewItem(
    index: Int, 
    card: Flashcard, 
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onTypeToggle: () -> Unit,
    onClick: () -> Unit = {}
) {
    Surface(
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(24.dp)).clickable { onClick() },
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 1.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = index.toString(), color = MaterialTheme.colorScheme.outline, style = MaterialTheme.typography.bodyMedium)
            
            SmartText(
                text = card.question,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                modifier = Modifier.weight(1f)
            )

            // Type Toggle Chip
            Surface(
                shape = RoundedCornerShape(40.dp),
                color = if (card.type == com.example.flashmind.data.CardType.TEXT) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.clickable { onTypeToggle() }
            ) {
                Text(
                    text = card.type.name.lowercase(),
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (card.type == com.example.flashmind.data.CardType.TEXT) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            
            IconButton(onClick = onEdit, modifier = Modifier.size(32.dp)) {
                Icon(Icons.Default.Edit, "Edit", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
            }
            IconButton(onClick = onDelete, modifier = Modifier.size(32.dp)) {
                Icon(Icons.Default.Delete, "Delete", tint = MaterialTheme.colorScheme.outline, modifier = Modifier.size(18.dp))
            }
        }
    }
}

@Composable
fun EmptyState(message: String) {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Surface(
            modifier = Modifier.size(100.dp),
            shape = RoundedCornerShape(32.dp),
            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text("✨", fontSize = 40.sp)
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = message, 
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Tap the button below to get started", 
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
