package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}

	@GetMapping("/{id}")
	public User getUserById(@PathVariable Long id) {
		return userService.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<User> getUserById2(@PathVariable Long id) {
		User user = userService.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
		// return new ResponseEntity<>(user, HttpStatus.OK);
		// Create an ETag based on user attributes, such as ID and last modified date
		String eTag = Integer.toHexString(user.hashCode()); // Or use a more complex strategy for ETag generation
		return ResponseEntity.ok().cacheControl(CacheControl.maxAge(300, TimeUnit.SECONDS)).eTag(eTag).body(user);
	}

	@GetMapping("/see/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id,
			@RequestHeader(value = "If-None-Match", required = false) String ifNoneMatch,
			@RequestHeader(value = "If-Modified-Since", required = false) String ifModifiedSince) {
		User user = userService.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

		String eTag = Integer.toHexString(user.hashCode());
		ZonedDateTime lastModifiedDate = ZonedDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);

		// Check ETag header
		if (ifNoneMatch != null && ifNoneMatch.equals(eTag)) {
			return ResponseEntity.status(HttpStatus.NOT_MODIFIED).eTag(eTag).build();
		}

		// Check Last-Modified header
		if (ifModifiedSince != null && !ifModifiedSince.isEmpty()) {
			ZonedDateTime ifModifiedSinceDate = ZonedDateTime.parse(ifModifiedSince);
			if (lastModifiedDate.isBefore(ifModifiedSinceDate) || lastModifiedDate.isEqual(ifModifiedSinceDate)) {
				return ResponseEntity.status(HttpStatus.NOT_MODIFIED).eTag(eTag)
						.lastModified(lastModifiedDate.toInstant().toEpochMilli()).build();
			}
		}

		return ResponseEntity.ok().cacheControl(CacheControl.maxAge(300, TimeUnit.SECONDS)).eTag(eTag)
				.lastModified(lastModifiedDate.toInstant().toEpochMilli()).body(user);
	}

	@PostMapping
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		User createdUser = userService.createUserKafka(user);
		return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
		User updatedUser = userService.updateUser(id, userDetails);
		return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<User> patchUser(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
		User updatedUser = userService.patchUser(id, updates);
		return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	}
	
	// private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	// Annotate your class with @Slf4j. This will generate a static Logger field named log

    @GetMapping("/test")
    public String testLogging() {
        log.info("Test logging endpoint hit");
        return "Logging test completed";
    }
}
