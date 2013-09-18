package br.org.stela.numere.csvloader;

import java.io.File;

/**
 * Class that starts the converting process from a CSV file to a Lucene
 * formatted index.
 * 
 * @author Instituto Stela
 * 
 */
public class StartLoader {

	private static final String DEFAULT_CSV_FILE = "content/file/ordemCompraColumbia.csv";

	/**
	 * Method called when the program is started. It accepts the CSV file path
	 * to be converted as parameter
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		File inputFile;

		if (args.length == 0) {
			inputFile = new File(DEFAULT_CSV_FILE);
		} else {
			inputFile = new File(args[0]);
		}

		String delimiter = ";";

		// checks if the file exists
		if (inputFile != null && inputFile.exists()) {
			
			CSVReader csvReader = new CSVReader(inputFile, delimiter);
			
			// reads the file
			CSVFile csvFile = csvReader.readFile();
			
			if (csvFile != null) {
				IndexService indexService = IndexService.getInstance();
				
				// writes the CSV content to a Lucene formatted index
				indexService.writeIndex(csvFile);
			}
			
		}
	}
}
