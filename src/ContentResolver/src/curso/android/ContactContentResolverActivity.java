package curso.android;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Este ejercicio demuestra el uso de un Content Provider. Muestra los contactos
 * que tenemos en el teléfono en una ListView. En el manifiesto la aplicación
 * necesitará permiso para leer los contactos
 */
public class ContactContentResolverActivity extends Activity {

  private ListView listView;
  private TextView textView;

  /**
   * Llama al setContentView el cual infla el recurso R.layou.main y añade la
   * jerarquía de vistas a la actividad.
   * 
   * A continuación se obtienen los datos de los contactos, los cuales serán
   * adaptados antes de pasarselos a la vista.
   * 
   * En caso de que no existan contactos se mostrará una mensaje indicando esta
   * situación en el TextView.
   */
  @Override
  public void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setContentView(R.layout.main);

    this.listView = (ListView) this.findViewById(R.id.listView);
    this.textView = (TextView) this.findViewById(R.id.textView);

    final List<String> peopleList = this.getPeopleContent();

    if (peopleList.size() == 0) {
      this.textView.setVisibility(View.VISIBLE);
    }

    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
        android.R.layout.simple_list_item_1, peopleList);
    this.listView.setAdapter(adapter);
  }

  /**
   * Devuelve una lista con cada contacto que exista en el teléfono.
   * 
   * Para obtener los contactos realiza una consulta al ContentResolver
   * pasándole la CONTENT_URI referente a los contactos.
   * 
   * Para extraer datos del cursor se obtienen los índices de las columnas en
   * las que se encuentran los valores del nombre y el teléfono
   */

  @SuppressWarnings("nls")
  private List<String> getPeopleContent() {
    final List<String> peopleList = new ArrayList<String>();

    final String select = "((" + Contacts.DISPLAY_NAME + " NOTNULL) AND ("
        + Contacts.HAS_PHONE_NUMBER + "=1) AND (" + Contacts.DISPLAY_NAME
        + " != '' ))";

    final ContentResolver contentResolver = this.getContentResolver();
    final Cursor cursor = contentResolver.query(Contacts.CONTENT_URI, null,
        select, null, null);
    this.startManagingCursor(cursor);

    final int nameIndex = cursor.getColumnIndexOrThrow(Contacts.DISPLAY_NAME);
    final int contactIdIndex = cursor.getColumnIndex(BaseColumns._ID);

    if (cursor.moveToFirst()) {
      do {

        final String name = cursor.getString(nameIndex);
        final String contactId = cursor.getString(contactIdIndex);

        final String numbers = this.getAllPhoneNumbers(contentResolver,
            contactId);

        peopleList.add(name + numbers);
      } while (cursor.moveToNext());
    }
    cursor.close();

    return peopleList;
  }

  @SuppressWarnings("nls")
  private String getAllPhoneNumbers(final ContentResolver contentResolver,
      final String contactId) {

    final Cursor phones = contentResolver.query(Phone.CONTENT_URI, null,
        Phone.CONTACT_ID + " = " + contactId, null, null);
    String numbers = "";
    while (phones.moveToNext()) {
      numbers = phones.getString(phones.getColumnIndex(Phone.NUMBER));
      final int type = phones.getInt(phones.getColumnIndex(Phone.TYPE));
      switch (type) {
        case Phone.TYPE_HOME:
          numbers = " HOME=" + numbers;
          break;
        case Phone.TYPE_MOBILE:
          numbers = " MOBILE=" + numbers;
          break;
        case Phone.TYPE_WORK:
          numbers = " WORK=" + numbers;
          break;
      }
    }
    phones.close();

    return numbers;
  }

}