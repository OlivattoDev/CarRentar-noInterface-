package services;

import java.time.Duration;

import entities.CarRental;
import entities.Invoice;

public class RentalService {

	private Double PricePerHour;
	private Double PricePerDay;
	
	private BrazilTaxService brasilTaxService;

	public RentalService(Double pricePerHour, Double pricePerDay, BrazilTaxService brasilTaxService) {
		PricePerHour = pricePerHour;
		PricePerDay = pricePerDay;
		this.brasilTaxService = brasilTaxService;
	}
	
	public void processInvoice(CarRental carRental) {
		
		double minutes = Duration.between(carRental.getStart(), carRental.getFinish()).toMinutes();
		double hours = minutes / 60.0;
		
		double basicPayment;
		if(hours <= 12.0) {
			basicPayment = PricePerHour * Math.ceil(hours);
		}
		
		else {
			basicPayment = PricePerDay * Math.ceil(hours / 24.0);
		}
		
		double tax = brasilTaxService.tax(basicPayment);
		
		carRental.setInvoice(new Invoice(basicPayment, tax));
	}
}