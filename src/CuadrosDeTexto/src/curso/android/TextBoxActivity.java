package curso.android;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.BufferType;

public class TextBoxActivity extends Activity {

  @Override
  public void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setContentView(R.layout.main);

    // Localizamos los controles
    final ImageView icono = (ImageView) this.findViewById(R.id.Icon);
    final EditText textBox = (EditText) this.findViewById(R.id.TextBox);
    final Button boldButton = (Button) this.findViewById(R.id.BoldButton);
    final Button helpButton1 = (Button) this.findViewById(R.id.HelpButton1);
    final Button helpButton2 = (Button) this.findViewById(R.id.HelpButton2);
    final TextView help = (TextView) this.findViewById(R.id.Help);

    // Añadimos los Listener
    this.addIconListener(icono);
    this.addBoldButtonListener(textBox, boldButton, help);
    this.addHelpButton1Listener(textBox, helpButton1, help);
    this.addHelpButton2Listener(textBox, helpButton2, help);
  }

  private void addIconListener(final ImageView icono) {
    icono.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(@SuppressWarnings("unused") final View view) {
        icono.setImageResource(R.drawable.ok);
      }
    });
    icono.setOnLongClickListener(new View.OnLongClickListener() {

      @Override
      public boolean onLongClick(@SuppressWarnings("unused") final View view) {
        icono.setImageResource(R.drawable.ko);
        return false;
      }
    });
  }

  private void addBoldButtonListener(final EditText textBox,
      final Button boldButton, final TextView help) {
    boldButton.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(@SuppressWarnings("unused") final View view) {

        // Ponemos en negrita el texto seleccionado
        final Spannable texto = textBox.getText();
        final int start = textBox.getSelectionStart();
        final int end = textBox.getSelectionEnd();
        final int ini;
        final int fin;
        if (start > end) {
          ini = end;
          fin = start;
        } else {
          ini = start;
          fin = end;
        }
        texto.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), ini, fin,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Muestra el texto CON etiquetas de formato HTML
        help.setText(Html.toHtml(textBox.getText()));
      }
    });
  }

  private void addHelpButton1Listener(final EditText textBox,
      final Button helpButton1, final TextView help) {
    helpButton1.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(@SuppressWarnings("unused") final View view) {

        // Creamos un texto con parte en negrita
        final Editable texto = Editable.Factory.getInstance().newEditable(
            "Esto es un simulacro."); //$NON-NLS-1$
        texto.setSpan(new StyleSpan(Typeface.BOLD), 11, 20,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textBox.setText(texto);

        // Muestra el texto CON etiquetas de formato HTML
        help.setText(Html.toHtml(textBox.getText()));
      }
    });
  }

  private void addHelpButton2Listener(final EditText textBox,
      final Button helpButton2, final TextView help) {
    helpButton2.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(@SuppressWarnings("unused") final View view) {

        // Asigna texto con formato HTML
        textBox.setText(Html.fromHtml("Otro <b>texto</b> de ejemplo."), //$NON-NLS-1$
            BufferType.SPANNABLE);

        // Muestra el texto CON etiquetas de formato HTML
        help.setText(Html.toHtml(textBox.getText()));
      }
    });
  }
}