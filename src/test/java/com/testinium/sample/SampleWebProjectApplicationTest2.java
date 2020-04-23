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

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.*;

import com.testinium.sample.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@SpringBootTest(classes = SampleWebProjectApplication.class)
@ExtendWith(WireMockExtension.class)
class SampleWebProjectApplicationTest2 {

	private static final Logger log = LoggerFactory.getLogger(SampleWebProjectApplicationTest2.class);

	@Autowired
	UserService userService;

	@InjectServer
	WireMockServer wireMockServer;

	@ConfigureWireMock
	Options options = wireMockConfig().
			port(9080)
			.notifier(new ConsoleNotifier(true))
			.extensions(new ResponseTemplateTransformer(true));


	@Test
	void userTest() throws InterruptedException {

		stubFor(get(urlPathMatching("/rest/user/[0-9]*"))

				.willReturn(aResponse()
						.withStatus(200)
						.withStatus(HttpStatus.OK.value())
						.withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
						//.withBody("{\"id\":53,\"createUser\":null,\"updateUser\":null,\"createDate\":null,\"updateDate\":null,\"name\":\"Ahmet\",\"lastname\":\"Dursun\",\"age\":29}")));
						.withBodyFile("user.json")));

		User user = userService.findByUser(53L);
		assertEquals("Ahmet", user.getName());
	}

	@Test
	void userListTest() throws InterruptedException {

		stubFor(get(urlPathEqualTo("/rest/user/list"))
				.willReturn(aResponse()
						.withStatus(200)
						.withStatus(HttpStatus.OK.value())
						.withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
						.withBodyFile("users.json")));

		List<User> userList = userService.getUserList();
		assertTrue(userList.size() > 0);

	}


	@Test
	void addUserTest() throws InterruptedException {

		stubFor(post(urlPathMatching("/rest/user/add"))

				.willReturn(aResponse()
						.withStatus(200)
						.withStatus(HttpStatus.OK.value())
						.withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
						.withBodyFile("add-user.json")));

		User user = userService.saveUser(new User("Melih", "Sakarya", 39));
		assertNotNull(user.getId());

	}

	@Test
	public void kullaniciListesi(){
		List<User> kullaniciListesi = userService.kullaniciListesi();
		assertTrue(kullaniciListesi.size() > 0);

	}

}
