package payroll;
import java.text.DecimalFormat;

/**
 * This class extends the Employee class and includes specific data and operations to a full-time employee.
 * 
 * @author KJ Wang, Mehdi Kamal
 */
public class Fulltime extends Employee {
    private static final double PAYMENT_PERIOD = 26.0;
    double salary = 0.0;  
    

    /**
     * This method is a constructor for employee
     * @param name the name of the employee
     * @param department the department of the employee
     * @param date the date hired of the employee
     * @param salary the salary of the employee
     */
    public Fulltime(String name, String department, String date, String salary){
        super(name, department, date);
        this.salary = Double.parseDouble(salary);
    }
    
    
    
    /**
    * This is a getter for the variable payment
    * @return salary
    */
    public double getSalary() {
    	return salary;
    }
    

    /**
     * This method returns a textual representation of a fulltime employees profile, payment, and annual salary
     * @return String a textual representation
     */
    @Override
    public String toString() { 
        DecimalFormat dec = new DecimalFormat("$###,###,###,##0.00");
        String textualRepresentation = (super.toString() + "::Payment " + dec.format(getPayment()) + "::FULL TIME::Annual Salary " + dec.format(salary));
        return textualRepresentation;
    }

    /**
     * This method checks if a fulltime employee is equal to another employee based on their profiles
     * @param obj an employee
     * @return boolean true if they are equal based on profile
     */
    @Override
    public boolean equals(Object obj) {  
        if (obj instanceof Fulltime) {
            Fulltime fulltime = (Fulltime) obj;
            return super.equals(fulltime);
        }
        return false; 
     }

     /**
      * This method calculates the payment for a fulltime employee based on salary per period, one period is 2 weeks
      */
    @Override
    public void calculatePayment() { 
        setPayment(this.salary / PAYMENT_PERIOD); 
    }
}