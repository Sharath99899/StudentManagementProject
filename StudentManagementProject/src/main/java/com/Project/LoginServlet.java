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

@WebServlet("/Login")
public class LoginServlet extends HttpServlet 
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        String email = request.getParameter("student_email");
        String password = request.getParameter("student_password");

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/StudentManagement", "root", "root"))
        {
            String query = "SELECT * FROM registration WHERE student_email = ? AND student_password = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, email);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) 
            {
                response.sendRedirect("dashboard.html");
            } 
            else 
            {
                response.sendRedirect("login.html?error=true");
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
            response.setStatus(500);
        }
    }
}