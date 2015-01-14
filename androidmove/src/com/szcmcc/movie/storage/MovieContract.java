package com.szcmcc.movie.storage;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public class MovieContract {//声明所有要用的表及其内各个字段
	/**
	 * Indicates the version of the database schema. !!!!! Increment this number
	 * when you make changes to the tables below !!!!!
	 */
	public static final int DB_VERSION = 1;
	
	public static final String SCHEME = "content";
	public static final String AUTHORITY = "com.itotem.szcmcc.movie";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);
	
	/**
     * The content URI for province
     */
	public static final String OPERATION_CONTENT_URI = "operation/";
	public static final String PICTURE_DB_CONTENT_URI = "picture_db/";
	public static final String PICTURE_SDCARD_CONTENT_URI = "picture_sdcard/";
	public static final String CLEAN_DB_CONTENT_URI = "clean/";
	public static final String DOWN_LOG_CONTENT_URI = "down_log/";
	
   
    

	public static final class Tables {

		private static final HashMap<Class<? extends AbstractTable>, HashSet<String>> tableFields = new HashMap<Class<? extends AbstractTable>, HashSet<String>>();
		private static final HashMap<Class<? extends AbstractTable>, String> tableNames = new HashMap<Class<? extends AbstractTable>, String>();

		/**
		 * Get value from given field.
		 * 
		 * @param f
		 *            field object. Field must be static.
		 * @return field's value or null if value can't be read.
		 */
		private static final String getFieldValue(Field f) {
			try {
				return f.get(null).toString();
			} catch (final Exception e) {
				return null;
			}
		}

		static {
			HashSet<String> fields = null;
			
			/**
			 * 图片--存库
			 */
			tableNames.put(Tables.PictureToDBTable.class, PictureToDBTable.TABLE_NAME);
			fields = new HashSet<String>();
			fields.add(Tables.PictureToDBTable.URL);
			fields.add(Tables.PictureToDBTable.BYTESTR);
			fields.add(Tables.PictureToDBTable._ID);
			tableFields.put(Tables.PictureToDBTable.class, fields);

			/**
			 * 图片--存SD卡
			 */
			tableNames.put(Tables.PictureToSDCardTable.class, PictureToSDCardTable.TABLE_NAME);
			fields = new HashSet<String>();
			fields.add(Tables.PictureToSDCardTable.URL);
			fields.add(Tables.PictureToSDCardTable.PATH);
			fields.add(Tables.PictureToSDCardTable._ID);
			tableFields.put(Tables.PictureToSDCardTable.class, fields);

			/**
			 * 操作表
			 */
			tableNames.put(Tables.OperationLogTable.class, OperationLogTable.TABLE_NAME);
			fields = new HashSet<String>();
			fields.add(Tables.OperationLogTable.KEY);
			fields.add(Tables.OperationLogTable.TIME);
			fields.add(Tables.OperationLogTable._ID);
			tableFields.put(Tables.OperationLogTable.class, fields);
			
			/**
			 * 断点下载记录
			 */
			tableNames.put(Tables.DownloadLogTable.class, DownloadLogTable.TABLE_NAME);
			fields = new HashSet<String>();
			fields.add(Tables.DownloadLogTable.DOWN_LENGTH);
			fields.add(Tables.DownloadLogTable.DOWN_PATH);
			fields.add(Tables.DownloadLogTable.THREAD_ID);
			fields.add(Tables.DownloadLogTable._ID);
			tableFields.put(Tables.DownloadLogTable.class, fields);

			
		}

		/**
		 * Abstract definition of local table.
		 */
		public static abstract class AbstractTable {

			@TableColumn(type = TableColumn.Types.INTEGER, isPrimary = true)
			public final static String _ID = "_id";
		}

		/**
		 * Annotation used for describing the name of table. If you use this
		 * notion for a class, it will visible as a table name.
		 */
		@Retention(RetentionPolicy.RUNTIME)
		protected @interface Table {
			String name();
		}

		/**
		 * Annotation used for describing table's columns. If you use this
		 * notion for a static field from class, it will visible as a table
		 * column. The type of the column is mandatory. Additionally you can
		 * mark a field as a primary key, index it, add "not null"/"unique"
		 * property.
		 */
		@Retention(RetentionPolicy.RUNTIME)
		protected @interface TableColumn {
			public enum Types {
				INTEGER, TEXT, BLOB
			}

			Types type();

			boolean isPrimary() default false;

			boolean isIndex() default false;

			/**
			 * A NOT NULL constraint may only be attached to a column
			 * definition, not specified as a table constraint. Not
			 * surprisingly, a NOT NULL constraint dictates that the associated
			 * column may not contain a NULL value. Attempting to set the column
			 * value to NULL when inserting a new row or updating an existing
			 * one causes a constraint violation.
			 */

			boolean isNotNull() default false;

			/**
			 * A UNIQUE constraint is similar to a PRIMARY KEY constraint,
			 * except that a single table may have any number of UNIQUE
			 * constraints. For each UNIQUE constraint on the table, each row,
			 * isPrimary=true must feature a unique combination of values in the
			 * columns identified by the UNIQUE constraint. As with PRIMARY KEY
			 * constraints, for the purposes of UNIQUE constraints NULL values
			 * are considered distinct from all other values (including other
			 * NULLs). If an INSERT or UPDATE statement attempts to modify the
			 * table content so that two or more rows feature identical values
			 * in a set of columns that are subject to a UNIQUE constraint, it
			 * is a constraint violation.
			 */
			boolean isUnique() default false;
		}

		/**
		 * Returns a set of class objects for all tables.
		 * 
		 * @param c
		 *            class object of the table.
		 * @return set with tables calsses.
		 */
		public final static Set<Class<? extends AbstractTable>> getTables() {
			return tableNames.keySet();//获取tableNames内所有键
		}

		/**
		 * Returns the table name used for storing the given table class
		 * 
		 * @param c
		 *            class object of the table.
		 * @return table's name or null if there is no table.
		 */
		public final static String getTableName(Class<? extends AbstractTable> c) {
			return tableNames.get(c);
		}

		/**
		 * Returns columns extracted from a class object.
		 * 
		 * @param c
		 *            table's class.
		 * @return set of columns or empty set.
		 */
		public final static Set<String> getTableColumns(Class<? extends AbstractTable> c) {
			return tableFields.get(c);
		}

		/**
		 * Return column list as array of Strings
		 * 
		 * @param c
		 *            Table's class
		 * @return Array of columns, will return empty array if there is no
		 *         columns in the table
		 */
		public final static String[] getTableColumnsArray(Class<? extends AbstractTable> c) {
			final Set<String> columns = getTableColumns(c);
			if (columns.size() == 0) {
				return new String[0];
			}
			final String[] result = new String[columns.size()];
			return columns.toArray(result);
		}

		/**
		 * Returns sql statements which will create table described by given
		 * class. Statements for creating indexes are placed at the end of
		 * result list.
		 * 
		 * @param c
		 *            table's class.
		 * @return list of sql statements or empty list.
		 * @throws IllegalArgumentException
		 *             when table's class doesn't have 'name' annotation.
		 */
		public final static List<String> getCreateStatments(Class<? extends AbstractTable> c) {
			final Table tableNameAnnotation = c.getAnnotation(Table.class);
			final List<String> createStatments = new ArrayList<String>();
			final List<String> indexStatments = new ArrayList<String>();
			if (tableNameAnnotation == null) {
				throw new IllegalArgumentException("No 'name' annotation for table: " + c.getSimpleName());
			} else {
				final StringBuilder builder = new StringBuilder();
				final String tableName = tableNameAnnotation.name();
				builder.append("CREATE TABLE ");
				builder.append(tableName);
				builder.append(" (");
				for (final Field f : c.getFields()) {
					f.setAccessible(true);
					final String fieldValue = getFieldValue(f);
					if (fieldValue != null) {
						final TableColumn tableColumnAnnotation = f.getAnnotation(TableColumn.class);
						if (tableColumnAnnotation != null) {
							builder.append(fieldValue);
							builder.append(" ");
							if (tableColumnAnnotation.type() == TableColumn.Types.INTEGER) {
								builder.append(" INTEGER");
							} else if (tableColumnAnnotation.type() == TableColumn.Types.BLOB) {
								builder.append(" BLOB");
							} else {
								builder.append(" TEXT");
							}
							if (tableColumnAnnotation.isPrimary()) {
								builder.append(" PRIMARY KEY");
							} else {
								if (tableColumnAnnotation.isNotNull()) {
									builder.append(" NOT NULL");
								}
								if (tableColumnAnnotation.isUnique()) {
									builder.append(" UNIQUE");
								}
							}
							if (tableColumnAnnotation.isIndex()) {
								indexStatments.add("CREATE INDEX idx_" + fieldValue + "_" + tableName + " ON "
										+ tableName + "(" + fieldValue + ");");
							}
							builder.append(", ");
						}
					}
				}
				builder.setLength(builder.length() - 2); // remove last ','
				builder.append(");");
				createStatments.add(builder.toString());
				createStatments.addAll(indexStatments);
				return createStatments;
			}
		}

		/**
		 * Returns sql selection statement for all columns of a given <i>c</i>
		 * table class.
		 * 
		 * @param c
		 *            table's class.
		 * @return selection statement (excluding SELECT)
		 */
		public final static String getFullSelectionList(Class<? extends Tables.AbstractTable> c) {
			final StringBuilder str = new StringBuilder();
			final Iterator<String> it = tableFields.get(c).iterator();
			while (it.hasNext()) {
				str.append(it.next());
				if (it.hasNext()) {
					str.append(",");
				}
			}
			return str.toString();
		}

		/**
		 * Fill content values using given columns with data from cursor. Copy
		 * operation is performed with following rules: 1. if there is no column
		 * in the cursor, add value with empty string. 2. if value from cursor
		 * is null, add value with empty string. 3. getString() method is used
		 * for retrieving data from cursor.
		 * 
		 * @param values
		 *            to fill.
		 * @param columns
		 *            names for fields.
		 * @param cursor
		 *            cursor with data.
		 */
		public static void fillConentVaules(ContentValues values, Iterator<String> columns, Cursor cursor) {
			values.clear();
			while (columns.hasNext()) {
				final String streamTableColumn = columns.next();
				final int streamTableColIndex = cursor.getColumnIndex(streamTableColumn);
				if (streamTableColIndex != -1) {
					values.put(streamTableColumn, cursor.isNull(streamTableColIndex) ? "" : cursor
							.getString(streamTableColIndex));
				}
			}
		}
		
		/**
		 * 图片存到数据库
		 * 
		 * @author LiaoWenXin
		 * 
		 */
		@Table(name = PictureToDBTable.TABLE_NAME)
		public static class PictureToDBTable extends AbstractTable {
			public final static String TABLE_NAME = "picture_db";

			@TableColumn(type = TableColumn.Types.TEXT, isIndex = true, isUnique = true, isNotNull = true)
			public final static String URL = "url";

			@TableColumn(type = TableColumn.Types.BLOB)
			public final static String BYTESTR = "byteStr";
		}

		/**
		 * 图片存到SD卡
		 * 
		 * @author LiaoWenXin
		 * 
		 */
		@Table(name = PictureToSDCardTable.TABLE_NAME)
		public static class PictureToSDCardTable extends AbstractTable {
			public final static String TABLE_NAME = "picture_sdcard";

			/**
			 * The image path
			 */
			@TableColumn(type = TableColumn.Types.TEXT, isUnique = true, isNotNull = true, isIndex = true)
			public final static String PATH = "path";

			@TableColumn(type = TableColumn.Types.TEXT, isIndex = true, isNotNull = true)
			public final static String URL = "url";

		}

		/**
		 * 操作记录
		 * 
		 * @author LiaoWenXin
		 * 
		 */
		@Table(name = OperationLogTable.TABLE_NAME)
		public static class OperationLogTable extends AbstractTable {
			public final static String TABLE_NAME = "operation_log";

			@TableColumn(type = TableColumn.Types.TEXT, isIndex = true, isUnique = true, isNotNull = true)
			public final static String KEY = "key";

			@TableColumn(type = TableColumn.Types.INTEGER)
			public final static String TIME = "time";
		}
		
		/**
		 * 断点下载记录
		 * 
		 * @author LiaoWenXin
		 * 
		 */
		@Table(name = DownloadLogTable.TABLE_NAME)
		public static class DownloadLogTable extends AbstractTable {
			public final static String TABLE_NAME = "download_log";

			@TableColumn(type = TableColumn.Types.TEXT, isIndex = true, isNotNull = true)
			public final static String DOWN_PATH = "down_path";

			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String THREAD_ID = "thread_id";

			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String DOWN_LENGTH = "down_length";

		}

	
		
		

	

		
	
	}
}
