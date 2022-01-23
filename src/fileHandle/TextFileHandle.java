package fileHandle;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TextFileHandle {
	
	// this method used to write string in given file
	public void saveTextToGivenFile(File file, String str) {
		
		BufferedWriter writer = null;
		
		try {
			writer = new BufferedWriter(new FileWriter(file));
			writer.write(str);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if(writer != null) {
				try {	
					writer.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
		
	}

}
