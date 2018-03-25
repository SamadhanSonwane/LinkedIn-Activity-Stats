package pages;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Activity{
	
	static XSSFWorkbook workbook;
	static int activityCount = 0;
	
	// method to get input data from the file LinkedInStats.xlsx
	public XSSFSheet getSheet(String sheetName) throws IOException{
		File source = new File(System.getProperty("user.dir") + "\\input-data\\LinkedInStats.xlsx");
		FileInputStream fis = new FileInputStream(source);
		
		workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheet(sheetName);
		
		return sheet;
	}
	
	// method to navigate to the Articles tab
	public void navigateToArticles(WebDriver driver) throws IOException{
		
		// click the Articles tab header
		driver.findElement(By.xpath(getSheet("Posts&Activity").getRow(5).getCell(4).getStringCellValue())).click();
		
	}
	
	// method to navigate to the Posts tab
	public void navigateToPosts(WebDriver driver) throws IOException{
		
		// click the Posts tab header
		driver.findElement(By.xpath(getSheet("Posts&Activity").getRow(6).getCell(4).getStringCellValue())).click();
		
	}
	
	// method to logout from LinkedIn account
	public void logout(WebDriver driver) throws IOException{
		
		// click the Navigation Settings button
		driver.findElement(By.xpath((getSheet("Posts&Activity").getRow(1).getCell(4).getStringCellValue()))).click();
		
		// wait for the Sign out button to appear
		WebDriverWait wait = new WebDriverWait(driver, 5);
		WebElement signout = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath((getSheet("Posts&Activity").getRow(2).getCell(4).getStringCellValue()))));

		signout.click();
		workbook.close();
	}
	
	// method to read all article statistics
	public void readActivityAnalytics(WebDriver driver) throws IOException, InterruptedException{
		
		// creating output workbook
		XSSFWorkbook outputWorkbook = new XSSFWorkbook();
		XSSFSheet outputSheet = outputWorkbook.createSheet("LinkedInStats");
		// write output file column headers
		writeColumnHeaders(outputSheet);
		
		// WebDriverWait wait = new WebDriverWait(driver, 10);
		
		// navigate to the Articles tab
			driver.findElement(By.xpath(getSheet("Posts&Activity").getRow(5).getCell(4).getStringCellValue())).click();
				// wait for 5 sec for all the article titles to appear
				Thread.sleep(5000);
				List<WebElement> articleTitles = driver.findElements(By.xpath(getSheet("Posts&Activity").getRow(8).getCell(4).getStringCellValue()));
			
			// all article analytics icons
			List<WebElement> articleAnalyticsIcons = driver.findElements(By.xpath(getSheet("Posts&Activity").getRow(9).getCell(4).getStringCellValue()));
			
			String activityStats[];
			for(int i=0; i<articleTitles.size(); i++){
				
				System.out.print(articleTitles.get(i).getText() + ": ");
				activityStats = getAnalytics(articleAnalyticsIcons.get(i), driver);
					System.out.print(activityStats[0] + "; ");	// views
					System.out.print(activityStats[1]);	// comments, likes and shares
					System.out.println();
				
				Row row = outputSheet.getRow(activityCount + 1);
				if (row == null){
					row = outputSheet.createRow(activityCount + 1);
				}
				
				Cell cell = row.getCell(0);
				if (cell == null){
					cell = row.createCell(0);
				}
				cell.setCellValue(articleTitles.get(i).getText());	// writing article title in the output file
				
				cell = row.getCell(1);
				if (cell == null){
					cell = row.createCell(1);
				}
				cell.setCellValue(activityStats[0]);	// writing views in the output file
				
				cell = row.getCell(2);
				if (cell == null){
					cell = row.createCell(2);
				}
				cell.setCellValue(activityStats[1]);	// writing comments, likes and shares in the output file
				
				activityCount++;
			}
		
		// navigate to the Posts tab
			driver.findElement(By.xpath(getSheet("Posts&Activity").getRow(6).getCell(4).getStringCellValue())).click();
				// wait for 5 sec for all the posts to appear
				Thread.sleep(5000);
				List<WebElement> postTimes = driver.findElements(By.xpath(getSheet("Posts&Activity").getRow(13).getCell(4).getStringCellValue()));
			
			// all post analytics icons
			List<WebElement> postAnalyticsIcons = driver.findElements(By.xpath(getSheet("Posts&Activity").getRow(9).getCell(4).getStringCellValue()));
			
			for(int i=0; i<postTimes.size(); i++){
				
				System.out.print(postTimes.get(i).getText() + " old post: ");
				activityStats = getAnalytics(postAnalyticsIcons.get(i), driver);
					System.out.print(activityStats[0] + "; ");	// views
					System.out.print(activityStats[1]);	// comments, likes and shares
					System.out.println();
				
				Row row = outputSheet.getRow(activityCount + 1);
				if (row == null){
					row = outputSheet.createRow(activityCount + 1);
				}
				
				Cell cell = row.getCell(0);
				if (cell == null){
					cell = row.createCell(0);
				}
				cell.setCellValue("post " + (i+1) + ", " + postTimes.get(i).getText() + " old post");	// writing post sequence and time elapsed in the output file
				
				cell = row.getCell(1);
				if (cell == null){
					cell = row.createCell(1);
				}
				cell.setCellValue(activityStats[0]);	// writing views in the output file
				
				cell = row.getCell(2);
				if (cell == null){
					cell = row.createCell(2);
				}
				cell.setCellValue(activityStats[1]);	// writing comments, likes and shares in the output file
				
				activityCount++;
			}			
			
		FileOutputStream fos = new FileOutputStream(new File(System.getProperty("user.dir") + "\\output-data\\LinkedInActivityStats.xlsx"));
		outputWorkbook.write(fos);
		fos.close();
		outputWorkbook.close();
	}
	
	// method to write column headers in the output file 
	public void writeColumnHeaders(XSSFSheet outputSheet) throws IOException{
		
		// writing column headers
		Row hRow = outputSheet.getRow(0);
		if (hRow == null){
			hRow = outputSheet.createRow(0);
		}
		Cell hCell = hRow.getCell(0);
		if (hCell == null){
			hCell = hRow.createCell(0);
		}
		hCell.setCellValue("Article/Post");	// column header: Article/Post
		hCell = hRow.getCell(1);
		if (hCell == null){
			hCell = hRow.createCell(1);
		}
		hCell.setCellValue("Views");	// column header: Views
		hCell = hRow.getCell(2);
		if (hCell == null){
			hCell = hRow.createCell(2);
		}
		hCell.setCellValue("Comments, Likes, Shares");	// column header: Comments, Likes, Shares
	}
	
	public String[] getAnalytics(WebElement analyticsIcon, WebDriver driver) throws IOException{
		
		String[] stats = {"", ""};
		
		analyticsIcon.click();
			try{ Thread.sleep(1000);
			}catch(InterruptedException e){ e.printStackTrace();
			}
		WebDriverWait wait = new WebDriverWait(driver, 10);
		// wait for all the activity views tab header (on the analytics panel) to appear
		WebElement views = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(getSheet("Posts&Activity").getRow(10).getCell(4).getStringCellValue())));
		stats[0] = views.getText();	// get views
		WebElement commentsLikesShares = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(getSheet("Posts&Activity").getRow(11).getCell(4).getStringCellValue())));
		stats[1] = commentsLikesShares.getText();	// get likes, comments and shares
		
		// close the analytics panel
		driver.findElement(By.xpath(getSheet("Posts&Activity").getRow(12).getCell(4).getStringCellValue())).click();
		
		return stats;
	}
}
