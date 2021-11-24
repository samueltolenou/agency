package com.agency.enums;

public enum StatusMessage {

    USERNAME_EXIST(0, "Ce nom d'utilisateur existe déjà."),
    EMAIL_EXIST(1, "Cette adresse email existe déjà."),
    USER_REGISTERED(2, "L'utilisateur a été créé avec succès."),
    USER_NOT_FOUND(3, "Aucun utilisateur ne correspond à ces informations de connexions."),
    INTERNAL_SERVER_ERROR(4, "Une erreur inattendue s'est produite. Contactez l'administrateur du système.");


    private final int value;
    private final String reasonPhrase;

    private StatusMessage(int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }


    public int getValue() {
        return value;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }
}
