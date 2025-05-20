package org.example.projet_java;

import org.example.projet_java.model.Notification;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotificationTest {

    @Test
    public void testConstructeur(){
        Notification notification = new Notification("Examen demain", "11/05/2025");

        assertNotNull(notification);
    }

    @Test
    public void testGetters(){
        Notification notification = new Notification("Examen demain", "11/05/2025");

        assertEquals("Examen demain", notification.getMessage());
        assertEquals("11/05/2025", notification.getDate());
    }

    @Test
    public void testSetMessage(){
        Notification notification = new Notification("Examen demain", "11/05/2025");

        notification.setMessage("Examen apres demain");
        assertEquals("Examen apres demain", notification.getMessage());
    }

    @Test
    public void testSetDate(){
        Notification notification = new Notification("Examen demain", "11/05/2025");

        notification.setDate("12/05/2025");
        assertEquals("12/05/2025", notification.getDate());
    }
}