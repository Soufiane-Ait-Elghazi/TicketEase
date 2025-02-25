package org.hahen.ticketEase.enums;

public enum TicketStatus {
    NEW("New"),
    IN_PROGRESS("In Progress") ,
    RESOLVED("Resolved");

    private final String text;
    TicketStatus(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }


    public static TicketStatus[] getAllValues() {
        return TicketStatus.values();
    }


    public static boolean contains(String value) {
        for (TicketStatus status : TicketStatus.values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }


    public static TicketStatus fromText(String text) {
        for (TicketStatus status : TicketStatus.values()) {
            if (status.text.equalsIgnoreCase(text)) {
                return status;
            }
        }
        throw new IllegalArgumentException("No matching status for text: " + text);
    }
}
