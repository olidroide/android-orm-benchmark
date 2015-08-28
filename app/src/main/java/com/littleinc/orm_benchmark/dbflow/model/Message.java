package com.littleinc.orm_benchmark.dbflow.model;

import com.littleinc.orm_benchmark.dbflow.DatabaseDefinition;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.builder.Condition;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.BaseModel;
import java.util.List;

@ModelContainer
@Table(databaseName = DatabaseDefinition.NAME)
public class Message extends BaseModel {
  @Column
  @PrimaryKey(autoincrement = true)
  long id;

  @Column
  long clientId;

  @Column
  long commandId;

  @Column
  double sortedBy;

  @Column
  int createdAt;

  @Column
  String content;

  @Column
  long senderId;

  @Column
  long channelId;


  public List<User> readers;

  @OneToMany(methods = {OneToMany.Method.SAVE, OneToMany.Method.DELETE}, variableName = "readers")
  public List<User> getMyReaders() {
    if(readers == null) {
      readers = new Select()
          .from(User.class)
          .where(Condition.column(User$Table.MESSAGESREAD_MESSAGE_ID).is(id))
          .queryList();
    }
    return readers;
  }

  public void setClientId(long clientId) {
    this.clientId = clientId;
  }

  public void setCommandId(long commandId) {
    this.commandId = commandId;
  }

  public void setSortedBy(double sortedBy) {
    this.sortedBy = sortedBy;
  }

  public void setCreatedAt(int createdAt) {
    this.createdAt = createdAt;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public void setSenderId(long senderId) {
    this.senderId = senderId;
  }

  public void setChannelId(long channelId) {
    this.channelId = channelId;
  }
}
