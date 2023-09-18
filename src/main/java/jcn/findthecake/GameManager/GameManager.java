package jcn.findthecake.GameManager;

import jcn.findthecake.Main.FindTheCake;

public class GameManager {
    private FindTheCake plugin;
    private GameState gameState = GameState.Nothing;

    public GameManager(FindTheCake plugin){
        this.plugin = plugin;
    }

    public GameState getGameState(){
        return gameState;
    }

    public void setGameState(GameState gameState){
        this.gameState = gameState;
    }
}
