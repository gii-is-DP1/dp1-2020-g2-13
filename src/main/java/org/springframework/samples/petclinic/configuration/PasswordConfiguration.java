package org.springframework.samples.petclinic.configuration;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordConfiguration {
	
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}

}
