# JavaFX_Socket_JDBC

### Description
This project icludes the following technologies:
* JavaFX for GUI
* JDBC for working with the database (MySQL)
* Socket for transfer data

### Architecture
![Architecture](https://www.guru99.com/images/1/091318_0745_DBMSArchite3.png)

### For run
1. mvn install
2. java -jar server/target/Server.jar - create and initialize the databses, wait for client connection
3. java -jar client/target/Client.jar - create connection and get data from server

### Details
Client and server communicate using events (Event class).The client and server have 2 objects: receiver (EventReceiver class) and sender (EventSender class)

EventSender has object output stream thanks to which it serializes the event and sends it to the network.

EventReceiver implementes Observer pattern. It has object input stream thanks to which it deserializes the event and sends it to observers.
