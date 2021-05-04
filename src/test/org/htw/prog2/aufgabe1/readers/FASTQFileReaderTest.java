package org.htw.prog2.aufgabe1.readers;

import org.htw.prog2.aufgabe1.exceptions.FileFormatException;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FASTQFileReaderTest {

    @Test
    void throws_doesNotExist() {
        Exception e = assertThrows(FileNotFoundException.class, () -> {
            new org.htw.prog2.aufgabe1.readers.FASTQFileReader().readFile("DOESNOTEXIST");
        });
    }

    @Test
    void throws_wrongFormat() {
        Exception e = assertThrows(FileFormatException.class, () -> {
            new org.htw.prog2.aufgabe1.readers.FASTQFileReader().readFile("HIVMutationPatternsPI.csv");
        });
        assertEquals("FASTQ File does not start with sequence header line.", e.getMessage());
    }

    @Test
    void throws_wrongFormat2() {
        Exception e = assertThrows(FileFormatException.class, () -> {
            new org.htw.prog2.aufgabe1.readers.FASTQFileReader().readFile("protease_reference.fasta");
        });
        assertEquals("FASTQ File does not start with sequence header line.", e.getMessage());
    }

    @Test
    void doesNotThrow_correctFormat() {
        assertDoesNotThrow(() -> {
            new org.htw.prog2.aufgabe1.readers.FASTQFileReader().readFile("protease_sequences.fastq");
        });
    }

    @Test
    void cannotRead_doesNotExist() {
        assertFalse(new org.htw.prog2.aufgabe1.readers.FASTQFileReader().canReadFile("DOESNOTEXIST"));
    }

    @Test
    void cannotRead_wrongFormat() {
        assertFalse(new org.htw.prog2.aufgabe1.readers.FASTQFileReader().canReadFile("HIVMutationPatternsPI.csv"));
    }

    @Test
    void cannotRead_wrongFormat2() {
        assertFalse(new org.htw.prog2.aufgabe1.readers.FASTQFileReader().canReadFile("protease_reference.fasta"));
    }

    @Test
    void canRead_correctFormat() {
        assertTrue(new org.htw.prog2.aufgabe1.readers.FASTQFileReader().canReadFile("protease_sequences.fastq"));
    }

    @Test
    void getNumberOfSequences_multiple() {
        org.htw.prog2.aufgabe1.files.SequenceFile seqfile = null;
        try {
            seqfile = new org.htw.prog2.aufgabe1.readers.FASTQFileReader().readFile("protease_sequences.fastq");
        } catch(Exception e) {
            fail("Es sollte keine Exception fliegen.");
        }
        assertEquals(1000, seqfile.getNumberOfSequences());
    }

    @Test
    void getSequences_multiple() {
        org.htw.prog2.aufgabe1.files.SequenceFile seqfile = null;
        try {
            seqfile = new org.htw.prog2.aufgabe1.readers.FASTQFileReader().readFile("protease_sequences.fastq");
        } catch(Exception e) {
            fail("Es sollte keine Exception fliegen.");
        }
        assertEquals(1000, seqfile.getSequences().size());
        assertTrue(seqfile.getSequences().contains("PQVTLRQRPIVCIKIGGSLKEALLDTGADDTVLEEMSLPGKWKPKMIGGYGGFIKVRQYDQVSIEICGHKAIGTVLIGPTPVNIIGRNLLTQLGCTLNF"));
        assertTrue(seqfile.getSequences().contains("PQVVVWQRPIVKIKIGGQLKEALLDTGADDTVLEEMSLDGKWKPKMIGGIGGRIKRRQYDQVSIEICGDKLIGTELIGPTPVNIGGTNLLTQLGCTLNF"));
    }

    @Test
    void getFirstSequence_multiple() {
        org.htw.prog2.aufgabe1.files.SequenceFile seqfile = null;
        try {
            seqfile = new org.htw.prog2.aufgabe1.readers.FASTQFileReader().readFile("protease_sequences.fastq");
        } catch(Exception e) {
            fail("Es sollte keine Exception fliegen.");
        }
        assertEquals("PQVTLRQRPIVCIKIGGSLKEALLDTGADDTVLEEMSLPGKWKPKMIGGYGGFIKVRQYDQVSIEICGHKAIGTVLIGPTPVNIIGRNLLTQLGCTLNF", seqfile.getFirstSequence());
    }
}
