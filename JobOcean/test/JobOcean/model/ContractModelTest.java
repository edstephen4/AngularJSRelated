package JobOcean.model;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class ContractModelTest extends AppEngineTestCase {

    private ContractModel model = new ContractModel();

    @Test
    public void test() throws Exception {
        assertThat(model, is(notNullValue()));
    }
}
