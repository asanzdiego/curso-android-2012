/*
 * Copyright (C) 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package curso.android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Este adaptador nos permitirá acceder a la base de datos y modificar las notas
 * que creemos.
 */
public class NotasDbHelper {

  public static final String KEY_TITLE = "title"; //$NON-NLS-1$
  public static final String KEY_BODY = "body"; //$NON-NLS-1$
  public static final String KEY_ROWID = "_id"; //$NON-NLS-1$

  private static final String[] COLUMNS = new String[] {
      NotasDbHelper.KEY_ROWID, NotasDbHelper.KEY_TITLE,
      NotasDbHelper.KEY_BODY };

  private static final String TAG = "NotasDbAdapter"; //$NON-NLS-1$
  private DatabaseHelper databaseHelper;
  private SQLiteDatabase sqliteDatabase;

  /**
   * Sentencia SQL para crear la base de datos
   */
  private static final String DATABASE_CREATE = "create table notes (" //$NON-NLS-1$
      + "_id integer primary key autoincrement, " //$NON-NLS-1$
      + "title text not null, " //$NON-NLS-1$
      + "body text not null);"; //$NON-NLS-1$

  private static final String DATABASE_NAME = "data";//$NON-NLS-1$
  private static final String TABLE_NAME = "notes";//$NON-NLS-1$
  private static final int DATABASE_VERSION = 2;

  private final Context context;

  private static class DatabaseHelper extends SQLiteOpenHelper {

    DatabaseHelper(final Context context) {
      super(context, NotasDbHelper.DATABASE_NAME, null,
          NotasDbHelper.DATABASE_VERSION);
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
      db.execSQL(NotasDbHelper.DATABASE_CREATE);
    }

    @SuppressWarnings("nls")
    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion,
        final int newVersion) {

      Log.w(NotasDbHelper.TAG, "Upgrading database from version " + oldVersion
          + " to " + newVersion + ", which will destroy all old data");

      db.execSQL("DROP TABLE IF EXISTS notes");
      this.onCreate(db);
    }
  }

  /**
   * Constructor - pasa el contexto para poder abrir o crear la DB
   * 
   * @param context
   *          Contexto en el que estamos trabajando
   */
  public NotasDbHelper(final Context context) {
    this.context = context;
  }

  /**
   * Abre la base de datos de notas. Si no puede abrirla, la crea. Si no se
   * puede lanza una excepción
   * 
   * @return this (self reference, allowing this to be chained in an
   *         initialization call)
   * @throws SQLException
   *           if the database could be neither opened or created
   */
  public NotasDbHelper open() throws SQLException {

    this.databaseHelper = new DatabaseHelper(this.context);
    this.sqliteDatabase = this.databaseHelper.getWritableDatabase();

    return this;
  }

  public void close() {

    this.databaseHelper.close();
  }

  /**
   * Inserta una nueva nota con el título y el texto dados. Si se crea
   * correctamente devuelve el rowId, en caso contrario, devuelve -1 para
   * indicar que ha habido un error..
   * 
   * @param title
   *          titulo de la nota
   * @param body
   *          texto de la nota
   * @return rowId o -1 si ha fallado
   */
  public long createNote(final String title, final String body) {

    final ContentValues initialValues = new ContentValues();

    initialValues.put(NotasDbHelper.KEY_TITLE, title);
    initialValues.put(NotasDbHelper.KEY_BODY, body);

    final long rowId = this.sqliteDatabase.insert(NotasDbHelper.TABLE_NAME,
        null, initialValues);

    return rowId;
  }

  /**
   * Borra la nota con el rowId dado
   * 
   * @param rowId
   *          id de la nota a borrar
   * @return true si se ha borrado, false en caso contrario
   */
  public boolean deleteNote(final long rowId) {

    return this.sqliteDatabase.delete(NotasDbHelper.TABLE_NAME,
        NotasDbHelper.KEY_ROWID + "=" + rowId, null) > 0; //$NON-NLS-1$
  }

  /**
   * Devuelve un Cursor apuntando a la lista de todas las notas
   * 
   * @return Cursor de todas las notas
   */
  public Cursor fetchAllNotes() {

    return this.sqliteDatabase.query(NotasDbHelper.TABLE_NAME,
        NotasDbHelper.COLUMNS, null, null, null, null, null);
  }

  /**
   * Devuelve un Cursor apuntando a la nota con el rowId dado
   * 
   * @param rowId
   *          id de la nota
   * @return Cursor posicionado en la nota que queremos
   * @throws SQLException
   *           si no se ha podido encontrar la nota
   */
  public Cursor fetchNote(final long rowId) throws SQLException {

    final String where = NotasDbHelper.KEY_ROWID + "=" + rowId; //$NON-NLS-1$

    final Cursor cursor = this.sqliteDatabase.query(true,
        NotasDbHelper.TABLE_NAME, NotasDbHelper.COLUMNS, where, null, null,
        null, null, null);

    if (cursor != null) {
      cursor.moveToFirst();
    }

    return cursor;

  }

  /**
   * Actualiza la nota con los detalles dados.
   * 
   * @param rowId
   *          id de la nota a actualizar
   * @param title
   *          valor nuevo del titulo
   * @param body
   *          valor nuevo del texto
   * @return true si la nota se ha editado correctamente, false en caso
   *         contrario
   */
  public boolean updateNote(final long rowId, final String title,
      final String body) {

    final String where = NotasDbHelper.KEY_ROWID + "=" + rowId; //$NON-NLS-1$

    final ContentValues args = new ContentValues();
    args.put(NotasDbHelper.KEY_TITLE, title);
    args.put(NotasDbHelper.KEY_BODY, body);

    return this.sqliteDatabase.update(NotasDbHelper.TABLE_NAME, args, where,
        null) > 0;
  }
}
