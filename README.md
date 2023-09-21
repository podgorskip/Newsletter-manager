## Newsletter manager 
A simple program that manages newsletter subscribers, allowing to send them emails with the news.

### Table of contents
* [General info](#general-info)
* [Features](#features)
* [Technologies used](#technologies-used)


### General info
The program's purpose is to manage the newsletter subscription, allowing for adding, removing new email or sms subscribers.
Provided connection with JavaMail API allows for sending emails to subscribers whose emails are stores in the database.
It is also possible to check news for a specified day of the year.

### Features
* **Adding new email/sms subscribers**: This feature allows administrators to add new subscribers to the newsletter service. Subscribers can opt to receive updates via email or SMS, providing flexibility in communication methods.
* **Removing subscribers**: Administrators have the ability to remove subscribers from the newsletter list. This feature helps maintain an up-to-date and relevant subscriber base.
* **Sending a content to email subscribers**: The program enables administrators to send newsletters or updates to subscribers who have opted for email notifications. It simplifies the process of reaching out to a large audience with news and updates.
* **Checking news for a specified date**: Users can retrieve news or content specifically for a chosen date. This feature is beneficial for users who want to access historical news or events on a particular day, providing a convenient way to retrieve relevant information.

### Technologies used
* **JDBC (Java Database Connectivity)**: Used to connect and interact with the MySQL database.
* **Swing**: Provides the graphical user interface.
* **Java**: The core programming language.
* **MySQL**: Stores product details, administrator accounts, and transaction history.
* **JavaMail API**: Sends emails to gathered users.