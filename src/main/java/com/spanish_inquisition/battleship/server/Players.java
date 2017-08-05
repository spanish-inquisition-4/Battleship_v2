package com.spanish_inquisition.battleship.server;

import java.util.LinkedList;
import java.util.List;

public class Players {
    Player player1;
    Player player2;
    Player currentPlayer;
    Player winner;

    public Players() {
    }

    public Players(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
    }

    public void switchCurrentPlayer() {
        currentPlayer = getOpponentOf(currentPlayer);
    }

    public Player getOpponentOf(Player player) {
        return player1.equals(player) ? player2 : player1;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void addPlayer(Player player){
        if(player1 == null) {
            player1 = player;
            currentPlayer = player1;
        } else if (player2 == null) {
            player2 = player;
        }
    }

    public List<Player> getBothPlayers() {
        List<Player> list = new LinkedList<>();
        list.add(player1);
        list.add(player2);
        return list;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }
}