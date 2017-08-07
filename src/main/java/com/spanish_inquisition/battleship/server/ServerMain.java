package com.spanish_inquisition.battleship.server;

public class ServerMain {
    public static void main(String[] args) {
        System.out.println("Server is running...");
        BattleshipServer server = new BattleshipServer();
        server.proceed();
    }
}
