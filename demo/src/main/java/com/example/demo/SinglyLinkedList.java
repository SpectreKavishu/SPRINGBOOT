package com.example.demo;

import com.example.demo.model.Node;

public class SinglyLinkedList {
	private Node head;
	
	public void display() {
		if (head == null) {
            System.out.println("List is empty");
            return;
        }
		Node current = head;
		while(current != null) {
			System.out.print(current.data + " -> ");
			current = current.next;
		}
		System.out.println("null");
	}
	
	public void insert(int data) {
		if(head == null) {
			head = new Node(data);
		} else {
			Node current = head;
			while(current.next != null) {
				current = current.next;
			}
			current.next = new Node(data);
		}
	}
	
	public void insertAtBegin(int data) {
		Node newNode = new Node(data);
		newNode.next = head;
		head = newNode;
	}
	
	public void delete(int data) {
		if (head == null) {
            System.out.println("List is empty");
            return;
        }
		
		// If head contains the matching data
        if (head.data == data) {
            head = head.next; // shift head to next
            return;
        }
        
        Node current = head;
        while (current.next != null && current.next.data != data) {
            current = current.next;
        }
        if (current.next == null) {
            System.out.println("Node not found");
        } else {
            current.next = current.next.next;
        }
		
	}
	
	public static void main(String[] args) {
		SinglyLinkedList obj = new SinglyLinkedList();
		obj.insert(10);
		obj.insert(20);
		obj.insertAtBegin(30);
		obj.insert(40);
		obj.display();
		obj.delete(30);
		obj.display();
	}
}
