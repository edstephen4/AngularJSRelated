package JobOcean.model;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class JobRequestModelTest extends AppEngineTestCase {

    private JobRequestModel model = new JobRequestModel();

    @Test
    public void test() throws Exception {
        assertThat(model, is(notNullValue()));
    }
}