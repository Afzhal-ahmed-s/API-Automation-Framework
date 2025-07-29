package api.utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {

    // Data Provider for all user data
    @DataProvider(name = "Data")
    public String[][] getAllData() throws IOException {
        // Get the path to the Excel file (UserData.xlsx)
        String path = System.getProperty("user.dir") + "//testData//UserData.xlsx";

        // Create an instance of XLUtility to interact with the Excel file
        XLUtility xl = new XLUtility(path);

        // Get the row count from "Sheet1" (excluding header row)
        int rownum = xl.getRowCount("Sheet1");
        // Get the cell count from the first row (header row) of "Sheet1"
        int colcount = xl.getCellCount("Sheet1", 1); // Assuming header is at row 1 (0-indexed)

        // Declare a 2D array to store the test data
        // Size is (number of data rows) x (number of columns)
        String apidata[][] = new String[rownum][colcount];

        // Loop through rows (starting from the first data row, which is row 1 after header)
        for (int i = 1; i <= rownum; i++) {
            // Loop through columns
            for (int j = 0; j < colcount; j++) {
                // Read cell data and store it in the array
                // apidata[i-1] because the array is 0-indexed and we start reading from row 1 in Excel
                apidata[i - 1][j] = xl.getCellData("Sheet1", i, j);
            }
        }
        return apidata; // Return the 2D array containing all data
    }

    // Data Provider for only usernames
    @DataProvider(name = "UserNames")
    public String[] getUserNames() throws IOException {
        // Get the path to the Excel file
        String path = System.getProperty("user.dir") + "//testData//UserData.xlsx";

        // Create an instance of XLUtility
        XLUtility xl = new XLUtility(path);

        // Get the row count from "Sheet1" (excluding header row)
        int rownum = xl.getRowCount("Sheet1");

        // Declare a 1D array to store usernames. Size is (number of data rows)
        String apidata[] = new String[rownum];

        // Loop through rows (starting from the first data row, which is row 1 after header)
        // We only need the username, which is assumed to be in the second column (index 1)
        for (int i = 1; i <= rownum; i++) {
            apidata[i - 1] = xl.getCellData("Sheet1", i, 1); // Read data from column 1 (Username)
        }
        return apidata; // Return the 1D array containing usernames
    }
}
