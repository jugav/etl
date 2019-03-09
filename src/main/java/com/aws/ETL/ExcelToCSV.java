package com.aws.ETL;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.opencsv.CSVWriter;

public class ExcelToCSV {
	private static final char COMMA_DELIMITER = ';';
	public static void convertToCSV(String xlsxFile,String filename, String csvoutput, String Outputpath) throws InvalidFormatException, IOException {

		Workbook wb = WorkbookFactory.create(new File(xlsxFile));

		DataFormatter formatter = new DataFormatter();
          
          File file2 = new File(Outputpath+"\\csv");
          if (!file2.exists()) {
              if (file2.mkdir()) {
                  System.out.println("csv Directory is created!");
              } else {
                  System.out.println("csv Failed to create directory!");
              }
          }
          FileWriter fileWriter ;
          CSVWriter csvwriter ;
		for(Sheet sheet : wb) {
			File f = new File(Outputpath + "\\csv\\"+filename+sheet.getSheetName()+".csv");
			 fileWriter = new FileWriter(f);
			 csvwriter = new CSVWriter(fileWriter, COMMA_DELIMITER, CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
				
	        for (Row row : sheet) {
	        	String nextLine[] = new String[row.getPhysicalNumberOfCells()];
	        	int k=0;
	            for (Cell cell : row) {
	            	String cellValue = formatter.formatCellValue(cell);
	            	nextLine[k] = cellValue;
	            	k++;
	            }
	            csvwriter.writeNext(nextLine);
	        }
	        try {
                csvwriter.close();
            } catch (IOException e) {
                System.out.println("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();
            }
            
        }
	}
	
}
