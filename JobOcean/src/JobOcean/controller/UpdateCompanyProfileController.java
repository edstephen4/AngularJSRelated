package JobOcean.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.repackaged.org.json.JSONObject;

import JobOcean.common.GlobalConstants;
import JobOcean.dto.CompanyDto;
import JobOcean.service.CompanyService;

public class UpdateCompanyProfileController extends Controller {

    CompanyService companyService = new CompanyService();
    
    @Override
    public Navigation run() throws Exception {
        System.out.println("CompanyProfileController.run " + "start");
        /**
         * Used to store the information from the request and send to the
         * service class.
         */
        CompanyDto companyDto = new CompanyDto();
        JSONObject json = null;
        try {  
            json = new JSONObject(this.request.getReader().readLine());
            Long id = json.getLong("id");
            String address = json.getString("address");
            String companyName = json.getString("companyName");
            String companyUrl = json.getString("companyUrl");
            String telNumber = json.getString("telNumber");
            
            companyDto.setAddress(address);
            companyDto.setCompanyName(companyName);
            companyDto.setCompanyUrl(companyUrl);
            companyDto.setId(id);
            companyDto.setTelNumber(telNumber);
             //AVILA//
            if(!companyService.isCompanyNameTaken(companyName)){
                companyService.updateCompanyProfile(companyDto);
              
                HttpSession session=request.getSession(false);  
                session.setAttribute("companyId",id);  
                session.setAttribute("companyName",companyName);  
                session.setAttribute("companyAddress",address);  
                session.setAttribute("companyTelephone",telNumber);  
                session.setAttribute("companyUrl",companyUrl);
                //status == 0 , no error
                json.put("status", 0);
            }
            else{
                //status == 1 , company name already exists
                json.put("status", 1);
            }
			 //AVILA//
        } catch (Exception e) {
            System.out.println("CompanyProfileController.run.exception " + e.toString());
        }  
        
        // set the type of response.
        response.setContentType(GlobalConstants.SYS_CONTENT_TYPE_JSON);
        // send the response back to the JS file.
        response.getWriter().write(json.toString());
        
        System.out.println("CompanyProfileController.run " + "end");
        // screen redirection.
        return null;
    }
}
