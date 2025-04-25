import java.util.*;

public class LinkShortener {
    private final Map<String, String> shortToLong;
    private final Map<String, String> longToShort;
    private final String baseUrl;
    private final String characters;
    private final int codeLength;

    public LinkShortener() {
        shortToLong = new HashMap<>();
        longToShort = new HashMap<>();
        baseUrl = "http://short.ly/";
        characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        codeLength = 6;
    }

    private String generateCode() {
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < codeLength; i++) {
            code.append(characters.charAt(random.nextInt(characters.length())));
        }
        return code.toString();
    }

    public String shortenURL(String longURL) {
        if (longToShort.containsKey(longURL)) {
            return baseUrl + longToShort.get(longURL);
        }
        String code;
        do {
            code = generateCode();
        } while (shortToLong.containsKey(code));
        shortToLong.put(code, longURL);
        longToShort.put(longURL, code);
        return baseUrl + code;
    }

    public String expandURL(String shortURL) {
        if (!shortURL.startsWith(baseUrl)) {
            return "Invalid short URL format.";
        }
        String code = shortURL.replace(baseUrl, "");
        return shortToLong.getOrDefault(code, "Short URL not found.");
    }

    public void startCLI() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n1. Shorten URL");
            System.out.println("2. Expand URL");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    System.out.print("Enter long URL: ");
                    String longURL = scanner.nextLine();
                    String shortURL = shortenURL(longURL);
                    System.out.println("Shortened URL: " + shortURL);
                    break;
                case "2":
                    System.out.print("Enter short URL: ");
                    String inputShort = scanner.nextLine();
                    String originalURL = expandURL(inputShort);
                    System.out.println("Original URL: " + originalURL);
                    break;
                case "3":
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    public static void main(String[] args) {
        LinkShortener app = new LinkShortener();
        app.startCLI();
    }
}
