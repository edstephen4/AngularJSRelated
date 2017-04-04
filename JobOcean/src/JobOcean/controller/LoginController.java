package JobOcean.controller;

import javax.servlet.http.HttpSession;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.repackaged.org.json.JSONObject;

import JobOcean.common.GlobalConstants;
import JobOcean.dto.CompanyDto;
import JobOcean.dto.JobRequestListDto;
import JobOcean.service.CompanyService;

public class LoginController extends Controller {

    CompanyService companyService = new CompanyService();    
    
    @Override
    public Navigation run() throws Exception {
        
        System.out.println("LoginController.run " + "start");
        
        CompanyDto companyDto = new CompanyDto();
        /**
         * Holds the information to be passed in the response.
         */
        JSONObject json = null;
        try {
            json = new JSONObject(this.request.getReader().readLine());
            String username = json.getString("username");
            String password = json.getString("password");
            
            companyDto.setEmail(username);
            companyDto.setPassword(password);
          
            int i = companyService.loginStatus(companyDto);
            if(i == 0){
                
                HttpSession session=request.getSession();  
                session.setAttribute("type","user");  
                
                // getting the company dto
                companyDto = companyService.confirmUser(companyDto);
                
                json.put("companyId", companyDto.getId());
                json.put("companyName", companyDto.getCompanyName());
                json.put("companyAddress", companyDto.getAddress());
                json.put("companyTelephone", companyDto.getTelNumber());
                json.put("companyUrl", companyDto.getCompanyUrl());
                
                session.setAttribute("companyId",companyDto.getId());  
                session.setAttribute("companyName",companyDto.getCompanyName());  
                session.setAttribute("companyAddress",companyDto.getAddress());  
                session.setAttribute("companyTelephone",companyDto.getTelNumber());  
                session.setAttribute("companyUrl",companyDto.getCompanyUrl());  
                
                json.put("loginStatus",0);
            } else if(i == 1){
                json.put("loginStatus", 1);
            } else if(i == 2){
                json.put("loginStatus", 2);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            // Adds an error message if there exists.
            companyDto.addError(GlobalConstants.ERR_SERVER_CONTROLLER_PREFIX + e.getMessage());
            // initialize the json object that will be passed as response.
            if (null == json) {
                json = new JSONObject();
            }
        }
        // add the error message to the json object.
        json.put("errorList", companyDto.getErrorList());
        
        // set the type of response.
        response.setContentType(GlobalConstants.SYS_CONTENT_TYPE_JSON);
        // send the response back to the JS file.
        response.getWriter().write(json.toString());
        
        System.out.println("LoginController.run " + "end");
        return null;
    }
}
