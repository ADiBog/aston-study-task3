package ServiceTest;

import com.example.firstwebapp.entity.Beer;
import com.example.firstwebapp.service.BeerService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.TestConProv;

import java.io.IOException;
import java.sql.*;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class BeerServiceTest {

    private static final String H2_CONNECTION_STRING = "jdbc:h2:mem:test";
    static Connection connection;
    BeerService beerService = new BeerService(new TestConProv());
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
        List<Beer> beers = beerService.select();
        assertThat(beers.size()).isEqualTo(4);
    }

    @Test
    void insertTest() {
        Beer live = new Beer(5, "Live");
        beerService.insert(live);
        List<Beer> beers = beerService.select();
        assertThat(beers.size()).isEqualTo(5);
    }

    @Test
    void deleteTest() {
        beerService.delete(2);
        List<Beer> beers = beerService.select();
        assertThat(beers.size()).isEqualTo(beerService.select().size());
    }

}