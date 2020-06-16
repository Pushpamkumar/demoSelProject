package projects;

import java.util.List;
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
		
	
		// TODO Auto-generated method stubs
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\pushpam\\Downloads\\Project\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		
		
		driver.get("https://ksrtc.in/oprs-web/guest/home.do");
		Thread.sleep(2000);
		
		WebElement input = driver.findElement(By.id("fromPlaceName"));
		input.sendKeys("ben");
		Thread.sleep(2000);
		
		//selenium cannot identify hidden element so we use javascript DOM(Document Object Model)
		//To use javascript in java we use JavascriptExecutor
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		String script = " return document.getElementById(\"fromPlaceName\").value;";
		String text = (String) js.executeScript(script);
		
		while(!text.equalsIgnoreCase("BENGALURU")){
			
			input.sendKeys(Keys.ARROW_DOWN);
			text = (String) js.executeScript(script);
			
		}
		input.sendKeys(Keys.ENTER);
		
		input = driver.findElement(By.id("toPlaceName"));//CHIKKAMAGALURU
		input.sendKeys("ch");
		Thread.sleep(2000);
		script = " return document.getElementById(\"toPlaceName\").value;";
		
        while(!text.equalsIgnoreCase("CHIKKAMAGALURU")){
			
			input.sendKeys(Keys.ARROW_DOWN);
			text = (String) js.executeScript(script);
			
		}
		
        input.sendKeys(Keys.ENTER);
		
		
		//Date of Departure
		driver.findElement(By.xpath("//input[@id='txtJourneyDate']")).click();;
		String month = "[class='ui-datepicker-month']"; 
		String next = "[data-handler='next']";
		while(!driver.findElement(By.cssSelector(month)).getText().contains("July")){
			
			driver.findElement(By.cssSelector(next)).click();
		}
		
		 //	selecting date	
		List<WebElement> dates = driver.findElements(By.xpath("//td[@data-handler='selectDay']"));
		int count =  driver.findElements(By.xpath("//td[@data-handler='selectDay']")).size();
		
		for(int i=0; i<count; i++){
			
			String datetext =  driver.findElements(By.xpath("//td[@data-handler='selectDay']")).get(i).getText();
			
			if(datetext.equalsIgnoreCase("16")){
				
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
		driver.findElements(By.cssSelector(".btnSelectLO.select-service-btn")).get(j).click();
		
		wt.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='tab-content-layout tab-content-berth']/div/ul/li[@class='availSeatClassS']")));
		//selecting no of seats = 2
		List<WebElement> availSeats = driver.findElements(By.cssSelector(".availSeatClassS"));
	    int availSeatsCount = driver.findElements(By.cssSelector(".availSeatClassS")).size();
	    	       
	    
	    int seatsReq=0;
		while(seatsReq<2){
			availSeats.get(seatsReq).click();
			seatsReq++;
		}
		
		
		
		//selecting boarding point = KEMPEGOWDA
		driver.findElement(By.xpath("//*[contains(@onclick,'KEMPEGOWDA')]")).click();
		
		//selecting dropping point = CHIKKAMAGALURU 
		driver.findElement(By.xpath("//*[contains(@onclick,'CHIKKAMAGALURU')]")).click();
		
		//giving customer details
		driver.findElement(By.xpath("//input[@id='mobileNo']")).sendKeys("9876543210");
		driver.findElement(By.xpath("//input[@id='email']")).sendKeys("johndoe@email.com");
		
		//giving passenger details
		
		int passengerSeq=0;
		while(passengerSeq<seatsReq){
		driver.findElements(By.xpath("//input[@name='passengerName']")).get(passengerSeq).sendKeys("John Doe");
		// select name="genderCodeId"
		Select s = new Select(driver.findElements(By.xpath("//select[@name='genderCodeId']")).get(passengerSeq));
		s.selectByVisibleText("MALE");
		//input name="passengerAge"
		driver.findElements(By.xpath("//input[@name='passengerAge']")).get(passengerSeq).sendKeys("22");
		passengerSeq++;
	}
		
		//click on payment button
		
	    driver.findElement(By.xpath("//input[@value='Make Payment']")).click();
		
	}



	

}
