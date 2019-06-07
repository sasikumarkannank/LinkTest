package Marlabs.Constants;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.*;
import org.apache.commons.collections4.MultiMap;
import org.apache.commons.collections4.map.MultiValueMap;

//import Marlabs.Core.Xls_Reader;
public abstract class Constants {

	// Online Education Bachelors Jira ID: OE-1969
	public static String currentURL ="https://health-uat6.usnews.com/health-products";

	public static String TestSuiteName_OE = "Subrankings_final.xlsx";
	public static String TestSuitePath_OE = System.getProperty("user.dir")
			+ "\\src\\Marlabs\\Data\\BestCountries\\" + TestSuiteName_OE;
	public static String Sheetname = "Subrankings";

	
	// Properties file path
	public static String PropertiesFilePath = System.getProperty("user.dir")
			+ "\\src\\Marlabs\\Data\\Procedure_data.properties";

	
	// Us News Link Test
		public static String TestSuiteName_LinkTest = "LinkTest.xlsx";
		public static String TestSuitePath_LinkTest = System.getProperty("user.dir") + "\\src\\Marlabs\\Data\\"
				+ TestSuiteName_LinkTest;
		public static String TestSuiteSheetname_LinkTest = "Sheet1";
		public static String TestEnvironment = "Environment";
		public static String Mainsheet = "Mainsheet";
		public static String FINANCE_SURVEY = "FINANCE_SURVEY";
	
	// Multimap Section------------------

	// Online education
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static MultiMap<String,?> multiMapForOnlineEducation = new MultiValueMap();
}
