package userManagementSystem;

import java.sql.*;
import java.util.Scanner;


public class Employee extends User{
    
    /*protected Employee(String name, int age, String gender, String city, String province, String country) {
        super(name, age, gender, city, province, country);
    }*/

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
    
    
	public boolean userExists(int userId) {
		
		String query = "SELECT COUNT(*) FROM users WHERE id = ?";

	    try (Connection conn = DriverManager.getConnection(url, username, password);
	         PreparedStatement pstmt = conn.prepareStatement(query)) {

	        pstmt.setInt(1, userId);

	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt(1) > 0; // Returns true if count > 0 (user exists)
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    return false; // User does not exist or query failed
	}

	// Getters
    protected void setUserFirstName() {
    	//try (Scanner scanner = new Scanner(System.in)) {
    		boolean valid ;
    		boolean restart;
    		int userId;
    		
    		do {
    			
    			restart = false;
    			valid = true;
	        
		        System.out.println("Enter the id of the user whose first name you want to change: ");
		    	userId = scanner.nextInt();
		    	scanner.nextLine();
		    	
		    	if(!userExists(userId)) {
		    		System.out.println("User does not exist");
		    		valid = false;
		    	}
		    	
		    	if(!valid) {
		        	restart = prompt(valid);
		        	if (!restart) return;
		    	}
		    	
    		} while (restart);
	        
	    	if(!reqNameChange(userId)) {  
	        	System.out.println("Access denied. You do not have permission to use this feature");
	        	return;
	        }
	    	
	    	setFirstName();		// *** Inherited method
    	//}
        
    }
    
    protected void setUserLastName() {
    	//try (Scanner scanner = new Scanner(System.in)) {
    		boolean valid ;
    		boolean restart;
    		int userId;
    		
    		do {
    			
    			restart = false;
    			valid = true;
	        
		        System.out.println("Enter the id of the user whose last name you want to change: ");
		    	userId = scanner.nextInt();
		    	scanner.nextLine();
		    	
		    	if(!userExists(userId)) {
		    		System.out.println("User does not exist");
		    		valid = false;
		    	}
		    	
		    	if(!valid) {
		        	restart = prompt(valid);
		        	if (!restart) return;
		    	}
		    	
    		} while (restart);
	        
	    	if(!reqNameChange(userId)) {  
	        	System.out.println("Access denied. You do not have permission to use this feature");
	        	return;
	        }
	    	
	    	setLastName();		// *** Inherited method
    	//}
        
    }
    
    protected void setUserAge() {
    	//try (Scanner scanner = new Scanner(System.in)) {
    		boolean valid ;
    		boolean restart;
    		int userId;
    		
    		do {
    			
    			restart = false;
    			valid = true;
	        
		        System.out.println("Enter the id of the user whose last name you want to change: ");
		    	userId = scanner.nextInt();
		    	scanner.nextLine();
		    	
		    	if(!userExists(userId)) {
		    		System.out.println("User does not exist");
		    		valid = false;
		    	}
		    	
		    	if(!valid) {
		        	restart = prompt(valid);
		        	if (!restart) return;
		    	}
		    	
    		} while (restart);
	        
	    	if(!reqNameChange(userId)) {  
	        	System.out.println("Access denied. You do not have permission to use this feature");
	        	return;
	        }
	    	
	    	setAge();		// *** Inherited method
    	//}
        
    }

    protected void getStatus() {
    	String field_name = "status";
    	System.out.println(getValue(field_name, id));
    }
    
    protected boolean reqNameChange(int id) {
        String field_name = "reqNameChange";
        
        return getValue(field_name, id).matches("true") ? true : false;
    }
}