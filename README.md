# 📰 News App

A modern Android News application that fetches the latest news from a remote **News API** with efficient pagination, offline caching, and a clean architecture approach.

---

## 📌 Features

- Fetches real-time news from a remote News API
- Smooth pagination using Paging 3 with RemoteMediator
- Offline support with local database caching
- Clean Architecture for better scalability and maintainability
- MVVM architecture pattern
- Background data fetching using Kotlin Coroutines
- Lifecycle-aware UI updates

---

## 🏗️ Architecture

This project follows **Clean Architecture** with clear separation of concerns:

- **Data Layer**  
  - API service (Retrofit)  
  - Local database (Room)  
  - RemoteMediator for network + cache sync  

- **Domain Layer**  
  - Use cases  
  - Business logic  

- **Presentation Layer**  
  - ViewModel  
  - UI (Activity / Fragment / Compose)

The app uses **MVVM (Model–View–ViewModel)** to keep UI logic simple and lifecycle-aware.

---

## 🔄 Data Flow

1. News data is fetched from the **News API**
2. **RemoteMediator** manages pagination and synchronizes remote data with the local database
3. Data is stored locally using **Room**
4. UI observes data through **ViewModel**
5. All background operations are handled using **Kotlin Coroutines**

---

## 🛠️ Tech Stack & Libraries

- **Kotlin**
- **MVVM Architecture**
- **Clean Architecture**
- **Retrofit** – Network calls
- **Room Database** – Local caching
- **Paging 3 + RemoteMediator**
- **Kotlin Coroutines** – Background processing
- **Flow / LiveData** – Reactive UI updates

---

## 🚀 Getting Started

### Prerequisites
- Android Studio (latest version)
- Android SDK
- News API key

### Setup
1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/news-app.git


