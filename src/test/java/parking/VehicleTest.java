package parking;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VehicleTest {

    @Test
    void vehicleBasics() {
        Vehicle v = new Vehicle(1, VehicleType.CAR, 200.0);

        assertEquals(1, v.getVehicleId());
        assertEquals(VehicleType.CAR, v.getVehicleType());
        assertTrue(v.getBalance() > 0);
    }

    @Test
    void checkVehicleID() {
        Vehicle v = new Vehicle(10, VehicleType.BUS, 100.0);
        assertEquals(10, v.getVehicleId());
    }

    @Test
    void checkVehicleType() {
        Vehicle v = new Vehicle(10, VehicleType.BUS, 100.0);
        assertEquals(VehicleType.BUS, v.getVehicleType());
    }

    @Test
    void checkVehicleBalance() {
        Vehicle v = new Vehicle(10, VehicleType.BUS, 100.0);
        assertEquals(100.0, v.getBalance());
    }

    @Test
    void checkVehicleBoth() {
        Vehicle v = new Vehicle(2, VehicleType.MOTORCYCLE, 150.0);

        assertEquals(2, v.getVehicleId());
        assertEquals(VehicleType.MOTORCYCLE, v.getVehicleType());
        assertEquals(150.0, v.getBalance());
    }
    
    @Test
    void checkBalancChange() {
        Vehicle v = new Vehicle(1, VehicleType.CAR, 200.0);

        v.getWallet().addFunds(50.0);
        assertEquals(250.0, v.getBalance());
    }

    @Test
    void checkNullBalance() {
        // Defect
        Vehicle v = new Vehicle(4, VehicleType.CAR, (Wallet) null);
        assertThrows(NullPointerException.class, () -> {
            v.getBalance();
        });
    }

    @Test
    void checkNegativeBalance() {
        // Defect
        Vehicle v = new Vehicle(3, VehicleType.CAR, -50.0);
        assertEquals(-50.0, v.getBalance());
    }
}
