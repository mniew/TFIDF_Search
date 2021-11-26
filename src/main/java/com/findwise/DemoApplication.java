package com.findwise;

import com.findwise.search.DefaultSearchEngine;

import java.util.List;
import java.util.Scanner;

public class DemoApplication {

	public static void main(String[] args) {
		System.out.println(" Search Engine");
		Scanner scanner = new Scanner(System.in);
		String input;
		DefaultSearchEngine searchEngine= new DefaultSearchEngine();

		searchEngine.indexDocument("document 1","the red fox jumped over the brown dog fox");
		searchEngine.indexDocument("document 2","the lazy brown dog sat in the corner");
		searchEngine.indexDocument("document 3","the red fox bit the lazy dog");

		do{
			System.out.println("Please type a command:");
			System.out.println("'a' - to add a document");
			System.out.println("'s' - to search documents");
			System.out.println("'t' - to search and sort by TFIDF score");
			System.out.println("'q' - to quit");
			input = scanner.nextLine().trim();
			if(input.equals("a")){
				System.out.println("Please enter the document name you would like to add");
				String docName = scanner.nextLine().trim();
				System.out.println("Please enter the content of the document");
				String content = scanner.nextLine().trim();
				searchEngine.indexDocument(docName,content);
			}else if(input.equals("s")){
				System.out.println("Please enter the word you would like to search:");
				String searchTerm = scanner.nextLine().trim();
				List<IndexEntry> entry = searchEngine.search(searchTerm);
				for (IndexEntry info : entry) {
					System.out.println(info.getId() + " : " + info.getScore() );
				}
			}else if(input.equals("t")) {
				System.out.println("Please enter the word you would like to TFIDF search:");
				String searchTerm = scanner.nextLine().trim();
				List<IndexEntry> entry = searchEngine.searchByTFIDF( searchTerm);
				for (IndexEntry info : entry) {
					System.out.println(info.getId() + " : " + info.getScore() );
				}
			}else if(input.equals("q")){
				return;
			}else{
				System.out.println("Unknown input please try again.");
			}

		}while (input != "q");
	}

}
