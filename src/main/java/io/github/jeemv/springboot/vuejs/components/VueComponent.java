package io.github.jeemv.springboot.vuejs.components;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.github.jeemv.springboot.vuejs.AbstractVueJS;
import io.github.jeemv.springboot.vuejs.beans.RawObject;
import io.github.jeemv.springboot.vuejs.configuration.VueConfig;
import io.github.jeemv.springboot.vuejs.console.CommandAction;
import io.github.jeemv.springboot.vuejs.console.CommandPrompt;
import io.github.jeemv.springboot.vuejs.parts.VueProps;
import io.github.jeemv.springboot.vuejs.utilities.JsUtils;


/**
 * VueJS component class
 * This class is part of springBoot-VueJS
 * @author jc
 * @version 1.0.1
 *
 */
public class VueComponent extends AbstractVueJS{
	private String name;
	private String template;
	private boolean internal;
	private VueProps props;
	private static final String ROOT_FOLDER="src/main/resources/static/";
	
	public VueComponent() {
		this(null,true);
	}
	
	public VueComponent(String name) {
		this(name,true);
	}
	
	public VueComponent(String name,boolean internal) {
		super();
		this.name=name;
		this.internal=internal;
		this.props=new VueProps();
	}

	@Override
	public String getScript() {
		String script="";
		try {
			if(name!=null && !"".equals(name)) {
				script="Vue.component('"+name+"',"+JsUtils.objectToJSON(this)+");";
				if(!internal)
					script= JsUtils.wrapScript(script);
			}else {
				script=JsUtils.objectToJSON(this);
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return script;
	}

	public String getName() {
		return name;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}
	
	public void setTemplateFile(String pathFilename) {
		try {
			loadTemplateFile("templates/"+pathFilename);
		} catch (IOException e) {
			template=e.getMessage();
			e.printStackTrace();
		}
	}
	
	public void setDefaultTemplateFile() {
		setTemplateFile(VueConfig.getTemplateComponentFolder()+"/"+name+".html");
	}

	public boolean isInternal() {
		return internal;
	}

	public void setInternal(boolean internal) {
		this.internal = internal;
	}

	public VueProps getProps() {
		return props;
	}

	public void setProps(String...props) {
		for(String prop:props) {
			this.props.add(prop);
		}
	}
	
	public VueProp addProp(String name,Object defaultValue) {
		VueProp prop=this.props.add(name);
		prop.setDefaultValue(defaultValue);
		return prop;
	}
	
	public VueProp addPropRaw(String name,String defaultValue) {
		VueProp prop=this.props.add(name);
		prop.setDefaultValue(new RawObject(defaultValue));
		return prop;
	}
	
	public VueProp addProp(String name,String type,boolean required) {
		VueProp prop=this.props.add(name);
		prop.setTypes(type);
		prop.setRequired(required);
		return prop;
	}
	
	/**
	 * Adds a mutable property (use mutProperty=value to assign a value)
	 * @param name then name of the property
	 * @param type the property type (Object,Array,Function,String,Number,Boolean)
	 * @param required if true property is required
	 * @param mutable if true property is mutable
	 * @return the property added
	 */
	public VueProp addProp(String name,String type,boolean required,boolean mutable) {
		addComputed("mut"+name.substring(0, 1).toUpperCase() + name.substring(1), " return this."+name+";","this.$emit('update:"+name+"', v);");
		return addProp(name, type, required);
	}
	
	protected void loadTemplateFile(String filename) throws IOException {
		Resource resource = new ClassPathResource(filename);
		InputStream resourceInputStream = resource.getInputStream();
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length;
		while ((length = resourceInputStream.read(buffer)) != -1) {
		    result.write(buffer, 0, length);
		}
		template= result.toString("UTF-8");
	}
	
	/**
	 * Generates a new VueJS component and save it to a file
	 * @param pathFilename the path file name (from static folder)
	 * @param minify if true minify the javascript source code
	 * @throws IOException for file creation
	 */
	public void createFile(String pathFilename,boolean minify) throws IOException {
		File resource = new File(ROOT_FOLDER);
		File f=new File(resource.getAbsolutePath().toString()+"\\"+pathFilename);
		f.getParentFile().mkdirs();
		if(f.exists()) {
			Scanner sc=new Scanner(System.in);
			System.out.println("File "+f.getName()+" exists, override it?");
			new CommandPrompt(sc, "Your choice: ", 
					new CommandAction(()->{try {
						writeInfile(f, pathFilename,minify);
					} catch (IOException e) {
						e.printStackTrace();
					}},"y"),
					new CommandAction(()->{System.out.println("Canceled");return;},"n"));
		}else if(f.createNewFile()) {
			writeInfile(f, pathFilename,minify);
		}else {
			System.out.println("Could not create file "+f.getName());
		}
	}
	
	public void createFile(boolean minify) throws IOException {
		String path=VueConfig.getComponentFolder()+"/"+name+".js";
		createFile(path, minify);
	}
	
	private void writeInfile(File f,String pathFilename,boolean minify) throws IOException {
		Writer writer=null;
		try {
			writer = new BufferedWriter(new FileWriter(f));
			internal=true;
			template=template.replace("\r\n", "").replace("\t", "    ");
			String contents = addGenerated()+this;
			if(!minify) {
				contents=JsUtils.cleanJS(contents);
			}
	        writeInfile(f, pathFilename, contents, "Script generated in "+f.getCanonicalPath()+"\r\nInsert <script src=\"/"+pathFilename+"\"></script> in html file to use it.");
		}finally{
			try{
				if ( writer != null)
					writer.close( );
			}catch ( IOException e){}
		}
	}
	
	private static void writeInfile(File f,String pathFilename,String contents,String consoleMessages) throws IOException {
		Writer writer=null;
		try {
			writer = new BufferedWriter(new FileWriter(f));
			writer.write(contents);
	        System.out.println(consoleMessages);
		}finally{
			try{
				if ( writer != null)
					writer.close( );
			}catch ( IOException e){}
		}
	}
	
	private static String addGenerated() {
		return "//Script generated with "+VueComponent.class.getSimpleName()+" at "+new Date()+"\r\n";
	}
	
	public static void globalJs() {
		File resource = new File(ROOT_FOLDER);
		String path=resource.getAbsolutePath().toString()+"/"+VueConfig.getComponentFolder();
		try (Stream<Path> walk = Files.walk(Paths.get(path))) {

			List<String> result = walk.map(x -> x.toString())
					.filter(f -> f.endsWith(".js")).collect(Collectors.toList());

			globalJs(result.toArray(new String[0]));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void globalJs(String...files ) throws IOException {
		StringBuilder sb = new StringBuilder("");
		for(int i=0;i<files.length;i++) {
			File f=new File(files[i]);
			if(f.exists() && !f.getName().equals("components.js")) {
				Scanner scanner = new Scanner(f);
				while (scanner.hasNextLine()) {
					String line=scanner.nextLine();
					if(!line.startsWith("//")) {
						sb.append(line+"\r\n");
					}
				}
				scanner.close();
			}
		}
		if(files.length>0) {
			sb.append(addGenerated());
			String pathFilename=VueConfig.getComponentFolder()+"/components.js";
			File resource = new File(ROOT_FOLDER);
			File f=new File(resource.getAbsolutePath().toString()+"\\"+pathFilename);
			writeInfile(f, pathFilename, sb.toString(), "Script generated in "+f.getCanonicalPath()+"\r\nInsert <script src=\"/"+pathFilename+"\"></script> in html file to use it.");
		}else
			System.out.println("No files to parse.");
	}
}
