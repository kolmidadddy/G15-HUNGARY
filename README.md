#G15-H UN G A R Y 
##Introduction 
A meal calendar and grocery management application named Eatsy. It is an Android application compiled entirely in Java programming language and powered by Firebase. 
Allow users to manage their inventory, generating smart shopping lists and easily plan and keep track of meals with a monthly calendar. 
With Eatsy, wave goodbye to food waste and hello to convenience. 
##Features 
- Calendar Meal Planner 
- Grocery Management 
- Shopping List 
- Notifications 
- Guide 
- Contact us 

##Usage 
###Calendar Meal Planner 
1. Timezone can be changed from the date format from CalendarView we used GMT+8 according to current time Kuala Lumpur, Malaysia offset. 
2. Used for meal planning with meal description and ingredients. 
3. Users could pick a date and create, delete, or change the items by retrieving the meal related data from Firebase which was integrated into 
the CalendarView in global variable. 
4. For items that are in 'RED', it'll pop up a taste message when users are planning their meal.

###Grocery Management, 
1. Handle your grocery, shopping and storage. 
2. Locate arranged items/products designation.(e.g whenever you couldn't find the items, you need not call for your MOM anymore) 
3. Customized toast message to notify the expiration as a reminder for expiring items. 
4. It is designed for users are required to fill up the specific location whenever an item is added. 
5. Essentials details are stored; such as item's name, brand, quantity and expiry date. 

###Category 
1. Display the purchased ingredients
2. It will then depict the details of each categories items.
3. Users can also update/delete the items.
4. One fun-fact is users are not allowed to edit before they click on the 'item_id' to indicate a pop message

###Shopping List, Notifications, Guide, Contact Us 
1. Shopping lists has its advantages where users could keep track of their buying list 
2. From the list, we could differentiate the color by 'RED' it means the items is unavailable when we compared it to the grocery management.
