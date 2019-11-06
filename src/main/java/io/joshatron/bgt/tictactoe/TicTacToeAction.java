package io.joshatron.bgt.tictactoe;

import io.joshatron.bgt.engine.action.Action;
import io.joshatron.bgt.engine.component.board.grid.GridBoardLocation;
import io.joshatron.bgt.engine.player.PlayerIndicator;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TicTacToeAction extends Action {
    private GridBoardLocation location;

    public TicTacToeAction(PlayerIndicator player, GridBoardLocation location) {
        super(player);
        this.location = location;
    }
}
