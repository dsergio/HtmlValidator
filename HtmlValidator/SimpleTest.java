// CSE 143, Winter 2009, Marty Stepp
// Homework 2: HTML Validator
//
// Instructor-provided code.
// This program is a very simple test for your HTML validator object.
// Please feel free to modify this file to create your own simple test cases.

/** Runs your HTML validator. */
public class SimpleTest {
    public static void main(String[] args) {
        // first test for short valid code
        // <html><body><b>hello</b><i>how are <b>you</b><br/></i></body></html>
        Queue<HtmlTag> tags = new LinkedQueue<HtmlTag>();
        tags.enqueue(new HtmlTag("html", true));   // <html>
        tags.enqueue(new HtmlTag("body", true));   // <body>
        tags.enqueue(new HtmlTag("b", true));      // <b>
        tags.enqueue(new HtmlTag("b", false));     // </b>
        tags.enqueue(new HtmlTag("i", true));      // <i>
        tags.enqueue(new HtmlTag("b", true));      // <b>
        tags.enqueue(new HtmlTag("b", false));     // </b>
        tags.enqueue(new HtmlTag("br"));           // <br/>
        tags.enqueue(new HtmlTag("i", false));     // </i>
        tags.enqueue(new HtmlTag("body", false));  // </body>
        tags.enqueue(new HtmlTag("html", false));  // </html>
        
        HtmlValidator validator = new HtmlValidator(tags);
        System.out.println("getTags: " + validator.getTags());
        System.out.println("validate:");
        validator.validate();
        
        // second test for invalid code and setTags
        tags = new LinkedQueue<HtmlTag>();
        tags.enqueue(new HtmlTag("html", true));   // <html>
        tags.enqueue(new HtmlTag("body", true));   // <body>
        tags.enqueue(new HtmlTag("b", true));      // <b>
        tags.enqueue(new HtmlTag("body", false));  // </body>
        tags.enqueue(new HtmlTag("b", false));     // </b>
        tags.enqueue(new HtmlTag("html", false));  // </html>
        
        System.out.println();
        System.out.println("setTags(" + tags + ")");
        validator.setTags(tags);
        System.out.println("getTags: " + validator.getTags());
        System.out.println("validate:");
        validator.validate();
    }
}
