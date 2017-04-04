package JobOcean.controller;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.repackaged.org.json.JSONObject;

import JobOcean.common.GlobalConstants;
import JobOcean.dto.JobRequestDto;
import JobOcean.service.JobRequestService;

/**
 * Controller that will accessed when 'localhost:8888' is entered.
 * @author Jireh Bautista
 * @version 0.01
 * Version History
 * [08/31/2016] 0.01 - Jireh Bautista - Initial codes.
 */

public class DeleteJobRequestController extends Controller {

    JobRequestService jobRequestService = new JobRequestService();
    
    @Override
    public Navigation run() throws Exception {
        System.out.println("DeleteJobRequestController.run " + "start");
        /**
         * Used to store the information from the request and send to the
         * service class.
         */
        JobRequestDto jobRequestDto = new JobRequestDto();
        
        JSONObject json = null;
        try {
            json = new JSONObject(this.request.getReader().readLine());
            String jobReqNumber = json.getString("jobRequestNumber");
            
            jobRequestDto.setJobRequestNumber(jobReqNumber);
            
            // deleting the job request with the given job request number
            jobRequestService.deleteJobRequest(jobRequestDto);
        } catch (Exception e) {
            System.out.println(e.toString());
            // Adds an error message if there exists.
            jobRequestDto.addError(GlobalConstants.ERR_SERVER_CONTROLLER_PREFIX + e.getMessage());
            // initialize the json object that will be passed as response.
            if (null == json) {
                json = new JSONObject();
            }
        }
        // add the error message to the json object.
        json.put("errorList", jobRequestDto.getErrorList());
        
        // set the type of response.
        response.setContentType(GlobalConstants.SYS_CONTENT_TYPE_JSON);
        // send the response back to the JS file.
        response.getWriter().write(json.toString());
        System.out.println("DeleteJobRequestController.run " + "end");
        // screen redirection.
        return null;
    }
}
