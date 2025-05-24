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

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;

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

}
