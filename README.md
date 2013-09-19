# CSV-Indexer

This project allows the generation of Lucene formatted indexes from a CSV file.

## Getting Started

### Requirements

* Download this project;
* Download the Apache Lucene library;
* Download the 2011 Purchase Orders from the District of Columbia file (available at ([http://data.octo.dc.gov/feeds/pass/archive/pass_2011_CSV.zip](http://data.octo.dc.gov/feeds/pass/archive/pass_2011_CSV.zip))).

### About Lucene indexing and field types

Lucene is a Java-based project that provides indexing and search technology, as well as spellchecking, hit highlighting and advanced analysis/tokenization capabilities. 

Lucene can also be used for storing data as a schemaless structure, providing full text search (including fuzzy and approximation searches) over all the stored data.

Lucene offers specific field types for an optimized indexing and data retrieve process. The following points show some recommendations (based on Lucene indexing format) to store the data and to give faster retrieval:

* Text fields:
	- Use a Field object;
	- In case of stored fields:
		* Enable term vector storing and indexing properties;
		* Disable tokenizing and term vector position storing properties;
	- Indexed fields:
		* Enable term vector storing, term vector position storing, and indexing properties;
		* Disable tokenizing and term storing properties;
* Numeric fields:
	- Use the specific-purpose field type:
		* For 4-byte integers: IntField;
		* For 8-byte integers: LongField;
		* For 4-byte floats: FloatField;
		* For 8-byte floats: DoubleField;
* Use a Field for other types.

Based on these points, the creation of a stored text field can coded like this:

	FieldType storedFieldType = new FieldType();
	
	//Set to true if you want to save the value of the entire column
	storedFieldType.setStored(true);
	
	//Set to true if this field's indexed form should be also stored into term vectors.
	storedFieldType.setStoreTermVectors(true);
	storedFieldType.setIndexed(true);
	storedFieldType.setTokenized(false);
	storedFieldType.freeze(); //for locking the changes

Also based on the previous points, the creation of a indexed text field can be coded like this: 

	FieldType indexedFieldType = new FieldType();
	
	//Set to true if this field's indexed form should be also stored into term vectors.
	indexedFieldType.setStoreTermVectors(true);
	
	//Set to true to also store token positions into the term vector for this field.
	indexedFieldType.setStoreTermVectorPositions(true);
	
	indexedFieldType.setIndexed(true); 
	
	//Set to true to tokenize this field's contents via the configured Analyzer.
	indexedFieldType.setTokenized(false);
	
	//for locking the changes
	indexedFieldType.freeze();

## Visualizing the Index

After create the index, how to view the information saved?

### Context OLAP

The tool Numere brings the vision OLAP. This tool can be added in a project java.

*   [Numere](numere.stela.org.br)

### Context of Document 

The tool Luke is used to visualizing the documents belonging to the index.

*   [Luke - GetOpt.Org](http://www.getopt.org/luke/)
*   [Luke - Lucene Index Toolbox](https://code.google.com/p/luke/)
