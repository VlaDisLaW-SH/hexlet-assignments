package exercise.service;

import exercise.model.User;
import exercise.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    // BEGIN
    public Mono<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    public Mono<User> create(User user) {
        return userRepository.save(user);
    }

    public Mono<User> patch(Long id, User patchUser) {
        return userRepository.findById(id)
            .flatMap(existingItem -> {
                if (patchUser.getFirstName() != null) {
                    existingItem.setFirstName(patchUser.getFirstName());
                }
                if (patchUser.getLastName() != null) {
                    existingItem.setLastName(patchUser.getLastName());
                }
                if (patchUser.getEmail() != null) {
                    existingItem.setEmail(patchUser.getEmail());
                }
                return userRepository.save(existingItem);
            });
    }

    public Mono<Void> delete(Long id) {
        return userRepository.deleteById(id);
    }
    // END
}
