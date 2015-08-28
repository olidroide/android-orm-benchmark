package com.littleinc.orm_benchmark.greendao;

import de.greenrobot.dao.DaoException;
import java.util.List;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 

/**
 * Entity mapped to table MESSAGE.
 */
public class Message {

  private Long id;
  private String content;
  private Long client_id;
  private Integer created_at;
  private Double sorted_by;
  private Long command_id;
  private long sender_id;
  private long channel_id;

  /** Used to resolve relations */
  private transient DaoSession daoSession;

  /** Used for active entity operations. */
  private transient MessageDao myDao;

  private List<User> readers;

  public Message() {
  }

  public Message(Long id) {
    this.id = id;
  }

  public Message(Long id, String content, Long client_id, Integer created_at, Double sorted_by,
      Long command_id, long sender_id, long channel_id) {
    this.id = id;
    this.content = content;
    this.client_id = client_id;
    this.created_at = created_at;
    this.sorted_by = sorted_by;
    this.command_id = command_id;
    this.sender_id = sender_id;
    this.channel_id = channel_id;
  }

  /** called by internal mechanisms, do not call yourself. */
  public void __setDaoSession(DaoSession daoSession) {
    this.daoSession = daoSession;
    myDao = daoSession != null ? daoSession.getMessageDao() : null;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Long getClient_id() {
    return client_id;
  }

  public void setClient_id(Long client_id) {
    this.client_id = client_id;
  }

  public Integer getCreated_at() {
    return created_at;
  }

  public void setCreated_at(Integer created_at) {
    this.created_at = created_at;
  }

  public Double getSorted_by() {
    return sorted_by;
  }

  public void setSorted_by(Double sorted_by) {
    this.sorted_by = sorted_by;
  }

  public Long getCommand_id() {
    return command_id;
  }

  public void setCommand_id(Long command_id) {
    this.command_id = command_id;
  }

  public long getSender_id() {
    return sender_id;
  }

  public void setSender_id(long sender_id) {
    this.sender_id = sender_id;
  }

  public long getChannel_id() {
    return channel_id;
  }

  public void setChannel_id(long channel_id) {
    this.channel_id = channel_id;
  }

  /**
   * To-many relationship, resolved on first access (and after reset). Changes to to-many relations
   * are not persisted, make changes to the target entity.
   */
  public List<User> getReaders() {
    if (readers == null) {
      if (daoSession == null) {
        throw new DaoException("Entity is detached from DAO context");
      }
      UserDao targetDao = daoSession.getUserDao();
      List<User> readersNew = targetDao._queryMessage_Readers(id);
      synchronized (this) {
        if (readers == null) {
          readers = readersNew;
        }
      }
    }
    return readers;
  }

  /** Resets a to-many relationship, making the next get call to query for a fresh result. */
  public synchronized void resetReaders() {
    readers = null;
  }

  /**
   * Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity
   * context.
   */
  public void delete() {
    if (myDao == null) {
      throw new DaoException("Entity is detached from DAO context");
    }
    myDao.delete(this);
  }

  /**
   * Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity
   * context.
   */
  public void update() {
    if (myDao == null) {
      throw new DaoException("Entity is detached from DAO context");
    }
    myDao.update(this);
  }

  /**
   * Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity
   * context.
   */
  public void refresh() {
    if (myDao == null) {
      throw new DaoException("Entity is detached from DAO context");
    }
    myDao.refresh(this);
  }
}
