package com.mycompany.rockpaperscissors;


import java.io.*;
import java.net.*;

public class RockPaperScissors {
    public static void main(String[] args) {
        int port = 12345;
        try {
            DatagramSocket socket = new DatagramSocket(port);

            while (true) {
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);

                String opponentChoice = new String(receivePacket.getData(), 0, receivePacket.getLength());

                String[] choices = {"rock", "paper", "scissors"};
                String myChoice = choices[(int) (Math.random() * 3)];

                String result = calculateWinner(myChoice, opponentChoice);

                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();
                String message = "You chose: " + opponentChoice + "\n";
                message += "I chose: " + myChoice + "\n";
                message += result;
                byte[] sendData = message.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                socket.send(sendPacket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String calculateWinner(String myChoice, String opponentChoice) {
        if (myChoice.equals(opponentChoice)) {
            return "It's a tie!";
        } else if ((myChoice.equals("rock") && opponentChoice.equals("scissors")) ||
                (myChoice.equals("paper") && opponentChoice.equals("rock")) ||
                (myChoice.equals("scissors") && opponentChoice.equals("paper"))) {
            return "Server win!";
        } else {
            return "Client win!";
        }
    }
}
