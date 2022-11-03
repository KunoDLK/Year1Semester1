package subscriptionmanager;

import java.util.Scanner;

/**
 * Methods to get specific inputs from user
 */
public class ConsoleMethods {

	public static Scanner scanner;

	// #region Pubic Methods

	/**
	 * Displays menu to the console, gets a validated response from console
	 * 
	 * @return user choice, int
	 */
	public static int RunMenu() {
		System.out.println();
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
	public static int GetValidatedInteger(int minInt, int MaxInt) {

		int returnInt = 0;
		boolean validated = false;

		do {
			try {

				String userInputStr = GetString();
				returnInt = Integer.parseInt(userInputStr);

				if (returnInt >= minInt && returnInt <= MaxInt) {
					return returnInt;
				} else {
					System.out.println("^^^^Not a option");
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
	public static int GetValidatedInteger(int[] validValues) {

		int returnInt = 0;
		boolean validated = false;

		do {
			try {

				String userInputStr = GetString();
				returnInt = Integer.parseInt(userInputStr);

				for (int value : validValues) {
					if (returnInt == value) {
						return returnInt;
					}
				}

				System.out.println("^^^^Not a option");
				// go round loop again

			} catch (NumberFormatException e) {

				System.out.println("^^^^Incorrect Input");
				// go round loop again
			}
		} while (!validated);

		return returnInt;
	}

	/**
	 * Get's a single char from console
	 * 
	 * @param validLetters the letters that are valid
	 * @returns inputted char
	 */
	public static char GetValidatedChar(char[] validLetters) {

		char returnChar = 0;
		boolean validated = false;

		do {
			try {

				String userInputStr = GetString();

				if (userInputStr.length() > 1) {
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

	/**
	 * @returns string from console
	 */
	public static String GetString() {
		return scanner.nextLine();
	}

	/**
	 * Displays subscription as defined in the spec
	 * 
	 * @param subscription sub to display
	 */
	public static void DisplaySubscription(Subscription subscription) {
		DrawHorizontalLine();
		DrawSubscriptionLine();

		String item = "Customer: " + subscription.GetName();
		DrawSubscriptionLine(item, "", HorizontalAlignment.Columned);

		DrawSubscriptionLine();

		item = "Date: " + subscription.GetStartDate();
		String secondItem = "Discount Code: " + subscription.GetDiscountCode();
		DrawSubscriptionLine(item, secondItem, HorizontalAlignment.Columned);

		item = "Package: " + subscription.GetSubPackage().name();
		secondItem = "Duration: " + subscription.GetDurationAsString();
		DrawSubscriptionLine(item, secondItem, HorizontalAlignment.Columned);

		item = "Terms: " + subscription.GetTermAsString();
		DrawSubscriptionLine(item, "", HorizontalAlignment.Columned);

		DrawSubscriptionLine();

		// Formate the cost value string so it displays nicely
		item = subscription.GetTermAsString() + " Subscription: £";
		String price = String.valueOf(subscription.GetCost());
		int decimalPointPos = price.indexOf('.');
		while (price.length() < decimalPointPos + 3) {
			price += "0";
		}
		item += price.substring(0, decimalPointPos + 3);

		DrawSubscriptionLine(item, "", HorizontalAlignment.Center);

		DrawSubscriptionLine();

		DrawHorizontalLine();
	}

	// #endregion Pubic Methods

	// #region Private Methods

	private static enum HorizontalAlignment {
		Center,
		Columned
	}

	private static void DrawHorizontalLine() {
		System.out.println("+===============================================+");
	}

	private static void DrawSubscriptionLine() {
		System.out.println("|                                               |");
	}

	private static void DrawSubscriptionLine(String firstItem, String secondItem, HorizontalAlignment alignment) {
		String stringToPrint = "|";

		switch (alignment) {

			case Center:
				for (int i = 0; i < (23 - firstItem.length() / 2); i++) {
					stringToPrint += " ";
				}
				stringToPrint += firstItem;
				break;

			case Columned:
				int centerPos = firstItem.indexOf(':');

				if (centerPos == -1) {

					System.out.println("ERROR: Unable to center text no ':' to center by");
					return;
				}

				for (int i = stringToPrint.length(); i < (10 - centerPos); i++) {
					stringToPrint += " ";
				}
				stringToPrint += firstItem;

				if (!secondItem.equals("")) {

					centerPos = secondItem.indexOf(':');

					if (centerPos == -1) {
						System.out.println("ERROR: Unable to center text no ':' to center by");
						return;
					}

					for (int i = stringToPrint.length(); i < (39 - centerPos); i++) {
						stringToPrint += " ";
					}
					stringToPrint += secondItem;
				}
				break;
		}

		while (stringToPrint.length() < 48) {
			stringToPrint += " ";
		}

		stringToPrint += "|";
		System.out.println(stringToPrint);
	}
	// #endregion Private Methods
}
