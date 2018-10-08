# SpringBoot-VueJS 
`SpringBoot-VueJS`adds `VueJS` to a `SpringBoot` Project to Create Client-Side Application Logic Within Spring Controllers.
## Maven integration

```xml
<dependency>
    <groupId>io.github.jeemv.springboot.vuejs</groupId>
    <artifactId>springboot-vuejs</artifactId>
    <version>1.0.2</version>
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

Mustache view use double mustache for variable, so the VueJS instance is set by default to use `<%` and `%>` as delimiters.

The `vue` variable generates the javascript code for the view instance creation. The triple mustache `{{{vue}}}`is use for unescape javascript/html code.


It is possible to avoid instantiation, and the passage of the variable to the view :

#### With @ModelAttribute annotation

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
## Configuration
### VueJS delimiters
Default delimiters are `<%` and `%>`.
For changing the plain text interpolation delimiters and avoid conflict with other template packages, you can modify them with:
```java
vue.setDelimiters("{!","!}");
```
