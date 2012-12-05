/**
 * 
 */
package com.booksdata.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import com.booksdata.application.Main;
import com.booksdata.domain.FileDetails;

/**
 * @author renis
 * 
 */
public class SerializationDemo {
	private static final String SERIALIZE_MATRIX = "serializeMatrix";

	public static void main(String args[]) {

		SerializationDemo demo=new SerializationDemo();
		demo.serializeMatrix(null);
		//demo.deserializeMyClass();
	}

	public void serializeMatrix(HashMap<String, HashMap<String, FileDetails>> matrix) {
		// Object serialization
		try {
			//MyClass object1 = new MyClass("Hello", -7, 2.7e10);
//			HashMap<String, MyClass> hashMap=new HashMap<String, MyClass>();
//			hashMap.put("test", object1);
			
			//System.out.println("object1: " + object1);
			FileOutputStream fos = new FileOutputStream(SERIALIZE_MATRIX);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			//oos.writeObject(object1);
			oos.writeObject(matrix);
			oos.flush();
			oos.close();
		} catch (Exception e) {
			System.out.println("Exception during serialization: " + e);
			System.exit(0);
		}
	}

	public void deserializeMatrix() {
		// Object deserialization

		try {
			MyClass object2;
			FileInputStream fis = new FileInputStream(SERIALIZE_MATRIX);
			ObjectInputStream ois = new ObjectInputStream(fis);
			HashMap<String, HashMap<String, FileDetails>> matrix=(HashMap<String, HashMap<String, FileDetails>>) ois.readObject();
			//object2 = (MyClass) ois.readObject();
			
			//new Main().cnvrtMtrxToWrd(matrix);
			
			/*MyClass myclass=hashMap.get("test");
			System.out.println("String:"+myclass.s);
			System.out.println("Integer:"+myclass.i);
			System.out.println("Double:"+myclass.d);
*/			ois.close();
			//System.out.println("object2: " + object2);
		} catch (Exception e) {
			System.out.println("Exception during deserialization: " + e);
			System.exit(0);
		}

	}

}
