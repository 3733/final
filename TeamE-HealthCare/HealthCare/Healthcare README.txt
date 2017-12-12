
Team E - Emerald Elbow Witches Healthcare API

To run the API: 

1. Download and extract the Healthcare folder in any location on your computer. 
2. The JAR file is located under the \libs folder. 
3. Add the healthcare JAR file as a module in your Intellij project by navigating to it in Project structure. 
4. The CSV file location for changing your email to what you want (Related to the healthcare API): build/libs/HealthAPI/AdminEmailInfo.csv
	- This is the file you will need to go into to create a specific email and password to log into the API as an admin. 
5. Paste this code into your patient controller, in whatever method you want for it to trigger, whether it be on a certain button or on launch: 
	
	HealthCareRun health = new HealthCareRun();
        try {
            health.run(0,0,600,350,"view/stylesheets/default.css","","");
        } catch (Exception e) {
            e.printStackTrace();
        }
		
	
	Note: putting in a negative x/y value will make the API launch in the center of the screen.
	
	If you have any further questions or need explanation, get in contact with us!
	