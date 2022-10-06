package subscriptionmanager;

import java.util.Scanner;

import subscriptionmanager.Subscription.SubPackages;

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
	 * @param minInt lowest included integer value
	 * @param MaxInt Highest included integer value
	 * @return validated integer
	 */
	public int GetValidatedInteger(int minInt, int MaxInt) {

		int returnInt = 0;
		boolean validated = false;

		do {
			try {

				String userInputStr = scanner.nextLine();
				returnInt = Integer.parseInt(userInputStr);

				if (returnInt >= minInt && returnInt <= MaxInt) {
					return returnInt;
				} else {
					System.out.println("^^^^Not a menu option");
					// go round loop again
				}

			} catch (NumberFormatException e) {

				System.out.println("^^^^Incorrect Input");
				// go round loop again
			}
		} while (!validated);

		return returnInt;
	}

	/**
	 * Get's a validated integer value from the console
	 * 
	 * @param validValues The possible valid values
	 * @return validated integer
	 */
	public int GetValidatedInteger(int[] validValues) {

		int returnInt = 0;
		boolean validated = false;

		do {
			try {

				String userInputStr = scanner.nextLine();
				returnInt = Integer.parseInt(userInputStr);

				for (int value : validValues) {
					if (returnInt == value) {
						return returnInt;
					}
				}

				System.out.println("^^^^Not a menu option");
				// go round loop again

			} catch (NumberFormatException e) {

				System.out.println("^^^^Incorrect Input");
				// go round loop again
			}
		} while (!validated);

		return returnInt;
	}

	public SubPackages GetValidatedChar(char[] packageletters) {
		return null;
	}

}
