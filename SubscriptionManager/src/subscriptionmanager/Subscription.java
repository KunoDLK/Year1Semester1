package subscriptionmanager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * class for subscription
 * with some helpful methods for converting to and from properties
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

    private int Duration;

    private String StartDate;

    private String Name;

    private SubPackages SubPackage;

    private PaymentTerms PaymentTerm;

    // #endregion Class Properties

    // #region Constructor

    /*
     * Constructor to make empty subscription object
     */
    public Subscription() {
        DiscountCode = "-";
    }

    /**
     * Constructor to populate subscription from the line stored in file
     * 
     * @param FileStorageLine string from file
     */
    public Subscription(String FileStorageLine) {
        String[] strArray = FileStorageLine.split("\\t");

        StartDate = strArray[0];

        SubPackage = GetPackageFromChar(strArray[1].charAt(0));

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

    public Subscription(String name, SubPackages subPackage, int duration, PaymentTerms paymentTerm,
            String discountCode) {

                this.Name = name;
                this.SubPackage = subPackage;
                this.Duration = duration;
                this.PaymentTerm = paymentTerm;
                this.DiscountCode = discountCode;
    }

    /**
     * @return returns discount code
     */
    public String GetDiscountCode() {
        return DiscountCode;
    }

    /**
     * @return returns cost
     */
    public double GetCost() {
        return this.Cost;
    }

    /**
     * @return returns duration
     */
    public int GetDuration() {
        return this.Duration;
    }

    public String GetName() {
        return this.Name;
    }

    public String GetStartDate() {
        return this.StartDate;
    }

    public SubPackages GetSubPackage() {
        return SubPackage;
    }

    /**
     * Converts from char to enum of package type
     * 
     * @param packageSelected char 'G', 'S', 'B'
     * @return null if not valid char
     */
    public static SubPackages GetPackageFromChar(char packageSelected) {

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
    public static boolean ValidateDiscountCode(String discountCode) {

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
        return true;
    }

    /**
     * Calculates the cost of the subscription
     * and sets the start date to today
     * 
     * @return true if subscription is valid and started
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

        // knocks 5%off is payed upfront
        if (PaymentTerm == PaymentTerms.OneOff) {
            this.Cost *= this.Duration;
            this.Cost *= 0.95;
        }

        // Knocks off the discount from the discount code
        if (!DiscountCode.equals("-")) {
            char discountChar = DiscountCode.charAt(5);
            Double discount = Double.parseDouble("0.0" + String.valueOf(discountChar));
            this.Cost *= (1 - discount);
        }

        StartDate = DateHelper.GetCurrentDate();

        return true;
    }

    /**
     * @returns payment term as a string "One-Off"/"Monthly"
     */
    public String GetTermAsString() {
        if (PaymentTerm == PaymentTerms.OneOff) {
            return "One-Off";

        } else {
            return PaymentTerms.Monthly.name();
        }
    }

    /**
     * @returns number as spelt out word
     */
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

    /**
     * Organizes subscriptions in to a structured data structure sorted by month and
     * package type
     * 
     * @param subscriptionList list of subscriptions
     * @return organized subscription
     */
    public static HashMap<String, HashMap<SubPackages, ArrayList<Subscription>>> OrginiseSubscriptions(
            ArrayList<Subscription> subscriptionList) {

        HashMap<String, HashMap<Subscription.SubPackages, ArrayList<Subscription>>> organizedSubscriptions = new HashMap<String, HashMap<Subscription.SubPackages, ArrayList<Subscription>>>();

        // adds entires for each month
        for (String month : DateHelper.Months) {

            organizedSubscriptions.put(month, new HashMap<Subscription.SubPackages, ArrayList<Subscription>>());
        }

        // Adds entries for each package type for each month
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

    /**
     * Converse subscription object to the string line found in file
     * 
     * @returns String line
     */
    public String ConvertToFileString() {
        String fileString = "";

        fileString += this.StartDate + "\t";
        fileString += this.SubPackage.name().charAt(0) + "\t";
        fileString += this.Duration + "\t";
        fileString += this.GetDiscountCode() + "\t";
        fileString += this.GetTermAsString().charAt(0) + "\t";
        fileString += String.valueOf((int) (this.GetCost() * 100)) + "\t";
        fileString += this.Name;

        return fileString;
    }

    // #endregion Public Methods

    // #region Private Methods

    /**
     * Checks if a character is a english upper case letter A-Z
     * 
     * @param letter letter in question
     * @return true/false if is a letter
     */
    private static boolean isLetterAToZ(char letter) {

        if (letter >= 'A' && letter <= 'Z') {
            return true;
        } else {
            return false;
        }
    }

    // #endregion Private Methods
}
