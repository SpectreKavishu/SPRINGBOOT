package com.example.demo;

import com.example.demo.model.DoublyNode;

public class DoublyLinkedList {
	private DoublyNode head;

	public void insert(int data) {
		DoublyNode newNode = new DoublyNode(data);
		if (head == null) {
			head = newNode;
		} else {
			DoublyNode current = head;
			while (current.next != null) {
				current = current.next;
			}
			current.next = newNode;
			newNode.prev = current;
		}
	}
	
	public void insertAtBeginning(int data) {
        DoublyNode newNode = new DoublyNode(data);
        if (head != null) {
            head.prev = newNode;
        }
        newNode.next = head;
        head = newNode;
    }
	
	public void displayForward() {
        if (head == null) {
            System.out.println("List is empty");
            return;
        }

        DoublyNode current = head;
        while (current != null) {
            System.out.print(current.data + " <-> ");
            current = current.next;
        }
        System.out.println("null");
    }
	
	public void displayBackward() {
        if (head == null) {
            System.out.println("List is empty");
            return;
        }

        // Go to the last node
        DoublyNode current = head;
        while (current.next != null) {
            current = current.next;
        }

        // Traverse backward
        while (current != null) {
            System.out.print(current.data + " <-> ");
            current = current.prev;
        }
        System.out.println("null");
    }
	
	public void delete(int data) {
        if (head == null) {
            System.out.println("List is empty");
            return;
        }

        // If head contains the data
        if (head.data == data) {
            head = head.next;
            if (head != null) {
                head.prev = null;
            }
            return;
        }

        DoublyNode current = head;
        while (current.next != null && current.next.data != data) {
            current = current.next;
        }

        if (current.next == null) {
            System.out.println("Node not found");
        } else {
            current.next = current.next.next;
            if (current.next != null) {
                current.next.prev = current;
            }
        }
    }
	
	public static void main(String args[]) {
		DoublyLinkedList obj = new DoublyLinkedList();
		obj.insert(20);
		obj.insert(30);
		obj.displayForward();
		
		obj.insertAtBeginning(10);
		obj.displayForward();
		obj.displayBackward();
		
		obj.delete(20);
		obj.displayForward();
		obj.displayBackward();
	}
}
