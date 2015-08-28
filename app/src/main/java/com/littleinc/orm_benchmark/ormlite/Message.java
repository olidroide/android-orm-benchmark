package com.littleinc.orm_benchmark.ormlite;

import android.provider.BaseColumns;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import java.sql.SQLException;

//import com.j256.ormlite.android.apptools.DatabaseQuery;

@DatabaseTable(tableName = Message.TABLE_NAME)
public class Message {

  public static final String TABLE_NAME = "message";

  public static final String CONTENT = "content";

  public static final String READERS = "readers";

  public static final String SORTED_BY = "sorted_by";

  public static final String CLIENT_ID = "client_id";

  public static final String SENDER_ID = "sender_id";

  public static final String CHANNEL_ID = "channel_id";

  public static final String COMMAND_ID = "command_id";

  public static final String CREATED_AT = "created_at";

  @DatabaseField(columnName = BaseColumns._ID, generatedId = true, dataType = DataType.LONG)
  public long mId;

  @DatabaseField(columnName = CLIENT_ID, dataType = DataType.LONG)
  public long mClientId;

  @DatabaseField(columnName = COMMAND_ID, index = true, dataType = DataType.LONG)
  public long mCommandId;

  @DatabaseField(columnName = SORTED_BY, dataType = DataType.DOUBLE)
  public double mSortedBy;

  @DatabaseField(columnName = CREATED_AT, dataType = DataType.INTEGER)
  public int mCreatedAt;

  @DatabaseField(columnName = CONTENT, dataType = DataType.STRING)
  public String mContent;

  @DatabaseField(columnName = SENDER_ID, canBeNull = false, dataType = DataType.LONG)
  public long mSenderId;

  @DatabaseField(columnName = CHANNEL_ID, canBeNull = false, dataType = DataType.LONG)
  public long mChannelId;

  @ForeignCollectionField(eager = false, columnName = READERS)
  private ForeignCollection<User> mReaders;

  private static Dao<Message, Long> sDao;

  public static Dao<Message, Long> getDao() {
    if (sDao == null) {
      try {
        sDao = DataBaseHelper.getInstance().getDao(Message.class);
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }
    return sDao;
  }

  //    public ForeignCollection<DataBaseHelper> getReaders() {
  //        return mReaders;
  //    }
}
