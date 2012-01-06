/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hadoop.hive.serde2.io;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableUtils;

public class BigDecimalWritable implements WritableComparable<BigDecimalWritable> {

  private byte[] internalStorage = new byte[0];
  private int scale;

  public BigDecimalWritable() {
  }

  public BigDecimalWritable(byte[] bytes, int scale) {
    set(bytes, scale);
  }

  public BigDecimalWritable(BigDecimalWritable writable) {
    set(writable.getBigDecimal());
  }

  public BigDecimalWritable(BigDecimal value) {
    set(value);
  }

  public void set(BigDecimal value) {
    set(value.unscaledValue().toByteArray(), value.scale());
  }

  public void set(BigDecimalWritable writable) {
    set(writable.getBigDecimal());
  }

  public void set(byte[] bytes, int scale) {
    this.internalStorage = bytes;
  }

  public BigDecimal getBigDecimal() {
    return new BigDecimal(new BigInteger(internalStorage), scale);
  }

  @Override
  public void readFields(DataInput in) throws IOException {
    WritableUtils.readVInt(in);
    int byteArrayLen = WritableUtils.readVInt(in);
    if (internalStorage.length != byteArrayLen) {
      internalStorage = new byte[byteArrayLen];
    }
    in.readFully(internalStorage);
  }

  @Override
  public void write(DataOutput out) throws IOException {
    WritableUtils.writeVInt(out, scale);
    WritableUtils.writeVInt(out, internalStorage.length);
    out.write(internalStorage);
  }

  @Override
  public int compareTo(BigDecimalWritable that) {
    return getBigDecimal().compareTo(that.getBigDecimal());
  }

}
