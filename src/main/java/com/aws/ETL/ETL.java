package com.aws.ETL;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class ETL 
{    //for output folder
    protected static String OUTPUT_PATH;
    //for input folder
    protected static String INPUT_PATH;
    
    protected static String AWS_ACCESS_KEY_ID;
    protected static String AWS_SECRET_KEY;
    protected static String REDSHIFT_URL;
    protected static String AWS_USERNAME;
    protected static String AWS_PASSWORD;
    protected static String S3_BUCKET;
    public static void main(String[] args) throws IOException{
    	ETL etl = new ETL();
    	etl.init();
        
        //Parse XML
    	System.out.println("parsing XML files");
    	XMLParser.parseXML(INPUT_PATH, OUTPUT_PATH);
        
        
        System.out.println("Uploading the parsed file to s3 bucket");
        File dir = new File(OUTPUT_PATH+"\\csv\\");
        File[] directoryListing = dir.listFiles();
        int fileCount=dir.list().length;
        
        String files[] = new String[fileCount];
        int i=0;
        if (directoryListing != null) {
          for (File child : directoryListing) {
                     files[i] = OUTPUT_PATH+"\\csv\\"+child.getName();
                     i++;
          }
            UploadS3.uploadFiles(files);
        }

        System.out.println("Load data to redshift");
        S3ToRedshift.ConnectToCluster(OUTPUT_PATH);
    	
    }
    public void init() throws IOException {
    	Properties mainProperties = new Properties();
    	FileInputStream file;

        //the base folder is ./, the root of the main.properties file  
        String path = "./configuration.properties";
        
      //load the file handle for main.properties
        file = new FileInputStream(path);
        
      //load all the properties from this file
        mainProperties.load(file);
        file.close();
        
       //read output file path and aws credentials from file
        OUTPUT_PATH = mainProperties.getProperty("etl.output_path");
        INPUT_PATH = mainProperties.getProperty("etl.input_path");
        AWS_ACCESS_KEY_ID = mainProperties.getProperty("etl.aws.access_key_id");
        AWS_SECRET_KEY = mainProperties.getProperty("etl.aws.secret_key");
        REDSHIFT_URL = mainProperties.getProperty("etl.aws.db_url");
        AWS_USERNAME = mainProperties.getProperty("etl.aws.username");
        AWS_PASSWORD = mainProperties.getProperty("etl.aws.password");
        S3_BUCKET = mainProperties.getProperty("etl.aws.s3_bucket");
    }

}