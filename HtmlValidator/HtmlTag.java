// CSE 143, Winter 2009, Marty Stepp
// Homework 2: HTML Validator
//
// Instructor-provided code.  You should not modify this file!

import java.util.*;

/** An HtmlTag object represents an HTML tag, such as <b> or </table>. */
public class HtmlTag {
    // fields
    private final String element;
    private final boolean isOpenTag;
    
    /** 
     * Constructs an HTML "opening" tag with the given element (e.g. "table").
     * Throws a NullPointerException if element is null.
     */
    public HtmlTag(String element) {
        this(element, true);
    }
    
    /** 
     * Constructs an HTML tag with the given element (e.g. "table") and type.
     * Self-closing tags like <br /> are considered to be "opening" tags,
     * but return false from the requiresClosingTag method.
     * Throws a NullPointerException if element is null.
     */
    public HtmlTag(String element, boolean isOpenTag) {
        this.element = element.toLowerCase();
        this.isOpenTag = isOpenTag;
    }
    
    /**
     * Returns true if this tag has the same element and type as the
     * given other tag.
     */
    public boolean equals(Object o) {
        if (o instanceof HtmlTag) {
            HtmlTag other = (HtmlTag) o;
            return element.equals(other.element) && isOpenTag == other.isOpenTag;
        } else {
            return false;
        }
    }
    
    /** Returns this HTML tag's element. */
    public String getElement() {
        return element;
    }
    
    /**
     * Returns true if this HTML tag is an "opening" (starting) tag and false
     * if it is a closing tag.
     * Self-closing tags like <br /> are considered to be "opening" tags,
     * and they also return true from the isSelfClosingTag method.
     */
    public boolean isOpenTag() {
        return isOpenTag;
    }
    
    /**
     * Returns true if the given other tag is non-null and matches this tag;
     * that is, if they have the same element but opposite types,
     * such as <body> and </body>.
     */
    public boolean matches(HtmlTag other) {
        return other != null && element.equals(other.element) && isOpenTag != other.isOpenTag;
    }
    
    /**
     * Returns true if this tag does not require a matching closing tag; usually this
     * is false, except for certain elements such as br and img.
     */
    public boolean isSelfClosingTag() {
        return NON_MATCHING_TAGS.contains(element);
    }
    
    /** Returns a string representation of this HTML tag, such as "</table>". */
    public String toString() {
        return "<" + (isOpenTag ? "" : "/") + (element.equals("!--") ? "!-- --" : element) + ">";
    }
    

    
    // a set of tags that don't need to be matched (self-closing)
    private static final Set<String> NON_MATCHING_TAGS = new HashSet<String>(
            Arrays.asList("!doctype", "!--", "area", "base", "basefont",
                          "br", "col", "frame", "hr", "img", "input",
                          "link", "meta", "param"));
    
    // all whitespace characters; used in text parsing
    private static final String WHITESPACE = " \f\n\r\t";

    /**
     * Reads the file or URL given, and tokenizes the text in that file,
     * placing the tokens into the given Queue.
     * You don't need to call this method in your homework code.
     * Precondition: address represents a valid file/URL
     */
    public static Queue<HtmlTag> tokenize(String text) {
        StringBuffer buf = new StringBuffer(text);
        Queue<HtmlTag> queue = new LinkedQueue<HtmlTag>();

        while (true) {
            HtmlTag nextTag = nextTag(buf);
            if (nextTag == null) {
                break;
            } else {
                queue.enqueue(nextTag);
            }
        }
        
        return queue;
    }

    // advances to next tag in input;
    // probably not a perfect HTML tag tokenizer, but it will do for this HW
    private static HtmlTag nextTag(StringBuffer buf) {
        int index1 = buf.indexOf("<");
        int index2 = buf.indexOf(">");
        
        if (index1 >= 0 && index2 > index1) {
            // check for HTML comments: <!-- -->
            if (index1 + 4 <= buf.length() && buf.substring(index1 + 1, index1 + 4).equals("!--")) {
                // a comment; look for closing comment tag -->
                index2 = buf.indexOf("-->", index1 + 4);
                if (index2 < 0) {
                    return null;
                } else {
                    buf.insert(index1 + 4, " ");    // fixes things like <!--hi-->
                    index2 += 3;    // advance to the closing >
                }
            }

            String element = buf.substring(index1 + 1, index2).trim();

            // remove attributes
            for (int i = 0; i < WHITESPACE.length(); i++) {
                int index3 = element.indexOf(WHITESPACE.charAt(i));
                if (index3 >= 0) {
                    element = element.substring(0, index3);
                }
            }
            
            // determine whether opening or closing tag
            boolean isOpenTag = true;
            if (element.indexOf("/") == 0) {
                isOpenTag = false;
                element = element.substring(1);
            }
            
            buf.delete(0, index2 + 1);
            return new HtmlTag(element, isOpenTag);
        } else {
            return null;
        }
    }    
}
