package com.gulshansingh.gwanyone.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class ServerInterface {

	private enum Answer {
		YES, NO
	}

	private static final String TAG = ServerInterface.class.getName();

	private static final String URL = "http://www.gulshansingh.com/dev/gwanyone/handler.php";

	private static final String REGISTER_URL = "http://www.gulshansingh.com/dev/gwanyone/register.php";

	public void registerUser(String username) {
		new RegisterUserTask().execute(username);
	}

	private class RegisterUserTask extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... args) {
			if (args.length != 1) {
				throw new IllegalArgumentException();
			}
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(REGISTER_URL);

			try {
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
				nameValuePairs.add(new BasicNameValuePair("user", args[0]));

				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
						nameValuePairs);
				httpPost.addHeader(entity.getContentType());
				httpPost.setEntity(entity);

				HttpResponse response = httpClient.execute(httpPost);

				Log.d(TAG, "Server response is "
						+ response.getStatusLine().getStatusCode());

			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	public ArrayList<String> getPeopleGoing() {
		AsyncTask<Answer, Void, ArrayList<String>> task = new GetPeopleTask()
				.execute(Answer.YES);

		ArrayList<String> result = null;
		try {
			result = task.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		return result;
	}

	public ArrayList<String> getPeopleNotGoing() {
		AsyncTask<Answer, Void, ArrayList<String>> task = new GetPeopleTask()
				.execute(Answer.NO);

		ArrayList<String> result = null;
		try {
			result = task.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		return result;
	}

	public void answerYes(String user) {
		new SendAnswerTask().execute("yes", user);
	}

	public void answerNo(String user) {
		new SendAnswerTask().execute("no", user);
	}

	private class SendAnswerTask extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... args) {
			if (args.length != 2) {
				throw new IllegalArgumentException();
			}
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(URL);

			try {
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
				nameValuePairs.add(new BasicNameValuePair("user", args[1]));
				nameValuePairs.add(new BasicNameValuePair("answer", args[0]));

				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
						nameValuePairs);
				httpPost.addHeader(entity.getContentType());
				httpPost.setEntity(entity);

				HttpResponse response = httpClient.execute(httpPost);

				Log.d(TAG, "Server response is "
						+ response.getStatusLine().getStatusCode());

			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	private class GetPeopleTask extends
			AsyncTask<Answer, Void, ArrayList<String>> {
		@Override
		protected ArrayList<String> doInBackground(Answer... args) {
			if (args.length != 1) {
				throw new IllegalArgumentException();
			}

			String url = URL + "?" + "answer="
					+ args[0].toString().toLowerCase(Locale.US);

			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);

			ArrayList<String> result = new ArrayList<String>();
			try {
				HttpResponse response = httpClient.execute(httpGet);
				BufferedReader br = new BufferedReader(new InputStreamReader(
						response.getEntity().getContent()));

				String json = br.readLine();
				JSONArray jsonArray = new JSONArray(json);

				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject object = jsonArray.getJSONObject(i);
					result.add(object.getString("user"));
				}

				Log.d(TAG, "Server response is "
						+ response.getStatusLine().getStatusCode());

			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return result;
		}
	}
}
