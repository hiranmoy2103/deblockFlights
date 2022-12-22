package org.deblock.exercise.deblockflights.exception;

public class InconsistentDateException extends RuntimeException {

    public InconsistentDateException(){
        super();
    }

    public InconsistentDateException(String message){
        super(message);
    }

}
