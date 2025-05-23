# PawPlates by team.java

## Faith Ota, Dakota Hernandez, Mac Johnson, Sofia Amador, Eli Hall, & Joshua Carroll

### Project Site:
https://macj2005.github.io/PawPlatesProjectSite/

### Health & Fitness App
You have been contracted to create an application that empowers a user to monitor and track their progress along their health journey. The application should offer a suite of tools that allows a user to record, analyze, and set goals for different aspects of their life.

# 🐾 Paw Plates – Nutrition & Fitness Tracker

**Paw Plates** is a Java Swing desktop application built to help users track nutrition, weight, and health goals with a clean, intuitive interface. It includes secure login, admin tools, and daily email reminders powered by the Mailjet API.

## 🌟 Features

### 🔐 Authentication & Profile Management
- User registration with fitness level and security questions
- Secure login system with support for admin users
- Password reset via user-defined security questions

### 🍽️ Calorie & Macro Tracker
- Add, edit, and delete food entries for each meal (breakfast, lunch, dinner)
- Tracks:
    - Food Name
    - Calories
    - Protein
    - Carbs
    - Fats
    - Fiber
- Daily progress bar that updates based on calorie totals
- Integrated calendar (LGoodDatePicker) for selecting and loading entries by date

### 📈 Weight Tracking
- Enter daily weights and visualize progress over a 7-day span
- Automatically fills missing days with last recorded weight
- Optional goal weight line on the graph

### 📧 Reminders Page
- Choose from pre-defined daily reminders (e.g., drink water, log food)
- Emails sent to user via **Mailjet API**
- Option to enter an alternate email address
- Email includes all selected reminders

### 🧮 Calorie Calculator
- Popup calculator estimates daily caloric needs based on weight and goal (lose, maintain, gain)
- Adapts based on chosen weight change rate (e.g., 1 lb/week)

### 🧑‍💼 Administrator Interface
- Table displaying all users (non-sensitive data only)
- Admins can assist in password reset verification
- No access to private information such as passwords

## 🛠️ Technologies Used

- **Java Swing** – GUI components
- **LGoodDatePicker** – Date selection popup
- **SQLite** – Lightweight embedded database for user data, food logs, and goals
- **Mailjet API** – Sends confirmation emails with selected reminders
- **JFreeChart** – Visualizes weight tracking progress
- **JUnit** – Unit testing framework
- **Maven** – Dependency and project management


## 📋 Home Page
> Entry point of the application. Includes logo, navigation menu bar, and logout functionality.
<img width="1247" alt="Home page " src="https://github.com/user-attachments/assets/7b8d0b2e-d2ca-4fa5-8920-73d31f77583c" />


## 🔐 Login Page
> Allows users to log in using a validated username, password, and email. Determines if the user is a general user or trainer.
<img width="449" alt="Login page" src="https://github.com/user-attachments/assets/baf09421-515b-49b3-8e0a-70459aab9bcc" />

## 🧾 Create Account Page
> Registration form where users can enter name, fitness level, and security questions for account recovery.
<img width="447" alt="create account page " src="https://github.com/user-attachments/assets/c1300936-d812-4bbe-9707-391fe08f42f4" />

## 🎯 Set Goals Page
> Users define their target weight, calorie intake, and sleep goals. Includes validation and confirmation dialogs.
<img width="1249" alt="Set goals page " src="https://github.com/user-attachments/assets/98186dc8-a480-4e67-bfb1-42079dfa45d7" />

## 🛏️ Sleep Tracking Page
> Users input daily sleep amounts, tracked against weekly goals with visual feedback.
<img width="1255" alt="Sleep tracking page " src="https://github.com/user-attachments/assets/b4eb5df8-97d0-45b6-a0d3-db882cc93557" />

## ⚖️ Weight Tracking Page
> Allows users to log daily weights and view a table and graph of progress over time.
<img width="1249" alt="Weight tracking page " src="https://github.com/user-attachments/assets/958f0750-4e1d-4983-9c71-f539826793af" />

## 🍽️ Calorie Tracking Page
> Users track calories and macros across meals (breakfast, lunch, dinner), with animated progress and data persistence.
<img width="1243" alt="Calorie tracking page " src="https://github.com/user-attachments/assets/3a34586f-770e-4e4d-960f-d999ecbe08fa" />

## 🏋️ Workout Plan Page
> Trainers or users create structured workouts by selecting and organizing exercises into dated plans.
<img width="1247" alt="workout plan page " src="https://github.com/user-attachments/assets/f94d13de-37bc-442b-8af0-39fffb93cca9" />

## 🔔 Reminders Page
> Users can opt-in for automated email reminders related to calorie goals, workouts, or general habits.
<img width="1245" alt="Reminders page " src="https://github.com/user-attachments/assets/0a920986-5011-4e26-a0f8-54600f49e068" />


# Summary of Contributions

---

## Sofia Amador

Implemented:

- **Create Profile Page**: Includes first name, last name, fitness level, and two dropdowns for selecting security questions used in password reset.
- **Reset Password Page**: Allows existing users to reset their password using the selected security questions.
- **Administrator Interface**: Displays a table of all system users. Admins can assist with password verification but cannot access private data such as passwords.

---

## Joshua Carroll

Developed:

- **TemplateFrame**:
  - Handles layout (split frames), menu bar creation, logout option, and reusable input field utilities.
- **Create Exercise Page**: Built using TemplateFrame.
- **Create Workout Page**: Users select exercises, assign a name/date, and create workouts.
- **Track Workout Page**: Displays workout details and exercises.
- **Trainer Classes**:
  - `TrainerClass` extends `Workout` for reuse.
  - Trainers can create, view (via a chart), and manage classes.
  - Users can register for available classes on the **Track Classes** page, which shows class size and trainer username.

---

## Eli Hall

Implemented tracking features:

- **Sleep Page**:
  - Allows users to input daily sleep.
  - Displays progress via a progress bar toward a weekly sleep goal.
  - Input validation ensures only valid numbers are accepted.

- **RecordWeight Page** (foundations):
  - Allows weight input.
  - Displays reports based on progress toward goal weight.

- **Testing**:
  - **JUnit** for model classes like `User` and `Exercise`.
  - **Manual testing** for 5 workflows: CreateExercise, RecordWeight, TrackCalories, RecordSleep, LoginPage.

---

## Dakota Hernandez

Contributions include:

- Created the initial **Calorie Tracker** (evolved from variables → CSV → database).
- Helped develop and integrate **TemplateFrame** across multiple pages.
- Built a **Themes Class** to centralize UI styling.
- Conducted a **UI Overhaul** to unify page sizes and components.
- Added a **Date Picker API** to HomePage for date-based navigation and data updates.
- Rebuilt **Weight Tracking Page**:
  - Added a table of past entries.
  - Integrated **JFreeChart** for weight-over-time graph.
- Connected **goals, weight, and calorie tracking** to the database.
- Built an **Email Reminder System** using the **Mailjet API**.
- Created a **Calorie Calculator Popup** to estimate daily intake on the SetGoals page.

---

## Mac Johnson

Led the development of:

- **User Account System**:
  - Login page with input validation (including proper email format).
  - Support for general users and trainers.
  - Tracks the current user across all interfaces via a shared variable.

- **Self-Paced Plans**:
  - Trainers can create/edit/delete daily workout plans composed of exercises.
  - Plans stored and managed via a dedicated SQLite schema.

- **Trainer/Client Integration**:
  - Users can browse and register for trainer-made plans.
  - Trainer interface is enabled based on user account type.

- **Project Website**: Designed and implemented from scratch using HTML, CSS, and JavaScript.

---

## Faith Ota

Created:

- **HomePage Frame** (subclass of abstract TemplateFrame):
  - Displays logo, integrated menu bar, and logout.
  - Menu enables navigation without opening redundant windows.

- **SetGoals Page**:
  - Allows users to define calorie, sleep, and weight goals.
  - Includes validation and confirmation dialogs.
  - Defaults applied if user opts out of the goals feature.
