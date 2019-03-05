package com.aws.ETL;

public class Fact {
	private String PaymentMethod;
	private int AmountPaid;
	public String getPaymentMethod() {
		return PaymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		PaymentMethod = paymentMethod;
	}
	public int getAmountPaid() {
		return AmountPaid;
	}
	public void setAmountPaid(int amountPaid) {
		AmountPaid = amountPaid;
	}
	@Override
	public String toString() {
		return "Payment Method: " + getPaymentMethod() + " ,Amount Paid: " +getAmountPaid();
	}
}
