package JobOcean.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
 * @author Ed Stephen Koh
 * @version 0.01
 * Version History
 * [09/29/2016] 0.01 - Ed Stephen Koh - Initial codes.
 */

public class RegisterContractController extends Controller {

    ContractService contractService = new ContractService();
    
    @Override
    public Navigation run() throws Exception {
        System.out.println("ContractController.run " + "start");
        
        ContractDto contractDto = new ContractDto();
        JSONObject jsonObject = null;
        try{
            jsonObject = new JSONObject(this.request.getReader().readLine());
            String jobRequestNumber = jsonObject.getString("jobRequestNumber");
            String contractType = jsonObject.getString("type");
            String contractorName = jsonObject.getString("contractorName");
            String contractNumber = jsonObject.getString("contractNumber");
            String startDate = jsonObject.getString("startDate");
            String endDate = jsonObject.getString("endDate");
            String status = jsonObject.getString("status");
            

            Date startdate = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
            Date enddate = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
            
            contractDto.setType(contractType);
            contractDto.setContractNumber(contractNumber);
            contractDto.setContractorName(contractorName);
            contractDto.setStartDate(startdate);
            contractDto.setEndDate(enddate);
            contractDto.setStatus(status);
            
            contractService.insertContract(contractDto, jobRequestNumber);
        }
        catch(Exception e){
            System.out.println("RegisterContractController.run.exception " + e.toString());
        }
        // add the error message to the json object.
        jsonObject.put("errorList", contractDto.getErrorList());
        
        // set the type of response.
        response.setContentType(GlobalConstants.SYS_CONTENT_TYPE_JSON);
        // send the response back to the JS file.
        response.getWriter().write(jsonObject.toString());

        System.out.println("ContractController.run " + "end");
        return null;
    }
}
