package com.aws.ETL;

public class Register {
	private Item item;
	private Fact fact;
	private Exception exception;
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public Fact getFact() {
		return fact;
	}
	public void setFact(Fact fact) {
		this.fact = fact;
	}
	public Exception getException() {
		return exception;
	}
	public void setException(Exception exception) {
		this.exception = exception;
	}
}
