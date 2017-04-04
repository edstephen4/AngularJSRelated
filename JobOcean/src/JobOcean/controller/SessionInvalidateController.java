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

public class SessionInvalidateController extends Controller {
   
    @Override
    public Navigation run() throws Exception {
        
        System.out.println("SessionInvalidateController.run " + "start");
        
        HttpSession session=request.getSession();  
        session.invalidate();  
        System.out.println("session invalidated");
        
        System.out.println("SessionInvalidateController.run " + "end");
        return null;
    }
}
