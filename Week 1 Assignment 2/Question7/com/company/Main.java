package com.company;

import java.sql.*;
import java.util.Scanner;

public class Main {
    static final String JDBC_URL = "jdbc:mysql://localhost:3306/employeedb";
    static final String USERNAME = "root";
    static final String PASSWORD = "password";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            System.out.println(" Connected to database!");

            while (true) {
                System.out.println("\n===== Employee CRUD Menu =====");
                System.out.println("1. Add Employee");
                System.out.println("2. View All Employees");
                System.out.println("3. Update Employee");
                System.out.println("4. Delete Employee");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();
                sc.nextLine(); // consume newline

                switch (choice) {
                    case 1:
                        System.out.print("Enter employee name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter department: ");
                        String dept = sc.nextLine();
                        String insertSQL = "INSERT INTO employees (name, department) VALUES (?, ?)";
                        try (PreparedStatement ps = connection.prepareStatement(insertSQL)) {
                            ps.setString(1, name);
                            ps.setString(2, dept);
                            int rows = ps.executeUpdate();
                            System.out.println(rows > 0 ? " Employee added." : "Failed to add employee.");
                        }
                        break;

                    case 2:
                        String selectSQL = "SELECT * FROM employees";
                        try (Statement stmt = connection.createStatement();
                             ResultSet rs = stmt.executeQuery(selectSQL)) {
                            System.out.println("\n Employee List:");
                            while (rs.next()) {
                                int id = rs.getInt("id");
                                String empName = rs.getString("name");
                                String empDept = rs.getString("department");
                                System.out.println("ID: " + id + ", Name: " + empName + ", Department: " + empDept);
                            }
                        }
                        break;

                    case 3:
                        System.out.print("Enter employee ID to update: ");
                        int updateId = sc.nextInt();
                        sc.nextLine(); // consume newline
                        System.out.print("Enter new name: ");
                        String newName = sc.nextLine();
                        System.out.print("Enter new department: ");
                        String newDept = sc.nextLine();
                        String updateSQL = "UPDATE employees SET name = ?, department = ? WHERE id = ?";
                        try (PreparedStatement ps = connection.prepareStatement(updateSQL)) {
                            ps.setString(1, newName);
                            ps.setString(2, newDept);
                            ps.setInt(3, updateId);
                            int rows = ps.executeUpdate();
                            System.out.println(rows > 0 ? "Employee updated." : "Employee not found.");
                        }
                        break;

                    case 4:
                        System.out.print("Enter employee ID to delete: ");
                        int deleteId = sc.nextInt();
                        sc.nextLine(); // consume newline
                        String deleteSQL = "DELETE FROM employees WHERE id = ?";
                        try (PreparedStatement ps = connection.prepareStatement(deleteSQL)) {
                            ps.setInt(1, deleteId);
                            int rows = ps.executeUpdate();
                            System.out.println(rows > 0 ? "Employee deleted." : "Employee not found.");
                        }
                        break;

                    case 5:
                        System.out.println("Exiting");
                        return;

                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error:");
            e.printStackTrace();
        }
    }
}
