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

public class ContractListDto extends BaseDto{
    
    private List<ContractDto> contractList = new ArrayList<ContractDto>();

    public List<ContractDto> getEntries() {
        return contractList;
    }

    public void setEntries(List<ContractDto> contractList) {
        this.contractList = contractList;
    }

    
}
