package application;

import javafx.geometry.Insets;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCombination;

public class NPMenuBar extends MenuBar{
	
	private MainView mainView;
	private Menu file, edit, help;
	private MenuItem newF, open, save, saveAs, print, exit, about;
	
	public NPMenuBar(MainView mainView) {
		this.mainView = mainView;
		createMenubar();
	}
	
	private void createMenubar() {
		
		// file menu
		file = new Menu("File");
		
		// create "New" menu item and perform clear operation using it
		newF = createMenuItemWithKeyCombination("New", "SHORTCUT+N");
		newF.setOnAction(event -> mainView.performActionOnTextArea(textArea -> textArea.clear()));
		
		open = createMenuItemWithKeyCombination("Open", "SHORTCUT+O");
		save = createMenuItemWithKeyCombination("Save", "SHORTCUT+S");
		
		saveAs = createMenuItemWithKeyCombination("Save As...", "SHORTCUT+SHIFT+S");
		saveAs.setOnAction(event -> mainView.saveFileAs());
		
		print = createMenuItemWithKeyCombination("Print", "SHORTCUT+P");
		exit = new MenuItem("Exit");
		file.getItems().addAll(newF, open, save, saveAs, new SeparatorMenuItem(), print, new SeparatorMenuItem(), exit);
		
		// edit menu
		edit = new Menu("Edit");
		
		// help menu
		help = new Menu("Help");
		about = new MenuItem("About");
		help.getItems().addAll(about);
		
		this.setPadding(new Insets(0));
		this.getMenus().addAll(file, edit, help);
	}
	
	// this method used to create menu item with given String and keyCombination
	private MenuItem createMenuItemWithKeyCombination(String str, String keyComb) {
		MenuItem menuItem = new MenuItem(str);
		menuItem.setAccelerator(KeyCombination.keyCombination(keyComb));
		return menuItem;
	}

}