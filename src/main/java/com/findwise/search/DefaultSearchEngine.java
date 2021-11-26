package com.findwise.search;

import com.findwise.IndexEntry;
import com.findwise.SearchEngine;
import com.findwise.model.DefaultIndexEntry;
import com.findwise.model.DocumentIdWithLengthAndFrequency;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

public class DefaultSearchEngine implements SearchEngine {

    private final Map<String, Set<DocumentIdWithLengthAndFrequency>> invertedIndex = new HashMap<>();
    private final AtomicInteger docsCount = new AtomicInteger();

    public DefaultSearchEngine() {
    }

    @Override
    public void indexDocument(String id, String content) {
        if ( content != null && id != null) {
            List<String> words = splitContent(content);

            for (String word : words) {
                Set<DocumentIdWithLengthAndFrequency> documentIdsContainingAWord = invertedIndex.get(word);
                Integer wordFrequency = calculateFrequency(word, words);
                Integer contentSizeInWords = words.size();
                if (documentIdsContainingAWord == null) { //if the word is a new occurrence add to map
                    invertedIndex.put(word, new HashSet<>() {{
                        add(new DocumentIdWithLengthAndFrequency(id, contentSizeInWords, wordFrequency)); //add document
                    }});
                } else {
                    documentIdsContainingAWord.add(new DocumentIdWithLengthAndFrequency(id, contentSizeInWords, wordFrequency));
                }
            }
            docsCount.incrementAndGet();
        }else{
            return;
        }
    }

    public Integer calculateFrequency(String word, List<String> words) {
        //calculates frequency of a particular word in a documents content.
        return words
                .stream()
                .mapToInt(w -> w.equals(word) ? 1 : 0)
                .sum();
    }

    public List<String> splitContent(@org.jetbrains.annotations.NotNull String content){

        List<String> info;

        info = Arrays.asList(content.toLowerCase(Locale.ROOT).split("[\\s]+")); //creates list of words that are separated by space or newline
        //to lowercase required to avoid any case sensitivity problems

        return(info);
    }

    @Override
    public List<IndexEntry> search(String term) {
        Set<DocumentIdWithLengthAndFrequency> result = invertedIndex.get(term);
        return result != null
                ? result
                .stream()
                .map(d -> toIndexEntry(d, docsCount.get(), result.size()))
                .sorted(Comparator.comparingDouble(IndexEntry::getScore).reversed())
                .collect(Collectors.toList())
                : emptyList();
    }

    public List<IndexEntry> searchByTFIDF(String term){

        List<IndexEntry> listOfEntries = search(term);
        return listOfEntries
                .stream()
                .sorted(Comparator.comparingDouble(IndexEntry::getScore).reversed())
                .collect(Collectors.toList());
    }

    private IndexEntry toIndexEntry(DocumentIdWithLengthAndFrequency doc, Integer numberOfAllDocuments, Integer numberOfDocumentsWithTerm) {
        double tf = doc.getFrequency().doubleValue() / doc.getLength();
        double idf = Math.log(numberOfAllDocuments.doubleValue() / numberOfDocumentsWithTerm);
        return new DefaultIndexEntry(doc.getId(), tf * idf);
    }
}
