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
		System.out.print(
				"1. Enter new Subscription\n2. Display Summary of subscriptions\n3. Display Summary of subscription for Selected Month\n4. Find and display subscription\n0. Exit\nInput Menu Number: ");

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

	/**
	 * @param validLetters
	 * @return
	 */
	public char GetValidatedChar(char[] validLetters) {
		
		char returnChar = 0;
		boolean validated = false;

		do {
			try {

				String userInputStr = scanner.nextLine();

				if (userInputStr.length() > 1)
				{
					System.out.println("^^^^Expected Single Character");
					continue;
				}
				userInputStr = userInputStr.toUpperCase();
				returnChar = userInputStr.charAt(0);

				for (int letter : validLetters) {
					if (returnChar == letter) {
						return returnChar;
					}
				}

				System.out.println("^^^^Not a valid option");
				// go round loop again

			} catch (StringIndexOutOfBoundsException e) {

				System.out.println("^^^^Incorrect Input");
				// go round loop again
			}
		} while (!validated);

		return returnChar;
	}

}
