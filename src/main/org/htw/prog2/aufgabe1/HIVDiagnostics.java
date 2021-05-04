package org.htw.prog2.aufgabe1;
import org.apache.commons.cli.*;
import org.htw.prog2.aufgabe1.files.MutationFile;
import org.htw.prog2.aufgabe1.files.SequenceFile;
import org.htw.prog2.aufgabe1.readers.*;

public class HIVDiagnostics {

    /**
     * Parst die Kommandozeilenargumente. Gibt null zurück, falls:
     * <ul>
     *     <li>Ein Fehler beim Parsen aufgetreten ist (z.B. eins der erforderlichen Argumente nicht angegeben wurde)</li>
     *     <li>Bei -m, -d und -r nicht die gleiche Anzahl an Argumenten angegeben wurde</li>
     * </ul>
     * @param args Array mit Kommandozeilen-Argumenten
     * @return CommandLine-Objekt mit geparsten Optionen
     */

    public static CommandLine parseOptions(String[] args) {
        Options options = new Options();
        options.addOption(Option.builder("m").
                hasArg(true).
                longOpt("mutationfile").
                required(true).
                desc("CSV file with mutation patterns.").build());
        options.addOption(Option.builder("d").
                hasArg(true).
                longOpt("drugname").
                required(true).
                desc("Drug name.").build());
        options.addOption(Option.builder("r").
                hasArg(true).
                longOpt("reference").
                required(true).
                desc("Reference sequence FASTA file.").build());
        options.addOption(Option.builder("p").
                hasArg(true).
                longOpt("patientseqs").
                required(true).
                desc("FASTA file with patient sequences.").build());
        CommandLineParser parser = new DefaultParser();
        CommandLine cli;
        try {
            cli = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println("Error: " + e.getMessage());
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("HIVDiagnostics", options);
            return null;
        }
        return cli;
    }

    public static void main(String[] args) {
        CommandLine cli = parseOptions(args);
        if(cli == null ){
            System.exit(1);
        }
        String referenceFileName = cli.getOptionValue('r');
        String patientSequenceFileName = cli.getOptionValue('p');
        String mutationFileName = cli.getOptionValue('m');

        // Add all available sequence file readers to sequenceReaderManager:
        ReaderManager<SequenceFileReader> sequenceReaderManager = new ReaderManager<>();
        sequenceReaderManager.addReader(new FASTAFileReader());
        sequenceReaderManager.addReader(new FASTQFileReader());

        // Add all available mutation file readers to mutationReaderManager:
        ReaderManager<MutationFileReader> mutationReaderManager = new ReaderManager<>();
        mutationReaderManager.addReader(new CSVFileReader());

        try {
            // Use reader managers to determine appropriate readers for all input files, and then use those
            // readers to read the files
            SequenceFile referencefile = sequenceReaderManager.getReaderForFile(referenceFileName).readFile(referenceFileName);
            SequenceFile patientseqs = sequenceReaderManager.getReaderForFile(patientSequenceFileName).readFile(patientSequenceFileName);
            MutationFile patterns = mutationReaderManager.getReaderForFile(mutationFileName).readFile(mutationFileName);
            System.out.println("Eingelesene Mutationen: " + patterns.getNumberOfMutations());
            System.out.println("Länge der eingelesenen Referenzsequenz: " +
                    referencefile.getFirstSequence().length() + " Aminosäuren");
            System.out.println("Anzahl der eingelesenen Patientensequenzen: " +
                    patientseqs.getNumberOfSequences());
        } catch(Exception e) {
            System.out.println("Fehler beim Einlesen einer der Dateien: " + e.getMessage());
        }
    }
}
