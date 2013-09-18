package br.org.stela.numere.csvloader;

import java.util.ArrayList;
import java.util.List;

/**
 * Data structure for storing each line from a CSV file.
 * It splits each CSV column into a String array.
 * 
 * @author Instituto Stela
 *
 */
public class CSVFile {

	private List<String[]> lineList = new ArrayList<>();
	
	public void addLine(String[] words) {
		this.lineList.add(words);
	}
	
	public List<String[]> getLineList() {
		return lineList;
	}
	
}
