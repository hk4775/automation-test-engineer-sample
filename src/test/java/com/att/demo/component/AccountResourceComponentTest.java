package com.att.demo.component;

import static org.hamcrest.CoreMatchers.equalTo;

import java.net.InetAddress;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.att.demo.model.Account;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountResourceComponentTest {
	@LocalServerPort
	protected int randomServerPort;
	
	private String uri ="/accounts";
	
	@Before
	public void setUp() throws Exception {
		RestAssured.baseURI = "http://" + InetAddress.getLocalHost().getHostName() + ":" + randomServerPort + "/api";
	}
	
	private  RequestSpecification givenBaseSpec() {
		return 
				RestAssured.given()
					.accept(ContentType.JSON)
					.contentType(ContentType.JSON);
	}
	
	@Test
	public void testfindAllAccount_success() {
		
		givenBaseSpec()
				.when()
				.get(uri)
				.then()
					.statusCode(200);
	}
	
	@Test
	public void testCreateAccount_success() {
		
			
		Account account = new Account();
		account.setId(54321);
		account.setName("test-create");
		
		givenBaseSpec()
			.body(account)
			.when()
				.post(uri)
				.then()
					.statusCode(201);
	}
	
	@Test
	//Validate get accountId Api
	public void testGetAccount_success() {
		givenBaseSpec()
			.pathParam("accountId", 1)
			.when()
				.get("/accounts/{accountId}")
				.then()
					.statusCode(200).and()
	                .contentType(ContentType.JSON).assertThat().body("content.name", equalTo("Account1"));
	                		
	}
	
	
	
}
