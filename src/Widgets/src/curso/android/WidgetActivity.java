package curso.android;

import android.app.Activity;
import android.os.Bundle;

public class WidgetActivity extends Activity {
  /** Called when the activity is first created. */
  @Override
  public void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setContentView(R.layout.main);
  }
}