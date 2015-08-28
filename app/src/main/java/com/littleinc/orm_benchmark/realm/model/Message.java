package com.littleinc.orm_benchmark.realm.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Message extends RealmObject {
  private long clientId;
  private long commandId;
  private double sortedBy;
  private int createdAt;
  private String content;
  private long senderId;
  private long channelId;
  private RealmList<User> readers;

  public long getClientId() {
    return clientId;
  }

  public void setClientId(long clientId) {
    this.clientId = clientId;
  }

  public long getCommandId() {
    return commandId;
  }

  public void setCommandId(long commandId) {
    this.commandId = commandId;
  }

  public double getSortedBy() {
    return sortedBy;
  }

  public void setSortedBy(double sortedBy) {
    this.sortedBy = sortedBy;
  }

  public int getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(int createdAt) {
    this.createdAt = createdAt;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public long getSenderId() {
    return senderId;
  }

  public void setSenderId(long senderId) {
    this.senderId = senderId;
  }

  public long getChannelId() {
    return channelId;
  }

  public void setChannelId(long channelId) {
    this.channelId = channelId;
  }

  public RealmList<User> getReaders() {
    return readers;
  }

  public void setReaders(RealmList<User> readers) {
    this.readers = readers;
  }
}
