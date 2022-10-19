package com.ergoton.ergoaudit.services;

import com.ergoton.ergoaudit.exceptions.EntityAlreadyExistsException;
import com.ergoton.ergoaudit.exceptions.EntityNotFoundException;
import com.ergoton.ergoaudit.model.User;
import com.ergoton.ergoaudit.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Page<User> listUsers(final Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User getUserByWalletAddress(String walletAddress) {

        Optional<User> optionalUser = userRepository.findByWalletAddress(walletAddress);

        optionalUser.orElseThrow(() -> new EntityNotFoundException("User with wallet address "
                + walletAddress + " doesn't exist."));

        return optionalUser.get();

    }

    public void addExperience(User user, int experience) {

        user.addExperience(experience);

        userRepository.save(user);

    }

    public User createNewUser(User newUser) {

        try {
            return userRepository.insert(newUser);
        } catch (DuplicateKeyException e) {
            throw new EntityAlreadyExistsException("User with wallet address "
                    + newUser.getWalletAddress() + " already exists.");
        }

    }
}
