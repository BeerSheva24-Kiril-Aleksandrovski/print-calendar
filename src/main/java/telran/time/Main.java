package telran.time;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Arrays;
import java.time.YearMonth;
import java.util.stream.Collectors;

record MonthYear(int month, int year) {}

public class Main {
    public static void main(String[] args) {
        try {
            args = new String[]{"8","2024"};
            
            MonthYear monthYear = getMonthYear(args); //if no arguments current year and month should be applied
            
            printCalendar(monthYear);
        } catch (RuntimeException e) {
                e.printStackTrace();
        }
        catch (Exception e) {
           System.out.println(e.getMessage());
        }
    }

    private static void printCalendar(MonthYear monthYear) {
        printTitle(monthYear);
        printWeekDays();
        printDates(monthYear);
    }

    private static void printWeekDays() {
        String daysOfWeek = Arrays.stream(DayOfWeek.values())
                                  .map((dayOfWeek) -> dayOfWeek.name().substring(0, 3))
                                  .collect(Collectors.joining(" "));
        
        System.out.println(" " + daysOfWeek);
    }
    
    private static void printTitle(MonthYear monthYear) {
        System.out.print(Month.of(monthYear.month()).toString() + " ");
        System.out.println(Year.of(monthYear.year()));
        
    }
    private static void printDates(MonthYear monthYear) {
        int offset = getOffset(getFirstDayofWeek(monthYear)); // 3
        
        int currDay = 1;
        while (currDay  <= getLastDayOfMonth(monthYear)) {
            if (currDay < 10){
                System.out.print("  " + currDay + " ");
            } else {
                System.out.print(" " + currDay + " ");
            }
            if( (currDay+ offset) % 7 == 0) System.out.print("\n");
            currDay++;
        }
    }
    
    private static MonthYear getMonthYear (String[] args) throws Exception {
        int month, year;
        try { 
            if (args.length != 2){
                args[0] = String.valueOf(LocalDate.now().getYear());
                args[1] = String.valueOf(LocalDate.now().getMonthValue());
            }

            year = Integer.parseInt(args[1]);
            if (year < 0 ) {
                throw new Exception();
            }

            month = Integer.parseInt(args[0]);
            if (month < 1 || month > 12) {
                throw new Exception();
            }
            
        } catch (Exception e) {
            throw new Exception("Invalid year or month");
        }       
        return new MonthYear(month, year);
    }
    private static int getFirstDayofWeek(MonthYear monthYear) {
        LocalDate firstDay = LocalDate.of(monthYear.year(), monthYear.month(), 1);
        return firstDay.getDayOfWeek().getValue();
    }
    private static int getOffset(int firstWeekDay) {
        int res = firstWeekDay - 1;
        for (int i = 0; i < res; i++) {
            System.out.print("    ");
        }
        return res;
    }
    private static int getLastDayOfMonth(MonthYear monthYear) {
        YearMonth yearMonth = YearMonth.of(monthYear.year(), monthYear.month());
        int daysInMonth = yearMonth.lengthOfMonth();
        return daysInMonth;
    }
}