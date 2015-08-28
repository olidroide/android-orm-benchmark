package com.littleinc.orm_benchmark.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import com.littleinc.orm_benchmark.BenchmarkExecutable;
import com.littleinc.orm_benchmark.util.Util;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import static com.littleinc.orm_benchmark.util.Util.getRandomString;

public enum SQLiteExecutorFast implements BenchmarkExecutable {

  INSTANCE;

  private DataBaseHelper mHelper;

  @Override
  public int getProfilerId() {
    return 1;
  }

  @Override
  public String getOrmName() {
    return "RAWFAST";
  }

  @Override
  public void init(Context context, boolean useInMemoryDb) {
    mHelper = new DataBaseHelper(context, useInMemoryDb, "fast");
  }

  @Override
  public long createDbStructure() throws SQLException {
    long start = System.nanoTime();
    User.createTable(mHelper);
    Message.createTable(mHelper);
    return System.nanoTime() - start;
  }

  @Override
  public long writeWholeData() throws SQLException {
    List<User> users = new LinkedList<User>();
    for (int i = 0; i < NUM_USER_INSERTS; i++) {
      User newUser = new User();
      newUser.setLastName(getRandomString(10));
      newUser.setFirstName(getRandomString(10));

      users.add(newUser);
    }

    List<Message> messages = new LinkedList<Message>();
    for (int i = 0; i < NUM_MESSAGE_INSERTS; i++) {
      Message newMessage = new Message();
      newMessage.setCommandId(i);
      newMessage.setSortedBy(System.nanoTime());
      newMessage.setContent(Util.getRandomString(100));
      newMessage.setClientId(System.currentTimeMillis());
      newMessage.setSenderId(Math.round(Math.random() * NUM_USER_INSERTS));
      newMessage.setChannelId(Math.round(Math.random() * NUM_USER_INSERTS));
      newMessage.setCreatedAt((int) (System.currentTimeMillis() / 1000L));

      messages.add(newMessage);
    }

    long start = System.nanoTime();
    SQLiteDatabase db = mHelper.getWritableDatabase();

    try {
      db.beginTransaction();

      ContentValues userCv = new ContentValues();
      for (User user : users) {
        user.prepareForInsert(userCv);
        db.insert(User.TABLE_NAME, null, userCv);
      }
      Log.d(SQLiteExecutorFast.class.getSimpleName(), "Done, wrote " + NUM_USER_INSERTS + " users");

      //            insertMessagesContentValues(messages, db);

      insertMessagesCompiled(messages, db);

      Log.d(SQLiteExecutorFast.class.getSimpleName(),
          "Done, wrote " + NUM_MESSAGE_INSERTS + " messages");
      db.setTransactionSuccessful();
    } finally {
      db.endTransaction();
    }
    return System.nanoTime() - start;
  }

  @Override
  public long readWholeData() throws SQLException {
    long start = System.nanoTime();
    Cursor c = null;
    try {
      SQLiteDatabase db = mHelper.getReadableDatabase();
      List<Message> messages = new LinkedList<Message>();
      c = db.query(Message.TABLE_NAME, Message.PROJECTION, null, null, null, null, null);

      while (c != null && c.moveToNext()) {
        Message newMessage = new Message();
        newMessage.setChannelId(c.getLong(c.getColumnIndex(Message.CHANNEL_ID)));
        newMessage.setClientId(c.getLong(c.getColumnIndex(Message.CLIENT_ID)));
        newMessage.setCommandId(c.getLong(c.getColumnIndex(Message.COMMAND_ID)));
        newMessage.setContent(c.getString(c.getColumnIndex(Message.CONTENT)));
        newMessage.setCreatedAt(c.getInt(c.getColumnIndex(Message.CREATED_AT)));
        newMessage.setSenderId(c.getLong(c.getColumnIndex(Message.SENDER_ID)));
        newMessage.setSortedBy(c.getDouble(c.getColumnIndex(Message.SORTED_BY)));

        messages.add(newMessage);
      }
      Log.d(SQLiteExecutorFast.class.getSimpleName(), "Read, " + messages.size() + " rows");
    } finally {
      if (c != null) {
        c.close();
      }
    }
    return System.nanoTime() - start;
  }

  @Override
  public long readIndexedField() throws SQLException {
    long start = System.nanoTime();
    Cursor c = null;
    try {
      SQLiteDatabase db = mHelper.getReadableDatabase();
      String selection = Message.COMMAND_ID + "=?";
      String[] selectionArgs = new String[] {
          String.valueOf(LOOK_BY_INDEXED_FIELD)
      };
      c = db.query(Message.TABLE_NAME, Message.PROJECTION, selection, selectionArgs, null, null,
          null);

      if (c != null && c.moveToFirst()) {
        Message newMessage = new Message();
        newMessage.setChannelId(c.getLong(c.getColumnIndex(Message.CHANNEL_ID)));
        newMessage.setClientId(c.getLong(c.getColumnIndex(Message.CLIENT_ID)));
        newMessage.setCommandId(c.getLong(c.getColumnIndex(Message.COMMAND_ID)));
        newMessage.setContent(c.getString(c.getColumnIndex(Message.CONTENT)));
        newMessage.setCreatedAt(c.getInt(c.getColumnIndex(Message.CREATED_AT)));
        newMessage.setSenderId(c.getLong(c.getColumnIndex(Message.SENDER_ID)));
        newMessage.setSortedBy(c.getDouble(c.getColumnIndex(Message.SORTED_BY)));

        Log.d(SQLiteExecutorFast.class.getSimpleName(), "Read, " + c.getCount() + " rows");
      }
    } finally {
      if (c != null) {
        c.close();
      }
    }
    return System.nanoTime() - start;
  }

  @Override
  public long readSearch() throws SQLException {
    long start = System.nanoTime();
    Cursor c = null;
    try {
      SQLiteDatabase db = mHelper.getReadableDatabase();
      String selection = Message.CONTENT + " LIKE ?";
      List<Message> messages = new LinkedList<Message>();
      String[] selectionArgs = new String[] { '%' + SEARCH_TERM + '%' };
      c = db.query(Message.TABLE_NAME, Message.PROJECTION, selection, selectionArgs, null, null,
          null, String.valueOf(SEARCH_LIMIT));

      while (c != null && c.moveToNext()) {
        Message newMessage = new Message();
        newMessage.setChannelId(c.getLong(c.getColumnIndex(Message.CHANNEL_ID)));
        newMessage.setClientId(c.getLong(c.getColumnIndex(Message.CLIENT_ID)));
        newMessage.setCommandId(c.getLong(c.getColumnIndex(Message.COMMAND_ID)));
        newMessage.setContent(c.getString(c.getColumnIndex(Message.CONTENT)));
        newMessage.setCreatedAt(c.getInt(c.getColumnIndex(Message.CREATED_AT)));
        newMessage.setSenderId(c.getLong(c.getColumnIndex(Message.SENDER_ID)));
        newMessage.setSortedBy(c.getDouble(c.getColumnIndex(Message.SORTED_BY)));

        messages.add(newMessage);
      }
      Log.d(SQLiteExecutorFast.class.getSimpleName(), "Read, " + messages.size() + " rows");
    } finally {
      if (c != null) {
        c.close();
      }
    }
    return System.nanoTime() - start;
  }

  @Override
  public long dropDb() throws SQLException {
    long start = System.nanoTime();
    User.dropTable(mHelper);
    Message.dropTable(mHelper);
    return System.nanoTime() - start;
  }

  private void insertMessagesContentValues(List<Message> messages, SQLiteDatabase db) {
    ContentValues messageCv = new ContentValues();
    for (Message message : messages) {
      message.prepareForInsert(messageCv);
      db.insert(Message.TABLE_NAME, null, messageCv);
    }
  }

  private void insertMessagesCompiled(List<Message> messages, SQLiteDatabase db) {
    SQLiteStatement sqlStatement = createSqlStatement(db);
    for (Message message : messages) {
      bindValues(sqlStatement, message);
      sqlStatement.execute();
    }
  }

  protected SQLiteStatement createSqlStatement(SQLiteDatabase db) {
    String sql =
        "INSERT INTO message(_id, client_id, sorted_by, created_at, content, sender_id, channel_id, command_id) values "
            + "(?, ?, ?, ?, ?, ?, ?, ?)";

    return db.compileStatement(sql);
  }

  protected void bindValues(SQLiteStatement stmt, Message entity) {
    stmt.clearBindings();

    //            Long id = entity.getId();
    //            if (id != null) {
    //                stmt.bindLong(1, id);
    //            }

    String content = entity.getContent();
    if (content != null) {
      stmt.bindString(2, content);
    }

    Long client_id = entity.getClientId();
    if (client_id != null) {
      stmt.bindLong(3, client_id);
    }

    Integer created_at = entity.getCreatedAt();
    if (created_at != null) {
      stmt.bindLong(4, created_at);
    }

    Double sorted_by = entity.getSortedBy();
    if (sorted_by != null) {
      stmt.bindDouble(5, sorted_by);
    }

    Long command_id = entity.getCommandId();
    if (command_id != null) {
      stmt.bindLong(6, command_id);
    }
    stmt.bindLong(7, entity.getSenderId());
    stmt.bindLong(8, entity.getChannelId());
  }
}
