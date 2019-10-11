package pink.digitally.games.whot;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Library")
public class LibraryTest {

    @DisplayName("some method is always true")
    @Test
    public void testSomeLibraryMethod() {
        Library classUnderTest = new Library();
        assertTrue(classUnderTest.someLibraryMethod());
    }
}
