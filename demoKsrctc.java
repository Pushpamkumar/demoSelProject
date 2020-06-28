package projects;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.io.FileInputStream;
import java.lang.*;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class demoKsrctc {

	@Test
	public void demo() throws Exception {
		
	
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\pushpam\\Downloads\\Project\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		
		
		driver.get("https://ksrtc.in/oprs-web/guest/home.do");
		Thread.sleep(2000);
		
		driver.manage().window().maximize();
		
		Properties prop = new Properties();
		FileInputStream data = new FileInputStream("C:\\Users\\pushpam\\workspace\\projects\\src\\projects\\data.properties");
		prop.load(data);
		
		
		WebElement input = driver.findElement(By.id("fromPlaceName"));
		input.sendKeys((prop.getProperty("inputSource")));
		Thread.sleep(2000);
				
		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		String script = " return document.getElementById(\"fromPlaceName\").value;";
		String text = (String) js.executeScript(script);
		
		while(!text.equalsIgnoreCase((prop.getProperty("selectSource")))){
			
			input.sendKeys(Keys.ARROW_DOWN);
			text = (String) js.executeScript(script);
			
		}
		input.sendKeys(Keys.ENTER);
		
		input = driver.findElement(By.id("toPlaceName"));
		input.sendKeys((prop.getProperty("inputDest")));
		Thread.sleep(2000);
		script = " return document.getElementById(\"toPlaceName\").value;";
		
        while(!text.equalsIgnoreCase((prop.getProperty("selectDest")))){	
			
			input.sendKeys(Keys.ARROW_DOWN);
			text = (String) js.executeScript(script);
			
		}
		
        input.sendKeys(Keys.ENTER);
		
		
		//Date of Departure
		driver.findElement(By.xpath("//input[@id='txtJourneyDate']")).click();;
		String month = "[class='ui-datepicker-month']"; 
		String next = "[data-handler='next']";
		while(!driver.findElement(By.cssSelector(month)).getText().contains((prop.getProperty("month")))){
			
			driver.findElement(By.cssSelector(next)).click();
			
		}
		
		 //	selecting date	
		List<WebElement> dates = driver.findElements(By.xpath("//td[@data-handler='selectDay']"));
		int count =  driver.findElements(By.xpath("//td[@data-handler='selectDay']")).size();
		
		for(int i=0; i<count; i++){
			
			String datetext =  driver.findElements(By.xpath("//td[@data-handler='selectDay']")).get(i).getText();
			
			if(datetext.equalsIgnoreCase((prop.getProperty("day")))){
				
				driver.findElements(By.xpath("//td[@data-handler='selectDay']")).get(i).click();
				break;
				
			}
		}
		
		driver.findElement(By.cssSelector(".btn.btn-primary.btn-lg.btn-block.btn-booking")).click();
		
		WebDriverWait wt = new WebDriverWait(driver, 5);
		wt.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='select-service']/h3/span/span")));
		
		//selecting bus with LOW FARE
		List<WebElement> ticketRates = driver.findElements(By.cssSelector(".select-service .TickRate"));
		
		count = ticketRates.size();
		
		String rate = ticketRates.get(0).getText();	
		
		int i=0;
		int j=0;
		for(i=1; i<count; i++){
			
			
			String rate2 = ticketRates.get(i).getText();
		
			if(Integer.parseInt(rate2)<Integer.parseInt(rate)){
				
				rate = rate2;
				j=i;
				
			}
					
		}
		
        driver.findElement(By.id("corover-close-btn")).click();
    
		driver.findElements(By.cssSelector("[class='select-service'] input[value='Select Seats']")).get(j).click();
		
		wt.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='tab-content-layout tab-content-berth']/div/ul/li[@class='availSeatClassS']")));
		//selecting no of seats = 2
		List<WebElement> availSeats = driver.findElements(By.cssSelector(".availSeatClassS"));
	    int availSeatsCount = driver.findElements(By.cssSelector(".availSeatClassS")).size();
	    	       
	    
	    int seatsReq=0;
	 
		while(seatsReq<Integer.parseInt(prop.getProperty("reqSeats"))){
			availSeats.get(seatsReq).click();
			seatsReq++;
		}
		
		
		
		//selecting boarding point
		
		driver.findElement(By.xpath(prop.getProperty("boardingPoint"))).click();
		
		//selecting dropping point
		driver.findElement(By.xpath(prop.getProperty("droppingPoint"))).click();
		
		//giving customer details
		driver.findElement(By.xpath("//input[@id='mobileNo']")).sendKeys(prop.getProperty("mobile"));
		driver.findElement(By.xpath("//input[@id='email']")).sendKeys(prop.getProperty("email"));
		
		//giving passenger details
		
		int passengerSeq=0;
		while(passengerSeq<seatsReq){
		driver.findElements(By.xpath("//input[@name='passengerName']")).get(passengerSeq).sendKeys(prop.getProperty("passengerName"));
		// select name="genderCodeId"
		Select s = new Select(driver.findElements(By.xpath("//select[@name='genderCodeId']")).get(passengerSeq));
		s.selectByVisibleText(prop.getProperty("passengerGeneder"));
		//input name="passengerAge"
		driver.findElements(By.xpath("//input[@name='passengerAge']")).get(passengerSeq).sendKeys(prop.getProperty("passengerAgee"));
		passengerSeq++;
	}
		
		//click on payment button
		
	    driver.findElement(By.xpath("//input[@value='Make Payment']")).click();
		
	}



	

}
