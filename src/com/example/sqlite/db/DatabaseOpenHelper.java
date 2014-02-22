package com.example.sqlite.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * データベース処理クラス
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {

	// データベース名の定数
	private static final String DB_NAME = "SAMPLE_SQLite";

	/**
	 * 初期投入サンプルデータ
	 */
	private String[][] dummyUserInfo = new String[][]{
			{"aaaa@example.com", "password"},
			{"bbbb@example.com", "password"},
			{"cccc@example.com", "password"},
			{"dddd@example.com", "password"},
			{"eeee@example.com", "password"},
			{"ffff@example.com", "password"},
			{"gggg@example.com", "password"},
			{"hhhh@example.com", "password"},
			{"iiii@example.com", "password"}
		};
	private String[][] dummyUserDetail = new String[][]{
			{"武田 隆太", "0001", "m", "1991/12/3", "080- 995-4862"},
			{"新谷 剛基", "0002", "m", "1980/5/29", "090-9442-5564"},
			{"宮部 浩正", "0003", "m", "1973/12/12", "090-6516-1914"},
			{"馬場 麗奈", "0004", "f", "1969/4/4", "090- 398-1335"},
			{"大崎 薫", "0005", "f", "1968/12/16", "080-5283-8925"},
			{"立花 はるみ", "0006", "f", "1954/8/27", "080-9578-2026"},
			{"沢田 惇", "0007", "m", "1991/6/4", "090-3706-1927"},
			{"井沢 竜也", "0008", "m", "1993/5/9", "090-2127-7115"},
			{"柳家 明日", "0009", "f", "1981/11/17", "090-5241-2697"}
		};

	/**
	 * コンストラクタ
	 */
	public DatabaseOpenHelper(Context context) {
		// 指定したデータベース名が存在しない場合は、新たに作成されonCreate()が呼ばれる
		// バージョンを変更するとonUpgrade()が呼ばれる
		super(context, DB_NAME, null, 1);
	}

	/**
	 * データベースの生成に呼び出されるので、 スキーマの生成を行う
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.beginTransaction();

		try{
			// テーブルの生成
			StringBuilder createSql = new StringBuilder();
			createSql.append("create table " + UserInfo.TABLE_NAME + " (");
			createSql.append(UserInfo.COLUMN_ID + " integer primary key autoincrement not null,");
			createSql.append(UserInfo.COLUMN_MAILADDRESS + " text not null,");
			createSql.append(UserInfo.COLUMN_PASSWORD + " text not null");
			createSql.append(")");

			db.execSQL( createSql.toString());

			// サンプルデータの投入
			for( String[] data: dummyUserInfo){
				ContentValues values = new ContentValues();
				values.put(UserInfo.COLUMN_MAILADDRESS, data[ 0]);
				values.put(UserInfo.COLUMN_PASSWORD, data[ 1]);
				db.insert(UserInfo.TABLE_NAME, null, values);
			}

			// テーブルの生成
			createSql.setLength(0);
			createSql.append("create table " + UserDetail.TABLE_NAME + " (");
			createSql.append(UserDetail.COLUMN_ID + " integer primary key autoincrement not null,");
			createSql.append(UserDetail.COLUMN_NAME + " text not null,");
			createSql.append(UserDetail.COLUMN_EMPNO + " text not null,");
			createSql.append(UserDetail.COLUMN_GENDER + " text not null,");
			createSql.append(UserDetail.COLUMN_BIRTHDAY + " text not null,");
			createSql.append(UserDetail.COLUMN_TEL + " text not null");
			createSql.append(")");

			db.execSQL( createSql.toString());

			// サンプルデータの投入
			for( String[] data: dummyUserDetail){
				ContentValues values = new ContentValues();
				values.put(UserDetail.COLUMN_NAME, data[ 0]);
				values.put(UserDetail.COLUMN_EMPNO, data[ 1]);
				values.put(UserDetail.COLUMN_GENDER, data[ 2]);
				values.put(UserDetail.COLUMN_BIRTHDAY, data[ 3]);
				values.put(UserDetail.COLUMN_TEL, data[ 4]);
				db.insert(UserDetail.TABLE_NAME, null, values);
			}

			db.setTransactionSuccessful();

		} finally {
			db.endTransaction();
		}
	}

	/**
	 * データベースの更新
	 * 
	 * 親クラスのコンストラクタに渡すversionを変更したときに呼び出される
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO 
	}
}