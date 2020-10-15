# SpringBoot-VueJS 

[![Join the chat at https://gitter.im/SpringBoot-VueJS/community](https://badges.gitter.im/SpringBoot-VueJS/community.svg)](https://gitter.im/SpringBoot-VueJS/community?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
![Maven Central](https://img.shields.io/maven-central/v/io.github.jeemv.springboot.vuejs/springboot-vuejs)

`SpringBoot-VueJS`adds `VueJS` to a `Spring-boot` Project for Creating Client-Side Application Logic Within Spring Controllers.
## Maven integration
Insert the dependency in your `pom.xml`file:
```xml
<dependency>
    <groupId>io.github.jeemv.springboot.vuejs</groupId>
    <artifactId>springboot-vuejs</artifactId>
    <version>[1.0,)</version>
</dependency>
```
## Documentation
 - [api documentation](https://api.kobject.net/springBoot-VueJS/)

## Usage

### Manual instanciation in a Spring controller
```java
@Controller
@RequestMapping("/ui/")
public class UiTest {
	@GetMapping("test")
	public String index(ModelMap model) {
		VueJS vue=new VueJS("#app");
		vue.addData("message", "Hello world!");
		model.put("vue", vue);
		return "index";
	}
}
```

The `index.html` mustache view:
```html
<div id="app">
<%message%>
<input v-model="message">
</div>
{{{vue}}}
```

Mustache view use double mustache for variables (message in the example), so the VueJS instance is set by default to use `<%` and `%>` as delimiters.

The `vue` variable generates the javascript code for the view instance creation. The triple mustache `{{{vue}}}`is use for javascript/html code unescaping.

#### With @AutoWired annotation
This technique has the advantage of providing a globale instance of VueJS for all the actions of a controller.
Create a configuration class to allow the autowiring of **VueJS**:

```java
@Configuration
@ComponentScan("io.github.jeemv.springboot.vuejs")
public class AppConfiguration {
}
```
In your controller:

```java
@Controller
@RequestMapping("/ui/")
public class UiTest {

	@AutoWired
	private VueJS vue;

        @ModelAttribute(name = "vue")
        private VueJS getVue() {
            return this.vue;
        }
	
	@GetMapping("test")
	public String index(ModelMap model) {
		vue.addData("message", "Hello world!");
		return "index";
	}
}
```
In this case, you can directly configure **VueJS** in the **application.properties** file:

```bash
springboot.vuejs.delimiters=<%,%>
springboot.vuejs.axios=true
springboot.vuejs.el=v-app
```

#### With @ModelAttribute annotation

For a more punctual use, in a single method for example, It is possible to use the **@ModelAttribute** annotation :

```java
@Controller
@RequestMapping("/ui/")
public class UiTest {

	@ModelAttribute("vue")
	public VueJS getVue() {
		return new VueJS("#app");
	}
	
	@GetMapping("test")
	public String index(@ModelAttribute("vue") VueJS vue) {
		vue.addData("message", "Hello world!");
		return "index";
	}
}
```

#### With @VueJSInstance annotation and Spring AOP

AOP loading in `pom.xml`:
```xml
	    <dependency>
	        <groupId>org.springframework.boot</groupId>
	        <artifactId>spring-boot-starter-aop</artifactId>
	    </dependency>
```
AOP activation in app config file:
```java
@Configuration
@ComponentScan("io.github.jeemv.springboot.vuejs.aspects")
@EnableAspectJAutoProxy
public class AppConfig {

}
```
AOP usage in controller:
```java
@Controller
@RequestMapping("/ui/")
public class UiTest {
	@GetMapping("test")
	@VueJSInstance
	public String test2(VueJS vue,ModelMap model) {
		vue.addData("message", "Hello world!");
		return "index";
	}
}
```

## Methods
### addData
Adds data object for the Vue instance.
```java
vue.addData("visible",false);
vue.addData("group",group);//where group is an instance of the class Group
vue.addData("users",users);//where users is an ArrayList of User
```
### addMethod
Adds a method to the vue instance
```java
vue.addMethod("toggleVisible", "this.visible=!this.visible;");
vue.addMethod("addScore","this.scores.push(score)","score");
```

### addComputed
Adds a computed property to the vue instance

```java
vue.addComputed("count", "return this.users.length");
```

A computed property can have a setter:

```java
vue.addMethod("fullname","return this.firstName + ' ' + this.lastName;","var names = newValue.split(' ');
                                                                        this.firstName = names[0];
									this.lastName = names[names.length - 1];");
```

### addWatcher
Adds a watcher on variable to the view instance

```java
vue.addWatcher("value", "'value was '+oldValue+'. It is now '+val;");
```

## Components
It is possible to create components at runtime, but it is more efficient to generate them before:

```java
public class CompoButton {
	public static void main (String[] args) throws java.lang.Exception  {
		VueComponent compo=new VueComponent("button-counter");
		compo.addData("count", 0);
		compo.setTemplate("<button @click=\"count++\">You clicked me {{ count }} times.</button>");
		compo.createFile(false);
	}
}
```
The generated file is created in `{project-folder}/src/main/resources/static/vueJS/button-counter.js`

```javascript
//Script generated with VueComponent at Thu Oct 11 03:01:09 CEST 2018
Vue.component('button-counter',{
	"data":function() {
		 return {
			"count":0
			};
		}
	,"template":"<button @click=\"count++\">You clicked me {{ count }} times.</button>"
	}
);
```
Usage:

```html
<script src="/vueJS/button-counter.js"></script>
...
<button-counter></button-counter>
```
### Component with template file
Templates are easier to create in a file:

Create the file `/src/main/resources/templates/vueJS/button-counter.html`
```html
<button @click="count++">
	You clicked me {{ count }} times.
</button>
```
Modify the class `CompoButton`:
```java
public class CompoButton {
	public static void main (String[] args) throws java.lang.Exception  {
		VueComponent compo=new VueComponent("button-counter");
		compo.addData("count", 0);
		compo.setDefaultTemplateFile();
		compo.createFile(false);
	}
}
```
the generated file is the same, but the method is more convenient.

## Configuration
### VueJS delimiters
Default delimiters are `<%` and `%>`.
For changing the plain text interpolation delimiters and avoid conflict with other template packages, you can modify them with:
```java
vue.setDelimiters("{!","!}");
```

## Http methods

You can also generate code to perform ajax queries:

### Submit a form

```java
vue.addMethod("submit",Http.postForm("formRef","console.log('submit datas!')"));
```
### Other Http methods
HTTP calls from Vue.js to SpringBoot REST backend:

```java
vue.addMethod("saveUser",Http.post("user/","user","console.log('User added!')"),"user");
vue.addMethod("updateUser",Http.put("user/","user","console.log('User updated!')"),"user");
```

For axios, Do not forget to include the corresponding js file.

### Javascript resource
The javascript code is sometimes too large to be neatly integrated into a java controller.
In this case, it can be delocalized in a javascript file, which can refer to java variables of the controller.

#### resource/static/js/sample.js
The java variables are parsed with `${varName}` usage.

```javascript
//resource/static/js/sample.js
console.log("${message}");
```
#### In the java controller
```java
	@GetMapping("sample")
	public String testJs(@ModelAttribute("vue") VueJS vue) throws IOException {
		JavascriptResource js = JavascriptResource.create("sample");
		js.put("message", "Hello world!");
		vue.addMethod("click", js.parseContent());
		return "view";
	}
```
### Javascript multi modules resource
To avoid the multiplicity of javascript files, it is possible to group several scripts in the same file.

Each script (qualified as a module) must be identified in the javascript file by a comment on a single line bearing its name, and a comment marking the end (also mentioning the name of the script).

#### resource/static/js/multi.js
Each script can possibly be isolated, which is without consequences.

```javascript
//resource/static/js/multi.js
//----------------consoleMsg-----------------------
console.log("${message}");
//----------------consoleMsg (end)-----------------

//----------------alertMsg-------------------------
(function(){
    alert("${message}");
})();
//----------------alertMsg (end)-------------------
```
#### In the java controller
```java
	@GetMapping("sample")
	public String testJsMulti(@ModelAttribute("vue") VueJS vue) throws IOException {
		JavascriptMultiModulesResource jsMulti=JavascriptMultiModulesResource.create("multi");
		jsMulti.getModule("consoleMsg").put("message", "This is a console message");
		vue.addMethod("click", js.parseContent("consoleMsg"));
		return "view";
	}
```
