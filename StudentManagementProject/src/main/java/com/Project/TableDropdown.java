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

@WebServlet("/TableDropdown")
public class TableDropdown extends HttpServlet 
{
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        String countryId = request.getParameter("countryId");
        if (countryId == null || countryId.trim().isEmpty()) 
        {
            out.write("Invalid country ID");
            return;
        }

        try 
        {  
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/StudentManagement", "root", "root")) 
            {
                String query = "SELECT id, name FROM State WHERE country_id = ?";
                PreparedStatement pst = con.prepareStatement(query);
                pst.setInt(1, Integer.parseInt(countryId));
                ResultSet rs = pst.executeQuery();
                
                while (rs.next()) 
                {
                    int stateId = rs.getInt("id");
                    String stateName = rs.getString("name");
                    out.write(stateId + ":" + stateName + "\n");
                }
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
            response.setStatus(500); 
            out.write("Database error: " + e.getMessage());
        } 
        catch (NumberFormatException e) 
        {
            e.printStackTrace();
            response.setStatus(400); 
            out.write("Invalid country ID format: " + e.getMessage());
        } 
        finally 
        {
            out.close();
        }
    }
}
