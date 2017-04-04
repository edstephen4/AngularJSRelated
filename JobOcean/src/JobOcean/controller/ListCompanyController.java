package JobOcean.controller;


import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.repackaged.org.json.JSONObject;

import JobOcean.common.GlobalConstants;
import JobOcean.dto.CompanyListDto;
import JobOcean.service.CompanyService;



/**
 * List of Company Controller
 * @author Jireh Bautista
 * @version 0.01
 * Version History
 * [09/29/2016] 0.01 – Jireh Bautista  – Initial codes.
 */
public class ListCompanyController extends Controller {
    
    /**
     * Instance of the TodoService that will be used to call the delete function.
     */
    private CompanyService companyService = new CompanyService(); 
    
    /**
     * The function that is ran when the controller is called.
     */
    @Override
    public Navigation run() throws Exception {
        System.out.println("ListCompanyController.run " + "start");
        /**
         * Use to store the information that will be passed to the service.
         */
        CompanyListDto companyListDto = new CompanyListDto();
        /**
         * Holds the information to be passed in the response.
         */
        JSONObject json = null;
        try {
            json = new JSONObject();
            // getting the list of items
            companyListDto = companyService.getAllCompanies();
            // adding the list to the json that will be passed as response.
            json.put("companyList", companyListDto.getEntries());
            
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            // Adds an error message if there exists.
            companyListDto.addError(GlobalConstants.ERR_SERVER_CONTROLLER_PREFIX + e.getMessage());
            // initialize the json object that will be passed as response.
            if (null == json) {
                json = new JSONObject();
            }
        }
        // add the error message to the json object.
        json.put("errorList", companyListDto.getErrorList());
        
        // set the type of response.
        response.setContentType(GlobalConstants.SYS_CONTENT_TYPE_JSON);
        // send the response back to the JS file.
        response.getWriter().write(json.toString());
        
        System.out.println("ListCompanyController.run " + "end");
        return null;
    }
}
