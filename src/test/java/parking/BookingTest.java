package parking;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class BookingTest {

    @Test
    void bookingBasics() {
        Vehicle v = new Vehicle(1, VehicleType.CAR, 200.0);
        ParkingSlot p = new ParkingSlot("S1", ParkingSlotType.REGULAR);

        LocalDateTime start = LocalDateTime.of(2026, 10, 2, 10, 0);
        LocalDateTime end = start.plusHours(2);

        Booking b = new Booking(1, v, p, start, end, 20.0);

        assertEquals(20.0, b.getAmount());
        assertEquals(BookingStatus.ACTIVE, b.getBookingStatus());
    }

    @Test
    void checkID() {
        Vehicle v = new Vehicle(1, VehicleType.CAR, 200.0);
        ParkingSlot p = new ParkingSlot("S1", ParkingSlotType.REGULAR);

        LocalDateTime start = LocalDateTime.of(2026, 10, 2, 10, 0);
        LocalDateTime end = start.plusHours(2);

        Booking b = new Booking(1, v, p, start, end, 20.0);

        assertEquals(1, b.getBookingId());
    }

    @Test
    void checkVehicle() {
        Vehicle v = new Vehicle(2, VehicleType.MOTORCYCLE, 50.0);
        ParkingSlot p = new ParkingSlot("S2", ParkingSlotType.COMPACT);

        LocalDateTime start = LocalDateTime.of(2026, 10, 2, 10, 0);
        LocalDateTime end = start.plusHours(2);

        Booking b = new Booking(1, v, p, start, end, 20.0);

        assertEquals(v, b.getVehicle());
    }

    @Test
    void checkSlot() {
        Vehicle v = new Vehicle(2, VehicleType.MOTORCYCLE, 50.0);
        ParkingSlot p = new ParkingSlot("S2", ParkingSlotType.COMPACT);

        LocalDateTime start = LocalDateTime.of(2026, 10, 2, 10, 0);
        LocalDateTime end = start.plusHours(2);

        Booking b = new Booking(1, v, p, start, end, 20.0);

        assertEquals(p, b.getParkingSlot());
    }

    @Test
    void checkSlotVehicleBoth() {
        Vehicle v = new Vehicle(2, VehicleType.MOTORCYCLE, 50.0);
        ParkingSlot p = new ParkingSlot("S2", ParkingSlotType.COMPACT);

        LocalDateTime start = LocalDateTime.of(2026, 10, 2, 10, 0);
        LocalDateTime end = start.plusHours(2);

        Booking b = new Booking(1, v, p, start, end, 20.0);

        assertEquals(v, b.getVehicle());
        assertEquals(p, b.getParkingSlot());
    }

    @Test
    void checkTime() {
        Vehicle v = new Vehicle(1, VehicleType.CAR, 200.0);
        ParkingSlot p = new ParkingSlot("S1", ParkingSlotType.REGULAR);

        LocalDateTime start = LocalDateTime.of(2026, 10, 2, 10, 0);
        LocalDateTime end = start.plusHours(2);

        Booking b = new Booking(1, v, p, start, end, 20.0);

        assertEquals(start, b.getStartTime());
        assertEquals(end, b.getEndTime());
    }

    @Test
    void checkNegativeAmount() {
        // Defect
        Vehicle v = new Vehicle(1, VehicleType.CAR, 200.0);
        ParkingSlot slot = new ParkingSlot("S1", ParkingSlotType.REGULAR);
        
        LocalDateTime start = LocalDateTime.of(2026, 10, 2, 10, 0);
        LocalDateTime end = start.plusHours(2);

        Booking b = new Booking(1, v, slot, start, end, -50.0);

        assertEquals(-50.0, b.getAmount());
    }

    @Test
    void checkBookingComplete() {
        Vehicle v = new Vehicle(1, VehicleType.CAR, 200.0);
        ParkingSlot slot = new ParkingSlot("S1", ParkingSlotType.REGULAR);

        LocalDateTime start = LocalDateTime.of(2026, 10, 2, 10, 0);
        LocalDateTime end = start.plusHours(2);

        Booking b = new Booking(10, v, slot, start, end, 30.0);

        b.completeBooking();
        assertEquals(BookingStatus.COMPLETED, b.getBookingStatus());
    }

    @Test
    void checkBookingCancel() {
        Vehicle v = new Vehicle(1, VehicleType.CAR, 200.0);
        ParkingSlot slot = new ParkingSlot("S1", ParkingSlotType.REGULAR);
        
        LocalDateTime start = LocalDateTime.of(2026, 10, 2, 10, 0);
        LocalDateTime end = start.plusHours(2);

        Booking b = new Booking(11, v, slot, start, end, 30.0);

        b.cancelBooking();
        assertEquals(BookingStatus.CANCELLED, b.getBookingStatus());
    }

    @Test
    void completeBookingCancelled() {
        // Defect
        Vehicle v = new Vehicle(1, VehicleType.CAR, 200.0);
        ParkingSlot slot = new ParkingSlot("S1", ParkingSlotType.REGULAR);
        
        LocalDateTime start = LocalDateTime.of(2026, 10, 2, 10, 0);
        LocalDateTime end = start.plusHours(2);

        Booking b = new Booking(12, v, slot, start, end, 20.0);

        b.cancelBooking();
        b.completeBooking();
        assertEquals(BookingStatus.COMPLETED, b.getBookingStatus());
    }

    @Test
    void cancelBookingCompleted() {
        // Defect
        Vehicle v = new Vehicle(1, VehicleType.CAR, 200.0);
        ParkingSlot slot = new ParkingSlot("S1", ParkingSlotType.REGULAR);
        
        LocalDateTime start = LocalDateTime.of(2026, 10, 2, 10, 0);
        LocalDateTime end = start.plusHours(2);

        Booking b = new Booking(13, v, slot, start, end, 20.0);

        b.completeBooking();
        b.cancelBooking();
        assertEquals(BookingStatus.CANCELLED, b.getBookingStatus());
    }
}