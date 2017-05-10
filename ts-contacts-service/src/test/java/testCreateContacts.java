import com.google.gson.Gson;
import contacts.domain.AddContactsInfo;
import contacts.domain.DocumentType;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class testCreateContacts {
    public static void main(String[] args)throws Exception{
        URL url = new URL("http://10.141.212.21:12347/createNewContacts");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(true);
        connection.setRequestProperty("Content-Type","application/json; charset=UTF-8");
        connection.connect();
        //POST请求
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        //添加新联系人
        AddContactsInfo aci = new AddContactsInfo();
        aci.setAccountId(UUID.fromString("906cc3da-b6b6-491f-8e13-cc72c5191ea2"));
        aci.setLoginToken("e50c7c87-c9bf-4278-8ff9-9bbcfdc0585d");
        aci.setDocumentNumber("142603199601311011");
        aci.setDocumentType(DocumentType.ID_CARD.getCode());
        aci.setName("jichao");
        aci.setPhoneNumber("+86 15221870263");
        Gson gson = new Gson();
        String str = gson.toJson(aci);
        System.out.println(str);
        //写入
        out.write(str.getBytes("UTF-8"));//这样可以处理中文乱码问题
        out.flush();
        out.close();
        //读取响应
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));
        String lines;
        StringBuffer sb = new StringBuffer("");
        while ((lines = reader.readLine()) != null) {
            lines = new String(lines.getBytes(), "utf-8");
            sb.append(lines);
        }
        System.out.println(sb);
        reader.close();
        // 断开连接
        connection.disconnect();
    }
}
