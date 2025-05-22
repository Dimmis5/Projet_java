package org.example.projet_java.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotificationTest {
    @Test
    public void testConstructor() {
        Notification notification = new Notification("Message important", "21/05/2025");

        assertEquals("Message important", notification.getMessage());
        assertEquals("21/05/2025", notification.getDate());
        assertNotNull(notification);
    }

    @Test
    public void testSetters() {
        Notification notification = new Notification("Message initial", "20/05/2025");

        notification.setMessage("Message mis à jour");
        notification.setDate("21/05/2025");

        assertEquals("Message mis à jour", notification.getMessage());
        assertEquals("21/05/2025", notification.getDate());
    }
}