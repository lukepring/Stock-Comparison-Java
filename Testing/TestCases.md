 # System Test Cases

These test cases validate the functionality, input validation, and reliability of the share comparison system.

## Test 1: Valid share comparison test  
This test checks the main functionality of the system by comparing two valid shares within an acceptable date range.  
Input: AAPL and MSFT with a valid date range within two years  
Expected result: The system retrieves historical data for both shares and displays them on a single graph with correct labels  
Actual result: Data is successfully retrieved and both shares are displayed clearly on the graph  
Result: Pass  
<img width="1381" height="866" alt="image" src="https://github.com/user-attachments/assets/9f3d9ad7-76d6-4b58-a360-fa5515db417e" />

---

## Test 2: Blank field validation test  
This test checks that the system enforces required input fields and prevents incomplete submissions.  
Input: Leave one ticker field empty and attempt to compare  
Expected result: The system displays a missing field message and does not process the request  
Actual result: The system prevents submission and shows a validation message  
Result: Pass  
<img width="1318" height="270" alt="image" src="https://github.com/user-attachments/assets/da6a7864-a22d-401f-84db-af67ded9829e" />

---

## Test 3: Invalid ticker symbol test  
This test checks how the system handles incorrect or non existent ticker symbols.  
Input: XXXX and AAPL with a valid date range  
Expected result: The system shows feedback that the ticker is invalid and does not attempt to display incorrect data  
Actual result: The system displays an error message and does not crash  
Result: Pass  

---

## Test 4: Maximum date range test  
This test checks that the system correctly allows the maximum supported range of two years.  
Input: Valid tickers with a date range of exactly two years  
Expected result: The system accepts the input and retrieves the data  
Actual result: Data is retrieved and displayed without issues  
Result: Pass  
<img width="1301" height="858" alt="image" src="https://github.com/user-attachments/assets/8c115e42-c688-49e5-9dd4-6951ca04bd26" />

---

## Test 5: Exceeding date range test  
This test checks whether the system enforces the two year limit.  
Input: Valid tickers with a date range greater than two years  
Expected result: The system rejects the input and displays a validation message  
Actual result: The system blocks the request and informs the user  
Result: Pass  
<img width="1097" height="202" alt="image" src="https://github.com/user-attachments/assets/d2e774ad-f261-4b16-a37f-6fc580a1beb5" />

---

## Test 6: Invalid date order test  
This test ensures that the system handles logical errors in date selection.  
Input: Start date later than end date  
Expected result: The system rejects the input and prompts the user to correct it  
Actual result: The system displays an error message and does not proceed  
Result: Pass  
<img width="1336" height="501" alt="image" src="https://github.com/user-attachments/assets/503916c4-e324-4983-840e-7896b0877f2b" />

---

## Test 7: Graph display test  
This test checks whether the system correctly visualises the retrieved data.  
Input: Valid share comparison request  
Expected result: A graph is displayed with price on the vertical axis and date on the horizontal axis  
Actual result: Graph is displayed correctly with clear data points  
Result: Pass  
<img width="1266" height="617" alt="image" src="https://github.com/user-attachments/assets/388a77b7-5b6d-4e2b-ae09-9074d4726fc1" />

---

## Test 8: Chart labelling test  
This test ensures that users can clearly identify which data belongs to which share.  
Input: Compare AAPL and MSFT  
Expected result: Each share is labelled clearly in the chart legend  
Actual result: Both shares are labelled correctly and are distinguishable  
Result: Pass  
<img width="1266" height="617" alt="image" src="https://github.com/user-attachments/assets/b38f559c-9bf3-4a82-87c2-72c872b9eaba" />

---

## Test 9: Offline data retrieval test  
This test checks the system’s ability to use stored data when the external data source is unavailable.  
Input: Disconnect network after previously retrieving share data  
Expected result: The system loads stored data instead of failing  
Actual result: Previously stored data is displayed successfully  
Result: Pass  

---

## Test 10: External data retrieval failure test  
This test checks how the system behaves when it cannot retrieve data from the external source.  
Input: Disable internet connection and request data that is not stored locally  
Expected result: The system displays an error message indicating that data retrieval failed  
Actual result: The system shows a failure message without crashing  
Result: Pass  

---

## Test 11: Manual refresh test  
This test checks whether the system can update existing share data when requested.  
Input: Load share data, then use the refresh function  
Expected result: The system retrieves updated data from the external source and updates the graph  
Actual result: The graph refreshes and reflects the latest available data  
Result: Pass  

---

## Test 12: Date range impact test  
This test checks that changing the date range correctly affects the displayed data.  
Input: Compare the same shares using different date ranges  
Expected result: The graph updates to reflect the selected time period with different data values  
Actual result: The graph changes according to the selected dates  
Result: Pass  
