/* CSE 143
 * David Sergio
 * Assignment #2
 * Baron Oldenburg
 * AB
 * 07/09/2009
 * 
 * 
 */

public class HtmlValidator {
	
	private Queue<HtmlTag> tagQueue; // queue of HtmlTags
	private boolean valid; // valid boolean, whether valid HTML or not
	private boolean tagsChanged; // has the client called setTags?
	private String output; // the entire output (tags, errors, linebreaks, etc.)
	
	
	// creates an HtmlValidator with an empty queue of HtmlTags
	// pre: none
	// post: creates a HtmlValidator with an empty queue of HtmlTags
	public HtmlValidator() {
		this(new LinkedQueue<HtmlTag>());
	}
	
	// creates an HtmlValidator with an given queue of HtmlTags
	// pre: the given queue should not be empty
	// post: creates a HtmlValidator with an given queue of HtmlTags
	public HtmlValidator(Queue<HtmlTag> tags) {
		valid = false;
		tagsChanged = true;
		if (!tags.isEmpty()) {
			tagQueue = tags;
		} else {
			throw new IllegalArgumentException("An empty queue was passed to HtmlValidator");
		}
	}
	
	// returns the queue of HtmlTags
	// pre: none
	// post: returns the queue of HtmlTags
	public Queue<HtmlTag> getTags() {
		return tagQueue;
	}
	
	// sets given queue of HtmlTags
	// pre: the given queue should not be empty
	// post: updated queue of HtmlTags
	public void setTags(Queue<HtmlTag> tags) {
		if (!tags.isEmpty()) {
			tagsChanged = true;
			tagQueue = tags;
		} else {
			throw new IllegalArgumentException("An empty queue was passed to setTags");
		}
	}
	
	// checks if the queue of HtmlTags is valid HTML
	// pre: none
	// post: returns true if the queue of HtmlTags is valid HTML, false otherwise
	public boolean validate() {
		
		if (!tagsChanged) { // has this queue already been validated?
			System.out.print(output);
			return valid;
		}
		
		Stack<HtmlTag> stack = new ArrayStack<HtmlTag>();
		valid = true; // first assume it is valid HTML
		String indent = "    "; // tab string
		output = "";
				
		for (int i = 0; i < tagQueue.size(); i++) {
			HtmlTag tag = tagQueue.dequeue();
			tagQueue.enqueue(tag); // dequeue, then enqueue on end (to preserve queue)
			if (!tag.isSelfClosingTag()) {
				if (tag.isOpenTag()) { // open tag
					for (int j = 0; j < stack.size(); j++)
						output += indent; // print indentation level
					output += (tag + "\n");
					stack.push(tag); // increase indentation level
				} else { // close tag
					if (!stack.isEmpty()) { 
						HtmlTag sTag = stack.pop();
						if (!tag.matches(sTag)) { // top of stack doesn't match last queue
							output += ("ERROR unexpected tag: " + tag + "\n");
							valid = false;
							stack.push(sTag); // move on, maybe the next queue does match
						} else {
							for (int j = 0; j < stack.size(); j++)
								output += indent; // print indentation level
							output += (tag + "\n");
						}
					} else { // for a close tag, stack should not be empty
						output += ("ERROR unexpected tag: " + tag + "\n");
						valid = false;
					}
				}
			} else { // self closing tag
				if (!tag.isOpenTag()) { // a self closing tag should not be closing
					output += ("ERROR unexpected tag: " + tag + "\n");
					valid = false;
				} else {
					for (int j = 0; j < stack.size(); j++)
						output += indent; // print indentation level
					output += (tag + "\n");
				}
			}
		}
		while (!stack.isEmpty()) { // unresolved (unclosed) stack elements
			output += ("ERROR unclosed tag: " + stack.pop() + "\n");
			valid = false;
		}
		
		if (valid)
			output += ("\nVALID markup!\n");
		else
			output += ("\nINVALID markup...\n");
		
		tagsChanged = false;
		System.out.print(output);
		
		return valid;
	}
}
