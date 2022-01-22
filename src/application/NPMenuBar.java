package application;

import javafx.geometry.Insets;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCombination;

public class NPMenuBar extends MenuBar{
	
	private Menu file, edit, help;
	private MenuItem newF, open, save, saveAs, exit, about;
	
	public NPMenuBar() {
		createMenubar();
	}
	
	private void createMenubar() {
		
		// file menu
		file = new Menu("File");
		
		newF = new MenuItem("New");
		newF.setAccelerator(KeyCombination.keyCombination("SHORTCUT+N"));
		open = new MenuItem("Open");
		open.setAccelerator(KeyCombination.keyCombination("SHORTCUT+O"));
		save = new MenuItem("Save");
		save.setAccelerator(KeyCombination.keyCombination("SHORTCUT+S"));
		saveAs = new MenuItem("Save As...");
		saveAs.setAccelerator(KeyCombination.keyCombination("SHORTCUT+SHIFT+S"));
		exit = new MenuItem("Exit");
		file.getItems().addAll(newF, open, save, saveAs, new SeparatorMenuItem(), exit);
		
		// edit menu
		edit = new Menu("Edit");
		
		// help menu
		help = new Menu("Help");
		about = new MenuItem("About");
		help.getItems().addAll(about);
		
		this.setPadding(new Insets(0));
		this.getMenus().addAll(file, edit, help);
	}

}
