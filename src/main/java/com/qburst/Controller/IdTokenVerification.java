package com.qburst.Controller;

import java.util.Collections;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.qburst.Model.UsersData;

public class IdTokenVerification {

	public UsersData processToken(String idTokenString) {
		String returnVal = "";
		NetHttpTransport transport = new NetHttpTransport();
		GsonFactory gsonfactory = new GsonFactory();
		UsersData user = new UsersData();

		if (idTokenString != null && !idTokenString.equals("")) {
			GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, gsonfactory)
					.setAudience(Collections
							.singletonList("967649209783-pi515k1vmqr1igmq535chm1o32hb7fet.apps.googleusercontent.com"))
					.build();
			try {
				GoogleIdToken idToken = verifier.verify(idTokenString);
				if (idToken != null) {
					Payload payload = idToken.getPayload();

					String id = payload.getSubject();
					String name = (String) payload.get("name");
					String email = payload.getEmail();
					String imageurl = (String) payload.get("picture");
					user.setEmployeeID(id);
					user.setEmail(email);
					user.setName(name);
					user.setImageurl(imageurl);
					user.setUserType("User");
				} else {
					System.out.println("Invalid token");
				}
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
		} else {
			System.out.println("Invalid token");
		}

		return user;
	}

}
