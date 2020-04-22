package com.testinium.sample;

import com.github.jenspiegsa.wiremockextension.ConfigureWireMock;
import com.github.jenspiegsa.wiremockextension.InjectServer;
import com.github.jenspiegsa.wiremockextension.WireMockExtension;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.core.Options;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import com.testinium.sample.configuration.SampleWebProjectApplication;
import com.testinium.sample.model.User;
import com.testinium.sample.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.Assert.*;

@SpringBootTest(classes = SampleWebProjectApplication.class)
class SampleWebProjectApplicationTest1 {

	private static final Logger log = LoggerFactory.getLogger(SampleWebProjectApplicationTest1.class);

	@Autowired
	UserService userService;


	@Test
	void userTest() throws InterruptedException {
		User user = userService.findByUser(53L);
		assertEquals("Ahmet", user.getName());
	}

	@Test
	void userListTest() throws InterruptedException {
		List<User> userList = userService.getUserList();
		assertTrue(userList.size() > 0);
	}


	@Test
	void addUserTest() throws InterruptedException {

		User user = userService.saveUser(new User("Melih", "Sakarya", 39));
		assertNotNull(user.getId());

	}



}
