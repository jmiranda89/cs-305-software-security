package com.snhu.sslserver;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class SslServerApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@Test
	void hashEndpointReturnsExpectedChecksumAndPlainText() throws Exception {
		mockMvc.perform(get("/hash"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
				.andExpect(content().string("Name: Jonathan Miranda\n"
						+ "Data: Jonathan Miranda - CS 305 Project Two - Artemis Financial - 2026-10-17\n"
						+ "SHA-256 Checksum: 69e8214bbe1873589b5b3478891e532d87f7cd39980ccbe90df5ea427e281119"));
	}

}
