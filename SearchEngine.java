import java.io.IOException;
import java.net.URI;
import java.util.Vector;

class Handler implements URLHandler {
        // The one bit of state on the server: a number that will be manipulated by
        // various requests.
        Vector<String> stringList = new Vector<String>();
    
        public String handleRequest(URI url) {
            if (url.getPath().equals("/")) {
                return String.format("Ready to search!");
            } 
            else {
                System.out.println("Path: " + url.getPath());
                if (url.getPath().contains("/add")) {
                    String[] parameters = url.getQuery().split("=");
                        stringList.add(parameters[1]);
                        return String.format("New word added: %s", parameters[1]);
                    
                }
                else if (url.getPath().contains("/search")) {
                    String[] parameters = url.getQuery().split("=");
                    Vector<String> results = new Vector<String>();
                        for (String s : stringList){
                            if (s.contains(parameters[1])){
                                results.add(s);
                            }
                        }
                    return results.toString();
                }
                return "404 Not Found!";
            }
        }
    }

    class SearchEngine {
        public static void main(String[] args) throws IOException {
            if(args.length == 0){
                System.out.println("Missing port number! Try any number between 1024 to 49151");
                return;
            }
    
            int port = Integer.parseInt(args[0]);
    
            Server.start(port, new Handler());
        }
    }