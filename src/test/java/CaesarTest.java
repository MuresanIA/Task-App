import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import utils.Caesar;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class CaesarTest {
    @Test
    public void testEncrypt1() {
        assertEquals("EXXEGOEXSRGI", new Caesar().encrypt("ATTACKATONCE", 4, 0));
    }
    @Test
    public void testEncrypt2() {
        assertEquals("XYZABCDEFGHIJKLMNOPQRSTUVW", new Caesar().encrypt("ABCDEFGHIJKLMNOPQRSTUVWXYZ", 23, 0));
    }

    @Test
    public void testDecrypt1() {
        assertEquals("ATTACKATONCE", new Caesar().encrypt("EXXEGOEXSRGI", 22, 0));
    }

    @Test
    public void testDecrypt2() {
        assertEquals("ABCDEFGHIJKLMNOPQRSTUVWXYZ", new Caesar().encrypt("XYZABCDEFGHIJKLMNOPQRSTUVW", 3, 0));
    }
}
