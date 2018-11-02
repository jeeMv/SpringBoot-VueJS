package io.github.jeemv.springboot.vuejs.parts;

public class VueDirective extends AbstractVueComposition{
	protected VueMethods methods;
	
	public VueDirective() {
		super();
		methods=new VueMethods();
	}

	public VueDirective(String name) {
		super(name);
		methods=new VueMethods();
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
	
	protected VueDirective on(String event,String body) {
		methods.add(event, body, "el", "binding", "vnode");
		return this;
	}

	@Override
	public String toString() {
		return methods.toString();
	}

	@Override
	public String getType() {
		return "directive";
	}
}
