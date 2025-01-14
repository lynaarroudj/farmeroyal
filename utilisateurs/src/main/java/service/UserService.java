package service;

import dao.UserDAO;
import dto.UserDTO;
import dto.AuthDTO;
import entity.User;
import exception.InvalidException;
import exception.UserAlreadyExistWithTheSameEmail;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.mindrot.jbcrypt.BCrypt;

import java.util.Set; 
import java.util.Date;

import io.jsonwebtoken.security.Keys;
import java.security.Key;

@ApplicationScoped //pour l'enregistrer dans le contexte CDI
public class UserService {

    @Inject
    UserDAO userDAO;


    public UserDTO getUserById(Long id) {
        User user = userDAO.getById(id);
        if(user == null) {
            return null;
        }
        return new UserDTO(user.getUsername(), user.getEmail(), user.getPassword());
    }

    @Transactional
    public Long CheckAndSaveUser(UserDTO userDTO) throws InvalidException, UserAlreadyExistWithTheSameEmail {

        //verification metier
        if (!userDTO.email().contains("@")){
            throw new InvalidException("Email is malformed");
        }

        //verification existance
        if(userDAO.isUserPresent(userDTO.email())){
            throw new UserAlreadyExistWithTheSameEmail();
        }

        User user = new User();
        user.setEmail(userDTO.email());
        user.setUsername(userDTO.pseudo());
        user.setPassword(hashPassword(userDTO.password()));

        userDAO.saveUser(user);

        return user.getId();
    }

    public AuthDTO.LoginResp authenticateUser(AuthDTO.LoginReq loginRequest) throws InvalidException {
        // Récupérer l'utilisateur par email
        User user = userDAO.findByEmail(loginRequest.email());
        if (user == null) {
            throw new InvalidException("Utilisateur non trouvé !");
        }

        // Vérifier le mot de passe
        if (!verifyPassword(loginRequest.password(), user.getPassword())) {
            throw new InvalidException("Mot de passe incorrect !");
        }

        // Générer un token JWT
        String token = generateToken(user);

        // Retourner une réponse de connexion
        return new AuthDTO.LoginResp("Bienvenue, " + user.getUsername(), token);
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    private boolean verifyPassword(String rawPassword, String hashedPassword) {
        return BCrypt.checkpw(rawPassword, hashedPassword);
    }

    private String generateToken(User user) {
        // Générer une clé sécurisée pour HS256
        Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("username", user.getUsername())
                .claim("roles", Set.of("user")) // Ajouter des claims personnalisés
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 12 * 60 * 60 * 1000)) // Expire dans 12 heures
                .signWith(secretKey) // Utiliser la clé générée
                .compact();
    }


}
