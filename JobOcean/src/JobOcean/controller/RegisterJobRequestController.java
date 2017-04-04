package JobOcean.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;







import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.repackaged.org.json.JSONObject;

import JobOcean.common.GlobalConstants;
import JobOcean.dto.JobRequestDto;
import JobOcean.service.JobRequestService;


/**
 * Controller that will accessed when 'localhost:8888' is entered.
 * @author Ed Stephen Koh
 * @version 0.01
 * Version History
 * [09/29/2016] 0.01 - Ed Stephen Koh - Initial codes.
 */
public class RegisterJobRequestController extends Controller {

    JobRequestService jobRequestService = new JobRequestService();
    
    @Override
    public Navigation run() throws Exception {
        System.out.println("JobRequestController.run " + "start");
        
        JobRequestDto jobRequestDto = new JobRequestDto();
        JSONObject jsonObject = null;
        try{
            jsonObject = new JSONObject(this.request.getReader().readLine());
            String idString = ""+jsonObject.getInt("id");
            String jobRequestNumber = jsonObject.getString("jobRequestNumber");
            String description = jsonObject.getString("description");
            String startDate = jsonObject.getString("startDate");
            System.out.print(startDate);
            String endDate = jsonObject.getString("endDate");
            String status = jsonObject.getString("status");
            Long id = Long.parseLong(idString);
            ArrayList<String> contracts = new ArrayList<String>();
            
            Date startdate = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
            Date enddate = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);

            jobRequestDto.setCompanyId(id);
            jobRequestDto.setJobRequestNumber(jobRequestNumber);
            jobRequestDto.setDescription(description);
            jobRequestDto.setContracts(contracts);
            jobRequestDto.setStartDate(startdate);
            jobRequestDto.setEndDate(enddate);
            jobRequestDto.setStatus(status);
            
            jobRequestService.insertJobRequest(jobRequestDto);
            
        }
        catch(Exception e){
            System.out.println("RegisterJobRequestController.run.exception " + e.toString());
        }
        // add the error message to the json object.
        jsonObject.put("errorList", jobRequestDto.getErrorList());
        
        // set the type of response.
        response.setContentType(GlobalConstants.SYS_CONTENT_TYPE_JSON);
        // send the response back to the JS file.
        response.getWriter().write(jsonObject.toString());

        System.out.println("JobRequestController.run " + "end");
        return null;
    }
}
