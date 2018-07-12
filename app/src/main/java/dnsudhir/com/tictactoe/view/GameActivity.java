package dnsudhir.com.tictactoe.view;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import dnsudhir.com.tictactoe.R;
import dnsudhir.com.tictactoe.databinding.ActivityGameBinding;
import dnsudhir.com.tictactoe.model.Player;
import dnsudhir.com.tictactoe.viewmodel.GameViewModel;

import static dnsudhir.com.tictactoe.utilities.StringUtility.isNullOrEmpty;

public class GameActivity extends AppCompatActivity {

  private static final String GAME_BEGIN_DIALOG_TAG = "game_dialog_tag";
  private static final String GAME_END_DIALOG_TAG = "game_end_dialog_tag";
  private static final String NO_WINNER = "No one";
  private GameViewModel gameViewModel;


  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_game);
    promptForPlayers();

  }

  public void promptForPlayers() {
    GameBeginDialog dialog = GameBeginDialog.newInstance(this);
    dialog.setCancelable(false);
    dialog.show(getSupportFragmentManager(), GAME_BEGIN_DIALOG_TAG);
  }

  public void onPlayersSet(String player1, String player2) {
    initDataBinding(player1, player2);
  }

  private void initDataBinding(String player1, String player2) {
    ActivityGameBinding activityGameBinding = DataBindingUtil.setContentView(this, R.layout.activity_game);
    gameViewModel = ViewModelProviders.of(this).get(GameViewModel.class);
    gameViewModel.init(player1, player2);
    activityGameBinding.setGameViewModel(gameViewModel);
    setUpOnGameEndListener();
  }

  private void setUpOnGameEndListener() {
    gameViewModel.getWinner().observe(this, this::onGameWinnerChanged);
  }

  public void onGameWinnerChanged(Player winner) {
    String winnerName = winner == null || isNullOrEmpty(winner.name) ? NO_WINNER : winner.name;
    GameEndDialog dialog = GameEndDialog.newInstance(this, winnerName);
    dialog.setCancelable(false);
    dialog.show(getSupportFragmentManager(), GAME_END_DIALOG_TAG);
  }
}
