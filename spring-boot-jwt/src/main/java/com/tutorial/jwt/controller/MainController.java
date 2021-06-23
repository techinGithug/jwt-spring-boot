package com.tutorial.jwt.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.tutorial.jwt.model.AuthenticationRequest;
import com.tutorial.jwt.model.AuthenticationResponse;
import com.tutorial.jwt.model.UserDetail;
import com.tutorial.jwt.service.MyUserDetailService;
import com.tutorial.jwt.util.JwtUtil;

@RestController
@CrossOrigin (origins = "*", allowedHeaders = "*")
//@RequestMapping("/api")
public class MainController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private MyUserDetailService userDetailsService;
	
	@Autowired
	private JwtUtil jwtTokenUtil;
	
	@GetMapping("/hello")
	public ResponseEntity<String> hello() {
		return new ResponseEntity<>("Hello, wrold!", HttpStatus.OK);
	}

	@GetMapping("/users")
//	public List<UserDetail> users() {
//		List<UserDetail> userList = new ArrayList<>();
//		userList = userDetailsService.getUsers();
//		return userList;
//	}
	
	public ResponseEntity<List<UserDetail>> users(UriComponentsBuilder b) {
		List<UserDetail> userList = new ArrayList<>();
		userList = userDetailsService.getUsers();
		
		// Set location
		UriComponents uriComponents = 
		        b.path("/users").buildAndExpand(userList);
		
		HttpHeaders header = new HttpHeaders();
		header.add("From", "MainController");
		header.setLocation(uriComponents.toUri());
		
		return ResponseEntity.ok().headers(header).body(userList);
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
			);

		} catch(BadCredentialsException e) {
			throw new Exception("Incorrect username or password "+e);

		}
		
		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());
		
		final String jwt = jwtTokenUtil.generateToken(userDetails);
//		return ResponseEntity.ok(new AuthenticationResponse(jwt));
        return new ResponseEntity<>(new AuthenticationResponse(jwt), HttpStatus.OK);

	}
}
