package tests;

import java.io.IOException;

import mymethods.LaunchBrowser;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import pages.Activity;
import pages.Home;
import pages.LinkedInLogin;

public class ReadActivityStats{
	
	@Test
	public void readArticleStats() throws IOException, InterruptedException{
		
		LaunchBrowser launcher = new LaunchBrowser();
		WebDriver driver = launcher.launchMyBrowser("Chrome");
		LinkedInLogin loginRunner = new LinkedInLogin();
		Home feedRunner = new Home();
		Activity activityRunner = new Activity();
		
		loginRunner.launchPage(driver);
		loginRunner.login(driver);
		feedRunner.navigateToActivity(driver);	// navigating to Posts & Activity page
		activityRunner.readActivityAnalytics(driver);	// record activity stats
		
		activityRunner.logout(driver);
		driver.quit();
	}
}
