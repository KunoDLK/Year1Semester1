package subscriptionmanager;

import java.util.Scanner;

/**
 * Methods to get specific inputs from user
 */
public class ConsoleMethods {

	public static Scanner scanner;

	// #region Pubic Methods

	/**
	 * Displays a menu of options to the console and gets a validated response from
	 * the user.
	 * 
	 * @return The user's selected menu option, as an integer.
	 */
	public static int RunMenu() {
		// Print menu options
		System.out.println();
		System.out.println("1. Enter new Subscription");
		System.out.println("2. Display Summary of subscriptions");
		System.out.println("3. Display Summary of subscription for Selected Month");
		System.out.println("4. Find and display subscription");
		System.out.println("0. Exit");
		System.out.print("Input Menu Number: ");

		// Get and return validated user input
		return GetValidatedInteger(0, 4);
	}

	/**
	 * Prompts the user for input and parses it as an integer.
	 * The method continues to prompt the user until a valid
	 * integer within the specified range is entered.
	 *
	 * @param minInt the minimum valid integer
	 * @param maxInt the maximum valid integer
	 * @return the user's integer
	 */
	public static int GetValidatedInteger(int minInt, int MaxInt) {

		int returnInt = 0;

		while (true) {
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
		}
	}

	/**
	 * Get's a validated integer value from the console
	 * 
	 * @param validValues The possible valid values
	 * @return validated integer
	 */
	public static int GetValidatedInteger(int[] validValues) {
		int returnInt = 0;

		while (true) {
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
		}
	}

	/**
	 * Get's a single char from console
	 * 
	 * @param validLetters the letters that are valid
	 * @return inputted char
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
	 * @return string from console
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

	/**
	 * Draws a horizontal line on the console.
	 */
	private static void DrawHorizontalLine() {
		System.out.println("+===============================================+");
	}

	/**
	 * Draws a subscription line on the console.
	 */
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
