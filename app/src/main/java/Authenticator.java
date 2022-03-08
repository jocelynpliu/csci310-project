import java.nio.charset.StandardCharsets;
import java.util.Random;

public class Authenticator {
    private Random rand;

    public String getSalt() {
        // implement
        return ""; // so it compiles
    }

    public void encryptPassword(String password, String salt) {
        // implement
    }

    public boolean verifyPassword(String password, String salt) {
        // implement
        return false; // so it compiles
    }

    public byte[] hash(String password, String salt) {
        // implement

        // so it compiles, REPLACE
        byte[] a = "FIX ME".getBytes(StandardCharsets.UTF_8);
        return a;
    }
}
