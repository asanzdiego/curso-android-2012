/*
 * Copyright (C) 2008 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.savedInstanceState
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package curso.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditarNotaActivity extends Activity {

  EditText textoTittle;
  EditText textoBody;
  Long rowId;

  @Override
  protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Fijamos el layout de esta actividad
    this.setContentView(R.layout.editar_nota);

    // Ponemos el resultado a canceled, por si acaso le dan a 'volver'
    EditarNotaActivity.this.setResult(Activity.RESULT_CANCELED);

    // Objetos que referencian a los campos editables del layout
    this.textoTittle = (EditText) this.findViewById(R.id.title);
    this.textoBody = (EditText) this.findViewById(R.id.body);
    final Button confirmButton = (Button) this.findViewById(R.id.confirm);

    // Si hay argumentos, los cogemos
    this.rowId = null;
    final Bundle extras = this.getIntent().getExtras();
    if (extras != null) {
      final String title = extras.getString(NotasDbHelper.KEY_TITLE);
      final String body = extras.getString(NotasDbHelper.KEY_BODY);
      this.rowId = new Long(extras.getLong(NotasDbHelper.KEY_ROWID));

      // Insertamos los valores actuales de la nota en los campos
      if (title != null) {
        this.textoTittle.setText(title);
      }
      if (body != null) {
        this.textoBody.setText(body);
      }
    }

    // Listener para el botón de confirmar
    confirmButton.setOnClickListener(new View.OnClickListener() {

      public void onClick(@SuppressWarnings("unused") final View view) {
        final Bundle bundle = new Bundle();

        // recuperamos los valores de los campos de texto
        final String title = EditarNotaActivity.this.textoTittle.getText()
            .toString();
        final String body = EditarNotaActivity.this.textoBody.getText()
            .toString();

        // Guardamos los nuevos valores en un Bundle
        bundle.putString(NotasDbHelper.KEY_TITLE, title);
        bundle.putString(NotasDbHelper.KEY_BODY, body);
        if (EditarNotaActivity.this.rowId != null) {
          bundle.putLong(NotasDbHelper.KEY_ROWID, EditarNotaActivity.this.rowId
              .longValue());
        }

        // y los mandamos de vuelta al método que los está esperando
        final Intent mIntent = new Intent();
        mIntent.putExtras(bundle);
        EditarNotaActivity.this.setResult(Activity.RESULT_OK, mIntent);
        EditarNotaActivity.this.finish();
      }

    });
  }
}