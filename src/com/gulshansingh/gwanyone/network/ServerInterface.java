package com.gulshansingh.gwanyone.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

import com.gulshansingh.gwanyone.activity.EventDetailsActivity.AsyncTaskCompleteListener;

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

	public void createEvent() {

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
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
						1);
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

	public void getPeopleGoing(
			AsyncTaskCompleteListener<ArrayList<String>> listener) {
		new GetPeopleTask(listener).execute(Answer.YES);
	}

	public void getPeopleNotGoing(
			AsyncTaskCompleteListener<ArrayList<String>> listener) {
		new GetPeopleTask(listener).execute(Answer.NO);
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
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
						2);
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

		private AsyncTaskCompleteListener<ArrayList<String>> listener;

		public GetPeopleTask(AsyncTaskCompleteListener<ArrayList<String>> l) {
			listener = l;
		}

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

		protected void onPostExecute(ArrayList<String> list) {
			listener.onComplete(list);
		}
	}
}
