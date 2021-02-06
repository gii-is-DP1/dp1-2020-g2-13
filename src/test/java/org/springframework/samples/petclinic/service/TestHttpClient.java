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
import org.springframework.samples.petclinic.model.Result;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

public class TestHttpClient {
	public static String json;
	public static String parse(String jsonLine) {
		JsonElement jelement = new JsonParser().parse(jsonLine);
		JsonObject jobject = jelement.getAsJsonObject();

		String result = jobject.get("result").getAsString();
		return result;
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost("http://www.golden5.org/limesurvey_3/index.php/admin/remotecontrol");
		post.setHeader("Content-type", "application/json");
		post.setEntity(new StringEntity(
				"{\"method\": \"get_session_key\", \"params\": [\"golden5\", \"mano;5;dedos\" ], \"id\": 1}"));
		try {
			HttpResponse response = client.execute(post);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				String sessionKey = parse(EntityUtils.toString(entity));
				System.out.println(sessionKey);
				post.setEntity(new StringEntity("{\"method\": \"list_surveys\", \"params\": [ \"" + sessionKey
						+ "\", \"golden5\" ], \"id\": 1}"));
				response = client.execute(post);
				if (response.getStatusLine().getStatusCode() == 200) {
					entity = response.getEntity();
					System.out.println(EntityUtils.toString(entity));
					json = EntityUtils.toString(entity);
					CoreSearch p = parse2Core(json);
					System.out.println(p);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
	public static CoreSearch parse2Core(String json) {
		Gson g = new Gson();
		CoreSearch p = g.fromJson(json, CoreSearch.class);
		return p;
	}
}
