drop table decimal_2;

create table decimal_2 (t decimal);
alter table decimal_2 set serde 'org.apache.hadoop.hive.serde2.lazybinary.LazyBinarySerDe';

insert overwrite table decimal_2
  select cast('17.29' as decimal) from src limit 1;
select cast(t as boolean) from decimal_2 limit 1;
select cast(t as tinyint) from decimal_2 limit 1;
select cast(t as smallint) from decimal_2 limit 1;
select cast(t as int) from decimal_2 limit 1;
select cast(t as bigint) from decimal_2 limit 1;
select cast(t as float) from decimal_2 limit 1;
select cast(t as double) from decimal_2 limit 1;
select cast(t as string) from decimal_2 limit 1;

drop table decimal_2;
