package br.org.stela.numere.csvloader;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.DoubleField;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.LongField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 * 
 * @author Instituto Stela
 *
 */
public class IndexService {
	
	// Set the destination folder where you will save the index.
	private final String PATH = "content/index";

	private static IndexWriter writer = null;
	
	private Analyzer analyzer = null;

	private File indexDir = null;

	private Directory directory = null;
	
	// Set the version of Lucene to be used.
	private Version LUCENE_VERSION = Version.LUCENE_44;

	private static IndexService instance = new IndexService();
	
	private FieldType storedFieldType = null;
	
	private FieldType indexedFieldType = null;

	private int add;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("M/d/yy");
	
	private IndexService() {
		this.indexConfig();
	}
	
	/**
	 * Returns the instance of the IndexIndexService;
	 * 
	 * @return Index
	 */
	public static IndexService getInstance() {
		return instance;
	}
	
	/**
	 * From a list type vector of String, is generated the ï¿½ndex.
	 */
	public void writeIndex(final CSVFile csvFile) {
		if(csvFile.getLineList() != null) {
			try {
				for (String[] words : csvFile.getLineList()) {
					this.addIndexRegister(words);
				}
			} catch (IOException e) {
				System.err.println(e);
			} catch (ParseException e) {
				System.err.println(e);
			} finally {
				this.closeIndex();
			}
		}
	}

	/**
	 * Adds a document in the index. For each line, a document is generated with N Field.
	 * Each Field receives three parameter, the first column will be assigned this value, 
	 * the second column assigns the value itself and the third column assigns as the data 
	 * is stored, it will be either stored or indexed. 
	 * 
	 * @param String[] words - Array of words to be indexed.
	 * @throws IOException
	 * @throws ParseException
	 */
	public void addIndexRegister(final String[] words) throws IOException, ParseException {
		if(words != null && words.length == 7) {
			Document document = new Document();
			
			{
				document.add(new Field("agency_name", words[0].trim(), storedFieldType));
				document.add(new Field("nigp_description", words[1].trim(), storedFieldType));
				document.add(new DoubleField("po_total_amount", this.processValue(words[2]), Store.YES));
				document.add(new LongField("order_date", this.processDate(words[3]), Store.YES));
				document.add(new Field("supplier", words[4].trim(), storedFieldType));
				document.add(new Field("supplier_city", words[5].trim(), storedFieldType));
				document.add(new Field("supplier_state", words[6].trim(), storedFieldType));
			}
			
			{
				// For indexed fields, use the prefix 'idx' thus separating the columns indexed and stored
				// with the same name.
				document.add(new Field("idx_agency_name", words[0].trim(), indexedFieldType));
				document.add(new Field("idx_nigp_description", words[1].trim(), indexedFieldType));
				document.add(new Field("idx_supplier", words[4].trim(), indexedFieldType));
				document.add(new Field("idx_supplier_city", words[5].trim(), indexedFieldType));
				document.add(new Field("idx_supplier_state", words[6].trim(), indexedFieldType));
			}
			
			writer.addDocument(document);
			System.out.println("Add doc"+(++add));
		}
			
	}

	/**
	 * Method responsible for committing and closing the index file generated.
	 */
	public void closeIndex() {
		try {
			if(writer != null) { 
				writer.commit();
				writer.close();
			}
		}catch (Exception e) {}
	}

	/**
	 * @see 
	 */
	private void indexConfig() {
		try {
			indexDir = new File(PATH);

			directory = FSDirectory.open(indexDir);

			// Define the type of analyzer, in this example we will use the type StandardAnalyzer.
			analyzer = new StandardAnalyzer(LUCENE_VERSION);
			IndexWriterConfig config = new IndexWriterConfig(LUCENE_VERSION, analyzer);
			writer = new IndexWriter(directory, config);
			
			// Stored type used to store the value without the use of an analyzer.
			// See more in Informações complementares on github.com/numere
			storedFieldType = new FieldType();
			storedFieldType.setStored(true);
			storedFieldType.setStoreTermVectors(true);
			storedFieldType.setIndexed(true);
			storedFieldType.setTokenized(false);
			storedFieldType.freeze();
			
			// Indexed type used to store the value using a predefined analyzer.
			// See more in Informaï¿½oes complementares on github.com/numere
			indexedFieldType = new FieldType();
			indexedFieldType.setStoreTermVectors(true);
			indexedFieldType.setStoreTermVectorPositions(true);
			indexedFieldType.setIndexed(true);
			indexedFieldType.setTokenized(true);
			indexedFieldType.freeze();
			
		} catch (Exception e) {
			System.err.println("Erro on the creation or opening the index");
		}
	}
	
	private Double processValue(final String value) {
		return Double.parseDouble(value.replace("$", "").trim());
	}
	
	private long processDate(final String value) throws ParseException {
		return sdf.parse(value).getTime();
	}

}
