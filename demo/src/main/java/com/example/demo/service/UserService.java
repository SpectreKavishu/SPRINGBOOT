package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.model.UserCreatedEvent;
import com.example.demo.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
	private final UserRepository userRepository;
	private final KafkaTemplate<String, UserCreatedEvent> kafkaTemplate;
	
	@Value("${kafka.topic}")
    private String userCreatedTopic;

	public UserService(UserRepository userRepository, KafkaTemplate<String, UserCreatedEvent> kafkaTemplate) {
		this.userRepository = userRepository;
		this.kafkaTemplate = kafkaTemplate;
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	public Optional<User> findById(Long id) {
		return userRepository.findById(id);
	}

	public User createUser(User user) {
		return userRepository.save(user);
		
	}
	
	public User createUserKafka(User user) {
		userRepository.save(user);
		// Publish UserCreatedEvent to Kafka (asynchronous)
        UserCreatedEvent event = new UserCreatedEvent(user.getId(), user.getName(), user.getEmail());
        kafkaTemplate.send(userCreatedTopic, event);
        return userRepository.save(user);
	}
	
	// Method to update an existing user
    public User updateUser(Long id, User userDetails) {
        // Find the user by id (optional)
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        // Update the fields with new values from userDetails
        existingUser.setName(userDetails.getName());
        existingUser.setEmail(userDetails.getEmail());
        // Update other fields as needed

        // Save the updated user back to the database
        return userRepository.save(existingUser);
    }

    // Method to delete a user by id
    public void deleteUser(Long id) {
        // Check if the user exists before deleting
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        // Delete the user from the repository
        userRepository.delete(existingUser);
    }
    
    // Method to partially update a user
    public User patchUser(Long id, Map<String, Object> updates) {
        // Find the existing user
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        // Iterate through the provided updates map and update the user fields dynamically
        updates.forEach((key, value) -> {
            switch (key) {
                case "name":
                    existingUser.setName((String) value);
                    break;
                case "email":
                    existingUser.setEmail((String) value);
                    break;
                // Add more fields as necessary
                default:
                    throw new IllegalArgumentException("Invalid field: " + key);
            }
        });

        // Save and return the updated user
        return userRepository.save(existingUser);
    }
}
