import com.laudynetwork.gameengine.game.Game;
import com.laudynetwork.gameengine.game.GameType;
import com.laudynetwork.gameengine.game.gamestate.GameState;

public class TestGame extends Game {
    public TestGame() {
        super(new GameType("MANHUNT"), 100, 10);
    }

    @Override
    public void onGameStateChange(GameState newState, GameState oldState) {

    }
}
