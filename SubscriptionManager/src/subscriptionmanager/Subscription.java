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

    public final static SubPackages[] IndexedSubPackages = {SubPackages.Bronze, SubPackages.Silver, SubPackages.Gold};

    public String StartDate;

    public int Duration;

    public String DiscountCode;

    public SubPackages SubPackage;

    public PaymentTerms PaymentTerm;

    public String Name;

    public static SubPackages GetPackage(char packageSelected) {

        for (int i = 0; i < PackageLetters.length; i++) {
            if (PackageLetters[i] == packageSelected)
            {
                return IndexedSubPackages[i];
            }
        }

        return null;
    }
}
