package Marlabs.Core;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.ExtentTestInterruptedException;
import com.relevantcodes.extentreports.LogStatus;

public class Keywords {

	// --------------- Compare the data and report ---------------
	public void dataComparisonContains(String ExpectedData, String ActualData, String nameForReport,
			ExtentTest testreport, ExtentTest testreportFail, String HOSP_URL, String StateName) throws Exception {
		Keywords keywords = new Keywords();
		

		try {		

			if (ExpectedData.length() > 0) 
			{
			
				if ((ActualData.toLowerCase().equals(ExpectedData.toLowerCase())))
				{
						
					
					testreport.log(LogStatus.PASS, keywords.resultPASSContains(ExpectedData, ActualData, nameForReport));
					
				}
							
				else {
					//System.out.println("Actual Value"+ActualData +" Expected Value"+ExpectedData);
					testreportFail.log(LogStatus.FAIL,
							keywords.resultFAILNEW(HOSP_URL, ExpectedData, ActualData, nameForReport,StateName));
				}
			}
			else {
			
				testreportFail.log(LogStatus.FAIL,
						keywords.resultFAILNEW(HOSP_URL, ExpectedData, ActualData, nameForReport, StateName));
			}
		} catch (Exception e) {
			testreportFail.log(LogStatus.FAIL,
					keywords.resultFAILNEW(HOSP_URL, ExpectedData, ActualData, nameForReport,StateName));

		}
	}
	
	
	public void dataComparisonEquals(String ExpectedData, String ActualData, String nameForReport,
			ExtentTest testreport, ExtentTest testreportFail, String HOSP_URL, String City) throws Exception {
		Keywords keywords = new Keywords();

		try {

			if (ExpectedData.length() > 0) 
			{

				if (ActualData.toLowerCase().equals(ExpectedData.toLowerCase())
						|| ExpectedData.toLowerCase().equals(ActualData.toLowerCase())) 
				{
					testreport.log(LogStatus.PASS, keywords.resultPASS(ExpectedData, ActualData, nameForReport,City));
				} 
				else 
				{
					testreportFail.log(LogStatus.FAIL,
							keywords.resultFAIL(HOSP_URL, ExpectedData, ActualData, nameForReport,City));
				}
				
				
			}
			else {
				testreportFail.log(LogStatus.FAIL,
						keywords.resultFAIL(HOSP_URL, ExpectedData, ActualData, nameForReport, City));
			}
		} catch (Exception e) {
			testreportFail.log(LogStatus.FAIL,
					keywords.resultFAIL(HOSP_URL, ExpectedData, ActualData, nameForReport, City));

		}
	}
	
	public void dataforskip(String nameForReport,
			ExtentTest testreport, ExtentTest testreportSkip, String HOSP_URL) throws Exception {
		Keywords keywords = new Keywords();
		try{
			testreportSkip.log(LogStatus.SKIP,
					keywords.resultSKIP(HOSP_URL,  nameForReport));
		}
		catch(ExtentTestInterruptedException e)
		{
			testreportSkip.log(LogStatus.SKIP,
					keywords.resultSKIP(HOSP_URL,  nameForReport));
		}
		}
	

	public String resultPASS(String CityName, String Expected_data, String Actual_data, String data) throws Exception {

		String text = 
				"</br></span>" + "<span style='font-weight:bold;color:black'>" + "Expected " + data + ": " + Expected_data
				+ "</br></span>" + "<span style='font-weight:bold;color:black'>" + "Actual " + data + ": " + Actual_data
				+ "</br></span>";
		return text;
	}

	public String resultFAIL(String Hosp_URL, String CityName, String Expected_data, String Actual_data, String data)
			throws Exception {

		String text = "<span style='font-weight:bold;color:blue'>" + Hosp_URL + "</br></span>"
				+ "<span style='font-weight:bold;color:black'>" + "City Name: " + CityName
				+ "<span style='font-weight:bold;color:black'>" + "Expected " + data + ": " + Expected_data
				+ "</br></span>" + "<span style='font-weight:bold;color:red'>" + "Actual " + data + ": " + Actual_data
				+ "</br></span>";
		return text;
	}
	
	
	
	public String resultPASSContains(String Expected_data, String Actual_data, String data) throws Exception {

		String text = "<span style='font-weight:bold;color:black'>" + "Expected " + data + ": " + Expected_data
				+ "</br></span>" + "<span style='font-weight:bold;color:black'>" + "Actual " + data + ": " + Actual_data
				+ "</br></span>";
		return text;
	}

	public String resultFAILContains(String Expected_data, String Actual_data, String data) throws Exception {

		String text = "<span style='font-weight:bold;color:black'>" + "Expected " + data + ": " + Expected_data
				+ "</br></span>" + "<span style='font-weight:bold;color:red'>" + "Actual " + data + ": " + Actual_data
				+ "</br></span>";
		return text;
	}

	public String resultFAILNEW(String Hosp_URL, String Expected_data, String Actual_data, String data,String name)
			throws Exception {

		String text = "<span style='font-weight:bold;color:blue'>" + Hosp_URL + "</br></span>"
				+ "<span style='font-weight:bold;color:black'>" + "City Name : " + name
				+ "</br></span>"+ "<span style='font-weight:bold;color:black'>" + "Expected " + data + ": " + Expected_data
				+ "</br></span>" + "<span style='font-weight:bold;color:red'>" + "Actual " + data + ": " + Actual_data
				+ "</br></span>";
		return text;
	}
	
	public String resultSKIP(String Hosp_URL, String data)
			throws Exception {

		String text = "<span style='font-weight:bold;color:blue'>" + Hosp_URL + "</br></span>"
				+ "<span style='font-weight:bold;color:black'>"  + data 
				+ "</br></span>";
		return text;
	}

}
