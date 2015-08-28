package com.littleinc.orm_benchmark.sqlite;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import com.raizlabs.android.dbflow.structure.Model;

public class User implements Model {

  public static final String TABLE_NAME = "user";

  public static final String LAST_NAME_COLUMN = "last_name";

  public static final String FIRST_NAME_COLUMN = "first_name";

  private long mId;

  private String mLastName;

  private String mFirstName;

  public static void createTable(DataBaseHelper helper) {
    helper.getWritableDatabase()
        .execSQL(new StringBuilder("CREATE TABLE '").append(TABLE_NAME)
                .append("' ('")
                .append(BaseColumns._ID)
                .append("' INTEGER PRIMARY KEY AUTOINCREMENT, '")
                .append(LAST_NAME_COLUMN)
                .append("' TEXT, '")
                .append(FIRST_NAME_COLUMN)
                .append("' TEXT);")
                .toString());
  }

  public static void dropTable(DataBaseHelper helper) {
    SQLiteDatabase db = helper.getWritableDatabase();

    db.execSQL(new StringBuilder("DROP TABLE '").append(TABLE_NAME).append("';").toString());
  }

  public long getId() {
    return mId;
  }

  public void setId(long id) {
    this.mId = id;
  }

  public String getLastName() {
    return mLastName;
  }

  public void setLastName(String lastName) {
    this.mLastName = lastName;
  }

  public String getFirstName() {
    return mFirstName;
  }

  public void setFirstName(String firstName) {
    this.mFirstName = firstName;
  }

  public ContentValues prepareForInsert(ContentValues contentValues) {
    contentValues.put(LAST_NAME_COLUMN, mLastName);
    contentValues.put(FIRST_NAME_COLUMN, mFirstName);
    return contentValues;
  }

  @Override
  public void save() {

  }

  @Override
  public void delete() {

  }

  @Override
  public void update() {

  }

  @Override
  public void insert() {

  }

  @Override
  public boolean exists() {
    return false;
  }


}
