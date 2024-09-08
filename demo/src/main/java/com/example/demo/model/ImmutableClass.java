package com.example.demo.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class ImmutableClass {
	private final int id;
	private final String name;
	private final String description;
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	
	public static void main(String[] args) {
        // Create an instance of ImmutableClass
        ImmutableClass person = new ImmutableClass(1, "Kavishu", "Software Engineer");

        // Print the properties to verify
        System.out.println("Name: " + person.getName());
        System.out.println("Description: " + person.getDescription());

        // Attempting to modify the object
        // person.name = "Bob";
        // person.description = "tester";
    }
	
}
