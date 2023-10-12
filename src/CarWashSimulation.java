public class CarWashSimulation {

    private static final int NUM_LOCATIONS = 3;
    private static final int WASH_TIME = 240;
    private static final int MAX_WAIT_TIME = 600;

    private static Car[] carsWaiting = new Car[10];
    private static Car[] carsBeingWashed = new Car[NUM_LOCATIONS];

    public static void main(String[] args) {
        // Start the simulation loop
        while (true) {
            // Check if there are any cars waiting
            int waitingCars = 0;
            for (Car car : carsWaiting) {
                if (car != null) {
                    waitingCars++;
                }
            }

            // If there are no cars waiting, exit the simulation loop
            if (waitingCars == 0) {
                break;
            }

            // Check if there are any available car wash locations
            int availableLocations = 0;
            for (Car car : carsBeingWashed) {
                if (car == null) {
                    availableLocations++;
                }
            }

            // If there are no available locations, check if any of the waiting cars have been waiting too long
            if (availableLocations == 0) {
                for (int i = 0; i < carsWaiting.length; i++) {
                    Car car = carsWaiting[i];
                    if (car != null && car.getWaitTime() > MAX_WAIT_TIME) {
                        carsWaiting[i] = null;
                        System.out.println("Car " + car.getId() + " left the queue because the wait time was too long.");
                    }
                }
            }

            // If there are any available car wash locations, start washing the waiting cars
            for (int i = 0; i < availableLocations; i++) {
                Car car = carsWaiting[0];
                if (car != null) {
                    carsWaiting[0] = null;
                    carsBeingWashed[i] = car;

                    // Simulate the washing process by waiting for a fixed amount of time
                    try {
                        Thread.sleep(WASH_TIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    carsBeingWashed[i] = null;
                    System.out.println("Car " + car.getId() + " is finished washing.");
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
