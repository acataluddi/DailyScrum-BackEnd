package com.qburst.Validator;

import java.util.Collections;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.qburst.Model.UsersData;

public class TokenValidator {

	private static final String GOOGLE_CLIENT_ID = "967649209783-pi515k1vmqr1igmq535chm1o32hb7fet.apps.googleusercontent.com";

	public UsersData verifyToken(String idToken) {
		UsersData user = new UsersData();
		try {
			GoogleIdToken.Payload payLoad = getPayload(idToken);
			String id = payLoad.getSubject();
			String name = (String) payLoad.get("name");
			String email = payLoad.getEmail();
			user.setMemberID(id);
			user.setEmail(email);
			user.setName(name);
			System.out.println("User id: " + id);
			System.out.println("User name: " + name);
			System.out.println("User email: " + email);
			return user;
		} catch (Exception e) {
			// throw new RuntimeException(e);
			System.out.println(e);
			return null;
		}
	}

	private GoogleIdToken.Payload getPayload(String tokenString) throws Exception {

		JacksonFactory jacksonFactory = new JacksonFactory();
		GoogleIdTokenVerifier googleIdTokenVerifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(),
				jacksonFactory).setAudience(Collections.singletonList(GOOGLE_CLIENT_ID)).build();
		GoogleIdToken token = GoogleIdToken.parse(jacksonFactory, tokenString);

		GoogleIdToken idToken = googleIdTokenVerifier.verify(tokenString);
		if (idToken != null) {
			GoogleIdToken.Payload payload = token.getPayload();
			return payload;
		} else {
			throw new IllegalArgumentException("id token cannot be verified");
		}
	}
}
