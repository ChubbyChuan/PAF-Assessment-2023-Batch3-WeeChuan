package ibf2022.assessment.paf.batch3.models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Orders {
    private String orderId;
    private LocalDateTime date;
    private int breweryId;
    private List<Order> orderItems;

    public Orders(int breweryId, List<Order> orderItems) {
        this.orderId = UUID.randomUUID().toString().substring(0, 8);
        this.date = LocalDateTime.now();
        this.breweryId = breweryId;
        this.orderItems = orderItems;
    }

    public String getOrderId() {
        return orderId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public int getBreweryId() {
        return breweryId;
    }

    public List<Order> getOrderItems() {
        return orderItems;
    }
}
