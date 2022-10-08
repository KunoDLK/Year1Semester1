/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package subscriptionmanager;

/**
 * DTO for a subscription
 * 
 * @author kleeuwkent
 */
public class Subscription {

    public enum SubPackages {
        Bronze,
        Silver,
        Gold
    }

    public enum PaymentTerms {
        OneOff,
        Monthly
    }

    public final static double[][] SubscriptionRates = { { 6, 5, 4, 3 }, { 8, 7, 6, 5 }, { 9.99, 8.99, 7.99, 6.99 } };

    public final static char[] PackageLetters = { 'B', 'S', 'G' };

    public final static SubPackages[] IndexedSubPackages = { SubPackages.Bronze, SubPackages.Silver, SubPackages.Gold };

    public final static int[] PackageDurations = { 1, 3, 6, 12 };

    public String StartDate;

    public int Duration;

    public String DiscountCode;

    public SubPackages SubPackage;

    public PaymentTerms PaymentTerm;

    public String Name;

    public Subscription() {
        DiscountCode = "-";
    }

    public static SubPackages GetPackage(char packageSelected) {

        for (int i = 0; i < PackageLetters.length; i++) {
            if (PackageLetters[i] == packageSelected) {
                return IndexedSubPackages[i];
            }
        }

        return null;
    }

    public boolean ValidateAndSetDiscountCode(String discountCode) {

        DiscountCode = "-";

        // code length validation
        if (discountCode.length() != 6)
            return false;

        // checks that the first two chars are letters
        for (int i = 0; i < 2; i++) {

            char letter = discountCode.charAt(i);

            if (!isLetterAToZ(letter))
                return false;
        }

        // code date validation
        int currentYear = DateHelper.GetCurrentYear();

        String validYearStr = discountCode.substring(2, 4);

        try {

            int validYear = Integer.parseInt(validYearStr);

            // checks if the the last two numbers of the year match the discount code
            if (currentYear % 100 != validYear)
                return false;

        } catch (NumberFormatException e) {

            return false;
        }

        int currentMonth = DateHelper.GetCurrentMonth();
        char monthChar = discountCode.charAt(4);
        char validChar;
        if (currentMonth > 6)
        {
            validChar = 'E';
        }
        else
        {
            validChar = 'L';
        }

        if (monthChar != validChar)
            return false;

            try {

                String discountPercentageChar = discountCode.substring(5);
                int discountPercentage = Integer.parseInt(discountPercentageChar);
    
                if (discountPercentage < 1 && discountPercentage > 9)
                    return true;
    
            } catch (NumberFormatException e) {
    
                return false;
            }


        DiscountCode = discountCode;
        return true;
    }

    private boolean isLetterAToZ(char letter) {

        if (letter >= 'A' && letter <= 'Z') {
            return true;
        } else {
            return false;
        }
    }
}
