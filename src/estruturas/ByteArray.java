package estruturas;

import java.io.Serializable;
import java.util.Arrays;

public class ByteArray implements Serializable {

    private byte[] array;
    private long offset;

    public ByteArray(byte[] array, long offset) {
        this.offset = offset;
        this.array = array;
    }

    public byte[] getArray() {
        return array;
    }

    public void setArray(byte[] array) {
        this.array = array;
    }

    public long getSize(){
        return this.array.length;
    }

    public long getOffset(){
        return this.offset;
    }

    @Override
    public String toString() {
        return "ByteArray{" +
                "array=" + Arrays.toString(array) +
                '}';
    }
}
