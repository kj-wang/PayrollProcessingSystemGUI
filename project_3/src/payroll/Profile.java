package payroll;

/** 
 * This class defines the profile which is unique to a given employee
 * Includes the employee's name, department, and date that they were hired
 *  
 * @author KJ Wang, Mehdi Kamal
 */
public class Profile {
    private String name; //employee's name in the form last name, first name
    private String department; //department code: CS, ECE, IT
    private Date dateHired;

    /**
     * This is the constructor the the profile object.
     * @param name the name of the employee
     * @param department the department of the employee
     * @param date the date hired of the employee
     */
    public Profile(String name, String department, String date){
        this.name = name;
        this.department = department;
        this.dateHired = new Date(date); 
    }

    /**
    * This method is a getter for the employee department
    * @return name of the department
    */
    public String getDepartment() {
        return department;
    }

    /**
    * This method is a getter for the employee date hired
    * @return date of when the employee was hired
    */
    public Date getDateHired() {
        return dateHired;
    }
    
    /**
    * This method creates a string for an employee's profile
    * The string includes name, department, and dateHired
    * @return String of instance variables combined to represent the profile
    */
    @Override
    public String toString() { 
        String textualRepresentation = (name + "::" + department + "::" + dateHired);
        return textualRepresentation;
    }
    
    /**
     * This method compares to Profiles to check if they are equal.
     * It compares their dateHired, name, and department
     * @return true if these variables are equal, else return false
     */
    @Override
    public boolean equals(Object obj) { 
        if (obj instanceof Profile) {
            Profile profile = (Profile) obj;
            if(profile.dateHired.compareTo(this.dateHired) == 0) {
                return profile.name.equals(this.name) && profile.department.equals(this.department);
            }       
        }
        return false;
    }        
}                                                                                                                                  