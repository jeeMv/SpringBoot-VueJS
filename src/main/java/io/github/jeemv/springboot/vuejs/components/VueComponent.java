package io.github.jeemv.springboot.vuejs.components;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.Date;
import java.util.Scanner;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.jeemv.springboot.vuejs.AbstractVueJS;
import io.github.jeemv.springboot.vuejs.VueConfig;
import io.github.jeemv.springboot.vuejs.beans.RawObject;
import io.github.jeemv.springboot.vuejs.console.CommandAction;
import io.github.jeemv.springboot.vuejs.console.CommandPrompt;
import io.github.jeemv.springboot.vuejs.parts.VueProps;
import io.github.jeemv.springboot.vuejs.utilities.JsUtils;

/**
 * VueJS component class
 * @author jcheron
 * @version 1.0.0.0
 */
public class VueComponent extends AbstractVueJS{
	private String name;
	private String template;
	private boolean internal;
	private VueProps props;
	
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
		Resource resource = new ClassPathResource("static/");
		File f=new File(resource.getFile().getAbsolutePath().toString()+"\\"+pathFilename);
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
	        writer.write(contents);
	        System.out.println("Script generated in "+f.getCanonicalPath());
	        System.out.println("Insert <script src=\"/"+pathFilename+"\"></script> in html file to use it.");
		}finally{
			try{
				if ( writer != null)
					writer.close( );
			}catch ( IOException e){}
		}
	}
	
	private String addGenerated() {
		return "//Script generated with "+this.getClass().getSimpleName()+" at "+new Date()+"\r\n";
	}
}
