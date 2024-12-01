package com.arca.auth_service.modules.user.service;

import com.arca.auth_service.modules.user.domain.dto.UserDisplayDto;
import com.arca.auth_service.modules.user.domain.dto.UserPasswordUpdateDto;
import com.arca.auth_service.modules.user.domain.dto.UserRegistrationDto;
import com.arca.auth_service.modules.user.domain.dto.UserUpdateDto;
import com.arca.auth_service.modules.user.domain.model.User;
import com.arca.auth_service.modules.user.exception.PasswordConfirmationException;
import com.arca.auth_service.modules.user.exception.UserNotFoundException;
import com.arca.auth_service.modules.user.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService
{
    @Autowired
    private UserRepository userRepository;


    /*
     *  CREATE USER
     *
     */
    public UserDisplayDto create(UserRegistrationDto userRegistrationDto)
    {
        checkPasswordConfirmation(userRegistrationDto.password(), userRegistrationDto.passwordConfirmation());

        String encryptedPassword = new BCryptPasswordEncoder().encode(userRegistrationDto.password());
        User newUser = new User();

        BeanUtils.copyProperties(userRegistrationDto, newUser);
        newUser.setPassword(encryptedPassword);
        newUser.setIsActive(true);

        return new UserDisplayDto(userRepository.save(newUser));
    }


    /*
     *  GET USER BY ID
     *
     */
    public UserDisplayDto getById(UUID id)
    {
        Optional<User> userOptional = userRepository.findById(id);

        if(userOptional.isPresent())
        {
            return new UserDisplayDto(userOptional.get());
        }
        else
        {
            throw new UserNotFoundException("User not found!");
        }
    }


    /*
     *  GET ALL USERS
     *
     */
    public Page<UserDisplayDto> getAll(Pageable pageable)
    {
        return userRepository.findAll(pageable).map(UserDisplayDto::new);
    }


    /*
     *  DELETE USER BY ID
     *
     */
    public void delete(UUID id)
    {
        Optional<User> userOptional = userRepository.findById(id);

        if(userOptional.isPresent())
        {
            userRepository.delete(userOptional.get());
        }
        else
        {
            throw new UserNotFoundException("User not found!");
        }
    }


    /*
     *  UPDATE USER
     *
     */
    public UserDisplayDto update(UserUpdateDto userUpdateDto)
    {
        Optional<User> userOptional = userRepository.findById(userUpdateDto.id());

        if(userOptional.isPresent())
        {
            return generalUpdates(userUpdateDto, userOptional.get());
        }
        else
        {
            throw new UserNotFoundException("User not found!");
        }
    }


    /*
     * PASSWORD UPDATE
     *
     */
    public UserDisplayDto updatePassword(UserPasswordUpdateDto userPasswordUpdateDto)
    {
        checkPasswordConfirmation(
                userPasswordUpdateDto.newPassword(),
                userPasswordUpdateDto.passwordConfirmation(),
                userPasswordUpdateDto.oldPassword()
        );

        Optional<User> userOptional = userRepository.findById(userPasswordUpdateDto.id());

        if(userOptional.isPresent())
        {
            User updatedUser = new User();
            BeanUtils.copyProperties(userOptional.get(), updatedUser);

            String encryptedOldPassword = new BCryptPasswordEncoder().encode(userPasswordUpdateDto.oldPassword());
            if(!Objects.equals(encryptedOldPassword, updatedUser.getPassword()))
            {
                throw new PasswordConfirmationException("Wrong password!");
            }

            String encryptedNewPassword = new BCryptPasswordEncoder().encode(userPasswordUpdateDto.newPassword());
            updatedUser.setPassword(encryptedNewPassword);

            return new UserDisplayDto(userRepository.save(updatedUser));
        }
        else
        {
            throw new UserNotFoundException("User not found!");
        }

    }


    /*
     *  GENERAL USER UPDATES
     *
     */
    private UserDisplayDto generalUpdates(UserUpdateDto userUpdateDto, User user)
    {
        User updatedUser = new User();
        BeanUtils.copyProperties(user, updatedUser);

        if(userUpdateDto.username() != null) {updatedUser.setUsername(userUpdateDto.username()); }
        if(userUpdateDto.email() != null) {updatedUser.setEmail(userUpdateDto.email()); }
        if(userUpdateDto.isActive() != null) {updatedUser.setIsActive(userUpdateDto.isActive()); }
        if(userUpdateDto.roles() != null && !userUpdateDto.roles().isEmpty())
        {
            updatedUser.setRoles(userUpdateDto.roles());
        }

        return new UserDisplayDto(userRepository.save(updatedUser));
    }


    /*
     *  CHECK PASSWORD CONFIRMATION
     *
     */
    private void checkPasswordConfirmation(String password, String passwordConfirmation)
    {
        if (!Objects.equals(password, passwordConfirmation))
        {
            throw new PasswordConfirmationException("Password confirmation failure!");
        }
    }

    private void checkPasswordConfirmation(String newPassword, String passwordConfirmation, String oldPassword)
    {
        if (!Objects.equals(newPassword, passwordConfirmation))
        {
            throw new PasswordConfirmationException("Password confirmation failure!");
        }
        if (Objects.equals(oldPassword, newPassword))
        {
            throw new PasswordConfirmationException("The new password must be different from the old password!");
        }
    }
}
