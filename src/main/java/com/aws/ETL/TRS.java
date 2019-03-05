package com.aws.ETL;


public class TRS {
	private int TransactionNumber;
	private String InvoiceNumber;
	private String CustomerID;
	private String CustomerName;
	private String TransactionStartDate;
	private String TransactionEndDate;
	private int TerminalCode;
	private String TerminalStoreCode;
	private int OperatorNumber;
	private String OperatorName;
	private String TransactionMode;
	private Register[] R;
        private T[] T;
	
	public int getTransactionNumber() {
		return TransactionNumber;
	}
	
	public void setTransactionNumber(int transactionNumber) {
		TransactionNumber = transactionNumber;
	}
	
	public String getInvoiceNumber() {
		return InvoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		InvoiceNumber = invoiceNumber;
	}
	
	public String getCustomerID() {
		return CustomerID;
	}
	public void setCustomerID(String customerID) {
		CustomerID = customerID;
	}
	
	public String getCustomerName() {
		return CustomerName;
	}
	public void setCustomerName(String customerName) {
		CustomerName = customerName;
	}
	//allow only two values
	public String getTransactionMode() {
		return TransactionMode;
	}
	public void setTransactionMode(String transactionMode) {
		TransactionMode = transactionMode;
	}
	public String getTransactionStartDate() {
		return TransactionStartDate;
	}
	public void setTransactionStartDate(String transactionStartDate) {
		TransactionStartDate = transactionStartDate;
	}
	public String getTransactionEndDate() {
		return TransactionEndDate;
	}
	public void setTransactionEndDate(String transactionEndDate) {
		TransactionEndDate = transactionEndDate;
	}
	public int getTerminalCode() {
		return TerminalCode;
	}
	public void setTerminalCode(int terminalCode) {
		TerminalCode = terminalCode;
	}
	public String getTerminalStoreCode() {
		return TerminalStoreCode;
	}
	public void setTerminalStoreCode(String terminalStoreCode) {
		TerminalStoreCode = terminalStoreCode;
	}
	public String getOperatorName() {
		return OperatorName;
	}
	public void setOperatorName(String operatorName) {
		OperatorName = operatorName;
	}
	public int getOperatorNumber() {
		return OperatorNumber;
	}
	public void setOperatorNumber(int operatorNumber) {
		OperatorNumber = operatorNumber;
	}
	
	
	
	@Override
	public String toString() {
		return "TRS - Transaction Number:" + getTransactionNumber() + " ,InvoiceNumber:" + getInvoiceNumber() + " ,CustomerID:" + getCustomerID() + " ,Customer Name:" + getCustomerName() + " ,Transaction Start Date:" + getTransactionStartDate() + " ,Transaction End Date:" + getTransactionEndDate() + " ,Terminal Code:" +  getTerminalCode() + " ,Terminal Store Code:" +  getTerminalStoreCode() + " ,Operator Number:" + getOperatorNumber() + " ,Operator Name:" + getOperatorName() + " ,TransactionMode:" + getTransactionMode()   ;
	}

	public Register[] getR() {
		return R;
	}
        

	public void setR(Register[] r) {
		R = r;
	}
        
        public T[] getT() {
		return T;
	}

	public void setT(T[] t) {
		T = t;
	}
}
