package JobOcean.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Controller that will accessed when 'localhost:8888' is entered.
 * @author Ed Stephen Koh
 * @version 0.01
 * Version History
 * [08/30/2016] 0.01 - Ed Stephen Koh - Initial codes.
 */

public class JobRequestListDto extends BaseDto{
    
    private List<JobRequestDto> jobRequestList = new ArrayList<JobRequestDto>();

    public List<JobRequestDto> getEntries() {
        return jobRequestList;
    }

    public void setJobRequestList(List<JobRequestDto> jobRequestList) {
        this.jobRequestList = jobRequestList;
    }

    
}
