package operation;

import java.util.Properties;

import core.Helpers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UIOperation {

	WebDriver driver;
	protected Logger log = LogManager.getLogger(this.getClass().getSimpleName());

	public UIOperation(WebDriver driver){
		this.driver = driver;
	}
	public void perform(Properties p,String operation,String objectName,String objectType,String value) throws Exception{
		System.out.println("");
		switch (operation.toUpperCase()) {
		case "CLICK":
			log.info("Click on '" + objectName + "' with path: '" + p.getProperty(objectName) + "'");
			driver.findElement(this.getObject(p,objectName,objectType)).click();
			break;
		case "SETTEXT":
			log.info("Set text '"+ value +"' into '" + objectName + "' with path: '" + p.getProperty(objectName) + "'");
			driver.findElement(this.getObject(p,objectName,objectType)).sendKeys(value);
			break;
		case "GOTOURL":
			log.info("Go to URL: '" + p.getProperty(value) + "'");
			driver.get(p.getProperty(value));
			break;
		case "GETTEXT":
			log.info("Get text of '" + objectName + "'");
			driver.findElement(this.getObject(p,objectName,objectType)).getText();
			break;
		case "WAITTITLETOBE":
			//Check expected page title in 2 seconds
			//Read value and split it by '|'
			String[] valueParams = value.split("\\|",2);
			String expectedValue = valueParams[0];
			int secondsToWait = Integer.parseInt(valueParams[1]);
			log.info("Waiting for page title with '" + valueParams[0] + "' text for '" + valueParams[1] + "' seconds");
			(new WebDriverWait(driver, secondsToWait)).until(ExpectedConditions
					.textToBePresentInElement(driver.findElement(this.getObject(p,objectName,objectType)), expectedValue));
			break;
		case "CHECKTEXT":
			log.info("Check text for '" + objectName + "' with path: '" + p.getProperty(objectName) + "'");
			String text = driver.findElement(this.getObject(p,objectName,objectType)).getText();
			Helpers.check2StringIfEquals(text,value);
			break;
		case "CHECKINFOMSG":
			log.info("Check text for '" + objectName + "' with path: '" + p.getProperty(objectName) + "'");
			String infoMsg = driver.findElement(this.getObject(p,objectName,objectType)).getText();
			Helpers.check2StringIfContains(infoMsg,value);
			break;
		case "CHECKCONTENTTYPE":
			//Check content TYPE in the Content table
			//Read xpath from property file and split it by '+'
			String[] tempXpathToType = p.getProperty(objectName).split("\\+",2);
			//Read value and split it by '|' in order to get content TITLE and TYPE
			String[] contentParams = value.split("\\|",2);
			String title = contentParams[0];
			String type = contentParams[1];
			//create xpath including content TITLE
			String xpathToType = tempXpathToType[0] + title + tempXpathToType[1];

			log.info("Check if content '" + title + "' has TYPE: '" + type + "'");
			Helpers.check2StringIfEquals(driver.findElement(By.xpath(xpathToType)).getText(), type);
			break;
		case "CHECKCONTENTSTATUS":
			//Check content STATUS in the Content table
			//Read xpath from property file and split it by '+'
			String[] tempXpathToStatus = p.getProperty(objectName).split("\\+",2);
			//Read value and split it by '|' in order to get content item title and status
			String[] contentItemParams = value.split("\\|",2);
			String contentTitle = contentItemParams[0];
			String status = contentItemParams[1];
			//create xpath including content TITLE
			String xpathToStatus = tempXpathToStatus[0] + contentTitle + tempXpathToStatus[1];

			log.info("Check if content '" + contentTitle + "' has STATUS: '" + status + "'");
			Helpers.check2StringIfEquals(driver.findElement(By.xpath(xpathToStatus)).getText(), status);
			break;
		case "SELECTCONTENTITEM":
			//Select an item in the Content table
			//Read xpath from property file and split it by '+'
			String[] xpathToChkBox = p.getProperty(objectName).split("\\+",2);
			//create xpath for required check box
			String xpathToChkItem = xpathToChkBox[0] + value + xpathToChkBox[1];
			log.info("Select a content item with '"+ value + "' title");
			driver.findElement(By.xpath(xpathToChkItem)).click();
			break;
		case "SLEEP":
			String[] valueParts = value.split("\\.",2);
			int secToSleep = Integer.parseInt(valueParts[0]);
			Helpers.sleep(secToSleep);
		case "QUIT":
			log.info("End of test. Closing the browser.");
			driver.quit();
			break;
		default:
			break;
		}
	}
	
	/**
	 * Find element BY using object type and value
	 * @param p
	 * @param objectName
	 * @param objectType
	 * @return
	 * @throws Exception
	 */
	private By getObject(Properties p,String objectName,String objectType) throws Exception{
		//Find by xpath
		if(objectType.equalsIgnoreCase("XPATH")){
			
			return By.xpath(p.getProperty(objectName));
		}
		//find by class
		else if(objectType.equalsIgnoreCase("CLASSNAME")){
			
			return By.className(p.getProperty(objectName));
			
		}
		//find by name
		else if(objectType.equalsIgnoreCase("NAME")){
			
			return By.name(p.getProperty(objectName));
			
		}
		//Find by css
		else if(objectType.equalsIgnoreCase("CSS")){
			
			return By.cssSelector(p.getProperty(objectName));
			
		}
		//find by link
		else if(objectType.equalsIgnoreCase("LINK")){
			
			return By.linkText(p.getProperty(objectName));
			
		}
		//find by partial link
		else if(objectType.equalsIgnoreCase("PARTIALLINK")){
			
			return By.partialLinkText(p.getProperty(objectName));
			
		}else
		{
			throw new Exception("Wrong object type");
		}
	}
}
