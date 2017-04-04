package JobOcean.dto;

import java.util.ArrayList;
import java.util.Date;


/**
 * Controller that will accessed when 'localhost:8888' is entered.
 * @author Ed Stephen Koh
 * @version 0.01
 * Version History
 * [08/30/2016] 0.01 - Ed Stephen Koh - Initial codes.
 */

public class JobRequestDto extends BaseDto{
    
    private Long id;
    private Long companyId;
    private String jobRequestNumber;
    private String description;
    private Date startDate;
    private Date endDate;
    private Date closedDate;
    private String status;
    private ArrayList<String> contracts;
    private Date creationDate;
    private Date modificationDate;

    public Date getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    
    public Date getModificationDate() {
        return modificationDate;
    }
    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }
    
    public ArrayList<String> getContracts() {
        return contracts;
    }
    public void setContracts(ArrayList<String> contracts) {
        this.contracts = contracts;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getCompanyId() {
        return companyId;
    }
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
    public String getJobRequestNumber() {
        return jobRequestNumber;
    }
    public void setJobRequestNumber(String jobRequestNumber) {
        this.jobRequestNumber = jobRequestNumber;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public Date getClosedDate() {
        return closedDate;
    }
    public void setClosedDate(Date closedDate) {
        this.closedDate = closedDate;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    
    
}
