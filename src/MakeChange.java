import java.util.Scanner;

public class MakeChange {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		System.out.println("Welcome to the Cash Register!");
		boolean isOpen = true;

		do { // start register loop
			System.out.print("\nPlease enter the purchase price: $");
			double price = sc.nextDouble();
			System.out.print("Please enter the the amount tendered by the customer: $");
			double payment = sc.nextDouble();

			if (price < 0 || payment < 0) {
				System.out.println("\nInvalid entry.");
			} 
			else if (price > payment) {
				underpaid(price, payment); //calculates and displays amount outstanding b/w price and payment  
				isOpen = determineOpen(sc); //asks user if they want to continue
			} 
			else if (price == payment) {
				System.out.println("\n> Customer paid the exact purchase price. No change is due.");
				isOpen = determineOpen(sc); //asks user if they want to continue
			} 
			else {

				double change = changeTotal(price, payment);
				System.out.println("> This cash combination makes their change:\n");

				//assigns a variable to each bill/coin denomination IF greater than the running change total,
				//starting from largest to smallest denominations;
				//then executes methods to count an appropriate quantity of the denomination,
				//display a statement with denomination quantity and type,
				//and update the running change total to evaluate next lower denomination
				if (change >= 100) {
					double interval = 100;
					change = makeChange(interval, change);
				}
				if (change >= 50) {
					double interval = 50;
					change = makeChange(interval, change);
				}
				if (change >= 20) {
					double interval = 20;
					change = makeChange(interval, change);
				}
				if (change >= 10) {
					double interval = 10;
					change = makeChange(interval, change);
				}
				if (change >= 5) {
					double interval = 5;
					change = makeChange(interval, change);
				}
				if (change >= 1) {
					double interval = 1;
					change = makeChange(interval, change);
				}
				if (change >= 0.25) {
					double interval = 0.25;
					change = makeChange(interval, change);
				}
				if (change >= 0.10) {
					double interval = 0.10;
					change = makeChange(interval, change);
				}
				if (change >= 0.05) {
					double interval = 0.05;
					change = makeChange(interval, change);
				}
				if (change >= 0.01) {
					double pennyCount = (double) change / .01;

					int pennyPrint= (int) (pennyCount); //casting count value to display clearly as a quantity
					if (pennyPrint > 1) {
						System.out.println("\t" + pennyPrint + " pennies");
					} else {
						System.out.println("\t" + pennyPrint + " penny");
					}
				}
				isOpen = determineOpen(sc); //asks user if they want to continue
			} 

		} while (isOpen); // end register loop

		System.out.println("\nGoodbye.");
		sc.close();

	} // close main

	public static void underpaid(double price, double payment) {
		double stillOwing = (Math.round((price - payment) * 100.00)) / 100.00;
		/*
		 * since integers display as truncated to the tenth's decimal when their
		 * hundredth's place is zero (e.g. .50 cents displays as .5), this if/else
		 * determines if the remainder due is a multiple of .10, and if it is,
		 * concatenates a "0" to appear like currency
		 */
		if (((stillOwing * 100) % 10 == 0)) {
			System.out.println("\n> Insufficient funds. The customer needs to pay $" + (stillOwing)
					+ "0 more to reach the purchase price.");
		} else {
			System.out.println("\n> Insufficient funds. The customer needs to pay $" + (stillOwing)
					+ " more to reach the purchase price.");
		}
	}

	public static double changeTotal(double price, double payment) {
		double change = (Math.round((payment - price) * 100.00)) / 100.00;
		// same logic as stillOwing, to clearly display as currency
		if ((change * 100) % 10 == 0) {
			System.out.println("\n> The change due to the customer is $" + change + "0.");
		} else {
			System.out.println("\n> The change due to the customer is $" + change + ".");
		}
		return change;
	}

	public static double makeChange(double interval, double change) {
		double count = countDenom(interval, change);
		printDenomCount(interval, change, count);
		change = updateChange(interval, change, count);
		return change;
	}

	// this method counts the max quantity of a whole bill/coin w/i remaining change balance
	// interval corresponds to the bill/coin value
	public static double countDenom(double interval, double change) {
		double count = 0;
		for (double i = interval; i <= change; i += interval) {
			count += 1;
		}
		return count;
	}

	// this method prints the quantity and name of a bill/coin that contributes to
	// the change;
	// also deducts that value from the running count for the remaining change
	public static void printDenomCount(double interval, double change, double count) {
		int denomType = (int) (interval * 100); // interval converted to integer so switch will accept variable;
		// multiplied by 100 to maintain decimal data correlated to bill/coin
		String denomSpelledOut = "";
		int denomPrint = (int) (count); //casting count value to display clearly as a quantity

		switch (denomType) {
		case 10000:
			denomSpelledOut = "hundred dollar bill";
			break;
		case 5000:
			denomSpelledOut = "fifty dollar bill";
			break;
		case 2000:
			denomSpelledOut = "twenty dollar bill";
			break;
		case 1000:
			denomSpelledOut = "ten dollar bill";
			break;
		case 500:
			denomSpelledOut = "five dollar bill";
			break;
		case 100:
			denomSpelledOut = "one dollar bill";
			break;
		case 25:
			denomSpelledOut = "quarter";
			break;
		case 10:
			denomSpelledOut = "dime";
			break;
		case 5:
			denomSpelledOut = "nickel";
			break;
		}
		String printChange = "\t" + denomPrint + " " + denomSpelledOut;
		if (denomPrint > 1) { // determines if bill/coin quantity is plural or single and displays concatenated "s" correspondingly
			System.out.println(printChange + "s");
		} else {
			System.out.println(printChange);
		}
	}

	public static double updateChange(double interval, double change, double count) {
		double remngChange = (Math.round((change - (count * interval)) * 100.00)) / 100.00;
		return remngChange;
	}

	public static boolean determineOpen(Scanner sc) {
		boolean bool = true;
		boolean stayOpen = true;

		do { // loops until user provides a valid response
			System.out.print("\nDo you want to complete another transaction (Y/N)? ");
			String keepOpen = sc.next();

			switch (keepOpen) {
			case "Y":
			case "y":
			case "Yes":
			case "YES":
			case "yes":
				bool = false;
				break;
			case "N":
			case "n":
			case "No":
			case "NO":
			case "no":
				bool = false;
				stayOpen = false;
				break;
			default:
				System.out.println("\nInvalid response.");
			}
		} while (bool);
		return stayOpen;
	}
}