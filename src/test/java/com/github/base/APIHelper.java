package com.github.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.requestPOJO.CreateRepoRequest;
import com.github.utilities.Constants;
import com.github.utilities.ExcelUtils;
import com.github.utilities.PropertiesUtility;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.internal.http.HTTPBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import lombok.extern.slf4j.Slf4j;



@Slf4j
public class APIHelper {
	RequestSpecification reqSpec;
	
	
	public APIHelper() {
		RestAssured.baseURI = PropertiesUtility.getProperty("baseURL");
		
		
		
	}
	
	public Response getSingleRepository(String repo_name) {
		reqSpec = RestAssured.given();
		reqSpec.headers(getHeaders());
		reqSpec.pathParams(getPathParams(repo_name));
		System.out.println(repo_name);
		Response response = null;
		try {
			response = reqSpec.get("/repos/{owner}/{repo}");
			if(response.getStatusCode() == HttpStatus.SC_OK) 
			 System.out.println("GET Method: Get Single Repository :: SUCCESSFUL");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return response;
		
	}
	
	public Response createRepository(CreateRepoRequest repoRequest) {
		reqSpec = RestAssured.given();
		reqSpec.headers(getHeaders());
		Response response = null;
		try {
			reqSpec.body(new ObjectMapper().writeValueAsString(repoRequest));
			response = reqSpec.when().post("/user/repos");
			response.prettyPrint();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public Response createRepositoryWithExistingName(CreateRepoRequest repoRequest) {
		reqSpec = RestAssured.given();
		reqSpec.headers(getHeaders());
		Response response = null;
		try {
			reqSpec.body(new ObjectMapper().writeValueAsString(repoRequest));
			response = reqSpec.when().post("/user/repos");
			response.prettyPrint();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public Response getAllRepositories() {
		reqSpec = RestAssured.given();
		reqSpec.headers(getHeaders());
		
		Response response = null;
		try {
			response = reqSpec.get("/user/repos");
			if(response.getStatusCode() == HttpStatus.SC_OK) 
			 System.out.println("GET Method: Get All Repositories :: SUCCESSFUL");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return response;
		
	}
	
	public HashMap<String, String> getHeaders() {
		HashMap<String, String> headers = new HashMap<String, String>();
		
		headers.put("Content-Type", "application/json");
		headers.put("Authorization", "Bearer "+ PropertiesUtility.getProperty("api_token"));
		
		return headers;
	}
	
	public HashMap<String, String> getPathParams(String repo_name) {
		HashMap<String, String> pathParams = new HashMap<String, String>();
		
		pathParams.put("owner", PropertiesUtility.getProperty("owner"));
		pathParams.put("repo",repo_name);
		
		return pathParams;
	}
	
	public static Headers httpHeaderManager(){
	    Header contentType = new Header("Content-Type","application/json");
	    Header authorization = new Header("Authorization", PropertiesUtility.getProperty("api_token"));
	    List<Header> headerList = new ArrayList<Header>();
	    headerList.add(contentType);
	    headerList.add(authorization);
	    Headers header = new Headers(headerList);
	    return header;
	}
	
	public Response updateRepository(String repo_name,String modifiedName) {
		reqSpec = RestAssured.given();
		reqSpec.headers(getHeaders());
		reqSpec.pathParams(getPathParams(repo_name));
		
		CreateRepoRequest updateRepoBody = CreateRepoRequest.builder().name(modifiedName).build();
		reqSpec.body(updateRepoBody);
		Response response = null;
		try {
			response = reqSpec.patch("/repos/{owner}/{repo}");
			if(response.getStatusCode() == HttpStatus.SC_OK) 
			 System.out.println("patch Method: update Repository :: SUCCESSFUL");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return response;
		
	}
	
	
	public Response deleteRepository(String repo_name) {
		reqSpec = RestAssured.given();
		reqSpec.headers(getHeaders());
		reqSpec.pathParams(getPathParams(repo_name));
		System.out.println(repo_name);
		Response response = null;
		try {
			response = reqSpec.delete("/repos/{owner}/{repo}");
			if(response.getStatusCode() == HttpStatus.SC_NO_CONTENT) 
			 System.out.println("delete Method: delete Repository :: SUCCESSFUL");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return response;
		
	}
	
	public Response deleteNonExistingRepository(String repo_name) {
		reqSpec = RestAssured.given();
		reqSpec.headers(getHeaders());
		reqSpec.pathParams(getPathParams(repo_name));
		System.out.println(repo_name);
		Response response = null;
		try {
			response = reqSpec.delete("/repos/{owner}/{repo}");
			if(response.getStatusCode() == HttpStatus.SC_NOT_FOUND) 
			 System.out.println("delete Method : deleting non existent repository :: SUCCESSFUL");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return response;
		
	}
	
	
	

}
