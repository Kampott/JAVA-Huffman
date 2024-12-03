import java.io.File;

public class Main {
    public static void main(String[] args) {
        String helpString = "Arguments format: <command> <inputFile> [outputFile], " +
                "where command is encode, decode, or info";

        if (args.length < 2) {
            System.out.println("Incorrect usage.");
            System.out.println("Use: java Main <encode/decode/info> <inputFile> [outputFile]");
            return;
        }

        HuffmanCodec h = new HuffmanCodec();

        String[] nargs = new String[args.length];
        System.arraycopy(args, 0, nargs, 0, args.length);

        switch (nargs[0]) {
            case "decode" -> {
                DataInfo dataInfo = FileEngine.readArchive(args[1]);

                byte[] result = h.decode(dataInfo.compressedData, dataInfo.recoveryMap);

                String outputFilePath = args.length >= 3 ? args[2] : dataInfo.originalFileName;

                FileEngine.writeFile(result, outputFilePath);
                System.out.println("Decoded file written to: " + outputFilePath);
            }
            case "encode" -> {
                byte[] data = FileEngine.readFile(nargs[1]);
                byte[] result = h.encode(data);
                File f = new File(nargs[1]);
                String originalFileName = f.getName();
                String outputFilePath = args.length >= 3 ? args[2] :
                        originalFileName.substring(0, originalFileName.lastIndexOf('.')) + ".huff";

                FileEngine.writeArchive(result, h.codeMap, originalFileName, outputFilePath);
                System.out.println("Encoded file written to: " + outputFilePath);
            }
            case "info" -> {
                DataInfo dataInfo = FileEngine.readArchive(args[1]);
                byte[] result = h.decode(dataInfo.compressedData, dataInfo.recoveryMap);

                FileEngine.test(dataInfo, result);
            }
            default -> {
                System.out.println("Invalid command.");
                System.out.println(helpString);
            }
        }
    }
}
