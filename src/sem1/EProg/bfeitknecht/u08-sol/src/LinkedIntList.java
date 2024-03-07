
/**
 * A singly-linked list of integer values with fast addFirst and addLast methods
 */
public class LinkedIntList {
    
    IntNode first;
    IntNode last;
    int size;
    
    /**
     * Return the integer value at position 'index'
     */
    int get(int index) {
        return getNode(index).value;
    }
    
    /**
     * Set the integer value at position 'index' to 'value'
     */
    void set(int index, int value) {
        getNode(index).value = value;
    }
    
    /**
     * Returns whether the list is empty (has no values)
     */
    boolean isEmpty() {
        return size == 0;
    }
    
    /**
     * Inserts 'value' at position 0 in the list.
     */
    void addFirst(int value) {
        IntNode newNode = new IntNode(value);
        newNode.next = first;
        first = newNode;
        if(last == null)
            last = newNode;
        size++;
    }
    
    /**
     * Appends 'value' at the end of the list.
     */
    void addLast(int value) {
        IntNode newNode = new IntNode(value);
        if(isEmpty())
            first = newNode;
        else
            last.next = newNode;
        
        last = newNode;
        size++;
    }
    
    /**
     * Removes and returns the first value of the list.
     */
    int removeFirst() {
        if(isEmpty()) {
        	Errors.error("removeFirst() on empty list!");
        }
        
        int value = first.value;
        if(first == last) {
            // List has only one element, so just clear it
            clear();
        }
        else {
            first = first.next;
            size--;
        }
        
        return value;
    }

    /**
     * Removes and returns the last value of the list.
     */
    int removeLast() {
        if(isEmpty()) {
            Errors.error("removeLast() on empty list!");
        }
        
        int value = last.value;
        if(first == last) {
            // List has only one element, so just clear it
            clear();
        }
        else {
            // List has more than one element
            IntNode currentNode = first;
            while(currentNode.next != last)
                currentNode = currentNode.next;
            
            currentNode.next = null;
            last = currentNode;
            size--;
        }
        return value;
    }
    
    /**
     * Removes all values from the list, making the list empty.
     */
    void clear() {
        first = last = null;
        size = 0;
    }
    
    /**
     * Returns a new int-array with the same contents as the list.
     */
    int[] toArray() {
        int[] array = new int[size];
        int i = 0;
        for(IntNode n = first; n != null; n = n.next, i++)
            array[i] = n.value;
        return array;
    }
    
    /**
     * For internal use only.
     */
    IntNode getNode(int index) {
        if(index < 0 || index >= size) {
            Errors.error("getNode() with invalid index: " + index);
        }
        
        IntNode current = first;
        for(int i = 0; i < index; i++)
            current = current.next;
        return current;
    }
}
