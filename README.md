#	CSV-INDEXER

Generation of the inverted index using the Apache Lucene library from a csv file.

##  Getting Started

*   Download the sample project;

*   Download all dependencies;

*   Download CSV file.

##  GETTING THE CODE

*   git clone git@github.com:numere/csv-indexer.git

##  Dependencies

Necessary dependencies for execution of code.

### Lucene

Engine generator of inverted index.

([Apache Lucene](http://lucene.apache.org/core/))

*   lucene-analyzers-common-4.4.0;
*   lucene-codecs-4.4.0;
*   lucene-core-4.4.0;
*   lucene-misc-4.4.0;
*   lucene-queries-4.4.0;
*   lucene-queryparser-4.4.0;
*   lucene-suggest-4.4.0;

Minimum requisite: Lucene-3.2.0

### CSV File 

File public of the District of Columbia regarding the purchase order in year of 2011.

([file.csv](http://data.octo.dc.gov/feeds/pass/archive/pass_2011_CSV.zip))

##	Additional Information

Para melhor entendimento do c�digo e suas propriedades, sugerimos a leitura destes conceitos e utiliza��es.

### Fields

How to define fields in Lucene indexes?

*   Text fields:
    Use a Field object;
    In case of stored fields:
        Enable term vector storing and indexing properties;
        Disable tokenizing and term vector position storing properties;
    Indexed fields:
        Enable term vector storing, term vector position storing, and indexing properties;
        Disable tokenizing and term storing properties;
*   Numeric fields:
        Use the specific-purpose field type:
            For 4-byte integers: IntField;
            For 8-byte integers: LongField;
            For 4-byte floats: FloatField;
            For 8-byte floats: DoubleField;
*   Use a Field for other types.

### Stored Field

FieldType storedFieldType = new FieldType();

//Set to true if you want to save the value of the entire column
storedFieldType.setStored(true);

//Set to true if this field's indexed form should be also stored into term vectors.
storedFieldType.setStoreTermVectors(true);
storedFieldType.setIndexed(true);
storedFieldType.setTokenized(false);
storedFieldType.freeze(); //for locking the changes

### Indexed Field

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

## How to viewing the index

Existing many forms to visualizing the index, we suggest two distinct ways.

### Vision OLAP

The tool Numete brings the vision OLAP. This tool can be added in a project java.

*   [Numere](numere.stela.org.br)

### Vision the Data

The tool 'Luke' is used to visualizing the documents belonging to the index.

*   [Luke - GetOpt.Org](http://www.getopt.org/luke/)
*   [Luke - Lucene Index Toolbox](https://code.google.com/p/luke/)
