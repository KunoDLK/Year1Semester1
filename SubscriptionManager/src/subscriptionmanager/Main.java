/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subscriptionmanager;

import java.util.Scanner;

/**
 *
 * @author YOUR NAME
 */
public class Main {

    public static Scanner scanner;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // String currentDate = DateHelper.getDate();
        // System.out.printf("The current date is %s \n", currentDate);

        scanner = new Scanner(System.in);

        System.out.println("Hello World!");
        MainApplicationLoop();
    }

    /**
     * Main Loop for program
     */
    public static void MainApplicationLoop() {
        boolean RunLoop = true;

        do {
            int menuChoice = DisplayMenu();
            switch (menuChoice) {
                case 0:
                    RunLoop = false;
                    break;

                default:
                    System.out.println("Menu Item: " + menuChoice);
                    break;
            }
        } while (RunLoop);
    }

    /**
     * Displays menu to the console, gets a validated response from console
     * 
     * @return user choice, int
     */
    private static int DisplayMenu() {
        System.out.println(
                "1. Enter new Subscription\n2. Display Summary of subscriptions\n3. Display Summary of subscription for Selected Month\n4. Find and display subscription\n0. Exit");

        int returnInt = 0;
        boolean validated = false;

        do {
            try {

                String userinput = scanner.nextLine();
                returnInt = Integer.parseInt(userinput);

                if (returnInt > 0 && returnInt <= 4)
                {
                    validated = true;
                }
                else
                {
                    System.out.println("^^^^Not a menu option");
                // go round loop again
                }
            } catch (NumberFormatException e) {
                System.out.println("^^^^Incorect Input");
                // go round loop again
            }
        } while (!validated);

        return returnInt;
    }

}
