# athDGS-ejb

Project description

Steam is a distribution platform for software in general. Steam is also known for selling games for the PC as a target platform. Installation and maintenance are automated via the Steam client and it is also possible to download games before the official release date, especially if the amount of data includes several gigabytes. 

For the reasons mentioned above, it is common for third-party vendors to sell license keys for Steam games. From this starting position the requirement is derived to develop software to enable the client to establish itself in the market as an official retailer. 
In order to come one step closer to this goal, four software components are being developed. 

In the EJB module are the actual implementations of the interfaces. Enterprise Session Beans are used to have read and write access to the database using DAO classes. In the DAO classes we use JPQL to perform queries, changes and deletions. In the Message Driven Bean class, messages are read from the Message Broker using the JMS provider and emails are sent from the local session bean to the users. 
