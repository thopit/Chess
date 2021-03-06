package de.thomas.chess.logic;

/**
 * Interface describing classes listening to game events
 * @author Thomas Opitz
 *
 */
public interface GameChangedListener {
	/**
	 * Called when the game has changed
	 * @param message a message describing the change
	 */
	public void handleMessage(GameMessage message);
}
