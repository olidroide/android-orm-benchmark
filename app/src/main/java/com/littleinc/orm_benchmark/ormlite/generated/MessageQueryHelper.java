package com.littleinc.orm_benchmark.ormlite.generated;

//import co.touchlab.rchery.app.models.Category;

import com.j256.ormlite.dao.RawRowObjectMapper;
import com.j256.ormlite.field.DataType;
import com.littleinc.orm_benchmark.ormlite.Message;

public class MessageQueryHelper {
  public static class Mapper implements RawRowObjectMapper<Message> {
    public Message mapRow(String[] columnNames, DataType[] dataTypes, Object[] resultColumns) {
      Message mq = new Message();
      mq.mId = ((Number) resultColumns[0]).longValue();
      mq.mClientId = ((Number) resultColumns[1]).longValue();
      mq.mCommandId = ((Number) resultColumns[2]).longValue();
      mq.mSortedBy = ((Number) resultColumns[3]).doubleValue();
      mq.mCreatedAt = ((Number) resultColumns[4]).intValue();
      mq.mContent = (String) resultColumns[5];
      mq.mSenderId = ((Number) resultColumns[6]).longValue();
      mq.mChannelId = ((Number) resultColumns[7]).longValue();
      return mq;
    }
  }
}
