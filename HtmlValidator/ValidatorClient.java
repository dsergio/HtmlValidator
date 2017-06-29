// CSE 143, Winter 2009, Marty Stepp
// Homework 2: HTML Validator
//
// Instructor-provided code.
// This program runs your HTML validator object on any file or URL you want.
//
// When it prompts you for a file name, if you type a simple string such
// as "test1.html" (without the quotes) it will just look on your hard disk
// in the same directory as your code or Eclipse project.
//
// If you type a string such as "http://www.google.com/index.html", it will
// connect to that URL and download the HTML content from it.

import java.io.*;
import java.net.*;
import java.util.*;

/** Runs your HTML validator. */
public class ValidatorClient {
    public static void main(String[] args) throws IOException {
        HtmlValidator validator = null;
        String pageText = "";
        Scanner console = new Scanner(System.in);
        String choice = "s";
        while (true) {
            if (choice.toLowerCase().startsWith("s")) {
                // prompt for page, then download it if it's a URL
                System.out.print("Page URL or file name: ");
                String url = console.nextLine();
                if (isURL(url)) {
                    System.out.println("Downloading from " + url + " ...");
                }
                pageText = readCompleteFileOrURL(url);
                Queue<HtmlTag> tags = HtmlTag.tokenize(pageText);
                
                // create/update the HTML validator
                if (validator == null) {
                    validator = new HtmlValidator(tags);
                } else {
                    validator.setTags(tags);
                }
            } else if (choice.toLowerCase().startsWith("p")) {
                System.out.println(pageText);
            } else if (choice.toLowerCase().startsWith("g")) {
                System.out.println("getTags: " + validator.getTags());
            } else if (choice.toLowerCase().startsWith("v")) {
                boolean result = validator.validate();
                System.out.println();
                System.out.println("validate() returned " + result);
            } else {
                break;
            }

            System.out.println();
            System.out.print("(g)etTags, (v)alidate, (s)et URL, (p)rint HTML, or (q)uit? ");
            choice = console.nextLine();
        }
    }
    
    /**
     * Returns an input stream to read from the given address.
     * Works with URLs or normal file names.
     */
    public static InputStream getInputStream(String address) throws IOException {
        if (isURL(address)) {
            try {
                return new URL(address).openStream();
            } catch (MalformedURLException e) {
                throw new RuntimeException("Bad URL: " + address);
            }
        } else {
            // local file
            return new FileInputStream(address);
        }
    }

    /** Returns true if the given string represents a URL. */
    public static boolean isURL(String address) {
        return address.startsWith("http://") || address.startsWith("https://") ||
                address.startsWith("www.") ||
                address.endsWith("/") || 
                address.endsWith(".com") || address.contains(".com/") || 
                address.endsWith(".org") || address.contains(".org/") || 
                address.endsWith(".edu") || address.contains(".edu/") || 
                address.endsWith(".gov") || address.contains(".gov/");
    }

    /**
     * Opens the given address for reading input, and reads it until the end 
     * of the file, and returns the entire file contents as a big String.
     * 
     * If address starts with http[s]:// , assumes address is a URL and tries
     * to download the data from the web.  Otherwise, assumes the address
     * is a local file and tries to read it from the disk.
     */
    public static String readCompleteFileOrURL(String address) throws IOException {
        InputStream stream = getInputStream(address);   // open file

        // read each letter into a buffer
        StringBuffer buffer = new StringBuffer();
        while (true) {
            int ch = stream.read();
            if (ch < 0) {
                break;
            }

            buffer.append((char) ch);
        }

        return buffer.toString();
    }    
}
