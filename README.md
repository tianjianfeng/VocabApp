# VocabApp - Vocabulary Flashcard App

A beautiful Android vocabulary learning app built with Kotlin and Jetpack Compose. Features flashcard-style learning with support for multiple vocabulary lists.

## Features

- 📚 **Multiple Vocabulary Lists** - Create and manage different vocabulary lists (TOEFL, GRE, books, etc.)
- 🃏 **Flashcard UI** - Beautiful card deck interface with flip animations
- 📝 **Rich Word Data** - Store words with phonetic symbols and multiple meanings with parts of speech
- 🔄 **Progress Memory** - Remembers your last visited list and card position
- ✨ **Modern UI** - Built with Material 3 and Jetpack Compose
- 💾 **Offline Storage** - Uses Room database for local storage

## Screenshots

The app features:
- **Home Screen**: Shows all vocabulary lists with word counts, and a "Continue Learning" card for quick access to your last studied list
- **Card Deck Screen**: Flip through flashcards showing words on the front and definitions on the back

## Project Structure

```
VocabApp/
├── app/
│   ├── src/main/
│   │   ├── java/com/vocabapp/
│   │   │   ├── data/
│   │   │   │   ├── entities/      # Room entities (VocabList, Word, Meaning)
│   │   │   │   ├── dao/           # Data Access Objects
│   │   │   │   ├── repository/    # Repository pattern
│   │   │   │   ├── preferences/   # DataStore preferences
│   │   │   │   └── VocabDatabase.kt
│   │   │   ├── ui/
│   │   │   │   ├── screens/       # Compose UI screens
│   │   │   │   ├── viewmodel/     # ViewModels
│   │   │   │   └── theme/         # Material Theme
│   │   │   ├── navigation/        # Navigation components
│   │   │   ├── MainActivity.kt
│   │   │   └── VocabApplication.kt
│   │   └── res/                   # Resources
│   └── build.gradle.kts
├── build.gradle.kts
└── settings.gradle.kts
```

## Requirements

- Android Studio Hedgehog (2023.1.1) or later
- JDK 17 or later (Java 21 is supported)
- Android SDK 34
- Minimum Android API 26 (Android 8.0)

## How to Run

### Using Android Studio (Recommended)

1. **Install Android Studio** (if not already installed):
   - Download from: https://developer.android.com/studio
   - Follow the installation wizard
   - During setup, install the Android SDK

2. **Open the project in Android Studio:**
   - Open Android Studio
   - Click **File → Open** (or **Open** on the welcome screen)
   - Navigate to: `/Users/jianfeng.tian/workspace/vocab/VocabApp`
   - Click **Open**

3. **Wait for Gradle sync:**
   - Android Studio will automatically download dependencies
   - This may take a few minutes on first run
   - Look for the "Gradle sync finished" message in the status bar

4. **Create an Android Emulator:**
   - Go to **Tools → Device Manager** (or click the phone icon in the toolbar)
   - Click **Create Device**
   - Select a phone (e.g., Pixel 6)
   - Click **Next**
   - Select a system image (API 33 or 34 recommended)
   - Click **Download** if needed, then **Next**
   - Click **Finish**
   - Click the **Play** button (▶️) next to your emulator to start it

5. **Run the app:**
   - Click the green **Run** button (▶️) in the toolbar
   - Or press `Control + R` (Mac) / `Shift + F10` (Windows/Linux)
   - Select your running emulator
   - The app will build, install, and launch automatically!

### Using Command Line (Requires Android SDK)

If you have the Android SDK installed and configured:

1. **Set ANDROID_HOME:**
   ```bash
   export ANDROID_HOME=~/Library/Android/sdk  # Mac
   export ANDROID_HOME=~/Android/Sdk          # Linux
   ```

2. **Navigate to the project:**
   ```bash
   cd /Users/jianfeng.tian/workspace/vocab/VocabApp
   ```

3. **Build the project:**
   ```bash
   ./gradlew assembleDebug
   ```

4. **Install on connected device/emulator:**
   ```bash
   ./gradlew installDebug
   ```

5. **Run:**
   ```bash
   adb shell am start -n com.vocabapp/.MainActivity
   ```

## Sample Data

The app comes pre-loaded with sample vocabulary lists:
- **TOEFL Essential** - 5 essential TOEFL words
- **GRE Advanced** - 5 advanced GRE vocabulary words
- **The Old Man and the Sea** - 5 words from Hemingway's novel

## Usage

1. **Browse Lists** - The home screen shows all your vocabulary lists
2. **Continue Learning** - Tap the highlighted card to continue where you left off
3. **Study Cards** - Tap a list to open the flashcard deck
4. **Flip Cards** - Tap a card or the eye button to flip between word and definition
5. **Navigate** - Swipe left/right or use the arrow buttons to move between cards
6. **Add Lists** - Tap the "New List" button to create custom vocabulary lists

## Technology Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Database**: Room (SQLite)
- **Architecture**: MVVM with Repository pattern
- **State Management**: StateFlow
- **Navigation**: Navigation Compose
- **Preferences**: DataStore
- **Animations**: Compose Animation APIs

## Data Model

```
VocabList (1) ──────┬─── (*) Word (1) ────── (*) Meaning
                    │
- id                │    - id                 - id
- name              │    - listId (FK)        - wordId (FK)
- description       │    - word               - partOfSpeech
- createdAt         │    - phonetic           - definition
```

## License

This project is for educational purposes.

