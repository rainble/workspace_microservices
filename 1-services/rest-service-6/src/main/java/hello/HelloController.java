package hello;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class HelloController {
    
    private static final Logger log = LoggerFactory.getLogger(Application.class);
    @Autowired
	private RestTemplate restTemplate;
    
    @Autowired
	private StringRedisTemplate template;

    @RequestMapping("/hello6")
    public String hello6(HttpSession session, 
    		@RequestHeader(value="user-token",required=false) String token, 
    		@RequestParam(value="optVal", required=false) String optVal)  throws InterruptedException, ExecutionException{

    	ValueOperations<String, String> ops = this.template.opsForValue();
		String key = token != null ? token : UUID.randomUUID().toString();
		System.out.println("Found key " + key + ", value=" + ops.get(key));
        
		HttpHeaders headers = new HttpHeaders();
		headers.add("user-token", key);
		ResponseEntity<String> exchange = restTemplate.exchange("http://rest-service-3:16003/hello3?name=service-6" + (optVal!=null?("&optVal="+optVal):""), 
				HttpMethod.GET,new HttpEntity<Void>(headers), String.class);
		String value = exchange.getBody();
        
		return value;
    }
    
    
    
    @RequestMapping("/process6")
    public String process6(HttpSession session, 
    		@RequestHeader(value="user-token",required=false) String token, 
    		@RequestParam(value="optVal", required=false) String optVal)  throws Exception{

    	ValueOperations<String, String> ops = this.template.opsForValue();
		String key = token != null ? token : UUID.randomUUID().toString();
		System.out.println("Found key " + key + ", value=" + ops.get(key));
        
		HttpHeaders headers = new HttpHeaders();
		headers.add("user-token", key);
		
		ResponseEntity<String> exchange = restTemplate.exchange("http://rest-service-3:16003/hello3?name=service-6&optVal=temp_approve", 
				HttpMethod.GET,new HttpEntity<Void>(headers), String.class);
		
		if(Math.random() < 0.6){
			ResponseEntity<String> exchange2 = restTemplate.exchange("http://rest-service-4:16004/hello4?optVal=temp_reject",
					HttpMethod.GET,new HttpEntity<Void>(headers), String.class);
		}else{
			ResponseEntity<String> exchange2 = restTemplate.exchange("http://rest-service-5:16005/hello5?optVal=temp_reject",
					HttpMethod.GET,new HttpEntity<Void>(headers), String.class);
		}
			
			
		ResponseEntity<String> exchange3 = restTemplate.exchange("http://rest-service-3:16003/hello3?name=service-6", 
				HttpMethod.GET,new HttpEntity<Void>(headers), String.class);
		
		String value = exchange3.getBody();
		
		log.info("--------------" + value);
		
		if(!"temp_reject".equals(value)){
			throw new Exception("error status!!!");
		}
        
		return value;
    }
}

