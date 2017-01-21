package tryan.inq.overhead;

import java.awt.Color;

public class QResourceLoader {
	
	private QResourceLoader() {}
	
	public static void loadResources(QResourceManager resMan) {
		resMan.addImage("cellBG", "res/img/InvCell2.jpg");
		resMan.addImage("blueItem", "res/img/invItemTest.png");
		resMan.addImage("greenItem", "res/img/invItemGreen.png");
		resMan.addImage("redItem", "res/img/invItemRed.png");
		resMan.addImage("purpleItem", "res/img/invItemPurple.png");
		resMan.addImage("phItem", "res/img/phItem.png");

		resMan.addColor("highlightClr", new Color(23, 164, 255));
	}
	
}
