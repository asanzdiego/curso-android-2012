package curso.android;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class CheckBoxActivity extends Activity {

  @Override
  public void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setContentView(R.layout.main);

    final CheckBox checkBox = (CheckBox) this.findViewById(R.id.Marcame);

    checkBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
      @SuppressWarnings("nls")
      public void onCheckedChanged(
          @SuppressWarnings("unused") final CompoundButton buttonView,
          final boolean isChecked) {
        if (isChecked) {
          checkBox.setText("Checkbox marcado!");
        } else {
          checkBox.setText("Checkbox desmarcado!");
        }
      }
    });
  }
}