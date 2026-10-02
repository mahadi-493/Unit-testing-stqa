package parking;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class ParkingSlotTest {

    @Test
    void slotBasics() {
        ParkingSlot p = new ParkingSlot("S1", ParkingSlotType.REGULAR);

        assertEquals("S1", p.getSlotId());
        assertEquals(ParkingSlotType.REGULAR, p.getSlotType());
    }

    @Test
    void checkSlotID() {
        ParkingSlot p = new ParkingSlot("S1", ParkingSlotType.REGULAR);

        assertEquals("S1", p.getSlotId());
    }

    @Test
    void checkSlotType() {
        ParkingSlot p = new ParkingSlot("S2", ParkingSlotType.COMPACT);

        assertEquals(ParkingSlotType.COMPACT, p.getSlotType());
    }

    @Test
    void checkSlotBalance() {
        ParkingSlot p = new ParkingSlot("S3", ParkingSlotType.LARGE);
        
        assertEquals(0.0, p.getBalance());
    }

    @Test
    void checkSlotBookings() {
        ParkingSlot p = new ParkingSlot("S4", ParkingSlotType.HANDICAPPED);

        assertEquals(0, p.getBookings().size());
    }

    @Test
    void checkDeactivate() {
        ParkingSlot p = new ParkingSlot("S1", ParkingSlotType.REGULAR);

        p.deactivate();
        assertFalse(p.isActive());
    }

    @Test
    void checkDeactiveThenActivate() {
        ParkingSlot p = new ParkingSlot("S1", ParkingSlotType.REGULAR);

        p.deactivate();
        p.activate();
        assertTrue(p.isActive());
    }

    @Test
    void checkInactiveSlot() {
        ParkingSlot p = new ParkingSlot("S1", ParkingSlotType.REGULAR);

        LocalDateTime start = LocalDateTime.of(2026, 10, 2, 10, 0);
        LocalDateTime end = start.plusHours(2);

        p.deactivate();

        assertFalse(p.isCompatible(VehicleType.CAR, start, end));
        assertFalse(p.isCompatible(VehicleType.MOTORCYCLE, start, end));
    }

    @Test
    void checkMotorcycle() {
        ParkingSlot s1 = new ParkingSlot("S1", ParkingSlotType.COMPACT);
        ParkingSlot s2 = new ParkingSlot("S2", ParkingSlotType.REGULAR);

        LocalDateTime start = LocalDateTime.of(2026, 10, 2, 10, 0);
        LocalDateTime end = start.plusHours(2);

        assertTrue(s1.isCompatible(VehicleType.MOTORCYCLE, start, end));
        assertTrue(s2.isCompatible(VehicleType.MOTORCYCLE, start, end));
    }

    @Test
    void checkCar() {
        ParkingSlot s1 = new ParkingSlot("S1", ParkingSlotType.COMPACT);
        ParkingSlot s2 = new ParkingSlot("S2", ParkingSlotType.REGULAR);
        ParkingSlot s3 = new ParkingSlot("S3", ParkingSlotType.LARGE);
        ParkingSlot s4 = new ParkingSlot("S4", ParkingSlotType.HANDICAPPED);

        LocalDateTime start = LocalDateTime.of(2026, 10, 2, 10, 0);
        LocalDateTime end = start.plusHours(2);

        // Compatible slots
        assertTrue(s2.isCompatible(VehicleType.CAR, start, end));
        assertTrue(s3.isCompatible(VehicleType.CAR, start, end));

        // Defect: Incompatible slots throws error
        // assertTrue(s1.isCompatible(VehicleType.CAR, start, end));
        // assertTrue(s4.isCompatible(VehicleType.CAR, start, end));
    }

    @Test
    void checkBus() {
        ParkingSlot s1 = new ParkingSlot("S1", ParkingSlotType.COMPACT);
        ParkingSlot s2 = new ParkingSlot("S2", ParkingSlotType.REGULAR);
        ParkingSlot s3 = new ParkingSlot("S3", ParkingSlotType.LARGE);
        ParkingSlot s4 = new ParkingSlot("S4", ParkingSlotType.HANDICAPPED);

        LocalDateTime start = LocalDateTime.of(2026, 10, 2, 10, 0);
        LocalDateTime end = start.plusHours(2);

        // Compatible slots
        assertTrue(s3.isCompatible(VehicleType.BUS, start, end));

        // Defect: Incompatible slots throws error
        // assertTrue(s1.isCompatible(VehicleType.BUS, start, end)); 
        // assertTrue(s2.isCompatible(VehicleType.BUS, start, end));
        // assertTrue(s4.isCompatible(VehicleType.BUS, start, end));
    }

    @Test
    void checkBicycle() {
        ParkingSlot s1 = new ParkingSlot("S1", ParkingSlotType.COMPACT);
        ParkingSlot s2 = new ParkingSlot("S2", ParkingSlotType.REGULAR);
        ParkingSlot s3 = new ParkingSlot("S3", ParkingSlotType.LARGE);
        ParkingSlot s4 = new ParkingSlot("S4", ParkingSlotType.HANDICAPPED);

        LocalDateTime start = LocalDateTime.of(2026, 10, 2, 10, 0);
        LocalDateTime end = start.plusHours(2);

        assertTrue(s1.isCompatible(VehicleType.BICYCLE, start, end));
        assertTrue(s2.isCompatible(VehicleType.BICYCLE, start, end));
        assertTrue(s3.isCompatible(VehicleType.BICYCLE, start, end));
        assertTrue(s4.isCompatible(VehicleType.BICYCLE, start, end));
    }

    @Test
    void checkMicrocar() {
        // D6: Missing break in case MICROCAR causes fallthrough to default
        ParkingSlot s1 = new ParkingSlot("S1", ParkingSlotType.COMPACT);
        ParkingSlot s2 = new ParkingSlot("S2", ParkingSlotType.REGULAR);
        ParkingSlot s3 = new ParkingSlot("S3", ParkingSlotType.LARGE);
        ParkingSlot s4 = new ParkingSlot("S4", ParkingSlotType.HANDICAPPED);

        LocalDateTime start = LocalDateTime.of(2026, 10, 2, 10, 0);
        LocalDateTime end = start.plusHours(2);

        // Compatible slots
        assertTrue(s1.isCompatible(VehicleType.MICROCAR, start, end));
        assertTrue(s2.isCompatible(VehicleType.MICROCAR, start, end));

        // Defect: Incompatible slots throws error
        // assertTrue(s3.isCompatible(VehicleType.MICROCAR, start, end));
        // assertTrue(s4.isCompatible(VehicleType.MICROCAR, start, end));
    }

    @Test
    void checkTruck() {
        ParkingSlot s1 = new ParkingSlot("S1", ParkingSlotType.COMPACT);
        ParkingSlot s2 = new ParkingSlot("S2", ParkingSlotType.REGULAR);
        ParkingSlot s3 = new ParkingSlot("S3", ParkingSlotType.LARGE);
        ParkingSlot s4 = new ParkingSlot("S4", ParkingSlotType.HANDICAPPED);

        LocalDateTime start = LocalDateTime.of(2026, 10, 2, 10, 0);
        LocalDateTime end = start.plusHours(2);

        assertFalse(s1.isCompatible(VehicleType.TRUCK, start, end));
        assertFalse(s2.isCompatible(VehicleType.TRUCK, start, end));
        assertFalse(s3.isCompatible(VehicleType.TRUCK, start, end));
        assertFalse(s4.isCompatible(VehicleType.TRUCK, start, end));
    }

    @Test
    void checkSlotAvailablity() {
        ParkingSlot s1 = new ParkingSlot("S1", ParkingSlotType.REGULAR);

        LocalDateTime start = LocalDateTime.of(2026, 10, 2, 10, 0);
        LocalDateTime end = start.plusHours(2);

        assertTrue(s1.isAvailable(start, end));
    }

    @Test
    void checkSlotAvailablityBeforeAndAfter() {
        ParkingSlot p = new ParkingSlot("S1", ParkingSlotType.REGULAR);

        LocalDateTime start = LocalDateTime.of(2026, 10, 2, 10, 0);
        LocalDateTime end = start.plusHours(2);

        Vehicle v = new Vehicle(1, VehicleType.CAR, 100.0);
        Booking b = new Booking(1, v, p, start, end, 20.0);

        p.getBookings().add(b);

        assertTrue(p.isAvailable(start.minusHours(2), start));
        assertTrue(p.isAvailable(end, end.plusHours(2)));
    }

    @Test
    void checkSlotAvailablityOverlap() {
        ParkingSlot p = new ParkingSlot("S1", ParkingSlotType.REGULAR);

        LocalDateTime start = LocalDateTime.of(2026, 10, 2, 10, 0);
        LocalDateTime end = start.plusHours(2);

        Vehicle v = new Vehicle(1, VehicleType.CAR, 100.0);
        Booking b = new Booking(1, v, p, start, end, 20.0);

        p.getBookings().add(b);

        assertFalse(p.isAvailable(start.minusHours(1), start.plusHours(1)));
        assertFalse(p.isAvailable(start.plusHours(1), end.plusHours(1)));
        assertFalse(p.isAvailable(start.plusMinutes(30), end.minusMinutes(30)));
    }

    @Test
    void cancelledBookinSlotBlock() {
        // Defect
        ParkingSlot p = new ParkingSlot("S1", ParkingSlotType.REGULAR);

        LocalDateTime start = LocalDateTime.of(2026, 10, 2, 10, 0);
        LocalDateTime end = start.plusHours(2);

        Vehicle v = new Vehicle(1, VehicleType.CAR, 100.0);
        Booking b = new Booking(1, v, p, start, end, 20.0);

        p.getBookings().add(b);

        b.cancelBooking();
        assertFalse(p.isAvailable(start, end));
    }
}
