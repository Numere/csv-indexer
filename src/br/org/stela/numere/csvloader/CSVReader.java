package br.org.stela.numere.csvloader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * 
 * @author Instituto Stela
 *
 */
public class CSVReader {
	
	private File inputFile = null;
	
	private String delimiter = null;
	
	private CSVFile csvFile = null;
	
	/**
	 * Constructor of the class.
	 * 
	 * @param File inputFile - Directory of the file to be read.
	 * @param String delimiter - Column delimiter of the file to be read. Ex ";"
	 */
	public CSVReader(File inputFile, String delimiter) {
		this.inputFile = inputFile;
		this.delimiter = delimiter;
	}
	
	/**
	 * Read a file line by line. From each line, generates an array of String and 
	 * adds this vector in an internal list of vector type String.
	 * <p>
	 * The first line and Blank lines are ignored.
	 */
	public CSVFile readFile() {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(inputFile));
			String str;
			csvFile = new CSVFile();
			if(in.ready()){
				str = in.readLine();
			}
			while(in.ready()) {
				str = in.readLine();
				this.processLine(str, delimiter);
			}
		} catch (IOException e) {
			System.err.println(e);
			return null;
		} finally {
			if(inputFile.exists()) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}
		return csvFile;
	}
	
	private void processLine(final String line, final String delimiter) {
		if(line != null && !line.isEmpty()) {
			csvFile.addLine(line.split(delimiter));
		}
	}

}
