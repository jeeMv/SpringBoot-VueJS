package io.github.jeemv.springboot.vuejs.parts;

/**
 * VueDirective
 * This class is part of springBoot-VueJS
 * @author jcheron myaddressmail@gmail.com
 * @version 1.0.0
 *
 */
public class VueDirective extends AbstractVueComposition{
	protected VueMethods methods;
	protected VueMethod shortHand;
	
	public VueDirective() {
		super();
		methods=new VueMethods();
	}

	/**
	 * @param name The directive name (ex: demo for v-demo directive)
	 */
	public VueDirective(String name) {
		super(name);
		methods=new VueMethods();
	}
	
	/**
	 * @param name The directive name
	 * @param shortHand Same behavior on bind and update, but don’t care about the other hooks
	 */
	public VueDirective(String name,String shortHand) {
		super(name);
		setShortHand(shortHand);
	}
	
	public void setShortHand(String shortHand) {
		this.shortHand=new VueMethod(shortHand,"el","binding","vnode");
	}

	/**
	 * called only once, when the directive is first bound to the element. This is where you can do one-time setup work.
	 * arguments of the method are el, binding and vnode
	 * @param body The javascript code associated
	 * @return the directive
	 */
	public VueDirective onBind(String body) {
		return on("bind", body);
	}
	
	/**
	 * called after the containing component’s VNode has updated, but possibly before its children have updated.
	 * arguments of the method are el, binding, vnode and oldVnode
	 * @param body The javascript code associated
	 * @return The directive
	 */
	public VueDirective onUpdate(String body) {
		methods.add("update", body, "el", "binding", "vnode","oldVnode");
		return this;
	}
	
	/**
	 * called after the containing component’s VNode and the VNodes of its children have updated.
	 * arguments of the method are el, binding, vnode and oldVnode
	 * @param body The javascript code associated
	 * @return The directive
	 */
	public VueDirective onComponentUpdated(String body) {
		methods.add("componentUpdated", body, "el", "binding", "vnode","oldVnode");
		return this;
	}
	
	/**
	 * called when the bound element has been inserted into its parent node (this only guarantees parent node presence, not necessarily in-document).
	 * arguments of the method are el, binding and vnode
	 * @param body The javascript code associated
	 * @return The directive
	 */
	public VueDirective onInserted(String body) {
		return on("inserted", body);
	}

	
	/**
	 * called only once, when the directive is unbound from the element.
	 * arguments of the method are el, binding and vnode
	 * @param body The javascript code associated
	 * @return The directive
	 */
	public VueDirective onUnbind(String body) {
		return on("unbind", body);
	}
	
	/**
	 * Adds an event in the directive
	 * 
	 * @param event The associated event
	 * @param body The javascript code associated
	 * @return
	 */
	protected VueDirective on(String event,String body) {
		methods.add(event, body, "el", "binding", "vnode");
		return this;
	}

	@Override
	public String toString() {
		if(shortHand instanceof VueMethod) {
			return shortHand.toString();
		}
		return methods.toString();
	}

	@Override
	public String getType() {
		return "directive";
	}
	
	/**
	 * To also detect nested value changes inside Objects
	 */
	public void setDeep() {
		methods.addProperty("deep", true);
	}
}
