package JobOcean.controller;

import javax.servlet.http.HttpSession;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.repackaged.org.json.JSONObject;

import JobOcean.common.GlobalConstants;
import JobOcean.dto.CompanyDto;
import JobOcean.dto.JobRequestListDto;
import JobOcean.service.CompanyService;

public class AdminLoginController extends Controller {

    CompanyService companyService = new CompanyService();    
    
    @Override
    public Navigation run() throws Exception {
        
        System.out.println("AdminLoginController.run " + "start");
        
        JSONObject json = new JSONObject(this.request.getReader().readLine());
        
        String username = json.getString("username");
        String password = json.getString("password");
        
        if(username.equals("admin")){
            if(password.equals("admin")){
                json.put("status", 0);
                HttpSession session=request.getSession();  
                session.setAttribute("type","admin");  
            } else {
                json.put("status", 2);
            }
        } else {
            json.put("status", 1);
        }
            
        // set the type of response.
        response.setContentType(GlobalConstants.SYS_CONTENT_TYPE_JSON);
        // send the response back to the JS file.
        response.getWriter().write(json.toString());

        
        System.out.println("AdminLoginController.run " + "end");
        return null;
    }
}
