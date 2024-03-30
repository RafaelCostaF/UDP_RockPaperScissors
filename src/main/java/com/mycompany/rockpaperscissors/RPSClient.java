/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.rockpaperscissors;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class RPSClient {
    public static void main(String[] args) {

        String host = "localhost";
        int port = 12345;
        Scanner scanner = new Scanner(System.in);
        
        // Prompt the user to enter their choice
        System.out.print("Enter your choice (Rock, Paper, or Scissors): ");
        String choice = scanner.nextLine();

        try {
            DatagramSocket socket = new DatagramSocket();

            InetAddress serverAddress = InetAddress.getByName(host);
            byte[] sendData = choice.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, port);
            socket.send(sendPacket);

            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);

            String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println(response);

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
