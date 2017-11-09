package com.devpino.memberandroid;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MemberDbAdapter {

    private static final String TAG = "MemberDbAdapter";
    
    public static MemberDbAdapter instance = null;
    
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    /**
     * Database creation sql statement
     */
    private static final String DATABASE_CREATE =
    	"create table devpino_member (_id integer primary key autoincrement, " +
                " email text not null, member_name text not null, password text, homepage text, " +
                " mobile_no text, address text, gender text, job text, comments text, photo_url text );";

    private static final String DATABASE_NAME = "memberdb";
    private static final String DATABASE_TABLE = "devpino_member";
    private static final int DATABASE_VERSION = 2;

    private final Context mCtx;
    
    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS memberdb");
            onCreate(db);
        }
    }    
    
    public MemberDbAdapter(Context context) {
    	this.mCtx = context;
    	
    	if (mDbHelper == null) {
            mDbHelper = new DatabaseHelper(mCtx);
		}

    }
    
    synchronized public static MemberDbAdapter getInstance(Context context) {
    	
    	if (instance == null) {
			instance = new MemberDbAdapter(context);
		}
    	
    	return instance;
    }

    synchronized public static MemberDbAdapter getInstance() {
    	
    	return instance;
    }

    public MemberDbAdapter open() throws SQLException {
    	
    	mDb = mDbHelper.getWritableDatabase();

    	return this;
    }

    public void close() {
        mDbHelper.close();
    }


    public long addMember(Member member) {
    	
        ContentValues initialValues = new ContentValues();
        
        initialValues.put(Member.KEY_ADDRESS, member.getAddress());
        initialValues.put(Member.KEY_COMMENTS, member.getComments());
        initialValues.put(Member.KEY_EMAIL, member.getEmail());
        initialValues.put(Member.KEY_GENDER, member.getGender());
        initialValues.put(Member.KEY_HOMEPAGE, member.getHomepage());
        initialValues.put(Member.KEY_PHOTO_URL, member.getPhotoUrl());
        initialValues.put(Member.KEY_JOB, member.getJob());
        initialValues.put(Member.KEY_MEMBER_NAME, member.getMemberName());
        initialValues.put(Member.KEY_MOBILE_NO, member.getMobileNo());
        initialValues.put(Member.KEY_PASSWORD, member.getPassword());

        return mDb.insert(DATABASE_TABLE, null, initialValues);
    }

    public boolean removeMember(long rowId) {

        return mDb.delete(DATABASE_TABLE, Member.KEY_ROWID + "=" + rowId, null) > 0;
    }

    public Cursor queryAllMembers() {

    	String[] columns = {Member.KEY_ROWID, Member.KEY_PHOTO_URL, Member.KEY_MEMBER_NAME,
                Member.KEY_EMAIL, Member.KEY_HOMEPAGE, Member.KEY_MOBILE_NO, Member.KEY_ADDRESS,
                Member.KEY_GENDER, Member.KEY_JOB, Member.KEY_COMMENTS};
    	
        return mDb.query(DATABASE_TABLE, null, null, null, null, null, null);
    }
    
    public List<Member> searchAllMembers() {
    	
    	List<Member> memberList = new ArrayList<Member>();
    	
    	Cursor mCursor = queryAllMembers();

        Member Member = null;
    	
    	while (mCursor.moveToNext()) {
		
    		Member = new Member();
    		
        	Member.setAddress(mCursor.getString(mCursor.getColumnIndexOrThrow(Member.KEY_ADDRESS)));
        	Member.setComments(mCursor.getString(mCursor.getColumnIndexOrThrow(Member.KEY_COMMENTS)));
        	Member.setEmail(mCursor.getString(mCursor.getColumnIndexOrThrow(Member.KEY_EMAIL)));
        	Member.setGender(mCursor.getString(mCursor.getColumnIndexOrThrow(Member.KEY_GENDER)));
        	Member.setHomepage(mCursor.getString(mCursor.getColumnIndexOrThrow(Member.KEY_HOMEPAGE)));
        	Member.setPhotoUrl(mCursor.getString(mCursor.getColumnIndexOrThrow(Member.KEY_PHOTO_URL)));
        	Member.setJob(mCursor.getString(mCursor.getColumnIndexOrThrow(Member.KEY_JOB)));
        	Member.setMemberName(mCursor.getString(mCursor.getColumnIndexOrThrow(Member.KEY_MEMBER_NAME)));
        	Member.setMobileNo(mCursor.getString(mCursor.getColumnIndexOrThrow(Member.KEY_MOBILE_NO)));
        	Member.setNo(mCursor.getInt(mCursor.getColumnIndexOrThrow(Member.KEY_ROWID)));

        	memberList.add(Member);
		}

    	mCursor.close();
    	
    	return memberList;
    }

    public Member getMember(long rowId) throws SQLException {

        Cursor mCursor = queryMember(rowId);

        Member member = new Member();
        
        if (mCursor != null) {
        	member.setAddress(mCursor.getString(mCursor.getColumnIndexOrThrow(member.KEY_ADDRESS)));
        	member.setComments(mCursor.getString(mCursor.getColumnIndexOrThrow(member.KEY_COMMENTS)));
        	member.setEmail(mCursor.getString(mCursor.getColumnIndexOrThrow(member.KEY_EMAIL)));
        	member.setGender(mCursor.getString(mCursor.getColumnIndexOrThrow(member.KEY_GENDER)));
        	member.setHomepage(mCursor.getString(mCursor.getColumnIndexOrThrow(member.KEY_HOMEPAGE)));
        	member.setPhotoUrl(mCursor.getString(mCursor.getColumnIndexOrThrow(member.KEY_PHOTO_URL)));
        	member.setJob(mCursor.getString(mCursor.getColumnIndexOrThrow(member.KEY_JOB)));
        	member.setMemberName(mCursor.getString(mCursor.getColumnIndexOrThrow(member.KEY_MEMBER_NAME)));
        	member.setMobileNo(mCursor.getString(mCursor.getColumnIndexOrThrow(member.KEY_MOBILE_NO)));
        	member.setNo(mCursor.getInt(mCursor.getColumnIndexOrThrow(member.KEY_ROWID)));
        }
        
        mCursor.close();
        
        return member;

    }

    public Cursor queryMember(long rowId) throws SQLException {

    	String[] columns = {Member.KEY_ROWID, Member.KEY_ADDRESS, Member.KEY_COMMENTS,
    			Member.KEY_EMAIL, Member.KEY_GENDER, Member.KEY_HOMEPAGE,
    			Member.KEY_PHOTO_URL, Member.KEY_JOB, Member.KEY_MEMBER_NAME,
    			Member.KEY_MOBILE_NO, Member.KEY_PASSWORD};

        Cursor mCursor =

            mDb.query(true, DATABASE_TABLE, columns, Member.KEY_ROWID + "=" + rowId, null,
                    null, null, null, null);
        
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        
        return mCursor;

    }

    public Member obtainMember(long rowId) {

        Member member = null;

        Cursor mCursor = queryMember(rowId);

        if (mCursor.moveToFirst()) {

            member = new Member();

            member.setAddress(mCursor.getString(mCursor.getColumnIndexOrThrow(member.KEY_ADDRESS)));
            member.setComments(mCursor.getString(mCursor.getColumnIndexOrThrow(member.KEY_COMMENTS)));
            member.setEmail(mCursor.getString(mCursor.getColumnIndexOrThrow(member.KEY_EMAIL)));
            member.setGender(mCursor.getString(mCursor.getColumnIndexOrThrow(member.KEY_GENDER)));
            member.setHomepage(mCursor.getString(mCursor.getColumnIndexOrThrow(member.KEY_HOMEPAGE)));
            member.setPhotoUrl(mCursor.getString(mCursor.getColumnIndexOrThrow(member.KEY_PHOTO_URL)));
            member.setJob(mCursor.getString(mCursor.getColumnIndexOrThrow(member.KEY_JOB)));
            member.setMemberName(mCursor.getString(mCursor.getColumnIndexOrThrow(member.KEY_MEMBER_NAME)));
            member.setMobileNo(mCursor.getString(mCursor.getColumnIndexOrThrow(member.KEY_MOBILE_NO)));
            member.setPassword(mCursor.getString(mCursor.getColumnIndexOrThrow(member.KEY_PASSWORD)));
            member.setNo(mCursor.getInt(mCursor.getColumnIndexOrThrow(member.KEY_ROWID)));

        }

        mCursor.close();

        return member;
    }
    
    public boolean checkEmail(String email) throws SQLException {

    	String[] columns = {Member.KEY_ROWID, Member.KEY_ADDRESS, Member.KEY_COMMENTS,
    			Member.KEY_EMAIL, Member.KEY_GENDER, Member.KEY_HOMEPAGE,
    			Member.KEY_PHOTO_URL, Member.KEY_JOB, Member.KEY_MEMBER_NAME,
    			Member.KEY_MOBILE_NO};

        Cursor mCursor =

            mDb.query(true, DATABASE_TABLE, columns, Member.KEY_EMAIL + "='" + email + "'", null,
                    null, null, null, null);

        if (mCursor != null && mCursor.getCount() > 0) {
            return false;
        }
        else {
        	return true;
        }

    }

    public boolean modifyMember(Member Member) {
        
        ContentValues contentValues = new ContentValues();
        
        contentValues.put(Member.KEY_ADDRESS, Member.getAddress());
        contentValues.put(Member.KEY_COMMENTS, Member.getComments());
        contentValues.put(Member.KEY_GENDER, Member.getGender());
        contentValues.put(Member.KEY_HOMEPAGE, Member.getHomepage());
        contentValues.put(Member.KEY_PHOTO_URL, Member.getPhotoUrl());
        contentValues.put(Member.KEY_JOB, Member.getJob());
        contentValues.put(Member.KEY_MOBILE_NO, Member.getMobileNo());
        contentValues.put(Member.KEY_PASSWORD, Member.getPassword());

        int result = mDb.update(DATABASE_TABLE, contentValues, Member.KEY_ROWID + "=" + Member.getNo(), null);

        if (result > 0) {
            return true;
        }
        else {
            return false;
        }

    }   
    
}
