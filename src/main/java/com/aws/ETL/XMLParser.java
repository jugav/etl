package com.aws.ETL;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class XMLParser 
{
    //Delimiter used in CSV file
    private static final String DELIMITER = ";";
    private static final String ORDER_FILE_HEADER = "Transaction Number; Active Price; Active Price Description; Department Code; Sub Department Code; Total Amount Paid; Total Units; Product Name, UPC Code";
    private static final String TRS_FILEHEADER = "Transaction Number for R; Transaction Number for T; InvoiceNumber; CustomerID; CustomerName; TransactionMode; TransactionStartDate; TransactionEndDate; TerminalCode; TerminalStoreCode; OperatorNumber; OperatorName";
    private static final String T_FILEHEADER = "Transaction Number; Totalizer Code; Ticket Concept; Total Amount; Total Unit; Total Weight";
    static List<TRS> trsList;
    
    public static String parseXML(String INPUT_PATH, String OUTPUT_PATH) {
        String csvfilepath = "";
        String filename2 = "";
        String csvoutput = "";
        
    	File dir = new File(INPUT_PATH);
        File[] directoryListing = dir.listFiles();
        
        if (directoryListing != null) {
          for (File child : directoryListing) {
        	  File dir2 = new File(INPUT_PATH+"\\"+child.getName());
              File[] directoryListing2 = dir2.listFiles();
              if (directoryListing2 != null) {
            	  for (File child2 : directoryListing2) {
                                           
                  //    if(!child2.getName().contains("001")){
                    	  try{
                    		    File fXmlFile = new File(INPUT_PATH+"\\"+child.getName()+"\\"+child2.getName());
                  				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                  				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                  				Document doc = dBuilder.parse(fXmlFile);
                  				doc.getDocumentElement().normalize();
                  	
                  			
                  				NodeList nodeList = doc.getElementsByTagName("trs");
                  				trsList = new ArrayList<TRS>();
                  		        for (int i = 0; i < nodeList.getLength(); i++) {
                  		                trsList.add(getTRS(nodeList.item(i)));
                  		        }
                  		        //TRS
                                
                                HSSFWorkbook workbook = null;
                                File file3 = new File(OUTPUT_PATH+"//"+child.getName());
                                    if (!file3.exists()) {
                                        if (file3.mkdir()) {
                                            System.out.println("Directory is created!");
                                        } else {
                                            System.out.println("Failed to create directory!");
                                        }
                                    }
                                    File file = new File(OUTPUT_PATH +"//"+child.getName()+"//"+child2.getName().substring(0, child2.getName().length() - 4)+"_"+child2.getName().substring(child2.getName().length() - 3)+".xls");
                                    csvfilepath = OUTPUT_PATH +"//"+child.getName()+"//"+child2.getName().substring(0, child2.getName().length() - 4)+"_"+child2.getName().substring(child2.getName().length() - 3)+".xls";
                                    filename2 = child.getName()+"_"+child2.getName().substring(0, child2.getName().length() - 4)+"_"+child2.getName().substring(child2.getName().length() - 3);
                                    csvoutput = OUTPUT_PATH +"//"+child.getName();
                                    
                                    FileOutputStream fileOut = new FileOutputStream(file);

                                    if (file.exists()) {
                                        workbook = new HSSFWorkbook();
                                        
                                    }
                                    else{
                                        
                                        try {
                                            workbook = (HSSFWorkbook)WorkbookFactory.create(file);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    CreationHelper createHelper = workbook.getCreationHelper();

                                    //cell style for hyperlinks
                                    //by default hyperlinks are blue and underlined
                                    CellStyle hlink_style = workbook.createCellStyle();
                                    Font hlink_font = workbook.createFont();
                                    hlink_font.setUnderline(Font.U_SINGLE);
                                    hlink_font.setColor(IndexedColors.BLUE.getIndex());
                                    hlink_style.setFont(hlink_font);
                                     
                                    
                                    HSSFSheet sheet = workbook.createSheet("Transaction");
                                    HSSFSheet my_sheet1 = workbook.createSheet("Item");
                                    HSSFSheet my_sheet = workbook.createSheet("Ticket");
                                  //sheet TRS
                                    Row row2 = sheet.createRow(0); 
                                    int ff2=0;
                                    String[] split2 = TRS_FILEHEADER.split(DELIMITER);
                                    for (int i = 0; i < split2.length; i++) {
                                        row2.createCell(i).setCellValue(split2[i]);
                                        ff2++;
                                    }
                                    char letter2 = (char)(ff2+64);

                                    int g2=1;

                                    int gr=1;
                                    int grsum = 0;
                                    
                                    int gt=1;
                                    int gtsum = 0;

                                    
                                    for (TRS trs: trsList) {
                                    	Row row = sheet.createRow(g2);
                                        
                                        
                                        //for R
                                        Cell cellr = row.createCell(0);
                                        cellr.setCellValue(new Double(trs.getTransactionNumber()));
                                        
                                        Hyperlink linkr = createHelper.createHyperlink(HyperlinkType.DOCUMENT);
                                        grsum = gr+1;
                                        linkr.setAddress("'Item'!A"+grsum);
                                        for ( int i =0; i< trs.getR().length ; i++) {
                                            
                                            gr++;
                                        }
                                        cellr.setHyperlink(linkr);
                                        cellr.setCellStyle(hlink_style);
                                      //for T
                                        Cell cell = row.createCell(1);
                                        cell.setCellValue(new Double(trs.getTransactionNumber()));
                                        
                                        Hyperlink link = createHelper.createHyperlink(HyperlinkType.DOCUMENT);
                                        gtsum = gt+1;
                                        link.setAddress("'Ticket'!A"+gtsum);
                                        for ( int i =0; i< trs.getT().length ; i++) {
                                            
                                            gt++;
                                        }
                                        cell.setHyperlink(link);
                                        cell.setCellStyle(hlink_style);
                                        try{
                                            row.createCell(2).setCellValue(Integer.parseInt(trs.getInvoiceNumber()));
                                        }
                                        catch(NumberFormatException ex){
                                            row.createCell(2).setCellValue("");
                                        }
                                        
                                        try{
                                        	row.createCell(3).setCellValue(Long.parseLong(trs.getCustomerID()));
                                        }catch(NumberFormatException ex){
                                        	row.createCell(3).setCellValue("");
                                        }
                                        row.createCell(4).setCellValue(String.valueOf(trs.getCustomerName()));
                                        row.createCell(5).setCellValue(String.valueOf(trs.getTransactionMode()));
                                        row.createCell(6).setCellValue(String.valueOf(trs.getTransactionStartDate()));
                                        row.createCell(7).setCellValue(String.valueOf(trs.getTransactionEndDate()));
                                        
                                        row.createCell(8).setCellValue(trs.getTerminalCode());
                                        if(trs.getTerminalStoreCode() != null) {
                                            row.createCell(9).setCellValue(Integer.parseInt(trs.getTerminalStoreCode()));
                                        } else {
                                        	row.createCell(9).setCellValue("");
                                        }
                                        row.createCell(10).setCellValue(trs.getOperatorNumber());
                                        row.createCell(11).setCellValue(String.valueOf(trs.getOperatorName()));
                                        g2++;
                                    }
                                    sheet.setAutoFilter(CellRangeAddress.valueOf("A1:"+letter2+g2));
                                    
                                    //sheet R
                                    Row row1 = my_sheet1.createRow(0); 
                                    int ff1=0;
                                    String[] split1 = ORDER_FILE_HEADER.split(DELIMITER);
                                    for (int i = 0; i < split1.length; i++) {
                                        row1.createCell(i).setCellValue(split1[i]);
                                        ff1++;
                                    }
                                    char letter1 = (char)(ff1+64);
                                    
                                    int g1=1;
                                    for (TRS trs: trsList) {
                                    	for ( int i =0; i< trs.getR().length ; i++) {
                                            Row row = my_sheet1.createRow(g1); 
                                            row.createCell(0).setCellValue(new Double(trs.getTransactionNumber()));
                                            try{
                                                row.createCell(1).setCellValue(new Double(trs.getR()[i].getItem().getActivePrice()));

                                            }catch(NumberFormatException ex){
                                            row.createCell(1).setCellValue("");
                                            }
                                            row.createCell(2).setCellValue(trs.getR()[i].getItem().getActivePriceDescription());
                                            row.createCell(3).setCellValue(trs.getR()[i].getItem().getDepartmentCode());
                                            row.createCell(4).setCellValue(trs.getR()[i].getItem().getSubDepartmentCode());
                                            
                                            row.createCell(5).setCellValue(new Double(trs.getR()[i].getItem().getTotalAmountPaid()));
                                            row.createCell(6).setCellValue(trs.getR()[i].getItem().getTotalUnits());
                                            row.createCell(7).setCellValue(String.valueOf(trs.getR()[i].getItem().getProductName()));
                                            row.createCell(8).setCellValue(String.valueOf(trs.getR()[i].getItem().getUPCCode()));
                                            g1++;
                                         }
                                    }
                                    my_sheet1.setAutoFilter(CellRangeAddress.valueOf("A1:"+letter1+g1));
                                    
                                    //sheet T
                                    Row row0 = my_sheet.createRow(0); 
                                    int ff=0;
                                    String[] split = T_FILEHEADER.split(DELIMITER);
                                    for (int i = 0; i < split.length; i++) {
                                        row0.createCell(i).setCellValue(split[i]);
                                        ff++;
                                    }
                                    char letter = (char)(ff+64);
                                    
                                    int g=1;
                                    for (TRS trs: trsList) {
                                    	 for ( int i =0; i< trs.getT().length ; i++) {
                                             Row row = my_sheet.createRow(g); 
                                             row.createCell(0).setCellValue(new Double(trs.getTransactionNumber()));
                                             row.createCell(1).setCellValue(new Double(trs.getT()[i].getTlz().get_totalizer_number()));
                                             row.createCell(2).setCellValue(String.valueOf(trs.getT()[i].getTlz().get_pos_desc()));
                                             row.createCell(3).setCellValue(new Double(trs.getT()[i].get_total_dollars()));
                                             if(trs.getT()[i].get_total_units() != null) {
                                                 row.createCell(4).setCellValue(new Double(trs.getT()[i].get_total_units()));
                                             }else {
                                            	 row.createCell(4).setCellValue("");
                                             }
                                     
                                             if(trs.getT()[i].get_total_weight() != null) {
                                            	 row.createCell(5).setCellValue(new Double(trs.getT()[i].get_total_weight()));
                                             } else {
                                            	 row.createCell(5).setCellValue("");
                                             }
                                             g++;
                                    	 }
                                    }
                                    my_sheet.setAutoFilter(CellRangeAddress.valueOf("A1:"+letter+g));
                                    
                                    workbook.write(fileOut);
                                    fileOut.close();
                                    
                                    String files[] = ExcelToCSV.convertToCSV(csvfilepath,filename2.replaceAll(" ", "_").toLowerCase()+"_", csvoutput, OUTPUT_PATH);
                    	  }
                    	  catch(Exception e) {
                  			e.printStackTrace();
                    	  }
                      }
            	//  }
              }
          }
      }
        return csvfilepath;
  }

	private static TRS getTRS(Node node) {
		TRS trs = new TRS();
		if (node.getNodeType() == Node.ELEMENT_NODE) {
	            Node element = node;
	           
	            if (getAttributeValue("F1032", element) != "" ) {
	            	trs.setTransactionNumber(Integer.parseInt(getAttributeValue("F1032", element)));
	            }
	            trs.setCustomerID(getAttributeValue("F1148", element));
	            trs.setCustomerName(getAttributeValue("F1155", element).replace(';', ' '));
                    trs.setInvoiceNumber(getAttributeValue("F1764", element));
                    trs.setOperatorName(getAttributeValue("F1127", element));
                if (getAttributeValue("F1126", element) != "" ) {
                	trs.setOperatorNumber(Integer.parseInt(getAttributeValue("F1126", element)));
                }
                if (getAttributeValue("F1057", element) != "" ) {
                	trs.setTerminalCode(Integer.parseInt(getAttributeValue("F1057", element)));
                }
	            trs.setTerminalStoreCode(getAttributeValue("F1056", element));
	            trs.setTransactionEndDate(getAttributeValue("F254", element));
	            trs.setTransactionStartDate(getAttributeValue("F253", element));
	            trs.setTransactionMode(getAttributeValue("F1068", element));
	            NodeList nodesList = element.getChildNodes();
	            
                    int rList = getRList(nodesList);
                            
	            Register r[] = new Register[rList] ;
	            int j =0;
	            for (int i = 0; i < nodesList.getLength() && j < rList; i++) {
	            	
	            	Node current = nodesList.item(i);
	            	 if (current.getNodeType() == Node.ELEMENT_NODE && current.getNodeName() == "r"){
	            			Register r1 = new Register();
	            				NodeList registerList = current.getChildNodes();
	            				boolean flag = false;
	            				for (int k = 0; k < registerList.getLength(); k++) {
	            					
	            					Node current1 = registerList.item(k);
	            			   	 	if (current1.getNodeType() == Node.ELEMENT_NODE && current1.getNodeName() == "itm"){
	            			   	 		r1.setItem(getItemValue(current1));
	            			   	 		r[j]=r1;
	            			   	 		j++;
	            			   	 		flag = true;
	            			   	 	}
	            			   	 	if (current1.getNodeType() == Node.ELEMENT_NODE && current1.getNodeName() == "F65" && flag == true){
	            			   	 		 r1.getItem().setTotalAmountPaid((current1.getTextContent()));
	            			   	 	}
	            				}
	            	 }
	            }
                    trs.setR(r);
                    
                    
                    int tList = getTList(nodesList);
                    
                    T t[] = new T[tList] ;
	            int p =0;
	            for (int q = 0; q < nodesList.getLength() && p < tList; q++) {
	            	
	            	Node current = nodesList.item(q);
	            	 if (current.getNodeType() == Node.ELEMENT_NODE && current.getNodeName() == "t"){
	            			T r2 = new T();
	            				NodeList registerList = current.getChildNodes();
	            				boolean flag = false;
	            				for (int k = 0; k < registerList.getLength(); k++) {
	            					
	            					Node current1 = registerList.item(k);
	            			   	 	if (current1.getNodeType() == Node.ELEMENT_NODE && current1.getNodeName() == "tlz"){
	            			   	 		r2.setTlz(getItemValue2(current1));
	            			   	 		t[p]=r2;
	            			   	 		p++;
	            			   	 		flag = true;
	            			   	 	}
                                                        if (current1.getNodeType() == Node.ELEMENT_NODE && current1.getNodeName() == "F65" && flag == true){

                                                                r2.set_total_dollars(current1.getTextContent());
                                                        }
                                                        if (current1.getNodeType() == Node.ELEMENT_NODE && current1.getNodeName() == "F67" && flag == true){

                                                                r2.set_total_weight(current1.getTextContent());
                                                        }
                                                        if (current1.getNodeType() == Node.ELEMENT_NODE && current1.getNodeName() == "F64" && flag == true){

                                                                r2.set_total_units(current1.getTextContent());
                                                        }       

	            				}
	            			   	 	
	            	 }
	            }
	            trs.setT(t);
	        }

	        return trs;
	}

	private static int getRList(NodeList nodesList) {
		int rList =0;
		 for (int i = 0; i < nodesList.getLength(); i++) {
         	Node current = nodesList.item(i);
         	 if (current.getNodeType() == Node.ELEMENT_NODE && current.getNodeName() == "r"){
         		 NodeList nodesList2 = current.getChildNodes();
         		 for (int k = 0; k < nodesList2.getLength(); k++) {
         			 Node current2 = nodesList2.item(k);
         			 if (current2.getNodeType() == Node.ELEMENT_NODE && current2.getNodeName() == "itm"){
         				 rList = rList+1;
         			 }
         		 }
         	 }
         }
		 return rList;
	}
        
        private static int getTList(NodeList nodesList) {
		int tList =0;
		 for (int i = 0; i < nodesList.getLength(); i++) {
         	Node current = nodesList.item(i);
         	 if (current.getNodeType() == Node.ELEMENT_NODE && current.getNodeName() == "t"){
         		 NodeList nodesList2 = current.getChildNodes();
         		 for (int k = 0; k < nodesList2.getLength(); k++) {
         			 Node current2 = nodesList2.item(k);
         			 if (current2.getNodeType() == Node.ELEMENT_NODE && current2.getNodeName() == "tlz"){
         				 tList = tList+1;
         			 }
         		 }
         	 }
         }
		 return tList;
	}

	private static Item getItemValue(Node element) {
		Item i = new Item();
		i.setActivePrice(getAttributeValue("F1007", element));
		i.setActivePriceDescription(getAttributeValue("F113", element));
		
		if(getAttributeValue("F03", element)!= "") {
			i.setDepartmentCode(Integer.parseInt(getAttributeValue("F03", element)));
		}
		
		i.setProductName(getAttributeValue("F02", element));
		if(getAttributeValue("F04", element)!= "") {
			i.setSubDepartmentCode(Integer.parseInt(getAttributeValue("F04", element)));
		} 
		if(getAttributeValue("F1006", element) != "") {
			i.setTotalUnits(Integer.parseInt(getAttributeValue("F1006", element)));
		
		}
		i.setUPCCode(getAttributeValue("F01", element));

		return i;
	}

        private static Tlz getItemValue2(Node element) {
		Tlz i = new Tlz();
		i.set_pos_desc(getAttributeValue("F02", element));
		i.set_totalizer_number(getAttributeValue("F1034", element));
		
		

		return i;
	}
        
        
	private static String getAttributeValue(String attrib, Node tempNode) {
		if (tempNode.hasAttributes()) {

			// get attributes names and values
			NamedNodeMap nodeMap = tempNode.getAttributes();


				try {
						Node node = nodeMap.getNamedItem(attrib);
						
						return node.getNodeValue();
				} catch(NullPointerException e){
					return "";
				}
				
			}
		
		return "";
	}
        
        


    

	private static int findRow(HSSFSheet sheet, String cellContent) {
    for (Row row : sheet) {
        for (Cell cell : row) {
            if (cell.getCellType() == CellType.STRING) {
                if (cell.getRichStringCellValue().getString().trim().equals(cellContent)) {
                    return row.getRowNum();  
                }
            }
        }
    }               
    return 0;
} 

        
     public static String getCellValueAsString(Cell cell) {
        String strCellValue = null;
        if (cell != null) {
            switch (cell.getCellType()) {
            case STRING:
                strCellValue = cell.toString();
                break;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat(
                            "dd/MM/yyyy");
                    strCellValue = dateFormat.format(cell.getDateCellValue());
                } else {
                    Double value = cell.getNumericCellValue();
                    Long longValue = value.longValue();
                    strCellValue = longValue.toString();
                }
                break;
            case BOOLEAN:
                strCellValue = new Boolean(cell.getBooleanCellValue()).toString();
                break;
            case BLANK:
                strCellValue = "";
                break;
                
                
            }
        }
        return strCellValue;
    }   
        
     
     public static int isNumber(String str) {
        int numeric = 0;

        if(str == "")
        {
            numeric = 1;
        }
        else
        {
            try {
                Double num = Double.parseDouble(str);
            } catch (NumberFormatException e) {
                numeric = 2;
            }

           
        }
        return numeric;
     }
        
}
