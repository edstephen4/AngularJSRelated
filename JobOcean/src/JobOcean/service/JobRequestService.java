package JobOcean.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import JobOcean.common.GlobalConstants;
import JobOcean.dao.ContractDao;
import JobOcean.dao.JobRequestDao;
import JobOcean.dto.CompanyDto;
import JobOcean.dto.ContractDto;
import JobOcean.dto.ContractListDto;
import JobOcean.dto.JobRequestDto;
import JobOcean.dto.JobRequestListDto;
import JobOcean.model.CompanyModel;
import JobOcean.model.ContractModel;
import JobOcean.model.JobRequestModel;

/**
 * Controller that will accessed when 'localhost:8888' is entered.
 * @author Ed Stephen Koh
 * @version 0.01
 * Version History
 * [08/30/2016] 0.01 - Ed Stephen Koh - Initial codes.
 */

public class JobRequestService {
	JobRequestDao jobRequestDao = new JobRequestDao();
    ContractDao contractDao = new ContractDao();
	
	public void insertJobRequest(JobRequestDto jobRequestDto){
	    System.out.println("JobRequestService.insertJobRequest " + "start");
		
		JobRequestModel jobRequestModel = new JobRequestModel();
		
		jobRequestModel.setCompanyId(jobRequestDto.getCompanyId());
		jobRequestModel.setJobRequestNumber(jobRequestDto.getJobRequestNumber());
		jobRequestModel.setDescription(jobRequestDto.getDescription());
		jobRequestModel.setStartDate(jobRequestDto.getStartDate());
		jobRequestModel.setEndDate(jobRequestDto.getEndDate());
        jobRequestModel.setStatus(jobRequestDto.getStatus());
        jobRequestModel.setContracts(jobRequestDto.getContracts());
		
		try{
			jobRequestDao.insertJobRequest(jobRequestModel);
		}
		catch(Exception e){
            System.out.println("Exception in inserting job request: " + e.toString());
		}
	    System.out.println("JobRequestService.insertJobRequest " + "end");
	}
	
	public void updateJobRequest(JobRequestDto inputJobRequest) {
        System.out.println("JobRequestService.updateJobRequest " + "start, end");
        /**
         * Used to store the data from the DTO object to the model.
         * Used as parameters in passing to the DAO layer.
         */
        JobRequestModel jobRequestModel = new JobRequestModel();
        
        jobRequestModel.setJobRequestNumber(inputJobRequest.getJobRequestNumber());
        jobRequestModel.setCompanyId(inputJobRequest.getCompanyId());
        jobRequestModel.setDescription(inputJobRequest.getDescription());
        jobRequestModel.setStartDate(inputJobRequest.getStartDate());
        jobRequestModel.setEndDate(inputJobRequest.getEndDate());
        jobRequestModel.setStatus(inputJobRequest.getStatus());

        try {
            
            JobRequestModel resultModel = jobRequestDao.getJobRequestById(jobRequestModel);
            if(resultModel != null){
                System.out.print("Job Request exist.");
                jobRequestModel.setId(resultModel.getId());
                jobRequestModel.setKey(resultModel.getKey());
                jobRequestModel.setContracts(resultModel.getContracts());
                jobRequestDao.updateJobRequest(jobRequestModel);
            }
            else{
                System.out.print("Job Request not found!");
            }
        } catch (Exception e) {
            System.out.println("Exception in updating job request: " + e.toString());
        }

        System.out.println("JobRequestService.updateJobRequest " + "start, end");
    }
	
	
	
	
	public void deleteJobRequest(JobRequestDto jobRequestDto){
	    System.out.println("JobRequestService.deleteJobRequest " + "start, end");
        /**
         * Used to store the data from the DTO object to the model.
         * Used as parameters in passing to the DAO layer.
         */
        JobRequestModel jobRequestModel = new JobRequestModel();
        
        jobRequestModel.setJobRequestNumber(jobRequestDto.getJobRequestNumber());     

        try {
            
            JobRequestModel resultModel = jobRequestDao.getJobRequestById(jobRequestModel);
            if(resultModel != null){
                //  jobRequestModel.setKey(resultModel.getKey());
                System.out.print("Job Request exists.");
                jobRequestDao.deleteJobRequest(resultModel);
            }
            else{
                System.out.print("Job Request not found!");
            }
        } catch (Exception e) {
            System.out.println("Exception in deleting job request: " + e.toString());
        }

        System.out.println("JobRequestService.deleteJobRequest " + "start, end");
	}
	
	public JobRequestListDto getJobRequestList(Long id) {
        System.out.println("JobRequestListDto.getJobRequestList " + "start");
        // initializing the dto to hold the list of Job Requests.
        JobRequestListDto jobRequestListDto =  new JobRequestListDto();
        
        try {
            // get the list of todos with the given id of the company. 
            List<JobRequestModel> jobRequestList = jobRequestDao.getJobRequestList(id);
            if (jobRequestList != null) {
                // convert each JobRequestModel from the jobRequestListDto into TodoDto JobRequestDto
                for (JobRequestModel resultModel : jobRequestList) {
                    JobRequestDto jobRequestDto = new JobRequestDto();
                    
                    jobRequestDto.setJobRequestNumber(resultModel.getJobRequestNumber());
                    jobRequestDto.setDescription(resultModel.getDescription());
                    jobRequestDto.setStartDate(resultModel.getStartDate());
                    jobRequestDto.setClosedDate(resultModel.getClosedDate());
                    jobRequestDto.setEndDate(resultModel.getEndDate());
                    jobRequestDto.setContracts(resultModel.getContracts());
                    jobRequestDto.setStatus(resultModel.getStatus());
                    jobRequestDto.setCreationDate(resultModel.getCreationDate());
                    jobRequestDto.setModificationDate(resultModel.getModificationDate());
                    
                    // adding the dto to the list
                    jobRequestListDto.getEntries().add(jobRequestDto);
                }
            }
        }catch(Exception e) {
            jobRequestListDto.addError(GlobalConstants.ERR_ENTRY_NOT_FOUND);
        }
        System.out.println("JobRequestListDto.getJobRequestList " + "end");
        return jobRequestListDto;
    }
	
	//getting all the List of Contracts under a Job Request from a Company
	public ContractListDto getTodoList(List<JobRequestDto> jobRequestDtoList) {
        System.out.println("PersonListDto.getTodoList " + "start");
        // initializing the dto to hold the list of Contracts.
        ContractListDto contractListDto =  new ContractListDto();
        
        try {
            // get the list of todos with the given status. 
            /*List<ContractModel> contractList = jobRequestDao.getContractList(jobRequestDtoList);
            if (contractList != null) {
                // convert each todoModel from the todoList into TodoDt
                for (ContractModel resultModel : contractList) {
                    ContractDto contractDto = new ContractDto();
                    
                    contractDto.setContractNumber(resultModel.getContractNumber());
                    contractDto.setContractorName(resultModel.getContractorName());
                    contractDto.setType(resultModel.getType());
                    contractDto.setStartDate(resultModel.getStartDate());
                    contractDto.setClosedDate(resultModel.getClosedDate());
                    contractDto.setEndDate(resultModel.getEndDate());
                    contractDto.setStatus(resultModel.getStatus());
                    contractDto.setCreationDate(resultModel.getCreationDate());
                    contractDto.setModificationDate(resultModel.getModificationDate());
                    
                    // adding the dto to the list
                    contractListDto.getEntries().add(contractDto);
                }
            }*/
            for(JobRequestDto jobRequests : jobRequestDtoList){
                List<String> contractNumbers = jobRequests.getContracts();
                for(String contracts : contractNumbers){
                    ContractModel contractModel = new ContractModel();
                    contractModel.setContractNumber(contracts);
                    
                    //get the ContractModel with the given contractNumber
                    ContractModel resultModel = contractDao.getContractByContractNumber(contractModel);
                    
                    // convert each ContractModel from the resultModel into ContractDto
                    if(resultModel != null){
                        ContractDto contractDto = new ContractDto();
                    
                        contractDto.setContractNumber(resultModel.getContractNumber());
                        contractDto.setContractorName(resultModel.getContractorName());
                        contractDto.setType(resultModel.getType());
                        contractDto.setStartDate(resultModel.getStartDate());
                        contractDto.setClosedDate(resultModel.getClosedDate());
                        contractDto.setEndDate(resultModel.getEndDate());
                        contractDto.setStatus(resultModel.getStatus());
                        contractDto.setCreationDate(resultModel.getCreationDate());
                        contractDto.setModificationDate(resultModel.getModificationDate());
                        
                        // adding the ContractDto to the ContractListDto
                        contractListDto.getEntries().add(contractDto);
                    }
                }
                
            }
        }catch(Exception e) {
            contractListDto.addError(GlobalConstants.ERR_ENTRY_NOT_FOUND);
        }
        System.out.println("PersonListDto.getTodoList " + "end");
        return contractListDto;
    }

    public JobRequestDto getJobRequestToEdit(String jobRequest) {
        System.out.println("JobRequestService.getJobRequestToEdit " + "start");
        //boolean flag = false;
        JobRequestDto jobRequestDto = new JobRequestDto();

        try {
            // get the list of todos with the given status. 
            JobRequestModel resultModel = jobRequestDao.getJobRequestToEdit(jobRequest);
            if(resultModel != null){
                jobRequestDto.setJobRequestNumber(resultModel.getJobRequestNumber());
                jobRequestDto.setDescription(resultModel.getDescription());
                jobRequestDto.setStartDate(resultModel.getStartDate());
                jobRequestDto.setEndDate(resultModel.getEndDate());
                jobRequestDto.setStatus(resultModel.getStatus());
                jobRequestDto.setContracts(resultModel.getContracts());
            }
        }catch(Exception e) {
            jobRequestDto.addError(GlobalConstants.ERR_ENTRY_NOT_FOUND);
        }
        
        System.out.println("JobRequestService.getJobRequestToEdit " + "end");
        return jobRequestDto;
    }

    public ContractListDto getContractsOfJobRequest(List<ContractDto> contracts) {
        System.out.println("JobRequestListDto.getContractsOfCompany " + "start");
        // initializing the dto to hold the list of todos.
        ContractListDto contractListDto =  new ContractListDto();
        
        try {
            /*// get the list of todos with the given status. 
            List<ContractModel> contractList = jobRequestDao.getContractsOfCompany(contracts);
            if (contractList != null) {
                // convert each todoModel from the todoList into TodoDt
                for (ContractModel resultModel : contractList) {
                    ContractDto contractDto = new ContractDto();
                    
                    contractDto.setContractNumber(resultModel.getContractNumber());
                    contractDto.setContractorName(resultModel.getContractorName());
                    contractDto.setType(resultModel.getType());
                    contractDto.setStartDate(resultModel.getStartDate());
                    contractDto.setClosedDate(resultModel.getClosedDate());
                    contractDto.setEndDate(resultModel.getEndDate());
                    contractDto.setStatus(resultModel.getStatus());
                    contractDto.setCreationDate(resultModel.getCreationDate());
                    contractDto.setModificationDate(resultModel.getModificationDate());
                    
                    // adding the dto to the list
                    contractListDto.getEntries().add(contractDto);
                }
            }*/
            for(ContractDto contract : contracts){
                ContractModel contractModel = new ContractModel();
                contractModel.setContractNumber(contract.getContractNumber());
                ContractModel resultModel = contractDao.getContractByContractNumber(contractModel);
                
                ContractDto contractDto = new ContractDto();
                
                contractDto.setContractNumber(resultModel.getContractNumber());
                contractDto.setContractorName(resultModel.getContractorName());
                contractDto.setType(resultModel.getType());
                contractDto.setStartDate(resultModel.getStartDate());
                contractDto.setClosedDate(resultModel.getClosedDate());
                contractDto.setEndDate(resultModel.getEndDate());
                contractDto.setStatus(resultModel.getStatus());
                contractDto.setCreationDate(resultModel.getCreationDate());
                contractDto.setModificationDate(resultModel.getModificationDate());
                
                // adding the dto to the list
                contractListDto.getEntries().add(contractDto);
            }
            
            
        }catch(Exception e) {
            contractListDto.addError(GlobalConstants.ERR_ENTRY_NOT_FOUND);
        }
        System.out.println("JobRequestListDto.getContractsOfCompany " + "end");
        return contractListDto;
    }

    /*public void updateJobRequestStatus(ArrayList<String> closedStatus,
            ArrayList<String> inProgressStatus, Date date) {
        System.out.println("JobRequestService.updateJobRequestStatus " + "start");
        // initializing the dto to hold the list of todos.
        
        try {
            // get the list of todos with the given status. 
            jobRequestDao.updateJobRequestStatus(closedStatus, inProgressStatus, date);
           
        }catch(Exception e) {
        }
        System.out.println("JobRequestService.updateJobRequestStatus " + "end");
    }*/
    
    public void updateJobRequestStatus(ArrayList<String> closedStatus,
            ArrayList<String> inProgressStatus, ArrayList<String> newStatus, Date closedDate) {
        System.out.println("JobRequestService.updateJobRequestStatus " + "start");
        // initializing the dto to hold the list of todos.
        
        try {
            // get the list of todos with the given status. 
            
            for(String closedStatuses : closedStatus){
                JobRequestModel jobRequestModel = new JobRequestModel();
                jobRequestModel.setJobRequestNumber(closedStatuses);
                jobRequestModel = jobRequestDao.getJobRequestById(jobRequestModel);
                
                jobRequestModel.setStatus("Closed");
                jobRequestModel.setClosedDate(closedDate);
                jobRequestDao.updateJobRequest(jobRequestModel);
            }
            
            for(String inProgressStatuses : inProgressStatus){
                JobRequestModel jobRequestModel = new JobRequestModel();
                jobRequestModel.setJobRequestNumber(inProgressStatuses);
                jobRequestModel = jobRequestDao.getJobRequestById(jobRequestModel);
                
                jobRequestModel.setStatus("In Progress");
                jobRequestDao.updateJobRequest(jobRequestModel);
            }
            
            for(String newStatuses : newStatus){
                JobRequestModel jobRequestModel = new JobRequestModel();
                jobRequestModel.setJobRequestNumber(newStatuses);
                jobRequestModel = jobRequestDao.getJobRequestById(jobRequestModel);
                
                jobRequestModel.setStatus("New");
                jobRequestDao.updateJobRequest(jobRequestModel);
            }
           
        }catch(Exception e) {
        }
        System.out.println("JobRequestService.updateJobRequestStatus " + "end");
    }
    
    public JobRequestListDto getTodoList() {
        System.out.println("PersonListDto.getTodoList " + "start");
        // initializing the dto to hold the list of todos.
        JobRequestListDto jobRequestListDto =  new JobRequestListDto();
        
        try {
            // get the list of todos with the given status. 
            List<JobRequestModel> jobRequestList = jobRequestDao.getJobRequestList();
            if (jobRequestList != null) {
                // convert each todoModel from the todoList into TodoDto
                for (JobRequestModel resultModel : jobRequestList) {
                    JobRequestDto jobRequestDto = new JobRequestDto();
                    
                    jobRequestDto.setJobRequestNumber(resultModel.getJobRequestNumber());
                    jobRequestDto.setDescription(resultModel.getDescription());
                    jobRequestDto.setStartDate(resultModel.getStartDate());
                    jobRequestDto.setClosedDate(resultModel.getClosedDate());
                    jobRequestDto.setEndDate(resultModel.getEndDate());
                    jobRequestDto.setContracts(resultModel.getContracts());
                    jobRequestDto.setStatus(resultModel.getStatus());
                    jobRequestDto.setCreationDate(resultModel.getCreationDate());
                    jobRequestDto.setModificationDate(resultModel.getModificationDate());
                    jobRequestDto.setCompanyId(resultModel.getCompanyId());
                    
                    // adding the dto to the list
                    jobRequestListDto.getEntries().add(jobRequestDto);
                }
            }
        }catch(Exception e) {
            jobRequestListDto.addError(GlobalConstants.ERR_ENTRY_NOT_FOUND);
        }
        System.out.println("PersonListDto.getTodoList " + "end");
        return jobRequestListDto;
    }
}
