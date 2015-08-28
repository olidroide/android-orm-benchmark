package com.littleinc.orm_benchmark.dbflow;

import android.content.Context;
import android.util.Log;
import com.littleinc.orm_benchmark.BenchmarkExecutable;
import com.littleinc.orm_benchmark.dbflow.model.Message;
import com.littleinc.orm_benchmark.dbflow.model.Message$Table;
import com.littleinc.orm_benchmark.dbflow.model.User;
import com.littleinc.orm_benchmark.util.Util;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.list.FlowQueryList;
import com.raizlabs.android.dbflow.sql.builder.Condition;
import com.raizlabs.android.dbflow.sql.language.Select;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public enum DBFlowExecutor implements BenchmarkExecutable {

  INSTANCE;

  @Override
  public int getProfilerId() {
    return 4;
  }

  @Override
  public String getOrmName() {
    return "DBFlow";
  }

  @Override
  public void init(Context context, boolean useInMemoryDb) {
    this.context = context;
    FlowManager.init(context);
  }

  @Override
  public long createDbStructure() throws SQLException {
    long start = System.nanoTime();

    return System.nanoTime() - start;
  }

  @Override
  public long writeWholeData() throws SQLException {
    final List<User> users = new LinkedList<User>();
    for (int i = 0; i < NUM_USER_INSERTS; i++) {
      User newUser = new User();
      newUser.setFirstName(Util.getRandomString(10));
      newUser.setLastName(Util.getRandomString(10));
      users.add(newUser);
    }

    final List<Message> messages = new LinkedList<Message>();
    for (long i = 0; i < NUM_MESSAGE_INSERTS; i++) {
      Message newMessage = new Message();

      newMessage.setCommandId(i);
      newMessage.setSortedBy(Double.valueOf(System.nanoTime()));
      newMessage.setContent(Util.getRandomString(100));
      newMessage.setClientId(System.currentTimeMillis());
      newMessage.setSenderId(Math.round(Math.random() * NUM_USER_INSERTS));
      newMessage.setChannelId(Math.round(Math.random() * NUM_USER_INSERTS));
      newMessage.setCreatedAt((int) (System.currentTimeMillis() / 1000L));

      messages.add(newMessage);
    }

    long start = System.nanoTime();

    FlowQueryList<User> userFlowQueryList = new FlowQueryList<>(User.class);
    userFlowQueryList.beginTransaction();
    userFlowQueryList.addAll(users);
    userFlowQueryList.endTransactionAndNotify();

    Log.d(DBFlowExecutor.class.getSimpleName(), "Done, wrote " + NUM_USER_INSERTS + " users");

    FlowQueryList<Message> flowMessageList = new FlowQueryList<>(Message.class);
    flowMessageList.beginTransaction();
    flowMessageList.addAll(messages);
    flowMessageList.endTransactionAndNotify();

    Log.d(DBFlowExecutor.class.getSimpleName(), "Done, wrote " + NUM_MESSAGE_INSERTS + " messages");

    return System.nanoTime() - start;
  }

  @Override
  public long readWholeData() throws SQLException {
    long start = System.nanoTime();

    List<User> userList = new FlowQueryList<>(User.class).getCopy();
    List<Message> messageList = new FlowQueryList<>(Message.class).getCopy();

    Log.d(DBFlowExecutor.class.getSimpleName(),
        "Read, " + (userList.size() + messageList.size()) + " rows");

    return System.nanoTime() - start;
  }

  @Override
  public long readIndexedField() throws SQLException {
    long start = System.nanoTime();
    float count = 0;

    FlowQueryList<Message> flowMessageList = new FlowQueryList<>(Message.class);
    for (Message message : flowMessageList) {
      count += message.getMyReaders().size();
    }

    Log.d(DBFlowExecutor.class.getSimpleName(), "Read Indexed, " + count + " entries");

    return System.nanoTime() - start;
  }

  @Override
  public long readSearch() throws SQLException {
    long start = System.nanoTime();
    List<Message> messages = new Select().from(Message.class)
        .where(Condition.column(Message$Table.CONTENT).like(SEARCH_TERM))
        .limit(SEARCH_LIMIT)
        .queryList();
    Log.d(DBFlowExecutor.class.getSimpleName(), "Read, " + messages.size() + " rows");
    return System.nanoTime() - start;
  }

  @Override
  public long dropDb() throws SQLException {
    long start = System.nanoTime();

    FlowQueryList<Message> flowMessageList = new FlowQueryList<>(Message.class);
    flowMessageList.beginTransaction();
    flowMessageList.clear();
    flowMessageList.endTransactionAndNotify();

    FlowQueryList<User> userFlowQueryList = new FlowQueryList<>(User.class);
    userFlowQueryList.beginTransaction();
    userFlowQueryList.clear();
    userFlowQueryList.endTransactionAndNotify();

    return System.nanoTime() - start;
  }

  private Context context;
}
