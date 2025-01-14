package com.github.potix2.spark.google.spreadsheets

import com.google.api.services.sheets.v4.model.{CellData, ExtendedValue, RowData}
import org.apache.spark.sql.Row
import org.apache.spark.sql.types.{DataTypes, StructType}

import scala.collection.JavaConverters._
import scala.collection.breakOut
import scala.language.postfixOps

object Util {

  def toRowData(row: Row): RowData =
      new RowData().setValues(
        row.schema.fields.zipWithIndex.map { case (f, i) =>
          new CellData()
            .setUserEnteredValue(
              f.dataType match {
                case DataTypes.StringType => new ExtendedValue().setStringValue(row.getString(i))
                case DataTypes.LongType => new ExtendedValue().setNumberValue(row.getLong(i).toDouble)
                case DataTypes.IntegerType => new ExtendedValue().setNumberValue(row.getInt(i).toDouble)
                case DataTypes.FloatType => new ExtendedValue().setNumberValue(row.getFloat(i).toDouble)
                case DataTypes.BooleanType => new ExtendedValue().setBoolValue(row.getBoolean(i))
                case DataTypes.DateType => new ExtendedValue().setStringValue(row.getDate(i).toString)
                case DataTypes.ShortType => new ExtendedValue().setNumberValue(row.getShort(i).toDouble)
                case DataTypes.TimestampType => new ExtendedValue().setStringValue(row.getTimestamp(i).toString)
                case DataTypes.DoubleType => new ExtendedValue().setNumberValue(row.getDouble(i))
              }
            )
        }(breakOut).asJava
      )
}
