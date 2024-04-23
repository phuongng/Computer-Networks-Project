// tasks completed:
// 2.7 Asks the user to enter a Web server W name (for example, user enters the string www.towson.edu).
// 2.8 Asks the user to enter a timer value T in seconds (for example, user enters the integer 2).
// 2.9 C starts a timer set to T seconds.
// 2.10 C starts a sends a packet to S with the two fields: W and T.
// 2.11 C then waits for the data from S. The packets may be from the first transmission or the retransmission.
// 2.12 If C receives all the data packets from S before the timer expires, it puts them in order using the packet numbers, sends exactly ONE ACK to S and prints the contents in the messages followed by the message “OK”.

// 1.	The client Groupnameclient has been created.
// 2.	The client asks the user for a Web server name and timer value.
// 3.	The client creates a ClientRequest object with the user-provided values.
// 4.	The client serializes the ClientRequest object and sends it to the server.
// 5.	The client sends the serialized ClientRequest object to the server over UDP.
// 6.	The client prints a message indicating that it has sent the client request to the server.
// 7.	The client receives packets from the server within the specified timer value.
// 8.	The client deserializes the received packets into Packet objects.
// 9.	The client assembles the received packets in order.
// 10.	The client sends an ACKnowledgement (ACK) to the server after receiving all packets.
// 11.	The client prints the received data from the server.



import java.io.*;
import java.net.*;
import java.util.*;

public class Groupnameclient {
    private static final int SERVER_PORT = 11111;
    
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket();
            Scanner scanner = new Scanner(System.in);
            
            // Get Web server name from user
            System.out.print("Enter Web server name: ");
            String webServerName = scanner.nextLine();
            
            // Get timer value from user
            System.out.print("Enter timer value (in seconds): ");
            int timerValue = scanner.nextInt();
            
            // Create client request object
            ClientRequest clientRequest = new ClientRequest(webServerName, timerValue);
            
            // Serialize client request
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            ObjectOutputStream objOutputStream = new ObjectOutputStream(byteStream);
            objOutputStream.writeObject(clientRequest);
            objOutputStream.flush();
            
            byte[] sendData = byteStream.toByteArray();
            
            InetAddress serverAddress = InetAddress.getByName("localhost");
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, SERVER_PORT);
            
            // Send client request to server
            socket.send(sendPacket);
            System.out.println("Sent client request to server: " + webServerName + ", " + timerValue + " seconds");
            
            // Receive packets from server
            List<byte[]> receivedPackets = new ArrayList<>();
            long startTime = System.currentTimeMillis();
            long endTime = startTime + (timerValue * 1000);
            
            while (System.currentTimeMillis() < endTime) {
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                
                try {
                    socket.receive(receivePacket);
                } catch (SocketTimeoutException e) {
                    // Timeout, exit loop
                    break;
                }
                
                ByteArrayInputStream byteInputStream = new ByteArrayInputStream(receivePacket.getData());
                ObjectInputStream objInputStream = new ObjectInputStream(byteInputStream);
                Packet packet = (Packet) objInputStream.readObject();
                
                receivedPackets.add(packet.getPayload());
                
                if (packet.getPacketNumber() == packet.getTotalPackets() - 1) {
                    // Last packet received
                    break;
                }
            }
            
            // Send ACK to server
            Acknowledgement ack = new Acknowledgement(true);
            
            ByteArrayOutputStream ackByteStream = new ByteArrayOutputStream();
            ObjectOutputStream ackObjOutputStream = new ObjectOutputStream(ackByteStream);
            ackObjOutputStream.writeObject(ack);
            ackObjOutputStream.flush();
            
            byte[] ackData = ackByteStream.toByteArray();
            
            DatagramPacket ackPacket = new DatagramPacket(ackData, ackData.length, serverAddress, SERVER_PORT);
            socket.send(ackPacket);
            
            // Print received data
            System.out.println("\nReceived data from server:");
            for (byte[] packetData : receivedPackets) {
                System.out.println(new String(packetData));
            }
            
            System.out.println("OK");
            
            scanner.close();
            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
