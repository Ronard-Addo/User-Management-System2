package userManagementSystem;

//import java.sql.*;
import java.util.Scanner;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        
    	User user = new User();
        Employee employee = new Employee();
        Admin admin = new Admin();
        
     // Created a HashMap to store role mappings
        HashMap<String, User> roleMap = new HashMap<>();
        
        roleMap.put("users", user);
        roleMap.put("employee", employee);
        roleMap.put("admin", admin);
        
        int choice;
        boolean valid = true;
        String role = "";
        boolean restart;
        
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Hi, Welcome to my User Management System!\n");
        
        System.out.println("Select a role:");

        do {
            System.out.println("1. User\n2. Employee\n3. Admin\n");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 0:
                    System.exit(0);
                    break;
                case 1:
                    role = "users";
                    break;
                case 2:
                    role = "employee";
                    break;
                case 3:
                    role = "admin";
                    break;
                default:
                    valid = false;
                    break;
            }

            if (!valid) {
                System.out.println("Invalid selection. Please select one of the following roles:");
            }
        } while (!valid);

        System.out.println("\nYou selected the " + role + " group");
        
        do {
        	valid = true;
        	restart = false;
        	
        	valid = user.authenticateUser(role);
        	
        	if(!valid) {
	        	restart = user.prompt(valid);
	        	if (!restart) return;
	    	}

        } while (restart);
        
        // Retrieving the object based on the selected role
        User selectedRole = roleMap.get(role);

        if (selectedRole != null) {
        	selectedRole.userOptions();
            }
            
        }  
    	
    	//user.setFirstName();
        
        //user.setLastName();
        
        //user.setAge();
    	
    	//user.setGender();
    	
    	//user.setAddress();
        
    	//user.setCity();
    	
    	//user.setProvince();
    	
    	//user.getFirstName();
    	
    	//scanner.close();
    	
} 