# OPSC-Part-3 POE
OPSEC POE part 3 github repository 

Math Budget App

**Introduction**
This reposotory has the code of the budget app called Math Budget App. It was developed by a team of software development students for a OPSC POE submision Part 3. It tracks your expenses in different catagories for easy budgeting and tracking expendature.

**Participants** 
*Aaron Adams 
*Kamvalethu Mdanjelwa
*Joel Mbya
*Neville Kalala
*David Ayowa

Program Language 
For this application we used kotlin as the base apllication language we also used SQL for the databse

Databse
For the database we used Roomdb as per the requirements it ustilises SQL and local connection to opperate.

Functions

Register 
First page is the register screen to register or log in to an existing account, also has logo image

LOGIN
After registering you login to your axount has a similar layout to register.

Menu
Has a scroll down function for displaying saved data, includes Total expenses, expenses and catagories. It also has the budget goal amount with how long that goal lasts. The botom is an cross which is a button to add catagories. It also has a drawer with and item, catagory, Total expenses, expenses and catagories buttons. The top right is the refresh button.

Catagory
This layout provides key UI elements for managing budget categories, including EditText fields for the category name, limit, and goal, as well as SeekBars to set minimum and maximum spending goals. A TextView displays selected goal values dynamically, while buttons enable saving and canceling category entries. Additionally, a FloatingActionButton facilitates navigation to item management for further expense tracking. The background is styled with a light green color (#15F8C3) for visual consistency. Let me know if you'd like further refinements! 

Expenses 
This layout features essential UI components for managing an expense entry: an EditText field for the expense name and description, a Spinner for category selection, and a numeric input field for the amount spent. The user can choose a date and time range using three buttons, ensuring precise tracking of expenses. A photo upload button allows users to attach an image, while a save button commits the data to the database. The entire form is wrapped in a ScrollView, ensuring accessibility on various screen sizes. Let me know if you need further refinements! 

New Activities
Currency Converter
Allows users to convert any amount between South African Rands (ZAR) and other currencies including USD, GBP, and JPY. A spinner enables currency selection, and the result is calculated using predefined exchange rates. The layout matches the app’s theme and includes drawer integration.

Budget Tips
Presents budgeting advice for different user types:
- Everyday People: Tips focus on monthly financial habits, subscription control, and meal planning.
- Students: Advice includes textbook savings, discount usage, and tracking pocket money. Two buttons let users toggle between these audiences. Drawer navigation and visual consistency with the home screen are maintained.

Graph View
Visualizes user expenses and category breakdown using bar charts or pie charts. Helps users better understand their spending patterns. Fully accessible from the drawer and button.

Rewards
Motivates users with achievements like “Goal Setter” after setting a budget target. Tracked via shared preferences and database queries. A dedicated screen displays unlocked rewards.

Progress 
Feature for progress tracking of financial goals and achievements.


Youtube link
https://youtu.be/5TP4k8bAlSY 
