/**
 * Sample Skeleton for 'payrollFile.fxml' Controller Class
 */

package payroll;

/** 
* This class acts as a control for the GUI and interacts with the buttons and functions
*  
* @author KJ Wang, Mehdi Kamal
*/
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class payrollController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="name"
    private TextField name; // Value injected by FXMLLoader

    @FXML // fx:id="hoursWorked"
    private TextField hoursWorked; // Value injected by FXMLLoader

    @FXML // fx:id="rate"
    private TextField rate; // Value injected by FXMLLoader

    @FXML // fx:id="dateHired"
    private DatePicker dateHired; // Value injected by FXMLLoader

    @FXML // fx:id="annualSalary"
    private TextField annualSalary; // Value injected by FXMLLoader

    @FXML // fx:id="director"
    private RadioButton director; // Value injected by FXMLLoader

    @FXML // fx:id="role"
    private ToggleGroup role; // Value injected by FXMLLoader

    @FXML // fx:id="departmentHead"
    private RadioButton departmentHead; // Value injected by FXMLLoader

    @FXML // fx:id="manager"
    private RadioButton manager; // Value injected by FXMLLoader

    @FXML // fx:id="management"
    private RadioButton management; // Value injected by FXMLLoader

    @FXML // fx:id="employment"
    private ToggleGroup employment; // Value injected by FXMLLoader

    @FXML // fx:id="parttime"
    private RadioButton parttime; // Value injected by FXMLLoader

    @FXML // fx:id="fulltime"
    private RadioButton fulltime; // Value injected by FXMLLoader

    @FXML // fx:id="cs"
    private RadioButton cs; // Value injected by FXMLLoader

    @FXML // fx:id="department"
    private ToggleGroup department; // Value injected by FXMLLoader

    @FXML // fx:id="ece"
    private RadioButton ece; // Value injected by FXMLLoader

    @FXML // fx:id="it"
    private RadioButton it; // Value injected by FXMLLoader

    @FXML // fx:id="addButton"
    private Button addButton; // Value injected by FXMLLoader

    @FXML // fx:id="removeButton"
    private Button removeButton; // Value injected by FXMLLoader

    @FXML // fx:id="setHoursButton"
    private Button setHoursButton; // Value injected by FXMLLoader

    @FXML // fx:id="clear"
    private Button clear; // Value injected by FXMLLoader

    @FXML // fx:id="outputArea"
    private TextArea outputArea; // Value injected by FXMLLoader

    //Instance Variables
    Company company = new Company();
    private static int ZERO = 0;
    private static final int WORK_HOUR_LIMIT = 100;
    private static final int NAME_INDEX = 0;
    private static final int DEPARTMENT_INDEX = 1;
    private static final int DATE_INDEX =2;
    
    private String[] profileCreator(){
        try {
            String name = this.name.getText();
            //Empty name 
            if (name.equals("")) {
                outputArea.appendText("No name has been entered. Please enter a name.\n");
                return null;
            }
            
            // Department selection
            String department = "";
            if (cs.isSelected()) {
                department = "CS";
            }
            else if (ece.isSelected()) {
                department = "ECE";
                
            }
            else if (it.isSelected()) {
                department = "IT";
                
            } else { //Department empty Alert
                outputArea.appendText("No department has been selected. Please select a department.\n");
                return null;
            }
            
            //Date empty Alert
            if (dateHired.getValue() == null) {
                outputArea.appendText("No date has been entered. Please enter a date.\n");
                return null;
            } 
                
            String date = dateHired.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            Date dateObj = new Date(date);
            
            if (!dateObj.isValid()) {
                outputArea.appendText("Date is an invalid date.\n");
                return null;
            	
            }
                
            String temp[] = {name, department, date};
            return temp;
        } 
        catch (NullPointerException e){
            return null;
        }
    }

    @FXML
    /**
     * This method adds an employee to the company based on the inputed fields and click of the add button
     * @param event event when the add button is clicked
     */
    void add(ActionEvent event) {
            String[] profile = profileCreator();
            if (profile == null) {
                return;
            }
            String name = profile[NAME_INDEX];
            String department = profile[DEPARTMENT_INDEX];
            String date = profile[DATE_INDEX];
        
        
            //Parttime employee
            if (parttime.isSelected()) {                              
                if (rate.getText().equals("")) {
                    outputArea.appendText("No rate has been entered. Please enter a rate.\n");
                    return;
                }
                else if (!hoursWorked.getText().equals("")) {
                	outputArea.appendText("Hours must be set separately.\n");
            		hoursWorked.clear();
                }
                

                try {
                    String hourlyPay = this.rate.getText();
                    Parttime employee = new Parttime(name, department, date, hourlyPay);
                	double hourlyPayDouble = Double.parseDouble(hourlyPay);
                	// check hourly pay
                    if (hourlyPayDouble < ZERO) {
                        outputArea.appendText("Pay rate cannot be negative. Please enter a valid pay rate.\n");
                        return;
                    }
                    // add employee
                    else if (company.add(employee)){
                        outputArea.appendText("Employee added.\n");
                    }
                    else {
                        outputArea.appendText("Employee is already in the list.\n");
                    }            
                }
                catch (NumberFormatException d) {
                	outputArea.appendText("Hourly rate is not valid.\n");
                }
               
    		}


            // fulltime employee
    		else if (fulltime.isSelected()) {
                // no salary input
    			if (annualSalary.getText().equals("")) {
                    outputArea.appendText("No Annual Salary has been entered. Please enter an Annual Salary.\n");
                    return;
                } 

                try {
                    String salary = this.annualSalary.getText();
                    
                    Fulltime employee = new Fulltime(name, department, date, salary);
                        
                    int salaryInt = Integer.parseInt(salary);
                    
                    // checking salary
                    if (salaryInt < ZERO) {
                        outputArea.appendText("Salary cannot be negative. Please enter a valid salary.\n");
                        return;
                    }
                    
                    // adding employee
                    if (company.add(employee)){
                        outputArea.appendText("Employee added.\n");
                    }
                    else {
                        outputArea.appendText("Employee is already in the list.\n");                
                    }
                }
                catch (NumberFormatException d) {
                	outputArea.appendText("Salary is not valid.\n");
                }
    			
    		}
            // manager employee
    		else if (management.isSelected()) {
    			if (annualSalary.getText().equals("")) {
    				outputArea.appendText("No Annual Salary has been entered. Please enter an Annual Salary.\n");
                    return;
                } 
    			
    			// check for role
    			String role = "";
    			if (manager.isSelected()) {
        			role = "1";
        			
        		}
    			else if (departmentHead.isSelected()) {
        			role = "2";
        		}
        		
        		else if (director.isSelected()) {
        			role = "3";
        			
        		} else { //role empty error
        			outputArea.appendText("No managerial role has been selected. Please select a managerial role.\n");
                    return;
                }
    			
                try {
                    String salary = this.annualSalary.getText();
                    
                    Management employee = new Management(name, department, date, salary, role);
                        
                    int salaryInt = Integer.parseInt(salary);
                    
                    // check salary
                    if (salaryInt < ZERO) {
                        outputArea.appendText("Salary cannot be negative. Please enter a valid salary.\n");
                        return;
                    }
                    
                    // add employee
                    if (company.add(employee)){
                        outputArea.appendText("Employee added.\n");
                    }
                    else {
                        outputArea.appendText("Employee is already in the list.\n");                
                    }
                }
                catch (NumberFormatException d) {
                	outputArea.appendText("Salary is not valid.\n");
                }
    		}
    		else {
    			outputArea.appendText("No employment type has been selected. Please select an employment type.\n");
    		}
    }

    @FXML
    /**
     * This is a method used for calculating payments of the employees upon the selection of the calculate button
     * @param event event when the calculate function is selected
     */
    void calculate(ActionEvent event) {
        if (company.getNumEmployee() == 0) {
            	outputArea.appendText("No employees in the company.\n");
                return;
        }
    	company.processPayments(); 
    	outputArea.appendText("Payments have been calculated.\n");
    }

    @FXML
    /**
     * This method checks disables and enables buttons based on the employment type selected
     * @param event event when any of the employment type radio buttons are selected
     */
    void checkEmployment(ActionEvent event) {
        if (management.isSelected() || fulltime.isSelected()) {
            //annualSalary
            annualSalary.clear();
            annualSalary.setDisable(false);
            
            //rate
            rate.setDisable(true);
            rate.clear();
            
            //hoursWorked
            hoursWorked.clear();
            hoursWorked.setDisable(true);
            
            //roles
                if (management.isSelected()) { //clears everything for Management
                    director.setDisable(false);
                    departmentHead.setDisable(false);
                    manager.setDisable(false);
                }
                else { //clears everything for Fulltime
                    director.setDisable(true);
                    director.setSelected(false);
                    departmentHead.setDisable(true);
                    departmentHead.setSelected(false);
                    manager.setDisable(true);
                    manager.setSelected(false);
                }
                
            // set hours clears
            setHoursButton.setDisable(true);   
         }
         else if (parttime.isSelected()) {
                //annualSalary
                annualSalary.clear();
                annualSalary.setDisable(true);
                
                //rate
                rate.clear();
                rate.setDisable(false);
                
                // hoursWorked
                hoursWorked.clear();
                hoursWorked.setDisable(false);
                
                //role
                director.setDisable(true);
                director.setSelected(false);
                departmentHead.setDisable(true);
                departmentHead.setSelected(false);
                manager.setDisable(true);
                manager.setSelected(false);
                
                // set hours button
                setHoursButton.setDisable(false);   
        }
    }

    @FXML
    /**
     * This method clears all the fields in the GUI through the clear button
     * @param event event when clear button is clicked
     */
    void clear(ActionEvent event) {
        name.clear();
        //department
        cs.setSelected(false);
        ece.setSelected(false);
        it.setSelected(false);
        //date
        dateHired.setValue(null);
        //employment type
        management.setSelected(false);
        parttime.setSelected(false);
        fulltime.setSelected(false);
        //text fields
        annualSalary.clear();
        annualSalary.setDisable(true);
        rate.clear();
        rate.setDisable(true);
        hoursWorked.clear();
        hoursWorked.setDisable(true);
        //managerial types
        director.setSelected(false);
        director.setDisable(true);
        departmentHead.setSelected(false);
        departmentHead.setDisable(true);
        manager.setSelected(false);
        manager.setDisable(true);
    }

    @FXML
    /**
     * This method creates a new file to be exported based on the company database
     * @param event event when export button is clicked
     */
    void exportMethod(ActionEvent event) {
        if (company.getNumEmployee() == 0) {
            	outputArea.appendText("No employees in the company.\n");
                return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("TextFile", "txt"));
        Stage mainStage = new Stage();
		File targetFile = fileChooser.showSaveDialog(mainStage); //get the reference of the target file

		//In case file not found
		try (PrintWriter pw = new PrintWriter(targetFile);){
            pw.print(company.exportDatabase());
		}
 		catch (FileNotFoundException e){
			outputArea.appendText("Export file not valid.\n");
		}
		catch (NullPointerException d) {
		}
    }
    
    /**
     * This method analyzes a single string from the imported file to ensure the employee can be added
     * @param String String of a single employee's toString()  to be inputed into the GUI and then added to company
     */
    void importHelper(String input){
        StringTokenizer st = new StringTokenizer(input, ","); 
        try {
            String employmentType = st.nextToken().trim(); 
            String name = st.nextToken().trim(); 
            String department = st.nextToken().trim(); 
            String date = st.nextToken().trim(); 
            

            //DEPARTMENT
            if (department.equals("CS")) {
                cs.setSelected(true);
            }
            else if (department.equals("ECE")) {
                ece.setSelected(true);
            }
            else if (department.equals("IT")) {
                it.setSelected(true); 			
            }
            else {
                outputArea.appendText("Invalid Department.");
                return;
            }
            
            
            //FINAL TOKEN(s): RATE/ANNUAL SALARY/ MANAGERIAL TYPE
            if (employmentType.equals("P")) {   
                parttime.setSelected(true);
                //set Rate
                rate.setText(st.nextToken().trim());
            }
            
            else if (employmentType.equals("F")){
                fulltime.setSelected(true);
                // set annual salary
                annualSalary.setText(st.nextToken().trim());
            }
            
            else if (employmentType.equals("M")) {
                management.setSelected(true);
                //set annual salary & management type
                annualSalary.setText(st.nextToken().trim());
                String role = st.nextToken().trim();
                if (role.equals("1")) {
                    manager.setSelected(true);      			
                }
                else if (role.equals("2")) {
                    departmentHead.setSelected(true);
                }
                else if (role.equals("3")) {
                    director.setSelected(true);
                }
                else {
                    outputArea.appendText("Invalid role.");
                    return; 
                }
            }
            else {
                outputArea.appendText("Invalid Employment Type.");
                return;
            }
            
            this.name.setText(name);
            Date dateObj = new Date(date);
            
            //Date check
            if (!dateObj.isValid()) {
                outputArea.appendText("Invalid Date.");
                return;
            }
            
            int year = dateObj.getYear();
            int month = dateObj.getMonth();
            int day = dateObj.getDay();
            dateHired.setValue(LocalDate.of(year, month, day));
            
            addButton.fire();            
            clear.fire();
        }
        catch (Exception e){
            outputArea.appendText("Invalid import file.");
        }
    }
  
    
    @FXML
    /**
     * This method imports a file and adds the employees listed to company if valid
     * @param event event when import button is clicked
     */
    void importMethod(ActionEvent event) {                                  // check if blank line matters
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
            new ExtensionFilter("Text Files", "*.txt"),
            new ExtensionFilter("All Files", "*.*"));
        Stage mainStage = new Stage();

        try {
            File selectedFile = fileChooser.showOpenDialog(mainStage);
            Scanner scanner = new Scanner(selectedFile);
            while (scanner.hasNext()) {
                String input = scanner.nextLine();
                importHelper(input);
            }
            scanner.close();
           
        } 
        catch (FileNotFoundException e) {
            outputArea.appendText("File not found.");
        }
        catch (NullPointerException d) {
        }
     }

    @FXML
    /**
     * This method prints the toString() for every employee in company
     * @param event event when print button is clicked
     */
    void print(ActionEvent event) {
        if (company.getNumEmployee() == 0) {
            	outputArea.appendText("No employees in the company.\n");
                return;
        }
    	outputArea.appendText("--Printing earning statements for all employees-- \n");
    	outputArea.appendText(company.exportDatabase());
    }

    @FXML
    /**
     * This method prints the toString() for every employee in company in order of date hired
     * @param event event when byDate button is clicked
     */
    void printByDateHired(ActionEvent event) {
        if (company.getNumEmployee() == 0) {
            	outputArea.appendText("No employees in the company.\n");
                return;
        }
    	outputArea.appendText("--Printing earning statements by date hired- \n");
    	outputArea.appendText(company.printByDate());
    }

    
    @FXML
    /**
     * This method prints the toString() for every employee in company in order of department
     * @param event event when byDate button is clicked
     */
    void printByDepartment(ActionEvent event) {
        if (company.getNumEmployee() == 0) {
            	outputArea.appendText("No employees in the company.\n");
                return;
        }
    	outputArea.appendText("--Printing earning statements by department-- \n");
    	outputArea.appendText(company.printByDepartment());
    }

    @FXML
    /**
     * This method removes an employee from company based on their name, department and date hired
     * @param event event when remove button is clicked
     */
    void remove(ActionEvent event) {
    	String name = this.name.getText();
		//Empty name 
		if (name.equals("")) {
			outputArea.appendText("No name has been entered. Please enter a name.\n");
		     return;
		}
		
        // Department selection
		String department = "";
		if (cs.isSelected()) {
			department = "CS";
		}
		else if (ece.isSelected()) {
			department = "ECE";
			
		}
		else if (it.isSelected()) {
			department = "IT";
			
		} else { //Department empty Alert
            outputArea.appendText("No department has been selected. Please select a department.\n");
			return;
        }
	
        //Date empty Alert
        if (dateHired.getValue() == null) {
        	outputArea.appendText("No date has been entered. Please enter a date.\n");
            return;
        } 
        
        String date = dateHired.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        Date dateObj = new Date(date);

        if (!dateObj.isValid()) {
                outputArea.appendText("Date is an invalid date.\n");
                return;   	
        }

        	//rate is empty check
        	if (rate.getText().equals("") == false) {
                outputArea.appendText("Rate is not necessary.\n");
                rate.clear();
            } 
            
            //Salary is empty check
			if (annualSalary.getText().equals("") == false) {
                outputArea.appendText("Annual Salary not necessary.\n");
                annualSalary.clear();
            } 
            
            //Hours is empty check
            if (hoursWorked.getText().equals("") == false) {
                outputArea.appendText("Hours worked is not necessary.\n");
                hoursWorked.clear();
            } 
            
            //Employment type is Empty check
			if (parttime.isSelected() || fulltime.isSelected() || management.isSelected()) {
    			outputArea.appendText("Employment type is not necessary.\n");
                parttime.setSelected(false);
                fulltime.setSelected(false);
                management.setSelected(false);
    		}

            //Managerial Role is empty check
			if (manager.isSelected() || departmentHead.isSelected() || director.isSelected()) {
    			outputArea.appendText("Managerial role is not necessary.\n");
                manager.setSelected(false);
                departmentHead.setSelected(false);
                director.setSelected(false);
    		}
            
            // remove employee
            Employee employee = new Employee(name, department, date);
            if (company.remove(employee)){
            	outputArea.appendText("Employee removed.\n");
            }
            else {
            	outputArea.appendText("Employee does not exist.\n");
            }            

            // return to default (disable all unnecessary fields)
            annualSalary.setDisable(true);
            rate.setDisable(true);
            hoursWorked.setDisable(true);
            director.setDisable(true);
            departmentHead.setDisable(true);
            manager.setDisable(true);
            setHoursButton.setDisable(true);
    }

    @FXML
    /**
     * This method sets the hours for a part time employee
     * @param event event when setHours button is clicked
     */
    void sethours(ActionEvent event) {
    	//CREATION OF TEMP EMPLOYEE 
        String[] profile = profileCreator();
        if (profile == null) {
                return;
        }
    	String name = profile[NAME_INDEX];
        String department = profile[DEPARTMENT_INDEX];
    	String date = profile[DATE_INDEX];

            //Parttime employee
            if (parttime.isSelected()) {        
            	try {
                    String hours = this.hoursWorked.getText();
                    int hoursInt = Integer.parseInt(hours);
                    
                    //Hours validity checks
                    if (hoursWorked.getText().equals("")) { //blank
                        outputArea.appendText("No hours has been entered. Please enter hours.\n");
                        return;
                    }
                    else if (!rate.getText().equals("")) {
            			outputArea.appendText("Rate is not necessary.\n");
            			rate.clear();
            		} 
                    else if (hoursInt < ZERO) { //negative
                        outputArea.appendText("Working hours cannot be negative.\n");
                        return;
                    } 
                    else if (hoursInt > WORK_HOUR_LIMIT) { //too big
                        outputArea.appendText("Invalid Hours: over 100.\n");
                        return;
                    }
                    
                    //temp employee 
                    Employee employee = new Employee(name, department, date);
        
                    // use a employee getter in company and use that to set the part time instance variable
                    Parttime partEmployee = (Parttime) company.getEmployee(employee);
                    
                    // SetHours if employee exists in company
                    if (partEmployee instanceof Parttime) {
                        partEmployee.setWorkingHours(hoursInt);
                        if (company.setHours(partEmployee)) {
                            outputArea.appendText("Working hours set.\n");    
                        }   
                        else {
                            outputArea.appendText("Employee does not exist.\n");
                        }
                    }
                }
                catch (NumberFormatException d) {
                	outputArea.appendText("Invalid hours.\n");
                }
            }
            else {
            	outputArea.appendText("Only applicable for parttime employees. \n");
            }
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert name != null : "fx:id=\"name\" was not injected: check your FXML file 'payrollFile.fxml'.";
        assert hoursWorked != null : "fx:id=\"hoursWorked\" was not injected: check your FXML file 'payrollFile.fxml'.";
        assert rate != null : "fx:id=\"rate\" was not injected: check your FXML file 'payrollFile.fxml'.";
        assert dateHired != null : "fx:id=\"dateHired\" was not injected: check your FXML file 'payrollFile.fxml'.";
        assert annualSalary != null : "fx:id=\"annualSalary\" was not injected: check your FXML file 'payrollFile.fxml'.";
        assert director != null : "fx:id=\"director\" was not injected: check your FXML file 'payrollFile.fxml'.";
        assert role != null : "fx:id=\"role\" was not injected: check your FXML file 'payrollFile.fxml'.";
        assert departmentHead != null : "fx:id=\"departmentHead\" was not injected: check your FXML file 'payrollFile.fxml'.";
        assert manager != null : "fx:id=\"manager\" was not injected: check your FXML file 'payrollFile.fxml'.";
        assert management != null : "fx:id=\"management\" was not injected: check your FXML file 'payrollFile.fxml'.";
        assert employment != null : "fx:id=\"employment\" was not injected: check your FXML file 'payrollFile.fxml'.";
        assert parttime != null : "fx:id=\"parttime\" was not injected: check your FXML file 'payrollFile.fxml'.";
        assert fulltime != null : "fx:id=\"fulltime\" was not injected: check your FXML file 'payrollFile.fxml'.";
        assert cs != null : "fx:id=\"cs\" was not injected: check your FXML file 'payrollFile.fxml'.";
        assert department != null : "fx:id=\"department\" was not injected: check your FXML file 'payrollFile.fxml'.";
        assert ece != null : "fx:id=\"ece\" was not injected: check your FXML file 'payrollFile.fxml'.";
        assert it != null : "fx:id=\"it\" was not injected: check your FXML file 'payrollFile.fxml'.";
        assert addButton != null : "fx:id=\"addButton\" was not injected: check your FXML file 'payrollFile.fxml'.";
        assert removeButton != null : "fx:id=\"removeButton\" was not injected: check your FXML file 'payrollFile.fxml'.";
        assert setHoursButton != null : "fx:id=\"setHoursButton\" was not injected: check your FXML file 'payrollFile.fxml'.";
        assert clear != null : "fx:id=\"clear\" was not injected: check your FXML file 'payrollFile.fxml'.";
        assert outputArea != null : "fx:id=\"outputArea\" was not injected: check your FXML file 'payrollFile.fxml'.";
    }
}
