package com.littleinc.orm_benchmark.realm;

import android.content.Context;
import android.util.Log;
import com.littleinc.orm_benchmark.BenchmarkExecutable;
import com.littleinc.orm_benchmark.realm.model.Message;
import com.littleinc.orm_benchmark.realm.model.User;
import com.littleinc.orm_benchmark.util.Util;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public enum RealmExecutor implements BenchmarkExecutable {
  INSTANCE;

  //private Realm realm;
  private Context context;

  @Override
  public int getProfilerId() {
    return 5;
  }

  @Override
  public String getOrmName() {
    return "Realm";
  }

  @Override
  public void init(Context context, boolean useInMemoryDb) {
    RealmConfiguration config =
        new RealmConfiguration.Builder(context).name("myrealm.realm").build();
    this.context = context;
  }

  @Override
  public long createDbStructure() throws SQLException {
    long start = System.nanoTime();

    return System.nanoTime() - start;
  }

  @Override
  public long writeWholeData() throws SQLException {
    final List<Message> messages = new LinkedList<Message>();
    final List<User> users = new LinkedList<User>();
    Realm realm = Realm.getInstance(context);
    long start = System.nanoTime();

    realm.beginTransaction();
    for (int i = 0; i < NUM_USER_INSERTS; i++) {
      User newUser = realm.createObject(User.class);
      newUser.setFirst_name(Util.getRandomString(10));
      newUser.setLast_name(Util.getRandomString(10));
      users.add(newUser);
    }
    realm.commitTransaction();
    Log.d(RealmExecutor.class.getSimpleName(), "Done, wrote " + NUM_USER_INSERTS + " users");

    realm.beginTransaction();
    for (long i = 0; i < NUM_MESSAGE_INSERTS; i++) {
      Message newMessage = realm.createObject(Message.class);
      newMessage.setCommandId(i);
      newMessage.setSortedBy(Double.valueOf(System.nanoTime()));
      newMessage.setContent(Util.getRandomString(100));
      newMessage.setClientId(System.currentTimeMillis());
      newMessage.setSenderId(Math.round(Math.random() * NUM_USER_INSERTS));
      newMessage.setChannelId(Math.round(Math.random() * NUM_USER_INSERTS));
      newMessage.setCreatedAt((int) (System.currentTimeMillis() / 1000L));

      messages.add(newMessage);
    }
    realm.commitTransaction();

    Log.d(RealmExecutor.class.getSimpleName(), "Done, wrote " + NUM_MESSAGE_INSERTS + " messages");

    return System.nanoTime() - start;
  }

  @Override
  public long readWholeData() throws SQLException {
    long start = System.nanoTime();
    Realm realm = Realm.getInstance(context);
    RealmResults<User> userRealmResults = realm.allObjects(User.class);
    RealmResults<Message> messageRealmResults = realm.allObjects(Message.class);
    for (Message message : messageRealmResults) {
      message.getReaders();
    }

    Log.d(RealmExecutor.class.getSimpleName(),
        "Read, " + (userRealmResults.size() + messageRealmResults.size()) + " rows");
    return System.nanoTime() - start;
  }

  @Override
  public long readIndexedField() throws SQLException {
    long start = System.nanoTime();
    Realm realm = Realm.getInstance(context);
    float count = 0;
    RealmResults<Message> messageRealmResults = realm.allObjects(Message.class);
    for (Message message : messageRealmResults) {
      count += message.getReaders().size();
    }
    Log.d(RealmExecutor.class.getSimpleName(), "Read Indexed, " + count + " entries");

    return System.nanoTime() - start;
  }

  @Override
  public long readSearch() throws SQLException {
    long start = System.nanoTime();
    Realm realm = Realm.getInstance(context);
    RealmResults<Message> realmResults =
        realm.where(Message.class).contains("content", SEARCH_TERM).findAll();

    Log.d(RealmExecutor.class.getSimpleName(), "Read, " + realmResults.size() + " rows");

    return System.nanoTime() - start;
  }

  @Override
  public long dropDb() throws SQLException {
    long start = System.nanoTime();
    Realm realm = Realm.getInstance(context);
    realm.beginTransaction();
    realm.allObjects(User.class).clear();
    realm.allObjects(Message.class).clear();
    realm.commitTransaction();

    return System.nanoTime() - start;
  }
}
