package JobOcean.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.repackaged.org.json.JSONArray;
import org.slim3.repackaged.org.json.JSONObject;

import JobOcean.common.GlobalConstants;
import JobOcean.dto.CompanyDto;
import JobOcean.dto.ContractDto;
import JobOcean.dto.ContractListDto;
import JobOcean.dto.JobRequestListDto;
import JobOcean.service.CompanyService;
import JobOcean.service.ContractService;

/**
 * Controller used to update a 'bento' to the datastore.
 * @author Ed Stephen Z. Koh
 * @version 0.01
 * Version History
 * [08/30/2016] 0.01 - Ed Stephen Z. Koh - Initial codes.
 */

public class UpdateCompanyContractStatusController extends Controller {

    ContractService contractService = new ContractService();    
    
    @Override
    public Navigation run() throws Exception {
        
        System.out.println("LoginController.run " + "start");
        /**
         * Holds the information to be passed in the response.
         */
        
        ContractDto contractDto = new ContractDto();
        
        
        JSONObject json = null;
        try {
            json = new JSONObject(this.request.getReader().readLine());
            
            // getting the list of items
            String status = json.getString("status");
            String contractNumber = json.getString("contractNumber");
            String closedDate = json.getString("closedDate");
            

            Date closeddate = new SimpleDateFormat("yyyy-MM-dd").parse(closedDate);
            
            contractDto.setStatus(status);
            contractDto.setClosedDate(closeddate);
            contractDto.setContractNumber(contractNumber);
            
            contractService.updateContractStatus(contractDto);
                    
            // adding the list to the json that will be passed as response.
        } catch (Exception e) {
            System.out.println(e.toString());
            // Adds an error message if there exists.
            //contractListDto.addError(GlobalConstants.ERR_SERVER_CONTROLLER_PREFIX + e.getMessage());
            // initialize the json object that will be passed as response.
            if (null == json) {
                json = new JSONObject();
            }
        }
        // add the error message to the json object.
        //json.put("errorList", contractListDto.getErrorList());
        
        // set the type of response.
        response.setContentType(GlobalConstants.SYS_CONTENT_TYPE_JSON);
        // send the response back to the JS file.
        response.getWriter().write(json.toString());
        
        System.out.println("LoginController.run " + "end");
        return null;
    }
}
