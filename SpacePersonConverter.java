import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SpacePersonConverter {

    public static void main(String[] args) {
        Map<Character, Character> spacePersonAlphabet = createSpacePersonAlphabet();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the English string: ");
        String englishString = scanner.nextLine();


        String spacePersonString = convertToSpacePersonString(englishString, spacePersonAlphabet);
        System.out.println("Space Person String: " + spacePersonString);


        String hashValue = calculateSHA256(spacePersonString);
        System.out.println("SHA256 Hash Value: " + hashValue);


        String caesarCipheredString = caesarCipher(englishString, 5);
        System.out.println("Caesar Cipher (5-character shift): " + caesarCipheredString);


        System.out.println("Brute Force Caesar Cipher (0-25 shifts):");
        bruteForceCaesarCipher(englishString);
    }

    private static Map<Character, Character> createSpacePersonAlphabet() {
        Map<Character, Character> spacePersonAlphabet = new HashMap<>();
        char[] englishAlphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        char[] spacePersonSymbols = "~!@#$%^&*".toCharArray();

        for (int i = 0; i < englishAlphabet.length; i++) {
            spacePersonAlphabet.put(englishAlphabet[i], spacePersonSymbols[i % spacePersonSymbols.length]);
        }

        return spacePersonAlphabet;
    }

    private static String convertToSpacePersonString(String englishString, Map<Character, Character> alphabet) {
        StringBuilder spacePersonString = new StringBuilder();

        for (char ch : englishString.toCharArray()) {
            if (Character.isLetter(ch)) {
                char convertedChar = Character.isUpperCase(ch) ?
                        Character.toUpperCase(alphabet.get(Character.toLowerCase(ch))) :
                        alphabet.get(ch);
                spacePersonString.append(convertedChar);
            } else {
                spacePersonString.append(ch);
            }
        }

        return spacePersonString.toString();
    }

    private static String calculateSHA256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String caesarCipher(String input, int shift) {
        StringBuilder result = new StringBuilder();

        for (char ch : input.toCharArray()) {
            if (Character.isLetter(ch)) {
                char base = Character.isUpperCase(ch) ? 'A' : 'a';
                result.append((char) ((ch - base + shift) % 26 + base));
            } else {
                result.append(ch);
            }
        }

        return result.toString();
    }

    private static void bruteForceCaesarCipher(String input) {
        for (int shift = 0; shift < 26; shift++) {
            String result = caesarCipher(input, shift);
            System.out.println("Shift " + shift + ": " + result);
        }
    }
}
