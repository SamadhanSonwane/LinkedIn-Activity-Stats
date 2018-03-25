package tests;

import java.io.IOException;

import mymethods.LaunchBrowser;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import pages.LinkedInLogin;

public class Login{
	
	@Test
	public void launchLoginPage() throws IOException{
		
		LaunchBrowser launcher = new LaunchBrowser();
		WebDriver driver = launcher.launchMyBrowser("Firefox");
		LinkedInLogin runner = new LinkedInLogin();
		
		runner.launchPage(driver);
		runner.login(driver);
		runner.logout(driver);
		
		driver.quit();
	}
}
