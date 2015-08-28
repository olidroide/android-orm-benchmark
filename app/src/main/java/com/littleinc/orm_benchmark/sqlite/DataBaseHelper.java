package com.littleinc.orm_benchmark.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

  // DB CONFIG
  private static int DB_VERSION = 1;

  private static String DB_NAME_PREFIX = "sqlite_db";

  public DataBaseHelper(Context context, boolean isInMemory, String suffix) {
    super(context, (isInMemory ? null : (DB_NAME_PREFIX + suffix)), null, DB_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
  }
}
