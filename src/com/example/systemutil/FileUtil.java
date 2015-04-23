package com.example.systemutil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public class FileUtil {
	
	public static List<Object> readObjectFromLocation(String filePath,int count) {
		ObjectInputStream objectInputStream = null;
		 //Object[] storeFileClass = null;
		 List<Object> storeFileClass = new ArrayList<Object>();
		try {
			File readFile = new File(filePath);
			if (readFile.exists()) {
				objectInputStream = new ObjectInputStream(new FileInputStream(
						filePath));
				try {
/*					storeFileClass = (List<Message>) objectInputStream
							.readObject();*/
					Object obj = null;
					for(int i =0;(obj = objectInputStream.readObject()) != null;i++){
						//storeFileClass[i] = obj;
						storeFileClass.add(obj);
					}
/*					for(int i =0;i<count;i++){
						//storeFileClass[i] = obj;
						storeFileClass.add(objectInputStream.readObject());
					}*/
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (objectInputStream != null)
					objectInputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return storeFileClass;
	}

	public static synchronized void writeObjectToLocation(String fileName,
			Object object, boolean isAppend) {
		//Log.e("FileUtil", "write object to location:" + object.toString());
		if(object == null && isAppend)
			return;
		/*ObjectOutputStream objectOutputStream = null;
		try {
			objectOutputStream = new ObjectOutputStream(new FileOutputStream(
					fileName, isAppend));
			objectOutputStream.writeObject(object);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				objectOutputStream.flush();
				if (objectOutputStream != null)
					objectOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}*/
		
		ExtenseObjectOutputStream objectOutputStream = null;
		try {
			File file = new File(fileName);
			objectOutputStream = ExtenseObjectOutputStream.newInstance(file, new FileOutputStream(
					fileName, isAppend));
			objectOutputStream.writeObject(object);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				objectOutputStream.flush();
				if (objectOutputStream != null)
					objectOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/*

	public static List<Message> readObjectFromLocation(String filePath) {
		ObjectInputStream objectInputStream = null;
		List<Message> storeFileClass = null;
		try {
			File readFile = new File(filePath);
			if (readFile.exists()) {
				objectInputStream = new ObjectInputStream(new FileInputStream(
						filePath));
				try {
					storeFileClass = (List<Message>) objectInputStream
							.readObject();
					//Object obj = null;
					while((obj = objectInputStream.readObject()) != null){
						storeFileClass.add((Message)obj);
					}
					for(int i =0;i<count;i++){
						storeFileClass.add((Message) objectInputStream.readObject());
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (objectInputStream != null)
					objectInputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return storeFileClass;
	}


	public static synchronized void writeObjectToLocation(String fileName,
			List<Message> object, boolean isAppend) {
		if(object == null)
			return;
		ObjectOutputStream objectOutputStream = null;
		try {
			objectOutputStream = new ObjectOutputStream(new FileOutputStream(
					fileName, isAppend));
			objectOutputStream.writeObject(object);
		ExtenseObjectOutputStream objectOutputStream = null;
		try {
			File file = new File(fileName);
			objectOutputStream = ExtenseObjectOutputStream.newInstance(file, new FileOutputStream(
					fileName, isAppend));
			objectOutputStream.writeObject(object);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				objectOutputStream.flush();
				if (objectOutputStream != null)
					objectOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		ExtenseObjectOutputStream objectOutputStream = null;
		try {
			File file = new File(fileName);
			objectOutputStream = ExtenseObjectOutputStream.newInstance(file, new FileOutputStream(
					fileName, isAppend));
			objectOutputStream.writeObject(object);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				objectOutputStream.flush();
				if (objectOutputStream != null)
					objectOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
*/}
