package at.htlkaindorf.backend_redogo.exception;

/**
 * Einheitlicher JSON-Body für Fehler: {"status": 400, "message": "..."}.
 */
public record ApiError(int status, String message) {
}
