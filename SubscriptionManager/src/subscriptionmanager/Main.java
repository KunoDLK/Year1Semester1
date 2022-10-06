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

                default:
                    System.out.println("Menu Item: " + menuChoice);
                    break;
            }
        } while (RunLoop);
    }
}
