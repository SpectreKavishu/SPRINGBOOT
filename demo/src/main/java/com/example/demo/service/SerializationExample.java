package com.example.demo.service;

import java.io.*;

//A simple class that implements Serializable
class Employee implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private int age;

	public Employee(String name, int age) {
		this.name = name;
		this.age = age;
	}

	@Override
	public String toString() {
		return "Employee{name='" + name + "', age=" + age + '}';
	}
}

public class SerializationExample {
	public static void main(String[] args) {
		Employee emp = new Employee("Alice", 30);

		// Serialize the object
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("employee.ser"))) {
			out.writeObject(emp);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Deserialize the object
		Employee deserializedEmp = null;
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("employee.ser"))) {
			deserializedEmp = (Employee) in.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		System.out.println("Deserialized Employee: " + deserializedEmp);
	}
}

/*
 Notes:
 Serialization: Converts an object to a byte stream.
 Deserialization: Converts a byte stream back to an object.
 serialVersionUID: Ensures compatibility between serialized and deserialized objects, 
 serving as a version control mechanism.
 */
