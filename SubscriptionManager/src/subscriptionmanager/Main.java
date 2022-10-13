/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subscriptionmanager;

import subscriptionmanager.Subscription.PaymentTerms;

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

    }

    private static void FindUserAndDisplay() {

    }

    private static void SelectMonthsSubscriptions() {

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
