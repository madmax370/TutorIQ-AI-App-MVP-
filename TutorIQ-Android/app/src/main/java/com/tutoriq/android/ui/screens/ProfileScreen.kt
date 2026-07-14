package com.tutoriq.android.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tutoriq.android.ui.theme.Primary
import com.tutoriq.android.ui.theme.Background
import com.tutoriq.android.ui.theme.Success
import com.tutoriq.android.ui.theme.Error as ErrorColor

@Composable
fun ProfileScreen(
    university: String,
    semester: String,
    course: String,
    apiKey: String,
    onSaveClick: (String, String, String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var uni by remember { mutableStateOf(university) }
    var sem by remember { mutableStateOf(semester) }
    var crs by remember { mutableStateOf(course) }
    var key by remember { mutableStateOf(apiKey) }
    
    val hasApiKey = key.isNotBlank()
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        Text(
            text = "Your Profile",
            style = MaterialTheme.typography.headlineMedium,
            color = Primary,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Configure your study preferences and API key",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        OutlinedTextField(
            value = uni,
            onValueChange = { uni = it },
            label = { Text("University Name") },
            placeholder = { Text("e.g., SPPU, Mumbai University") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        OutlinedTextField(
            value = sem,
            onValueChange = { sem = it },
            label = { Text("Semester") },
            placeholder = { Text("e.g., 5th Sem, Final Year") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        OutlinedTextField(
            value = crs,
            onValueChange = { crs = it },
            label = { Text("Course / Program") },
            placeholder = { Text("e.g., BSc CS, BE IT") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        HorizontalDivider(color = Primary.copy(alpha = 0.3f))
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Google Gemini API Key",
                style = MaterialTheme.typography.titleMedium,
                color = Primary,
                modifier = Modifier.weight(1f)
            )
            
            if (hasApiKey) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Saved",
                    tint = Success,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("Saved", color = Success, fontSize = 12.sp)
            } else {
                Icon(
                    imageVector = Icons.Default.Error,
                    contentDescription = "Missing",
                    tint = ErrorColor,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("Missing", color = ErrorColor, fontSize = 12.sp)
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        OutlinedTextField(
            value = key,
            onValueChange = { key = it },
            label = { Text("API Key") },
            placeholder = { Text("Enter your API key here") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Get your free API key from ",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
        
        TextButton(
            onClick = { /* Open browser to Google AI Studio */ },
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(
                text = "Google AI Studio",
                color = Primary,
                style = MaterialTheme.typography.bodySmall
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Button(
            onClick = { onSaveClick(uni, sem, crs, key) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Primary)
        ) {
            Text("Save Profile", color = Color.White)
        }
    }
}
