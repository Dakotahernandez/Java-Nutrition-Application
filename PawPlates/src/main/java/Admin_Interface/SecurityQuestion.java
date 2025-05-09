package Admin_Interface;

public enum SecurityQuestion {
    A("What is your car's manufacturer?"),
    B("What city were you born in?"),
    C("What is your first pet’s name?"),
    D("What is your favorite workout?"),
    E("What is your favorite gym's name?"),
    F("What is your artist to listen to while working out?");

    private final String fullText;

    SecurityQuestion(String fullText) {
        this.fullText = fullText;
    }

    public String getFullText() {
        return fullText;
    }

    public static String resolve(String code) {
        try {
            return SecurityQuestion.valueOf(code).getFullText();
        } catch (Exception e) {
            return "Unknown Question (" + code + ")";
        }
    }
}
