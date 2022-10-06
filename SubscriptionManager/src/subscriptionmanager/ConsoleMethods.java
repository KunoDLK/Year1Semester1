package subscriptionmanager;

import java.util.Scanner;

public class ConsoleMethods {

        public Scanner scanner;

        /**
         * constructor for ConsoleMethods class 
         */
        public ConsoleMethods() {

                scanner = new Scanner(System.in);
        }

        /**
         * Displays menu to the console, gets a validated response from console
         * 
         * @return user choice, int
         */
        public int RunMenu() {
                System.out.println(
                                "1. Enter new Subscription\n2. Display Summary of subscriptions\n3. Display Summary of subscription for Selected Month\n4. Find and display subscription\n0. Exit");

                int menuOption = GetValidatedInteger(0, 4);
                return menuOption;
        }

        
        /**
         * Get's a validated integer value from the console
         * 
         * @param minInt lowest incuded integer value
         * @param MaxInt Highest incuded integer value
         * @return valided integer
         */
        public int GetValidatedInteger(int minInt, int MaxInt) {

                int returnInt = 0;
                boolean validated = false;

                do {
                        try {

                                String userinput = scanner.nextLine();
                                returnInt = Integer.parseInt(userinput);

                                if (returnInt >= minInt && returnInt <= MaxInt) {
                                        validated = true;
                                } else {
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
