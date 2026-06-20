# ApexGrade - Advanced Student Analytics Dashboard

An enterprise-grade, full-stack student academic performance and analytics application built with a **Java Spring Boot** REST API backend and a responsive **React.js & Tailwind CSS** web interface.

---

## 🚀 Features

*   **Multi-Subject Gradebook Registry**: Track student performance across four core subjects: **Mathematics**, **Physics**, **Chemistry**, and **Computer Science**.
*   **Real-time SVG Range Distribution Chart**: Displays a beautiful, custom-rendered SVG bar chart visualizing the frequency distribution of student GPAs across standard letter grade ranges (A, B, C, D, F).
*   **Dynamic Threshold Configurator**: Slide the passing score threshold and watch class averages, highest/lowest grades, and class pass/fail percentages calculate in real-time.
*   **Academic Report Card Generator Tab**: Select any student to compile their subject-specific scores, GPA, pass/fail status, and generate custom remarks in a clean, printable report card view.
*   **H2 Database Persistence**: Embedded database engine with local file-based storage (`gradesdb`) to retain student records across restarts.
*   **Google OAuth & Guest Login**: Premium security access wall supporting real Google Identity Services or a single-click Guest Login bypass.

---

## 🛠️ Technology Stack

*   **Backend**: Java 17, Spring Boot 3.x, Spring Data JPA, Hibernate, H2 Database.
*   **Frontend**: React (ES6+), Tailwind CSS, Babel, HTML5, Custom Responsive SVG Charts.
*   **Build Tool**: Maven Wrapper (included).

---

## 💻 How to Run Locally

You only need **Java 17 or higher** installed.

### Step 1: Clone the repository
```bash
git clone https://github.com/Arpi-tect/CodeAlpha_StudentGradeTracker.git
cd CodeAlpha_StudentGradeTracker
```

### Step 2: Start the Application
Run the Maven wrapper command in your terminal:
*   **On Windows**:
    ```cmd
    mvnw.cmd spring-boot:run
    ```
*   **On Linux / macOS**:
    ```bash
    chmod +x mvnw
    ./mvnw spring-boot:run
    ```

The Maven Wrapper will automatically boot the Tomcat server on port `8080`.

### Step 3: Open in Browser
👉 **[http://localhost:8080](http://localhost:8080)**

---

## 📁 Repository Structure
```text
StudentGradeTracker/
├── pom.xml                     # Maven configurations
├── mvnw & mvnw.cmd             # Maven Wrapper scripts
├── data/                       # H2 Local database storage
└── src/
    └── main/
        ├── java/com/apex/gradetracker/
        │   ├── StudentGradeTrackerApplication.java  # Main Boot class
        │   ├── model/Student.java                  # JPA Database Entity
        │   ├── repository/StudentRepository.java    # Database access layer
        │   └── controller/GradeController.java      # REST API endpoints
        └── resources/
            ├── application.properties               # Config file (Port 8080)
            └── static/
                └── index.html                       # React Web Client
```

---

## 📡 REST API Documentation

*   **Get All Students**: `GET /api/students`
*   **Add Student**: `POST /api/students` 
    *   Parameters: `name`, `mathGrade`, `physicsGrade`, `chemistryGrade`, `csGrade`
*   **Delete Student**: `DELETE /api/students/{id}`
*   **Subject Specific Analytics**: `GET /api/students/stats` (Returns class averages, GPAs, and distribution aggregates)
