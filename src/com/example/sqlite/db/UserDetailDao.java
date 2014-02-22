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
public class UserDetailDao {

	private DatabaseOpenHelper helper = null;

	public UserDetailDao(Context context) {
		helper = new DatabaseOpenHelper(context);
	}

	/**
	 * UserDetailの保存
	 * rowidがnullの場合はinsert、rowidが!nullの場合はupdate
	 * @param userDetail 保存対象のオブジェクト
	 * @return 保存結果
	 */
	public UserDetail save( UserDetail userDetail){
		SQLiteDatabase db = helper.getWritableDatabase();
		UserDetail result = null;
		try {
			ContentValues values = new ContentValues();
			values.put( UserDetail.COLUMN_NAME, userDetail.getName());
			values.put( UserDetail.COLUMN_EMPNO, userDetail.getEmpNo());
			values.put( UserDetail.COLUMN_GENDER, userDetail.getGender());
			values.put( UserDetail.COLUMN_BIRTHDAY, userDetail.getBirthday());
			values.put( UserDetail.COLUMN_TEL, userDetail.getTel());

			Long rowId = userDetail.getRowid();
			// IDがnullの場合はinsert
			if( rowId == null){
				rowId = db.insert( UserDetail.TABLE_NAME, null, values);
			}
			else{
				db.update( UserDetail.TABLE_NAME, values, UserDetail.COLUMN_ID + "=?", new String[]{ String.valueOf( rowId)});
			}
			result = load( rowId);
		} finally {
			db.close();
		}
		return result;
	}

	/**
	 * レコードの削除
	 * @param userDetail 削除対象のオブジェクト
	 */
	public void delete(UserDetail userDetail) {
		SQLiteDatabase db = helper.getWritableDatabase();
		try {
			db.delete( UserDetail.TABLE_NAME, UserDetail.COLUMN_ID + "=?", new String[]{ String.valueOf( userDetail.getRowid())});
		} finally {
			db.close();
		}
	}

	/**
	 * idでUserDetailをロードする
	 * @param rowId PK
	 * @return ロード結果
	 */
	public UserDetail load(Long rowId) {
		SQLiteDatabase db = helper.getReadableDatabase();

		UserDetail userDetail = null;
		try {
			Cursor cursor = db.query( UserDetail.TABLE_NAME, null, UserDetail.COLUMN_ID + "=?", new String[]{ String.valueOf( rowId)}, null, null, null);
			cursor.moveToFirst();
			userDetail = getUserDetail(cursor);
		} finally {
			db.close();
		}
		return userDetail;
	}

	/**
	 * 一覧を取得する
	 * @return 検索結果
	 */
	public List<UserDetail> list() {
		SQLiteDatabase db = helper.getReadableDatabase();

		List<UserDetail> userDetailList;
		try {
			Cursor cursor = db.query( UserDetail.TABLE_NAME, null, null, null, null, null, UserDetail.COLUMN_ID);
			userDetailList = new ArrayList<UserDetail>();
			cursor.moveToFirst();
			while( !cursor.isAfterLast()){
				userDetailList.add( getUserDetail( cursor));
				cursor.moveToNext();
			}
		} finally {
			db.close();
		}
		return userDetailList;
	}

	/**
	 * カーソルからオブジェクトへの変換
	 * @param cursor カーソル
	 * @return 変換結果
	 */
	private UserDetail getUserDetail( Cursor cursor){
		UserDetail userDetail = new UserDetail();

		userDetail.setRowid( cursor.getLong(0));
		userDetail.setName( cursor.getString(1));
		userDetail.setEmpNo( cursor.getString(2));
		userDetail.setGender( cursor.getString(3));
		userDetail.setBirthday( cursor.getString(4));
		userDetail.setTel( cursor.getString(5));
		return userDetail;
	}
}