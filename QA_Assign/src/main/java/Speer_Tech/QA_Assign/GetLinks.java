package Speer_Tech.QA_Assign;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import com.opencsv.CSVWriter;

public class GetLinks {

	public static void main(String[] args) throws IOException {
		// Getting URL and number of cycles from the User
		String url = getInputUrl();
		// Webdriver setup
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		// verify url and throw error if not Wiki
		verfyURL(url, driver);

		// Get number of cycles and verify for 1-20
		int number = getInputCycles();

		driver.get(url);

// Collection to store all the links links
		List<String> links = new ArrayList<String>();
		// Providing first i/p to Arraylist (Base URL )
		links.add(url);

		int Start = 0; // Defining the Start oin to use in loop

		for (int i = 1; i <= number; i++) // Main outer loop to run the cycles ( N times based on i/p )
		{
			int FinalLength = links.size(); // Setting the end value of inner loop

			for (int j = Start; j <= FinalLength - 1; j++) {
				Start = FinalLength; // Setting he Start point of loop for next cycle
				String Url2 = links.get(j);
				ArrayList<String> links3 = scrapeLinks(driver, Url2); // Calling the ScareLink Method to get Links
				for (String lin : links3) {

					links.add(lin); // Adding the values to Main ArrayList (get from Method )
				}
			}
		}
// Calling Method to Save in CSV and print
		printLoadCSV(links);

		driver.close();
	}

/// Method to verify URL and throw an error if its not wiki link	
	private static void verfyURL(String url, WebDriver driver) {
		if (isWikiLink(url)) {
			System.out.println("The URL is a Wikipedia link");
		} else {
			System.out.println("its not wikipedia link");
			driver.quit();
			throw new AssertionError("The URL is not a Wikipedia link");
		}

	}

// Method o get input 
	private static String getInputUrl() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the URL ");
		String urlInput = scanner.nextLine();

		return urlInput;
	}

// Method to get Number of cycles from user
	public static int getInputCycles() {

		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the number of cycle ");
		int inputNumber = scanner.nextInt();
		// Check for the validation of i/p Nuber of Cycles
		while (inputNumber < 1 || inputNumber > 20) {
			System.out.print("Enter a number between 1 and 20: ");
			inputNumber = scanner.nextInt();
		}
		System.out.println("The number you entered is: " + inputNumber);

		return inputNumber;
	}

// Method ScrapeLinks t get the WIKI Links from the URL
// driver and URL is provided to method and Array list is the Return type 
	public static ArrayList<String> scrapeLinks(WebDriver driver, String url) {
		ArrayList<String> links = new ArrayList<String>(); // Defining Arraylist for the method
		try {
			driver.get(url);
		} catch (StaleElementReferenceException e) // For the exception of URL not working
		{
			return links;
		}
//}

		for (WebElement link : driver.findElements(By.tagName("a")))// getting links from the page
		{
			try {
				String href = link.getAttribute("href"); // getting URL
				if (href != null && href.contains("wikipedia.org/wiki/")) // cheking if link is WIkiLINK
				{
					if (!links.contains(href)) { // To check if link is present in List to add unique element only
						links.add(href);
						System.out.println(href);
					}
				}
			} catch (StaleElementReferenceException e)

			{
				continue; // Used for stale element exception
// Retry the steps can be used OR Wait can be used 
			}
		}
		return links;
	}
	// Method to check Wikilink URL

	public static boolean isWikiLink(String url) {
		return url.startsWith("https://en.wikipedia.org/wiki/");
	}

	// Method to create csv file and print values in console

	public static void printLoadCSV(List<String> links) throws IOException {
		FileWriter outputfile = new FileWriter("data.csv)");
		CSVWriter writer = new CSVWriter(outputfile);
		System.out.println("Total number of links found: " + links.size());
		for (String link : links) // Iterating through each element of Arrayist
		{
			System.out.println(link);
			writer.writeNext(new String[] { link });
		}
		System.out.println("Total number of links found: " + links.size());

	}
}
