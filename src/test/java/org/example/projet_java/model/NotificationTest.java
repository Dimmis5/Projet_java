package org.example.projet_java.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotificationTest {
    @Test
    public void testConstructorAndGetters() {
        Notification notification = new Notification("Message important", "2025-05-21");

        assertEquals("Message important", notification.getMessage());
        assertEquals("2025-05-21", notification.getDate());
    }

    @Test
    public void testSetters() {
        Notification notification = new Notification("Initial message", "2025-05-20");

        notification.setMessage("Message mis à jour");
        notification.setDate("2025-05-21");

        assertEquals("Message mis à jour", notification.getMessage());
        assertEquals("2025-05-21", notification.getDate());
    }
}