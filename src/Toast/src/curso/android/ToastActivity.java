package curso.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ToastActivity extends Activity {

  private Button btnDefecto = null;
  private Button btnGravity = null;
  private Button btnLayout = null;

  @Override
  public void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setContentView(R.layout.main);

    this.btnDefecto = (Button) this.findViewById(R.id.btnPorDefecto);
    this.btnGravity = (Button) this.findViewById(R.id.btnGravity);
    this.btnLayout = (Button) this.findViewById(R.id.btnLayout);

    this.btnDefecto.setOnClickListener(new OnClickListener() {
      @SuppressWarnings("nls")
      @Override
      public void onClick(@SuppressWarnings("unused") final View view) {
        final Toast toast1 = Toast.makeText(ToastActivity.this.getApplicationContext(),
            "Toast por defecto", Toast.LENGTH_SHORT);

        toast1.show();
      }
    });

    this.btnGravity.setOnClickListener(new OnClickListener() {
      @SuppressWarnings("nls")
      @Override
      public void onClick(@SuppressWarnings("unused") final View view) {
        final Toast toast2 = Toast.makeText(ToastActivity.this.getApplicationContext(),
            "Toast con gravity", Toast.LENGTH_SHORT);

        toast2.setGravity(Gravity.CENTER | Gravity.LEFT, 0, 0);
        toast2.show();
      }
    });

    this.btnLayout.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(@SuppressWarnings("unused") final View view) {
        final Toast toast3 = new Toast(ToastActivity.this.getApplicationContext());

        final LayoutInflater inflater = ToastActivity.this.getLayoutInflater();
        final View layout = inflater.inflate(R.layout.toast_layout,
            (ViewGroup) ToastActivity.this.findViewById(R.id.lytLayout));

        final TextView txtMsg = (TextView) layout.findViewById(R.id.txtMensaje);
        txtMsg.setText("Toast Personalizado"); //$NON-NLS-1$

        toast3.setDuration(Toast.LENGTH_SHORT);
        toast3.setView(layout);
        toast3.show();
      }
    });
  }
}