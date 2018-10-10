package io.github.jeemv.springboot.vuejs.console;


public class CommandAction {
	private Runnable action;
	private String option;
	public CommandAction(Runnable action, String option) {
		this.action = action;
		this.option = option;
	}
	public Runnable getAction() {
		return action;
	}
	public void setAction(Runnable action) {
		this.action = action;
	}
	public String getOption() {
		return option;
	}
	public void setOption(String option) {
		this.option = option;
	}
	@Override
	public String toString() {
		return option;
	}
}
