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
        assertEquals("XYZABCDEFGHIJKLMNOPQRSTUVW", new Caesar().encrypt("ABCDEFGHIJKLMNOPQRSTUVWXYZ", 23, 0 ));
    }

    @Test
    public void testEncrypt3() {
        assertEquals("vrrvkhh456", new Caesar().encrypt("sooshee123", 3, 3));
    }

    @Test
    public void testEncrypt4() {
        assertEquals("lrqfb123", new Caesar().encrypt("ioncy890", 3, 3));
    }

    @Test
    public void testDecrypt1() {
        assertEquals("ATTACKATONCE", new Caesar().encrypt("EXXEGOEXSRGI", 22, 0));
    }

    @Test
    public void testDecrypt2() {
        assertEquals("ABCDEFGHIJKLMNOPQRSTUVWXYZ", new Caesar().encrypt("XYZABCDEFGHIJKLMNOPQRSTUVW", 3,0));
    }

    @Test
    public void testDecrypt3() {
        assertEquals("sooshee123", new Caesar().encrypt("vrrvkhh456", 23,7));
    }

    @Test
    public void testDecrypt4() {
        assertEquals("ioncy890", new Caesar().encrypt("lrqfb123", 23, 7));
    }
}
