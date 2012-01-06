package org.apache.hadoop.hive.serde2.lazy.objectinspector.primitive;

import java.math.BigDecimal;

import org.apache.hadoop.hive.serde2.io.BigDecimalWritable;
import org.apache.hadoop.hive.serde2.lazy.LazyBigDecimal;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.BigDecimalObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorUtils;

public class LazyBigDecimalObjectInspector
    extends AbstractPrimitiveLazyObjectInspector<BigDecimalWritable>
    implements BigDecimalObjectInspector {

  protected LazyBigDecimalObjectInspector() {
    super(PrimitiveObjectInspectorUtils.decimalTypeEntry);
  }

  @Override
  public Object copyObject(Object o) {
    return o == null ? null : new LazyBigDecimal((LazyBigDecimal) o);
  }

  @Override
  public BigDecimal getPrimitiveJavaObject(Object o) {
    return o == null ? null : ((LazyBigDecimal) o).getWritableObject().getBigDecimal();
  }

}
