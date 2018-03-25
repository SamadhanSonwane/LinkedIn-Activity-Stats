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

public class LinkedInLogin {
	
	static XSSFWorkbook workbook;
	// method to get input data from the file LinkedInStats.xlsx
	public XSSFSheet getSheet(String sheetName) throws IOException{
		File source = new File(System.getProperty("user.dir") + "\\input-data\\LinkedInStats.xlsx");
		FileInputStream fis = new FileInputStream(source);
		
		workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheet(sheetName);
		
		return sheet;
	}
	
	// method to launch the LinkedIn login page
	public void launchPage(WebDriver driver) throws IOException{
		
		String url = getSheet("LinkedInLogin").getRow(1).getCell(1).getStringCellValue();
		driver.get(url);

		workbook.close();
	}
	
	// method to login to LinkedIn account
	public void login(WebDriver driver) throws IOException{
		
		WebDriverWait wait = new WebDriverWait(driver, 15);
		WebElement email = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath((getSheet("LinkedInLogin").getRow(1).getCell(4).getStringCellValue()))));
		WebElement password = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath((getSheet("LinkedInLogin").getRow(2).getCell(4).getStringCellValue()))));
		WebElement signin = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath((getSheet("LinkedInLogin").getRow(3).getCell(4).getStringCellValue()))));
		
		// write login credentials
		email.sendKeys(getSheet("LinkedInLogin").getRow(2).getCell(1).getStringCellValue());
		password.sendKeys(getSheet("LinkedInLogin").getRow(3).getCell(1).getStringCellValue());
		
		signin.click();
		
		// wait for the Navigation Settings button to appear after login
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath((getSheet("LinkedInLogin").getRow(4).getCell(4).getStringCellValue()))));
	}
	
	//method to logout from LinkedIn account
	public void logout(WebDriver driver) throws IOException{
		
		// click the Navigation Settings button
		driver.findElement(By.xpath((getSheet("LinkedInLogin").getRow(4).getCell(4).getStringCellValue()))).click();
		
		// wait for the Sign out button to appear
		WebDriverWait wait = new WebDriverWait(driver, 5);
		WebElement signout = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath((getSheet("LinkedInLogin").getRow(5).getCell(4).getStringCellValue()))));

		signout.click();
	}
}
