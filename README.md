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
	@GetMapping("form")
	public String form(ModelMap model) {
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
