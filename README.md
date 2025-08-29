# RepoFinder

📱 Android app built with **Kotlin** and **Jetpack Compose**, following **Clean Architecture** and **MVI**.  
Implements an **offline-first strategy** with Room, DataStore, and Coroutines.  
The project demonstrates a **multi-module, scalable architecture** with clear separation of concerns.

---

## 🛠️ Tech Stack

### Core
- **Kotlin**
- **Jetpack Compose**
- **Kotlin Coroutines & Flow**

### Dependency Injection
- **Koin**
- **AndroidX Startup**

### Data Layer
- **Room** (local database, offline-first)
- **DataStore** (key-value persistence)
- **Retrofit + OkHttp** (networking)
- **Kotlinx Serialization** (JSON parsing)

### Presentation
- **Jetpack Compose** (Material3, animations)
- **Coil** (image loading)

### Navigation
- **Navigation-Compose**
- **Accompanist Navigation Animation**

### Testing
- **JUnit & Kotlin Test**
- **MockK** (mocking)
- **Turbine** (Flow testing)
- **MockWebServer** (integration tests)

---

## 📐 Architecture

RepoFinder is organized into **multi-modules** to encourage scalability, modularity, and testability:

- **:app** → Application entry point, wires together all modules
- **:common** → Shared data sources, repositories, and use cases
- **:core** → Core abstractions, UI components, and extensions
- **:home** → Home feature module (screen + ViewModel)
- **:details** → Details feature module (screen + ViewModel)
- **:navigation** → Contracts for navigation to decouple feature modules from each other

This design allows each feature to evolve independently while keeping common logic centralized.  
Navigation is **interface-based**, meaning feature modules don’t directly depend on each other, preserving modularity.

---

## 🎯 Purpose

This project is mainly for **learning and experimentation** with modern Android development tools and patterns.  
It demonstrates how to structure a **clean, modular, and testable** Android application.

---

## 🚀 Getting Started

1. Clone the repository
2. Open in Android Studio (Giraffe or newer recommended)
3. Build & run the app on an emulator or device

---

## 📄 License

This project is licensed under the MIT License – see the [LICENSE](LICENSE) file for details.
