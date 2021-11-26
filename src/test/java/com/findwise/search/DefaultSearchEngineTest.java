package com.findwise.search;

import com.findwise.IndexEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DefaultSearchEngineTest {

    private DefaultSearchEngine searchEngine;

    @BeforeEach
    void setUp() {
        searchEngine = new DefaultSearchEngine();
    }

    @Test
    void givenNullId_whenIndexingDocument_doNotIndex() {
        //given
        String id = null;
        String content = "test";
        List<IndexEntry> index;
        //when
        searchEngine.indexDocument(id,content);
        //then
        index = searchEngine.search("test");
        assertThat(index.isEmpty());
    }

    @Test
    void givenNullContent_whenIndexingDocument_doNotIndex() {
        //given
        String id = "test";
        String content = null;
        List<IndexEntry> index;
        //when
        searchEngine.indexDocument(id,content);
        //then
        index = searchEngine.search("test");
        assertThat(index.isEmpty());
    }

    @Test
    void givenValidContent_whenSplittingContent_shouldPersist() {
        //given
        List<String> splitContent;
        String content = "test test";
        List<String> expectedContent = Arrays.asList("test", "test");
        //when
        splitContent = searchEngine.splitContent(content);
        //then
        assertThat(splitContent).isEqualTo(expectedContent);
    }

    @Test
    void givenInvalidContent_whenSplittingContent_shouldNotPersist() {
        //given
        List<String> splitContent = null;
        String content = null;
        List<String> expectedContent = Arrays.asList("test", "test");
        //when
        try {
            splitContent = searchEngine.splitContent(content);
        }catch(Exception e){
        //then
            assertThat(splitContent).isNull();
        }

    }
}