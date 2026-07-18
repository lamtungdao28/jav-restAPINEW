package com.lam.javarestapi.dto.response;

import jakarta.validation.Payload;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class ResponseSuccess extends ResponseEntity<ResponseSuccess.Payload> {

    //PATCH, PUT, DELETE
    public ResponseSuccess(HttpStatusCode status, String message) {
        super(new Payload(status.value(), message), HttpStatus.OK);
    }
    //POST, GET
    public ResponseSuccess(HttpStatusCode status, String message, Object data) {
        super(new Payload(status.value(), message,data), HttpStatus.OK);
    }
    public static class Payload{
        private final int status;
        private final String message;
        private Object data;

        public Payload(int status, String message, Object data) {
            this.status = status;
            this.message = message;
            this.data = data;
        }

        public Payload(int status,String message) {
            this.message = message;
            this.status = status;
        }

        public int getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }

        public Object getData() {
            return data;
        }
    }
}
