package JobOcean.controller;

import javax.servlet.http.HttpSession;

import org.mortbay.util.ajax.JSON;
import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.repackaged.org.json.JSONObject;

import JobOcean.common.GlobalConstants;
import JobOcean.dto.CompanyDto;
import JobOcean.dto.JobRequestListDto;
import JobOcean.service.CompanyService;

public class SessionCheckerController extends Controller {
   
    @Override
    public Navigation run() throws Exception {
        
        System.out.println("SessionCheckerController.run " + "start");
        
        HttpSession session = request.getSession(false);  
        JSONObject json = new JSONObject();
        
        if(session!=null){  
            json.put("status", true);   
            String type = (String)session.getAttribute("type");
            if(type == null){
                session.invalidate();
            }
            
            else if(type.equals("user")){
				json.put("companyId",session.getAttribute("companyId"));
				json.put("companyName",session.getAttribute("companyName"));  
				json.put("companyAddress",session.getAttribute("companyAddress"));  
				json.put("companyTelephone",session.getAttribute("companyTelephone"));  
				json.put("companyUrl",session.getAttribute("companyUrl")); 
			}			
            json.put("type", type);
        }  
        else{   
           json.put("status", false);
        }  

        
        // set the type of response.
        response.setContentType(GlobalConstants.SYS_CONTENT_TYPE_JSON);
        // send the response back to the JS file.
        response.getWriter().write(json.toString());
        
        System.out.println("SessionCheckerController.run " + "end");
        return null;
    }
}
