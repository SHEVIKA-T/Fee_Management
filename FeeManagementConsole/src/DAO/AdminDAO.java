package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Model.Admin;
import Model.DBConnection;

public class AdminDAO {
    private Connection connection;

    public AdminDAO() {
        connection = DBConnection.getConnection();
    }
    

	   public void addAdmin(Admin admin) {
	        try {
	            String query = "INSERT INTO admin (admin_id, username, password) VALUES (?, ?, ?)";
	            PreparedStatement statement = connection.prepareStatement(query);
	            statement.setString(1, admin.getAdminId());
	            statement.setString(2, admin.getUsername());
	            statement.setString(3, admin.getPassword());
	            int rowsInserted = statement.executeUpdate();
	            if (rowsInserted > 0) {
	                System.out.println("Admin added successfully.");
	            } else {
	                System.out.println("Failed to add admin.");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    public void deleteAdmin(String adminId) {
	        try {
	            String query = "DELETE FROM admin WHERE admin_id = ?";
	            PreparedStatement statement = connection.prepareStatement(query);
	            statement.setString(1, adminId);
	            int rowsDeleted = statement.executeUpdate();
	            if (rowsDeleted > 0) {
	                System.out.println("Admin deleted successfully.");
	            } else {
	                System.out.println("Admin not found.");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

    public List<Admin> viewAdmins() {
        List<Admin> admins = new ArrayList<>();
        try {
            String query = "SELECT admin_id,username FROM admin";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String adminId = resultSet.getString("admin_id");
                String username = resultSet.getString("username");
              
                Admin admin = new Admin(adminId, username);
                admins.add(admin);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admins;
    }
}
