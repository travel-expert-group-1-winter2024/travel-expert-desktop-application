package org.example.travelexpertdesktopapplication.models;

import javafx.beans.property.*;

import java.time.LocalDate;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BookingDetails {
    private final SimpleIntegerProperty bookingDetailId;
    private final SimpleStringProperty itineraryNo;
    private final ObjectProperty<LocalDate> tripStart;
    private final ObjectProperty<LocalDate> tripEnd;
    private final SimpleStringProperty description;
    private final SimpleStringProperty destination;
    private final SimpleDoubleProperty basePrice;
    private final SimpleDoubleProperty agencyCommission;
    private final SimpleIntegerProperty bookingId;
    private final SimpleIntegerProperty agentId;
    private final SimpleStringProperty region;
    private final SimpleStringProperty className;
    private final SimpleStringProperty fee;
    private final SimpleStringProperty product;
    private final SimpleStringProperty supplier;

    // Constructor
    public BookingDetails(int bookingDetailId, String itineraryNo, LocalDate tripStart, LocalDate tripEnd,
                          String description, String destination, double basePrice, double agencyCommission,
                          int bookingId, int agentId, String region, String className,
                          String fee, String product, String supplier) {
        this.bookingDetailId = new SimpleIntegerProperty(bookingDetailId);
        this.itineraryNo = new SimpleStringProperty(itineraryNo);
        this.tripStart = new SimpleObjectProperty<>(tripStart);
        this.tripEnd = new SimpleObjectProperty<>(tripEnd);
        this.description = new SimpleStringProperty(description);
        this.destination = new SimpleStringProperty(destination);
        this.basePrice = new SimpleDoubleProperty(basePrice);
        this.agencyCommission = new SimpleDoubleProperty(agencyCommission);
        this.bookingId = new SimpleIntegerProperty(bookingId);
        this.agentId = new SimpleIntegerProperty(agentId);
        this.region = new SimpleStringProperty(region);
        this.className = new SimpleStringProperty(className);
        this.fee = new SimpleStringProperty(fee);
        this.product = new SimpleStringProperty(product);
        this.supplier = new SimpleStringProperty(supplier);
    }

    // Getters for properties (for TableView bindings)
    public IntegerProperty bookingDetailIdProperty() { return bookingDetailId; }
    public StringProperty itineraryNoProperty() { return itineraryNo; }
    public ObjectProperty<LocalDate> tripStartProperty() { return tripStart; }
    public ObjectProperty<LocalDate> tripEndProperty() { return tripEnd; }
    public StringProperty descriptionProperty() { return description; }
    public StringProperty destinationProperty() { return destination; }
    public DoubleProperty basePriceProperty() { return basePrice; }
    public DoubleProperty agencyCommissionProperty() { return agencyCommission; }
    public IntegerProperty bookingIdProperty() { return bookingId; }
    public IntegerProperty agentIdProperty() { return agentId; }
    public StringProperty regionProperty() { return region; }
    public StringProperty classNameProperty() { return className; }
    public StringProperty feeProperty() { return fee; }
    public StringProperty productProperty() { return product; }
    public StringProperty supplierProperty() { return supplier; }

    public String toSearchableStringPastBookings() {
        return Stream.of(
                        String.valueOf(bookingDetailId.get()), itineraryNo.get(),
                        tripStart.get().toString(), tripEnd.get().toString(),
                        description.get(), destination.get(),
                        String.valueOf(basePrice.get()), String.valueOf(agencyCommission.get()),
                        String.valueOf(bookingId.get()), String.valueOf(agentId.get()),
                        region.get(), className.get(), fee.get(),
                        product.get(), supplier.get()
                )
                .filter(Objects::nonNull) // Ensure no null values
                .map(String::toLowerCase) // Convert to lowercase
                .collect(Collectors.joining(" "));
    }

}
