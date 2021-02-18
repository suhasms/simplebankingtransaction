package com.salesken.interview.utils;

import com.google.common.net.MediaType;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;

public class RequestHelper {

	public static String sendPostRequest(final String url, final String body, final Map<String, String> headers,
			final MediaType mediaType) throws IOException {

		final CloseableHttpClient client = HttpClients.createDefault();
		final HttpPost httpPost = new HttpPost(url);
		final StringEntity entity = new StringEntity(body);
		httpPost.setEntity(entity);
		for (final Map.Entry<String, String> entry : headers.entrySet()) {
			httpPost.setHeader(entry.getKey(), entry.getValue());
		}
		httpPost.setHeader("Content-type", mediaType.toString());

		final CloseableHttpResponse response = client.execute(httpPost);
		final String responseBody = EntityUtils.toString(response.getEntity(), "UTF-8");
		client.close();
		response.close();
		return responseBody;
	}

	public static String sendGetRequest(final String url) throws IOException {
		final HttpGet httpGet = new HttpGet(url);
		final CloseableHttpClient client = HttpClients.createDefault();
		final CloseableHttpResponse response = client.execute(httpGet);
		final String responseBody = EntityUtils.toString(response.getEntity(), "UTF-8");
		client.close();
		response.close();
		return responseBody;
	}

}
