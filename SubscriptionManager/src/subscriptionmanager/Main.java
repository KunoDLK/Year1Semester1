/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subscriptionmanager;

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
                    AddNewSubscription();
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

    private static void AddNewSubscription() {

        Subscription newSubscription = new Subscription();

        System.out.print("Please enter Customer's name: ");

        String name = consoleMethods.GetString();

        newSubscription.Name = name;

        System.out.print("Please enter package type Gold, Silver or Bronze ('G', 'S', 'B'): ");

        char packageSelected = consoleMethods.GetValidatedChar(Subscription.PackageLetters);

        newSubscription.SubPackage = Subscription.GetPackage(packageSelected);

    }
}
