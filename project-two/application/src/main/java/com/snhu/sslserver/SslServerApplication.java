
package com.snhu.sslserver;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SslServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SslServerApplication.class, args);
	}

	@GetMapping(value = "/hash", produces = "text/plain")
	public String getHash() throws NoSuchAlgorithmException {

		String data = "Jonathan Miranda - CS 305 Project Two - Artemis Financial - 2026-10-17";

		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));

		StringBuilder checksum = new StringBuilder();

		for (byte b : hash) {
			checksum.append(String.format("%02x", b & 0xff));
		}

		return "Name: Jonathan Miranda\n"
				+ "Data: " + data + "\n"
				+ "SHA-256 Checksum: " + checksum;
	}
}
