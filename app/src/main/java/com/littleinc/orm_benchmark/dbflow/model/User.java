package com.littleinc.orm_benchmark.dbflow.model;

import com.littleinc.orm_benchmark.dbflow.DatabaseDefinition;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.ForeignKeyReference;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.container.ForeignKeyContainer;

@Table(databaseName = DatabaseDefinition.NAME)
public class User extends BaseModel {
  @Column
  @PrimaryKey(autoincrement = true)
  Long id;

  @Column
  String last_name;

  @Column
  String first_name;

  @Column
  @ForeignKey(
      references = {
          @ForeignKeyReference(columnName = "message_id",
              columnType = Long.class,
              foreignColumnName = "id")
      },
      saveForeignKeyModel = false)
  ForeignKeyContainer<Message> messagesRead;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getLast_name() {
    return last_name;
  }

  public void setLastName(String last_name) {
    this.last_name = last_name;
  }

  public String getFirst_name() {
    return first_name;
  }

  public void setFirstName(String first_name) {
    this.first_name = first_name;
  }

  public ForeignKeyContainer<Message> getMessagesRead() {
    return messagesRead;
  }

  public void setMessagesRead(ForeignKeyContainer<Message> messagesRead) {
    this.messagesRead = messagesRead;
  }
}
