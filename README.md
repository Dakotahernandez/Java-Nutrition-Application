# PawPlates by team.java

## Faith Ota, Dakota Hernandez, Mac Johnson, Sofia Amador, Eli Hall, & Joshua Carroll

### Project Site:
https://macj2005.github.io/PawPlatesProjectSite/

### Health & Fitness App
You have been contracted to create an application that empowers a user to monitor and track their progress along their health journey. The application should offer a suite of tools that allows a user to record, analyze, and set goals for different aspects of their life.

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
Feature Summary

**Health**
The application should allow users to track and set goals for a variety of health-related aspects of their life.

**Tracking**

Users can create a profile and have a dashboard with a summary of their current health and fitness progress
At a minimum, users can track their:
+ Daily calorie intake
+ Weight
+ Sleep
+ Goals and Reporting

Users can set daily, weekly, or monthly goals for each tracked aspect
Users can view their historical data for each aspect
Users can get statistics about their progress toward a goal over a specific timeframe
Users can set reminders for daily tasks

**Fitness**
The application should allow users to monitor their exercise in a variety of ways.

**Trainer Led Exercise**

Trainers use the application to create exercise plans. These plans can be either self-paced or a scheduled class
Self-paced plans include information about required equipment, recommended fitness level, average session length, and suggested frequency
Scheduled classes include information about the date, time, and days of the week the class occurs, the length of the class (i.e. session length and number of weeks the class lasts), recommended fitness level, recommended or required prerequisites, and required equipment
Trainers can limit the number of participants in the class
Trainers can see information about their plans and classes. For example, they can check how many people have signed up for a plan and fully completed it or how many people are registered for one of their classes

**Personal Exercise**

+ Users can track their daily exercise
+ They can record individual workouts, including information like duration, type of exercise, calories burned, etc.
+ They can track their daily step count
+ They can create templates for their favorite workouts
+ They can set reminders to start a workout
+ Users can register for Trainer created exercises
+ They can add a self-paced plan to their account
+ They can sign up for and attend trainer-led classes
+ Users can set goals for weekly exercise and view reports on their progress

**Bonus**
The application provides social functionality, allowing a user to challenge and encourage other users. Examples:

Users can friend each other and then send exercise challenges, send encouraging messages, or congratulate them for achieving a goal
Users can join a group and set group challenges

**General Requirements**
It is very important to keep in mind that the requirements do not include all corner cases and validations that are needed. These are just a few examples to get you started. Part of your project is to think about each use case and discuss what would be the best solution with your team. If you have doubts, ask me. Example: What happens when a user tries to join a class that is full or doesn’t meet the prerequisites?

These are the requirements for a general user:

+ A general user should be able to log in to the system using a username and password
+ A general user should be able to log daily calorie intake, weight, and sleep
+ A general user should be able to record a workout
+ A general user should be able to search for and sign up for self-paced plans
+ A general user should be able to search for and sign up for and attend trainer-led classes
+ A general user should be able to set goals and track their progress
+ A general user should be able to see their historical trends

These are the requirements for a trainer:

+ A trainer should be able to log in to the system using a username and password
+ A trainer should be able to create self-paced exercise plans
+ A trainer should be able to create exercise classes
+ A trainer should be able to modify their plans and classes
+ A trainer should be able to start and end a session of a particular class
+ A trainer should be able to view statistics about their self-paced plans and classes

These are the requirements for an admin:

+ An admin should be able to log in to the system using a username and password
+ An admin should be able to view all the general users and trainers
+ An admin should be able to reset passwords

Some examples of topics to discuss with your team while designing the application:

Should an admin have access to a general user’s data?
What information should a trainer know about their students?
What are the potential tradeoffs between functionality and usability?
The minimum scope of the full implementation (with GUI connected) expected for the Iteration 3 final demo:

A general user should be able to:
+ Create an account with a username and password
+ Log in to the system using a username and password
+ See a dashboard of their current progress
+ Log daily information, like calorie intake and weight
+ Record a workout
+ Search for self-paced exercise plans and add them to their account
+ Search for classes and register for the class
+ Attend an in-progress class
+ See a report of their historical data and goal targets
A trainer should be able to:
+ Create an account with a username and password
+ Log in to the system using a username and password
+ Create a self-paced exercise plan
+ Create and register a class
+ Host a session of a class
+ Modify an existing plan or class
+ View reports about their plans and classes
An admin should be able to:
+ Log in to the system using a username and password
+ Add a user to the system
+ Reset a user’s password

**Bonus Features**

+ A general user should be able to friend another user
+ A general user should be able to challenge a friend
+ A general user should be alerted when a friend reaches a goal
+ A general user should be able to join a group
+ A group should be able to post challenges
+ A trainer should be able to message a class’s participants

