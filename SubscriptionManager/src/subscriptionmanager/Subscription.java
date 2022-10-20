/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package subscriptionmanager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * DTO for a subscription
 * 
 * @author kleeuwkent
 */
public class Subscription {

    // #region Enums

    public enum SubPackages {
        Bronze,
        Silver,
        Gold
    }

    public enum PaymentTerms {
        OneOff,
        Monthly
    }

    // #endregion Enums

    // #region Final Static Arrays

    public final static double[][] SubscriptionRates = { { 6, 5, 4, 3 }, { 8, 7, 6, 5 }, { 9.99, 8.99, 7.99, 6.99 } };

    public final static char[] PackageLetters = { 'B', 'S', 'G' };

    public final static SubPackages[] IndexedSubPackages = { SubPackages.Bronze, SubPackages.Silver, SubPackages.Gold };

    public final static int[] PackageDurations = { 1, 3, 6, 12 };

    // #endregion Final Static Arrays

    // #region Class Properties

    private String DiscountCode;

    private double Cost;

    public int Duration;

    public String StartDate;

    public String Name;

    public SubPackages SubPackage;

    public PaymentTerms PaymentTerm;

    // #endregion Class Properties

    // #region Constructor

    public Subscription() {
        DiscountCode = "-";
    }

    public Subscription(String FileStorageLine) {
        String[] strArray = FileStorageLine.split("\\t");

        StartDate = strArray[0];

        SubPackage = GetPackage(strArray[1].charAt(0));

        Duration = Integer.parseInt(strArray[2]);

        DiscountCode = strArray[3];

        if (strArray[4].equals("O")) {
            PaymentTerm = PaymentTerms.OneOff;

        } else {
            PaymentTerm = PaymentTerms.Monthly;
        }

        Cost = (double) Integer.parseInt(strArray[5]) / 100;

        Name = strArray[6];
    }

    // #endregion Constructor

    // #region Public Methods

    public String GetDiscountCode() {
        return DiscountCode;
    }

    public static SubPackages GetPackage(char packageSelected) {

        for (int i = 0; i < PackageLetters.length; i++) {
            if (PackageLetters[i] == packageSelected) {
                return IndexedSubPackages[i];
            }
        }

        return null;
    }

    /**
     * Validates Discount code,
     * will set discount code if valid
     * 
     * @param discountCode as a string
     * @return true/false if code is valid
     */
    public boolean ValidateAndSetDiscountCode(String discountCode) {

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

        if (currentMonth > 6) {
            validChar = 'L';
        } else {
            validChar = 'E';
        }

        // Checks the month letter matches the current month letter
        if (monthChar != validChar)
            return false;

        try {

            // parses discount % and validates it's in range
            String discountPercentageChar = discountCode.substring(5);
            int discountPercentage = Integer.parseInt(discountPercentageChar);

            if (discountPercentage < 1 && discountPercentage > 9)
                return true;

        } catch (NumberFormatException e) {

            return false;
        }

        // if code has made it this far code is valid
        DiscountCode = discountCode;
        return true;
    }

    /**
     * Calculates the cost of the subscription
     * and sets the start date to today
     */
    public boolean StartSubscription() {

        int subPackageIndex;

        if (this.SubPackage != null) {
            subPackageIndex = this.SubPackage.ordinal();

        } else {

            System.out.println("ERROR, you can not start subscription. SubPackage not Set");
            return false;
        }

        int durationIndex = -1;
        for (int i = 0; i <= 3; i++) {
            if (PackageDurations[i] == this.Duration) {
                durationIndex = i;
            }
        }

        if (durationIndex == -1) {
            System.out.println("ERROR, can not start subscription. package duration not set correctly");
            return false;
        }

        this.Cost = SubscriptionRates[subPackageIndex][durationIndex];

        if (PaymentTerm == PaymentTerms.OneOff) {
            this.Cost *= this.Duration;
            this.Cost *= 0.95;
        }

        if (!DiscountCode.equals("-")) {
            char discountChar = DiscountCode.charAt(5);
            Double discount = Double.parseDouble("0.0" + String.valueOf(discountChar));
            this.Cost *= (1 - discount);
        }

        StartDate = DateHelper.GetCurrentDate();

        return true;
    }

    // #endregion Public Methods

    // #region Private Methods

    /**
     * Checks if a character is a english upper case letter A-Z
     * 
     * @param letter letter in question
     * @return true/false if is a letter
     */
    private boolean isLetterAToZ(char letter) {

        if (letter >= 'A' && letter <= 'Z') {
            return true;
        } else {
            return false;
        }
    }

    public double GetCost() {
        return this.Cost;
    }

    public String GetTermAsString() {
        if (PaymentTerm == PaymentTerms.OneOff) {
            return "One-Off";

        } else {
            return PaymentTerms.Monthly.name();
        }
    }

    public String GetDurationAsString() {
        switch (this.Duration) {
            case 1:
                return "One";

            case 3:
                return "Three";

            case 6:
                return "Six";

            case 12:
                return "Twelve";

            default:
                return "N/A";
        }
    }

    public static HashMap<String, HashMap<SubPackages, ArrayList<Subscription>>> OrginiseSubscriptions(ArrayList<Subscription> subscriptionList) {

        HashMap<String, HashMap<Subscription.SubPackages, ArrayList<Subscription>>> organizedSubscriptions = new HashMap<String, HashMap<Subscription.SubPackages, ArrayList<Subscription>>>();

        for (String month : DateHelper.Months) {

            organizedSubscriptions.put(month, new HashMap<Subscription.SubPackages, ArrayList<Subscription>>());
        }

        organizedSubscriptions.forEach((k, v) -> {
            v.put(SubPackages.Bronze, new ArrayList<Subscription>());
            v.put(SubPackages.Silver, new ArrayList<Subscription>());
            v.put(SubPackages.Gold, new ArrayList<Subscription>());
        });

        // Place subscriptions into organized hash map
        for (Subscription subscription : subscriptionList) {

            String key1 = subscription.StartDate.substring(3, 6);

            SubPackages key2 = subscription.SubPackage;

            organizedSubscriptions.get(key1).get(key2).add(subscription);
        }

        return organizedSubscriptions;
    }

    // #endregion Private Methods
}
