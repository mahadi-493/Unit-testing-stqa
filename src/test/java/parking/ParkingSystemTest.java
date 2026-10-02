package parking;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ParkingSystemTest {

    @Test
    void parkingSystemBasics() {
        ParkingSystem ps = ParkingSystem.getInstance();
        ps.resetForTesting();

        assertEquals(0, ps.getVehicles().size());
        assertEquals(0, ps.getParkingSlots().size());
        assertEquals(0, ps.getBookings().size());
        assertEquals(10.0, ps.getPARKING_RATE_PER_HOUR());
        assertEquals(0.0, ps.getBalance());
    }

    @Test
    void checkSameInstance() {
        ParkingSystem ps1 = ParkingSystem.getInstance();
        ParkingSystem ps2 = ParkingSystem.getInstance();

        assertSame(ps1, ps2);
    }

    @Test
    void checkAddVehicle() {
        ParkingSystem ps = ParkingSystem.getInstance();
        ps.resetForTesting();

        Vehicle v = new Vehicle(1, VehicleType.CAR, 100.0);
        ps.addVehicle(v);

        assertEquals(1, ps.getVehicles().size());
        assertEquals(v, ps.getVehicles().get(0));
    }

    @Test
    void checkAddSlot() {
        ParkingSystem ps = ParkingSystem.getInstance();
        ps.resetForTesting();

        ParkingSlot slot = new ParkingSlot("S1", ParkingSlotType.REGULAR);
        ps.addParkingSlot(slot);

        assertEquals(1, ps.getParkingSlots().size());
        assertEquals(slot, ps.getParkingSlots().get(0));
    }

    @Test
    void checkRate() {
        ParkingSystem ps = ParkingSystem.getInstance();
        ps.resetForTesting();

        ps.setPARKING_RATE_PER_HOUR(15.0);
        assertEquals(15.0, ps.getPARKING_RATE_PER_HOUR());
    }

    @Test
    void checkWallet() {
        ParkingSystem ps = ParkingSystem.getInstance();
        ps.resetForTesting();

        Wallet w = new Wallet(50.0);
        ps.setSYSTEM_WALLET(w);

        assertEquals(w, ps.getSYSTEM_WALLET());
        assertEquals(50.0, ps.getBalance());
    }

    @Test
    void checkAvailableSlots() {
        ParkingSystem ps = ParkingSystem.getInstance();
        ps.resetForTesting();

        ParkingSlot s1 = new ParkingSlot("S1", ParkingSlotType.REGULAR);
        ParkingSlot s2 = new ParkingSlot("S2", ParkingSlotType.COMPACT);
        ParkingSlot s3 = new ParkingSlot("S3", ParkingSlotType.REGULAR);
        s3.deactivate();

        ps.addParkingSlot(s1);
        ps.addParkingSlot(s2);
        ps.addParkingSlot(s3);

        LocalDateTime start = LocalDateTime.of(2026, 10, 2, 10, 0);
        LocalDateTime end = start.plusHours(2);

        Vehicle v = new Vehicle(1, VehicleType.CAR, 100.0);
        List<ParkingSlot> list = ps.getAvailableParkingSlots(v, start, end);

        assertEquals(1, list.size());
        assertEquals("S1", list.get(0).getSlotId());
    }

    @Test
    void checkEndBeforeStart() {
        ParkingSystem ps = ParkingSystem.getInstance();
        ps.resetForTesting();

        Vehicle v = new Vehicle(1, VehicleType.CAR, 100.0);
        ParkingSlot slot = new ParkingSlot("S1", ParkingSlotType.REGULAR);

        LocalDateTime start = LocalDateTime.of(2026, 10, 2, 10, 0);
        LocalDateTime end = start.plusHours(2);

        assertThrows(IllegalBookingTimeException.class, () -> {
            ps.book(v, slot, end, start);
        });
    }

    @Test
    void checkEndEqualStart() {
        ParkingSystem ps = ParkingSystem.getInstance();
        ps.resetForTesting();

        Vehicle v = new Vehicle(1, VehicleType.CAR, 100.0);
        ParkingSlot slot = new ParkingSlot("S1", ParkingSlotType.REGULAR);

        LocalDateTime start = LocalDateTime.of(2026, 10, 2, 10, 0);

        assertThrows(IllegalBookingTimeException.class, () -> {
            ps.book(v, slot, start, start);
        });
    }

    @Test
    void checkIncompatibleSlot() {
        // Defect D7: Throws standard IllegalArgumentException instead of custom IllegalBookingArgumentException
        ParkingSystem ps = ParkingSystem.getInstance();
        ps.resetForTesting();

        Vehicle v = new Vehicle(1, VehicleType.CAR, 100.0);
        ParkingSlot slot = new ParkingSlot("C1", ParkingSlotType.COMPACT);

        LocalDateTime start = LocalDateTime.of(2026, 10, 2, 10, 0);
        LocalDateTime end = start.plusHours(2);

        assertThrows(IllegalArgumentException.class, () -> {
            ps.book(v, slot, start, end);
        });
    }

    @Test
    void bookCarRegular() {
        ParkingSystem ps = ParkingSystem.getInstance();
        ps.resetForTesting();

        Vehicle v = new Vehicle(1, VehicleType.CAR, 100.0);
        ParkingSlot slot = new ParkingSlot("S1", ParkingSlotType.REGULAR);

        LocalDateTime start = LocalDateTime.of(2026, 10, 2, 10, 0);
        LocalDateTime end = start.plusHours(2);

        Booking b = ps.book(v, slot, start, end);

        assertEquals(1, b.getBookingId());
        assertEquals(20.0, b.getAmount());
        assertEquals(BookingStatus.ACTIVE, b.getBookingStatus());
    }

    @Test
    void bookDurationUnderOneHour() {
        // Defect
        ParkingSystem ps = ParkingSystem.getInstance();
        ps.resetForTesting();

        Vehicle v = new Vehicle(1, VehicleType.CAR, 100.0);
        ParkingSlot slot = new ParkingSlot("S1", ParkingSlotType.REGULAR);

        LocalDateTime start = LocalDateTime.of(2026, 10, 2, 10, 0);
        LocalDateTime halfHour = start.plusMinutes(30);

        assertThrows(InvalidAmountException.class, () -> {
            ps.book(v, slot, start, halfHour);
        });
    }

    @Test
    void bookMotorcycleCompact() {
        ParkingSystem ps = ParkingSystem.getInstance();
        ps.resetForTesting();

        Vehicle v = new Vehicle(2, VehicleType.MOTORCYCLE, 50.0);
        ParkingSlot slot = new ParkingSlot("C1", ParkingSlotType.COMPACT);

        LocalDateTime start = LocalDateTime.of(2026, 10, 2, 10, 0);
        LocalDateTime end = start.plusHours(2);

        Booking b = ps.book(v, slot, start, end);
        assertEquals(8.0, b.getAmount());
    }

    @Test
    void bookBusLarge() {
        ParkingSystem ps = ParkingSystem.getInstance();
        ps.resetForTesting();

        Vehicle v = new Vehicle(3, VehicleType.BUS, 200.0);
        ParkingSlot slot = new ParkingSlot("L1", ParkingSlotType.LARGE);

        LocalDateTime start = LocalDateTime.of(2026, 10, 2, 10, 0);
        LocalDateTime end = start.plusHours(2);

        Booking b = ps.book(v, slot, start, end);
        assertEquals(60.0, b.getAmount());
    }

    @Test
    void bookInsufficientFunds() {
        // Defect
        ParkingSystem ps = ParkingSystem.getInstance();
        ps.resetForTesting();

        Vehicle v = new Vehicle(1, VehicleType.CAR, 5.0);
        ParkingSlot slot = new ParkingSlot("S1", ParkingSlotType.REGULAR);

        LocalDateTime start = LocalDateTime.of(2026, 10, 2, 10, 0);
        LocalDateTime end = start.plusHours(2);

        assertThrows(InsufficientFundsException.class, () -> {
            ps.book(v, slot, start, end);
        });
        assertEquals(1, ps.getBookings().size());
    }

    @Test
    void checkBookingComplete() {
        ParkingSystem ps = ParkingSystem.getInstance();
        ps.resetForTesting();

        Vehicle v = new Vehicle(1, VehicleType.CAR, 100.0);
        ParkingSlot slot = new ParkingSlot("S1", ParkingSlotType.REGULAR);

        LocalDateTime start = LocalDateTime.of(2026, 10, 2, 10, 0);
        LocalDateTime end = start.plusHours(2);

        Booking b = ps.book(v, slot, start, end);
        ps.completeBooking(b);

        assertEquals(BookingStatus.COMPLETED, b.getBookingStatus());
        assertEquals(16.0, slot.getBalance());
        assertEquals(4.0, ps.getBalance());
    }

    @Test
    void completeBookingMultipleTimes() {
        // Defect
        ParkingSystem ps = ParkingSystem.getInstance();
        ps.resetForTesting();

        Vehicle v = new Vehicle(1, VehicleType.CAR, 100.0);
        ParkingSlot slot = new ParkingSlot("S1", ParkingSlotType.REGULAR);

        LocalDateTime start = LocalDateTime.of(2026, 10, 2, 10, 0);
        LocalDateTime end = start.plusHours(2);

        Booking b = ps.book(v, slot, start, end);
        ps.completeBooking(b);

        assertThrows(InsufficientFundsException.class, () -> {
            ps.completeBooking(b);
        });
    }

    @Test
    void checkBookingCancel() {
        ParkingSystem ps = ParkingSystem.getInstance();
        ps.resetForTesting();

        Vehicle v = new Vehicle(1, VehicleType.CAR, 100.0);
        ParkingSlot slot = new ParkingSlot("S1", ParkingSlotType.REGULAR);

        LocalDateTime start = LocalDateTime.of(2026, 10, 2, 10, 0);
        LocalDateTime end = start.plusHours(2);

        Booking b = ps.book(v, slot, start, end);
        ps.cancelBooking(b);

        assertEquals(BookingStatus.CANCELLED, b.getBookingStatus());
        assertEquals(98.0, v.getBalance());
        assertEquals(2.0, ps.getBalance());
    }

    @Test
    void cancelBookingMultipleTimes() {
        // Defect
        ParkingSystem ps = ParkingSystem.getInstance();
        ps.resetForTesting();

        Vehicle v = new Vehicle(1, VehicleType.CAR, 100.0);
        ParkingSlot slot = new ParkingSlot("S1", ParkingSlotType.REGULAR);

        LocalDateTime start = LocalDateTime.of(2026, 10, 2, 10, 0);
        LocalDateTime end = start.plusHours(2);

        Booking b = ps.book(v, slot, start, end);
        ps.cancelBooking(b);

        assertThrows(InsufficientFundsException.class, () -> {
            ps.cancelBooking(b);
        });
    }
}
