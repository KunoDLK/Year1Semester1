package subscriptionmanager;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import subscriptionmanager.Subscription.PaymentTerms;
import subscriptionmanager.Subscription.SubPackages;

/**
 * Main class of application
 * Subscriptions manager
 * 
 * @author Kuno DLK
 */
public class Main {

    static DecimalFormat decimalFormatter;

    /**
     * Main constructor
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        ConsoleMethods.scanner = new Scanner(System.in);
        decimalFormatter = new DecimalFormat("#.##");
        decimalFormatter.setRoundingMode(RoundingMode.HALF_UP);

        System.out.println("Hello World!");

        MainApplicationLoop();

        System.out.println("Goodbye (:");

        System.exit(0);
    }

    /**
     * Main Loop for program
     */
    public static void MainApplicationLoop() {
        boolean RunLoop = true;

        do {
            int menuChoice = ConsoleMethods.RunMenu();
            switch (menuChoice) {
                case 0:
                    RunLoop = false;
                    break;
                case 1:
                    Subscription newSubscription = CreateNewSubscription();
                    ConsoleMethods.DisplaySubscription(newSubscription);
                    FileIO.AddSubscriptionToFile(newSubscription);
                    break;

                case 2:
                    SummariesSubscriptions();
                    break;

                case 3:
                    SelectMonthsSubscriptions();
                    break;

                case 4:
                    FindSubAndDisplay();
                    break;

                default:
                    System.out.println("Unknown menu item: " + menuChoice);
                    break;
            }
        } while (RunLoop);
    }

    /**
     * Runs the summaries function
     */
    private static void SummariesSubscriptions() {

        ArrayList<Subscription> subscriptionList = ReadFile();

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

        System.out.print("Total number of subscriptions:");
        System.out.println(subscriptionList.size());

        System.out.print("Average monthly subscriptions: ");
        System.out.println(decimalFormatter.format(subscriptionList.size() / 12));

        System.out.print("Average monthly subscription fee: £");
        System.out.println(decimalFormatter.format(totalCost / subscriptionList.size()));

        System.out.println(months);
        System.out.println(totalSubs);
        System.out.println(bronzeSubs);
        System.out.println(silverSubs);
        System.out.println(goldSubs);

    }

    /**
     * Runs the find subscription function
     * summarizes all subscriptions
     */
    private static void FindSubAndDisplay() {

        System.out.print("Input Name to search for: ");
        String searchString;
        do {
            searchString = ConsoleMethods.GetString().toLowerCase();

        } while (searchString.isEmpty());

        ArrayList<Subscription> subscriptionList = ReadFile();
        ArrayList<Subscription> searchResults = new ArrayList<Subscription>();

        for (Subscription subscription : subscriptionList) {

            if (subscription.GetName().toLowerCase().contains(searchString)) {

                searchResults.add(subscription);
            }
        }

        System.out.print("Search results:");
        System.out.println(searchResults.size());

        for (Subscription subscription : searchResults) {

            ConsoleMethods.DisplaySubscription(subscription);
        }
    }

    /**
     * Summaries subscription for a given month
     */
    private static void SelectMonthsSubscriptions() {

        float totalCost = 0;

        System.out.print("Select month (1-12): ");
        int month = ConsoleMethods.GetValidatedInteger(1, 12);

        ArrayList<Subscription> subscriptionList = ReadFile();

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

        System.out.print("Average subscription fee: £");
        System.out.println(decimalFormatter.format(totalCost / totalSubs));

        System.out.println("Percentage of subscriptions:");
        System.out.print("Bronze:\t");
        System.out.print(decimalFormatter.format(monthBronzeSubs.size() / (double) totalSubs * 100));
        System.out.println("%");

        System.out.print("Silver:\t");
        System.out.print(decimalFormatter.format(monthSilverSubs.size() / (double) totalSubs * 100));
        System.out.println("%");

        System.out.print("Gold:\t");
        System.out.print(decimalFormatter.format(monthGoldSubs.size() / (double) totalSubs * 100));
        System.out.println("%");

    }

    /**
     * Creates a new subscription from user input
     * 
     * @return new subscription
     */
    private static Subscription CreateNewSubscription() {

        Subscription.SubPackages subSubPackage;
        String subName = "";
        int subDuration;
        String subDiscountCode;
        Subscription.PaymentTerms subPaymentTerm;

        Boolean validated = false;

        do {
            System.out.print("Please enter Customer's name: ");

            String name = ConsoleMethods.GetString();

            if (name.length() <= 25) {
                subName = name;
                validated = true;

            } else {
                System.out.println("^^^^Name too long (25 characters max)");
            }

        } while (!validated);

        System.out.print("Please enter package type Gold, Silver or Bronze ('G', 'S', 'B'): ");

        char packageSelected = ConsoleMethods.GetValidatedChar(Subscription.PackageLetters);
        subSubPackage = Subscription.GetPackageFromChar(packageSelected);

        System.out.print("Please enter subscription duration 1, 3, 6, 12 (months): ");

        subDuration = ConsoleMethods.GetValidatedInteger(Subscription.PackageDurations);

        validated = false;
        do {

            System.out.print("Please enter discount code ('-' for no discount code): ");
            subDiscountCode = ConsoleMethods.GetString();

            Boolean validCode = Subscription.ValidateDiscountCode(subDiscountCode.toUpperCase());

            if (subDiscountCode.equals("-")) {
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
        char enteredChar = ConsoleMethods.GetValidatedChar(validChars);

        if (enteredChar == 'N') {
            subPaymentTerm = PaymentTerms.Monthly;
        } else {
            subPaymentTerm = PaymentTerms.OneOff;
        }

        Subscription newSubscription = new Subscription(subName, subSubPackage, subDuration, subPaymentTerm,
                subDiscountCode);

        // calculates cost and sets the start date
        newSubscription.StartSubscription();

        return newSubscription;
    }

    /**
     * @returns subscriptions from file
     */
    private static ArrayList<Subscription> ReadFile() {
        System.out.print("Reading file");
        ArrayList<Subscription> subscriptionList = FileIO.GetAllSubscriptions();
        System.out.println("...DONE");
        System.out.println();
        return subscriptionList;
    }
}
