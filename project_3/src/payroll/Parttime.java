package payroll;
import java.text.DecimalFormat;

/**
 * This class extends the Employee class and includes specific data and operations to a part-time employee.
 *  
 * @author KJ Wang, Mehdi Kamal
 */
public class Parttime extends Employee {
    int workingHours = 0;
    double hourlyPay = 0;
    private static final double OVERPAY_RATE = 1.5;
    
    /**
     * This method is a constructor for parttime
     * @param name name of the employee
     * @param department department they work in
     * @param date date of employment
     * @param hourlyPay hourlypay
     */
    public Parttime(String name, String department, String date, String hourlyPay){
        super(name, department, date);
        this.hourlyPay = Double.parseDouble(hourlyPay);
    }
     
     
    /**
     * This method is a setter for workingHours
     * @param workingHours the working hours of the employee
     */
    public void setWorkingHours(int workingHours) {
        this.workingHours = workingHours;
    }
    

    /**
     * This method is a setter for the hourly pay of the employee
     * @param hourlyPay the hourly wage of the employee
     */
    public void setHourlyPay(double hourlyPay) {
        this.hourlyPay = hourlyPay;
    }

    
    /**
     * This method creates a textual representation of the employee based on instance variables and profile
     * @return String textual representation of the employee
     */
    @Override
    public String toString() {   
        DecimalFormat dec = new DecimalFormat("$###,###,###,##0.00");
        String textualRepresentation = (super.toString() + "::Payment " + dec.format(getPayment()) + "::PART TIME::Hourly Rate " 
                                        + dec.format(hourlyPay) + "::Hours worked this period: " + workingHours);
        return textualRepresentation;
     } 
     

     /**
      * This method compare the profile, workingHours, hourlyPay, and paymentAmt of two employees
      * @param obj of type employee extended to parttime
      * @return true if they are equal, false if they arent
      */
    @Override
    public boolean equals(Object obj) {    
        if (obj instanceof Parttime) {
            Parttime parttime = (Parttime) obj;
            //compare profile, workingHours, hourlyPay, and paymentAmt
            return super.equals(parttime); 
        }   
        return false; 
    }

    /**
     * This method calculates the paymentAmt for a parttime employee 
     */
    @Override
    public void calculatePayment() { 
        if (this.workingHours > 80) {
            double excessHours = workingHours - 80;
            double excessWage = excessHours * hourlyPay * OVERPAY_RATE;
            setPayment((hourlyPay * 80) + excessWage);  
        }
        else {
            setPayment(hourlyPay * workingHours);   
        }
    }
}