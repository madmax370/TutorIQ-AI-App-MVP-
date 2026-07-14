package com.tutoriq.android.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import com.tutoriq.android.data.model.ToolData
import com.tutoriq.android.ui.theme.Primary
import com.tutoriq.android.ui.theme.Background
import com.tutoriq.android.ui.theme.Surface

data class Message(val text: String, val isUser: Boolean)

@Composable
fun ToolsScreen(
    subject: String,
    marks: String,
    onSendMessage: (String, ToolData) -> Unit,
    modifier: Modifier = Modifier
) {
    var message by remember { mutableStateOf("") }
    var currentSubject by remember { mutableStateOf(subject) }
    var currentMarks by remember { mutableStateOf(marks) }
    val messages = remember { mutableStateListOf<Message>() }
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Background)
    ) {
        // Configuration Panel
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = Surface),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Answer Maker Config",
                    style = MaterialTheme.typography.titleMedium,
                    color = Primary
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                OutlinedTextField(
                    value = currentSubject,
                    onValueChange = { currentSubject = it },
                    label = { Text("Subject") },
                    placeholder = { Text("e.g. Data Structures") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                var expanded by remember { mutableStateOf(false) }
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = currentMarks,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Marks Weightage") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        shape = RoundedCornerShape(8.dp)
                    )
                    
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        listOf("1", "2", "5", "10").forEach { marks ->
                            DropdownMenuItem(
                                text = { Text("$marks ${if (marks == "1") "Mark" else "Marks"}") },
                                onClick = {
                                    currentMarks = marks
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
        }
        
        // Chat Messages
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(messages.toList()) { msg ->
                ChatBubble(message = msg)
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Input Field
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            OutlinedTextField(
                value = message,
                onValueChange = { message = it },
                placeholder = { Text("Ask your question...") },
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 56.dp),
                shape = RoundedCornerShape(24.dp),
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
                maxLines = 4
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            
            FloatingActionButton(
                onClick = {
                    if (message.isNotBlank()) {
                        messages.add(Message(message, true))
                        onSendMessage(message, ToolData(currentSubject, currentMarks))
                        message = ""
                    }
                },
                containerColor = Primary,
                shape = RoundedCornerShape(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Send",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
private fun ChatBubble(message: Message) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(if (message.isUser) Alignment.End else Alignment.Start),
        colors = CardDefaults.cardColors(
            containerColor = if (message.isUser) Primary else Surface
        ),
        shape = RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp,
            bottomStart = if (message.isUser) 16.dp else 4.dp,
            bottomEnd = if (message.isUser) 4.dp else 16.dp
        )
    ) {
        Text(
            text = message.text,
            modifier = Modifier.padding(12.dp),
            color = if (message.isUser) Color.White else Color.Black
        )
    }
}
