package dnsudhir.com.tictactoe.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableArrayMap;
import dnsudhir.com.tictactoe.model.Cell;
import dnsudhir.com.tictactoe.model.Game;
import dnsudhir.com.tictactoe.model.Player;
import dnsudhir.com.tictactoe.utilities.StringUtility;

public class GameViewModel extends ViewModel {

  public ObservableArrayMap<String, String> cells;
  private Game game;

  public void init(String player1, String player2) {
    game = new Game(player1, player2);
    cells = new ObservableArrayMap<>();
  }

  public void onClickedCellAt(int row, int column) {
    if (game.cells[row][column] == null) {
      game.cells[row][column] = new Cell(game.currentPlayer);
      cells.put(StringUtility.stringFromNumbers(row, column), game.currentPlayer.value);
      if (game.hasGameEnded()) {
        game.reset();
      } else {
        game.switchPlayer();
      }
    }
  }

  public LiveData<Player> getWinner() {
    return game.winner;
  }
}
