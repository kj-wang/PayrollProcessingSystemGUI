package payroll;
import java.text.DecimalFormat;

/**
 * This class extends the Fulltime class and includes specific data and operations to a full-time employee with a management role.
 * 
 * @author KJ Wang, Mehdi Kamal
 */
public class Management extends Fulltime {
    private static final int NO_ROLE = 0;
    private static final int MANAGER = 1;
    private static final int DEPARTMENT_HEAD = 2;
    private static final int DIRECTOR = 3;
    private static final double PAYMENT_PERIOD = 26.0;
    private static final double MANAGER_COMPENSATION = 5000 / PAYMENT_PERIOD;
    private static final double DEPARTMENT_HEAD_COMPENSATION = 9500 / PAYMENT_PERIOD;
    private static final double DIRECTOR_COMPENSATION = 12000 / PAYMENT_PERIOD;
    int role = NO_ROLE;

    /**
     * This method is a constructor for the Management class
     * @param name name of the employee
     * @param department department of the employee
     * @param date date hired of the employee
     * @param salary the yearly salary of the employee
     * @param role the role of the employee
     */
    public Management(String name, String department, String date, String salary, String role){
        super(name, department, date, salary);
        this.role = Integer.parseInt(role);
    }
    
    /**
     * This method is a setter for the management role of the employee
     * @param role the type of manager role
     */
    public void setRole(int role) {
        this.role = role;
    }

    /**
     * This method is to check the type of the managment role and if it is valid
     * @param role the role of the management employee
     * @return return true if valid, false otherwise
     */
    public boolean checkType(String role) {
        int managementType = Integer.parseInt(role);
        if (managementType == MANAGER || managementType == DEPARTMENT_HEAD || managementType == DIRECTOR) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * This method creates a textual representation of a given fulltime employee with a manager role.
     * The textual representation includes all that of a fulltime including additional compensation.
     * @return string textual representation
    */
    @Override
    public String toString() {
        DecimalFormat dec = new DecimalFormat("$###,###,###,##0.00");
        if (role == MANAGER) {
            return (super.toString() + "::Manager Compensation " + dec.format(MANAGER_COMPENSATION));
        }
        else if (role == DEPARTMENT_HEAD) {
            return (super.toString() + "::Department Head Compensation " + dec.format(DEPARTMENT_HEAD_COMPENSATION));
        }
        else if (role == DIRECTOR) {
            return (super.toString() + "::Director Compensation " + dec.format(DIRECTOR_COMPENSATION));
        }
        else {
            return ("invalid management code");
        }
    }

    /**
     * This method checks if two employees are equal based on their profile
     * @param obj an employee to be compared to
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {  
        if (obj instanceof Management) { 
            Management management = (Management) obj;
            if (super.equals(management)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method calculates the payment for a management employee based on their management type and salary
     */
    @Override
    public void calculatePayment() { 
        if (role == MANAGER) {
            setPayment((getSalary() / PAYMENT_PERIOD) + MANAGER_COMPENSATION);
        }
        else if (role == DEPARTMENT_HEAD) {
            setPayment((getSalary() / PAYMENT_PERIOD) + DEPARTMENT_HEAD_COMPENSATION);
        }
        else if (role == DIRECTOR) {
            setPayment((getSalary() / PAYMENT_PERIOD) + DIRECTOR_COMPENSATION);
        }
    }
}