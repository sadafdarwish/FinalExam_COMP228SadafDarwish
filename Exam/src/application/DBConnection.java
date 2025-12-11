package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class DBConnection {

    // Load MySQL driver once when the class is loaded
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL JDBC Driver Loaded Successfully.");
        } catch (ClassNotFoundException e) {
            System.out.println("Failed to load MySQL JDBC Driver.");
            e.printStackTrace();
        }
    }

    // UPDATE user/password to match your MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/final_exam_db";
    private static final String USER = "root";       // your MySQL username
    private static final String PASSWORD = "sadaf";       // your MySQL password, if any

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * Inserts a new applicant into ApplicantTable and an 'Applied' row
     * into EmploymentTable. Returns true if both inserts succeed.
     */
    public static boolean insertApplication(String fullName, String contact,
                                            String education, LocalDate date,
                                            double salary) {

        String insertApplicantSql =
                "INSERT INTO ApplicantTable (full_name, contact_number, education, dob, salary) " +
                "VALUES (?, ?, ?, ?, ?)";

        String insertEmploymentSql =
                "INSERT INTO EmploymentTable (applicant_id, status) VALUES (?, 'Applied')";

        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false); // start transaction

            int applicantId;

            // 1) Insert into ApplicantTable
            try (PreparedStatement ps = conn.prepareStatement(insertApplicantSql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, fullName);
                ps.setString(2, contact);
                ps.setString(3, education);
                ps.setDate(4, java.sql.Date.valueOf(date));
                ps.setDouble(5, salary);

                int rows = ps.executeUpdate();
                if (rows == 0) {
                    conn.rollback();
                    return false;
                }

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        applicantId = rs.getInt(1);
                    } else {
                        conn.rollback();
                        return false;
                    }
                }
            }

            // 2) Insert into EmploymentTable with status 'Applied'
            try (PreparedStatement ps2 = conn.prepareStatement(insertEmploymentSql)) {
                ps2.setInt(1, applicantId);
                ps2.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            System.out.println("Database insert error:");
            e.printStackTrace();
            return false;
        }
    }
}
