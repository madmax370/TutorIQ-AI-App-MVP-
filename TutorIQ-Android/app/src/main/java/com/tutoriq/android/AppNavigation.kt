package com.tutoriq.android

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tutoriq.android.data.model.ChatRequest
import com.tutoriq.android.data.model.LoginRequest
import com.tutoriq.android.data.model.RegisterRequest
import com.tutoriq.android.data.model.ToolData
import com.tutoriq.android.data.network.RetrofitClient
import com.tutoriq.android.ui.screens.*
import com.tutoriq.android.util.DataStoreManager
import kotlinx.coroutines.launch

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Signup : Screen("signup")
    object Dashboard : Screen("dashboard")
    object Profile : Screen("profile")
    object Tools : Screen("tools")
}

@Composable
fun AppNavigation(dataStoreManager: DataStoreManager) {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    
    var isLoggedIn by remember { mutableStateOf(false) }
    var userName by remember { mutableStateOf<String?>(null) }
    var loginError by remember { mutableStateOf<String?>(null) }
    var signupError by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    
    var university by remember { mutableStateOf("") }
    var semester by remember { mutableStateOf("") }
    var course by remember { mutableStateOf("") }
    var apiKey by remember { mutableStateOf("") }
    
    LaunchedEffect(Unit) {
        dataStoreManager.authToken.collect { token ->
            isLoggedIn = !token.isNullOrEmpty()
        }
        
        dataStoreManager.userName.collect { name ->
            userName = name
        }
        
        dataStoreManager.profileData.collect { profile ->
            university = profile[com.tutoriq.android.util.PreferencesKeys.UNIVERSITY] as? String ?: ""
            semester = profile[com.tutoriq.android.util.PreferencesKeys.SEMESTER] as? String ?: ""
            course = profile[com.tutoriq.android.util.PreferencesKeys.COURSE] as? String ?: ""
            apiKey = profile[com.tutoriq.android.util.PreferencesKeys.GEMINI_API_KEY] as? String ?: ""
        }
    }
    
    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) Screen.Dashboard.route else Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginClick = { email, password ->
                    scope.launch {
                        isLoading = true
                        // Dummy Login: Success for any gmail account
                        kotlinx.coroutines.delay(1000) // Simulate network delay
                        
                        dataStoreManager.saveAuthToken("dummy_token")
                        dataStoreManager.saveUserInfo("1", "Demo User", email)
                        isLoggedIn = true
                        
                        navController.navigate(Screen.Dashboard.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                        isLoading = false
                    }
                },
                onNavigateToSignup = {
                    loginError = null
                    navController.navigate(Screen.Signup.route)
                }
            )
            
            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                    CircularProgressIndicator(color = com.tutoriq.android.ui.theme.Primary)
                }
            }
        }
        
        composable(Screen.Signup.route) {
            SignupScreen(
                onSignupClick = { name, email, password ->
                    scope.launch {
                        isLoading = true
                        // Dummy Signup: Just navigate back to login
                        kotlinx.coroutines.delay(1000)
                        navController.popBackStack()
                        isLoading = false
                    }
                },
                onNavigateToLogin = {
                    signupError = null
                    navController.popBackStack()
                }
            )

            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                    CircularProgressIndicator(color = com.tutoriq.android.ui.theme.Primary)
                }
            }
        }
        
        composable(Screen.Dashboard.route) {
            DashboardScreen(
                userName = userName,
                onNavigateToProfile = {
                    navController.navigate(Screen.Profile.route)
                },
                onNavigateToTools = {
                    navController.navigate(Screen.Tools.route)
                },
                onLogout = {
                    scope.launch {
                        dataStoreManager.clearAllData()
                        isLoggedIn = false
                        navController.navigate(Screen.Login.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                }
            )
        }
        
        composable(Screen.Profile.route) {
            ProfileScreen(
                university = university,
                semester = semester,
                course = course,
                apiKey = apiKey,
                onSaveClick = { uni, sem, crs, key ->
                    scope.launch {
                        dataStoreManager.saveProfile(uni, sem, crs, key)
                    }
                }
            )
        }
        
        composable(Screen.Tools.route) {
            ToolsScreen(
                subject = "",
                marks = "5",
                onSendMessage = { message, toolData ->
                    scope.launch {
                        try {
                            val response = RetrofitClient.apiService.chat(
                                ChatRequest(
                                    toolType = "answerMaker",
                                    message = message,
                                    toolData = toolData
                                )
                            )
                            if (response.isSuccessful) {
                                // Handle response
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            )
        }
    }
}
