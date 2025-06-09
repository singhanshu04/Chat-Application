import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Start as (s)erver or (c)lient? ");
        String choice = scanner.nextLine().toLowerCase();

        if (choice.equals("s")) {
            new Server().startServer();
        } else if (choice.equals("c")) {
            new Client().startClient();
        } else {
            System.out.println("Invalid choice.");
        }
    }
}