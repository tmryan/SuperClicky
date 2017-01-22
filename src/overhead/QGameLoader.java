package tryan.inq.overhead;

import java.util.concurrent.ThreadLocalRandom;

import tryan.inq.controls.QGameController;
import tryan.inq.gfx.QGraphics;
import tryan.inq.gfx.QInventoryCell;
import tryan.inq.gfx.QInventoryItem;
import tryan.inq.gfx.QInventoryScene;
import tryan.inq.gfx.QScene;
import tryan.inq.state.QGameState;
import tryan.inq.state.QGameWinConditions;
import tryan.inq.state.QInventoryCellState;
import tryan.inq.state.QInventoryItemState;
import tryan.inq.state.QInventoryState;
import tryan.inq.state.QItemType;
import tryan.inq.state.QMainMenuState;

/**
 * This class serves as the entry point into the Super Clicky game. 
 * 
 * Its responsibilities include loading resources and major systems, presenting 
 * the player with the main menu, managing load time game settings, launching 
 * the game controller, and (currently) initializing game resources.
 * 
 * @author 		Thomas Ryan (Jan 2017)
 * @version		0.2 (Last update: Jan 2017)
 *
 */

public class QGameLoader {
	public static void main(String[] args) {
		// Creating initial game settings values
		int SCREEN_WIDTH = 300;
		int SCREEN_HEIGHT = 300;
		int LOOP_DELAY_MS = 16;
		
		// Creating loading screen if needed
		
		// Loading resources
		QResourceManager resMan = new QResourceManager();
		QResourceLoader.loadResources(resMan);
		
		// Creating game settings
		QGameSettings settings = new QGameSettings(SCREEN_WIDTH, SCREEN_HEIGHT, LOOP_DELAY_MS);
		
		// Creating game state
		QGameState gameState = new QGameState();
			
		// Creating graphics engine
		QGraphics gfx = new QGraphics(gameState);
		
		// Initializing game resources
		initializeGame(resMan, gameState, gfx);
		
		// Creating game controller and game settings objects
		new QGameController(resMan, gameState, gfx, settings);
	}
	
	////////////////////
	// Game Generation
	////////////////
	
	// Note: Temporary location for game generation
	
	// Tile type enum for randomization
	public enum TileType {
		RED(QItemType.RED),
		BLUE(QItemType.BLUE),
		PURPLE(QItemType.PURPLE),
		GREEN(QItemType.GREEN);
		
		private final QItemType itemType;
		
		private TileType(QItemType itemType) {
			this.itemType = itemType;
		}
		
		public QItemType getItemType() { return itemType; }
	}	
	
	// Generating game scenes
	private static void initializeGame(QResourceManager resMan, QGameState gameState, QGraphics gfx) {
		int INIT_WIDTH = 300;
		int INIT_HEIGHT = 300;
		int ROW_SIZE = 3;
		int NUM_CELLS = ROW_SIZE * ROW_SIZE;
		int HINT_DELAY = 2000;
		
		// Creating initial screen
		QMainMenuState mainMenuState = new QMainMenuState(INIT_WIDTH, INIT_HEIGHT, gameState);
		QScene mainMenuScene = new QScene(INIT_WIDTH, INIT_HEIGHT, resMan, resMan.getImage("mainScreen"));
		gameState.addSceneState(mainMenuState);
		mainMenuScene.attachSceneState(mainMenuState);
		gfx.addScene(mainMenuScene, mainMenuState.getSceneStateId());
		
		int nextSceneId;
		QInventoryState sceneState;
		QInventoryScene scene;
		TileType[] chosenTiles;
		QGameWinConditions winConditions;

		for(int i = 3; i <= NUM_CELLS; i++) {
			// Creating scene and sceneState
			sceneState = new QInventoryState(INIT_WIDTH, INIT_HEIGHT, gameState, ROW_SIZE, 100, HINT_DELAY);
			scene = new QInventoryScene(INIT_WIDTH, INIT_HEIGHT, resMan, resMan.getImage("breakScreen"));

			// Generating random tileset for this level and populating game board
			chosenTiles = generateGameGrid(resMan, sceneState, scene, ROW_SIZE, 100, i, 1);
			
			// Attaching model to view
			gameState.addSceneState(sceneState);
			scene.attachSceneState(sceneState);
			gfx.addScene(scene, sceneState.getSceneStateId());
			
			// Setting win conditions
			if(i == NUM_CELLS) {
				nextSceneId = 9999;
			} else {
				nextSceneId = sceneState.getSceneStateId() + 1;
			}
			winConditions = new QGameWinConditions(3, nextSceneId);
			generateWinConds(resMan, winConditions, scene, ROW_SIZE, 100, i, 1, chosenTiles);
			sceneState.setWinConditions(winConditions);
		}
	}
	
	// Generates cell grid for each scene state
	private static TileType[] generateGameGrid(QResourceManager resMan, QInventoryState sceneState, QInventoryScene scene, 
			int rowSize, int cellWidth, int numTiles, int difficulty) 
	{
		QInventoryCellState[][] cellGrid = sceneState.getCellGrid();
		
		// Note: Using cellWidth for width and height temporarily
		// Populating inventory with cells
		for(int i = 0; i < rowSize; i++) {
			for(int j = 0; j < rowSize; j++) {
				// Populating game grid with cell states
				cellGrid[i][j] = new QInventoryCellState(i * cellWidth, j * cellWidth, cellWidth, cellWidth, j, i, QGameState.generateActorId());
				
				// Creating cell actor and attaching cell state
				QInventoryCell cell = new QInventoryCell(resMan.getImage("cellBG"), resMan, 1);
				cell.attachActorState(cellGrid[i][j]);
				
				// Adding cell actor to scene
				scene.addActor(cell);
			}
		}
		
		// Creating list of tiles to place
		TileType[] tileList = generateTileList(numTiles);
		TileType[] chosenTiles = new TileType[numTiles];
		
		for(int k = 0; k < numTiles; k++) {
			// Generating random i and j
			int i = ThreadLocalRandom.current().nextInt(rowSize);
			int j = ThreadLocalRandom.current().nextInt(rowSize);

			// Placing tiles if randomized cell is empty
			if(cellGrid[i][j].isEmpty()) {
				// Populating game grid with randomly placed tile states
				cellGrid[i][j].setItemState(new QInventoryItemState(cellGrid[i][j].getX(), cellGrid[i][j].getY(), 
						cellWidth, cellWidth, QGameState.generateActorId(), tileList[k].getItemType()));
							
				// Creating item actor and attaching item state
				QInventoryItem item = new QInventoryItem(null, resMan, 2);
				item.attachActorState(cellGrid[i][j].getItemState());
				
				// Remembering chosen tile
				chosenTiles[k] = tileList[k];
				
				// Adding item actor to scene
				scene.addActor(item);
			} else {
				k--;
			}
		}
		
		return chosenTiles;
	}
	
	// Generates win conditions for each scene state
	private static void generateWinConds(QResourceManager resMan, QGameWinConditions winConditions, QInventoryScene scene, 
			int rowSize, int cellWidth, int numTiles, int difficulty, TileType[] chosenTiles) 
	{
		QInventoryCellState[][] cellGrid = winConditions.getCellGrid();
		
		// Populating inventory with cells
		for(int i = 0; i < rowSize; i++) {
			for(int j = 0; j < rowSize; j++) {
				// Populating game grid with cell states
				cellGrid[i][j] = new QInventoryCellState(i * cellWidth, j * cellWidth, cellWidth, cellWidth, j, i, QGameState.generateActorId());
				
				// Creating cell actor and attaching cell state
				QInventoryCell cell = new QInventoryCell(resMan.getImage("hintCellBG"), resMan, 1);
				cell.attachActorState(cellGrid[i][j]);
				
				// Adding cell actor to scene
				scene.addWinConditionActor(cell);
			}
		}
		
		for(int k = 0; k < numTiles; k++) {
			// Generating random i and j
			int i = ThreadLocalRandom.current().nextInt(rowSize);
			int j = ThreadLocalRandom.current().nextInt(rowSize);

			// Placing tiles if randomized cell is empty
			if(cellGrid[i][j].isEmpty()) {
				// Populating win condition grid with tiles from game grid
				cellGrid[i][j].setItemState(new QInventoryItemState(cellGrid[i][j].getX(), cellGrid[i][j].getY(), 
						cellWidth, cellWidth, QGameState.generateActorId(), chosenTiles[k].getItemType()));
								
				// Creating item actor and attaching item state
				QInventoryItem item = new QInventoryItem(null, resMan, 2);
				item.attachActorState(cellGrid[i][j].getItemState());
				
				// Adding item actor to scene
				scene.addWinConditionActor(item);
			} else {
				k--;
			}
		}
	}
	
	// Picks numTiles tiles at random from possible types
	public static TileType[] generateTileList(int numTiles) {
		TileType[] tileList = new TileType[numTiles];		
		
		// Radmonly generating list of possible tiles from set of all tile types
		for(int i = 0; i < numTiles; i++) {
			tileList[i] = TileType.values()[ThreadLocalRandom.current().nextInt(TileType.values().length)];
		}
		
		return tileList;
	}
	
}
