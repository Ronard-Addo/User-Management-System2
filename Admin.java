package userManagementSystem;

import java.sql.*;
import java.util.Scanner;


	
class Admin extends Employee {
	
	// Scanner declared as a class variable
    private static final Scanner scanner = new Scanner(System.in);
    
    
	// Database connection details
    private static final String url = "jdbc:mysql://localhost:3306/ums";
    private static final String username = "root";
    private static final String password = "";
    
	
	@Override	// Method overriding
	protected void userOptions() {	
    	boolean valid ;
		boolean restart;
		
		do {
			valid = true;
			restart = false;
			
	    	System.out.println("\nChoose one of the following options:\n1.Change user's firstname.\n2. Change last name\3. Change age");
	    	int choice = scanner.nextInt();
	    	
	    	switch(choice) {
		    	case 0:
		    		return;
		    	case 1:
		    		setUserFirstName();
		    		break;
		    	case 2:
		    		setUserLastName();
		    		break;
		    	case 3:
		    		setUserAge();
		    		break;
		    	default:
		    		System.out.println("Invalid input!");
		    		valid = false;
		    		  			
	    	}
	    	
	    	if(!valid) {
	        	restart = prompt(valid);
	        	if (!restart) return;
	    	}
	    	
		} while (restart);
    }
	
	
	public void removeRow() {
        
		boolean valid ;
		boolean restart;
		String tableName;
		String columnName;
		int id;
		
		// Sorry about the redundant code
		do {
			
			restart = false;
			valid = true;
			
			System.out.print("Enter the table name: ");
			tableName = scanner.nextLine();
			
			if(!valid) {
	        	restart = prompt(valid);
	        	if (!restart) return;
	    	}
			
		} while (restart);
		
		
		do {
			
			restart = false;
			valid = true;
			
			System.out.print("Enter the table name: ");
			columnName = scanner.nextLine();
			
			if(!valid) {
	        	restart = prompt(valid);
	        	if (!restart) return;
	    	}
			
		} while (restart);
		
		do {
			
			restart = false;
			valid = true;
			
			System.out.print("Enter the table name: ");
			id = scanner.nextInt();
			
			if(!valid) {
	        	restart = prompt(valid);
	        	if (!restart) return;
	    	}
			
		} while (restart);
		
		String query = "DELETE FROM " + tableName + " WHERE " + columnName + " = ?";
        
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
             
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Row deleted successfully.");
            } else {
                System.out.println("No matching record found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}