package cli;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class UserCLI {

    public static void main(String[] args) {
        TextIO textIO = TextIoFactory.getTextIO();
        HttpClient httpClient = HttpClient.newHttpClient();

        textIO.getTextTerminal().println("=== Bienvenue à Farm Royal ===");

        boolean running = true;

        while (running) {
            // Afficher le menu principal
            String choice = textIO.newStringInputReader()
                    .withDefaultValue("1")
                    .read("\nChoisissez une option :\n" +
                            "1. S'inscrire\n" +
                            "2. Se connecter\n" +
                            "3. Se déconnecter\n" +
                            "4. Quitter\n" +
                            "Votre choix :");

            switch (choice) {
                case "1": // Inscription
                    handleRegistration(textIO, httpClient);
                    break;
                case "2": // Connexion
                    handleLogin(textIO, httpClient);
                    break;
                case "3": // Déconnexion
                    textIO.getTextTerminal().println("=== Vous êtes déconnecté ! ===");
                    break;
                case "4": // Quitter
                    textIO.getTextTerminal().println("=== Au revoir ! ===");
                    running = false;
                    break;
                default:
                    textIO.getTextTerminal().println("=== Option invalide. Veuillez choisir une option valide. ===");
                    break;
            }
        }
    }

    private static void handleRegistration(TextIO textIO, HttpClient httpClient) {
        System.out.println("\n=== Inscription ===");

        String username = textIO.newStringInputReader()
                .read("Choisissez un nom d'utilisateur :");
        String email = textIO.newStringInputReader()
                .read("Entrez votre adresse email :");
        String password = textIO.newStringInputReader()
                .withInputMasking(true)
                .read("Choisissez un mot de passe :");

        try {
            String requestBody = String.format(
                    "{\"pseudo\":\"%s\", \"email\":\"%s\", \"password\":\"%s\"}",
                    username, email, password
            );

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/user")) // Endpoint du POST /user
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 201) {
                System.out.println("Inscription réussie !");
            } else if (response.statusCode() == 400) {
                System.out.println("Erreur : " + response.body());
            } else if (response.statusCode() == 409) {
                System.out.println("Erreur : Un utilisateur avec cet email existe déjà.");
            } else {
                System.out.println("Erreur inattendue : " + response.statusCode());
            }
        } catch (Exception e) {
            System.out.println("Erreur de communication avec le serveur : " + e.getMessage());
        }
    }


    private static void handleLogin(TextIO textIO, HttpClient httpClient) {
        System.out.println("\n=== Connexion ===");

        String email = textIO.newStringInputReader()
                .read("Entrez votre adresse email :");
        String password = textIO.newStringInputReader()
                .withInputMasking(true)
                .read("Entrez votre mot de passe :");

        try {
            String requestBody = String.format(
                    "{\"email\":\"%s\", \"password\":\"%s\"}",
                    email, password
            );

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/user/login")) // Endpoint du POST /user/login
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                System.out.println("Connexion réussie !");
                System.out.println("Réponse du serveur : " + response.body());
            } else if (response.statusCode() == 401) {
                System.out.println("Erreur : Identifiants incorrects.");
            } else {
                System.out.println("Erreur inattendue : " + response.statusCode());
            }
        } catch (Exception e) {
            System.out.println("Erreur de communication avec le serveur : " + e.getMessage());
        }
    }
}