package com.example.demo.service;

import java.io.*;

class User implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private transient String password; // Transient field

	public User(String name, String password) {
		this.name = name;
		this.password = password;
	}

	@Override
	public String toString() {
		return "User{name='" + name + "', password='" + password + "'}";
	}
}

public class TransientExample {
	public static void main(String[] args) {
		User user = new User("JohnDoe", "secret123");

		// Serialize the object
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("user.ser"))) {
			out.writeObject(user);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Deserialize the object
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("user.ser"))) {
			User deserializedUser = (User) in.readObject();
			System.out.println("Deserialized User: " + deserializedUser);
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
