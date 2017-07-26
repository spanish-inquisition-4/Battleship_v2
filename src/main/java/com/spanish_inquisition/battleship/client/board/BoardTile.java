package com.spanish_inquisition.battleship.client.board;

import javafx.scene.control.Button;

/**
 * @author Michal_Partacz
 */
public class BoardTile extends Button {

    int index;

    public BoardTile(int index) {
        this.index = index;
    }
}
