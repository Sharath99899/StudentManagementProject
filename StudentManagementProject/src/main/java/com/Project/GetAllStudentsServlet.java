package com.Project;

import java.io.IOException;
import java.io.PrintWriter;
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

@WebServlet("/GetAllStudents")
public class GetAllStudentsServlet extends HttpServlet 
{

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/StudentManagement", "root", "root")) 
        {
            String query = "SELECT * FROM dashboard";
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            StringBuilder jsonResponse = new StringBuilder();
            jsonResponse.append("[");

            boolean first = true;
            while (rs.next()) 
            {
                if (!first) jsonResponse.append(",");
                first = false;

                jsonResponse.append("{");
                jsonResponse.append("\"student_id\":").append(rs.getInt("student_id")).append(",");
                jsonResponse.append("\"student_name\":\"").append(rs.getString("student_name")).append("\",");
                jsonResponse.append("\"student_age\":").append(rs.getInt("student_age")).append(",");
                jsonResponse.append("\"student_gender\":\"").append(rs.getString("student_gender")).append("\",");
                jsonResponse.append("\"student_dob\":\"").append(rs.getDate("student_dob")).append("\",");
                jsonResponse.append("\"student_phone\":\"").append(rs.getString("student_phone")).append("\",");
                jsonResponse.append("\"student_date_of_joining\":\"").append(rs.getDate("student_date_of_joining")).append("\",");
                jsonResponse.append("\"student_fee\":").append(rs.getBigDecimal("student_fee")).append(",");
                jsonResponse.append("\"student_branch\":\"").append(rs.getString("student_branch")).append("\",");
                jsonResponse.append("\"student_country\":\"").append(rs.getString("student_country")).append("\",");
                jsonResponse.append("\"student_state\":\"").append(rs.getString("student_state")).append("\",");
                jsonResponse.append("\"student_address\":\"").append(rs.getString("student_address")).append("\"");
                jsonResponse.append("}");
            }
            jsonResponse.append("]");

            PrintWriter out = response.getWriter();
            out.print(jsonResponse.toString());
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
            response.setStatus(500);
            response.getWriter().write("{\"error\":\"Database connection failed: " + e.getMessage() + "\"}");
        }
    }
}
