
import java.util.InputMismatchException;

import java.util.Scanner;

public class AssEx1 {

	public static void main(String[] args) {

		// a scanner to get user input
		Scanner s = new Scanner(System.in);

		System.out.print("Please enter your name: ");
		String name = s.nextLine();

		double balance = 0; // declare and initialise balance
		try {
			System.out.print("Please enter your current balance: £");
			balance = s.nextDouble(); // reassign variable to be next double entered by user
		} catch (InputMismatchException e) {
			System.out.println("You must enter a number, please try again: ");
			s.nextLine();
			balance = s.nextDouble(); // user has one chance to correct error
		}

		s.nextLine(); // so the scanner doesn't get stuck

		Printer.printAndSendtoFile("Hello " + name + ", your current balance is: £" + String.format("%5.2f", balance) + "\n"); //prints to console and to file

		// customer object made after receiving user input otherwise it asks to initialise and declare
		// variables
		CustomerAccount customerOne = new CustomerAccount(name, balance);

		String wineName; 
		boolean loop = true; //boolean initialised and declared in order to make an infinite loop
		while (loop = true) {
			System.out.print("Please enter wine name: ");
			wineName = s.nextLine(); 

			if (wineName.length() <= 0) {
				break;
			}

			double wineQuantity = 0;
			try {
				System.out.print("Please enter number of bottles (-ve for returns): ");
				wineQuantity = s.nextDouble();
			} catch (InputMismatchException e) {
				System.out.println("You must enter a number, please try again: ");
				s.nextLine();
				wineQuantity = s.nextDouble();
			}

			double pricePerBottle = 0;
			try {
				System.out.print("Please enter price per bottle: £");
				pricePerBottle = s.nextDouble();
			} catch (InputMismatchException e) {
				System.out.println("You must enter a number, please try again: ");
				s.nextLine();
				pricePerBottle = s.nextDouble();
			}
			s.nextLine();
			// wine object made
			Wine wine = new Wine(wineName, wineQuantity, pricePerBottle);

			// prints out processTransaction summary using user input which has been stored in customerOne and wine objects
			Printer.printAndSendtoFile(customerOne.doTransaction(wine));

		}

		s.close(); // close the scanner

	}

}
