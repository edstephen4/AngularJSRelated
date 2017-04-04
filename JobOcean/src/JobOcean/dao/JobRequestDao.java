package JobOcean.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slim3.datastore.Datastore;

import JobOcean.dto.JobRequestDto;
import JobOcean.meta.CompanyModelMeta;
import JobOcean.meta.ContractModelMeta;
import JobOcean.meta.JobRequestModelMeta;
import JobOcean.model.CompanyModel;
import JobOcean.model.ContractModel;
import JobOcean.model.JobRequestModel;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Transaction;

/**
 * Controller that will accessed when 'localhost:8888' is entered.
 * @author Ed Stephen Koh
 * @version 0.01
 * Version History
 * [08/30/2016] 0.01 - Ed Stephen Koh - Initial codes.
 */

public class JobRequestDao {
	
	public void insertJobRequest(JobRequestModel jobRequestModel){
        System.out.println("JobrequestDao.insertJobRequest " + "start");
        
        Transaction transaction = Datastore.beginTransaction();
        // creating key and ID for the new entity
        Key parentKey = KeyFactory.createKey("JobRequest", jobRequestModel.getJobRequestNumber());
        Key key = Datastore.allocateId(parentKey, "JobRequestModel");
        
        // setting the 'key' and 'id' of the model
        jobRequestModel.setKey(key);
        jobRequestModel.setId(key.getId());
        
        // inserting the item to the datastore
        Datastore.put(jobRequestModel);
        transaction.commit();
        
        System.out.println("JobrequestDao.insertJobRequest " + "end");
	}
	
	public List<JobRequestModel> getJobRequestList(Long id){
        System.out.println("JobRequestDao.getJobRequestList " + "end");
        List<JobRequestModel> jobRequestModelList = new ArrayList<JobRequestModel>();
        JobRequestModelMeta meta = JobRequestModelMeta.get();
        jobRequestModelList = Datastore.query(meta).filter(meta.companyId.equal(id))
                .asList();
        System.out.println("JobRequestDao.getJobRequestList " + "end");
        return jobRequestModelList;
    }
	
	public List<JobRequestModel> getJobRequestList(){
        List<JobRequestModel> jobRequestModelList = new ArrayList<JobRequestModel>();
        JobRequestModelMeta meta = JobRequestModelMeta.get();
        jobRequestModelList = Datastore.query(meta)
                .asList();
        return jobRequestModelList;
    }
	
	public List<ContractModel> getContractList(List<JobRequestDto> jobRequestDtoList){
        System.out.println("JobRequestDao.getJobRequestList " + "end");
        List<ContractModel> contractModelList = new ArrayList<ContractModel>();
        for(JobRequestDto jobModel : jobRequestDtoList){
            List<String> contracts = jobModel.getContracts();
            for(String contract : contracts){
                ContractModelMeta meta = ContractModelMeta.get();
                contractModelList.add(Datastore.query(meta).filter(meta.contractNumber.equal(contract))
                        .asSingle());
            }
        }
        System.out.println("JobRequestDao.getJobRequestList " + "end");
        return contractModelList;
    }
	
	public JobRequestModel getJobRequestById(JobRequestModel inputJobRequest) {
        System.out.println("JobRequestDao.getJobRequestById " + "start");
        // container for the query result
        JobRequestModel jobRequestModel = new JobRequestModel();
        
        JobRequestModelMeta meta = JobRequestModelMeta.get();
        // retrieve a 'bento' with same name and id
        jobRequestModel = Datastore.query(meta)
                         .filter(meta.jobRequestNumber.equal(inputJobRequest.getJobRequestNumber()))
                         .asSingle();
        
        
        System.out.println("JobRequestDao.getJobRequestById " + "end");
        return jobRequestModel;
    }
	
	public void updateJobRequest(JobRequestModel inputJobRequest) {
        System.out.println("JobRequestDao.updateJobRequest " + "start");

        Transaction transaction = Datastore.beginTransaction();
        Datastore.put(inputJobRequest);
        transaction.commit();
        
        System.out.println("JobRequestDao.updateJobRequest " + "end");
    }
	
	public void deleteJobRequest(JobRequestModel model){
	    System.out.println("JobRequestDao.deleteJobRequest " + "start");

        Transaction transaction = Datastore.beginTransaction();
        Datastore.delete(model.getKey());
        transaction.commit();
        
        System.out.println("JobRequestDao.deleteJobRequest " + "end");
	}

    public JobRequestModel getJobRequestToEdit(String jobRequest) {
        System.out.println("JobRequestDao.getJobRequestToEdit " + "start");
        JobRequestModel jobRequestModel = new JobRequestModel();
        JobRequestModelMeta meta = JobRequestModelMeta.get();
        jobRequestModel = Datastore.query(meta)
                .filter(meta.jobRequestNumber.equal(jobRequest))
                .asSingle();
        System.out.println("JobRequestDao.getJobRequestToEdit " + "start");
        return jobRequestModel;
    }

    public List<ContractModel> getContractsOfCompany(ArrayList<String> contracts) {
        System.out.println("JobRequestDao.getJobRequestList " + "end");
        List<ContractModel> contractModelList = new ArrayList<ContractModel>();
        for(String contract : contracts){
            ContractModelMeta meta = ContractModelMeta.get();
            contractModelList.add(Datastore.query(meta).filter(meta.contractNumber.equal(contract))
                    .asSingle());
        }
        System.out.println("JobRequestDao.getJobRequestList " + "end");
        return contractModelList;
    }

    /*public void updateJobRequestStatus(ArrayList<String> closedStatus,
            ArrayList<String> inProgressStatus, Date dates) {
        System.out.println("JobRequestDao.updateJobRequestContractStatus " + "start");

        JobRequestModelMeta meta = JobRequestModelMeta.get();
        JobRequestModel jobRequestModel = new JobRequestModel();
        for(String status : closedStatus){
            jobRequestModel = Datastore.query(meta).filter(meta.jobRequestNumber.equal(status))
                    .asSingle();
            if(jobRequestModel != null){
                jobRequestModel.setStatus("Closed");
                jobRequestModel.setClosedDate(dates);
                
                Transaction transaction = Datastore.beginTransaction();
                Datastore.put(jobRequestModel);
                transaction.commit();
            }
        }
        
        for(String status : inProgressStatus){
            jobRequestModel = Datastore.query(meta).filter(meta.jobRequestNumber.equal(status))
                    .asSingle();
            if(jobRequestModel != null){
                jobRequestModel.setStatus("In Progress");
                
                Transaction transaction = Datastore.beginTransaction();
                Datastore.put(jobRequestModel);
                transaction.commit();
            }
        }
       
        System.out.println("JobRequestDao.updateJobRequestContractStatus " + "end");
    }*/
    
    public void updateJobRequestStatus(ArrayList<String> closedStatus,
            ArrayList<String> inProgressStatus, ArrayList<String> newStatus, Date dates) {
        System.out.println("JobRequestDao.updateJobRequestContractStatus " + "start");

        JobRequestModelMeta meta = JobRequestModelMeta.get();
        JobRequestModel jobRequestModel = new JobRequestModel();
        for(String status : closedStatus){
            jobRequestModel = Datastore.query(meta).filter(meta.jobRequestNumber.equal(status))
                    .asSingle();
            if(jobRequestModel != null){
                jobRequestModel.setStatus("Closed");
                jobRequestModel.setClosedDate(dates);
                
                Transaction transaction = Datastore.beginTransaction();
                Datastore.put(jobRequestModel);
                transaction.commit();
            }
        }
        
        for(String status : inProgressStatus){
            jobRequestModel = Datastore.query(meta).filter(meta.jobRequestNumber.equal(status))
                    .asSingle();
            if(jobRequestModel != null){
                jobRequestModel.setStatus("In Progress");
                
                Transaction transaction = Datastore.beginTransaction();
                Datastore.put(jobRequestModel);
                transaction.commit();
            }
        }
        
        for(String status : newStatus){
            jobRequestModel = Datastore.query(meta).filter(meta.jobRequestNumber.equal(status))
                    .asSingle();
            if(jobRequestModel != null){
                jobRequestModel.setStatus("New");
                
                Transaction transaction = Datastore.beginTransaction();
                Datastore.put(jobRequestModel);
                transaction.commit();
            }
        }
       
        System.out.println("JobRequestDao.updateJobRequestContractStatus " + "end");
    }
	
}
