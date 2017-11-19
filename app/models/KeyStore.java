package models;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.Transaction;
import com.sleepycat.je.txn.Txn;


public class KeyStore {

	private static EnvironmentConfig environmentConfig;
	private static Environment environ;
	private static DatabaseConfig databaseConfig;
	private static Database db;
	private static Transaction txn;
	
	public KeyStore() {

		environmentConfig = new EnvironmentConfig();
		environmentConfig.setAllowCreate(true);
		environmentConfig.setTransactional(true);
		
		environ = new Environment(new File("configdb"), environmentConfig);
		
		databaseConfig = new DatabaseConfig();
		databaseConfig.setAllowCreate(true);
		databaseConfig.setTransactional(true);
		
		
		txn = environ.beginTransaction(null, null);
		db = environ.openDatabase(null, "Navigator", 
				databaseConfig);

	}
	
	public static String get(String key) {

		DatabaseEntry keyEntry = new DatabaseEntry();
		DatabaseEntry dataEntry = new DatabaseEntry();

		try {
			keyEntry.setData(key.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			return null;
		}

		db.get(null, keyEntry, dataEntry, LockMode.DEFAULT);

		try {
			byte [] data = dataEntry.getData();
			if (data == null) return null;
			return new String(dataEntry.getData(), "utf-8");
		} catch (UnsupportedEncodingException e) {
			return null;
		}

	}
	
	public static boolean put(String key, String value) {

		try {
			db.put(null, new DatabaseEntry(key.getBytes("utf-8")), 
					new DatabaseEntry(value.getBytes("utf-8")));
		} catch (DatabaseException e) {
			return false;
		} catch (UnsupportedEncodingException e){
			return false;
		}
		
		return true;

	}
	
	public static void commit() {
		txn.commit();
	}
	
	public static void close() {
		db.close();
		environ.close();
	}

}