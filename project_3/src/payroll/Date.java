package payroll;
import java.util.Calendar;
import java.util.StringTokenizer;

/**
 * This class defines the Date object.
 * The Date object is defaulted to the current date unless specified.
 * 
 * @author KJ Wang, Mehdi Kamal
 */
public class Date implements Comparable<Date> {
    private int year;
    private int month;
    private int day;
    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUATERCENTENNIAL = 400;
    private static final int JAN = 1;
    private static final int FEB = 2;
    private static final int MAR = 3;
    private static final int APR = 4;
    private static final int MAY = 5;
    private static final int JUN = 6;
    private static final int JUL = 7;
    private static final int AUG = 8;
    private static final int SEP = 9;
    private static final int OCT = 10;
    private static final int NOV = 11;
    private static final int DEC = 12;
    private static final int MINIMUM_YEAR = 1900;
    private static final int THIRTY_DAY_MONTH = 30;
    private static final int TWENTY_NINE_DAY_MONTH = 29;
    private static final int TWENTY_EIGHT_DAY_MONTH = 28;
    private static final int THIRTY_ONE_DAY_MONTH = 31;
    private static final int FIRST_DAY = 1;
    
    private static final int MORE_RECENT_DATE = -1;
    private static final int LESS_RECENT_DATE = 1;
    private static final int EQUAL_DATE = 0;
    private static final int IS_OF_TYPE = 0;
    
    /**
     * This method is a getter for year
     * @return int year
     */
    public int getYear(){
        return year;
    }

    /**
     * This method is a getter for month
     * @return int month
     */
    public int getMonth(){
        return month;
    }

    /**
     * This method is a getter for day
     * @return int day
     */
    public int getDay(){
        return day;
    }

    /**
     * This constructor divides an inputted date string into integer variables: day, month, year
     * @param date the String date that is being inputed and made into a Date object
     */
    public Date(String date) { 

        StringTokenizer st = new StringTokenizer(date, "/");
        String month = st.nextToken().trim();
        String day = st.nextToken().trim();
        String year = st.nextToken().trim();

        this.year = Integer.parseInt(year);
        this.month = Integer.parseInt(month);
        this.day = Integer.parseInt(day);
    } 

    /**
     * This constructor sets the date as the current date utilizing the Calendar class
     */
    public Date() { 
        Calendar calendar =  Calendar.getInstance();
        this.year = calendar.get(Calendar.YEAR);
        this.day = calendar.get(Calendar.DATE);
        this.month = calendar.get(Calendar.MONTH)+1;
    } 


    /**
     *  This method takes in a date and compares it to see which is older
     *  @return int 1 if given date is older, 0 if its the same day, -1 if given date is younger
     */  
    @Override
    public int compareTo(Date date) { 
        int date1Year = this.year;
        int date1Month = this.month;
        int date1Day = this.day;
        int date2Year = date.year;
        int date2Month = date.month;
        int date2Day = date.day;

            //CHANGE WHATS RETURNED
        if (date1Year < date2Year){
            return MORE_RECENT_DATE;
        }
        else if (date2Year < date1Year ){
            return LESS_RECENT_DATE;
        }
        else {
            if (date1Month < date2Month){
                return MORE_RECENT_DATE;
            }
            else if (date2Month < date1Month){
                return LESS_RECENT_DATE;
            }
            else {
                if (date1Day < date2Day){
                    return MORE_RECENT_DATE;
                }
                else if (date2Day < date1Day){
                    return LESS_RECENT_DATE;
                }
                else {
                    //SAME DAY SAME MONTH SAME YEAR
                    return EQUAL_DATE;
                }
            }
        }
    }

    /**
     * This method checks to see if a given year is a Leap year
     * @param year the year that is being checked
     * @return true if year is a leap year , false otherwise
     */
    private boolean isLeap(int year) {
        if (year % QUADRENNIAL == IS_OF_TYPE){
            if (year % CENTENNIAL == IS_OF_TYPE){
                if (year % QUATERCENTENNIAL == IS_OF_TYPE) {
                    return true;
                } 
                else {
                    return false;
                }
            }
            return true;
        } 
        else {
            return false;
        }
    }

    /**
     * This method checks to see if a month is valid
     * @param month the month that is being checked
     * @param year the year that is being checked
     * @return true if month is valid , false otherwise
     */
    private boolean isMonthValid(int month, int year){
        Calendar today = Calendar.getInstance();
        if (today.get(Calendar.YEAR) == year) {
            if (month > today.get(Calendar.MONTH)+1){
                return false;
            }
        }
        
        if (month > DEC || month < JAN){
            return false;
        }
        return true;
    }

    /**
     * This method checks to see if a day is valid based on month
     * @param month the month that is being checked
     * @param day the day that is being checked
     * @param year the day year that is being checked
     * @return true if day is valid , false otherwise
     */
    private boolean isDayValid(int month, int day, int year){
        Calendar today = Calendar.getInstance();
       
        if (year==today.get(Calendar.YEAR) && month==today.get(Calendar.MONTH)+1){
            if(day>today.get(Calendar.DATE)){
                return false;
            }
        }

        if (month == JAN || month == MAR || month == MAY || month == JUL || month == AUG || month == OCT || month == DEC){
            if (day > THIRTY_ONE_DAY_MONTH ||day < FIRST_DAY){
                return false;
            }
        }

        else if (month == FEB){ 
            if (isLeap(year)) {
                if (day > TWENTY_NINE_DAY_MONTH || day < FIRST_DAY) {
                    return false;
                }
            }
            else {
                if(day > TWENTY_EIGHT_DAY_MONTH ||day < FIRST_DAY){
                    return false;
                }
            }
        }

        else if (month == APR || month == JUN ||month == SEP ||month == NOV){
            if (day > THIRTY_DAY_MONTH ||day < FIRST_DAY){
                return false;
            }
        }
        return true;
    }
    
    /**
     * This method checks if a year is valid
     * @param year the year that is being checked
     * @return true if year is valid, false otherwise 
     */
    private boolean isYearValid(int year){
        Calendar today = Calendar.getInstance();
        if (year < MINIMUM_YEAR || year > today.get(Calendar.YEAR)){
            return false;
        }
        return true;
    }

    /**
     * This method prints a text
     * @return a textual representation of date 
     */
    @Override
    public String toString() { 
       String textualRepresentation = ( month + "/" + day + "/" + year);
       return textualRepresentation;
    }
    
     /**
     * This method checks to see if a date is valid
     * @return true if date is valid, false otherwise
     */
    public boolean isValid() {
        return isYearValid(year) && isMonthValid(month, year) && isDayValid(month,day,year);
     }   
}