import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.json.JSONArray;
import org.json.JSONObject;

@ManagedBean(name = "securityAppMBean")
@SessionScoped
public class SecurityAppMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String URL_SERVICE_1 = "http://localhost:8080/Example-Application-Security-REST/securityapp/service1";
	private static final String URL_SERVICE_2 = "http://localhost:8080/Example-Application-Security-REST/securityapp/service2";

	private String username;
	private String name;
	
	private String resultHelloWorld, resultGreeting;
	private String resultListNames, resultAddName;

	public SecurityAppMBean() {
		this.username = "";
		this.name = "";
	}

	public void helloWorld() {
		String strJson = callWebservice(URL_SERVICE_1);
		JSONObject jsonObject = new JSONObject(strJson);
		resultHelloWorld = jsonObject.getString("result");
	}

	public void greeting() {
		if (!username.equals("")) {
			String strJson = callWebservice(URL_SERVICE_1 + "/" + username);
			JSONObject jsonObject = new JSONObject(strJson);
			resultGreeting = jsonObject.getString("result");
			username = "";
		} else {
			resultGreeting = "Fill the username field first!";
		}
	}

	public void listNames() {
		String strJson = callWebservice(URL_SERVICE_2);
		JSONObject jsonObject = new JSONObject(strJson);
		JSONArray jsonArrayNames = jsonObject.getJSONArray("names");		
		resultListNames = jsonArrayNames.toString();
	}

	public void addName() {
		if (!name.equals("")) {
			callWebservice(URL_SERVICE_2 + "/" + name);
			resultAddName = "Name added on list!";
			name = "";
		} else {
			resultAddName = "Fill the name field first!";
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getResultHelloWorld() {
		return resultHelloWorld;
	}

	public void setResultHelloWorld(String resultHelloWorld) {
		this.resultHelloWorld = resultHelloWorld;
	}

	public String getResultGreeting() {
		return resultGreeting;
	}

	public void setResultGreeting(String resultGreeting) {
		this.resultGreeting = resultGreeting;
	}

	public String getResultListNames() {
		return resultListNames;
	}

	public void setResultListNames(String resultListNames) {
		this.resultListNames = resultListNames;
	}

	public String getResultAddName() {
		return resultAddName;
	}

	public void setResultAddName(String resultAddName) {
		this.resultAddName = resultAddName;
	}

	private static String getStringFromInputStream(InputStream is) {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}
	
	private String callWebservice(String urlWebservice) {
		HttpURLConnection connection = null;
	    try {
	      URL url = new URL(urlWebservice);
	      connection = (HttpURLConnection)url.openConnection();
	        
	      InputStream inputStream = connection.getInputStream();
	      return getStringFromInputStream(inputStream);

	    } catch (IOException e) {
	      throw new RuntimeException(e);
	    } finally {
	      connection.disconnect();
	    }
	}

}
