package ServiceTest;

import com.example.firstwebapp.entity.Distributor;
import com.example.firstwebapp.service.DistributorService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.TestConProv;

import java.io.IOException;
import java.sql.*;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DistributorServiceTest {
    private static final String H2_CONNECTION_STRING = "jdbc:h2:mem:test";
    static Connection connection;
    DistributorService distributorService = new DistributorService(new TestConProv());
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
        List<Distributor> distributors = distributorService.select();
        assertThat(distributors.size()).isEqualTo(2);
    }

    @Test
    void insertTest() {
        Distributor spBeer = new Distributor(3, "SPBeer");
        distributorService.insert(spBeer);
        List<Distributor> distributors = distributorService.select();
        assertThat(distributors.size()).isEqualTo(3);
    }

    @Test
    void deleteTest() {
        distributorService.delete(2);
        List<Distributor> distributors = distributorService.select();
        assertThat(distributors.size()).isEqualTo(distributorService.select().size());
    }
}
