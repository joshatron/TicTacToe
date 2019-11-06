package io.joshatron.bgt.tictactoe;

import io.joshatron.bgt.engine.action.ActionResult;
import io.joshatron.bgt.engine.component.board.grid.GridBoard;
import io.joshatron.bgt.engine.engines.inorder.InOrderGameEngine;
import io.joshatron.bgt.engine.engines.inorder.InOrderGameParameters;
import io.joshatron.bgt.engine.exception.BoardGameEngineException;
import io.joshatron.bgt.engine.player.PlayerIndicator;
import io.joshatron.bgt.engine.state.GameStatus;
import io.joshatron.bgt.engine.state.Status;

import java.util.List;
import java.util.stream.Collectors;

public class TicTacToeEngine extends InOrderGameEngine<TicTacToeState,InOrderGameParameters,TicTacToeAction> {

    @Override
    protected boolean isActionValid(TicTacToeState state, TicTacToeAction action) {
        try {
            return state.getBoard().getTile(action.getLocation()).getOwner() == PlayerIndicator.NONE;
        } catch (BoardGameEngineException e) {
            return false;
        }
    }

    @Override
    protected ActionResult updateState(TicTacToeState state, TicTacToeAction action) {
        state.getBoard().getTile(action.getLocation()).setOwner(action.getPlayer());
        checkForWinner(state);
        return null;
    }

    private void checkForWinner(TicTacToeState state) {
        try {
            GridBoard<TicTacToePiece> board = state.getBoard();

            for (int i = 0; i < 3; i++) {
                boolean horizontal = true;
                boolean vertical = true;
                for (int j = 0; j < 3; j++) {
                    if (board.getTile(i, j).getOwner() == PlayerIndicator.NONE || board.getTile(i, j).getOwner() != board.getTile(i, 0).getOwner()) {
                        horizontal = false;
                    }
                    if (board.getTile(j, i).getOwner() == PlayerIndicator.NONE || board.getTile(j, i).getOwner() != board.getTile(0, i).getOwner()) {
                        vertical = false;
                    }
                }

                if(horizontal) {
                    state.setStatus(new GameStatus(Status.COMPLETE, board.getTile(i, 0).getOwner()));
                    return;
                }
                if(vertical) {
                    state.setStatus(new GameStatus(Status.COMPLETE, board.getTile(0, i).getOwner()));
                    return;
                }
            }

            PlayerIndicator corner = board.getTile(0, 0).getOwner();
            if(corner != PlayerIndicator.NONE && board.getTile(1, 1).getOwner() == corner && board.getTile(2, 2).getOwner() == corner) {
                state.setStatus(new GameStatus(Status.COMPLETE, corner));
                return;
            }

            corner = board.getTile(2, 0).getOwner();
            if(corner != PlayerIndicator.NONE && board.getTile(1, 1).getOwner() == corner && board.getTile(0, 2).getOwner() == corner) {
                state.setStatus(new GameStatus(Status.COMPLETE, corner));
            }
        } catch (BoardGameEngineException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected boolean isTurnDone(TicTacToeState state) {
        return true;
    }

    @Override
    public TicTacToeState createInitialStateFromParameters(InOrderGameParameters gameParameters) {
        return new TicTacToeState();
    }

    @Override
    public List<TicTacToeAction> getPossibleActions(TicTacToeState state) {
        return state.getBoard().getAllLocations().parallelStream().filter(location -> {
            try {
                return state.getBoard().getTile(location).getOwner() == PlayerIndicator.NONE;
            } catch (BoardGameEngineException e) {
                return false;
            }
        }).map(location -> {
            try {
                return new TicTacToeAction(state.getCurrentPlayerInfo().getIdentifier(), location);
            } catch (BoardGameEngineException e) {
                return null;
            }
        }).collect(Collectors.toList());
    }
}
