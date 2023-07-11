package ServletTest;

import com.example.firstwebapp.entity.Beer;
import com.example.firstwebapp.entity.BeerDistributor;
import com.example.firstwebapp.service.BeerDistributorService;
import com.example.firstwebapp.service.BeerService;
import com.example.firstwebapp.servlet.BeerDistributorServlet;
import com.example.firstwebapp.servlet.BeerServlet;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.TestConProv;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BeerDistributorServletTest {
    private static final String H2_CONNECTION_STRING = "jdbc:h2:mem:test";
    static Connection connection;
    BeerDistributorService beerDistributorService = new BeerDistributorService(new TestConProv());
    private static String getFileContent() throws IOException {
        try (var inputStream = BeerDistributorServletTest.class.getClassLoader().getResourceAsStream("seed.sql")) {
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
    public void testServletPost() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("beer_id")).thenReturn("1");
        when(request.getParameter("distributor_id")).thenReturn("1");
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        new BeerDistributorServlet(beerDistributorService).doPost(request, response);
        ArrayList<BeerDistributor> beerDistributors = beerDistributorService.select();
        Assertions.assertThat(beerDistributors).hasSize(beerDistributorService.select().size());
    }

    @Test
    public void testServletDel() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("beer_id")).thenReturn("1");
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        new BeerDistributorServlet(beerDistributorService).doDelete(request, response);
        ArrayList<BeerDistributor> beerDistributors = beerDistributorService.select();
        Assertions.assertThat(beerDistributors).hasSize(beerDistributorService.select().size());
    }
}
