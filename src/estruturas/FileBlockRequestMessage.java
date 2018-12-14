package estruturas;

import java.io.Serializable;

public class FileBlockRequestMessage implements Serializable {

    private FileDetails fileDetails;
    private int offset;
    private int length;

    public FileBlockRequestMessage(FileDetails fileDetails, int offset, int length) {
        this.fileDetails = fileDetails;
        this.offset = offset;
        this.length = length;
    }

    public FileDetails getFileDetails() {
        return fileDetails;
    }

    public int getOffset() {
        return offset;
    }

    public int getLength() {
        return length;
    }

    @Override
    public String toString() {
        return this.fileDetails + " offset: " + this.offset + " length: " + this.length;
    }
}
