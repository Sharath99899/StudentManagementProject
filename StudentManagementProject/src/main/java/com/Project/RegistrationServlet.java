package com.Project;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Register")
public class RegistrationServlet extends HttpServlet 
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        String name = request.getParameter("student_name");
        int age = Integer.parseInt(request.getParameter("student_age"));
        String gender = request.getParameter("student_gender");
        String phone = request.getParameter("student_phone");
        String bloodGroup = request.getParameter("student_blood_group"); // Corrected field name
        String email = request.getParameter("student_email");
        String password = request.getParameter("student_password");

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/StudentManagement", "root", "root"))
        {
            String checkEmailQuery = "SELECT * FROM registration WHERE student_email = ?";
            PreparedStatement checkEmailStmt = con.prepareStatement(checkEmailQuery);
            checkEmailStmt.setString(1, email);
            ResultSet rs = checkEmailStmt.executeQuery();

            if (rs.next()) {
                response.sendRedirect("register.html?error=duplicate");
                return;
            }

            String query = "INSERT INTO registration (student_name, student_age, student_gender, student_phone, student_blood_group, student_email, student_password) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, name);
            pst.setInt(2, age);
            pst.setString(3, gender);
            pst.setString(4, phone);
            pst.setString(5, bloodGroup); // Corrected parameter
            pst.setString(6, email);
            pst.setString(7, password);

            int result = pst.executeUpdate();
            if (result > 0) 
            {
                response.sendRedirect("register.html?status=success");
            } 
            else 
            {
                response.sendRedirect("register.html?status=error");
            }
        } 
        catch (SQLException e)
        {
            e.printStackTrace();
            response.setStatus(500);
        }
    }
}
