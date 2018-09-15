package goodr0ne.trampwitter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrampWeetRepositoryTest {

    @Autowired
    private TrampWeetRepository trampWeetRepository;

    @Test
    public void testSaveAndReadSingleTrampWeet() {
        TrampWeet trampWeet = trampWeetRepository
                .save(new TrampWeet("leet"));
        TrampWeet savedTrampWeet = trampWeetRepository
                .getOne(trampWeet.getId());

        assertNotNull(savedTrampWeet);
        assertEquals(trampWeet.getJson(), savedTrampWeet.getJson());
    }
}
