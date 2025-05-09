package com.utils;

public class ErrorUtil {
    public static String getUserFriendlyMessage(String errorCode) {
        switch (errorCode) {
            case "DB_ERROR":
                return "Ocurrió un problema al acceder a la base de datos.";
            case "NOT_FOUND":
                return "El recurso solicitado no fue encontrado.";
            case "INVALID_INPUT":
                return "Los datos ingresados no son válidos.";
            case "CART_EMPTY":
                return "Tu carrito está vacío.";
            case "SESSION_ERROR":
                return "La sesión ha expirado, por favor, inicia sesión nuevamente.";
            default:
                return "Ha ocurrido un error inesperado. Inténtalo de nuevo.";
        }
    }
}
