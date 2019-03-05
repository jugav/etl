package com.aws.ETL;

public class Item {
	private String ActivePrice;
	private String ActivePriceDescription;
	private int DepartmentCode;
	private int SubDepartmentCode;
	private String TotalAmountPaid;
	private int TotalUnits;
	private String ProductName;
	private String UPCCode;
	public String getActivePrice() {
		return ActivePrice;
	}
	public void setActivePrice(String activePrice) {
		ActivePrice = activePrice;
	}
	public String getActivePriceDescription() {
		return ActivePriceDescription;
	}
	public void setActivePriceDescription(String activePriceDescription) {
		ActivePriceDescription = activePriceDescription;
	}
	public int getDepartmentCode() {
		return DepartmentCode;
	}
	public void setDepartmentCode(int departmentCode) {
		DepartmentCode = departmentCode;
	}
	public int getSubDepartmentCode() {
		return SubDepartmentCode;
	}
	public void setSubDepartmentCode(int subDepartmentCode) {
		SubDepartmentCode = subDepartmentCode;
	}
	public String getTotalAmountPaid() {
		return TotalAmountPaid;
	}
	public void setTotalAmountPaid(String totalAmountPaid) {
		TotalAmountPaid = totalAmountPaid;
	}
	public int getTotalUnits() {
		return TotalUnits;
	}
	public void setTotalUnits(int totalUnits) {
		TotalUnits = totalUnits;
	}
	public String getProductName() {
		return ProductName;
	}
	public void setProductName(String productName) {
		ProductName = productName;
	}
	public String getUPCCode() {
		return UPCCode;
	}
	public void setUPCCode(String uPCCode) {
		UPCCode = uPCCode;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return " ,Item- ActivePriceDescription:" + getActivePriceDescription() + " ,DescriptionCode:" + getDepartmentCode() +",Sub Department Code: " + getSubDepartmentCode()+ " ,Total Amount Paid:" + getTotalAmountPaid() + " ,Total Units:" + getTotalUnits() + " ,Product Name:" + getProductName() + " ,UPC Code:" + getUPCCode();
	}
}
