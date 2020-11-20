package in.pks.journal.java.aop.exception;

public class ConstraintValidationException extends RuntimeException {

    public ConstraintValidationException(String msg){
        super(msg);
    }

    public ConstraintValidationException(){
        super();
    }

    public ConstraintValidationException(Throwable t){
        super(t);
    }

    public ConstraintValidationException(String msg, Throwable t){
        super(msg, t);
    }
}
