package org.example.test;

import com.linkedin.avro.fastserde.*;
import com.linkedin.avroutil1.compatibility.AvroCompatibilityHelper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Encoder;

public class Java11Compatible {

  private static DatumWriter<GenericRecord> serializer;
  private static DatumWriter<GenericRecord> fastSerializer;
  private static Schema testBenchmarkSchema = Schema.parse("{\"namespace\": \"example.avro\",\"type\": \"record\",\"name\": \"User\",\"fields\": [{\"name\": \"name\", \"type\": \"string\"},{\"name\": \"favorite_number\",  \"type\": [\"int\", \"null\"]},{\"name\": \"favorite_color\", \"type\": [\"string\", \"null\"]}]}");

  public static void main(String[] args) throws IOException {

    serializer = new GenericDatumWriter<>(testBenchmarkSchema);
    fastSerializer = new FastGenericDatumWriter<>(testBenchmarkSchema);

    ByteArrayOutputStream output = new ByteArrayOutputStream();
    Encoder encoder = AvroCompatibilityHelper.newBinaryEncoder(output, true, null);

    for(int i=0; i<10; i++) {
      GenericRecord user1 = new GenericData.Record(testBenchmarkSchema);
      user1.put("name", "Alyssa");
      user1.put("favorite_number", i);

      serializer.write(user1, encoder);
      fastSerializer.write(user1, encoder);

      System.out.println(user1);
    }


  }
}
