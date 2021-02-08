package org.springframework.samples.petclinic.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.samples.petclinic.model.CoreSearch;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ClientAPIService {

	public static String parse(String jsonLine) {
		JsonElement jelement = new JsonParser().parse(jsonLine);
		JsonObject jobject = jelement.getAsJsonObject();

		String result = jobject.get("result").getAsString();
		return result;
	}

	public static CoreSearch LlamadaLimeSurvey() throws UnsupportedEncodingException {
		DefaultHttpClient client = new DefaultHttpClient();
		CoreSearch p= null;
		HttpPost post = new HttpPost("http://www.golden5.org/limesurvey_3/index.php/admin/remotecontrol");
		post.setHeader("Content-type", "application/json");
		post.setEntity(new StringEntity(
				"{\"method\": \"get_session_key\", \"params\": [\"golden5\", \"mano;5;dedos\" ], \"id\": 1}"));
		try {
			HttpResponse response = client.execute(post);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				String sessionKey = parse(EntityUtils.toString(entity));
				post.setEntity(new StringEntity("{\"method\": \"list_surveys\", \"params\": [ \"" + sessionKey
						+ "\", \"golden5\" ], \"id\": 1}"));
				response = client.execute(post);
				if (response.getStatusLine().getStatusCode() == 200) {
					entity = response.getEntity();
					p = parse2Core(EntityUtils.toString(entity));
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			System.out.println("mal parse");
			e.printStackTrace();
		}
		return p;
	}

	public static CoreSearch parse2Core(String json) {
		Gson g = new Gson();
		CoreSearch p = g.fromJson(json, CoreSearch.class);
		return p;
	}
}
