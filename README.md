# FlashMind ✨

A modern Android flashcard application built with Jetpack Compose, focused on efficient learning of scientific formulas and languages.

The app was created as a personal project to practice clean architecture, Room database design, and building a pleasant user experience around mathematical content.

---

## Features 💫

- Hierarchical organization: Subjects → Sets → Cards
- Two card types:
  - **Text** – classic question & answer
  - **Trace** – freehand drawing mode for characters/symbols ✏️
- Native LaTeX rendering for mathematical formulas
- Live formula preview while creating cards
- Math shortcuts bar for faster input
- Study mode with Check / Reveal functionality
- Light, Dark and System theme support 🌙☀️
- Fully offline (Room database)
- Preloaded with an extensive Physics formula set and basic Hiragana

---

## Tech Stack 🛠️

- **Language:** Kotlin  
- **UI:** Jetpack Compose + Material 3  
- **Architecture:** MVVM + Repository pattern  
- **Database:** Room (normalized schema)  
- **Asynchronous:** Coroutines + Flow  

Cards are stored in a separate table with a proper foreign key relationship to their parent set, instead of being serialized as JSON.

---

## Getting Started 🚀

1. Clone the repository
2. Open the project in Android Studio
3. Sync Gradle and run the app

Sample data (Physics formulas + Hiragana) is automatically inserted on first launch.

---

## Project Focus 📌

This project was an opportunity to practice:

- Designing a clean and normalized Room database
- Building reactive UI with Flow and ViewModel
- Creating a usable experience for scientific content (LaTeX support)
- Structuring a multi-screen Compose application

---

## Future Improvements 🌱

- Spaced repetition algorithm
- Progress statistics
- Search functionality
- Import / Export options
