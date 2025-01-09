package service;

import dao.UserDAO;
import dto.UserDTO;
import entity.User;
import exception.InvalidException;
import exception.UserAlreadyExistWithTheSameEmail;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped //pour l'enregistrer dans le contexte CDI
public class UserService {

    @Inject
    UserDAO userDAO;

    public UserDTO getUserById(Long id) {
        User user = userDAO.getById(id);
        if(user == null) {
            return null;
        }
        return new UserDTO(user.getUsername(), user.getEmail());
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

        userDAO.saveUser(user);

        return user.getId();
    }
}
