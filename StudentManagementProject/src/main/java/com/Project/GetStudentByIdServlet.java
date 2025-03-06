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

@WebServlet("/GetStudentById")
public class GetStudentByIdServlet extends HttpServlet 
{
    private static final String DB_URL = "jdbc:mysql://localhost:3306/StudentManagement";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        String idParam = request.getParameter("id");

        if (idParam == null || idParam.isEmpty()) 
        {
            response.getWriter().write("error|ID parameter is required");
            return;
        }

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PrintWriter out = response.getWriter()) 
        {

            String query = "SELECT * FROM dashboard WHERE student_id = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, Integer.parseInt(idParam));
            ResultSet rs = pst.executeQuery();

            if (rs.next()) 
            {
                out.write(rs.getInt("student_id") + "|" +
                          rs.getString("student_name") + "|" +
                          rs.getInt("student_age") + "|" +
                          rs.getString("student_gender") + "|" +
                          rs.getDate("student_dob") + "|" +
                          rs.getString("student_phone") + "|" +
                          rs.getDate("student_date_of_joining") + "|" +
                          rs.getBigDecimal("student_fee") + "|" +
                          rs.getString("student_branch") + "|" +
                          rs.getInt("student_country") + "|" +
                          rs.getInt("student_state") + "|" +
                          rs.getString("student_address"));
            } 
            else 
            {
                out.write("error|Student not found");
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
            response.getWriter().write("error|An error occurred. Please try again later.");
        }
    }
}
