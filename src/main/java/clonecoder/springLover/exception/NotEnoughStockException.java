package clonecoder.springLover.exception;

public class NotEnoughStockException extends Exception{
    public NotEnoughStockException() { }
    public NotEnoughStockException(String msg) {
        super(msg);
    }

}
