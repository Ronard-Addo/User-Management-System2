package userManagementSystem;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class User {
    protected int id;
	protected String firstName;
    protected String lastName;
    protected int age;
    protected String gender;
    protected String address;
    protected String city;
    protected String province;
    protected String country;
    protected String status;
    protected float balance;
    protected float totalDepositAmount = 10000;
    protected int numDeposits = 20;
    protected int totalEmployees = 100;
    protected boolean existing = true;
    protected boolean reqNameChange = false;

    
    // Database connection details
    private static final String url = "jdbc:mysql://localhost:3306/ums";
    private static final String username = "root";
    private static final String password = "";
    
    // Scanner declared as a class variable
    private static final Scanner scanner = new Scanner(System.in);
    
    protected boolean prompt(boolean valid) {
	    
    	//Scanner scanner = new Scanner(System.in); 
	        while (true) {
	            System.out.println("\nWould you like to enter a different input?");
	            System.out.println("Enter 1 to try a different input or 0 to return to the main menu: ");
	            
	            if (!scanner.hasNextInt()) { // Validate input type
	                System.out.println("\nInvalid input! Please enter 1 or 0.");
	                scanner.next(); // Consume invalid input
	                continue;
	            }
	            
	            int choice = scanner.nextInt();
	            scanner.nextLine(); // Clear buffer
	            
	            if (choice == 1) {
	                return true;  // Restart process
	            } else if (choice == 0) {
	                return false; // Exit and return to main menu
	            } else {
	                System.out.println("\nInvalid input! Enter 1 or 0.");
	            }
	        }
	        
    }
    
    protected void userOptions() {
    	
    	boolean valid ;
		boolean restart;
		
		do {
			valid = true;
			restart = false;
			
	    	System.out.println("\nChoose one of the following options:\n1.Change firstname.\n2. Change last name\n3. Change age");
	    	int choice = scanner.nextInt();
	    	scanner.nextLine();
	    	
	    	switch(choice) {
		    	case 0:
		    		return;
		    	case 1:
		    		setFirstName();
		    		break;
		    	case 2:
		    		setLastName();
		    		break;
		    	case 3:
		    		setAge();
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
    
    
    
    // Getters
    public String getValue(String field_name, int id) {
        String value = null; // Declare firstName properly

        try {
            // Load MySQL driver (Not needed in modern Java, but fine)
            //Class.forName("com.mysql.cj.jdbc.Driver");

        	String query = "SELECT " + field_name + " FROM users WHERE id = ?";
        	
        	// Establish connection
            try (Connection conn = DriverManager.getConnection(url, username, password);
                 PreparedStatement pstmt = conn.prepareStatement(query)) {

                pstmt.setInt(1, id); // Set ID parameter

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        value = rs.getString(field_name);
                    }
                }
            } // Try-with-resources ensures Connection and PreparedStatement are closed

        } catch (Exception e) {
            e.printStackTrace(); // Print any SQL or connection errors
        }

        return value; // Return the retrieved value
    }

    

    public boolean authenticateUser(String tableName) {
        //Scanner scanner = new Scanner(System.in);

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            System.out.print("Enter your username: ");
            String inputUsername = scanner.nextLine();

            // Query to check if the user exists
            String checkUserQuery = "SELECT * FROM " + tableName + " WHERE username = ?";
            
            try (PreparedStatement pstmt = conn.prepareStatement(checkUserQuery)) {
                pstmt.setString(1, inputUsername);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (!rs.next()) {
                        System.out.println("User does not exist.");
                        return false;
                    }

                    // If user exists, prompt for password
                    System.out.print("Enter your password: ");
                    String inputPassword = scanner.nextLine();

                    // Check if password matches
                    if (!rs.getString("password").equals(inputPassword)) {
                        System.out.println("Incorrect password.");
                        return false;
                    }

                    System.out.println("Login successful!\n");

                    // **Field Mapping for Dynamic Initialization**
                    HashMap<String, Object> fieldMap = new HashMap<>();
                    fieldMap.put("id", null);
                    fieldMap.put("first_name", null);
                    fieldMap.put("last_name", null);
                    fieldMap.put("gender", null);
                    fieldMap.put("age", null);
                    fieldMap.put("address", null);

                    // Populate fields dynamically from the database
                    for (Map.Entry<String, Object> entry : fieldMap.entrySet()) {
                        String fieldName = entry.getKey();
                        Object value = rs.getObject(fieldName);
                        fieldMap.put(fieldName, value);
                    }

                    // Assign values to class variables
                    this.id = (Integer) fieldMap.get("id");
                    this.firstName = (String) fieldMap.get("first_name");
                    this.lastName = (String) fieldMap.get("last_name");
                    this.gender = (String) fieldMap.get("gender");              
                    this.age = (fieldMap.get("age") != null) ? (int) fieldMap.get("age") : 0;
                    this.address = (String) fieldMap.get("address");

                    System.out.println("User data initialized successfully.");
                    System.out.println(this.id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } /*finally {
            scanner.close();
        }*/
        return true;
    }
    
    protected String getFirstName() {
    	return firstName;
    }
    
    protected String getLastName() {
    	return lastName;
    }
    
    protected String getGender() {
    	return gender;
    }
    
    protected int getAge() {
    	return age;
    }
    
    protected String getAddress() {
    	return address;
    }
    
    protected String getCity() {
    	return address;
    }
    
    protected String getProvince() {
    	return address;
    }
    
    protected String getCountry() {
    	return address;
    }
    
    protected void displayUserInfo() {
        getFirstName();
        System.out.println(
            "\nFirst Name: " + getFirstName() +
            "\nAge: " + getAge() +
            "\nGender: " + getGender() +
            "\nCity: " + getCity() +
            "\nProvince: " + getProvince() +
            "\nCountry: " + getCountry()
        );
    }


    // Setters
    public void insertValue(int id, String field_name, Object value) {
    	try {
            // Load MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");

         // SQL query to insert name into users table
	        String query = existing ? "UPDATE users SET " + field_name + " = ? WHERE id = ?" : "INSERT INTO users (" + field_name + ") VALUES (?)";
            // Establish connection
            Connection conn = DriverManager.getConnection(url, username, password);

            // Create PreparedStatement
            PreparedStatement pstmt = conn.prepareStatement(query);
            
            
            // Determine the type of value and assign methodName
            if (value instanceof String) {
                pstmt.setString(1, (String) value);
            } else if (value instanceof Integer) {
                pstmt.setInt(1, (Integer) value);
            } else {
                throw new IllegalArgumentException("Unsupported data type: " + value.getClass().getSimpleName());
            }

            // If updating, set the WHERE condition (id)
            if (existing) {
                pstmt.setInt(2, id);
            }

            // Execute query
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println(existing ? "Name updated successfully." : "Name inserted successfully.");
            }

            // Close resources
            pstmt.close();
            conn.close();
            
        } catch (Exception e) {
            e.printStackTrace(); // Print any SQL or connection errors
        }
    }
    
    
    public void setFirstName() {
    	//Scanner scanner = new Scanner(System.in);
    		boolean valid;
    		boolean restart;
    		
    		do {
    			valid = true;
        		restart = false;
	    		
    			System.out.print("Enter your first name: ");
	    		this.firstName = scanner.nextLine();
	    		
	    		int count = this.firstName.trim().isEmpty() ? 0 : this.firstName.trim().split("\\s+").length;
	    		
	    		// Validation: Ensure firstName contains only letters
		        if (count == 1) {
		    		if (!this.firstName.matches("^[a-zA-Z]+$")) {
			            System.out.println("Invalid first name! First name must not contain spaces, numbers, or special characters.");
			            valid = false;
			        }
		        } else {
		        	System.out.println("\nInvalid input. First name must only be 1 word!");
		        	valid = false;
		        }
		        
		        if(!valid) {
		        	restart = prompt(valid);
		        	if (!restart) return;
		    	}
		        
    		} while (restart);
    		
    		//if (!restart) return;
    		
    		
		        // Convert first letter to uppercase and the rest to lowercase
		        this.firstName = this.firstName.substring(0, 1).toUpperCase() + this.firstName.substring(1).toLowerCase();
		        
		    	String field_name = "first_name";	// Only change field name here
		        		        
		       insertValue(this.id, field_name, this.firstName);
    		
    	//}
    }
    
    
    public void setLastName() {
    	//try (Scanner scanner = new Scanner(System.in)) {
    		
    		boolean valid;
    		boolean restart;
    		
    		do {
    			valid = true;
        		restart = false;
        		
        		System.out.print("Enter your last name: ");
        		this.lastName = scanner.nextLine();
	    		
    			int count = this.lastName.trim().isEmpty() ? 0 : this.lastName.trim().split("\\s+").length;
	    		
	    		// Validation: Ensure firstName contains only letters
		        if (count == 1) {
		        	if (!this.lastName.matches("^[a-zA-Z]+$")) { // Check if input contains only letter
		    			System.out.println("Invalid last name! Last name must not contain spaces, numbers, or special characters.");
			            return;
			        }
		        } else {
		        	System.out.println("\nInvalid input. Last name must only be 1 word!");
		        	valid = false;
		        }
		        
		        if(!valid) {
		        	restart = prompt(valid);
		        	if (!restart) return;
		    	}
	        
    		} while (restart);
    		
	        
	        // Convert first letter to uppercase and the rest to lowercase
	        this.lastName = this.lastName.substring(0, 1).toUpperCase() + this.lastName.substring(1).toLowerCase();
	        
	    	String field_name = "last_name";	// Only change field name here
	        
	        insertValue(this.id, field_name, this.lastName);
	       
	       scanner.close();
    	//}
    }

    protected void setAge() {
    	//try (Scanner scanner = new Scanner(System.in)) {	// Try-with-resources to prevent memory leak
    	
    		boolean valid ;
    		boolean restart;
    		int intAge = 0;
    		
    		do {
    			restart = false;
    			valid = true;
    			
	    		System.out.print("Enter age: ");
		    	String age = scanner.nextLine();
		    	
		    	int count = age.trim().isEmpty() ? 0 : age.trim().split("\\s+").length;
		    	
		    	
		    	if (count == 1) {
		    		try {
			    		intAge = Integer.parseInt(age); // Convert String to int
			        } catch (NumberFormatException e) {
			            System.out.println("\nInvalid input. Age must be a number");
			            valid = false;
			        }
		        } else {
		        	System.out.println("\nInvalid input. Age must be only a single number");
		        	valid = false;
		        }
		    	
		    	
		    	if(!valid) {
		    		System.out.println("\nWould you like to enter a different input?");
		    	}
		    		
		    	if(!valid) {
		        	restart = prompt(valid);
		        	if (!restart) return;
		    	}
		    	
	    	} while (restart);
	    	
	    	this.age = intAge;
	    	
	        String field_name = "age";	// Only change field name here
	        
	        insertValue(this.id, field_name, this.age);
	       
	       scanner.close();
	       
    	//}
    }

    protected void setGender() {
    	//try (Scanner scanner = new Scanner(System.in)) {	// Try-with-resources to prevent memory leak
	    	
    	boolean valid ;
		boolean restart;
		
		do {
			
			restart = false;
			valid = true;
			
    		System.out.println("\nChoose a gender.\n1. Male\n2. Female\n3. Prefer not to Say\n");
	    	int choice = scanner.nextInt();
	    	scanner.nextLine(); // Consume the remaining newline character
	    	
	    	
	    	
	    	switch(choice) {
		    	case 1:
		    		this.gender = "Male";
		    		break;
		    	case 2:
		    		this.gender = "Female";
		    		break;
		    	case 3:
		    		this.gender = "PNS";
		    		break;
		    	default:
		    		System.out.println("Invalid");
		    		valid = false;
	    	}
	    	
	    	if(!valid) {
	        	restart = prompt(valid);
	        	if (!restart) return;
	    	}
	    	
	    } while(restart);
	    	
	    	String field_name = "gender";	// Only change field name here
	        
	        insertValue(this.id, field_name, this.gender);
	       
	       scanner.close();
    	//}
    }
    

    protected void setAddress() {
    	//try (Scanner scanner = new Scanner(System.in)) {	// Try-with-resources to prevent memory leak
	    	
    		boolean valid;
    		boolean restart;
    		
    		do {
    			restart = false;
    			valid = true;
    			
	    		System.out.println("\nEnter an address: ");
		    	this.address = scanner.nextLine();
		    	
		    	
		    	int count = this.address.trim().isEmpty() ? 0 : this.address.trim().split("\\s+").length;
		    	
		    	String[] substrings = this.address.trim().split("\\s+"); // Split by spaces
		    	
		    	
		    	if (count == 3) {
			    	for (int i = 1; i < 3; i++) {
			    		if (!substrings[i].matches("^[a-zA-Z]+$")) {
			    			System.out.println("\nInvalid input! 2nd and 3rd words must contain only letters.");
			    			valid = false;
			    		}
			    	}
		    	} else {
		    		System.out.println("\nInvalid input. Address must be 3 words long!");
		    		valid = false;
		    	}
		    	
		    	
		    	try {
		    		Integer.parseInt(substrings[0]); // Convert String to int
		        } catch (NumberFormatException e) {
		            System.out.println("\nInvalid input. First word must be a number");
		            valid = false;
		        }
		    	
		    	
		    		
		    	if(!valid) {
		        	restart = prompt(valid);
		        	if (!restart) return;
		    	}
		    	
    		} while (restart);
	    	
	    	String field_name = "address";	// Only change field name here
	        
	        insertValue(this.id, field_name, this.address);
    	//}
    }
    
    
    protected void setCity() {
    	//try (Scanner scanner = new Scanner(System.in)) {	// Try-with-resources to prevent memory leak
        	
    		boolean valid;
    		boolean restart;
    		
    		do {
    			
	    		restart = false;
	    		valid = true;
	    		
    			System.out.print("Enter your city: ");
		    	this.city = scanner.nextLine();
	    		
		    	int count = this.city.trim().isEmpty() ? 0 : this.city.trim().split("\\s+").length;
		    	
		    	if (count == 3) {
			    	if (!this.city.matches("^[a-zA-Z]+$")) { // Check if input contains only letter
		    			System.out.println("\nInvalid city name! City name must not contain spaces, numbers, or special characters.");
			            valid = false;
			        }
		    	} else {
		    		System.out.println("\nInvalid input! City must only be one word");
		    		valid = false;
		    	}
		        
		    	if(!valid) {
		        	restart = prompt(valid);
		        	if (!restart) return;
		    	}
		    	
    		} while (restart);	
		    	
		    	// Convert first letter to uppercase and the rest to lowercase
	    		this.city = this.city.substring(0, 1).toUpperCase() + this.city.substring(1).toLowerCase();
		        	
		        String field_name = "city";	// Only change field name here
		        
		        insertValue(this.id, field_name, this.city);
		           
    	//}
    }

    protected void setProvince() {
    	
    	//try (Scanner scanner = new Scanner(System.in)) {
    		boolean valid;
    		boolean restart;
    		
    		do {
	    		restart = false;
	    		valid = true;
	    		
    			System.out.print("Enter your province: ");
	    		this.province = scanner.nextLine();
	    		
	    		int count = this.province.trim().isEmpty() ? 0 : this.province.trim().split("\\s+").length;
	    		
	    		// Validation: Ensure firstName contains only letters
		        if (count == 1) {
		    		if (!this.province.matches("^[a-zA-Z]+$")) {
			            System.out.println("Invalid province name! Province name must not contain spaces, numbers, or special characters.");
			            valid = false;
			        }
		        } else {
		        	System.out.println("\nInvalid input! Province name must only be 1 word.");
		        	valid = false;
		        }
		        
		        if(!valid) {
		        	restart = prompt(valid);
		        	if (!restart) return;
		    	}
		    	
		        
    		} while (restart);
    		
    		// Convert first letter to uppercase and the rest to lowercase
    		this.province = this.province.substring(0, 1).toUpperCase() + this.province.substring(1).toLowerCase();
	        	
	        String field_name = "province";	// Only change field name here
	        
	        insertValue(this.id, field_name, this.province);
    		
    	//}
    }

    protected void setCountry() {
    	//try (Scanner scanner = new Scanner(System.in)) {
    		boolean valid;
    		boolean restart;
    		
    		do {
	    		restart = false;
	    		valid = true;
	    		
    			System.out.print("Enter your city: ");
	    		this.city = scanner.nextLine();
	    		
	    		int count = this.city.trim().isEmpty() ? 0 : this.city.trim().split("\\s+").length;
	    		
	    		// Validation: Ensure firstName contains only letters
		        if (count == 1) {
		    		if (!this.city.matches("^[a-zA-Z]+$")) {
			            System.out.println("Invalid input! City name must not contain spaces, numbers, or special characters.");
			            valid = false;
			        }
		        } else {
		        	System.out.println("\nInvalid input! City name must only be 1 word.");
		        	valid = false;
		        }
		        
		        if(!valid) {
		        	restart = prompt(valid);
		        	if (!restart) return;
		    	}
		    	
		        
    		} while (restart);
    		
    		// Convert first letter to uppercase and the rest to lowercase
    		this.city = this.province.substring(0, 1).toUpperCase() + this.province.substring(1).toLowerCase();
	        	
	        String field_name = "province";	// Only change field name here
	        
	        insertValue(this.id, field_name, this.province);
    		
    	//}
    }

    protected void reqNameChange() {
        reqNameChange = true;
    }
}