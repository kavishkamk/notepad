package application;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

public class NotepadMenuBar extends MenuBar{
	
	public NotepadMenuBar() {
		createMenubar();
	}
	
	private void createMenubar() {
		
		Menu file = new Menu("File");
		MenuItem newF = new MenuItem("New");
		MenuItem open = new MenuItem("Open");
		MenuItem save = new MenuItem("Save");
		MenuItem saveAs = new MenuItem("Save As...");
		MenuItem exit = new MenuItem("Exit");
		file.getItems().addAll(newF, open, save, saveAs, new SeparatorMenuItem(), exit);
		
		Menu edit = new Menu("Edit");
		
		Menu help = new Menu("Help");
		MenuItem about = new MenuItem("About");
		help.getItems().addAll(about);
		
		this.getMenus().addAll(file, edit, help);
	}

}
