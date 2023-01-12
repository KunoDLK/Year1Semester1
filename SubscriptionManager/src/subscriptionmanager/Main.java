package subscriptionmanager;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import subscriptionmanager.Subscription.PaymentTerms;
import subscriptionmanager.Subscription.SubPackages;

/**
 * Main class of application Subscriptions manager
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

        System.out.println();

        float totalCost = 0;
        for (Subscription subscription : subscriptionList) {
            totalCost += subscription.GetCost();
        }

        int totalSubs = 0;
        int totalBronzeSubs = 0;
        int totalSilverSubs = 0;
        int totalGoldSubs = 0;
        String monthsString = "Month:\t";
        String totalSubsString = "Total:\t";
        String bronzeSubsString = "Bronze:\t";
        String silverSubsString = "Silver:\t";
        String goldSubsString = "Gold\t";

        for (String month : DateHelper.Months) {

            HashMap<SubPackages, ArrayList<Subscription>> monthSubs = organizedSubscriptions.get(month);
            int monthBronzeSubs = monthSubs.get(Subscription.SubPackages.Bronze).size();
            int monthSilverSubs = monthSubs.get(Subscription.SubPackages.Silver).size();
            int monthGoldSubs = monthSubs.get(Subscription.SubPackages.Gold).size();

            monthsString += month + "\t";
            bronzeSubsString += monthBronzeSubs + "\t";
            totalBronzeSubs += monthBronzeSubs;
            silverSubsString += monthSilverSubs + "\t";
            totalSilverSubs += monthSilverSubs;
            goldSubsString += monthGoldSubs + "\t";
            totalGoldSubs += monthGoldSubs;

            int totalAddition = (monthBronzeSubs + monthSilverSubs + monthGoldSubs);
            totalSubs += totalAddition;
            totalSubsString += totalAddition + "\t";
        }

        System.out.print("Total number of subscriptions: ");
        System.out.println(subscriptionList.size());

        System.out.print("Average monthly subscriptions: ");
        System.out.println(decimalFormatter.format(subscriptionList.size() / 12));

        System.out.print("Average monthly subscription fee: £");
        System.out.println(decimalFormatter.format(totalCost / subscriptionList.size()));
        System.out.println();

        System.out.println("Percentage of subscriptions:");
        System.out.print("Bronze:\t");
        System.out.print(decimalFormatter.format(totalBronzeSubs / (double) totalSubs * 100));
        System.out.println("%");

        System.out.print("Silver:\t");
        System.out.print(decimalFormatter.format(totalSilverSubs / (double) totalSubs * 100));
        System.out.println("%");

        System.out.print("Gold:\t");
        System.out.print(decimalFormatter.format(totalGoldSubs / (double) totalSubs * 100));
        System.out.println("%");
        System.out.println();

        System.out.println(monthsString);
        System.out.println(totalSubsString);
        System.out.println(bronzeSubsString);
        System.out.println(silverSubsString);
        System.out.println(goldSubsString);

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

        System.out.println();

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
        System.out.println();

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
     * Runs the find subscription function summarizes all subscriptions
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

        Subscription newSubscription = new Subscription();

        Boolean validated = false;

        do {
            System.out.print("Please enter Customer's name: ");

            String name = ConsoleMethods.GetString();

            if (name.length() <= 25) {
                if (name.length() > 0) {

                    subName = name;
                    validated = true;

                } else {
                    System.out.println("^^^^No name given");
                }
                subName = name;
                validated = true;

            } else {
                System.out.println("^^^^Name too long (25 characters max)");
            }

        } while (!validated);

        newSubscription.SetName(subName);

        System.out.print("Please enter package type Gold, Silver or Bronze ('G', 'S', 'B'): ");

        char packageSelected = ConsoleMethods.GetValidatedChar(Subscription.PackageLetters);
        subSubPackage = Subscription.GetPackageFromChar(packageSelected);
        newSubscription.SetSubPackage(subSubPackage);

        System.out.print("Please enter subscription duration 1, 3, 6, 12 (months): ");

        subDuration = ConsoleMethods.GetValidatedInteger(Subscription.PackageDurations);
        newSubscription.SetDuration(subDuration);

        validated = false;
        do {

            System.out.print("Please enter discount code ('-' for no discount code): ");
            subDiscountCode = ConsoleMethods.GetString();

            Boolean validCode = newSubscription.SetDiscountCode(subDiscountCode.toUpperCase());

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

        char[] validChars = {'Y', 'N'};
        char enteredChar = ConsoleMethods.GetValidatedChar(validChars);

        if (enteredChar == 'N') {
            subPaymentTerm = PaymentTerms.Monthly;
        } else {
            subPaymentTerm = PaymentTerms.OneOff;
        }

        newSubscription.SetTerm(subPaymentTerm);

        // calculates cost and sets the start date
        newSubscription.StartSubscription();

        return newSubscription;
    }

    /**
     * @returns subscriptions from file
     */
    private static ArrayList<Subscription> ReadFile() {

        System.out.println("Select file:");
        for (int i = 0; i < FileIO._ReadFiles.size(); i++) {
            System.out.println(FileIO._ReadFiles.get(i) + " (" + (i + 1) + ")");
        }

        int fileOption = ConsoleMethods.GetValidatedInteger(1, FileIO._ReadFiles.size());

        System.out.print("Reading file");
        long startTime = System.currentTimeMillis();

        ArrayList<Subscription> subscriptionList = FileIO.GetAllSubscriptions(FileIO._ReadFiles.get(fileOption - 1));

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        System.out.println("...DONE (" + elapsedTime + "ms)");
        return subscriptionList;
    }
}
