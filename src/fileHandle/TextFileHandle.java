package fileHandle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TextFileHandle {
	
	// this method used to write string in given file
	// return false if some error occurred, otherwise return true
	public boolean saveTextToGivenFile(File file, String str) {
		
		BufferedWriter writer = null;
		boolean success = true;
		
		try {
			writer = new BufferedWriter(new FileWriter(file));
			writer.write(str);
		} catch (IOException ex) {
			success = false;
			ex.printStackTrace();
		} finally {
			if(writer != null) {
				try {	
					writer.close();
				} catch (IOException ex) {
					success = false;
					ex.printStackTrace();
				}
			}
		}
		return success;
	}
	
	
	// read given file and return file content
	public String readTextFile(File file) {
		
		BufferedReader reader = null;
		String str = null;
		String line = null;
		
		try {
			reader = new BufferedReader(new FileReader(file));
			str = reader.readLine();
			while ((line = reader.readLine()) != null) {
				str = str + "\n" + line;
			}
		} catch(FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
		
		return str;
	}

}
