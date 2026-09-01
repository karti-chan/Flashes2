package com.example.flashmind.ui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hrm.latex.renderer.Latex
import com.hrm.latex.renderer.model.LatexConfig
import com.hrm.latex.renderer.model.LatexTheme

@Composable
fun SmartText(
    text: String, 
    style: androidx.compose.ui.text.TextStyle, 
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified
) {
    val lines = text.split("\n")
    
    Column(modifier = modifier) {
        lines.forEach { line ->
            if (line.isBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
            } else {
                // Check if the line is a pure formula or contains mixed text
                // For now, if the line is long and contains math, we wrap it in a scrollable box
                // If it's short or plain text, we let it wrap.
                
                val isMath = line.contains("\\") || line.contains("^") || line.contains("_") || line.contains("$") || line.contains("{")
                
                if (isMath && line.length < 50) {
                    // Short math line, render as is
                    Latex(
                        latex = line,
                        config = LatexConfig(
                            fontSize = style.fontSize,
                            theme = LatexTheme.material3()
                        ),
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                } else if (isMath) {
                    // Long math line, allow horizontal scroll
                    Box(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                        Latex(
                            latex = line,
                            config = LatexConfig(
                                fontSize = style.fontSize,
                                theme = LatexTheme.material3()
                            ),
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                } else {
                    // Plain text, wraps naturally
                    Text(
                        text = line, 
                        style = style, 
                        color = color,
                        softWrap = true
                    )
                }
            }
        }
    }
}
