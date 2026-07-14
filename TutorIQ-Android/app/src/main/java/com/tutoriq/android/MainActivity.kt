package com.tutoriq.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.tutoriq.android.data.model.LoginRequest
import com.tutoriq.android.data.model.RegisterRequest
import com.tutoriq.android.data.model.ToolData
import com.tutoriq.android.data.network.RetrofitClient
import com.tutoriq.android.ui.screens.*
import com.tutoriq.android.ui.theme.TutorIQTheme
import com.tutoriq.android.util.DataStoreManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    
    private lateinit var dataStoreManager: DataStoreManager
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        dataStoreManager = DataStoreManager(this)
        
        setContent {
            TutorIQTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(dataStoreManager)
                }
            }
        }
    }
}
