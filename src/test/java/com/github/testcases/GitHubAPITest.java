package com.github.testcases;


import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.github.base.APIHelper;
import com.github.base.BaseTest;
import com.github.javafaker.Faker;
import com.github.requestPOJO.CreateRepoRequest;
import com.github.responsePOJO.GetNonExisitingRepoResponse;
import com.github.responsePOJO.GetSingleRepositoryResponse;
import com.github.responsePOJO.UnProcessableEntityResponse;
import com.github.utilities.Constants;
import com.github.utilities.ExcelUtils;
import com.github.utilities.JsonSchemaValidate;
import com.github.utilities.PropertiesUtility;
import com.github.utilities.TestDataUtility;
import com.google.gson.JsonObject;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@Listeners(com.github.listeners.GithubListeners.class)
public class GitHubAPITest extends BaseTest {
	APIHelper apiHelper;
    ExcelUtils excelUtils;
    private Faker faker;

    @BeforeClass
    public void beforeClass() {
        faker = new Faker();
        apiHelper = new APIHelper();
        excelUtils = new ExcelUtils();
    }

    @Test(description = "TestCase 1 : GET Method : get single Repository")
    public void test_getSingleRepository() {
    	
		Object[][] repoNames = excelUtils.readAllDataSheet(Constants.REPONAMES, "repositories");
    	Random random = new Random();
        int randInt = random.nextInt(repoNames.length);
        String repo_name = (String) repoNames[randInt][0];
        
        String actualRepoFullName = PropertiesUtility.getProperty("owner")+"/"+repo_name;
    	Response response = apiHelper.getSingleRepository(repo_name);
    	GetSingleRepositoryResponse resObj = response.getBody().as(new TypeRef<GetSingleRepositoryResponse>() {
        });
    	
    	System.out.println(resObj.default_branch);
    	System.out.println(response.contentType());
    	
    	JsonSchemaValidate.validateSchema(response.asPrettyString() ,"SingleRepositoryResponse.json");
    	assertEquals(response.statusCode(), HttpStatus.SC_OK, TestDataUtility.getProperty("404"));
    	assertEquals(actualRepoFullName, resObj.full_name, "The expected full_name doesnot match with the actual full name");
    	assertEquals("master", resObj.default_branch, "The expected default_branch doesnot match with the actual default_branch");
    	assertEquals(response.contentType(), "application/json; charset=utf-8", "Content type doesnot match");
    }
    
    @Test(description = "TestCase 2: Get Method: Negative TestCase : get a non existing repository")
    public void test_getNonExistingRespository() {
    	String fakeRepoName = faker.name().firstName();
    	// System.out.println(fakeRepoName);
    	Response response = apiHelper.getSingleRepository(fakeRepoName);
    	
    	GetNonExisitingRepoResponse resObj = response.getBody().as(new TypeRef<GetNonExisitingRepoResponse>() {});
    	
    	JsonSchemaValidate.validateSchema(response.asPrettyString(), "NonExistentRepoResponseSchema.json");
    	assertEquals(TestDataUtility.getProperty("404"), resObj.message, "Mismatch in the error message");
    	assertEquals("404", resObj.status, "Status code mismatch: 404 expected");
    }
    
    @Test(description = "TestCase 3 : Get Method: Get all the repositories")
    public void test_getAllRepositories() {
    	try {
    		Response response = apiHelper.getAllRepositories();
    		List<GetSingleRepositoryResponse> resObj = response.getBody().as(new TypeRef<List <GetSingleRepositoryResponse>>() {});
    		System.out.println(response.getStatusCode());
    		assertEquals(HttpStatus.SC_OK, response.getStatusCode(),"Status Code mismatch: 200 Expected, but nt found");
    		System.out.println("Total number of repositories: "+ resObj.size());
    		System.out.println("Printing all the public repository names: ");
    		for(GetSingleRepositoryResponse res: resObj) {
    			if(res.myprivate == false) {
    				System.out.println(res.name);
    			}
    		}
    		// JsonSchemaValidate.validateSchema(response.asPrettyString(), "SingleRepositoryResponse.json");
    		assertEquals(response.contentType(), "application/json; charset=utf-8", "Content type doesnot match");
    		
    	}
    	
    	catch(Exception e) {
    		Assert.fail(e.getMessage());
    	}

    }
    
    @Test(priority = 1, description = "TestCase 4: POST method : create a repsoitory")
    public void test_creatingRepository() throws IOException {
    	String repoName = faker.name().lastName();
    	CreateRepoRequest newRepoRequestBody = CreateRepoRequest.builder().name(repoName).description("This is your first repo!").
    			homepage("https://github.com").myprivate(false).build();
    	Response response = apiHelper.createRepository(newRepoRequestBody);
    	// response.then().log().all();
    	
    	File file = new File(Constants.NEW_REPONAMES);
    	FileInputStream fis = new FileInputStream(file);
    	Properties prop = new Properties();
    	prop.load(fis);
   
    	prop.put("repoName", repoName);
    	FileOutputStream fos = new FileOutputStream(file);
    	prop.store(fos, "Write to newRepo.properties file");
    	
    	JsonSchemaValidate.validateSchema(response.asPrettyString(), "CreateRepositoryResponseSchema.json");
    	GetSingleRepositoryResponse resObj = response.getBody().as(new TypeRef<GetSingleRepositoryResponse>(){});
    	assertEquals(response.statusCode(),HttpStatus.SC_CREATED, "UNSUCCESSFUL");
    	assertEquals(repoName, resObj.name, "Repository names mismatch");
    	assertEquals(PropertiesUtility.getProperty("owner"), resObj.owner.login,"login owner mismatch");
    	assertEquals("User", resObj.owner.type,"login owner type mismatch");
    }
    
    
    @Test(description = "TestCase 5: POST Method: Negative TestCase : create a repository with an existing repository name")
    public void test_creatingRepoWithExistingName() {
    	Object[][] repoNames = excelUtils.readAllDataSheet(Constants.REPONAMES, "repositories");
    	Random random = new Random();
        int randInt = random.nextInt(repoNames.length);
        String repo_name = (String) repoNames[randInt-1][0];
        System.out.println(repo_name);
        
        CreateRepoRequest newRepoRequestBody = CreateRepoRequest.builder().name(repo_name).description("This is your first repo!").
    			homepage("https://github.com").myprivate(false).build();
        Response response = apiHelper.createRepositoryWithExistingName(newRepoRequestBody);
    	// response.then().log().all();
        
        UnProcessableEntityResponse resObj = response.getBody().as(new TypeRef<UnProcessableEntityResponse>() {});
    	
    	JsonSchemaValidate.validateSchema(response.asPrettyString(), "UnProcessableEntityResponse_422_Schema.json");
    	assertEquals(response.statusCode(),HttpStatus.SC_UNPROCESSABLE_ENTITY, "UNSUCCESSFUL");
    	assertEquals(resObj.getMessage(), TestDataUtility.getProperty("422"));
    	assertEquals(resObj.errors.get(0).message, "name already exists on this account");

    }
    
    @Test(priority = 2, dependsOnMethods = {"test_creatingRepository"}, description = "TestCase 6 : PATCH Method : update an existing repository" )
    public void test_updateRepository() throws IOException {
    	Response response = null;
    	File file = new File(Constants.NEW_REPONAMES);
    	FileInputStream fis = new FileInputStream(file);
    	Properties prop = new Properties();
    	prop.load(fis);
    	String repoName = prop.getProperty("repoName");
    	String modifiedName = repoName+"_updatedName";
    	response = apiHelper.updateRepository(repoName, modifiedName);
    	GetSingleRepositoryResponse resObj = response.getBody().as(new TypeRef<GetSingleRepositoryResponse>() {});
    	
    	if(response.getStatusCode()==HttpStatus.SC_OK) {
    		prop.put("repoName", modifiedName);
    		FileOutputStream fos = new FileOutputStream(file);
    		prop.store(fos, "Writing the modified repository name");
    	}
    	assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Status mismatch");
    	assertEquals(resObj.getName(), modifiedName, "The name of the repository not updated");
    	
    }
    
    @Test(priority = 3, dependsOnMethods = {"test_creatingRepository","test_updateRepository"}, description = "TestCase 7: DELETE Method : delete a repository")
    public void test_deleteRepo() throws IOException {
    	File file = new File(Constants.NEW_REPONAMES);
    	FileInputStream fis = new FileInputStream(file);
    	Properties prop = new Properties();
    	prop.load(fis);
    	String repoName = prop.getProperty("repoName");
    	Response response =null;
    	response = apiHelper.deleteRepository(repoName);
    	assertEquals(response.getStatusCode(), HttpStatus.SC_NO_CONTENT,"Status code mismatch");
    	assertEquals(response.getBody().asString(),"", "Response body is not empty");
    	
    }
    
    @Test(description = "TestCase 8 : DELETE Method: Negative testcase : Delete a non exisitng repository")
    public void test_deleteNonExistingRepo() {
    	Response response = null;
    	String fakeRepoName = faker.name().firstName();
    	System.out.println(fakeRepoName);
    	
    	response = apiHelper.deleteNonExistingRepository(fakeRepoName);
    	GetNonExisitingRepoResponse resObj = response.getBody().as(new TypeRef<GetNonExisitingRepoResponse>() {});
    	
    	assertEquals(response.statusCode(), HttpStatus.SC_NOT_FOUND, "Status code mismatch");
    	assertEquals(resObj.message, TestDataUtility.getProperty("404"), "Response message mismatch");
    	
    }
}
