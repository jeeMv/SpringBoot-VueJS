package io.github.jeemv.springboot.vuejs.parts;

/**
 * VueFilter
 * This class is part of springBoot-VueJS
 * @author jcheron myaddressmail@gmail.com
 * @version 1.0.0
 *
 */
public class VueFilter extends AbstractVueComposition{
	protected VueMethod method;
	
	public VueFilter(String body,String...args) {
		super();
		method=new VueMethod(body,getArgs(args));
	}
	
	public VueFilter(String name,String body,String...args) {
		super(name);
		method=new VueMethod(body,getArgs(args));
	}

	
	private String[] getArgs(String...args) {
		String[] dest = new String[args.length + 1];
	    dest[0] = "value";
	    System.arraycopy(args, 0, dest, 1, args.length);
	    return dest;
	}
	
	@Override
	public String toString() {
		return method.toString();
	}

	@Override
	public String getType() {
		return "filter";
	}
}
