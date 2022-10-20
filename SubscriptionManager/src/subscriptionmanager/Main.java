/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subscriptionmanager;

import java.util.ArrayList;
import java.util.HashMap;

import subscriptionmanager.Subscription.PaymentTerms;
import subscriptionmanager.Subscription.SubPackages;

/**
 *
 * @author YOUR NAME
 */
public class Main {

    static ConsoleMethods consoleMethods;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // String currentDate = DateHelper.getDate();
        // System.out.printf("The current date is %s \n", currentDate);

        consoleMethods = new ConsoleMethods();

        System.out.println("Hello World!");

        MainApplicationLoop();

        System.exit(0);
    }

    /**
     * Main Loop for program
     */
    public static void MainApplicationLoop() {
        boolean RunLoop = true;

        do {
            int menuChoice = consoleMethods.RunMenu();
            switch (menuChoice) {
                case 0:
                    RunLoop = false;
                    break;
                case 1:
                    Subscription newSubscription = CreateNewSubscription();
                    consoleMethods.DisplaySubscription(newSubscription);
                    break;

                case 2:
                    SummariesSubscriptions();
                    break;

                case 3:
                    SelectMonthsSubscriptions();
                    break;

                case 4:
                    FindUserAndDisplay();
                    break;

                default:
                    System.out.println("Unknown menu item: " + menuChoice);
                    break;
            }
        } while (RunLoop);
    }

    private static void SummariesSubscriptions() {

        System.out.print("Reading file");
        ArrayList<Subscription> subscriptionList = FileIO.GetAllSubscriptions();
        System.out.println("...DONE");

        HashMap<String, HashMap<SubPackages, ArrayList<Subscription>>> organizedSubscriptions = Subscription
                .OrginiseSubscriptions(subscriptionList);

        float totalCost = 0;
        for (Subscription subscription : subscriptionList) {
            totalCost += subscription.GetCost();
        }

        String months = "Month:\t";
        String totalSubs = "Total:\t";
        String bronzeSubs = "Bronze:\t";
        String silverSubs = "Silver:\t";
        String goldSubs = "Gold\t";

        for (String month : DateHelper.Months) {

            HashMap<SubPackages, ArrayList<Subscription>> monthSubs = organizedSubscriptions.get(month);
            int monthBronzeSubs = monthSubs.get(Subscription.SubPackages.Bronze).size();
            int monthSilverSubs = monthSubs.get(Subscription.SubPackages.Silver).size();
            int monthGoldSubs = monthSubs.get(Subscription.SubPackages.Gold).size();

            months += month + "\t";
            bronzeSubs += monthBronzeSubs + "\t";
            silverSubs += monthSilverSubs + "\t";
            goldSubs += monthGoldSubs + "\t";
            totalSubs += (monthBronzeSubs + monthSilverSubs + monthGoldSubs) + "\t";

        }

        System.out.print("Total number of subscriptions: ");
        System.out.println(subscriptionList.size());

        System.out.print("Average monthly subscription: ");
        System.out.println((float) subscriptionList.size() / 12);

        System.out.print("Average monthly subscription fee: ");
        System.out.println(totalCost / subscriptionList.size());

        System.out.println(months);
        System.out.println(totalSubs);
        System.out.println(bronzeSubs);
        System.out.println(silverSubs);
        System.out.println(goldSubs);
        System.out.println();
    }

    private static void FindUserAndDisplay() {

    }

    private static void SelectMonthsSubscriptions() {

        float totalCost = 0;

        System.out.print("Select month (1-12): ");
        int month = consoleMethods.GetValidatedInteger(1, 12);

        System.out.print("Reading file");
        ArrayList<Subscription> subscriptionList = FileIO.GetAllSubscriptions();
        System.out.println("...DONE");

        HashMap<String, HashMap<SubPackages, ArrayList<Subscription>>> organizedSubscriptions = Subscription
                .OrginiseSubscriptions(subscriptionList);

        HashMap<SubPackages, ArrayList<Subscription>> monthSubs = organizedSubscriptions
                .get(DateHelper.Months[month - 1]);

        ArrayList<Subscription> monthBronzeSubs = monthSubs.get(Subscription.SubPackages.Bronze);
        ArrayList<Subscription> monthSilverSubs = monthSubs.get(Subscription.SubPackages.Silver);
        ArrayList<Subscription> monthGoldSubs = monthSubs.get(Subscription.SubPackages.Gold);
        
        for (Subscription subscription : monthBronzeSubs) {
            totalCost += subscription.GetCost();
        }
        for (Subscription subscription : monthSilverSubs) {
            totalCost += subscription.GetCost();
        }
        for (Subscription subscription : monthGoldSubs) {
            totalCost += subscription.GetCost();
        }

        int totalSubs = monthBronzeSubs.size() + monthSilverSubs.size() + monthGoldSubs.size();

        System.out.print("Total number of subscriptions for ");
        System.out.print(DateHelper.Months[month - 1]);
        System.out.print(" : ");
        System.out.println(totalSubs);

        System.out.print("Average subscription fee: £ ");
        System.out.println(totalCost / totalSubs);
        System.out.println(2);

        System.out.println("Percentage of subscriptions:");
        System.out.print("Bronze: ");
        System.out.print(monthBronzeSubs.size() / (double) totalSubs * 100);
        System.out.println(" %");

        System.out.print("Silver: ");
        System.out.print(monthSilverSubs.size() / (double) totalSubs * 100);
        System.out.println(" %");

        System.out.print("Gold: ");
        System.out.print(monthGoldSubs.size() / (double) totalSubs * 100);
        System.out.println(" %");
        System.out.println();

    }

    private static Subscription CreateNewSubscription() {

        Subscription newSubscription = new Subscription();

        Boolean validated = false;

        do {
            System.out.print("Please enter Customer's name: ");

            String name = consoleMethods.GetString();

            if (name.length() <= 25) {
                newSubscription.Name = name;
                validated = true;

            } else {
                System.out.println("^^^^Name too long (25 characters max)");
            }

        } while (!validated);

        System.out.print("Please enter package type Gold, Silver or Bronze ('G', 'S', 'B'): ");

        char packageSelected = consoleMethods.GetValidatedChar(Subscription.PackageLetters);
        newSubscription.SubPackage = Subscription.GetPackage(packageSelected);

        System.out.print("Please enter subscription duration 1, 3, 6, 12 (months): ");

        int subDuration = consoleMethods.GetValidatedInteger(Subscription.PackageDurations);
        newSubscription.Duration = subDuration;

        validated = false;
        do {

            System.out.print("Please enter discount code ('-' for no discount code): ");
            String discountCode = consoleMethods.GetString();

            Boolean validCode = newSubscription.ValidateAndSetDiscountCode(discountCode.toUpperCase());

            if (discountCode.equals("-")) {
                validated = true;

            } else if (!validCode) {
                System.out.println("^^^^Invalid code");

            } else {
                System.out.println("Code Validated");
                validated = true;
            }

        } while (!validated);

        // gets what term the subscription is
        System.out.print("Would you like to pay upfront, with a 5% discount ('Y'/'N'): ");

        char[] validChars = { 'Y', 'N' };
        char enteredChar = consoleMethods.GetValidatedChar(validChars);

        if (enteredChar == 'N') {
            newSubscription.PaymentTerm = PaymentTerms.Monthly;
        } else {
            newSubscription.PaymentTerm = PaymentTerms.OneOff;
        }

        // calculates cost and sets the start date
        newSubscription.StartSubscription();

        return newSubscription;
    }
}
