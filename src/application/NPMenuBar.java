package application;

import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCombination;

public class NPMenuBar extends MenuBar{
	
	private MainView mainView;
	private Menu file, edit, help;
	private MenuItem newF, open, save, saveAs, print, exit, about, copy, paste, cut, selectAll;
	
	public NPMenuBar(MainView mainView) {
		this.mainView = mainView;
		createMenubar();
	}
	
	private void createMenubar() {
		
		// file menu
		file = new Menu("File");
		
		/*
		 * create "New" menu item and perform clear operation using it
		 * when perform action clear text area
		 * reset the title bar
		 */
		newF = createMenuItemWithKeyCombination("New", "SHORTCUT+N");
		newF.setOnAction(event -> mainView.newFile());
		
		open = createMenuItemWithKeyCombination("Open", "SHORTCUT+O");
		open.setOnAction(event -> mainView.openFile());
		
		// content save
		save = createMenuItemWithKeyCombination("Save", "SHORTCUT+S");
		save.setOnAction(event -> mainView.saveFile());
		
		// content save as..
		saveAs = createMenuItemWithKeyCombination("Save As...", "SHORTCUT+SHIFT+S");
		saveAs.setOnAction(event -> mainView.saveFileAs());
		
		print = createMenuItemWithKeyCombination("Print", "SHORTCUT+P");
		print.setOnAction(event -> mainView.printText());
		
		exit = new MenuItem("Exit");
		exit.setOnAction(event -> mainView.exitFromPlatform());
		
		file.getItems().addAll(newF, open, save, saveAs, new SeparatorMenuItem(), print, new SeparatorMenuItem(), exit);
		
		// edit menu
		edit = new Menu("Edit");
		
		copy = createMenuItemWithKeyCombination("Copy", "SHORTCUT+C");
		copy.setOnAction(event -> mainView.copySelectedText());
		
		paste = createMenuItemWithKeyCombination("Paste", "SHORTCUT+V");
		paste.setOnAction(event -> mainView.pasteCopyedText());
		
		cut = createMenuItemWithKeyCombination("Cut", "SHORTCUT+X");
		cut.setOnAction(event -> mainView.cutSelectedText());
		
		selectAll = createMenuItemWithKeyCombination("Select All", "SHORTCUT+A");
		selectAll.setOnAction(event -> mainView.selectAll());
		
		edit.getItems().addAll(copy, paste, cut, selectAll);
		
		// help menu
		help = new Menu("Help");
		about = new MenuItem("About");
		about.setOnAction(event -> showAboutDialog());
		
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
	
	// show about dialog
	private void showAboutDialog() {
		ButtonType loginButtonType = new ButtonType("OK", ButtonData.OK_DONE);
		Dialog<String> aboutDialog = new Dialog<>();
		aboutDialog.initOwner(mainView.getStage());
		aboutDialog.setTitle("About Notepad");
		aboutDialog.setContentText("This is a basic notepad");
		aboutDialog.getDialogPane().getButtonTypes().add(loginButtonType);
		aboutDialog.getDialogPane().lookupButton(loginButtonType).setDisable(false);
		aboutDialog.showAndWait();
	}

}
