package com.findwise.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DocumentIdWithLengthAndFrequencyTest {

    @Test
    void givenDocument_withValidInput_testShouldPersist() {
        //given
        DocumentIdWithLengthAndFrequency document ;
        //when
        document = new DocumentIdWithLengthAndFrequency("Test",1,1);
        //then
        assertThat(document.getId()).isEqualTo("Test");
        assertThat(document.getLength()).isEqualTo(1);
        assertThat(document.getFrequency()).isEqualTo(1);

    }
}