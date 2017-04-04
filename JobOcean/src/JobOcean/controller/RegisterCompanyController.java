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


/**
 * Controller class used to get the list of todos.
 * @author Ed Stephen Z. Koh
 * @version 0.01
 * Version History
 * [03/29/2016] 0.01 – Ed Stephen Z. Koh  – Initial codes.
 */
public class RegisterCompanyController extends Controller {

    CompanyService companyService = new CompanyService();
    
    @Override
    public Navigation run() throws Exception {
        
        System.out.println("CompanyController.run " + "start");
        
        CompanyDto companyDto = new CompanyDto();
        JSONObject json = null;
        try {
            json = new JSONObject(this.request.getReader().readLine());
            String companyName = json.getString("companyName");
            String address = json.getString("address");
            String telNumber = json.getString("telephoneNumber");
            String companyUrl = json.getString("companyUrl");
            String email = json.getString("email");
            String password = json.getString("password");
            
            companyDto.setAddress(address);
            companyDto.setCompanyName(companyName);
            companyDto.setCompanyUrl(companyUrl);
            companyDto.setPassword(password);
            companyDto.setTelNumber(telNumber);
            companyDto.setEmail(email);
            
            if(!companyService.isCompanyNameTaken(companyName)){
                if(!companyService.isEmailTaken(email)){
                    companyService.insertCompany(companyDto);
                    //status == 0 , no error
                    Long id = companyService.getCompanyIdByEmail(email);
                    json.put("companyId", id);

                    HttpSession session=request.getSession();  
                    session.setAttribute("type","user");  
                    session.setAttribute("companyId",id);  
                    session.setAttribute("companyName",companyName);  
                    session.setAttribute("companyAddress",address);  
                    session.setAttribute("companyTelephone",telNumber);  
                    session.setAttribute("companyUrl",companyUrl);
                    
                    json.put("flag", 0);
                } else {
                    json.put("flag", 2);
                }
            }
            else{
                //status == 1 , company name already exists
                json.put("flag", 1);
            }
            
        }
        catch (Exception e) {
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
