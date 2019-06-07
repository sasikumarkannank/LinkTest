package Marlabs.LinkTest;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import Marlabs.Constants.Constants;
import Marlabs.Core.Xls_Reader;
import Marlabs.Reports.ExtentReport;

public class LinkTest_2 {	
	
	public static String URL;
	public static Xls_Reader excel = null;
	public static Xls_Reader getData = null;
	public static int k;
	public static String sub_URL = null;
	public static int numOfScenarios = 0;
	public static String execStatus = null;
	public static String page_Name = null;


	public static void main(String args[]) {
		ExtentReports extent = ExtentReport.startReport("Result");
		ExtentTest passedReport = extent.startTest("Passed Links");
		ExtentTest failedreport = extent.startTest("Failed Links");
		ExtentTest redirectReports = extent.startTest("Redirected Links");
		
		// To get the subURL
		HtmlUnitDriver driver = new HtmlUnitDriver();
		int i=0;
		//System.out.println("No of Scenarios: " + numOfScenarios);
		Xls_Reader getdata_subURL = new Xls_Reader(Constants.TestSuitePath_LinkTest);
		numOfScenarios = getdata_subURL.getRowCount(Constants.Mainsheet);
		System.out.println("No of Scenarios: " + numOfScenarios);
		try {
			for (k = 1; k <= numOfScenarios; k++) {
				execStatus = getdata_subURL.getCellData(Constants.Mainsheet, 2, k);
				System.out.println("Execution Status: " + execStatus);
				if (execStatus.equalsIgnoreCase("YES")) {
					sub_URL = getdata_subURL.getCellData(Constants.Mainsheet, 1, k);
					page_Name = getdata_subURL.getCellData(Constants.Mainsheet, 0, k);
					System.out.println("Page Name: " + page_Name);

					URL = Constants.currentURL.concat(sub_URL);

					System.out.println("URL: " + URL);

					// Initialize by getting the main URL
					driver.get(URL);

					String links_href = null;
					int count = 0;
					try {
						URL baseUrl = new URL(URL);
						HttpURLConnection connection_baseURL = (HttpURLConnection) baseUrl.openConnection();
						int statusCode_baseURL = connection_baseURL.getResponseCode();
						System.out.println(statusCode_baseURL);
						String msg_baseURl = connection_baseURL.getResponseMessage();
						System.out.println(msg_baseURl);

						if (statusCode_baseURL == 200) {
							System.out.println("Pass");
							// status = "Pass";
							passedReport.log(LogStatus.PASS,
									"<span style='font-weight:bold;color:black'>" + page_Name + "</br></span>"
											+ "<span style='font-weight:bold;color:blue'>" + "" + "Actual_URL: " + URL
											+ "</br></span>"
											+ "Response code:<span style='font-weight:bold; font-size:large;color:green'>"
											+ statusCode_baseURL + "</span>");
						} else if (statusCode_baseURL == 301) {
							System.out.println("External links with statusCode 301");
							redirectReports.log(LogStatus.PASS,
									"<span style='font-weight:bold;color:black'>" + page_Name + "</br></span>"
											+ "<span style='font-weight:bold;color:blue'>" + "" + "Actual_URL: " + URL
											+ "</br></span>"
											+ "Response code:<span style='font-weight:bold; font-size:large;color:green'>"
											+ statusCode_baseURL + "</span>");
							
						} else {
							System.out.println("Fail");
							// status = "Fail";
							failedreport.log(LogStatus.FAIL,
									"<span style='font-weight:bold;color:black'>" + page_Name + "</br></span>"
											+ "<span style='font-weight:bold;color:blue'>Actual_URL: " + URL
											+ " </br></span>"
											+"Response code:<span style='font-weight:bold; font-size:large;color:red'>"
											+ statusCode_baseURL + "</span>");

							extent.endTest(passedReport);
							extent.endTest(failedreport);
							extent.endTest(redirectReports);
							
							continue;

						}
					} catch (Exception e) {
						System.out.println("status code of out range");
					}
					// read the links in the web page and store in a WebElement
					// List
					List<WebElement> link_counter = driver.findElements(By.tagName("a"));
					count = link_counter.size();
					System.out.println("No of links: " + count);

					for (int d = 1; d < count; d++) {
						String actual_linktext = link_counter.get(d).getText().trim();
						System.out.println("Actual Link: " + actual_linktext);
						try {
							links_href = driver.findElement(By.linkText(actual_linktext)).getAttribute("href");
							System.out.println(d + ": Actual URL: " + links_href);
						} catch (Exception e) {
							System.out.println("Status code out of range");
							failedreport.log(LogStatus.ERROR,
									"<span style='font-weight:bold;color:Red'>" + actual_linktext + "</br></span>"
											+ "<span style='font-weight:bold;color:Red'>Actual_URL: " + links_href
											+ " </br></span>" + "</span>");
							continue;
						}
						// To get the HTTP response code
						try {
							URL url = new URL(links_href);
							HttpURLConnection connection = (HttpURLConnection) url.openConnection();
							int statusCode = connection.getResponseCode();
							System.out.println(statusCode);
							String msg = connection.getResponseMessage();
							System.out.println(msg);

							// Validation of response code and reporting
							if (statusCode == 200) {
								System.out.println("Pass");
								// status = "Pass";
								passedReport.log(LogStatus.PASS,
										"<span style='font-weight:bold;color:black'>" + actual_linktext + "</br></span>"
												+ "<span style='font-weight:bold;color:blue'>" + "" + "Actual_URL: "
												+ links_href + "</br></span>"
												+ "Response code:<span style='font-weight:bold; font-size:large;color:green'>"
												+ statusCode + "</span>");
							} else if (statusCode == 301) {
								System.out.println("External links with statusCode 301");
								redirectReports.log(LogStatus.PASS,
										"<span style='font-weight:bold;color:black'>" + page_Name + "</br></span>"
												+ "<span style='font-weight:bold;color:blue'>" + "" + "Actual_URL: " + URL
												+ "</br></span>"
												+ "Response code:<span style='font-weight:bold; font-size:large;color:green'>"
												+ statusCode + "</span>");
								
							} else {
								System.out.println("Fail");
								// status = "Fail";
								failedreport.log(LogStatus.FAIL,
										"<span style='font-weight:bold;color:black'>" + actual_linktext + "</br></span>"
												+ "<span style='font-weight:bold;color:blue'>Actual_URL: " + links_href
												+ " </br></span>"
												+ "Response code:<span style='font-weight:bold; font-size:large;color:red'>"
												+ statusCode + "</span>");
							}
						} catch (Exception e) {
							System.out.println("Status code out of range");
							failedreport.log(LogStatus.ERROR,
									"<span style='font-weight:bold;color:Red'>" + actual_linktext + "</br></span>"
											+ "<span style='font-weight:bold;color:Red'>Actual_URL: " + links_href
											+ " </br></span>" + "</span>");
						}
					}

					extent.endTest(passedReport);
					extent.endTest(failedreport);
					extent.endTest(redirectReports);

				}

			}
			
			// driver.close();
			System.out.println("Completed");
		}
			
			/*
			URL =Constants.currentURL;

			System.out.println("URL: " + URL);
			driver.get(URL);
			
	           List<WebElement>links = driver.findElements(By.tagName("a"));
	           System.out.println("Total URLs ="+links.size());
	           for(WebElement linkno: links) {
	                
	                String URL = linkno.getAttribute("href");             

	                System.out.println(i++ +": Current URL: "+URL);
					// Initialize by getting the main URL
					
					try {
						
							
						
						URL baseUrl = new URL(URL);
						HttpURLConnection connection_baseURL = (HttpURLConnection) baseUrl.openConnection();
						int statusCode_baseURL = connection_baseURL.getResponseCode();
						System.out.println(statusCode_baseURL);
						String msg_baseURl = connection_baseURL.getResponseMessage();
						System.out.println(msg_baseURl);

						if (statusCode_baseURL == 200) {
							testreport.log(LogStatus.PASS,
									"<span style='font-weight:bold;color:black'>"+"</br></span>"
											+ "<span style='font-weight:bold;color:blue'>" + "" + "Actual_URL: " + URL
											+ "</br></span>"
											+ "Response code:<span style='font-weight:bold; font-size:large;color:green'>"
											+ statusCode_baseURL + "</span>");
						} else if (statusCode_baseURL == 301) {
							System.out.println("External links with statusCode 301");
						} else {
											
							if(statusCode_baseURL==500||statusCode_baseURL==400) {				FatalErrors_Report.log(LogStatus.FATAL,
									"<span style='font-weight:bold;color:blue'>"+ "URL: " + URL + "</br></span>"
											+ "Response code:<span style='font-weight:bold; font-size:large;color:red'>"
											+ statusCode_baseURL + "</span>");
						}
							else{
								FatalErrors_Report.log(LogStatus.FATAL,
										"<span style='font-weight:bold;color:blue'>"+ "URL: " + URL + "</br></span>"
												+ "Response code:<span style='font-weight:bold; font-size:large;color:red'>"
												+ statusCode_baseURL + "</span>");
								
							}

							//extent.endTest(testreport);
							continue;

						}
					} catch (Exception e) {
						System.out.println("status code of out range");
					}
				//extent.endTest(testreport);

				}
	           
				System.out.println("Completed");
			
			
		*/ catch (Exception e)

		{
			System.out.println(e);
			/*extent.close();
			extent.flush();*/
			driver.quit();
			System.out.println("Execution aborted");
			System.out.println("Execution Completed");
		}
		extent.endTest(passedReport);
		extent.endTest(failedreport);
		extent.endTest(redirectReports);
		extent.close();
		extent.flush();
        driver.quit();
        
		
		 	
	}
}
