package utils;

public class Caesar {
    public static String encrypt(String password, int n, int d) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < password.length(); i++) {
            if (Character.isUpperCase(password.charAt(i))) {
                char ch = (char) (((int) password.charAt(i) +
                        n - 65) % 26 + 65);
                result.append(ch);

            } else if (Character.isDigit(password.charAt(i))) {
                char chDigit = (char) (((int) password.charAt(i) + d - 48) % 10 + 48);
                result.append(chDigit);
            } else {
                char ch = (char) (((int) password.charAt(i) +
                        n - 97) % 26 + 97);
                result.append(ch);
            }
        }
        return result.toString();
    }
}