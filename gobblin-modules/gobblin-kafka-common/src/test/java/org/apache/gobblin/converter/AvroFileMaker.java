package org.apache.gobblin.converter;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.avro.Schema;
import org.apache.avro.file.CodecFactory;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.gobblin.configuration.ConfigurationKeys;
import org.apache.gobblin.util.AvroUtils;
import org.testng.annotations.Test;


public class AvroFileMaker {
  private static final Schema.Parser PARSER = new Schema.Parser();

  @Test
  public void doWrite()
      throws IOException {
    Schema schema = PARSER.parse(getClass().getResourceAsStream("/converter/record.avsc"));
    String time = getTime();

    GenericRecord record = new GenericData.Record(schema);
    record.put("id", "simpleRecord-" + time);
    record.put("created", Long.parseLong(time));

    Schema fileSchema = new Schema.Parser().parse(getClass().getResourceAsStream("/converter/envelope.avsc"));
    GenericDatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<>(fileSchema);
    DataFileWriter<GenericRecord> fileWriter = new DataFileWriter<>(datumWriter);
    fileWriter.setCodec(CodecFactory.deflateCodec(ConfigurationKeys.DEFAULT_DEFLATE_LEVEL));
    fileWriter.create(fileSchema, new File("/Users/zhchen/data/envelope.avro"));

    GenericRecord fileRecord = new GenericData.Record(fileSchema);

    String key = "envelopeRecord-" + time;
    fileRecord.put("key", ByteBuffer.wrap(key.getBytes()));

    Map<String, String> metadata = new HashMap<>();
    metadata.put("payloadSchemaId", "payloadSchemaId-" + time);
    fileRecord.put("metadata", metadata);

    fileRecord.put("payload", ByteBuffer.wrap(AvroUtils.recordToByteArray(record)));
    fileRecord.put("nestedRecord", record);

    fileWriter.append(fileRecord);
    fileWriter.close();
  }

  private String getTime() {
    DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    Date date = new Date();
    return dateFormat.format(date);
  }

  public void testJava8() {
  }
}
