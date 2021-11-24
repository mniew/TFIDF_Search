package com.findwise.search;

import com.findwise.IndexEntry;
import com.findwise.SearchEngine;
import com.findwise.exception.DocumentAlreadyIndexedException;
import com.findwise.model.Document;
import com.findwise.model.IndexEntryClass;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.log10;

public class DocumentSearchEngine implements SearchEngine {

    Set<Document> collection = new HashSet<>();

    public DocumentSearchEngine() {
    }

    @Override
    public void indexDocument(String id, String content) {

        if (id == null || content == null){
            System.out.println("Error adding to collection.");
            return;
        }
        try{
            if(collection.contains(id)){
                throw new DocumentAlreadyIndexedException(id);
            }
            collection.add(new Document(id, splitContent(content)));
        }catch (Exception e) {
            System.out.println("Error adding to collection.");
            e.printStackTrace();
        }
    }

    public List<String> splitContent(@org.jetbrains.annotations.NotNull String content){

        List<String> info;

        info = Arrays.asList(content.toLowerCase(Locale.ROOT).split("[\\s]+")); //creates list of words that are separated by space or newline
        //to lowercase required to avoid any case sensitivity problems

        return(info);
    }

    @Override
    public List<IndexEntry> search(String term) {

        List<Document> refinedDocList;
        List<IndexEntry> indexEntryList = null;

        refinedDocList = this.collection.stream()
                .filter(collection -> collection.getDescription().stream().anyMatch(term::equalsIgnoreCase))
                .collect(Collectors.toList());

        for (Document doc : refinedDocList) {
            calculateTFIDF( doc, refinedDocList, term);
//            System.out.println(doc.getId() + ":" + doc.getDescription() + ":"+ doc.getScore());
//            indexEntryList.add(createIndexFromDocument(doc));

        }

//        return indexEntryList;
//        return null;
      return refinedDocList.stream().map(document -> createIndexFromDocument(document))
                .collect(Collectors.toList());
    }

    public List<IndexEntry> searchByTFIDF(String term){
//        List<Document> refinedDocList;
//        List<IndexEntry> indexEntryList = null;
//
//        refinedDocList = this.collection.stream()
//                .filter(collection -> collection.getDescription().stream().anyMatch(term::equalsIgnoreCase))
//                .collect(Collectors.toList());
//
//        for (Document doc : refinedDocList) {
//            calculateTFIDF( doc, refinedDocList, term);
////            System.out.println(doc.getId() + ":" + doc.getDescription() + ":"+ doc.getScore());
////            indexEntryList.add(createIndexFromDocument(doc));
//
//        }
//
//        refinedDocList = refinedDocList.stream().sorted(Comparator.comparingDouble(IndexEntry::getScore).reversed()).collect(Collectors.toList());

//https://stackabuse.com/java-8-how-to-sort-list-with-stream-sorted/
//        for (Document doc : refinedDocList) {
//            System.out.println(doc.getId() + ":" + doc.getDescription() + ":"+ doc.getScore());
//
//        }
        List<IndexEntry> listOfEntries = search(term);
        return listOfEntries.stream().sorted(Comparator.comparingDouble(IndexEntry::getScore).reversed()).collect(Collectors.toList());
    }

    public IndexEntry createIndexFromDocument(Document doc){
        IndexEntry entry = new IndexEntryClass();
        entry.setScore(doc.getScore());
        entry.setId(doc.getId());
        return entry;
    }

    public double calculateTF(List<String> contentList, String term){
        double calculatedTF;
        double numberOccurrencesInDoc = contentList
                .stream()
                .filter(term::equalsIgnoreCase)
                .count();
        double numberOfDocs = contentList
                .stream()
                .count();

        calculatedTF =  numberOccurrencesInDoc / numberOfDocs ;

        return calculatedTF;
    }
    public double calculateIDF(List<Document> sortedDocument){
        double calculatedIDF;
        double totalNumberOfDocs = this.collection
                .stream()
                .count();
        double docsWithOccurrences = sortedDocument
                .stream()
                .count();

        calculatedIDF = log10(totalNumberOfDocs/docsWithOccurrences);

        return calculatedIDF;
    }

    public void calculateTFIDF(Document doc, List<Document> sortedDocument, String term){
        double TFIDF = calculateIDF(sortedDocument) * calculateTF(doc.getDescription(), term);

        doc.setScore(TFIDF);
    }
    /**
     * Print info in hashset
     */
    public void printHashset() {

        for (Document doc : this.collection) {
            System.out.println(doc.getId() + ":" + doc.getDescription());
        }
    }
}
