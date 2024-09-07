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
		while (current != null) {
			System.out.print(current.data + " -> ");
			current = current.next;
		}
		System.out.println("null");
	}

	public void insert(int data) {
		if (head == null) {
			head = new Node(data);
		} else {
			Node current = head;
			while (current.next != null) {
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

	// Reverse the linked list
	public void reverse() {
		Node previous = null;
		Node current = head;
		Node next = null;
		while (current != null) {
			next = current.next; // Store next node
			current.next = previous; // Reverse the link
			previous = current; // Move previous to current
			current = next; // Move to next node
		}
		head = previous; // Update the head to the last node
	}

	// Detect if there's a cycle in the list
	public boolean detectCycle() {
		Node slow = head;
		Node fast = head;
		while (fast != null && fast.next != null) {
			slow = slow.next;
			fast = fast.next.next;
			if (slow == fast) {
				return true; // Cycle detected
			}
		}
		return false; // No cycle
	}

	// Create a cycle for testing purposes
	public void createCycle() {
		if (head == null || head.next == null)
			return;
		Node current = head;
		while (current.next != null) {
			current = current.next;
		}
		current.next = head.next; // Create a cycle by linking last node to second node
	}
	
	// Merge two sorted linked lists
    public static SinglyLinkedList merge(SinglyLinkedList list1, SinglyLinkedList list2) {
        Node dummy = new Node(0);  // Dummy node to simplify merging
        Node tail = dummy;
        Node head1 = list1.head;
        Node head2 = list2.head;

        while (head1 != null && head2 != null) {
            if (head1.data <= head2.data) {
                tail.next = head1;
                head1 = head1.next;
            } else {
                tail.next = head2;
                head2 = head2.next;
            }
            tail = tail.next;
        }

        // Append the remaining nodes from either list
        if (head1 != null) {
            tail.next = head1;
        } else {
            tail.next = head2;
        }

        SinglyLinkedList mergedList = new SinglyLinkedList();
        mergedList.head = dummy.next;  // Skip the dummy node
        return mergedList;
    }

	public static void main(String[] args) {
		SinglyLinkedList obj = new SinglyLinkedList();
		SinglyLinkedList obj2 = new SinglyLinkedList();
		obj.insert(20);
		obj.insert(30);
		obj.display();

		obj.insertAtBegin(10);
		obj.insert(40);
		obj.display();
		
		obj2.insert(2);
		obj2.insert(3);
		obj2.display();
		
		obj2.insertAtBegin(1);
		obj2.insert(4);
		obj2.display();
		
		SinglyLinkedList.merge(obj, obj2).display();

		// obj.delete(30);
		// obj.display();

		obj.reverse();
		obj.display();

		obj.createCycle();
		System.out.println(obj.detectCycle());
	}
}
