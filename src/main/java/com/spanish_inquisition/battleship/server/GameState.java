package com.spanish_inquisition.battleship.server;

public abstract class GameState {
    public boolean isGameRunning() {
        return true;
    }

    public abstract GameState transform();
}
