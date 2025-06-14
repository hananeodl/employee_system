package com.example.employeeprofile.service;

import com.example.employeeprofile.model.Role;
import com.example.employeeprofile.model.User;
import com.example.employeeprofile.repository.RoleRepository;
import com.example.employeeprofile.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//@RequiredArgsConstructor
public class UserService implements UserDetailsService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public User registerUser(String username, String email, String password, String roleName) {
		if (userRepository.existsByUsername(username)) {
			throw new RuntimeException("Username already exists");
		}
		if (userRepository.existsByEmail(email)) {
			throw new RuntimeException("Email already exists");
		}

		Role role = roleRepository.findByName(roleName)
				.orElseThrow(() -> new RuntimeException("Role not found"));

		User user = new User();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(passwordEncoder.encode(password));
		user.setRole(role);

		return userRepository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

		return org.springframework.security.core.userdetails.User.builder()
				.username(user.getUsername())
				.password(user.getPassword())
				.roles(user.getRole().getName().replace("ROLE_", "")) // ex: ADMIN
				.build();
	}

	public User findByUsername(String username) {
		return userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
	}
	// Méthode pour récupérer tous les utilisateurs (à ajouter)
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}
	public User saveUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		// Optionnel: affecter rôle USER par défaut si null
		if (user.getRole() == null) {
			Role role = roleRepository.findByName("ROLE_USER")
					.orElseThrow(() -> new RuntimeException("Role USER not found"));
			user.setRole(role);
		}
		return userRepository.save(user);
	}

	// Méthode pour mettre à jour un utilisateur existant
	public User updateUser(Long id, User updatedUser) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("User not found with id " + id));

		user.setUsername(updatedUser.getUsername());
		user.setEmail(updatedUser.getEmail());
		if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
			user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
		}
		user.setRole(updatedUser.getRole());

		return userRepository.save(user);
	}

	// Méthode pour supprimer un utilisateur
	public void deleteUser(Long id) {
		if (!userRepository.existsById(id)) {
			throw new RuntimeException("User not found with id " + id);
		}
		userRepository.deleteById(id);
	}
	// Méthode pour récupérer un user par id
	public User findById(Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("User not found with id: " + id));
	}
}
