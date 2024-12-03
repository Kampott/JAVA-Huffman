import java.util.HashMap;

public class DataInfo {
    String compressedData;
    String originalFileName;
    HashMap<String, Byte> recoveryMap;
    int initialLength;
    int dataOffset;
};