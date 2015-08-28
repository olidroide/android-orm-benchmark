package com.littleinc.orm_benchmark.dbflow;

import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.config.BaseDatabaseDefinition;

@Database(name = DatabaseDefinition.NAME, version = DatabaseDefinition.VERSION)
public class DatabaseDefinition{

  public static final int VERSION = 1;
  public static final String NAME = "dbflow_db";

  //@Override
  //public String getDatabaseName() {
  //  return NAME;
  //}
  //
  //@Override
  //public int getDatabaseVersion() {
  //  return VERSION;
  //}
  //
  //@Override
  //public boolean areConsistencyChecksEnabled() {
  //  return true;
  //}
  //
  //@Override
  //public boolean isForeignKeysSupported() {
  //  return true;
  //}
  //
  //@Override
  //public boolean backupEnabled() {
  //  return false;
  //}
}
