

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SimpleHotelReservationSystem {

    // Room Class
    static class Room {
        private int roomNumber;
        private String category; // e.g., "Single", "Double", "Suite"
        private boolean isAvailable;

        public Room(int roomNumber, String category, boolean isAvailable) {
            this.roomNumber = roomNumber;
            this.category = category;
            this.isAvailable = isAvailable;
        }

        public int getRoomNumber() {
            return roomNumber;
        }

        public String getCategory() {
            return category;
        }

        public boolean isAvailable() {
            return isAvailable;
        }

        public void setAvailable(boolean available) {
            isAvailable = available;
        }

        @Override
        public String toString() {
            return "Room " + roomNumber + " (" + category + ") - " + (isAvailable ? "Available" : "Not Available");
        }
    }

    // Reservation Class
    static class Reservation {
        private int reservationId;
        private Room room;
        private String guestName;
        private LocalDate checkInDate;
        private LocalDate checkOutDate;

        public Reservation(int reservationId, Room room, String guestName, LocalDate checkInDate, LocalDate checkOutDate) {
            this.reservationId = reservationId;
            this.room = room;
            this.guestName = guestName;
            this.checkInDate = checkInDate;
            this.checkOutDate = checkOutDate;
        }

        public int getReservationId() {
            return reservationId;
        }

        public Room getRoom() {
            return room;
        }

        public String getGuestName() {
            return guestName;
        }

        public LocalDate getCheckInDate() {
            return checkInDate;
        }

        public LocalDate getCheckOutDate() {
            return checkOutDate;
        }

        @Override
        public String toString() {
            return "Reservation ID: " + reservationId + "\n" +
                   "Room: " + room + "\n" +
                   "Guest Name: " + guestName + "\n" +
                   "Check-in Date: " + checkInDate + "\n" +
                   "Check-out Date: " + checkOutDate;
        }
    }

    // Hotel Class
    static class Hotel {
        private List<Room> rooms;
        private List<Reservation> reservations;
        private int nextReservationId;

        public Hotel() {
            rooms = new ArrayList<>();
            reservations = new ArrayList<>();
            nextReservationId = 1;
        }

        public void addRoom(Room room) {
            rooms.add(room);
        }

        public List<Room> searchAvailableRooms() {
            List<Room> availableRooms = new ArrayList<>();
            for (Room room : rooms) {
                if (room.isAvailable()) {
                    availableRooms.add(room);
                }
            }
            return availableRooms;
        }

        public Reservation makeReservation(Room room, String guestName, LocalDate checkInDate, LocalDate checkOutDate) {
            if (!room.isAvailable()) {
                System.out.println("Room is not available.");
                return null;
            }

            Reservation reservation = new Reservation(nextReservationId++, room, guestName, checkInDate, checkOutDate);
            reservations.add(reservation);
            room.setAvailable(false);
            return reservation;
        }

        public Reservation viewReservation(int reservationId) {
            for (Reservation reservation : reservations) {
                if (reservation.getReservationId() == reservationId) {
                    return reservation;
                }
            }
            System.out.println("Reservation not found.");
            return null;
        }
    }

    // Main Method (User Interface)
    public static void main(String[] args) {
        Hotel hotel = new Hotel();
        Scanner scanner = new Scanner(System.in);

        // Add some rooms to the hotel
        hotel.addRoom(new Room(101, "Single", true));
        hotel.addRoom(new Room(102, "Double", true));
        hotel.addRoom(new Room(201, "Suite", true));

        while (true) {
            System.out.println("Hotel Reservation System");
            System.out.println("1. Search Available Rooms");
            System.out.println("2. Make a Reservation");
            System.out.println("3. View Reservation");
            System.out.println("4. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    searchAvailableRooms(hotel);
                    break;
                case 2:
                    makeReservation(hotel, scanner);
                    break;
                case 3:
                    viewReservation(hotel, scanner);
                    break;
                case 4:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void searchAvailableRooms(Hotel hotel) {
        List<Room> availableRooms = hotel.searchAvailableRooms();
        if (availableRooms.isEmpty()) {
            System.out.println("No rooms available.");
        } else {
            for (Room room : availableRooms) {
                System.out.println(room);
            }
        }
    }

    private static void makeReservation(Hotel hotel, Scanner scanner) {
        System.out.print("Enter room number: ");
        int roomNumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Room room = null;
        for (Room r : hotel.searchAvailableRooms()) {
            if (r.getRoomNumber() == roomNumber) {
                room = r;
                break;
            }
        }

        if (room == null) {
            System.out.println("Room not found or not available.");
            return;
        }

        System.out.print("Enter guest name: ");
        String guestName = scanner.nextLine();

        System.out.print("Enter check-in date (yyyy-MM-dd): ");
        String checkInDateStr = scanner.nextLine();

        System.out.print("Enter check-out date (yyyy-MM-dd): ");
        String checkOutDateStr = scanner.nextLine();

        LocalDate checkInDate = convertToDate(checkInDateStr);
        LocalDate checkOutDate = convertToDate(checkOutDateStr);

        if (checkInDate == null || checkOutDate == null) {
            System.out.println("Invalid date format.");
            return;
        }

        Reservation reservation = hotel.makeReservation(room, guestName, checkInDate, checkOutDate);
        if (reservation != null) {
            System.out.println("Reservation successful!");
            System.out.println(reservation);
        }
    }

    private static LocalDate convertToDate(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            return LocalDate.parse(dateStr, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    private static void viewReservation(Hotel hotel, Scanner scanner) {
        System.out.print("Enter reservation ID: ");
        int reservationId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Reservation reservation = hotel.viewReservation(reservationId);
        if (reservation != null) {
            System.out.println(reservation);
        }
    }
}
