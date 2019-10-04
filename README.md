# message-passer
Java application that implements the message exchange via threads and via sockets.

Given a Player class - an instance of which can communicate with other Players. 
One of the players should send a message to second player.
When a player receives a message, it should reply with a message that contains the received message concatenated with the value of a counter 
holding the number of messages this player already sent. 
The program should be finalized after the initiator sent 10 messages and received back 10 messages.

In multithreaded implementation both players run in the same Java process.
In socket implementation - in separate Java processes.
