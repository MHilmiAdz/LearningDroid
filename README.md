![Logo](https://github.com/MHilmiAdz/LearningDroid/blob/master/app/src/main/res/drawable/ldbanner.png?raw=true)

<div>

# LearningDroid

My personal Android Development learning journal — from zero to MASTER.

![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Version](https://img.shields.io/badge/version-v0.3.1-blue?style=for-the-badge)

</div>

---

## 📖 About

This repository documents my hands-on journey learning Android development. Each feature is built around a specific concept, from basic intents to Room databases and local AI integration.

## Main Feature
- **F.R.O.S.T** — an AI feature to support your local-hosted AI model from Ollama.
- **Learned Features from Simplest to Most Complex**
- **Connect your Local-hosted LLM usually it looks like this: http://{your host IP}:11434**

> **Note on F.R.O.S.T:** 
> This feature connects to a locally-hosted LLM on local-hosted AI Model. 
> It will not work on your phone unless you configure your own AI Model with Ollama.
> If you have low computing power use lighter LLM like olmo2.
> This feature is working with olmo2 LLM.

---

## 🧠 Study References

| Topic                            | Resource                                                                                                                                         |
|----------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------|
| Android Overview                 | [Kotlin Android Docs](https://kotlinlang.org/docs/android-overview.html)                                                                         |
| Activity                         | [Theory & Implementation](https://www.notion.so/healmee/Android-Activity-2b15b65c18f180d8b553dcc493a236d6)                                       |
| Intent                           | [Theory & Implementation](https://www.notion.so/healmee/ANDROID-INTENT-2bb5b65c18f180dc9f7dce8ec8388e3e)                                         |
| Layout                           | [Theory & Implementation](https://www.notion.so/healmee/View-ViewGroup-2c75b65c18f1806c8dfbd8283c8d4a8c)                                         |
| Styling & Theme                  | [Theory & Implementation](https://www.notion.so/healmee/Style-and-Theme-2ca5b65c18f1801cb3bcdc33bd9a7fae)                                        |
| Recycler View                    | [Theory & Implementation](https://www.notion.so/healmee/Recycler-View-2d45b65c18f1806fbe9ad91cb74f42fd)                                          |
| Fragments                        | [Theory & Implementation](https://www.notion.so/healmee/Fragments-App-architecture-Android-DevelopersFRAGMENTS-2f65b65c18f180d4b794dd36e35b993f) |
| Navigation                       | [Theory & Implementation](https://www.notion.so/healmee/NAVIGATION-2fe5b65c18f1801dab5ce29e901e95fc)                                             |
| Background Thread & Networking   | [Theory & Implementation](https://www.notion.so/healmee/BACKGROUND-THREAD-NETWORKING-3205b65c18f18038a1dcfa8a49379eec)                           |
| MAD (Modern Android Development) | [Theory & Implementation](https://www.notion.so/healmee/Android-Architecture-Component-3305b65c18f18001a8d4de1d3931f06e)                         |
| Unit Testing                     | [Theory & Implementation](https://www.notion.so/healmee/Testing-34b5b65c18f180d8882fc6c698b32622)                                                |
| Local Data Persistence           | [Theory & Implementation](https://www.notion.so/healmee/Local-Data-Persistent-34d5b65c18f1802298fed60a0ea47304)                                  |
| Background Task & Scheduler      | [Theory & Implementataion](https://www.notion.so/healmee/Background-Task-Scheduler-3625b65c18f180a2b940c49f9e6fc7bd?source=copy_link)            |

---

## ✅ Feature Changelog

### 🧩 Core UI & Navigation
- Basic views with Button, TextView, and Intent-based navigation
- Scrolling views and custom Sage theme
- Recycler View with view binding and Glide image loading
- Fragments with resume/pause/stop lifecycle and back stack
- Navigation component with SafeArgs and transitions
- AppBar (2 menus), SearchBar, Drawer, Bottom Nav, Tab Layout

### 🌐 Networking & Async
- Asynchronous task execution
- Web API calls (direct and via Retrofit)
- **F.R.O.S.T** — an AI feature powered by a locally-hosted LLM (`olmo2:latest`)

### 🏗️ Architecture
- ViewModel & LiveData lifecycle management
- Unit Testing

### 💾 Data Persistence
- File read/write
- SharedPreferences
- DataStore Preferences
- SQLite CRUD (via Room Notes)

### 📦 Data Passing
- Intent with primitive extras
- Intent with Parcelable object
- Intent with Kotlin `@Parcelize`
- Activity result contracts (returning values)
- Implicit Intent (phone dialer)

### Advanced UI
- Custom View
- Canvas View
- Web View
- Transition Animation
- Motion Layout

---

## 📸 Screenshots - 5 Latest Updates

### v0.3.2 — Simple Notification
![v0.3.2](https://github.com/MHilmiAdz/LearningDroid/blob/master/app/src/main/res/drawable/ss032.png?raw=true)

### v0.3.3 — Custom View
![v0.3.3](https://github.com/MHilmiAdz/LearningDroid/blob/master/app/src/main/res/drawable/ss033.png?raw=true)

### v0.3.4 — Canvas View
![v0.3.4](https://github.com/MHilmiAdz/LearningDroid/blob/master/app/src/main/res/drawable/ss034.png?raw=true)

### v0.3.5 — Web View
![v0.3.5](https://github.com/MHilmiAdz/LearningDroid/blob/master/app/src/main/res/drawable/ss035.png?raw=true)

### v0.3.6 — Motion Layout and Localization
![v0.3.6](https://github.com/MHilmiAdz/LearningDroid/blob/master/app/src/main/res/drawable/ss036.png?raw=true)

---

## ⚙️ Prerequisites

- Android Studio (Hedgehog or later recommended)
- JDK 17+
- Android SDK 26+

---

## 🚀 Installation

```bash
git clone https://github.com/MHilmiAdz/LearningDroid.git
```

Open the project in Android Studio and let Gradle sync. Build and run on an emulator or physical device.
There is 100+ people cloning my app. If you like this please leave a star 🌟🌟🌟🌟🌟 😊

---

## 👤 Author

**M. Hilmi Adz**  
[@MHilmiAdz](https://github.com/MHilmiAdz)