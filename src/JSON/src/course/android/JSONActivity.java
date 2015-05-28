package course.android;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class JSONActivity extends Activity implements OnClickListener {

  private ListView tweets;

  private EditText twitterUser;

  /** Called when the activity is first created. */
  @Override
  public void onCreate(final Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    this.setContentView(R.layout.main);

    this.tweets = (ListView) this.findViewById(R.id.tweets);
    this.twitterUser = (EditText) this.findViewById(R.id.twitter_user);

    final Button changeUser = (Button) this
        .findViewById(R.id.view_twitter_user_timeline);
    changeUser.setOnClickListener(this);
  }

  @Override
  public void onClick(final View view) {

    final String twitterUserName = this.twitterUser.getText().toString();

    final List<String> userTweets = this.getUserTweets(twitterUserName);

    final ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
        android.R.layout.simple_list_item_1, userTweets);

    this.tweets.setAdapter(adaptador);
  }

  /**
   * @param twitterUserName
   * @return a list of tweets
   */
  private List<String> getUserTweets(final String twitterUserName) {

    final List<String> userTweets = new ArrayList<String>();

    try {

      final JSONArray jsonArray = this.getUserTimeline(twitterUserName);

      for (int i = 0; i < jsonArray.length(); i++) {
        final JSONObject jsonObject = jsonArray.getJSONObject(i);
        userTweets.add(jsonObject.getString("text"));
      }

    } catch (final Exception e) {

      throw new RuntimeException(e);
    }

    return userTweets;
  }

  /**
   * @param twitterUserName
   * @return a JSONArray with the twitter user timeline
   */
  private JSONArray getUserTimeline(final String twitterUserName)
      throws Exception {

    final StringBuilder builder = new StringBuilder();

    final HttpResponse response = new DefaultHttpClient().execute(new HttpGet(
        "http://twitter.com/statuses/user_timeline/" + twitterUserName
            + ".json"));

    final int statusCode = response.getStatusLine().getStatusCode();

    if (statusCode == 200) {

      final BufferedReader reader = new BufferedReader(new InputStreamReader(
          response.getEntity().getContent()));
      String line;
      while ((line = reader.readLine()) != null) {
        builder.append(line);
      }
    }

    return new JSONArray(builder.toString());
  }
}