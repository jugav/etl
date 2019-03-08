package com.aws.ETL;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.redshift.AmazonRedshift;
import com.amazonaws.services.redshift.AmazonRedshiftClientBuilder;
import java.io.File;

import java.sql.*;
import java.util.Properties;

public class S3ToRedshift {
	public static void ConnectToCluster(String Outputpath) {	     
	     Connection conn = null;
	     Statement stmt = null;
	     try{
	           //Dynamically load driver at runtime.
	           //Redshift JDBC 4 driver: com.amazon.redshift.jdbc4.Driver
	           Class.forName("com.amazon.redshift.jdbc4.Driver");

	           //Open a connection and define properties.
	           System.out.println("Connecting to database...");
	           Properties props = new Properties();

	           //Uncomment the following line if using a keystore.
	           //props.setProperty("ssl", "true");
	           props.setProperty("user", ETL.AWS_USERNAME);
	           props.setProperty("password", ETL.AWS_PASSWORD);
	           conn = DriverManager.getConnection(ETL.REDSHIFT_URL, props);

               String sql="";
               String sql2="";
	             
	           String command = " CREDENTIALS     'aws_access_key_id="+ ETL.AWS_ACCESS_KEY_ID +";aws_secret_access_key="+ ETL.AWS_SECRET_KEY +"' IGNOREHEADER AS 1 ESCAPE ACCEPTINVCHARS DELIMITER ';' FILLRECORD REGION 'us-east-2';";
	           
               File dir = new File(Outputpath+"\\csv\\");
               File[] directoryListing = dir.listFiles();

               if (directoryListing != null) {
                      for (File child : directoryListing) {
                        String filename = child.getName().substring(0, child.getName().length() - 4);
                            if(filename.contains("Item"))
                            {
                                sql="CREATE TABLE "+filename+" \n" +
                                "(\n" +
                                "    TransactionNumber INTEGER not null,\n" +
                                "    ActivePrice DOUBLE PRECISION,\n" +
                                "    ActivePriceDescription VARCHAR(100),\n" +
                                "    DepartmentCode INTEGER,\n" +
                                "    SubDepartmentCode VARCHAR(100), \n" +
                                "    TotalAmountPaid DOUBLE PRECISION,\n" +
                                "    TotalUnits INTEGER,\n" +
                                "    ProductName VARCHAR(100),\n" +
                                "    UPCCode VARCHAR(100)\n" +
                                ");";
                                
                                sql2="copy "+filename+" from 's3://"+ETL.S3_BUCKET+"/"+filename+"'"+command;
                            }
                            if(filename.contains("Ticket"))
                            {
                                sql="CREATE TABLE "+filename+" \n" +
                                "(\n" +
                                "TransactionNumber INTEGER not null,\n" +
                                "TotalizerCode INTEGER,\n" +
                                "TicketConcept VARCHAR(50),\n" +
                                "TotalUnit  DOUBLE PRECISION,\n" +
                                "TotalAmount DOUBLE PRECISION,\n" +
                                "TotalWeight DOUBLE PRECISION\n" +
                                ");";
                                
                                sql2="copy "+filename+" from 's3://"+ETL.S3_BUCKET+"/"+filename+"' "+command;
                            }
                            if(filename.contains("Transaction"))
                            {
                                sql="CREATE TABLE "+filename+" (\n" +
                                "	  RTransactionNumber INTEGER not null,\n" +
                                "	  TTransactionNumber INTEGER not null,\n" +
                                "      InvoiceNumber INTEGER,\n" +
                                "      CustomerID BIGINT,\n" +
                                "      CustomerName VARCHAR(100),\n" +
                                "      TransactionMode VARCHAR(22),\n" +
                                "      TransactionStartDate date,\n" +
                                "      TransactionEndDate date,\n" +
                                "      TerminalCode INTEGER,\n" +
                                "      TerminalStoreCode INTEGER,\n" +
                                "      OperatorNumber INTEGER,\n" +
                                "      OperatorName VARCHAR(100)\n" +
                                ");";
                                
                                sql2="copy "+filename+" (RTransactionNumber, TTransactionNumber, InvoiceNumber, CustomerID, CustomerName , TransactionMode ,TransactionStartDate ,TransactionEndDate, TerminalCode ,TerminalStoreCode ,OperatorNumber , OperatorName ) from 's3://"+ETL.S3_BUCKET+"/"+filename+"' DATEFORMAT AS 'YYYY-MM-DD' "+command;
                                
                            }

                        
                        
                        stmt = conn.createStatement();
                        int j =  stmt.executeUpdate(sql);
                        System.out.println("Create Table Response : " + j);

                        int j2 =  stmt.executeUpdate(sql2);
                        System.out.println("COPY contents response : "+ j2);
                        
                        }
                    

                    }
                   stmt.close();
	               conn.close();
	      }catch(Exception ex){
	    	  //For convenience, handle all errors here.,
	    	  ex.printStackTrace();
	      }finally{
	    	  //Finally block to close resources.
	    	  try{
	    		  if(stmt!=null)
                            stmt.close();
	    	  }catch(Exception ex){
	    	  }// nothing we can do
	    	  try{
	    		  if(conn!=null)
                            conn.close();
	    	  }catch(Exception ex){
	    		  ex.printStackTrace();
	    	  }
	      }
	}
	public static void readBucket() {
		String endpoint = "https://redshift.us-east-2.amazonaws.com/";
		String region = "us-east-2";
		AwsClientBuilder.EndpointConfiguration config = new AwsClientBuilder.EndpointConfiguration(endpoint, region);
		AmazonRedshiftClientBuilder clientBuilder = AmazonRedshiftClientBuilder.standard();
                clientBuilder.setEndpointConfiguration(config);
                AmazonRedshift client = clientBuilder.build();

		//Create CLuster ??
		//Cluster already exists
		
              
	}
}   
