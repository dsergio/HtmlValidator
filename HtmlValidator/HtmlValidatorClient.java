public class HtmlValidatorClient {
	public static void main(String[] args) {
	
		Queue<HtmlTag> q = new LinkedQueue<HtmlTag>();
		
		q.enqueue(new HtmlTag("1",true));
		q.enqueue(new HtmlTag("2",true));
		q.enqueue(new HtmlTag("1",false));
			
		HtmlValidator v = new HtmlValidator(q);
		
		v.validate();
		
		System.out.println(q);
		
	}
}
