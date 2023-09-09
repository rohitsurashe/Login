package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


// 09/09/2023

public class LoginServlet extends HttpServlet {

	Connection con;
	public void init(ServletConfig config) throws ServletException{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:xe","system","Manager");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void destroy() {
		try {
			con.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String s1 = request.getParameter("uname");
			String s2 = request.getParameter("password");
			PreparedStatement pstmt = con.prepareStatement("select * from uinfo where uname=? and pword=?");
			pstmt.setString(1, s1);
			pstmt.setString(2, s2);
			ResultSet rs = pstmt.executeQuery();
			PrintWriter pw = response.getWriter();
			
			pw.println("<html><body bgcolor=cyan text=blue ><h1>");
			if(rs.next())
			{
				pw.println("Welcome "+s1);
			}
			else
			{
				pw.println("Invalid Username/Password");
			}
			pw.println("</h1></body></html>");
	}catch(Exception e) {
		e.printStackTrace();
	}
	}

}
