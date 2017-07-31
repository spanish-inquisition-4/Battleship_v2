package com.spanish_inquisition.battleship.server;

public class EndState extends GameState {
    @Override
    public boolean isGameRunning() {
        return false;
    }

    @Override
    public GameState transform() {
        return null;
    }
}
