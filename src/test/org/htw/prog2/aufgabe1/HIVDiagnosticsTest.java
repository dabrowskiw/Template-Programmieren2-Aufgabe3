package org.htw.prog2.aufgabe1;

import org.apache.commons.cli.CommandLine;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class HIVDiagnosticsTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void parseOptions_requiredArguments() {
        assertNull(HIVDiagnostics.parseOptions(new String[] {}));
        assertNull(HIVDiagnostics.parseOptions(
                "-m HIVMutationPatternsPI.csv -r protease_reference.fasta".split(" ")));
        assertNull(HIVDiagnostics.parseOptions(
                "-m HIVMutationPatternsPI.csv -d ProteaseInhibitor".split(" ")));
        assertNull(HIVDiagnostics.parseOptions(
                "-d ProteaseInhibitor -r protease_reference.fasta".split(" ")));
        assertNull(HIVDiagnostics.parseOptions(
                "-m HIVMutationPatternsPI.csv -d ProteaseInhibitor -r protease_reference.fasta".
                        split(" ")));
        assertNull(HIVDiagnostics.parseOptions(
                "-m HIVMutationPatternsPI.csv -p protease_sequences.fasta -r protease_reference.fasta".
                        split(" ")));
        assertNull(HIVDiagnostics.parseOptions(
                "-p protease_sequences.fasta -r protease_reference.fasta -d ProteaseInhibitor ".
                        split(" ")));
        assertNotNull(HIVDiagnostics.parseOptions(
                "-m HIVMutationPatternsPI.csv -p protease_sequences.fasta -r protease_reference.fasta -d ProteaseInhibitor ".
                        split(" ")));
    }

    @Test
    void parseOptions_argumentValues() {
        CommandLine cli = HIVDiagnostics.parseOptions(
                "-m HIVMutationPatternsPI.csv -p protease_sequences.fasta -r protease_reference.fasta -d ProteaseInhibitor ".
                        split(" "));
        assertEquals("HIVMutationPatternsPI.csv", cli.getOptionValue('m'));
        assertEquals("protease_sequences.fasta", cli.getOptionValue('p'));
        assertEquals("protease_reference.fasta", cli.getOptionValue('r'));
        assertEquals("ProteaseInhibitor", cli.getOptionValue('d'));
    }

    @Test
    void main() {
        HIVDiagnostics.main("-m HIVMutationPatternsPI.csv -d ProteaseInhibitor -r protease_reference.fasta -p protease_sequences.fasta".split(" "));
        assertEquals("Eingelesene Mutationen: 825\n" +
                "Länge der eingelesenen Referenzsequenz: 99 Aminosäuren\n" +
                "Anzahl der eingelesenen Patientensequenzen: 1000", outContent.toString().strip());
    }
}
