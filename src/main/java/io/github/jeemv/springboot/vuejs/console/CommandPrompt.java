package io.github.jeemv.springboot.vuejs.console;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * CommandPrompt
 * This class is part of springBoot-VueJS
 * @author jcheron myaddressmail@gmail.com
 * @version 1.0.0
 *
 */
public class CommandPrompt {
	private String prompt;
	private List<CommandAction> actions;
	private Scanner scanner;

	public CommandPrompt(Scanner scanner,String prompt,CommandAction...actions) {
	    this.scanner=scanner;
		this.prompt = prompt;
	    this.actions=Arrays.asList(actions);
	    this.run();
	}

	// example usage
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
	    new CommandPrompt(scanner,
	            "Choose",new CommandAction( () -> {System.out.println("yes");}, "y"),
	            new CommandAction(() -> {System.out.println("no");}, "n")
	            );

	    new CommandPrompt(
	            scanner,"Choose",new CommandAction( () -> {System.out.println("1");}, "1"),
	            new CommandAction(() -> {System.out.println("2");}, "2")
	            );

	}

	public void run() {
        while (true) {
            System.out.print(prompt +" "+ actions);
        	String option = scanner.next();
        	for(CommandAction action:actions) {
	            if (action.getOption().equals(option)){
	               action.getAction().run();
	               return;
	            }
            }
        }
    }
}
