package com.Project;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Dashboard")
public class DashboardServlet extends HttpServlet 
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        try 
        {
            int studentId = parseInteger(request.getParameter("student_id"));
            String studentName = request.getParameter("student_name");
            int studentAge = parseInteger(request.getParameter("student_age"));
            String studentGender = request.getParameter("student_gender");
            String studentDOB = request.getParameter("student_dob");
            String studentPhone = request.getParameter("student_phone");
            String studentDOJ = request.getParameter("student_date_of_joining");
            String studentFee = request.getParameter("student_fee");
            String studentBranch = request.getParameter("student_branch");
            int studentCountry = parseInteger(request.getParameter("student_country"));
            int studentState = parseInteger(request.getParameter("student_state"));
            String studentAddress = request.getParameter("student_address");

            if (isNullOrEmpty(studentName) || isNullOrEmpty(studentGender) || isNullOrEmpty(studentDOB)
                || isNullOrEmpty(studentPhone) || isNullOrEmpty(studentDOJ) || isNullOrEmpty(studentFee)
                || isNullOrEmpty(studentBranch) || isNullOrEmpty(studentAddress)) 
            {
                throw new IllegalArgumentException("Required fields are missing.");
            }

            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/StudentManagement", "root", "root")) 
            {
                String query = "INSERT INTO dashboard (student_id, student_name, student_age, student_gender, student_dob, student_phone, student_date_of_joining, student_fee, student_branch, student_country, student_state, student_address) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pst = con.prepareStatement(query);
                pst.setInt(1, studentId);
                pst.setString(2, studentName);
                pst.setInt(3, studentAge);
                pst.setString(4, studentGender);
                pst.setString(5, studentDOB);
                pst.setString(6, studentPhone);
                pst.setString(7, studentDOJ);
                pst.setString(8, studentFee);
                pst.setString(9, studentBranch);
                pst.setInt(10, studentCountry);
                pst.setInt(11, studentState);
                pst.setString(12, studentAddress);

                int result = pst.executeUpdate();
               
                if (result > 0) 
                {
                    response.sendRedirect("dashboard.html?status=success");
                } 
                else 
                {
                    response.sendRedirect("dashboard.html?status=failure");
                }
            } 
            catch (SQLException e) 
            {
                e.printStackTrace();
                response.sendRedirect("dashboard.html?status=failure");
            }

        } catch (IllegalArgumentException e) 
        {
            e.printStackTrace();
            response.sendRedirect("dashboard.html?status=failure");
        }
    }

    private boolean isNullOrEmpty(String str) 
    {
        return str == null || str.trim().isEmpty();
    }

    private int parseInteger(String str) 
    {
        if (isNullOrEmpty(str)) 
        {
            return 0;
        }
        return Integer.parseInt(str);
    }
}
