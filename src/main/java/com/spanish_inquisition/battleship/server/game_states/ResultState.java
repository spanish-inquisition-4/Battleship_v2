package com.spanish_inquisition.battleship.server.game_states;

import com.spanish_inquisition.battleship.common.Header;
import com.spanish_inquisition.battleship.common.NetworkMessage;
import com.spanish_inquisition.battleship.server.Player;
import com.spanish_inquisition.battleship.server.Players;
import com.spanish_inquisition.battleship.server.bus.MessageBus;

import static com.spanish_inquisition.battleship.server.BattleshipServer.SERVER_ID;

public class ResultState extends GameState {
    public ResultState(Players players, MessageBus requestBus) {
        super(players, requestBus);
    }

    @Override
    public GameState transform() {
        buildWinnerResponse();
        return new EndState(players, requestBus);
    }

    private void buildWinnerResponse() {
        Player winner = players.getWinner();
        if(winner == null) {return;}
        NetworkMessage winningMessage = new NetworkMessage(Header.GAME_WON, "true");
        NetworkMessage losingMessage = new NetworkMessage(Header.GAME_WON, "false");
        requestBus.addMessage(SERVER_ID, players.getWinner().getPlayerId(), NetworkMessage.Parser.parseMessageToString(winningMessage));
        requestBus.addMessage(SERVER_ID, players.getOpponentOf(players.getWinner()).getPlayerId(), NetworkMessage.Parser.parseMessageToString(losingMessage));
    }
}
