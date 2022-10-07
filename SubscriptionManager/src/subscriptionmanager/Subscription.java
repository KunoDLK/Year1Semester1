/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package subscriptionmanager;

import java.time.LocalDateTime;

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

    public static SubPackages GetPackage(char packageSelected) {

        for (int i = 0; i < PackageLetters.length; i++) {
            if (PackageLetters[i] == packageSelected) {
                return IndexedSubPackages[i];
            }
        }

        return null;
    }

    public boolean ValidateDiscountCode() {

        if (DiscountCode.length() != 6)
            return false;

        for (int i = 0; i < 2; i++) {

            char letter = DiscountCode.charAt(i);

            if (!isLetterAToZ(letter))
                return false;
        }

        int currentYear = LocalDateTime.now().getYear();

        String validYearStr = DiscountCode.substring(3, 2);

        try {

            int validYear = Integer.parseInt(validYearStr);

            if (currentYear % 100 != validYear)
                return false;

        } catch (NumberFormatException e) {

            return false;
        }

        

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
