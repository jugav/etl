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

		//PrintStream out ;
                PrintStream out2 ;
		String file[] = new String[wb.getNumberOfSheets()];
		int i = 0;
                
                File file3 = new File(csvoutput+"\\csv");
                if (!file3.exists()) {
                    if (file3.mkdir()) {
                        System.out.println("csv Directory is created!");
                    } else {
                        System.out.println("csv Failed to create directory!");
                    }
                }
                
                File file2 = new File(Outputpath+"\\csv");
                if (!file2.exists()) {
                    if (file2.mkdir()) {
                        System.out.println("csv Directory is created!");
                    } else {
                        System.out.println("csv Failed to create directory!");
                    }
                }
                                    
		for (Sheet sheet : wb) {
			out2 = new PrintStream(new FileOutputStream(Outputpath +"\\csv\\"+filename+sheet.getSheetName()+".csv"),
	                true, "UTF-8");
			file[i] = Outputpath + "\\csv\\"+filename+sheet.getSheetName()+".csv";
			for (Row row : sheet) {
				boolean firstCell = true;
				for (Cell cell : row) {
					if ( ! firstCell )out2.print(';'); 
                          String text = formatter.formatCellValue(cell);
					      out2.print(text);
					      firstCell = false;
				}
				out2.print(';'); 
                                out2.println();
			}
			i++;
		}
		return file;
	}
	
}
