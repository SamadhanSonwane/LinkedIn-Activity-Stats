package pages;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Home {
	
	static XSSFWorkbook workbook;
	// method to get input data from the file LinkedInStats.xlsx
	public XSSFSheet getSheet(String sheetName) throws IOException{
		File source = new File(System.getProperty("user.dir") + "\\input-data\\LinkedInStats.xlsx");
		FileInputStream fis = new FileInputStream(source);
		
		workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheet(sheetName);
		
		return sheet;
	}
	
	// method to navigate to Posts & Activity page from Home
	public void navigateToActivity(WebDriver driver) throws IOException, InterruptedException{
		
		WebDriverWait wait = new WebDriverWait(driver, 10);
		// wait for the Navigation Settings button to appear
		WebElement navSetting = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath((getSheet("Home").getRow(1).getCell(4).getStringCellValue()))));
		Thread.sleep(2000);
		navSetting.click();
		Thread.sleep(2000);
		
		// wait for the Activity & Posts menu item to appear
		WebElement activity = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath((getSheet("Home").getRow(3).getCell(4).getStringCellValue()))));
		activity.click();
		
		// wait for Posts & Activity page header to appear
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath((getSheet("Home").getRow(4).getCell(4).getStringCellValue()))));
	}
	
	//method to logout from LinkedIn account
	public void logout(WebDriver driver) throws IOException{
		
		// click the Navigation Settings button
		driver.findElement(By.xpath((getSheet("Home").getRow(1).getCell(4).getStringCellValue()))).click();
		
		// wait for the Sign out button to appear
		WebDriverWait wait = new WebDriverWait(driver, 5);
		WebElement signout = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath((getSheet("Home").getRow(2).getCell(4).getStringCellValue()))));

		signout.click();
	}
}
