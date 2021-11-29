package com.findwise.menu;

import com.findwise.IndexEntry;
import com.findwise.search.DefaultSearchEngine;

import java.util.List;
import java.util.Scanner;

public class MenuHelpers {

    public void menuInitialization() {
        System.out.println(" Search Engine");

        DefaultSearchEngine searchEngine = new DefaultSearchEngine();

        baseDocumentInitialization(searchEngine);

        menuAddSearch(searchEngine);

    }

    public void baseDocumentInitialization(DefaultSearchEngine searchEngine){
        searchEngine.indexDocument("document 1", "the red fox jumped over the brown dog");
        searchEngine.indexDocument("document 2", "the lazy brown dog sat in the corner");
        searchEngine.indexDocument("document 3", "the red fox bit the lazy dog");

    }

    public void menuAddSearch(DefaultSearchEngine searchEngine){
        Scanner scanner = new Scanner(System.in);
        String input;

        do {
			System.out.println("Please type a command:");
			System.out.println("'a' - to add a document");
			System.out.println("'s' - to search documents");
			System.out.println("'t' - to search and sort by TFIDF score");
			System.out.println("'q' - to quit");

            input = scanner.nextLine().trim();

            if(input.equals("a")){
                menuAdd( searchEngine, scanner);
			}else if(input.equals("s") || input.equals("t")){
                menuSearch( searchEngine, scanner , input);
			}else if(input.equals("q")){
				return;
			}else{
				System.out.println("Unknown input please try again.");
			}
        }while(input != "q");

    }

    public void menuAdd(DefaultSearchEngine searchEngine, Scanner scanner){
        System.out.println("Please enter the document name you would like to add");
        String docName = scanner.nextLine().trim();
        System.out.println("Please enter the content of the document");
        String content = scanner.nextLine().trim();
        searchEngine.indexDocument(docName,content);
    }

    public void menuSearch(DefaultSearchEngine searchEngine, Scanner scanner, String typeOfSearch){

        List<IndexEntry> entry;

        if(typeOfSearch.equals("s") ){
            System.out.println("Please enter the word you would like to search:");
            String searchTerm = scanner.nextLine().trim();
            entry = searchEngine.search(searchTerm);
        }else{
            System.out.println("Please enter the word you would like to TFIDF search:");
            String searchTerm = scanner.nextLine().trim();
            entry = searchEngine.searchByTFIDF( searchTerm);
        }

        for (IndexEntry info : entry) {
            System.out.println(info.getId() + " : " + info.getScore() );
        }

    }
}
