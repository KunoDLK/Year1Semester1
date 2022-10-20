/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subscriptionmanager;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author YOUR NAME
 */
public class DateHelper {

    public static final String[] Months = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };

    public static String GetCurrentDate() {
        
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        return sdf.format(cal.getTime());
    }

    public static int GetCurrentYear() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR);
    }

    public static int GetCurrentMonth() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.MONTH);
    }

}
