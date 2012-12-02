/**
 * 
 */
package com.booksdata.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

/**
 * @author renis
 * 
 */
public class SerializationDemo {
	public static void main(String args[]) {

		SerializationDemo demo=new SerializationDemo();
		demo.serializeMyClass();
		//demo.deserializeMyClass();
	}

	public void serializeMyClass() {
		// Object serialization
		try {
			MyClass object1 = new MyClass("Hello", -7, 2.7e10);
			HashMap<String, MyClass> hashMap=new HashMap<String, MyClass>();
			hashMap.put("test", object1);
			
			System.out.println("object1: " + object1);
			FileOutputStream fos = new FileOutputStream("serial");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			//oos.writeObject(object1);
			oos.writeObject(hashMap);
			oos.flush();
			oos.close();
		} catch (Exception e) {
			System.out.println("Exception during serialization: " + e);
			System.exit(0);
		}
	}

	public void deserializeMyClass() {
		// Object deserialization

		try {
			MyClass object2;
			FileInputStream fis = new FileInputStream("serial");
			ObjectInputStream ois = new ObjectInputStream(fis);
			HashMap<String, MyClass> hashMap=(HashMap<String, MyClass>) ois.readObject();
			//object2 = (MyClass) ois.readObject();
			MyClass myclass=hashMap.get("test");
			System.out.println("String:"+myclass.s);
			System.out.println("Integer:"+myclass.i);
			System.out.println("Double:"+myclass.d);
			ois.close();
			//System.out.println("object2: " + object2);
		} catch (Exception e) {
			System.out.println("Exception during deserialization: " + e);
			System.exit(0);
		}

	}

}
