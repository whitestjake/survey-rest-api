package com.in28minutes.springboot.restapi.survey;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(controllers = SurveyResource.class)
class SurveyResourceTest {
	
	@MockBean
	private SurveyService surveyService;
	
	@Autowired
	private MockMvc mockMvc;
	
	private static String SPECIFIC_QUESTION_URL = "http://localhost:8080/surveys/Survey1/questions/Question1";
	
	@Test
	void retrieveSpecificSurveyQuestion_basicScenario() throws Exception {
		
		RequestBuilder requestBuilder = 
				MockMvcRequestBuilders
				.get(SPECIFIC_QUESTION_URL)
				.accept(MediaType.APPLICATION_JSON);
		
		Question question = new Question("Question1", "Most Popular Cloud Platform Today", 
				Arrays.asList("AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");
		
		when(surveyService.retrieveSpecificSurveyQuestion("Survey1","Question1"))
												.thenReturn(question);
		
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		
		String expectedResponse =
				"""
					{
						"id":"Question1",
						"description":"Most Popular CLoud Platform Today",
						"options":["AWS","Azure","Google Cloud","Oracle Cloud"],
						"correctAnswer":"AWS"
					}
				""";
		
		assertEquals(200, mvcResult.getResponse().getStatus());
		JSONAssert.assertEquals(
				expectedResponse, mvcResult.getResponse().getContentAsString(), false
				);
		
	}

}











