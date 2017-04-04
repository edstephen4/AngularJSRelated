package JobOcean.controller;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.repackaged.org.json.JSONObject;

import JobOcean.common.GlobalConstants;
import JobOcean.dto.ContractDto;
import JobOcean.dto.JobRequestDto;
import JobOcean.service.ContractService;
import JobOcean.service.JobRequestService;

/**
 * Controller that will accessed when 'localhost:8888' is entered.
 * @author Jireh Bautista
 * @version 0.01
 * Version History
 * [08/31/2016] 0.01 - Jireh Bautista - Initial codes.
 */

public class DeleteContractController extends Controller {

    ContractService contractService = new ContractService();
    
    @Override
    public Navigation run() throws Exception {
        System.out.println("DeleteJobRequestController.run " + "start");
        /**
         * Used to store the information from the request and send to the
         * service class.
         */
        ContractDto contractDto = new ContractDto();
        JobRequestDto jobRequestDto = new JobRequestDto();
        
        JSONObject json = null;
        try {
            json = new JSONObject(this.request.getReader().readLine());
            String contractNumber = json.getString("contractNumber");
            
            contractDto.setContractNumber(contractNumber);
            
            // deleting the contract with the given contract number
            contractService.deleteContract(contractDto);
            
            //deleting the contracts under the job request with the given contract number
            contractService.deleteContractInJobrequest(contractDto);
            
        } catch (Exception e) {
            System.out.println(e.toString());
            // Adds an error message if there exists.
            contractDto.addError(GlobalConstants.ERR_SERVER_CONTROLLER_PREFIX + e.getMessage());
            // initialize the json object that will be passed as response.
            if (null == json) {
                json = new JSONObject();
            }
        }
        // add the error message to the json object.
        json.put("errorList", contractDto.getErrorList());
        
        // set the type of response.
        response.setContentType(GlobalConstants.SYS_CONTENT_TYPE_JSON);
        // send the response back to the JS file.
        response.getWriter().write(json.toString());
        System.out.println("DeleteJobRequestController.run " + "end");
        // screen redirection.
        return null;
    }
}
