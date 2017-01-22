package tryan.inq.overhead;

public class QResourceLoader {
	
	private QResourceLoader() {}
	
	public static void loadResources(QResourceManager resMan) {
		resMan.addImage("mainScreen", "res/img/mainScreen.jpg");
		resMan.addImage("breakScreen", "res/img/breakScreen.jpg");
		resMan.addImage("roundEndOverlay", "res/img/levelEndOverlay.png");
		resMan.addImage("endGameOverlay", "res/img/endOverlay.png");
		resMan.addImage("cellBG", "res/img/InvCell2.jpg");
		resMan.addImage("cellHighlight", "res/img/InvCellHighlight.png");
		resMan.addImage("hintCellBG", "res/img/InvCellHint.jpg");
		resMan.addImage("blueItem", "res/img/invItemTest.png");
		resMan.addImage("greenItem", "res/img/invItemGreen.png");
		resMan.addImage("redItem", "res/img/invItemRed.png");
		resMan.addImage("purpleItem", "res/img/invItemPurple.png");
		resMan.addImage("phItem", "res/img/phItem.png");
	}
	
}
