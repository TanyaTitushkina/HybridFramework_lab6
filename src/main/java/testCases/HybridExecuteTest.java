package testCases;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import excelExportAndFileIO.ReadGuru99ExcelFile;
import operation.ReadObject;
import operation.UIOperation;

public class HybridExecuteTest {

	protected Logger log ;//= LogManager.getLogger(this.getClass().getSimpleName());
	private static LocalDateTime timePoint = LocalDateTime.now();
	private String dateTime = timePoint.get(ChronoField.YEAR_OF_ERA) + "-" + timePoint.getMonth() + "-" + timePoint.getDayOfMonth() + "_"+ timePoint.getHour() + "-" + timePoint.getMinute() + "-" + timePoint.getSecond();
	private String testName= getClass().getSimpleName();

	WebDriver webdriver = null;
    @Test(dataProvider="hybridData")
	public void testLogin(String testcaseName,String keyword,String objectName,String objectType,String value) throws Exception {

		String logPath = "logs/" + testName + "_" + dateTime + "/" ;
		String logFileName = logPath + testName + "_" + dateTime + ".log";
		System.setProperty("logPath", logPath);
		System.setProperty("logFileName", logFileName);
		log = LogManager.getLogger(this.getClass().getSimpleName());
		log.info(this.getClass().getSimpleName() + " test started");

    	if(testcaseName!=null && testcaseName.length()!=0){
    		webdriver = new FirefoxDriver();
    	}
        ReadObject object = new ReadObject();
        Properties allObjects =  object.getObjectRepository();
        UIOperation operation = new UIOperation(webdriver);

      	//Call perform function to perform operation on UI
    	operation.perform(allObjects, keyword, objectName, objectType, value);
	}

    @DataProvider(name="hybridData")
	public Object[][] getDataFromDataprovider() throws IOException{
    	Object[][] object = null; 
    	ReadGuru99ExcelFile file = new ReadGuru99ExcelFile();
        
         //Read keyword sheet
         Sheet guru99Sheet = file.readExcel(System.getProperty("user.dir"),"TestCase.xlsx" , "KeywordFramework");
       //Find number of rows in excel file	
     	int rowCount = guru99Sheet.getLastRowNum()-guru99Sheet.getFirstRowNum();
     	object = new Object[rowCount][5];
     	for (int i = 0; i < rowCount; i++) {
    		//Loop over all the rows
    		Row row = guru99Sheet.getRow(i+1);
    		//Create a loop to print cell values in a row
    		for (int j = 0; j < row.getLastCellNum(); j++) {
    			//Print excel data in console
    			object[i][j] = row.getCell(j).toString();
    		}
    	}
     	System.out.println("");
     	  return object;	 
	}
}
