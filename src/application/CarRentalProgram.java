package application;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

import javax.print.attribute.standard.RequestingUserName;

import entities.CarRental;
import entities.Vehicle;
import services.BrazilTaxService;
import services.RentalService;

public class CarRentalProgram {

	public static void main(String[] args) {

		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

		System.out.println("Enter rental data");
		System.out.print("Car model: ");
		String model = sc.nextLine();
		System.out.print("Pickup (dd/MM/yyyy hh:mm): ");
		LocalDateTime date1 = LocalDateTime.parse(sc.nextLine(), dtf);
		System.out.print("Return (dd/MM/yyyy hh:mm): ");
		LocalDateTime date2 = LocalDateTime.parse(sc.nextLine(), dtf);
		
		CarRental cr = new CarRental(date1, date2, new Vehicle(model));
		
		System.out.print("Enter price per hour: ");
		double pricePerHour = sc.nextDouble();
		System.out.print("Enter price per day: ");
		double pricePerDay = sc.nextDouble();
		
		RentalService rentalService = new RentalService(pricePerHour, pricePerDay, new BrazilTaxService());
		
		rentalService.processInvoice(cr);
	
		System.out.println("\nINVOICE: ");
		System.out.println("Basic Payment: " + String.format("%.2f", cr.getInvoice().getBasicPayment()));
		System.out.println("Tax: " + String.format("%.2f", cr.getInvoice().getTax()))	;
		System.out.println("Total Payment: " + String.format("%.2f", cr.getInvoice().getTotalPayment()));
	
		
		
		sc.close();
	}
}