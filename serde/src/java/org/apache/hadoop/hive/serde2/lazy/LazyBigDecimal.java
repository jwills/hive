package org.apache.hadoop.hive.serde2.lazy;

import java.math.BigDecimal;
import java.nio.charset.CharacterCodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hive.serde2.io.BigDecimalWritable;
import org.apache.hadoop.hive.serde2.lazy.objectinspector.primitive.LazyBigDecimalObjectInspector;
import org.apache.hadoop.io.Text;

public class LazyBigDecimal extends LazyPrimitive<LazyBigDecimalObjectInspector, BigDecimalWritable> {
  static final private Log LOG = LogFactory.getLog(LazyBigDecimal.class);

  public LazyBigDecimal(LazyBigDecimalObjectInspector oi) {
    super(oi);
    data = new BigDecimalWritable();
  }

  public LazyBigDecimal(LazyBigDecimal copy) {
    super(copy);
    data = new BigDecimalWritable(copy.data);
  }

  /**
   * Initilizes LazyBigDecimal object by interpreting the input bytes
   * as a numeric string
   *
   * @param bytes
   * @param start
   * @param length
   */
  @Override
  public void init(ByteArrayRef bytes, int start, int length) {
    String byteData = null;
    try {
      byteData = Text.decode(bytes.getData(), start, length);
      data.set(new BigDecimal(byteData));
      isNull = false;
    } catch (NumberFormatException e) {
      isNull = true;
      LOG.debug("Data not in the BigDecimal data type range so converted to null. Given data is :"
          + byteData, e);
    } catch (CharacterCodingException e) {
      isNull = true;
      LOG.debug("Data not in the BigDecimal data type range so converted to null.", e);
    }
  }

  @Override
  public BigDecimalWritable getWritableObject() {
    return data;
  }
}
