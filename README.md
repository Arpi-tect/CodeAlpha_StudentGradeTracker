# CodeAlpha_StudentGradeTracker

An advanced, full-stack student grade management and analytics application built with a **Java Spring Boot** REST API backend and a responsive **React.js & Tailwind CSS** web interface.

## 🚀 Features
*   **Gradebook Registry**: Add, delete, and view student records in a clean tabular view.
*   **Real-time Class Analytics**: Dynamic statistics cards calculation class average, highest grade, and lowest grade on every updates.
*   **Local File Database Persistence**: Uses an embedded H2 database with local file-based persistence (`gradesdb`), retaining data across restarts.
*   **API-Driven Architecture**: Clear separation of concern between the Spring Boot REST endpoints and the React frontend.
*   **Sleek Glassmorphic Design**: Modern typography (Outfit font) and responsive card layouts.

---

## 🛠️ Technology Stack
*   **Backend**: Java 17+, Spring Boot 3.x, Spring Data JPA, Hibernate, H2 Database.
*   **Frontend**: React (ES6+), Tailwind CSS, Babel, HTML5.
*   **Build Tool**: Maven Wrapper (included).

---

## 💻 How to Run Locally

You only need **Java 17 or higher** installed on your machine. You do not need to install Maven or Node.js.

### Step 1: Clone the repository
```bash
git clone https://github.com/<your-username>/CodeAlpha_StudentGradeTracker.git
cd CodeAlpha_StudentGradeTracker
```

### Step 2: Boot up the Spring Boot Application
Run the Maven wrapper command in your terminal:

*   **On Windows (Command Prompt / PowerShell)**:
    ```cmd
    mvnw.cmd spring-boot:run
    ```
*   **On Linux / macOS**:
    ```bash
    chmod +x mvnw
    ./mvnw spring-boot:run
    ```

The Maven Wrapper will automatically download Maven and the required dependencies on the first run, initialize the local H2 file database, and start the Tomcat web server on port `8080`.

### Step 3: Open in Browser
Open your browser and navigate to:
👉 **[http://localhost:8080](http://localhost:8080)**

---

## 📁 Repository Structure
```text
StudentGradeTracker/
├── pom.xml                     # Maven dependencies & configurations
├── mvnw & mvnw.cmd             # Maven Wrapper scripts
├── data/                       # H2 Local database file storage
└── src/
    └── main/
        ├── java/com/codealpha/gradetracker/
        │   ├── StudentGradeTrackerApplication.java  # Main Boot class
        │   ├── model/Student.java                  # JPA Database Entity
        │   ├── repository/StudentRepository.java    # Database access layer
        │   └── controller/GradeController.java      # REST API endpoints
        └── resources/
            ├── application.properties               # H2 database configurations
            └── static/
                └── index.html                       # React & Tailwind Web Client
```

---

## 📡 REST API Documentation

*   **Get All Students**: `GET /api/students`
*   **Add Student**: `POST /api/students` (Body: `{ "name": "Alice", "grade": 92.5 }`)
*   **Delete Student**: `DELETE /api/students/{id}`
*   **Class Statistics**: `GET /api/students/stats` (Returns average, highest, lowest, and count)
