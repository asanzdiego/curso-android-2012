package curso.android;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class ListadoNotasActivity extends ListActivity {

  private NotasDbHelper dbHelper;
  private Cursor cursorNotas;

  private static final int ACTIVITY_CREATE = 0;
  private static final int ACTIVITY_EDIT = 1;

  private static final int INSERT_ID = Menu.FIRST;
  private static final int DELETE_ID = Menu.FIRST + 1;

  @Override
  public void onCreate(final Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    this.setContentView(R.layout.listar_notas);
    this.dbHelper = new NotasDbHelper(this);
    this.dbHelper.open();
    this.fillData();
    this.registerForContextMenu(this.getListView());
  }

  @Override
  public boolean onCreateOptionsMenu(final Menu menu) {

    final boolean result = super.onCreateOptionsMenu(menu);
    menu.add(0, ListadoNotasActivity.INSERT_ID, 0, R.string.menu_insert);
    return result;
  }

  @Override
  public boolean onOptionsItemSelected(final MenuItem item) {

    switch (item.getItemId()) {
      case INSERT_ID:
        this.createNote();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onCreateContextMenu(final ContextMenu menu, final View view,
      final ContextMenuInfo menuInfo) {

    super.onCreateContextMenu(menu, view, menuInfo);
    menu.add(0, ListadoNotasActivity.DELETE_ID, 0, R.string.menu_delete);
  }

  @Override
  public boolean onContextItemSelected(final MenuItem item) {

    switch (item.getItemId()) {
      case DELETE_ID:
        final AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
            .getMenuInfo();
        this.dbHelper.deleteNote(info.id);
        this.fillData();
        return true;
    }
    return super.onContextItemSelected(item);
  }

  @Override
  protected void onListItemClick(final ListView listView, final View view,
      final int position, final long id) {

    super.onListItemClick(listView, view, position, id);
    final Cursor c = this.cursorNotas;
    c.moveToPosition(position);
    final Intent intent = new Intent(this, EditarNotaActivity.class);
    final int titleIndex = c.getColumnIndexOrThrow(NotasDbHelper.KEY_TITLE);
    final int bodyIndex = c.getColumnIndexOrThrow(NotasDbHelper.KEY_BODY);
    intent.putExtra(NotasDbHelper.KEY_ROWID, id);
    intent.putExtra(NotasDbHelper.KEY_TITLE, c.getString(titleIndex));
    intent.putExtra(NotasDbHelper.KEY_BODY, c.getString(bodyIndex));
    this.startActivityForResult(intent, ListadoNotasActivity.ACTIVITY_EDIT);
  }

  @Override
  protected void onActivityResult(final int requestCode, final int resultCode,
      final Intent intent) {

    super.onActivityResult(requestCode, resultCode, intent);

    if (intent != null) {

      final Bundle extras = intent.getExtras();

      final Long rowId = new Long(extras.getLong(NotasDbHelper.KEY_ROWID));
      final String title = extras.getString(NotasDbHelper.KEY_TITLE);
      final String body = extras.getString(NotasDbHelper.KEY_BODY);

      switch (requestCode) {
        case ACTIVITY_CREATE:
          this.dbHelper.createNote(title, body);
          break;
        case ACTIVITY_EDIT:
          this.dbHelper.updateNote(rowId.longValue(), title, body);
          break;
      }
    }
    this.fillData();
  }

  private void fillData() {

    // Recupera todas las notas de la DB y las guarda en el cursor
    this.cursorNotas = this.dbHelper.fetchAllNotes();
    this.startManagingCursor(this.cursorNotas);

    // Array con los campos que queremos mostrar en la lista
    final String[] from = new String[] { NotasDbHelper.KEY_TITLE };

    // array con las variables asociadas para esos campos
    final int[] to = new int[] { R.id.text1 };

    final SimpleCursorAdapter notes = new SimpleCursorAdapter(this,
        R.layout.fila_nota, this.cursorNotas, from, to);
    this.setListAdapter(notes);
  }

  private void createNote() {
    final Intent i = new Intent(this, EditarNotaActivity.class);
    this.startActivityForResult(i, ListadoNotasActivity.ACTIVITY_CREATE);
  }
}
