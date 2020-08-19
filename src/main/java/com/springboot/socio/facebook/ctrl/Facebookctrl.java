package com.springboot.socio.facebook.ctrl;

import java.util.Arrays;

import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Class to show the index page, validate facebook credentials, and display the user details.
 * @author yatin-batra
 */
@Controller
public class Facebookctrl {

	// Creates a facebook connection using the given application id and secret key.
//	private FacebookConnectionFactory factory = new FacebookConnectionFactory("<!-- Your application id -->", "<!-- Your secret key -->");
	private FacebookConnectionFactory factory = new FacebookConnectionFactory("363204241248594", "d95a42ee352421ddf0aa61c6fc16979e");

	// Index page.
	@GetMapping(value= "/")
	public ModelAndView index() {
		return new ModelAndView("welcome");
	}

	// Redirection uri.
	@GetMapping(value = "/useapp")
	public String redirect() {
		// Creates the OAuth2.0 flow and performs the oauth handshake on behalf of the user.
		OAuth2Operations operations= factory.getOAuthOperations();

		// Builds the OAuth2.0 authorize url and the scope parameters.
		OAuth2Parameters params= new OAuth2Parameters();
		params.setRedirectUri("http://localhost:8102/forwardLogin");
		params.setScope("email, public_profile, user_birthday,user_friends,user_gender,user_hometown,user_location");
//		params.setScope("birthday, user_birthday");

		// Url to redirect the user for authentication via OAuth2.0 authorization code grant.
		String authUrl = operations.buildAuthenticateUrl(params);
		System.out.println("Generated url is= " + authUrl);
		return "redirect:" + authUrl;
	}

	// Welcome page.
	@GetMapping(value = "/forwardLogin")
	public ModelAndView prodducer(@RequestParam("code") String authorizationCode) {
		// Creates the OAuth2.0 flow and performs the oauth handshake on behalf of the user.
		OAuth2Operations operations= factory.getOAuthOperations();

		// OAuth2.0 access token.
		// "exchangeForAccess()" method exchanges the authorization code for an access grant.
		AccessGrant accessToken= operations.exchangeForAccess(authorizationCode, "http://localhost:8102/forwardLogin", null);

		Connection<Facebook> connection= factory.createConnection(accessToken);

		// Getting the connection that the current user has with facebook.
		Facebook facebook= connection.getApi();
		// Fetching the details from the facebook.
		String[] fields = { "id", "name", "email", "about", "birthday","gender","hometown","location"};
		User userProfile= facebook.fetchObject("me", User.class, fields);
		System.out.println("User Details::::::::::::                        "+userProfile.getHometown().getName());
		ModelAndView model = new ModelAndView("details");
		model.addObject("user", userProfile);
		return model;
	}
}