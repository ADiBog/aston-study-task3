package ServiceTest;

import com.example.firstwebapp.entity.BeerDistributor;
import com.example.firstwebapp.service.BeerDistributorService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.TestConProv;

import java.io.IOException;
import java.sql.*;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BeerDistributorServiceTest {
    private static final String H2_CONNECTION_STRING = "jdbc:h2:mem:test";
    static Connection connection;
    BeerDistributorService beerDistributorService = new BeerDistributorService(new TestConProv());
    private static String getFileContent() throws IOException {
        try (var inputStream = BeerServiceTest.class.getClassLoader().getResourceAsStream("seed.sql")) {
            if (inputStream == null) {
                return "";
            }
            return new String(inputStream.readAllBytes());
        }
    }

    @BeforeAll
    public static void init() throws SQLException, IOException {
        connection = DriverManager.getConnection(H2_CONNECTION_STRING);
        try (PreparedStatement preparedStatement = connection.prepareStatement(getFileContent())) {
            preparedStatement.executeUpdate();
        }
    }

    @Test
    void selectTest() {
        List<BeerDistributor> beerDistributors = beerDistributorService.select();
        assertThat(beerDistributors.size()).isEqualTo(5);
    }

    @Test
    void insertTest() {
        BeerDistributor bd = new BeerDistributor(4, 1);
        beerDistributorService.insert(bd);
        List<BeerDistributor> beerDistributors = beerDistributorService.select();
        assertThat(beerDistributors.size()).isEqualTo(6);
    }

    @Test
    void deleteTest() {
        beerDistributorService.delete(2);
        List<BeerDistributor> beerDistributors = beerDistributorService.select();
        assertThat(beerDistributors.size()).isEqualTo(beerDistributorService.select().size());
    }
}
