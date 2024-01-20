package fmb.parserTool;

public class ParserScript {
    public static void main(String[] args){
        switch (args.length) {
            case 2:
                handler(parseArg(args[1]));
                break;
            case 3:
                handler(parseArg(args[1]) args[2]);
                break;
            default:
                printUsage();
                System.exit(1);
                break;
        }
    }

    private static String parseArg(String arg) {

    }

    public static void handler(String arg){

    }
    private static void printUsage() {
        System.err.println("Usage: java StringProcessorApp <action-optional> <dbTableTitle-optional> <text>");
    }
}
