package org.hahen.ticketEase.enums;

public enum TicketPriority {
    LOW("Low"),
    MEDIUM("Medium") ,
    HIGH("High");

    private final String text;
    TicketPriority(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }


    public static TicketPriority[] getAllValues() {
        return TicketPriority.values();
    }


    public static boolean contains(String value) {
        for (TicketPriority priority : TicketPriority.values()) {
            if (priority.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }


    public static TicketPriority fromTextToPrio(String text) {
        for (TicketPriority priority : TicketPriority.values()) {
            if (priority.getText().equalsIgnoreCase(text)) {
                return priority;
            }
        }
        throw new IllegalArgumentException("Unknown priority: " + text);
    }
}
