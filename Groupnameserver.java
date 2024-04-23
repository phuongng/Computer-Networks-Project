//////// The server S runs as localhost and listens on port 11111. S does the following.
// 2.1 S waits for a client request. It has two fields: Name of a Web server W and a timer value T in seconds.
// 2.2 When the server S gets the client request, it starts a separate handler (thread) for the client C. The server S does
// all the client-related processing and communication using the handler. The main thread in the server S only listens
// for client requests.
// 2.3 S starts a timer set to T seconds.
// 2.4 S sends a GET request to the Web server W over HTTPS using HttpURLConnection.
// 2.5 S stores all the data received from W in memory.
// 2.6 After receiving all the data from W, S sends packets with W’s data to C. The payload in each packet from S to C
// carries 1000 bytes of W’s data except for the payload in the last packet that carries the remaining bytes of W’s data.
// 2.7 If S gets the ACK from C before the timer expires, S prints “DONE”. If the timer expires before receiving the
// ACK from C, S retransmits all the data to W and prints “RESENT”. The data is retransmitted only once and there is
// only ONE ACK from C.


// tasks need to be done:
// 1.	The server listens on port 11111 for incoming UDP packets from clients.
// 2.	When the server receives a client request in the format "WebServerName,TimerValue", it starts a new thread to handle that client's request.
// 3.	The server deserializes the received client request into a ClientRequest object.
// 4.	The server prints the received client request to the console.
// 5.	A separate handler thread (ClientHandler) is started for each client request.
// 6.	The server sends the client request object to the ClientHandler thread.
// 7.	The server sends a GET request to the specified web server over HTTPS.
// 8.	The server receives data from the web server and stores it in memory.
// 9.	The server splits the received data into packets.
// 10.	The server sends these packets to the client.
// 11.	The server starts a timer after sending the packets.
// 12.	The server waits for an ACKnowledgement (ACK) from the client.
// 13.	The server prints "DONE" if it receives the ACK before the timer expires, otherwise it prints "RESENT".


import java.io.*;
import java.net.*;
import java.util.*;

public class Groupnameserver {
    private static final int SERVER_PORT = 11111;
    private static final int PACKET_SIZE = 1000;
    
    public static void main(String[] args) {
        
    }
}
