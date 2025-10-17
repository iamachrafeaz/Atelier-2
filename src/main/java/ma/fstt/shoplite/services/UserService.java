package ma.fstt.shoplite.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import ma.fstt.shoplite.dtos.UserSessionResponse;
import ma.fstt.shoplite.entities.User;
import ma.fstt.shoplite.repositories.interfaces.IUserRepository;
import ma.fstt.shoplite.utils.PasswordUtils;


@Named
@ApplicationScoped
public class UserService {

    @Inject
    IUserRepository userRepository;

    public void registerUserRequest(
            String firstName,
            String lastName,
            String email,
            String password,
            Long phoneNumber,
            String address
    )
    {
        validateUserFields(firstName, lastName, email, password, phoneNumber, address);

        if(checkExistence(email)){
            throw new IllegalArgumentException("User already exists");
        }

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setPassword(PasswordUtils.hashPassword(password));

        userRepository.save(user);
    }

    public void updateUserRequest(
            String firstName,
            String lastName,
            String email,
            String password,
            Long phoneNumber,
            String address
    ){
        validateUserFields(firstName, lastName, email, password, phoneNumber, address);

        User existingUser = userRepository.findByEmail(email);

        if (existingUser == null) {
            throw new IllegalArgumentException("User not found with id: " + email);
        }
        existingUser.setFirstName(firstName);
        existingUser.setLastName(lastName);
        existingUser.setEmail(email);
        existingUser.setPassword(PasswordUtils.hashPassword(password));
        existingUser.setPhoneNumber(phoneNumber);
        existingUser.setAddress(address);

        if (email != null) {
            if (!existingUser.getEmail().equals(email) && userRepository.existsByEmail(email)) {
                throw new IllegalArgumentException("Email is already used by another user");
            }
            existingUser.setEmail(email);
        }

        userRepository.save(existingUser);
    }


    public UserSessionResponse loginUserRequest(String email, String password)
    {
        validateUserFields(email, password);

        if(!checkExistence(email)){
            throw new IllegalArgumentException("User not found!");
        }

        User user = userRepository.findByEmail(email);

        if(!PasswordUtils.checkPassword(password, user.getPassword())){
            throw new IllegalArgumentException("Wrong password");
        }

        return new UserSessionResponse(user.getId(), user.getEmail(), user.getFirstName(), user.getLastName());
    }

    private void validateUserFields(String firstName, String lastName, String email, String password, Long phoneNumber, String address) {
        if(firstName == null || firstName.isEmpty()) throw new IllegalArgumentException("First name is empty");
        if(lastName == null || lastName.isEmpty()) throw new IllegalArgumentException("Last name is empty");
        if(email == null || email.isEmpty()) throw new IllegalArgumentException("Email is empty");
        if(password == null || password.isEmpty()) throw new IllegalArgumentException("Password is empty");
        if(phoneNumber == null || phoneNumber <= 0) throw new IllegalArgumentException("Invalid phone number");
        if(address == null || address.isEmpty()) throw new IllegalArgumentException("Address is empty");
    }

    private void validateUserFields(String email, String password){
        if (email == null || email.isEmpty()) throw new IllegalArgumentException("Email is empty");
        if (password == null || password.isEmpty()) throw new IllegalArgumentException("Password is empty");
    }

    private Boolean checkExistence (String email)
    {
        return userRepository.existsByEmail(email);
    }

    public User getUser(UserSessionResponse userSessionResponse){
        return userRepository.findByEmail(userSessionResponse.getEmail());
    }
}
