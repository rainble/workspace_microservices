/*
 * This Java source file was generated by the Gradle 'init' task.
 */

package org.services.analysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class CallAnalysis {

	public static void main(String[] args) {

		String call2x = readFile("./sample/call2x.json");
		JSONArray spanlist = new JSONArray(call2x);

		HashMap<String, HashMap<String, String>> elements = new HashMap<String, HashMap<String, String>>();


		for (int j = 0; j < spanlist.length(); j++) {
			JSONObject spanobj = (JSONObject) spanlist.get(j);

			String traceId = spanobj.getString("traceId");
			String id = spanobj.getString("id");
			String name = spanobj.getString("name");

			HashMap<String, String> content = new HashMap<String, String>();
			content.put("spanid", id);
			JSONArray annotations = spanobj.getJSONArray("annotations");
			for (int i = 0; i < annotations.length(); i++) {
				JSONObject anno = annotations.getJSONObject(i);
				if ("sr".equals(anno.getString("value"))) {
					JSONObject endpoint = anno.getJSONObject("endpoint");
					String service = endpoint.getString("serviceName");
					content.put("service", service);
				}
			}

			JSONArray binaryAnnotations = spanobj.getJSONArray("binaryAnnotations");
			for (int i = 0; i < binaryAnnotations.length(); i++) {
				JSONObject anno = binaryAnnotations.getJSONObject(i);
				if ("mvc.controller.class".equals(anno.getString("key"))) {
					String classname = anno.getString("value");
					content.put("classname", classname);
				}
				if ("mvc.controller.method".equals(anno.getString("key"))) {
					String methodname = anno.getString("value");
					content.put("methodname", methodname);
				}
			}
			elements.put("--" + traceId + "--" + id + "--" + name + "--", content);
		}
		elements.values().stream().forEach(n -> System.out.println(n));  

	}

	public static String readFile(String path) {
		File file = new File(path);
		BufferedReader reader = null;
		String laststr = "";
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				laststr = laststr + tempString;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return laststr;
	}
}
