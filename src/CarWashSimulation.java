import java.util.LinkedList;
import java.util.Queue;

public class CarWashSimulation {

    private static final int NUM_LOCATIONS = 3;
    private static final int WASH_TIME = 240;
    private static final int MAX_WAIT_TIME = 600;

    private static Queue<Car> queue = new LinkedList<>();

    public static void main(String[] args) {
        // Create an array to store the status of each car wash location
        boolean[] locationStatus = new boolean[NUM_LOCATIONS];
        for (int i = 0; i < NUM_LOCATIONS; i++) {
            locationStatus[i] = false;
        }

        // Start the simulation loop
        while (true) {
            // Check if there are any cars waiting in the queue
            if (!queue.isEmpty()) {
                // Find an available car wash location
                int availableLocation = -1;
                for (int i = 0; i < NUM_LOCATIONS; i++) {
                    if (!locationStatus[i]) {
                        availableLocation = i;
                        break;
                    }
                }

                // If there is an available location, start washing the car
                if (availableLocation != -1) {
                    Car car = queue.remove();
                    locationStatus[availableLocation] = true;
                    int finalAvailableLocation = availableLocation;
                    new Thread(() -> {
                        try {
                            Thread.sleep(WASH_TIME);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        locationStatus[finalAvailableLocation] = false;
                        System.out.println("Car " + car.getId() + " is finished washing.");
                    }).start();
                } else {
                    // If there are no available locations, check if the car has been waiting too long
                    Car car = queue.peek();
                    if (car.getWaitTime() > MAX_WAIT_TIME) {
                        queue.remove();
                        System.out.println("Car " + car.getId() + " left the queue because the wait time was too long.");
                    }
                }
            } else {
                // If there are no cars waiting in the queue, sleep for a bit
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class Car {
        private int id;
        private long arrivalTime;

        public Car(int id) {
            this.id = id;
            this.arrivalTime = System.currentTimeMillis();
        }

        public int getId() {
            return id;
        }

        public long getWaitTime() {
            return System.currentTimeMillis() - arrivalTime;
        }
    }
}
