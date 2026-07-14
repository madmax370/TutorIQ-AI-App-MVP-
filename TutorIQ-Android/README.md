# TutorIQ AI - Android MVP

A Minimum Viable Product Android application for TutorIQ AI using Kotlin and Jetpack Compose with a liquid glass UI theme.

## Features

### 1. Authentication System
- **Signup Screen**: Name, Email (@gmail.com only), Password (min 6 chars)
- **Login Screen**: Email (@gmail.com only), Password
- **Session Management**: JWT token storage, auto-login on app launch
- **Email Validation**: Strict @gmail.com regex validation

### 2. Profile Management (BYOK Setup)
- **Profile Screen**: University, Semester, Course inputs
- **API Key Input**: Google Gemini API key (password field type)
- **Key Status Indicator**: Show Saved ✅ / Missing ❌
- **Link to Google AI Studio**: For key acquisition

### 3. Single AI Tool - Answer Maker Only
- **Tool Selection**: Just Answer Maker
- **Configuration Panel**: Subject input, Marks dropdown (1/2/5/10)
- **Chat Interface**: User message input, AI response display
- **Message Rendering**: Basic text support

### 4. Simple Dashboard
- **Welcome Message**: Display user's name
- **Quick Start Guide**: 3-step setup instructions
- **Navigation**: Cards to Profile and Tools only

## Tech Stack

- **Language**: Kotlin
- **UI**: Jetpack Compose
- **Architecture**: MVVM (simplified)
- **Navigation**: Navigation Compose
- **Networking**: Retrofit
- **Async**: Coroutines
- **Storage**: DataStore for token/profile

## Liquid Glass UI Theme

Coffee & Cream palette with basic glassmorphism:

```kotlin
// Colors
val Primary = Color(0xFF6F4E37)
val Background = Color(0xFFFDFBF7)
val Surface = Color(0xFFF5F2EA)
```

## Project Structure

```
app/src/main/java/com/tutoriq/android/
├── MainActivity.kt
├── AppNavigation.kt
├── data/
│   ├── model/
│   │   └── Models.kt
│   └── network/
│       ├── ApiService.kt
│       └── RetrofitClient.kt
├── ui/
│   ├── components/
│   │   └── GlassCard.kt
│   ├── screens/
│   │   ├── LoginScreen.kt
│   │   ├── SignupScreen.kt
│   │   ├── DashboardScreen.kt
│   │   ├── ProfileScreen.kt
│   │   └── ToolsScreen.kt
│   └── theme/
│       ├── Color.kt
│       └── Theme.kt
└── util/
    └── DataStoreManager.kt
```

## API Endpoints

- `POST /api/auth/register`
- `POST /api/auth/login`
- `POST /api/auth/logout`
- `GET /api/profile`
- `POST /api/profile/update`
- `POST /api/ai/chat`

## Setup Instructions

1. Open the project in Android Studio
2. Sync Gradle files
3. Update the BASE_URL in `RetrofitClient.kt` to point to your server
4. Build and run on an emulator or device (API 26+)

## Navigation Flow

```
Launch → Check Auth → [No Token] → Login/Signup → Dashboard → Tools
                      [Has Token] → Dashboard → Tools
```

## License

MIT License
