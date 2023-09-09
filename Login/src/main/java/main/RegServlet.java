package main;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RegServlet extends HttpServlet {
       
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
			String s1 = request.getParameter("fname");
			String s2 = request.getParameter("lname");
			String s3 = request.getParameter("uname");
			String s4 = request.getParameter("password");
			PreparedStatement pstmt = con.prepareStatement("Insert into uinfo values(?,?,?,?)");
			pstmt.setString(1, s1);
			pstmt.setString(2, s2);
			pstmt.setString(3, s3);
			pstmt.setString(4, s4);
			pstmt.executeUpdate();
			PrintWriter pw = response.getWriter();
			pw.println("<html><body bgcolor=red text=Yellow>");
			pw.println("<h1>You Have Registrated Successfully </h1>");
			pw.println("</body></html>");
		}catch(SQLException | IOException e) {
			e.printStackTrace();
		}
	}

}
