package filmprojekt;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.fail;

public class ProjektTester {
    private static final String MAIN_CLASS = Main.class.getName();

    @Test
    public void movieNetworkTestMatrix() {
        passedTestNetzwerk("--filmnetzwerk=2081", "Keanu Reeves", "Day the Earth Stood Still", 3, 42);
    }

    @Test
    public void movieNetworkTestLordOfTheRings() {
        passedTestNetzwerk("--filmnetzwerk=5045", "Ali Astin", "The Fellowship of the Ring", 3, 6);
    }

    @Test
    public void movieNetworkTestAsterix() {
        passedTestNetzwerk("--filmnetzwerk=5764", "Jacques Morel", "Asterix and the Vikings ", 3, 0);
    }

    @Test
    public void actorNetworkTestJasonStatham() {
        passedTestNetzwerk("--schauspielernetzwerk=19786", "Ice Cube", "Italian Job", 26, 15);
    }

    @Test
    public void actorNetworkTestEricClaptop() {
        passedTestNetzwerk("--schauspielernetzwerk=15729", "Joe Brown", "Concert for George", 5, 2);
    }

    @Test
    public void actorNetworkTestWillSmith() {
        passedTestNetzwerk("--schauspielernetzwerk=18925", "Robert De Niro", "Men in Black", 42, 17);
    }

    @Test
    public void actorNetworkTestBudSpencer() {
        passedTestNetzwerk("--schauspielernetzwerk=14230", "Marina Langner", "Banana Joe", 2, 0);
    }

    /**
     * Leider war ich bei der Formulierung der Aufgabe etwas unpräzise, daher muss an dieser Stelle die Anzahl der Kommas gezählt werden, was nicht unbedingt der Anzahl an
     * Filmen entspricht, e.g. "Matrix, The".
     *
     * @param arg Programmargument
     * @param schauspielerContaines String, welcher in der Schauspielerzeile vorkommen muss
     * @param filmeContains String, welcher in der Filmzeile vorkommen muss
     * @param schauspielerComma Anzahl der Kommas in der Schauspielerzeile
     * @param filmeComma Anzahl der Kommas in der Filmzeile
     * @return
     */
    private static void passedTestNetzwerk(String arg, String schauspielerContaines, String filmeContains, int schauspielerComma, int filmeComma) {
        boolean passed = true;
        // Der System.out Stream muss umgebogen werden, damit dieser später überprüft werden kann.
        PrintStream normalerOutput = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
        String[] args = {arg};
        try {
            // MainClass mittels Reflection bekommen und main Methode aufrufen
            Class<?> mainClass = Class.forName(MAIN_CLASS);
            Method mainMethod = mainClass.getDeclaredMethod("main", String[].class);
            mainMethod.invoke(null, (Object) args);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Main Klasse konnte nicht geladen werden, bitte Konfiguration prüfen.");
            System.exit(1);
        } finally {
            // System.out wieder zurücksetzen
            System.setOut(normalerOutput);
        }

        // Ergebnisse überprüfen.
        String matrixOutput = baos.toString();
        String[] lines = matrixOutput.split(System.lineSeparator());
        for (String line : lines) {
            if (line.startsWith("Schauspieler")) {
                if (!line.contains(schauspielerContaines)) {
                    fail("Test fehlgeschlagen, weil Schauspieler nicht stimmen.");
                }
                if (countComma(line) != schauspielerComma) {
                    fail("Test fehlgeschlagen, weil Anzahl der Schauspieler nicht stimmen. Erwartet: " + schauspielerComma + ", erhalten: " + countComma(line));
                }
            } else if (line.startsWith("Filme")) {
                if (!line.contains(filmeContains)) {
                    fail("Test fehlgeschlagen, weil Filme nicht stimmen.");
                }
                if (countComma(line) != filmeComma) {
                    fail("Test fehlgeschlagen, weil Anzahl der Filme nicht stimmen. Erwartet: " + filmeComma + ", erhalten: " + countComma(line));
                }
            }
        }
    }

    /**
     * Zählt das Auftreten von Kommas im gegebenen String.
     *
     * @param line
     * @return
     */
    private static int countComma(String line) {
        return line.length() - line.replace(",", "").length();
    }
}
