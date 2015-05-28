package curso.android;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class FicherosActivity extends Activity {

  EditText txtResultado = null;

  @Override
  public void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setContentView(R.layout.main);

    final Button leerRaw = (Button) this.findViewById(R.id.LeerRaw);
    final Button escribirFich = (Button) this.findViewById(R.id.EscribirFich);
    final Button leerFichero = (Button) this.findViewById(R.id.LeerFichero);
    final Button ecribirSD = (Button) this.findViewById(R.id.EscribirSD);
    final Button leerSD = (Button) this.findViewById(R.id.LeerSD);
    this.txtResultado = (EditText) this.findViewById(R.id.Resultado);

    leerRaw.setOnClickListener(new OnClickListener() {

      @SuppressWarnings("nls")
      @Override
      public void onClick(@SuppressWarnings("unused") final View view) {

        BufferedReader fichero = null;

        try {

          // abrimos el fichero para lectura
          fichero = new BufferedReader(new InputStreamReader(
              FicherosActivity.this.getResources().openRawResource(
                  R.raw.prueba_raw)));

          // leemos el fichero linea a linea
          final StringBuilder texto = new StringBuilder();
          String linea = fichero.readLine();
          while (linea != null) {
            texto.append(linea);
            linea = fichero.readLine();
          }

          // mostramos el texto por pantalla
          FicherosActivity.this.txtResultado.setText(texto);

        } catch (final Exception e) {
          Log.e("Ficheros", "Error al leer fichero desde recurso raw", e);
        } finally {
          try {
            if (fichero != null) {
              fichero.close();
            }
          } catch (final Exception e) {
            Log.e("Ficheros", "Error cerrando el fichero desde recurso raw");
          }
        }
      }
    });

    escribirFich.setOnClickListener(new OnClickListener() {

      @SuppressWarnings("nls")
      @Override
      public void onClick(@SuppressWarnings("unused") final View view) {

        OutputStreamWriter fichero = null;

        try {

          // abrimos el fichero para escritura
          fichero = new OutputStreamWriter(FicherosActivity.this
              .openFileOutput("prueba_int.txt", Context.MODE_PRIVATE));

          // escribimos en el fichero
          fichero.write("Texto de prueba.");

          // mostramos un mensaje por pantalla
          FicherosActivity.this.txtResultado.setText("Fichero creado!");

        } catch (final Exception e) {
          Log.e("Ficheros", "Error al escribir fichero en memoria interna", e);
        } finally {
          try {
            if (fichero != null) {
              fichero.close();
            }
          } catch (final Exception e) {
            Log.e("Ficheros", "Error cerrando el fichero en memoria interna");
          }
        }
      }
    });

    leerFichero.setOnClickListener(new OnClickListener() {

      @SuppressWarnings("nls")
      @Override
      public void onClick(@SuppressWarnings("unused") final View view) {

        BufferedReader fichero = null;

        try {

          // abrimos el fichero para lectura
          fichero = new BufferedReader(new InputStreamReader(
              FicherosActivity.this.openFileInput("prueba_int.txt")));

          // leemos el fichero linea a linea
          final StringBuilder texto = new StringBuilder();
          String linea = fichero.readLine();
          while (linea != null) {
            texto.append(linea);
            linea = fichero.readLine();
          }

          // mostramos el texto por pantalla
          FicherosActivity.this.txtResultado.setText(texto);

        } catch (final Exception e) {
          Log.e("Ficheros", "Error al leer fichero desde memoria interna", e);
        } finally {
          try {
            if (fichero != null) {
              fichero.close();
            }
          } catch (final Exception e) {
            Log
                .e("Ficheros",
                    "Error cerrando el fichero desde memoria interna");
          }
        }
      }
    });

    ecribirSD.setOnClickListener(new OnClickListener() {

      @SuppressWarnings("nls")
      @Override
      public void onClick(@SuppressWarnings("unused") final View view) {

        // Vemos el estado de la memoria SD
        final String estado = Environment.getExternalStorageState();

        // Si la memoria externa está disponible y se puede escribir
        if (Environment.MEDIA_MOUNTED.equals(estado)) {

          OutputStreamWriter fichero = null;

          try {

            // obtenemos el fichero
            final File rutaSd = Environment.getExternalStorageDirectory();
            final File file = new File(rutaSd.getAbsolutePath(),
                "prueba_sd.txt");

            // abrimos el fichero para escritura
            fichero = new OutputStreamWriter(new FileOutputStream(file));

            // escribimos en el fichero
            fichero.write("Texto de prueba para el fichero guardado en la SD.");

            FicherosActivity.this.txtResultado.setText("Fichero SD creado!");

          } catch (final Exception e) {
            Log.e("Ficheros", "Error al escribir fichero a tarjeta SD", e);
          } finally {
            try {
              if (fichero != null) {
                fichero.close();
              }
            } catch (final Exception e) {
              Log.e("Ficheros", "Error cerrando el fichero a tarjeta SD");
            }
          }
        }
      }
    });

    leerSD.setOnClickListener(new OnClickListener() {

      @SuppressWarnings("nls")
      @Override
      public void onClick(@SuppressWarnings("unused") final View view) {

        BufferedReader fichero = null;

        try {

          // obtenemos el fichero
          final File rutaSd = Environment.getExternalStorageDirectory();
          final File file = new File(rutaSd.getAbsolutePath(), "prueba_sd.txt");

          // abrimos el fichero para lectura
          fichero = new BufferedReader(new InputStreamReader(
              new FileInputStream(file)));

          // leemos el fichero linea a linea
          final StringBuilder texto = new StringBuilder();
          String linea = fichero.readLine();
          while (linea != null) {
            texto.append(linea);
            linea = fichero.readLine();
          }

          // mostramos el texto por pantalla
          FicherosActivity.this.txtResultado.setText(texto);

        } catch (final Exception e) {
          Log.e("Ficheros", "Error al leer fichero desde memoria SD", e);
        } finally {
          try {
            if (fichero != null) {
              fichero.close();
            }
          } catch (final Exception e) {
            Log.e("Ficheros", "Error cerrando el fichero desde memoria SD");
          }
        }
      }
    });
  }
}