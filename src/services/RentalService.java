package services;

import java.time.Duration;

import entities.CarRental;
import entities.Invoice;

public class RentalService {

	private Double PricePerHour;
	private Double PricePerDay;
	
	private TaxService taxService;

	public RentalService(Double pricePerHour, Double pricePerDay, TaxService taxService) {
		PricePerHour = pricePerHour;
		PricePerDay = pricePerDay;
		this.taxService = taxService;
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
		
		double tax = taxService.tax(basicPayment);
		
		carRental.setInvoice(new Invoice(basicPayment, tax));
	}
}