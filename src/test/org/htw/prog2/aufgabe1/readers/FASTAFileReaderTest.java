package org.htw.prog2.aufgabe1.readers;

import org.htw.prog2.aufgabe1.exceptions.FileFormatException;
import org.htw.prog2.aufgabe1.readers.FASTAFileReader;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class FASTAFileReaderTest {

    @Test
    void throws_doesNotExist() {
        Exception e = assertThrows(FileNotFoundException.class, () -> {
            new org.htw.prog2.aufgabe1.readers.FASTAFileReader().readFile("DOESNOTEXIST");
        });
    }

    @Test
    void throws_wrongFormat() {
        Exception e = assertThrows(FileFormatException.class, () -> {
            new org.htw.prog2.aufgabe1.readers.FASTAFileReader().readFile("HIVMutationPatternsPI.csv");
        });
        assertEquals("FASTA File does not start with sequence header line.", e.getMessage());
    }

    @Test
    void throws_wrongFormat2() {
        Exception e = assertThrows(FileFormatException.class, () -> {
            new org.htw.prog2.aufgabe1.readers.FASTAFileReader().readFile("protease_sequences.fastq");
        });
        assertEquals("FASTA File does not start with sequence header line.", e.getMessage());
    }

    @Test
    void doesntThrow_correctFormat() {
        assertDoesNotThrow(() -> {
            new org.htw.prog2.aufgabe1.readers.FASTAFileReader().readFile("protease_reference.fasta");
        });
    }

    @Test
    void cannotRead_doesNotExist() {
        assertFalse(new org.htw.prog2.aufgabe1.readers.FASTAFileReader().canReadFile("DOESNOTEXIST"));
    }

    @Test
    void cannotRead_wrongFormat() {
        assertFalse(new org.htw.prog2.aufgabe1.readers.FASTAFileReader().canReadFile("HIVMutationPatternsPI.csv"));
    }

    @Test
    void cannotRead_wrongFormat2() {
        assertFalse(new org.htw.prog2.aufgabe1.readers.FASTAFileReader().canReadFile("protease_sequences.fastq"));
    }

    @Test
    void canRead_correctFormat() {
        assertTrue(new org.htw.prog2.aufgabe1.readers.FASTAFileReader().canReadFile("protease_reference.fasta"));
    }

    @Test
    void getNumberOfSequences_single() {
        org.htw.prog2.aufgabe1.files.SequenceFile seqfile = null;
        try {
            seqfile = new org.htw.prog2.aufgabe1.readers.FASTAFileReader().readFile("protease_reference.fasta");
        } catch(Exception e) {
            fail("Es sollte keine Exception fliegen.");
        }
        assertEquals(1, seqfile.getNumberOfSequences());
    }

    @Test
    void getNumberOfSequences_multiple() {
        org.htw.prog2.aufgabe1.files.SequenceFile seqfile = null;
        try {
            seqfile = new org.htw.prog2.aufgabe1.readers.FASTAFileReader().readFile("protease_sequences.fasta");
        } catch(Exception e) {
            fail("Es sollte keine Exception fliegen.");
        }
        assertEquals(1000, seqfile.getNumberOfSequences());
    }

    @Test
    void getSequences_single() {
        org.htw.prog2.aufgabe1.files.SequenceFile seqfile = null;
        try {
            seqfile = new org.htw.prog2.aufgabe1.readers.FASTAFileReader().readFile("protease_reference.fasta");
        } catch(Exception e) {
            fail("Es sollte keine Exception fliegen.");
        }
        assertEquals(1, seqfile.getSequences().size());
        assertTrue(seqfile.getSequences().contains("PQVTLWQRPIVTIKIGGQLKEALLDTGADDTVLEEMSLPGKWKPKMIGGIGGFIKVRQYDQVSIEICGHKAIGTVLIGPTPVNIIGRNLLTQLGCTLNF"));
    }

    @Test
    void getSequences_multiple() {
        org.htw.prog2.aufgabe1.files.SequenceFile seqfile = null;
        try {
            seqfile = new org.htw.prog2.aufgabe1.readers.FASTAFileReader().readFile("protease_sequences.fasta");
        } catch(Exception e) {
            fail("Es sollte keine Exception fliegen.");
        }
        assertEquals(1000, seqfile.getSequences().size());
        assertTrue(seqfile.getSequences().contains("PQVTLRQRPIVCIKIGGSLKEALLDTGADDTVLEEMSLPGKWKPKMIGGYGGFIKVRQYDQVSIEICGHKAIGTVLIGPTPVNIIGRNLLTQLGCTLNF"));
        assertTrue(seqfile.getSequences().contains("PQVVVWQRPIVKIKIGGQLKEALLDTGADDTVLEEMSLDGKWKPKMIGGIGGRIKRRQYDQVSIEICGDKLIGTELIGPTPVNIGGTNLLTQLGCTLNF"));
    }

    @Test
    void getFirstSequence_single() {
        org.htw.prog2.aufgabe1.files.SequenceFile seqfile = null;
        try {
            seqfile = new org.htw.prog2.aufgabe1.readers.FASTAFileReader().readFile("protease_reference.fasta");
        } catch(Exception e) {
            fail("Es sollte keine Exception fliegen.");
        }
        assertEquals("PQVTLWQRPIVTIKIGGQLKEALLDTGADDTVLEEMSLPGKWKPKMIGGIGGFIKVRQYDQVSIEICGHKAIGTVLIGPTPVNIIGRNLLTQLGCTLNF", seqfile.getFirstSequence());
    }

    @Test
    void getFirstSequence_multiple() {
        org.htw.prog2.aufgabe1.files.SequenceFile seqfile = null;
        try {
            seqfile = new org.htw.prog2.aufgabe1.readers.FASTAFileReader().readFile("protease_sequences.fasta");
        } catch(Exception e) {
            fail("Es sollte keine Exception fliegen.");
        }
        assertEquals("PQVTLRQRPIVCIKIGGSLKEALLDTGADDTVLEEMSLPGKWKPKMIGGYGGFIKVRQYDQVSIEICGHKAIGTVLIGPTPVNIIGRNLLTQLGCTLNF", seqfile.getFirstSequence());
    }
}