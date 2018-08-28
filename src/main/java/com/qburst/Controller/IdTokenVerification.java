package com.qburst.Controller;

import java.util.Collections;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;

public class IdTokenVerification {
	
	
	public String processToken(String idTokenString) {
		String returnVal="";
		NetHttpTransport transport = new NetHttpTransport();
		GsonFactory gsonfactory = new GsonFactory();
		
		if(idTokenString != null && !idTokenString.equals("")){
			GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, gsonfactory)
				.setAudience(Collections.singletonList("967649209783-pi515k1vmqr1igmq535chm1o32hb7fet.apps.googleusercontent.com"))
				//.setAudience(Arrays.asList(clientid1, clientid2, clientid3)) //if more than one client id
				.build();
			try{
				GoogleIdToken idToken = verifier.verify(idTokenString);
				if (idToken != null) {
					Payload payload = idToken.getPayload();
					returnVal = "Valid Token! User ID: " + payload.getSubject();
//					String email = payload.getEmail();
//					System.out.println(email);
//					boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
//					System.out.println(emailVerified);
//					String name = (String) payload.get("name");
//					System.out.println(name);
//					String pictureUrl = (String) payload.get("picture");
//					System.out.println(pictureUrl);
//					String locale = (String) payload.get("locale");
//					System.out.println(locale);
//					String familyName = (String) payload.get("family_name");
//					System.out.println(familyName);
//					String givenName = (String) payload.get("given_name");
//					System.out.println(givenName);
				} else {
					returnVal = "Invalid ID token.";
				}
			} catch (Exception ex){
				returnVal = ex.getMessage();
			}
		}
		else{
			returnVal = "Bad Token Passed";
		}
		
		return returnVal;
	}

}
