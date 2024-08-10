import java.util.Queue;

public class Restaurant extends Thread {
    private ArrayList<Waiter> waiters = new ArrayList<Waiter>();
    private Queue<Customer> customers = new LinkedList<>();
    private final int customerLimit = 50;

    public Restaurant(ArrayList<Waiter> waiters) {
        this.waiters = waiters;
    }

    // Das Restaurant wiederholt undendlich lange:
    // 1. Wartet, falls die Restaurantkapazit ̈at erreicht ist
    // 2. F ̈ugt Kunden an das Ende der Queue an
    // 3. Benachrichtigt Kellner  ̈uber hinzugef ̈ugte Kunden
    /* The Restaurant repeats forever:
        1. Waits if the restaurant capacity is full
        2. Adds customers to the back of the queue
        3. Notifies waiters about newly added customers */
    public void run() {
        while(true) {
            synchronized(this){
            // 1.
            while (customers.size() == customerLimit) {
                wait();
            }
            // 2.
            customers.add(new Customer());
            // 3.
            notifyAll();
        }
    }
    }

    public synchronized Customer getCustomer() {
        return customers.remove();
    }

    // Wird von Kellner-Threads aufgerufen.
    /* Should be called by the waiter threads. */
    public synchronized boolean shouldWaiterServe(Waiter waiter) {
        // No need to lock individual waiters, as synchronized locks the whole restaurant
        if (customers.size() <= 0)
            return false;
        for (int i = 0; i < waiters.size(); i++) {
            if (waiters.get(i).getServedCustomers < waiter.getServedCustomers())
                return false;
        }
        return true;
    }
}

public class Waiter extends Thread {
    private Restaurant restaurant;
    private int servedCustomers = 0;

    public Waiter(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
    
    // Ein Kellner wiederholt undendlich lange:
    // 1. Wartet, falls es keine Kunden zu bedienen gibt ODER
    //    falls er mehr Kunden bedient hat als andere Kellner.
    // 2. Bedient den n ̈achsten Kunden in der Queue
    /* A waiter repeats forever:
        1. Waits if there are no customers to serve OR
           if they have served more customers than another waiter.
        2. Serves the next customer in the customer queue */
    public void run() {
        while (true) {
            synchronized(restaurant) {
            // 1.
            while (!restaurant.shouldWaiterServe(this)) {
                restaurant.wait();
            }
            // 2.
            serveCustomer(restaurant.getCustomer());
            restaurant.notifyAll();
        }
      }
    }

    public int getServedCustomers() {
        return servedCustomers;
    }
    private void serveCustomer(Customer customer) {
        servedCustomers++;
        System.out.println("Served Customer: " + customer);
    }
}

public class Customer {
    public Customer() {}
}
