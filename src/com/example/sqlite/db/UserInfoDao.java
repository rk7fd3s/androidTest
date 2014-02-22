package com.example.sqlite.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * BizCard用データアクセスクラス
 */
public class UserInfoDao {

	private DatabaseOpenHelper helper = null;

	public UserInfoDao(Context context) {
		helper = new DatabaseOpenHelper(context);
	}

	/**
	 * UserInfoの保存
	 * rowidがnullの場合はinsert、rowidが!nullの場合はupdate
	 * @param userInfo 保存対象のオブジェクト
	 * @return 保存結果
	 */
	public UserInfo save( UserInfo userInfo){
		SQLiteDatabase db = helper.getWritableDatabase();
		UserInfo result = null;
		try {
			ContentValues values = new ContentValues();
			values.put( UserInfo.COLUMN_MAILADDRESS, userInfo.getMailaddress());
			values.put( UserInfo.COLUMN_PASSWORD, userInfo.getPassword());

			Long rowId = userInfo.getRowid();
			// IDがnullの場合はinsert
			if( rowId == null){
				rowId = db.insert( UserInfo.TABLE_NAME, null, values);
			}
			else{
				db.update( UserInfo.TABLE_NAME, values, UserInfo.COLUMN_ID + "=?", new String[]{ String.valueOf( rowId)});
			}
			result = load( rowId);
		} finally {
			db.close();
		}
		return result;
	}

	/**
	 * レコードの削除
	 * @param userInfo 削除対象のオブジェクト
	 */
	public void delete(UserInfo userInfo) {
		SQLiteDatabase db = helper.getWritableDatabase();
		try {
			db.delete( UserInfo.TABLE_NAME, UserInfo.COLUMN_ID + "=?", new String[]{ String.valueOf( userInfo.getRowid())});
		} finally {
			db.close();
		}
	}

	/**
	 * idでUserInfoをロードする
	 * @param rowId PK
	 * @return ロード結果
	 */
	public UserInfo load(Long rowId) {
		SQLiteDatabase db = helper.getReadableDatabase();

		UserInfo userInfo = null;
		try {
			Cursor cursor = db.query( UserInfo.TABLE_NAME, null, UserInfo.COLUMN_ID + "=?", new String[]{ String.valueOf( rowId)}, null, null, null);
			cursor.moveToFirst();
			userInfo = getUserInfo(cursor);
		} finally {
			db.close();
		}
		return userInfo;
	}

	public UserInfo load(String mail, String pswd) {
		SQLiteDatabase db = helper.getReadableDatabase();

		UserInfo userInfo = null;
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM ");
		sb.append(UserInfo.TABLE_NAME);
		sb.append(" WHERE " + UserInfo.COLUMN_MAILADDRESS + "=?");
		sb.append(" and " + UserInfo.COLUMN_PASSWORD + "=?");
		try {
			Cursor cursor = db.rawQuery(sb.toString(), new String[]{mail, pswd});
			if (cursor.getCount() > 0) {
				cursor.moveToFirst();
				userInfo = getUserInfo(cursor);
			}
		} finally {
			db.close();
		}
		return userInfo;
	}

	/**
	 * 一覧を取得する
	 * @return 検索結果
	 */
	public List<UserInfo> list() {
		SQLiteDatabase db = helper.getReadableDatabase();

		List<UserInfo> userInfoList;
		try {
			Cursor cursor = db.query( UserInfo.TABLE_NAME, null, null, null, null, null, UserInfo.COLUMN_ID);
			userInfoList = new ArrayList<UserInfo>();
			cursor.moveToFirst();
			while( !cursor.isAfterLast()){
				userInfoList.add( getUserInfo( cursor));
				cursor.moveToNext();
			}
		} finally {
			db.close();
		}
		return userInfoList;
	}

	/**
	 * カーソルからオブジェクトへの変換
	 * @param cursor カーソル
	 * @return 変換結果
	 */
	private UserInfo getUserInfo( Cursor cursor){
		UserInfo userInfo = new UserInfo();

		userInfo.setRowid( cursor.getLong(0));
		userInfo.setMailaddress( cursor.getString(1));
		userInfo.setPassword( cursor.getString(2));
		return userInfo;
	}

	public void init() {
		SQLiteDatabase db = helper.getWritableDatabase();

		try {
			db.rawQuery("DROP DATABASE SAMPLE_SQLite;", null);
		} finally {
			db.close();
		}
	}
}