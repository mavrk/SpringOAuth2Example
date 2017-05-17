package com.mavrk.springmvc;

import com.mavrk.springmvc.model.User;
import com.mavrk.springmvc.model.AuthTokenInfo;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Sanatt on 17-05-2017.
 * TODO add more tests to play with functionality
 */
public class SpringRestClient {

	public static final String REST_SERVICE_URI = "http://localhost:8080/SpringOAuth2Example";
	public static final String AUTH_SERVER_URI = "http://localhost:8080/SpringOAuth2Example/oauth/token";
	public static final String PASSWORD_GRANT = "?grant_type=password&username=bill&password=abc123";
	public static final String ACCESS_TOKEN = "?access_token=";

	/**
	 * Prepare HTTP Headers
	 * @return HTTP headers
	 */
	private static HttpHeaders getHeaders(){
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		return headers;
	}

	/**
	 * Add HTTP Authorization header, using Basic-Authentication to send client-credentials
	 * @return HTTP headers
	 */
	private static HttpHeaders getHeadersWithClientCredentials(){
		String plainClientCredentials = "my-trusted-client:secret";
		String base64ClientCredentials = new String(Base64.encodeBase64(plainClientCredentials.getBytes()));

		HttpHeaders headers = getHeaders();
		headers.add("Authorization", "Basic " + base64ClientCredentials);
		return headers;
	}

	private static AuthTokenInfo sendTokenRequest(){
		RestTemplate restTemplate = new RestTemplate();
		AuthTokenInfo tokenInfo = null;

		HttpEntity<String> request = new HttpEntity<String>(getHeadersWithClientCredentials());
		ResponseEntity<Object> responseEntity = restTemplate.exchange(AUTH_SERVER_URI + PASSWORD_GRANT, HttpMethod.POST, request, Object.class);
		LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) responseEntity.getBody();

		if(map!=null){
			tokenInfo = new AuthTokenInfo();
			tokenInfo.setAccess_token((String) map.get("access_token"));
			tokenInfo.setToken_type((String) map.get("token_type"));
			tokenInfo.setRefresh_token((String) map.get("refresh_token"));
			tokenInfo.setExpires_in((int) map.get("expires_in"));
			tokenInfo.setScope((String) map.get("scopes"));
			System.out.println(tokenInfo);
		} else {
			System.out.println("No user exists");
		}

		return tokenInfo;
	}

	private static void listAllUsers(AuthTokenInfo tokenInfo){
		Assert.notNull(tokenInfo, "Authenticate first please!!");
		System.out.println("\nTesting listAllUsers API----------");
		RestTemplate restTemplate = new RestTemplate();

		HttpEntity<String> request = new HttpEntity<String>(getHeaders());
		ResponseEntity<List> response = restTemplate.exchange(REST_SERVICE_URI + "/user/" + ACCESS_TOKEN + tokenInfo.getAccess_token(),
				HttpMethod.GET, request, List.class);
		List<LinkedHashMap<String, Object>> usersMap = (List<LinkedHashMap<String, Object>>) response.getBody();

		if(usersMap != null){
			for(LinkedHashMap<String, Object> map : usersMap){
				System.out.println("User : id=" + map.get("id") + ", Name=" + map.get("name") + ", Age=" + map.get("age") + ", Salary=" + map.get("salary"));
			}
		} else {
			System.out.println("No user exists -------");
		}
	}

	private static void	getUser(AuthTokenInfo tokenInfo){
		Assert.notNull(tokenInfo, "Authenticate first please....");
		System.out.println("\nTesting getUser API----------");
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> request = new HttpEntity<String>(getHeaders());
		ResponseEntity<User> responseEntity = restTemplate.exchange(REST_SERVICE_URI + "/user/1" + ACCESS_TOKEN + tokenInfo.getAccess_token(),
				HttpMethod.GET, request, User.class);
		User user = responseEntity.getBody();
	}

	private static void createUser(AuthTokenInfo tokenInfo) {
		Assert.notNull(tokenInfo, "Authenticate first please......");
		System.out.println("\nTesting create User API----------");
		RestTemplate restTemplate = new RestTemplate();
		User user = new User(0,"Sanatt",19,1234);
		HttpEntity<Object> request = new HttpEntity<Object>(user, getHeaders());
		URI uri = restTemplate.postForLocation(REST_SERVICE_URI+"/user/"+ACCESS_TOKEN+tokenInfo.getAccess_token(),
				request, User.class);
		System.out.println("Location : "+uri.toASCIIString());
	}

	public static void main(String[] args) {
		AuthTokenInfo tokenInfo = sendTokenRequest();

		listAllUsers(tokenInfo);
		getUser(tokenInfo);
	}
}
