package org.deblock.exercise.deblockflights.exception;

public class ValidationException extends RuntimeException {

    public ValidationException(){
        super();
    }

    public ValidationException(String message){
        super(message);
    }

}
