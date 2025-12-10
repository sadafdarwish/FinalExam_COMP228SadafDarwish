# Final Exam – JavaFX Employment Application  
**Author:** Sadaf Darwish  
**Course:** COMP 228 – Java Programming  
**Date:** December 2025  

---

## 📌 Project Overview  
This project is a JavaFX desktop application created for the COMP228 final exam.  
It allows a user to submit employment application data, validates the input, and stores the information into a MySQL database.

The application includes:

- JavaFX GUI (Form)
- Form validation  
- MySQL database connection  
- MVC structure (`Main`, `FormController`, `DBConnection`)  
- FXML-based UI layout  
- Clean input rules (name, contact, education, date, salary)

---

## 🖥️ Application Features  

### ✔ Full Name  
- Only letters and spaces  
- Max 50 characters  

### ✔ Contact Number  
- Must be **exactly 10 digits**

### ✔ Highest Education  
- Selected from a ComboBox  

### ✔ Date Picker  
- Must not be empty  

### ✔ Salary  
- Up to 8 digits + 2 decimals  
- Example: `12345678.50`

### ✔ Database Insert  
When the user clicks **Submit**, the form:

1. Validates input  
2. Sends the data to MySQL  
3. Displays success or error messages  

---

## 🗂️ Project Structure  

FinalExam_COMP228SadafDarwish/
│
├── src/
│ ├── application/
│ │ ├── Main.java
│ │ ├── FormController.java
│ │ ├── DBConnection.java
│ │ └── form.fxml
│
├── javafx-sdk-25.0.1/ (JavaFX library)
├── .classpath
├── .project
└── final exam_SadafDarwish.zip


---

## 🔧 Technologies Used  
- **Java 21**  
- **JavaFX 25**  
- **MySQL 8 JDBC Driver**  
- **Eclipse IDE**

---

## ▶️ How to Run This Project

### **1. Install JavaFX**
Download JavaFX SDK 25 and extract it.

### **2. Add JavaFX libraries in Eclipse**
`Project → Properties → Java Build Path → Add External JARs`  
Select all files inside:



javafx-sdk-25.0.1/lib


### **3. Add VM Arguments (IMPORTANT)**  
Go to:



Run → Run Configurations → Arguments → VM Arguments


Paste this (update path to your JavaFX lib folder):



--module-path "C:\path\to\javafx-sdk-25.0.1\lib" --add-modules javafx.controls,javafx.fxml


### **4. Add MySQL JDBC Driver (mysql-connector-j.jar)**
Add to Build Path.

---

## 🗄️ Database Setup  

Create a database and table:

```sql
CREATE DATABASE employment;

USE employment;

CREATE TABLE applications (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fullname VARCHAR(50),
    contact VARCHAR(10),
    education VARCHAR(50),
    date DATE,
    salary DECIMAL(10,2)
);
