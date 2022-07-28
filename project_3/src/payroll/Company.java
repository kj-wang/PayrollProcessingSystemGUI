package payroll;

/**
 * This class defines the Company which is where all the employees work at.
 * 
 * Company is an array class that holds the employees.
 * There is multiple methods used to manipulate the company. 
 *
 * @author KJ Wang, Mehdi Kamal
 */
public class Company {
    private Employee[] emplist;
    private int numEmployee;
    private static final int INDEX_NOT_FOUND = -1;
    private static final int GROWTH_SIZE = 4;

    /**
     * This method is a constructor
     */
    public Company() { 
        emplist = new Employee[GROWTH_SIZE];
        numEmployee = 0;
    }

    /**
     * This method is a getter for employee list
     * @return the employee list
     */
    public Employee[] getEmpList(){
        return emplist;
    }

    /**
     * This method is a getter for number of employees
     * @return int an integer that represents amount of employees
     */
    public int getNumEmployee(){
        return numEmployee;
    }

    /**
    * This method acts as a getter for an employee in emplist
    * @param employee with profile to be found
    * @return employee in emplist with matching profile
    */
    public Employee getEmployee(Employee employee){
        int index = find(employee);
        return emplist[index];
    }

    /**
     * This method takes in an employee and find it's index in the employee list
     * @param   employee the employee that is being looked for
     * @return  index in which the employee is, however if it is not found then indexNotFound is returned
     */
    private int find(Employee employee) {
        for(int i = 0; i < numEmployee; i++){ 
            if(employee.equals(emplist[i])){
                return i;
            }
        }
        return INDEX_NOT_FOUND;
     }

    /**
     * This method helps the employee list capacity increase by 4 (GROWTH_SIZE)
     */
    private void grow() { 
        Employee[] emplist = new Employee[numEmployee + GROWTH_SIZE];
        for (int i = 0; i < numEmployee; i++) {
            emplist[i] = this.emplist[i];
        }
        this.emplist = emplist;
    }

    /**
     * This method adds an employee to the employee list
     * @param employee the employee that is being added
     * @return true if added, false if the employee is already in the list
     */
    public boolean add(Employee employee) { 
        
        if(find(employee) != INDEX_NOT_FOUND){ //check if the employee is already in the list
            return false;
        }
        if (numEmployee == emplist.length){ //check if employee list must be expanded
            grow();
        }
        emplist[numEmployee] = employee;
        numEmployee ++;
        return true; 
    }

    /**
     * This method removes an employee from the employee list
     * @param employee the employee being removed
     * @return true if removed, false if not found
     */
    public boolean remove(Employee employee) { 
         int removedIndex = find(employee);
        
         if (removedIndex == INDEX_NOT_FOUND){ 
             return false;
         }

         // remove the employee at the returned index
         int ptr1 = removedIndex;
         int ptr2 = ptr1 + 1;
 
         //shift the array left to fill empty index
         while(ptr2 < numEmployee) {
             emplist[ptr1] = emplist[ptr2];
             ptr1 ++;
             ptr2 ++;
         }
         numEmployee --;
         return true;
    }

    /**
     * This method is checks if you are able to set hours for a given employee
     * @param employee the employee that the hours are supposed to be set for
     * @return true if employee exists, false otherwise
     */
    public boolean setHours(Employee employee) {     
        if(find(employee) == INDEX_NOT_FOUND){
            return false;
        }
        else{
            int index = find(employee);
            emplist[index] = employee;
        }
        return true;
    } 

    /**
     * This method calculates the payments for every employee in employee list
     */
    public void processPayments() { 
        for(int i = 0; i < numEmployee; i++){
            emplist[i].calculatePayment();
        }
    } 

    /**
     * This method sorts the employee list by department
     * @return Array of employees grouped by department
     */
    private Employee[] sortByDepartment(){
        Employee[] sortedArr = emplist;
        for(int i = 0; i < numEmployee - 1; i++){
            int min = i;
            for(int j= i + 1; j < numEmployee; j++){  
                String depart1 = sortedArr[j].getProfile().getDepartment();
                String depart2 = sortedArr[min].getProfile().getDepartment();
                if(depart1.charAt(0) < depart2.charAt(0)){
                    min = j;
                }
            }           
            Employee temp = sortedArr[min];
            sortedArr[min] = sortedArr[i];
            sortedArr[i] = temp;
        }
        return sortedArr;
    }

     /**
     * This method sorts the employee list by the dateHired
     * @return Array of employees sorted by dateHired
    */
    private Employee[] sortByDate(){
        Employee[] sortedArr = emplist;
        for(int i = 0; i < numEmployee - 1; i++){
            int old = i;
            for(int j= i + 1; j < numEmployee; j++){ 
                Date datePtr = sortedArr[j].getProfile().getDateHired();
                Date dateOld = sortedArr[old].getProfile().getDateHired();
                if(datePtr.compareTo(dateOld) == -1){    
                    old = j;
                }
            }
            Employee temp = sortedArr[old];
            sortedArr[old] = sortedArr[i];
            sortedArr[i] = temp;
        }
        return sortedArr;
    }

    /**
     * This method prints an employee list that is sorted based on department utilizing selection sort
     * @return output string
     */
    public String printByDepartment() { 
        Employee[] depSorted = sortByDepartment();
        StringBuilder str = new StringBuilder();
        for(int i = 0; i < numEmployee; i++){ 
        	str.append(depSorted[i].toString() + "\n");
        }
        return str.toString();
    } 

     /**
     * This method prints the list of employees by dateHired utilizing selection sort
     * @return output string
     */
    public String printByDate() { 
        Employee[] dateSorted = sortByDate();
        StringBuilder str = new StringBuilder();
        for(int i = 0; i < numEmployee; i++){ 
            str.append(dateSorted[i].toString() + "\n");
        }
        return str.toString();
    }
    
    /**
     * This method prints the earning statements for all employees in no particular order
     * @return output string
     */
    public String exportDatabase() {
    	StringBuilder str = new StringBuilder();
    	for(int i = 0; i < numEmployee; i++){ 
    		str.append(this.emplist[i].toString() + "\n");
        }
    	return str.toString();
    } 
}