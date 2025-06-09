public class Validator {
    public static boolean isValidUsername(String username) {
        return username != null && !username.trim().isEmpty() && username.length() <= 15;
    }
}