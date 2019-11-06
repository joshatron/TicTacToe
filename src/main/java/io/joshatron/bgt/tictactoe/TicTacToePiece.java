package io.joshatron.bgt.tictactoe;

import io.joshatron.bgt.engine.component.piece.Piece;
import io.joshatron.bgt.engine.player.PlayerIndicator;

public class TicTacToePiece extends Piece {
    public TicTacToePiece(PlayerIndicator owner) {
        super(owner);
    }

    @Override
    public String toString() {
        switch(getOwner()) {
            case PLAYER_0:
                return "X";
            case PLAYER_1:
                return "O";
            default:
                return "";
        }
    }
}
