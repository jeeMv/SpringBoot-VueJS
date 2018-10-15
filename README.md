# SpringBoot-VueJS 
`SpringBoot-VueJS`adds `VueJS` to a `SpringBoot` Project for Creating Client-Side Application Logic Within Spring Controllers.
## Maven integration
Insert the dependency in your `pom.xml`file:
```xml
<dependency>
    <groupId>io.github.jeemv.springboot.vuejs</groupId>
    <artifactId>springboot-vuejs</artifactId>
    <version>[1.0,)</version>
</dependency>
```
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


#### With @ModelAttribute annotation

It is possible to avoid instantiation, and the passage of the variable to the view :

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
The generated file is created in `{project-folder}/target/classes/static/vueJS/button-counter.js`

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
