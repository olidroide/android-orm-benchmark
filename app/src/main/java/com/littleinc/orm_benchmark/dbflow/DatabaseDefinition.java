package com.littleinc.orm_benchmark.dbflow;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = DatabaseDefinition.NAME, version = DatabaseDefinition.VERSION)
public class DatabaseDefinition {

  public static final int VERSION = 1;
  public static final String NAME = "dbflow_db";
}
