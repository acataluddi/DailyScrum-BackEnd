package com.qburst.Controller;

import java.util.Collections;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

public class IdTokenVerifierAndParser {
	private static final String GOOGLE_CLIENT_ID = "967649209783-pi515k1vmqr1igmq535chm1o32hb7fet.apps.googleusercontent.com";

    public static GoogleIdToken.Payload getPayload (String tokenString) throws Exception {

        JacksonFactory jacksonFactory = new JacksonFactory();
        GoogleIdTokenVerifier googleIdTokenVerifier =
//                            new GoogleIdTokenVerifier(new NetHttpTransport(), jacksonFactory);
        					new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), jacksonFactory)
        					// Specify the CLIENT_ID of the app that accesses the backend:
        				    .setAudience(Collections.singletonList(GOOGLE_CLIENT_ID))
        				    .build();
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
