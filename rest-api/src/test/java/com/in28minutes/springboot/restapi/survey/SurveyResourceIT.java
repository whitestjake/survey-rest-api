package com.in28minutes.springboot.restapi.survey;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SurveyResourceIT {
	
	private static String GENERIC_QUESTIONS_URL = "/surveys/survey1/questions/";
	
	@Autowired
	TestRestTemplate template;
	
	@Test
	void retrieveSpecificSurveyQuestion_basicScenario() throws JSONException {
		
		ResponseEntity<String> responseEntity = 
									template.getForEntity(GENERIC_QUESTIONS_URL, String.class);
		
		String expectedResponse =
				"""
					[
						{
							"id": "Question1"
						},
						{
							"id": "Question2"
						},
						{
							"id": "Question3"
						}
					]
				""";
		
		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
		assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));
		
		JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);	

	}
	
	@Test
	void addNewSurveyQuestion_basicScenario() {
		
		String requestBody =
				"""
					{
						"description": "Your Favorite Language",
						"options": [
								"Java",
								"Python",
								"JavaScript",
								"Haskell"
						],
						"correctAnswer": "Python"
					}
				""";
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type","application/json");
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(requestBody, headers);
		
		ResponseEntity<String> responseEntity = 
				template.exchange(GENERIC_QUESTIONS_URL, HttpMethod.POST, httpEntity, 
												String.class);
		
		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
		
		assertTrue(responseEntity.getHeaders().get("Location").get(0).contains("/surveys/Survey1/questions/"));
		
		
		System.out.println(responseEntity.getHeaders());
		System.out.println(responseEntity.getBody());
		
		
	}

}














