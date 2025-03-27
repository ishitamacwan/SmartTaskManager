# 📋 Task Manager App

## 🚀 Project Overview
A comprehensive Task Management application designed to help users organize, track, and prioritize their tasks efficiently.

## 🛠 Tech Stack
- **Platform:** Android
- **Language:** Kotlin (v2.0.0)
- **Target SDK:** 35
- **Minimum SDK:** 24

## 📦 Dependencies
- **ViewPager2:** For seamless navigation between task lists
- **SDP Library:** Responsive padding and margin scaling
- **SSP Library:** Scalable text sizing
- **Room Database:** Local data persistence and management
- **Lottie:** Added Lottie for JSON Animations

## ✨ Key Features

### 1. Task List Screens
- **All Tasks:** Comprehensive view of all tasks
- **Pending Tasks:** Focus on incomplete tasks
- **Completed Tasks:** Track accomplished items

### 2. Task Management
- **Drag and Drop with Haptic Feedback:** 
  - Intuitively reorder tasks
  - Enhanced user experience with tactile response
- **Swipe Actions:**
  - Left Swipe: Delete task
  - Right Swipe: Mark task as completed

### 3. Task Details
- Detailed view of individual task
- Option to mark task as read
- Task deletion functionality

### 4. Add Task
- Intuitive task creation interface
- Capture details:
  - Title
  - Due Date
  - Priority
  - Description

### 5. Advanced Sorting
Sort tasks by:
- Due Date
- Priority
- Alphabetical Order

### 6. Customization
- Primary color selection
- Dark/Light mode toggle


## 🔧 Setup and Installation

### Prerequisites
- **Android Studio** (Latest Version)
- **JDK 11** or higher
- **Internet Connection** for dependency downloading

### Step-by-Step Installation

1. **Clone the Repository**
   ```bash
   git clone https://github.com/ishitamacwan/SmartTaskManager.git
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Select "Open an Existing Project"
   - Navigate to the cloned repository folder
   - Select the project's root directory

3. **Configure SDK**
   - Open Android Studio's SDK Manager
   - Ensure Android SDK 35 is installed
   - Verify SDK Tools are up to date

4. **Sync Gradle Dependencies**
   - Click on "File" > "Sync Project with Gradle Files"
   - Wait for Android Studio to download and sync all dependencies

5. **Build the Project**
   - Go to "Build" > "Make Project"
   - Resolve any potential sync or build errors

6. **Run the Application**
   - Connect an Android device with USB debugging enabled
   - Or set up an Android Virtual Device (AVD) in Android Studio
   - Click the "Run" button or press Shift+F10

### Troubleshooting
- Ensure you have the latest version of Android Studio
- Check that all required SDK platforms are installed
- Verify Kotlin plugin is updated to version 2.0.0

**Happy Task Managing!** 🎉
