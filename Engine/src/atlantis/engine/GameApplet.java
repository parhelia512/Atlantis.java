// AtlantisEngine.java - Copyright (C) Yannick Comte.
// This file is subject to the terms and conditions defined in
// file 'LICENSE', which is part of this source code package.
package atlantis.engine;

import java.awt.Dimension;

import javax.swing.JApplet;

import atlantis.engine.input.KeyboardComponent;
import atlantis.engine.input.MouseComponent;
import atlantis.engine.level.LevelManager;
import atlantis.framework.Game;
import atlantis.framework.GameTime;
import atlantis.framework.IDrawable;
import atlantis.framework.IUpdateable;
import atlantis.framework.platform.JPanelRenderer;

/**
 * Provide basic initialization of an applet
 * @see Game
 * @author Yannick
 */
public class GameApplet extends JApplet implements IUpdateable, IDrawable {
	private static final long serialVersionUID = 5413818823844604622L;
	protected JPanelRenderer renderer;
	protected Game game;
	protected LevelManager levelManager;
	
	public void init() {
		Dimension dim = this.getSize();
		this.setSize(dim.width, dim.height);
		this.setVisible(true);
		this.game = new Game(dim.width, dim.height, "Atlantis Applet");
		this.game.content().setLoadType(0);
		//this.game.getGameWindow().getRenderer().addDrawable(this);

		KeyboardComponent keyboardComponent = new KeyboardComponent(this.game);
		MouseComponent mouseComponent = new MouseComponent(this.game);
		
		this.levelManager = new LevelManager(this.game);
		this.game.components().add(this.levelManager);
		this.game.components().add(keyboardComponent);
		this.game.components().add(mouseComponent);
		
		Application.game = this.game;
		Application.levelManager = levelManager;
		Application.content = this.game.content();
		Application.components = this.game.components();
		
		Input.keys = keyboardComponent;
		Input.mouse = mouseComponent;
	
		Screen.setup(dim.width, dim.height);
	}
	
	/**
	 * Start the game.
	 */
	public void start() {
		this.game.run();
	}
	
	public void setRenderer(JPanelRenderer renderer) {
		this.renderer = renderer;
		this.setContentPane(this.renderer);
	}

	@Override
	public void update(GameTime gameTime) {
		this.game.update(gameTime);
	}

	@Override
	public void draw(GameTime gameTime) {
		this.game.draw(gameTime);
	}
}